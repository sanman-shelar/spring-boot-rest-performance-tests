package com.pt.controller;

import com.pt.dto.PersonDto;
import com.pt.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/person")
@RestController
@RequiredArgsConstructor
public class PersonController {

	private final PersonService personService;

	@GetMapping
	public Page<PersonDto> findAll(@PageableDefault Pageable pageable) {
		return personService.findAll(pageable);
	}

	@PostMapping
	public PersonDto createPerson(@RequestBody PersonDto personDto) {
		return personService.createPerson(personDto);
	}

}
