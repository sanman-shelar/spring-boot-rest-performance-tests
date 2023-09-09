package com.pt.mapper;

import com.pt.dto.PersonDto;
import com.pt.entity.Person;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PersonMapper {

	Person toEntity(PersonDto personDto);

	PersonDto toDto(Person person);

}
