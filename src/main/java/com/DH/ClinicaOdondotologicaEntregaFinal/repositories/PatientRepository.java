package com.DH.ClinicaOdondotologicaEntregaFinal.repositories;
import com.DH.ClinicaOdondotologicaEntregaFinal.entities.Address;
import com.DH.ClinicaOdondotologicaEntregaFinal.entities.Patient;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

    @Query("SELECT p FROM Patient p WHERE p.dni = :dni")
    List<Patient> findPatientByDni2(@Param("dni") String dni);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("UPDATE Patient p SET p.name = :name, p.lastName = :lastName, p.dni = :dni, p.entryDate = :entryDate WHERE p.id = :id")
    void updatePatient(@Param("name") String name, @Param("lastName") String lastName, @Param("dni") String dni,@Param("entryDate") Date entryDate,@Param("id") Long id);

}
