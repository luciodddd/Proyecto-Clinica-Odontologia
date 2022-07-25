package com.DH.ClinicaOdondotologicaEntregaFinal.controllers;

import com.DH.ClinicaOdondotologicaEntregaFinal.dto.PatientDTO;
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
@RequestMapping("/patient")
public class PatientController {
    @Autowired
    PatientService patientService;

    @PostMapping
    public ResponseEntity<PatientDTO> savePatient(@RequestBody Patient patient){
        return ResponseEntity.ok(patientService.save(patient));
    }

    @GetMapping("/dni/{dni}")
    public ResponseEntity<PatientDTO> getPatientByDni(@PathVariable String dni) throws NotFoundException {
        return ResponseEntity.ok(patientService.searchPatientByDni(dni));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Patient> getPatient(@PathVariable String id) throws NotFoundException {
        return ResponseEntity.ok(patientService.searchPatient(Long.parseLong(id)));
    }



    @DeleteMapping("/{id}")
    public void deletePatient(@PathVariable String id){
        patientService.delete(Long.parseLong(id));
    }

    @GetMapping
    public ResponseEntity<List<PatientDTO>> listAllPatients() throws NotFoundException {
        return ResponseEntity.ok(patientService.getAllPatients());
    }
    @PutMapping
    public ResponseEntity<PatientDTO> updatePatient(@RequestBody Patient patient){
        return ResponseEntity.ok(patientService.update(patient));
    }
}
