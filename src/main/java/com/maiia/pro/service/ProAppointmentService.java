package com.maiia.pro.service;

import com.maiia.pro.entity.Appointment;
import com.maiia.pro.exception.AppointmentConflictException;
import com.maiia.pro.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class ProAppointmentService {
    @Autowired
    private AppointmentRepository appointmentRepository;

    public Appointment find(String appointmentId) {
        return appointmentRepository.findById(appointmentId).orElseThrow();
    }

    public List<Appointment> findAll() {
        return appointmentRepository.findAll();
    }

    public List<Appointment> findByPractitionerId(Integer practitionerId) {
        return appointmentRepository.findByPractitionerId(practitionerId);
    }

    public Appointment addAppointment(Appointment appointment)  {
        if(Objects.nonNull(appointmentRepository.findOneByPractitionerIdAndStartDateBetween(
                appointment.getPractitionerId(), appointment.getStartDate(), appointment.getEndDate()
        ))) {
            throw new AppointmentConflictException("Appointment already exist !");
        }

        return appointmentRepository.save(appointment);
    }
}
