package com.DH.ClinicaOdondotologicaEntregaFinal.repositories;
import com.DH.ClinicaOdondotologicaEntregaFinal.entities.Odontologist;
import com.DH.ClinicaOdondotologicaEntregaFinal.entities.Patient;
import com.DH.ClinicaOdondotologicaEntregaFinal.entities.Turn;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface TurnRepository extends JpaRepository<Turn, Long> {
    @Query("SELECT t FROM Turn t WHERE t.date BETWEEN :initialDate AND :finalDate")
    List<Turn> findTurnByDate(@Param("initialDate") Date initialDate,@Param("finalDate") Date finalDate);

    /*@Transactional
    @Modifying(clearAutomatically = true)
    @Query("UPDATE Turn t SET t.date = :date, t.pacient_id = :patient_id, t.odontologist_id = :odontologist_id WHERE t.id = :id")
    void updateTurn(@Param("date") Date date, @Param("patient_id") Long patient_id, @Param("odontologist_id") Long odontologist_id, @Param("id") Long id);*/
}
