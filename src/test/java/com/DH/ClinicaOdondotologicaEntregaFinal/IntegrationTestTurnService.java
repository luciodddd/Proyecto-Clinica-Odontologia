package com.DH.ClinicaOdondotologicaEntregaFinal;

import com.DH.ClinicaOdondotologicaEntregaFinal.dto.PatientDTO;
import com.DH.ClinicaOdondotologicaEntregaFinal.dto.TurnDTO;
import com.DH.ClinicaOdondotologicaEntregaFinal.entities.*;
import com.DH.ClinicaOdondotologicaEntregaFinal.servivies.OdontologistService;
import com.DH.ClinicaOdondotologicaEntregaFinal.servivies.PatientService;
import com.DH.ClinicaOdondotologicaEntregaFinal.servivies.TurnService;
import com.fasterxml.jackson.databind.ObjectMapper;
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

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class IntegrationTestTurnService {
    @Autowired
    TurnService turnService;
    @Autowired
    PatientService patientService;
    @Autowired
    OdontologistService odontologistService;
    @Autowired
    private MockMvc mockMvc;
    public ObjectMapper mapper = new ObjectMapper();

    static public Address address =new Address("Caseros","123","cordoba","cordoba");
    static public Patient patient = new Patient("Lucio","Dipre","38596300", null , address);
    static public Odontologist odontologist = new Odontologist("Pedro","Mario","12345");
    static public TurnPOJO turnPOJO = new TurnPOJO(null, "38596300","12345");

    @Test
    public void saveTest() throws Exception {
        // Save:
        patientService.save(patient);
        odontologistService.save(odontologist);
        TurnDTO turnDTO = new TurnDTO (4L,null, "Lucio Dipre", "38596300","Pedro Mario", "12345");
        String payloadJson = mapper.writeValueAsString(turnPOJO);
        String respondJson = mapper.writeValueAsString(turnDTO);
        MvcResult responseSave = this.mockMvc.perform(MockMvcRequestBuilders.post("/turn/save")
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

        Turn turn = new Turn (4L,patient,odontologist,null);
        String respondJsonSearch = mapper.writeValueAsString(turn);

        MvcResult responseSearch = this.mockMvc.perform(MockMvcRequestBuilders.get("/turn/{id}", "4"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andReturn();
        Assertions.assertEquals(respondJsonSearch,responseSearch.getResponse().getContentAsString());
    }
}