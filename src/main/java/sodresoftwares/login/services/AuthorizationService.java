package sodresoftwares.login.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import sodresoftwares.login.model.user.User;
import sodresoftwares.login.repositories.UserRepository;

@Service
public class AuthorizationService implements UserDetailsService{

	private UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	
	public AuthorizationService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		 UserDetails user = userRepository.findByLogin(username);
	        if (user == null) {
	            throw new UsernameNotFoundException("Usuário não encontrado");
	        }
	        return user;
	}
}
