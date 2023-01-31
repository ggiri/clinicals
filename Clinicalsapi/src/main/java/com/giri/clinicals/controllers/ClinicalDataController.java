package com.giri.clinicals.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.giri.clinicals.dto.ClinicalDataRequest;
import com.giri.clinicals.model.ClinicalData;
import com.giri.clinicals.model.Patient;
import com.giri.clinicals.repos.ClinicalDataRepository;
import com.giri.clinicals.repos.PatientRepository;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class ClinicalDataController {
	
	private ClinicalDataRepository clinicalRepository;
	private PatientRepository patientRepository;
	
	@Autowired
	ClinicalDataController(ClinicalDataRepository clinicalRepository, PatientRepository patientRepository){
		this.clinicalRepository = clinicalRepository;
		this.patientRepository = patientRepository;
	}
	
	@RequestMapping(value="/clinicals", method = RequestMethod.POST)
	public ClinicalData saveClinicalData(@RequestBody ClinicalDataRequest request) {
		Patient patient = patientRepository.findById(request.getPatientId()).get();
		ClinicalData clinicalData = new ClinicalData();
		clinicalData.setComponentName(request.getComponentName());
		clinicalData.setComponentValue(request.getComponentValue());
		clinicalData.setPatient(patient);
		return clinicalRepository.save(clinicalData);	
	}

}
