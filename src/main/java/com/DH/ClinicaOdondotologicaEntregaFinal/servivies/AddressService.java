package com.DH.ClinicaOdondotologicaEntregaFinal.servivies;

import com.DH.ClinicaOdondotologicaEntregaFinal.dto.AddressDTO;
import com.DH.ClinicaOdondotologicaEntregaFinal.entities.Address;
import com.DH.ClinicaOdondotologicaEntregaFinal.repositories.AddressRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.logging.Logger;

@Service
public class AddressService {
    @Autowired
    ObjectMapper mapper;
    @Autowired
    private AddressRepository addressRepository;
    @PersistenceContext
    public EntityManager entityManager;

    public static Logger logger = Logger.getLogger(String.valueOf(AddressService.class));

    public AddressDTO save(Address address){
        AddressDTO addressDTO = mapper.convertValue(addressRepository.save(address),AddressDTO.class);
        logger.info("Creando "+address);
        return addressDTO;
    }

    public void delete(Long id){
        addressRepository.deleteById(id);
    }

    public AddressDTO searchAddress(Long id){
        Address address = addressRepository
                .findById(id)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        AddressDTO addressDTO = mapper.convertValue(address,AddressDTO.class);
        return addressDTO;
    }

    public AddressDTO update(Address address){
        Address address1 = (Address)entityManager.createQuery(
                "update ADDRESS a set a.STREET= ?1," +
                        " a.NUMBER = ?2 , a.LOCATION = ?3," +
                        " a.PROVINCE = ?4 where a.ID=?5;")
                .setParameter(1,address.getStreet())
                .setParameter(2,address.getNumber())
                .setParameter(3,address.getLocation())
                .setParameter(4,address.getProvince())
                .setParameter(5,address.getId())
                .getSingleResult();
        logger.info("Modificando "+address);
        AddressDTO addressDTO = mapper.convertValue(address,AddressDTO.class);
        return addressDTO;
    }
}
