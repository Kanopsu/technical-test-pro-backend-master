package com.maiia.pro.service;

import com.maiia.pro.entity.Appointment;
import com.maiia.pro.entity.Availability;
import com.maiia.pro.entity.TimeSlot;
import com.maiia.pro.repository.AppointmentRepository;
import com.maiia.pro.repository.AvailabilityRepository;
import com.maiia.pro.repository.TimeSlotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class ProAvailabilityService {

    public static final Integer SLOT = 15;

    @Autowired
    private AvailabilityRepository availabilityRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private TimeSlotRepository timeSlotRepository;


    public List<Availability> findByPractitionerId(Integer practitionerId) {
        return availabilityRepository.findByPractitionerId(practitionerId);
    }

    public List<Availability> generateAvailabilities(Integer practitionerId) {
        List<TimeSlot> timeSlots = timeSlotRepository.findByPractitionerId(practitionerId);
        List<Availability> availabilities = new ArrayList<>();

        for (TimeSlot timeSlot : timeSlots) {
            LocalDateTime startDate = timeSlot.getStartDate();
            LocalDateTime endDate = timeSlot.getEndDate();
            while (startDate.isBefore(endDate)) {
                LocalDateTime endCheckDate = startDate.plusMinutes(SLOT);
                Appointment appointment = appointmentRepository.findOneByPractitionerIdAndStartDateBetween(practitionerId, startDate, endCheckDate);
                if (Objects.isNull(appointment)) {
                    availabilities.add(Availability.builder().practitionerId(practitionerId).startDate(startDate).build());
                    startDate = startDate.plusMinutes(SLOT);
                } else {
                    if (appointment.getEndDate().equals(startDate)) {
                        startDate = processAvailability(availabilities, startDate, practitionerId);
                    } else if (appointment.getStartDate().equals(endCheckDate)) {
                        startDate = processAvailability(availabilities, startDate, practitionerId);
                    } else {
                        startDate = appointment.getEndDate();
                    }
                }
            }
        }
        return availabilities;
    }

    public LocalDateTime processAvailability(List<Availability> availabilities, LocalDateTime startDate, Integer practitionerId) {
        availabilities.add(Availability.builder().practitionerId(practitionerId).startDate(startDate).endDate(startDate.plusMinutes(SLOT)).build());
        return startDate.plusMinutes(SLOT);
    }
}