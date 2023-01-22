package com.maiia.pro.service;

import com.maiia.pro.entity.Appointment;
import com.maiia.pro.exception.AppointmentConflictException;
import com.maiia.pro.repository.AppointmentRepository;
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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class ProAppointmentServiceTest {
    @Mock
    private AppointmentRepository appointmentRepository;

    @InjectMocks
    private ProAppointmentService appointmentService;

    private Appointment appointment;

    @BeforeEach
    public void setUp() {
        LocalDateTime startDate = LocalDateTime.of(2020, Month.FEBRUARY, 5, 11, 0, 0);
        appointment = Appointment.builder().practitionerId(1).patientId(1).startDate(startDate)
                .endDate(startDate.plusMinutes(15)).build();
    }

    @Test
    public void testAddAppointment() {

        when(appointmentRepository.save(appointment)).thenReturn(appointment);

        Appointment result = appointmentService.addAppointment(appointment);

        assertEquals(appointment, result);
    }

    @Test
    public void testAddAppointment_timeConflict_throwsException() {
        Appointment conflictingAppointment = Appointment.builder().practitionerId(1).patientId(2)
                .startDate(appointment.getStartDate())
                .endDate(appointment.getEndDate())
                .build();
        when(appointmentRepository.findOneByPractitionerIdAndStartDateBetween( appointment.getPractitionerId(), appointment.getStartDate(), appointment.getEndDate())).thenReturn(conflictingAppointment);

        assertThrows(AppointmentConflictException.class, () -> {
            appointmentService.addAppointment(appointment);
        });
    }
}
