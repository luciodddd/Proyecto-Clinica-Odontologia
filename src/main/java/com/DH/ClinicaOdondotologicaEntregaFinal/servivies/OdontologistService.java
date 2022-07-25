package com.DH.ClinicaOdondotologicaEntregaFinal.servivies;

import com.DH.ClinicaOdondotologicaEntregaFinal.dto.OdontologistDTO;
import com.DH.ClinicaOdondotologicaEntregaFinal.entities.Odontologist;
import com.DH.ClinicaOdondotologicaEntregaFinal.exceptions.NotFoundException;
import com.DH.ClinicaOdondotologicaEntregaFinal.repositories.OdontologistRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Service
public class OdontologistService {
    @Autowired
    private OdontologistRepository odontologistRepository;
    @Autowired
    ObjectMapper mapper;

    public static Logger logger = Logger.getLogger(String.valueOf(OdontologistService.class));

    private OdontologistDTO dtoConversion(Odontologist odontologist){
        OdontologistDTO odontologistDTO = mapper.convertValue(odontologist,OdontologistDTO.class);
        try {
            odontologistDTO.setNumberOfTurns(odontologist.getTurns().size());

        } catch (NullPointerException e){
            odontologistDTO.setNumberOfTurns(0);
        }
        odontologistDTO.setFullName(odontologist.getName()+" "+odontologist.getLastName());
        return odontologistDTO;
    }

    public OdontologistDTO save(Odontologist odontologist){
        odontologistRepository.save(odontologist);
        logger.info("Guardando "+odontologist);
        OdontologistDTO odontologistDTO = dtoConversion(odontologist);
        return odontologistDTO;
    }

    public void delete(Long id){
        odontologistRepository.deleteById(id);
        logger.info("Eliminando Odontólogo id: " + id);
    }

    public OdontologistDTO update(Odontologist odontologist){
        odontologistRepository.updateOdontologist(
                odontologist.getName(),
                odontologist.getLastName(),
                odontologist.getRegistrationNumber(),
                odontologist.getId()
        );
        logger.info("Modificando "+odontologist);
        OdontologistDTO odontologistDTO = dtoConversion(odontologist);
        return odontologistDTO;
    }

    public List<OdontologistDTO> getAllOdontologists() throws NotFoundException {
        List<Odontologist> listOdontologists = odontologistRepository.findAll();
        List<OdontologistDTO> listOdontologistsDTO = new ArrayList<>();
        if (listOdontologists.size()==0){
            throw new NotFoundException("No hay odontologos que mostrar");
        }
        for (Odontologist o: listOdontologists){
            listOdontologistsDTO.add(dtoConversion(o));
        }
        return listOdontologistsDTO;
    }
    public Odontologist searchOdontologist(Long id){
        Odontologist odontologist = odontologistRepository
                .findById(id)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return odontologist;
    }
    public OdontologistDTO searchOdontologistByRegistrationNumber(String registrationNumber) throws NotFoundException {
        Odontologist odontologist = null;
        try{
            odontologist = odontologistRepository.findOdontologistByRegistrationNumber(registrationNumber).get(0);
        } catch (Exception e){
            throw new NotFoundException("No hay odontologos que mostrar");
        }

        OdontologistDTO odontologistDTO = dtoConversion(odontologist);
        return odontologistDTO;
    }
}
