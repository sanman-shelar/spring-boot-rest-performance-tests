package com.pt.service;

import com.pt.dto.PersonDto;
import com.pt.entity.Person;
import com.pt.mapper.PersonMapper;
import com.pt.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PersonService {

	private final PersonRepository personRepository;

	private final PersonMapper personMapper;

	public Page<PersonDto> findAll(Pageable pageable) {
		return personRepository.findAll(pageable).map(personMapper::toDto);
	}

	public PersonDto createPerson(PersonDto personDto) {
		Person person = personRepository.save(personMapper.toEntity(personDto));
		return personMapper.toDto(person);
	}

}
