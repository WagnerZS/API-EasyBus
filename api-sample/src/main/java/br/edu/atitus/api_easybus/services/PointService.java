package br.edu.atitus.api_easybus.services;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import br.edu.atitus.api_easybus.dtos.PointDTO;
import br.edu.atitus.api_easybus.entities.PointEntity;
import br.edu.atitus.api_easybus.entities.UserEntity;
import br.edu.atitus.api_easybus.repositories.PointRepository;

@Service
public class PointService {

	private final PointRepository repository;

	public PointService(PointRepository repository) {
		super();
		this.repository = repository;
	}
	
	public PointEntity save(PointEntity point) throws Exception {
		if (point == null)
			throw new Exception("Objeto Nulo");
		
		if (point.getDescription() == null || point.getDescription().isEmpty())
			throw new Exception("Descrição Inválida");
		point.setDescription(point.getDescription().trim());
		
		if (point.getLatitude() < -90 || point.getLatitude() > 90)
			throw new Exception("Latitude Inválida");
		
		if (point.getLongitude() < -180 || point.getLongitude() > 180)
			throw new Exception("Longitude Inválida");
		
		UserEntity userAuth = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		point.setUser(userAuth);
		
		return repository.save(point);
	}
	
	public void deleteById(UUID id) throws Exception {
		var point = repository.findById(id)
				.orElseThrow(() -> new Exception("Não existe ponto cadastrado com este ID"));
		
		UserEntity userAuth = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		if (!point.getUser().getId().equals(userAuth.getId()))
			throw new Exception("Você não tem permissão para apagar este registro");
		
		repository.deleteById(id);
	}
	
	public List<PointEntity> findAll() {
		return repository.findAll();
	}
	
	public List<PointEntity> findByUser() {
		UserEntity userAuth = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return repository.findByUser(userAuth);
	}
	
	public PointEntity update(UUID id, PointDTO dto) throws Exception {
		var point = repository.findById(id)
				.orElseThrow(() -> new Exception("Não existe ponto cadastrado com este ID"));
		
		UserEntity userAuth = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		if (!point.getUser().getId().equals(userAuth.getId()))
			throw new Exception("Você não tem permissão para apagar este registro");
		
		BeanUtils.copyProperties(dto, point);
		return repository.save(point);
	}
	
}