package br.edu.atitus.api_easybus.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.atitus.api_easybus.entities.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID>{
	
	boolean existsByEmail(String email);

	Optional<UserEntity> findByEmail(String email);
}
