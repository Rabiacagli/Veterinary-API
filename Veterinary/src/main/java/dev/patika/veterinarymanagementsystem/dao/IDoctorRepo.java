package dev.patika.veterinarymanagementsystem.dao;

import dev.patika.veterinarymanagementsystem.entities.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IDoctorRepo extends JpaRepository<Doctor, Long> {

    boolean existsByNameAndPhoneAndMailAndAddressAndCity
            (String name,
             String phone,
             String mail,
             String address,
             String city);
}
