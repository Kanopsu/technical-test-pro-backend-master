package com.maiia.pro.controller;

import com.maiia.pro.dto.AppointmentDto;
import com.maiia.pro.entity.Appointment;
import com.maiia.pro.mapper.AppointmentMapper;
import com.maiia.pro.service.ProAppointmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class ProAppointmentControllerTest {

    @InjectMocks
    private ProAppointmentController appointmentController;

    @Mock
    private ProAppointmentService proAppointmentService;

    @Mock
    private AppointmentMapper appointmentMapper;

    private Appointment appointmentEntity;
    private AppointmentDto appointmentDto;

    @BeforeEach
    public void setUp() {
        LocalDateTime startDate = LocalDateTime.of(2020, Month.FEBRUARY, 5, 11, 0, 0);
        appointmentEntity = Appointment.builder().practitionerId(1).patientId(1).startDate(startDate)
                .endDate(startDate.plusMinutes(15)).build();
        appointmentDto = AppointmentDto.builder().practitionerId(1).patientId(1).startDate(startDate)
                .endDate(startDate.plusMinutes(15)).build();
    }

    @Test
    public void testAddAppointment() {

        when(appointmentMapper.toEntity(appointmentDto)).thenReturn(appointmentEntity);
        when(proAppointmentService.addAppointment(appointmentEntity)).thenReturn(appointmentEntity);
        when(appointmentMapper.toDto(appointmentEntity)).thenReturn(appointmentDto);

        AppointmentDto response = appointmentController.addAppointment(appointmentDto);

        assertEquals(appointmentDto, response);

        verify(appointmentMapper, times(1)).toEntity(any());
        verify(appointmentMapper, times(1)).toDto(any());
    }
}
