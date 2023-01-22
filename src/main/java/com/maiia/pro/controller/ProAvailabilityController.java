package com.maiia.pro.controller;

import com.maiia.pro.dto.AvailabilityDto;
import com.maiia.pro.mapper.AvailabilityMapper;
import com.maiia.pro.service.ProAvailabilityService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/availabilities", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProAvailabilityController {
    @Autowired
    private ProAvailabilityService proAvailabilityService;

    @Autowired
    private AvailabilityMapper availabilityMapper;

    @ApiOperation(value = "Get availabilities by practitionerId")
    @GetMapping
    public List<AvailabilityDto> getAvailabilities(@RequestParam final Integer practitionerId) {

        return availabilityMapper.toDtos(proAvailabilityService.findByPractitionerId(practitionerId));
    }
}
