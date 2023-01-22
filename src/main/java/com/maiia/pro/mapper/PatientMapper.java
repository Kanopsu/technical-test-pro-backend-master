package com.maiia.pro.mapper;

import com.maiia.pro.dto.PatientDto;
import com.maiia.pro.entity.Patient;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface PatientMapper {
    Patient toEntity(PatientDto patientDto);
    PatientDto toDto(Patient patient);
    List<PatientDto> toDtos(List<Patient> patient);
}
