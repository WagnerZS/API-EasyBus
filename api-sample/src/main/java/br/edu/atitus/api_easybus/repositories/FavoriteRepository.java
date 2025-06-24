package br.edu.atitus.api_easybus.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.atitus.api_easybus.entities.FavoriteEntity;
import br.edu.atitus.api_easybus.entities.PointEntity;
import br.edu.atitus.api_easybus.entities.UserEntity;

public interface FavoriteRepository extends JpaRepository<FavoriteEntity, UUID> {
	List<FavoriteEntity> findByUser(UserEntity user);
	Optional<FavoriteEntity> findByUserAndPoint(UserEntity user, PointEntity point);
	boolean existsByUserAndPoint(UserEntity user, PointEntity point);
}
