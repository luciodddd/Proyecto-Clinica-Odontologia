package com.DH.ClinicaOdondotologicaEntregaFinal.controllers;

import com.DH.ClinicaOdondotologicaEntregaFinal.dto.OdontologistDTO;
import com.DH.ClinicaOdondotologicaEntregaFinal.entities.Odontologist;
import com.DH.ClinicaOdondotologicaEntregaFinal.exceptions.NotFoundException;
import com.DH.ClinicaOdondotologicaEntregaFinal.servivies.OdontologistService;
import com.DH.ClinicaOdondotologicaEntregaFinal.servivies.OdontologistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RestController
@RequestMapping("/odontologist")
public class OdontologistController {
    @Autowired
    OdontologistService odontologistService;

    @PostMapping
    public ResponseEntity<OdontologistDTO> saveOdontologist(@RequestBody Odontologist odontologist){
        return ResponseEntity.ok(odontologistService.save(odontologist));
    }

    @GetMapping("/registrationNumber/{registrationNumber}")
    public ResponseEntity<OdontologistDTO> getOdontologistByDni(@PathVariable String registrationNumber) throws NotFoundException {
        return ResponseEntity.ok(odontologistService.searchOdontologistByRegistrationNumber(registrationNumber));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Odontologist> getOdontologist(@PathVariable String id) throws NotFoundException {
        return ResponseEntity.ok(odontologistService.searchOdontologist(Long.parseLong(id)));
    }

    @DeleteMapping("/{id}")
    public void deleteOdontologist(@PathVariable String id){
        odontologistService.delete(Long.parseLong(id));
    }

    @GetMapping
    public ResponseEntity<List<OdontologistDTO>> listAllOdontologists() throws NotFoundException {
        return ResponseEntity.ok(odontologistService.getAllOdontologists());
    }
    @PutMapping
    public ResponseEntity<OdontologistDTO> updateOdontologist(@RequestBody Odontologist odontologist){
        return ResponseEntity.ok(odontologistService.update(odontologist));
    }
}
