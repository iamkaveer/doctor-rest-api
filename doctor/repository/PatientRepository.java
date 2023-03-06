package com.doctorAPI.doctor.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.doctorAPI.doctor.model.Doctor;
import com.doctorAPI.doctor.model.Patient;

public interface PatientRepository extends JpaRepository<Patient, Integer> {

	List<Patient> findByDoctor(Doctor doctor);

}
