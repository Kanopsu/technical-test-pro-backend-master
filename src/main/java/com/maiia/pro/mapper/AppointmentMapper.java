package com.maiia.pro.mapper;

import com.maiia.pro.dto.AppointmentDto;
import com.maiia.pro.entity.Appointment;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface AppointmentMapper {
    Appointment toEntity(AppointmentDto appointment);
    AppointmentDto toDto(Appointment appointment);
    List<AppointmentDto> toDtos(List<Appointment> appointments);
}
