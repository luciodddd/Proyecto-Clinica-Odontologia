package com.DH.ClinicaOdondotologicaEntregaFinal;

import ch.qos.logback.core.net.ObjectWriter;
import com.DH.ClinicaOdondotologicaEntregaFinal.dto.OdontologistDTO;
import com.DH.ClinicaOdondotologicaEntregaFinal.entities.Address;
import com.DH.ClinicaOdondotologicaEntregaFinal.entities.Odontologist;
import com.DH.ClinicaOdondotologicaEntregaFinal.entities.Patient;
import com.DH.ClinicaOdondotologicaEntregaFinal.exceptions.NotFoundException;
import com.DH.ClinicaOdondotologicaEntregaFinal.servivies.OdontologistService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.platform.engine.TestExecutionResult;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class IntegrationTestOdontologistService {
    @Autowired
    OdontologistService odontologistService;
    @Autowired
    private MockMvc mockMvc;

    static public Odontologist odontologist = new Odontologist("Pedro","Mario","12345");
    static public OdontologistDTO odontologistDTO = new OdontologistDTO (1L,"Pedro Mario", "12345", 0);
    ObjectMapper mapper = new ObjectMapper();
    @Test
    public void saveTest() throws Exception {
        String payloadJson = mapper.writeValueAsString(odontologist);
        String respondJson = mapper.writeValueAsString(odontologistDTO);

        MvcResult response = this.mockMvc.perform(MockMvcRequestBuilders.post("/odontologist")
                .contentType(MediaType.APPLICATION_JSON)
                .content(payloadJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andReturn();
        Assertions.assertEquals(respondJson,response.getResponse().getContentAsString());

    }

    @Test
    public void searchTest() throws Exception {
        // Search:
        odontologistService.save(odontologist);
        Odontologist odontologist1 = odontologist;
        odontologist1.setId(1L);
        String respondJsonSearch = mapper.writeValueAsString(odontologist1);

        MvcResult responseSearch = this.mockMvc.perform(MockMvcRequestBuilders.get("/odontologist/{id}", "1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andReturn();
        Assertions.assertEquals(respondJsonSearch,responseSearch.getResponse().getContentAsString());
    }
}