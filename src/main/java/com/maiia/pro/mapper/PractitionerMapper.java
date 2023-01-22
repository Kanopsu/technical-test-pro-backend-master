package com.maiia.pro.mapper;

import com.maiia.pro.dto.PractitionerDto;
import com.maiia.pro.entity.Practitioner;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface PractitionerMapper {
    Practitioner toEntity(PractitionerDto practitionerDto);
    PractitionerDto toDto(Practitioner practitioner);
    List<PractitionerDto> toDtos(List<Practitioner> practitioners);
}
