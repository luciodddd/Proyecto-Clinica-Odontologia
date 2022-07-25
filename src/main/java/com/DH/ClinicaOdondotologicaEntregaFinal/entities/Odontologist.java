package com.DH.ClinicaOdondotologicaEntregaFinal.entities;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Entity
@Getter
@Setter
@ToString
public class Odontologist {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String name;
    private String lastName;
    private String registrationNumber;

    @OneToMany(
            mappedBy = "odontologist",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    @JsonIgnore
    private List<Turn> turns;

    public Odontologist(String name, String lastName, String registrationNumber) {
        this.name = name;
        this.lastName = lastName;
        this.registrationNumber = registrationNumber;
    }

    public Odontologist(Long id,String name, String lastName, String registrationNumber) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.registrationNumber = registrationNumber;
    }
    public Odontologist(){}
}
