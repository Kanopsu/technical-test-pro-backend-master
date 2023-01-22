package com.maiia.pro.service;

import com.maiia.pro.entity.Appointment;
import com.maiia.pro.entity.Availability;
import com.maiia.pro.entity.TimeSlot;
import com.maiia.pro.repository.AppointmentRepository;
import com.maiia.pro.repository.AvailabilityRepository;
import com.maiia.pro.repository.TimeSlotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

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
                    availabilities.add(processAvailability(startDate, practitionerId));
                    startDate = startDate.plusMinutes(SLOT);
                } else {
                    if (appointment.getEndDate().equals(startDate)) {
                        availabilities.add(processAvailability(startDate, practitionerId));
                        startDate = startDate.plusMinutes(SLOT);
                    } else if (appointment.getStartDate().equals(endCheckDate)) {
                        availabilities.add(processAvailability(startDate, practitionerId));
                        startDate = startDate.plusMinutes(SLOT);
                    } else {
                        startDate = appointment.getEndDate();
                    }
                }
            }
            avoidDuplicatedAvailabilities(availabilities, timeSlot.getPractitionerId(),
                    timeSlot.getStartDate(), timeSlot.getEndDate());
        }
        availabilityRepository.saveAll(availabilities);
        return availabilities;
    }

    private void avoidDuplicatedAvailabilities(List<Availability> availabilities, Integer practitionerId,
                                               LocalDateTime startDate, LocalDateTime endDate) {
        List<Availability> currentAvailabilities = availabilityRepository.findByPractitionerIdAndStartDateBetween(
                practitionerId, startDate, endDate
        );
        if(!CollectionUtils.isEmpty(currentAvailabilities)) {
            availabilities.removeIf(a -> currentAvailabilities.stream().anyMatch(a2 -> !a2.getEndDate()
                    .isBefore(a.getStartDate()) && !a2.getStartDate().isAfter(a.getEndDate()) || a2.getStartDate().equals(a.getEndDate())));
        }
    }

    private Availability processAvailability(LocalDateTime startDate, Integer practitionerId) {
        LocalDateTime endDate = startDate.plusMinutes(SLOT);
        return Availability.builder().practitionerId(practitionerId).startDate(startDate).endDate(endDate).build();
    }
}