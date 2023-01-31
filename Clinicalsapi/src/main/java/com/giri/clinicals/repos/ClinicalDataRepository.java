package com.giri.clinicals.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.giri.clinicals.model.ClinicalData;

public interface ClinicalDataRepository extends JpaRepository<ClinicalData, Integer> {

}
