package com.maiia.pro.controller;

import com.maiia.pro.dto.PatientDto;
import com.maiia.pro.entity.Patient;
import com.maiia.pro.mapper.PatientMapper;
import com.maiia.pro.service.ProPatientService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/patients", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProPatientController {
    @Autowired
    private ProPatientService proPatientService;

    @Autowired
    private PatientMapper patientMapper;

    @ApiOperation(value = "Get patients")
    @GetMapping
    public List<PatientDto> getPatients() {
        return patientMapper.toDtos(proPatientService.findAll());
    }
}
