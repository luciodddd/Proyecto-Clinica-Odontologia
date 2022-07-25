package com.DH.ClinicaOdondotologicaEntregaFinal;

import com.DH.ClinicaOdondotologicaEntregaFinal.dto.OdontologistDTO;
import com.DH.ClinicaOdondotologicaEntregaFinal.dto.PatientDTO;
import com.DH.ClinicaOdondotologicaEntregaFinal.entities.Address;
import com.DH.ClinicaOdondotologicaEntregaFinal.entities.Odontologist;
import com.DH.ClinicaOdondotologicaEntregaFinal.entities.Patient;
import com.DH.ClinicaOdondotologicaEntregaFinal.exceptions.NotFoundException;
import com.DH.ClinicaOdondotologicaEntregaFinal.servivies.PatientService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class IntegrationTestPatientService {
    @Autowired
    PatientService patientService;
    @Autowired
    private MockMvc mockMvc;
    public ObjectMapper mapper = new ObjectMapper();

    static public Address address =new Address("Caseros","123","cordoba","cordoba");
    static public Patient patient = new Patient("Lucio","Dipre","38596300", null , address);

    @Test
    public void saveTest() throws Exception {
        // Save:
        PatientDTO patientDTO = new PatientDTO (1L,"Lucio Dipre", "38596300", "cordoba","cordoba", 0);
        String payloadJson = mapper.writeValueAsString(patient);
        String respondJson = mapper.writeValueAsString(patientDTO);
        MvcResult responseSave = this.mockMvc.perform(MockMvcRequestBuilders.post("/patient")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payloadJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andReturn();
        Assertions.assertEquals(respondJson,responseSave.getResponse().getContentAsString());
    }

    @Test
    public void searchTest() throws Exception {
        // Search:
        patientService.save(patient);
        Patient patient1 = patient;
        Address address1 = address;
        address1.setId(4L);
        patient1.setId(1L);
        patient1.setAddress(address1);
        String respondJsonSearch = mapper.writeValueAsString(patient1);

        MvcResult responseSearch = this.mockMvc.perform(MockMvcRequestBuilders.get("/patient/{id}", "1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andReturn();
        Assertions.assertEquals(respondJsonSearch,responseSearch.getResponse().getContentAsString());
    }
}