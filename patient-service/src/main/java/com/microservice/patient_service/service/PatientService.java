package com.microservice.patient_service.service;

import com.microservice.patient_service.dto.PatientRequestDTO;
import com.microservice.patient_service.dto.PatientResponseDTO;
import com.microservice.patient_service.entity.Patient;
import com.microservice.patient_service.exception.EmailAlreadyExistsException;
import com.microservice.patient_service.exception.PatientNotFoundException;
import com.microservice.patient_service.grpc.BillingServiceGrpcClient;
import com.microservice.patient_service.mapper.PatientMapper;
import com.microservice.patient_service.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class PatientService {
    private final PatientRepository patientRepository;
    private final BillingServiceGrpcClient billingServiceGrpcClient;

    public List<PatientResponseDTO> getPatients(){
        List<Patient> patients = patientRepository.findAll();
        List<PatientResponseDTO> patientResponseDTOS = patients.stream().map(PatientMapper::toDTO).toList();
        return patientResponseDTOS;
    }

    public PatientResponseDTO createPatient(PatientRequestDTO patientRequestDTO){
        if(patientRepository.existsByEmail(patientRequestDTO.getEmail())){
            throw new EmailAlreadyExistsException("A patient already exists"+patientRequestDTO.getEmail());
        }
        Patient patient = patientRepository.save(PatientMapper.toEntity(patientRequestDTO));
        //grpc
        billingServiceGrpcClient.createBillingAccount(patient.getId().toString(), patient.getName().toString(),patient.getEmail().toString());
        PatientResponseDTO responseDTO = PatientMapper.toDTO(patient);
        return responseDTO;
    }

    public PatientResponseDTO updatePatient(UUID id, PatientRequestDTO patientRequestDTO){
        Patient patient = patientRepository.findById(id).orElseThrow(()->new PatientNotFoundException("Patient not found with id"+ id));
        if(patientRepository.existsByEmailAndIdNot(patientRequestDTO.getEmail(), id)){
            throw new EmailAlreadyExistsException("A patient exists with this email"+patientRequestDTO.getEmail());
        }
        patient.setName(patientRequestDTO.getName());
        patient.setAddress(patientRequestDTO.getAddress());
        patient.setEmail(patientRequestDTO.getEmail());
        patient.setDateOfBirth(LocalDate.parse(patientRequestDTO.getDateOfBirth()));

        Patient updatedPatient = patientRepository.save(patient);

        return PatientMapper.toDTO(updatedPatient);
    }

    public void deletePatient(UUID id){
        Patient patient = patientRepository.findById(id).orElseThrow(()->new PatientNotFoundException("Patient not found with id"+ id));
        if(patient!=null) patientRepository.deleteById(id);
    }
}
