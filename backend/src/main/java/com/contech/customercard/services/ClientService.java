package com.contech.customercard.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.contech.customercard.dto.ClientDTO;
import com.contech.customercard.entities.Client;
import com.contech.customercard.repositories.ClientRepository;
import com.contech.customercard.services.exceptions.ResourceNotFoundException;

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
		Client entity = ObjOptional.orElseThrow(() -> new ResourceNotFoundException("Id não Encontrado."));
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

	@Transactional
	public ClientDTO update(Long id, ClientDTO dto) {
		try {
			Client client = repository.getReferenceById(id);
			client.setBirthDate(dto.getBirthDate());
			client.setName(dto.getName());
			client.setCpf(dto.getCpf());
			client.setIncome(dto.getIncome());
			client.setChildren(dto.getChildren());
			client = repository.save(client);
			return new ClientDTO(client);
		} 
		catch (EntityNotFoundException e) {
			
			throw new ResourceNotFoundException("Id Not Found " + id);
			

		}

	}

}
