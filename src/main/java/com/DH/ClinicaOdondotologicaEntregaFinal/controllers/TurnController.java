package com.DH.ClinicaOdondotologicaEntregaFinal.controllers;

import com.DH.ClinicaOdondotologicaEntregaFinal.dto.TurnDTO;
import com.DH.ClinicaOdondotologicaEntregaFinal.entities.DatePOJO;
import com.DH.ClinicaOdondotologicaEntregaFinal.entities.Turn;
import com.DH.ClinicaOdondotologicaEntregaFinal.entities.TurnPOJO;
import com.DH.ClinicaOdondotologicaEntregaFinal.exceptions.NotFoundException;
import com.DH.ClinicaOdondotologicaEntregaFinal.servivies.TurnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RestController
@RequestMapping("/turn")
public class TurnController {
    @Autowired
    TurnService turnService;

    @PostMapping
    public ResponseEntity<TurnDTO> saveTurn(@RequestBody Turn turn){
        return ResponseEntity.ok(turnService.save(turn));
    }
    @PostMapping("/save")
    public ResponseEntity<TurnDTO> saveTurnPOJO(@RequestBody TurnPOJO turnPOJO) throws NotFoundException {
        return ResponseEntity.ok(turnService.saveFromPOJO(turnPOJO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Turn> getTurn(@PathVariable String id) throws NotFoundException {
        return ResponseEntity.ok(turnService.searchTurn(Long.parseLong(id)));
    }

    @DeleteMapping("/{id}")
    public void deleteTurn(@PathVariable String id){
        turnService.delete(Long.parseLong(id));
    }

    @GetMapping("/patient-id/{id}")
    public ResponseEntity<List<TurnDTO>> getTurnByPatient(@PathVariable String id) throws NotFoundException {
        return ResponseEntity.ok(turnService.searchTurnByPatient(Long.parseLong(id)));
    }

    @GetMapping("/odontologist-id/{id}")
    public ResponseEntity<List<TurnDTO>> getTurnByOdontologist(@PathVariable String id) throws NotFoundException {
        return ResponseEntity.ok(turnService.searchTurnByOdontologist(Long.parseLong(id)));
    }

    @GetMapping("/by-date")
    public ResponseEntity<List<TurnDTO>> getTurnByDate(@RequestBody DatePOJO datePOJO) throws NotFoundException {
        return ResponseEntity.ok(turnService.searchTurnByDate(datePOJO));
    }
    @GetMapping("/by-date/{minDate}/{maxDate}")
    public ResponseEntity<List<TurnDTO>> getTurnByDateParameters(@PathVariable String minDate,@PathVariable String maxDate) throws NotFoundException, ParseException {
        System.out.println(new SimpleDateFormat("yyyy-MM-dd").parse(minDate));
        Date minDateTransformed = new SimpleDateFormat("yyyy-MM-dd").parse(minDate);
        Date maxDateTransformed = new SimpleDateFormat("yyyy-MM-dd").parse(maxDate);
        return ResponseEntity.ok(turnService.searchTurnByDateParameters(minDateTransformed,maxDateTransformed));
    }

    @GetMapping
    public ResponseEntity<List<TurnDTO>> listAllTurns() throws NotFoundException {
        return ResponseEntity.ok(turnService.getAllTurns());
    }

    @GetMapping("/patient-dni/{dni}")
    public ResponseEntity<List<TurnDTO>> getTurnByPatientDni(@PathVariable String dni) throws NotFoundException {
        return ResponseEntity.ok(turnService.searchTurnByDni(dni));
    }

    @GetMapping("/odontologist-registrationNumber/{registrationNumber}")
    public ResponseEntity<List<TurnDTO>> getTurnByOdontologistRegistrationNumber(@PathVariable String registrationNumber) throws NotFoundException {
        return ResponseEntity.ok(turnService.searchTurnByRegistrationNumber(registrationNumber));
    }


    @PutMapping
    public ResponseEntity<TurnDTO> updateTurn(@RequestBody TurnPOJO turn) throws NotFoundException {
        return ResponseEntity.ok(turnService.update(turn));
    }
}
