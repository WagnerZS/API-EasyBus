package br.edu.atitus.api_easybus.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.edu.atitus.api_easybus.entities.FavoriteEntity;
import br.edu.atitus.api_easybus.services.FavoriteService;

@RestController
@RequestMapping("/ws/point/favorite")
public class FavoriteController {

	private final FavoriteService service;

	public FavoriteController(FavoriteService service) {
		this.service = service;
	}
	
	//Pede ao service todos os pontos favoritados do usuario
	@GetMapping
	public ResponseEntity<List<FavoriteEntity>> findByUser() {
		return ResponseEntity.ok(service.findByUser());
	}

	//Pede ao service para criar um novo favorito via ID do ponto
	@PostMapping("/{pointId}")
	public ResponseEntity<FavoriteEntity> save(@PathVariable UUID pointId) throws Exception {
		FavoriteEntity favorite = service.save(pointId);
		return ResponseEntity.status(HttpStatus.CREATED).body(favorite);
	}
	//Requisição delete via ID do ponto
	@DeleteMapping("/{pointId}")
	public ResponseEntity<String> delete(@PathVariable UUID pointId) throws Exception {
		service.deleteByPoint(pointId);
		return ResponseEntity.ok("Favorito removido com sucesso!");
	}

	@ExceptionHandler(value = Exception.class)
	public ResponseEntity<String> handlerException(Exception ex) {
		String message = ex.getMessage().replaceAll("\r\n", "");
		return ResponseEntity.badRequest().body(message);
	}
}
