package com.maiia.pro.mapper;

import com.maiia.pro.dto.AvailabilityDto;
import com.maiia.pro.entity.Availability;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface AvailabilityMapper {
    Availability toEntity(AvailabilityDto availabilityDto);
    AvailabilityDto toDto(Availability availability);

    List<AvailabilityDto> toDtos(List<Availability> availabilities);
}
