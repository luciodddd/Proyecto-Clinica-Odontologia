package com.DH.ClinicaOdondotologicaEntregaFinal.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Entity
@Getter
@Setter
@ToString
public class Turn {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "pacient_id",referencedColumnName = "id")
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "odontologist_id",referencedColumnName = "id")
    private Odontologist odontologist;

    private Date date;

    public Turn(Long id, Patient patient, Odontologist odontologist, Date date) {
        this.id = id;
        this.patient = patient;
        this.odontologist = odontologist;
        this.date = date;
    }
    public Turn(){}
}
