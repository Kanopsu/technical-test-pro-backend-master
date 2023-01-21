package com.maiia.pro.service;

import com.maiia.pro.entity.Appointment;
import com.maiia.pro.repository.AppointmentRepository;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ProAppointmentServiceTest {
    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    private ProAppointmentService appointmentService;
    private Appointment appointment;

    @Test
    public void testAddAppointment() {
        appointment = new Appointment();
        appointment.setId(1);

        Appointment result = appointmentService.addAppointment(appointment);
        assertEquals(appointment, result);
    }
}
