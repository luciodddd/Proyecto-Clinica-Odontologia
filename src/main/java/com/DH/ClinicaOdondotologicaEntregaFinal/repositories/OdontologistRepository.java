package com.DH.ClinicaOdondotologicaEntregaFinal.repositories;
import com.DH.ClinicaOdondotologicaEntregaFinal.entities.Odontologist;
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
public interface OdontologistRepository extends JpaRepository<Odontologist, Long> {
    @Query("SELECT o FROM Odontologist o WHERE o.registrationNumber = :registrationNumber")
    List<Odontologist> findOdontologistByRegistrationNumber(@Param("registrationNumber") String registrationNumber);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("UPDATE Odontologist o SET o.name = :name,o.lastName = :lastName ,o.registrationNumber = :registrationNumber WHERE o.id = :id")
    void updateOdontologist(@Param("name") String name, @Param("lastName") String lastName, @Param("registrationNumber") String registrationNumber, @Param("id") Long id);
}
