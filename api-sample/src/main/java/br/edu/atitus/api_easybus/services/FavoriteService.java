package br.edu.atitus.api_easybus.services;

import java.util.List;
import java.util.UUID;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import br.edu.atitus.api_easybus.entities.FavoriteEntity;
import br.edu.atitus.api_easybus.entities.PointEntity;
import br.edu.atitus.api_easybus.entities.UserEntity;
import br.edu.atitus.api_easybus.repositories.FavoriteRepository;
import br.edu.atitus.api_easybus.repositories.PointRepository;

@Service
public class FavoriteService {

	private final FavoriteRepository repository;
	private final PointRepository pointRepository;

	public FavoriteService(FavoriteRepository repository, PointRepository pointRepository) {
		this.repository = repository;
		this.pointRepository = pointRepository;
	}

	public FavoriteEntity save(UUID pointId) throws Exception {
		UserEntity userAuth = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		PointEntity point = pointRepository.findById(pointId)
				.orElseThrow(() -> new Exception("Ponto não encontrado"));

		if (repository.existsByUserAndPoint(userAuth, point)) {
			throw new Exception("Este ponto já está nos seus favoritos.");
		}

		FavoriteEntity favorite = new FavoriteEntity();
		favorite.setUser(userAuth);
		favorite.setPoint(point);

		return repository.save(favorite);
	}

	public List<FavoriteEntity> findByUser() {
		UserEntity userAuth = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return repository.findByUser(userAuth);
	}

	public void deleteByPoint(UUID pointId) throws Exception {
		UserEntity userAuth = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		PointEntity point = pointRepository.findById(pointId)
				.orElseThrow(() -> new Exception("Ponto não encontrado"));

		FavoriteEntity favorite = repository.findByUserAndPoint(userAuth, point)
				.orElseThrow(() -> new Exception("Este ponto não está nos seus favoritos."));

		repository.deleteById(favorite.getId());
	}
}
