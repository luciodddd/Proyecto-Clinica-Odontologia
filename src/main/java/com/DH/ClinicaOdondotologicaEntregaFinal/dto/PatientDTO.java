package com.DH.ClinicaOdondotologicaEntregaFinal.dto;
import com.DH.ClinicaOdondotologicaEntregaFinal.entities.Turn;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PatientDTO {
    private Long id;
    private String fullName;
    private String dni;
    private String location;
    private String province;
    private Integer numberOfTurns;

    public PatientDTO(Long id, String fullName, String dni, String location, String province, Integer numberOfTurns) {
        this.id = id;
        this.fullName = fullName;
        this.dni = dni;
        this.location = location;
        this.province = province;
        this.numberOfTurns = numberOfTurns;
    }
    public  PatientDTO(){}
}
