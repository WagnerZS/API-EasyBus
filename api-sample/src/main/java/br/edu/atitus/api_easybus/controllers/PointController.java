package br.edu.atitus.api_easybus.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.atitus.api_easybus.dtos.PointDTO;
import br.edu.atitus.api_easybus.entities.PointEntity;
import br.edu.atitus.api_easybus.services.PointService;

@RestController
@RequestMapping("/ws/point")
public class PointController {

	private final PointService service;

	public PointController(PointService service) {
		super();
		this.service = service;
	}
	
	@GetMapping()
	public ResponseEntity<List<PointEntity>> findAll(){
		return ResponseEntity.ok(service.findAll());
	}
	
	@GetMapping("/byUser")
	public ResponseEntity<List<PointEntity>> findByUser(){
		return ResponseEntity.ok(service.findByUser());
	}

	@PostMapping()
	public ResponseEntity<PointEntity> save(@RequestBody PointDTO dto) throws Exception{
		PointEntity point = new PointEntity();
		BeanUtils.copyProperties(dto, point);
		
		service.save(point);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(point);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<String> delete(@PathVariable(required = true) UUID id) throws Exception{
		service.deleteById(id);
		return ResponseEntity.ok("Ponto removido com sucesso!");
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<PointEntity> update(@PathVariable(required = true) UUID id, @RequestBody PointDTO dto) throws Exception{
		PointEntity point = service.update(id, dto);
		return ResponseEntity.ok(point);
	}
	
	@ExceptionHandler(value = Exception.class)
	public ResponseEntity<String> handlerException(Exception ex){
		String message = ex.getMessage().replaceAll("\r\n", "");
		return ResponseEntity.badRequest().body(message);
	}
}
