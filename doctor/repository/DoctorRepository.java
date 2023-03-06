package com.doctorAPI.doctor.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.doctorAPI.doctor.model.Doctor;

public interface DoctorRepository extends JpaRepository<Doctor, Integer> {

}
