package com.DH.ClinicaOdondotologicaEntregaFinal.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AddressDTO {
    private Long id;
    private String street;
    private String number;
    private String location;
    private String province;
}
