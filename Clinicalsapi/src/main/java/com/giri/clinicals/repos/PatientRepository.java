package com.giri.clinicals.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.giri.clinicals.model.Patient;

public interface PatientRepository extends JpaRepository<Patient, Integer> {

}
