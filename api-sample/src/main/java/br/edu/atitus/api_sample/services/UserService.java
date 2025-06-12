package br.edu.atitus.api_sample.services;

import java.util.regex.Pattern;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.edu.atitus.api_sample.entities.UserEntity;
import br.edu.atitus.api_sample.repositories.UserRepository;

@Service
public class UserService {
	
	private final UserRepository repository;
	private final PasswordEncoder passwordEncoder;
	
	public UserService(UserRepository repository, PasswordEncoder passwordEncoder) {
		super();
		this.repository = repository;
		this.passwordEncoder = passwordEncoder;
	}

	private final String MODELO_EMAIL = "^[^@\\s]+@([a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,}$";
	private final String MODELO_SENHA = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$";

	public UserEntity save(UserEntity user) throws Exception {
		if (user == null) {
			throw new Exception("Objeto não pode ser nulo.");
		}

		if (user.getName() == null || user.getName().isEmpty()) {
			throw new Exception("Nome inválido");
		}
		user.setName(user.getName().trim());

		if (user.getEmail() == null || user.getEmail().isEmpty() || !Pattern.matches(MODELO_EMAIL, user.getEmail())) {
			throw new Exception("Email inválido");
		}
		user.setEmail(user.getEmail().trim().toLowerCase());

		if (user.getPassword() == null || user.getPassword().isEmpty() || !Pattern.matches(MODELO_SENHA, user.getPassword())) {
			throw new Exception("Password inválido");
		}
		user.setPassword(user.getPassword());
		
		if (user.getType() == null) {
			throw new Exception("Tipo de usuário inválido");
		}
		
		if (repository.existsByEmail(user.getEmail())) {
			throw new Exception("Já existe usuário cadastrado com este e-mail");
		}
		
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		
		repository.save(user);
		
		return user;
	}

}
