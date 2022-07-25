package com.DH.ClinicaOdondotologicaEntregaFinal.entities;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@Table(name = "address")
@ToString
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String street;
    private String number;
    private String location;
    private String province;

    public Address(String street, String number, String location, String province) {
        this.street = street;
        this.number = number;
        this.location = location;
        this.province = province;
    }
    public Address(){}
}
