package com.forensys.backend.service.impl;

import com.forensys.backend.dto.PatientDto;
import com.forensys.backend.entity.Patient;
import com.forensys.backend.mapper.PatientMapper;
import com.forensys.backend.repository.PatientRepository;
import com.forensys.backend.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;
    private final PatientMapper patientMapper;

    @Override
    @Transactional
    public PatientDto createPatient(PatientDto patientDto) {
        Patient patient = patientMapper.toEntity(patientDto);
        patient = patientRepository.save(patient);
        return patientMapper.toDto(patient);
    }

    @Override
    @Transactional(readOnly = true)
    public PatientDto getPatientById(Long id) {
        return patientRepository.findById(id)
                .map(patientMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Patient not found"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<PatientDto> getAllPatients() {
        return patientRepository.findAll().stream()
                .map(patientMapper::toDto)
                .toList();
    }

    @Override
    @Transactional
    public PatientDto updatePatient(Long id, PatientDto patientDto) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Patient not found"));
        
        patient.setFullName(patientDto.getFullName());
        patient.setAddress(patientDto.getAddress());
        patient.setDateOfBirth(patientDto.getDateOfBirth());
        patient.setSex(patientDto.getSex());
        patient.setConsentGiven(patientDto.getConsentGiven());
        
        com.forensys.backend.entity.Identification identification = patient.getIdentification();
        if (identification == null) {
            identification = new com.forensys.backend.entity.Identification();
        }
        identification.setNicNo(patientDto.getNicNo());
        identification.setPassportNo(patientDto.getPassportNo());
        patient.setIdentification(identification);
        
        patient = patientRepository.save(patient);
        return patientMapper.toDto(patient);
    }

    @Override
    @Transactional
    public void deletePatient(Long id) {
        patientRepository.deleteById(id);
    }
}
