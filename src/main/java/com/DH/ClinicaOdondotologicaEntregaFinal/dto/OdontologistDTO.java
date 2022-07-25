package com.DH.ClinicaOdondotologicaEntregaFinal.dto;
import com.DH.ClinicaOdondotologicaEntregaFinal.entities.Turn;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class OdontologistDTO {
    private Long id;
    private String fullName;
    private String registrationNumber;
    private Integer numberOfTurns;

    public OdontologistDTO(Long id, String fullName, String registrationNumber, Integer numberOfTurns) {
        this.id = id;
        this.fullName = fullName;
        this.registrationNumber = registrationNumber;
        this.numberOfTurns = numberOfTurns;
    }
    public OdontologistDTO(){}
}

