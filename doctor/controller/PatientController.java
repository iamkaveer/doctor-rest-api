package com.doctorAPI.doctor.controller;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.doctorAPI.doctor.Service.PatientService;
import com.doctorAPI.doctor.model.Doctor;
import com.doctorAPI.doctor.model.Patient;
import com.doctorAPI.doctor.repository.DoctorRepository;

import jakarta.annotation.Nullable;

import java.sql.Timestamp;

@RestController
public class PatientController {

    @Autowired
    DoctorRepository doctorRepository;

    @Autowired
    PatientService service;

    //set a new patient
    @PostMapping(value = "/patient")
    public String savePatient(@RequestBody String patientRequest) {

        JSONObject json = new JSONObject(patientRequest);
        Patient patient = setPatient(json);
        service.savePatient(patient);

        return "Saved patient";

    }

    //set patient
    private Patient setPatient(JSONObject json) {

        Patient patient = new Patient();
        patient.setPatientId(json.getInt("patientId"));
        patient.setPatientName(json.getString("patientName"));
        patient.setAge(json.getInt("age"));
        patient.setPhoneNumber(json.getString("phoneNumber"));
        patient.setDiseaseType(json.getString("diseaseType"));
        patient.setGender(json.getString("gender"));
        Timestamp currTime = new Timestamp(System.currentTimeMillis());
        patient.setAdmitDate(currTime);
        String doctorId = json.getString("doctorId");
        Doctor doctor = doctorRepository.findById(Integer.valueOf(doctorId)).get();
        patient.setDoctor(doctor);
        return patient;
    }


    //get patient
    @GetMapping(value = "/patient")
    public ResponseEntity getPatients(@Nullable @RequestParam String doctorId,
                                      @Nullable @RequestParam String patientId) {
        JSONArray patientDetails = service.getPatients();
        if (doctorId == null && patientId == null) {
            patientDetails = service.getPatients();
        }
        else if (doctorId == null) {
            patientDetails = service.getPatientById(patientId);
        }
        else if (patientId == null) {
            patientDetails = service.getPatientsByDoctorId(doctorId);
        } else {
            return new ResponseEntity<>("Invalid Request", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(patientDetails.toString(), HttpStatus.OK);


    }
}