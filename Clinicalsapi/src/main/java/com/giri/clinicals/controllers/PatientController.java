package com.giri.clinicals.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.giri.clinicals.exception.PatientNotFoundException;
import com.giri.clinicals.model.ClinicalData;
import com.giri.clinicals.model.Patient;
import com.giri.clinicals.repos.PatientRepository;

import jakarta.validation.Valid;
import jakarta.validation.metadata.MethodType;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class PatientController {
	
	private PatientRepository repository;
	
	Map<String, String> filters = new HashMap<>();
	
	@Autowired
	PatientController(PatientRepository repository){
		this.repository = repository;
	}
	
	@RequestMapping(value="/patients", method=RequestMethod.GET)
	public List<Patient> getPatients(){
		return repository.findAll();
		
	}
	
	@RequestMapping(value="/patients/{id}", method=RequestMethod.GET)
	public Patient getPatient(@PathVariable("id") int  patientId) {
		return repository.findById(patientId).orElseThrow(()->new PatientNotFoundException(patientId));
	}
	
	@RequestMapping(value="/patients", method=RequestMethod.POST)
	public Patient savePatient(@RequestBody @Valid Patient patient) {
		return repository.save(patient);
	}
	
	@RequestMapping(value="patients/analyse/{id}", method = RequestMethod.GET)
	public Patient analyse(@PathVariable("id") int id) {
		Patient patient = repository.findById(id).get();
		List<ClinicalData> clinicalData = patient.getClinicalData();
		List<ClinicalData> duplicateClinicalData = new ArrayList<>(clinicalData);
		for(ClinicalData each: duplicateClinicalData) {
			if(filters.containsKey(each.getComponentName())) {
				clinicalData.remove(each);
				continue;
			}else {
				filters.put(each.getComponentName(), null);
			}
			if(each.getComponentName().equals("hw")) {
				String[] heightAndWeight = each.getComponentValue().split("/");
				if(heightAndWeight != null && heightAndWeight.length > 1) {
					float heightInMetres = Float.parseFloat(heightAndWeight[0])*0.4536F;
					float bmi = Float.parseFloat(heightAndWeight[1])/(heightInMetres*heightInMetres);
					ClinicalData bmiData = new ClinicalData();
					bmiData.setComponentName("bmi");
					bmiData.setComponentValue(Float.toString(bmi));
					clinicalData.add(bmiData);	
				}			
			}
		}
		filters.clear();
		return patient;
		
	}

}
