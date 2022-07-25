package com.DH.ClinicaOdondotologicaEntregaFinal.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class TurnPOJO {
    Long id;
    Date date;
    String dni;
    String registrationNumber;

    public TurnPOJO(Date date, String dni, String registrationNumber) {
        this.date = date;
        this.dni = dni;
        this.registrationNumber = registrationNumber;
    }
    public TurnPOJO(){}
}
