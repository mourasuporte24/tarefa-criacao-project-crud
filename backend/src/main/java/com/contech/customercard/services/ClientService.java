package com.contech.customercard.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.contech.customercard.dto.ClientDTO;
import com.contech.customercard.entities.Client;
import com.contech.customercard.repositories.ClientRepository;
import com.contech.customercard.services.exceptions.EntityNotFoundException;

@Service
public class ClientService {

	@Autowired
	private ClientRepository repository;

	@Transactional(readOnly = true)
	public List<ClientDTO> findAll() {
		List<Client> listClient = repository.findAll();

		return listClient.stream().map(x -> new ClientDTO(x)).collect(Collectors.toList());

	}

	@Transactional(readOnly = true)
	public ClientDTO findById(Long id) {
		Optional<Client> ObjOptional = repository.findById(id);
		Client entity = ObjOptional.orElseThrow(() -> new EntityNotFoundException("Id não Encontrado."));
		return new ClientDTO(entity);

	}

	@Transactional
	public ClientDTO plus(ClientDTO dto) {

		Client entity = new Client();
		entity.setBirthDate(dto.getBirthDate());
		entity.setChildren(dto.getChildren());
		entity.setCpf(dto.getCpf());
		entity.setIncome(dto.getIncome());
		entity.setName(dto.getName());
		entity = repository.save(entity);
		return new ClientDTO(entity);

	}

}
