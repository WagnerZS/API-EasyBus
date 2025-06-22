package br.edu.atitus.api_easybus.controllers;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.atitus.api_easybus.components.JWTUtils;
import br.edu.atitus.api_easybus.dtos.SigninDTO;
import br.edu.atitus.api_easybus.dtos.SignupDTO;
import br.edu.atitus.api_easybus.entities.UserEntity;
import br.edu.atitus.api_easybus.entities.UserType;
import br.edu.atitus.api_easybus.services.UserService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final PasswordEncoder getPasswordEncoder;
	private final UserService service;
	private final AuthenticationConfiguration authConfig;
	
	public AuthController(UserService service, AuthenticationConfiguration authConfig, PasswordEncoder getPasswordEncoder) {
		super();
		this.service = service;
		this.authConfig = authConfig;
		this.getPasswordEncoder = getPasswordEncoder;
	}
	
	@PostMapping("/signin")
	public ResponseEntity<String> signin(@RequestBody SigninDTO signin){
		try {
			authConfig.getAuthenticationManager()
				.authenticate(new UsernamePasswordAuthenticationToken(signin.email(), signin.password()));
		} catch (AuthenticationException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		return ResponseEntity.ok(JWTUtils.generateToken(signin.email()));
	}

	@PostMapping("/signup")
	public ResponseEntity<UserEntity> signup(@RequestBody SignupDTO dto) throws Exception{
		UserEntity user = new UserEntity();
		BeanUtils.copyProperties(dto, user);
		user.setType(UserType.Commom);
		
		service.save(user);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(user);
	}
	
	@ExceptionHandler(value = Exception.class)
	public ResponseEntity<String> handlerException(Exception ex){
		String message = ex.getMessage().replaceAll("\r\n", "");
		return ResponseEntity.badRequest().body(message);
	}
}
