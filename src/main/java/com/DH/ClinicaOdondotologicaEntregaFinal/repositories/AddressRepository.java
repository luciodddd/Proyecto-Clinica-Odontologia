package com.DH.ClinicaOdondotologicaEntregaFinal.repositories;

import com.DH.ClinicaOdondotologicaEntregaFinal.entities.Address;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("UPDATE Address a SET a.street = :street, a.number = :number, a.location = :location, a.province = :province WHERE a.id = :id")
    void updateAdress(@Param("street") String street, @Param("number") String number, @Param("location") String location, @Param("province") String province,@Param("id") Long id);
}
