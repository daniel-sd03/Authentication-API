package sodresoftwares.login.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import sodresoftwares.login.infra.security.TokenService;
import sodresoftwares.login.model.user.AuthenticationDTO;
import sodresoftwares.login.model.user.LoginResponseDTO;
import sodresoftwares.login.model.user.RegisterDTO;
import sodresoftwares.login.model.user.User;
import sodresoftwares.login.repositories.UserRepository;


@RestController
@RequestMapping("auth")	
public class AuthenticationController {
	
	private TokenService tokenService;
	
	private AuthenticationManager authenticationManager;
	private UserRepository userRepository;
	public AuthenticationController(AuthenticationManager authenticationManager, UserRepository userRepository, 
			TokenService tokenService) {
		this.authenticationManager = authenticationManager;
		this.userRepository = userRepository;
		this.tokenService = tokenService;
	}
	
	@PostMapping("/login")
	public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid AuthenticationDTO data) {
		var usernamePassword = new  UsernamePasswordAuthenticationToken(data.login(), data.password());
		var auth = this.authenticationManager.authenticate(usernamePassword);
		
		var token = tokenService.generateToken((User) auth.getPrincipal() );
		
		return ResponseEntity.ok(new LoginResponseDTO(token));
	}
	
	@PostMapping("/register")
	public ResponseEntity<Void> register(@RequestBody @Valid RegisterDTO data ) {
		 if(this.userRepository.findByLogin(data.login()) != null) 
			 return ResponseEntity.badRequest().build();
		 
		 String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
		 User newUser = new User(data.login(), encryptedPassword, data.role());
		
		 this.userRepository.save(newUser);
		 return ResponseEntity.ok().build();
	}
}
