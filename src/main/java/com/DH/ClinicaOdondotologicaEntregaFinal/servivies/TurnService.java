package com.DH.ClinicaOdondotologicaEntregaFinal.servivies;

import com.DH.ClinicaOdondotologicaEntregaFinal.dto.OdontologistDTO;
import com.DH.ClinicaOdondotologicaEntregaFinal.dto.PatientDTO;
import com.DH.ClinicaOdondotologicaEntregaFinal.dto.TurnDTO;
import com.DH.ClinicaOdondotologicaEntregaFinal.entities.*;
import com.DH.ClinicaOdondotologicaEntregaFinal.exceptions.NotFoundException;
import com.DH.ClinicaOdondotologicaEntregaFinal.repositories.TurnRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jdk.swing.interop.SwingInterOpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

@Service
public class TurnService {
    @Autowired
    private TurnRepository turnRepository;
    @Autowired
    private PatientService patientService;
    @Autowired
    private OdontologistService odontologistService;

    @Autowired
    ObjectMapper mapper;

    public static Logger logger = Logger.getLogger(String.valueOf(TurnService.class));

    private TurnDTO dtoConversion(Turn turn){
        TurnDTO turnDTO = mapper.convertValue(turn,TurnDTO.class);
        Patient patient = patientService.searchPatient(turn.getPatient().getId());
        Odontologist odontologist = odontologistService.searchOdontologist(turn.getOdontologist().getId());
        turnDTO.setPatientName(patient.getName()+" "+patient.getLastName());
        turnDTO.setOdontologistName(odontologist.getName()+" "+odontologist.getLastName());
        turnDTO.setPatientDni(patient.getDni());
        turnDTO.setOdontologistRegistrationNumber(odontologist.getRegistrationNumber());
        return turnDTO;
    }
    private Turn dtoConversionFromPojo(TurnPOJO turnPOJO) throws NotFoundException {
        Turn turn = new Turn();
        PatientDTO patient = patientService.searchPatientByDni(turnPOJO.getDni());
        OdontologistDTO odontologist = odontologistService.searchOdontologistByRegistrationNumber(turnPOJO.getRegistrationNumber());
        turn.setDate(turnPOJO.getDate());
        turn.setPatient(mapper.convertValue(patient,Patient.class));
        turn.setOdontologist(mapper.convertValue(odontologist,Odontologist.class));
        if (turnPOJO.getId() != null) {
            turn.setId(turnPOJO.getId());
        }
        return turn;
    }


    public TurnDTO saveFromPOJO(TurnPOJO turnPOJO) throws NotFoundException {
        logger.info("Creando turno" + turnPOJO);
        return save(dtoConversionFromPojo(turnPOJO));
    }

    public TurnDTO save(Turn turn){
        turnRepository.save(turn);
        logger.info("Creando turno" + turn);
        return dtoConversion(turn);
    }

    public void delete(Long id){
        turnRepository.deleteById(id);
        logger.info("Eliminando turno id " + id);
    }

    /*public TurnDTO updateQuery(TurnPOJO turnPOJO) throws NotFoundException {
        turnRepository.updateTurn(
                turnPOJO.getDate(),
                patientService.searchPatientByDni(turnPOJO.getDni()).getId(),
                odontologistService.searchOdontologistByRegistrationNumber(turnPOJO.getRegistrationNumber()).getId(),
                turnPOJO.getId()
        );
        TurnDTO turnDTO = dtoConversion(dtoConversionFromPojo(turnPOJO));
        return turnDTO;
    }*/

    public TurnDTO update(TurnPOJO turn) throws NotFoundException {
        logger.info("Modificando turno" + turn);
        return saveFromPOJO(turn);}

    public List<TurnDTO> getAllTurns() throws NotFoundException {
        List<Turn> listTurns = turnRepository.findAll();
        List<TurnDTO> listTurnsDTO = new ArrayList<>();
        if (listTurns.size()==0){
            throw new NotFoundException("No hay turnos que mostrar");
        }
        for (Turn t: listTurns){
            listTurnsDTO.add(dtoConversion(t));
        }
        return listTurnsDTO;
    }
    public Turn searchTurn(Long id){
        Turn turn = turnRepository
                .findById(id)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return turn;
    }
    public List<TurnDTO> searchTurnByPatient(Long id) throws NotFoundException {
        List<TurnDTO> turnDTOList = new ArrayList<>();
        try{
            List<Turn> turnList = patientService.searchPatient(id).getTurns();
            for (Turn t: turnList) {
                turnDTOList.add(dtoConversion(t));
            }
        } catch (Exception e){
            throw new NotFoundException("No hay turnos para este paciente");
        }
        return turnDTOList;
    }

    public List<TurnDTO> searchTurnByOdontologist(Long id) throws NotFoundException {
        List<TurnDTO> turnDTOList = new ArrayList<>();
        try{
            List<Turn> turnList = odontologistService.searchOdontologist(id).getTurns();
            for (Turn t: turnList) {
                turnDTOList.add(dtoConversion(t));
            }
        } catch (Exception e){
            throw new NotFoundException("No hay turnos para este odontólogo");
        }
        return turnDTOList;
    }

    public List<TurnDTO> searchTurnByDate(DatePOJO datePOJO) throws NotFoundException {
        List<TurnDTO> turnDTOList = new ArrayList<>();
        try{
            List<Turn> turnList = turnRepository.findTurnByDate(datePOJO.getMinDate(),datePOJO.getMaxDate());
            for (Turn t: turnList) {
                turnDTOList.add(dtoConversion(t));
            }
        } catch (Exception e){
            throw new NotFoundException("No hay turnos para este paciente");
        }
        return turnDTOList;
    }

    public List<TurnDTO> searchTurnByRegistrationNumber(String registrationNumber) throws NotFoundException {
        Long odontologistId = odontologistService.searchOdontologistByRegistrationNumber(registrationNumber).getId();
        return searchTurnByOdontologist(odontologistId);
    }

    public List<TurnDTO> searchTurnByDni(String dni) throws NotFoundException {
        Long patientId = patientService.searchPatientByDni(dni).getId();
        return searchTurnByPatient(patientId);
    }


    public List<TurnDTO> searchTurnByDateParameters(Date minDate, Date maxDate) throws NotFoundException {
        List<TurnDTO> turnDTOList = new ArrayList<>();
        try{
            List<Turn> turnList = turnRepository.findTurnByDate(minDate,maxDate);
            for (Turn t: turnList) {
                turnDTOList.add(dtoConversion(t));
            }
        } catch (Exception e){
            throw new NotFoundException("No hay turnos para este paciente");
        }
        return turnDTOList;
    }
}
