package com.DH.ClinicaOdondotologicaEntregaFinal;

import com.DH.ClinicaOdondotologicaEntregaFinal.entities.Address;
import com.DH.ClinicaOdondotologicaEntregaFinal.entities.Patient;
import com.DH.ClinicaOdondotologicaEntregaFinal.servivies.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ClinicaOdondotologicaEntregaFinalApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClinicaOdondotologicaEntregaFinalApplication.class, args);
	}
}
