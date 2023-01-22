package com.maiia.pro.controller;

import com.maiia.pro.dto.AppointmentDto;
import com.maiia.pro.entity.Appointment;
import com.maiia.pro.exception.AppointmentConflictException;
import com.maiia.pro.mapper.AppointmentMapper;
import com.maiia.pro.service.ProAppointmentService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/appointments", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProAppointmentController {
    @Autowired
    private ProAppointmentService proAppointmentService;

    @Autowired
    private AppointmentMapper appointmentMapper;

    @ApiOperation(value = "Get appointments by practitionerId")
    @GetMapping("/{practitionerId}")
    public List<AppointmentDto> getAppointmentsByPractitioner(@PathVariable final Integer practitionerId) {
        return appointmentMapper.toDtos(proAppointmentService.findByPractitionerId(practitionerId));
    }

    @ApiOperation(value = "Get all appointments")
    @GetMapping
    public List<AppointmentDto> getAppointments() {
        return appointmentMapper.toDtos(proAppointmentService.findAll());
    }

    @ApiOperation(value = "Add appointment")
    @PostMapping
    public AppointmentDto addAppointment(@RequestBody AppointmentDto appointment) {
        return appointmentMapper.toDto(proAppointmentService.addAppointment(appointmentMapper.toEntity(appointment)));
    }
}
