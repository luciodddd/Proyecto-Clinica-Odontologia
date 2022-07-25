package com.DH.ClinicaOdondotologicaEntregaFinal.servivies;

import com.DH.ClinicaOdondotologicaEntregaFinal.dto.PatientDTO;
import com.DH.ClinicaOdondotologicaEntregaFinal.entities.Patient;
import com.DH.ClinicaOdondotologicaEntregaFinal.exceptions.NotFoundException;
import com.DH.ClinicaOdondotologicaEntregaFinal.repositories.AddressRepository;
import com.DH.ClinicaOdondotologicaEntregaFinal.repositories.PatientRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Service
public class PatientService {
    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    ObjectMapper mapper;

    public static Logger logger = Logger.getLogger(String.valueOf(PatientService.class));

    private PatientDTO dtoConversion(Patient patient){
        PatientDTO patientDTO = mapper.convertValue(patient,PatientDTO.class);
        try {
            patientDTO.setNumberOfTurns(patient.getTurns().size());

        } catch (NullPointerException e){
            patientDTO.setNumberOfTurns(0);
        }
        patientDTO.setFullName(patient.getName()+" "+patient.getLastName());
        patientDTO.setLocation(patient.getAddress().getLocation());
        patientDTO.setProvince(patient.getAddress().getProvince());
        return patientDTO;
    }

    public PatientDTO save(Patient patient){
        if (patient.getPassword()==null){
            patient.setPassword(patient.getDni());
        }
        patientRepository.save(patient);
        PatientDTO patientDTO = dtoConversion(patient);
        logger.info("Creando "+patient);
        return patientDTO;
    }

    public void delete(Long id){
        logger.info("Eliminando paciente con id: "+ searchPatient(id).getId());
        patientRepository.deleteById(id);
    }

    public PatientDTO update(Patient patient){
        patientRepository.updatePatient(
                patient.getName(),
                patient.getLastName(),
                patient.getDni(),
                patient.getEntryDate(),
                patient.getId()
        );
        addressRepository.updateAdress(
                patient.getAddress().getStreet(),
                patient.getAddress().getNumber(),
                patient.getAddress().getLocation(),
                patient.getAddress().getProvince(),
                patient.getAddress().getId()
                );
        logger.info("Se actualizó "+patient);
        PatientDTO patientDTO = dtoConversion(patient);
        return patientDTO;
    }

    public List<PatientDTO> getAllPatients() throws NotFoundException {
        List<Patient> listPatients = patientRepository.findAll();
        List<PatientDTO> listPatientsDTO = new ArrayList<>();
        if (listPatients.size()==0){
            throw new NotFoundException("No hay pacientes que mostrar");
        }
        for (Patient p: listPatients){
            listPatientsDTO.add(dtoConversion(p));
        }
        return listPatientsDTO;
    }
    public Patient searchPatient(Long id){
        Patient patient = patientRepository
                .findById(id)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return patient;
    }
    public PatientDTO searchPatientByDni(String dni) throws NotFoundException {
        Patient patient = null;
        try {
            patient = patientRepository.findPatientByDni2(dni).get(0);
        } catch (Exception e){throw new NotFoundException("No se ha encontrado al paciente");}

        PatientDTO patientDTO = dtoConversion(patient);
        return patientDTO;
    }
}
