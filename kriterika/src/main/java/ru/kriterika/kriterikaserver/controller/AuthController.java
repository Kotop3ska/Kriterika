package ru.kriterika.kriterikaserver.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kriterika.kriterikaserver.dto.request.LoginRequest;
import ru.kriterika.kriterikaserver.dto.request.RegisterRequest;
import ru.kriterika.kriterikaserver.dto.response.UserDto;
import ru.kriterika.kriterikaserver.model.User;
import ru.kriterika.kriterikaserver.security.JwtTokenProvider;
import ru.kriterika.kriterikaserver.service.UserService;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	private final UserService userService;
	private final JwtTokenProvider tokenProvider;

	public AuthController(UserService userService, JwtTokenProvider tokenProvider) {
		this.userService = userService;
		this.tokenProvider = tokenProvider;
	}

	@PostMapping("/register")
	public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request) {
		User user = userService.register(request.getEmail(), request.getUsername(), request.getPassword());
		String token = tokenProvider.generateToken(user.getId(), user.getUsername());
		return ResponseEntity.ok(Map.of(
				"user", new UserDto(user.getId(), user.getEmail(), user.getUsername(), user.getCreatedAt()),
				"token", token
		));
	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
		return userService.login(request.getEmail(), request.getPassword())
				.map(user -> {
					String token = tokenProvider.generateToken(user.getId(), user.getUsername());
					return ResponseEntity.ok((Object) Map.of(
							"user", new UserDto(user.getId(), user.getEmail(), user.getUsername(), user.getCreatedAt()),
							"token", token
					));
				})
				.orElse(ResponseEntity.status(401).body(Map.of("error", "Неверный email или пароль")));
	}
}
