package com.DH.ClinicaOdondotologicaEntregaFinal.controllers;

import com.DH.ClinicaOdondotologicaEntregaFinal.dto.PatientDTO;
import com.DH.ClinicaOdondotologicaEntregaFinal.entities.LoginPOJO;
import com.DH.ClinicaOdondotologicaEntregaFinal.entities.Patient;
import com.DH.ClinicaOdondotologicaEntregaFinal.exceptions.NotFoundException;
import com.DH.ClinicaOdondotologicaEntregaFinal.servivies.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RestController
@RequestMapping("/login")
public class LoginController {
    @Autowired
    PatientService patientService;

    @GetMapping("/{dni}")
    public ResponseEntity<LoginPOJO> getPatientByDni(@PathVariable String dni) throws NotFoundException {
        LoginPOJO loginPOJO = new LoginPOJO();
        loginPOJO.setId(patientService.searchPatientByDni(dni).getId());
        loginPOJO.setPassword(patientService.searchPatient(patientService.searchPatientByDni(dni).getId()).getPassword());
        return ResponseEntity.ok(loginPOJO);
    }
}
