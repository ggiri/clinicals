package com.giri.clinicals.exception;

public class PatientNotFoundException extends RuntimeException {
	
	public PatientNotFoundException(int id) {
		super(String.format("Patient with ID %d not found", id));
	}

}
