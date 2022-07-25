package com.DH.ClinicaOdondotologicaEntregaFinal.dto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.Date;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TurnDTO {
    private Long id;
    private Date date;
    private String patientName;
    private String patientDni;
    private String odontologistName;
    private String odontologistRegistrationNumber;

    public TurnDTO(Long id, Date date, String patientName, String patientDni, String odontologistName, String odontologistRegistrationNumber) {
        this.id = id;
        this.date = date;
        this.patientName = patientName;
        this.patientDni = patientDni;
        this.odontologistName = odontologistName;
        this.odontologistRegistrationNumber = odontologistRegistrationNumber;
    }
    public TurnDTO(){}
}
