package com.DH.ClinicaOdondotologicaEntregaFinal.entities;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String name;
    private String lastName;
    @Column
    private String dni;
    private String password = null;
    private Date entryDate;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;

    @OneToMany(
            mappedBy = "patient",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    @JsonIgnore
    private List<Turn> turns;

    public Patient(String name, String lastName, String dni, Date entryDate, Address address) {
        this.name = name;
        this.lastName = lastName;
        this.dni = dni;
        this.entryDate = entryDate;
        this.address = address;
    }

    public Patient(Long id,String name, String lastName, String dni, Date entryDate, Address address) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.dni = dni;
        this.entryDate = entryDate;
        this.address = address;
    }
    public Patient(){}
}
