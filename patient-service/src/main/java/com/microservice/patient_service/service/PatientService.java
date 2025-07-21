package com.microservice.patient_service.service;

import com.microservice.patient_service.dto.PatientRequestDTO;
import com.microservice.patient_service.dto.PatientResponseDTO;
import com.microservice.patient_service.entity.Patient;
import com.microservice.patient_service.mapper.PatientMapper;
import com.microservice.patient_service.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PatientService {
    private final PatientRepository patientRepository;

    public List<PatientResponseDTO> getPatients(){
        List<Patient> patients = patientRepository.findAll();
        List<PatientResponseDTO> patientResponseDTOS = patients.stream().map(PatientMapper::toDTO).toList();
        return patientResponseDTOS;
    }

    public PatientResponseDTO createPatient(PatientRequestDTO patientRequestDTO){
        Patient patient = patientRepository.save(PatientMapper.toEntity(patientRequestDTO));

        PatientResponseDTO responseDTO = PatientMapper.toDTO(patient);
        return responseDTO;
    }

}
