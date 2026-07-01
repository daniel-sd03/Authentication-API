package sodresoftwares.login.services;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import org.springframework.web.server.ResponseStatusException;
import sodresoftwares.login.infra.security.TokenService;
import sodresoftwares.login.dto.AuthenticationDTO;
import sodresoftwares.login.dto.RegisterDTO;
import sodresoftwares.login.model.user.User;
import sodresoftwares.login.repositories.UserRepository;

import java.util.Objects;

@Service
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final TokenService tokenService;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationService(AuthenticationManager authenticationManager,
                                 UserRepository userRepository,
                                 TokenService tokenService,
                                 PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.tokenService = tokenService;
        this.passwordEncoder = passwordEncoder;
    }

    public String login(AuthenticationDTO data) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        return tokenService.generateToken((User) Objects.requireNonNull(auth.getPrincipal()));
    }

    public void register(RegisterDTO data) {
        if (this.userRepository.existsByLogin(data.login())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User already exists");
        }

        String encryptedPassword = passwordEncoder.encode(data.password());

        User newUser = User.builder()
                .login(data.login())
                .password(encryptedPassword)
                .role(data.role())
                .build();

        this.userRepository.save(newUser);
    }
}