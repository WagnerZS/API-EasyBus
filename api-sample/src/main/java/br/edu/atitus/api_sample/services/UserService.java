package br.edu.atitus.api_sample.services;

import br.edu.atitus.api_sample.entities.UserEntity;

public class UserService {

	public UserEntity save(UserEntity user) throws Exception {
		if (user == null) {
			throw new Exception("Objeto não pode ser nulo.");
		}

		if (user.getName() == null || user.getName().isEmpty()) {
			throw new Exception("Nome inválido");
		}
		user.setName(user.getName().trim());

		if (user.getEmail() == null || user.getEmail().isEmpty()) {
			throw new Exception("Email inválido");
		}
		user.setEmail(user.getEmail().trim().toLowerCase());
		//TODO validar a sintaxe do email (texto@texto.texto) => REGEX
		

		if (user.getPassword() == null || user.getPassword().isEmpty() || user.getPassword().length() < 8) {
			throw new Exception("Password inválido");
		}
		user.setPassword(user.getPassword());
		//TODO Validar a força da senha (caracteres maiusculos, minusculos e numerais) => REGEX
		
		if (user.getType() == null) {
			throw new Exception("Tipo de usuário inválido");
		}

		// TODO invocar metodo save da camada repository

		return user;
	}

}
