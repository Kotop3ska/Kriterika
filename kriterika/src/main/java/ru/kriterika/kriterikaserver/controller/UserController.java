package ru.kriterika.kriterikaserver.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.kriterika.kriterikaserver.dto.response.UserDto;
import ru.kriterika.kriterikaserver.security.UserPrincipal;
import ru.kriterika.kriterikaserver.service.UserService;

import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping("/me")
	public ResponseEntity<?> getMe() {
		Integer userId = getCurrentUserId();
		return userService.findById(userId)
				.map(user -> ResponseEntity.ok((Object) new UserDto(
						user.getId(), user.getEmail(), user.getUsername(), user.getCreatedAt())))
				.orElse(ResponseEntity.notFound().build());
	}

	@PutMapping("/me")
	public ResponseEntity<?> updateMe(@RequestBody Map<String, String> updates) {
		Integer userId = getCurrentUserId();
		try {
			userService.updateProfile(userId, updates.get("email"), updates.get("username"));
			return userService.findById(userId)
					.map(user -> ResponseEntity.ok((Object) new UserDto(
							user.getId(), user.getEmail(), user.getUsername(), user.getCreatedAt())))
					.orElse(ResponseEntity.notFound().build());
		} catch (RuntimeException e) {
			return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
		}
	}

	private Integer getCurrentUserId() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserPrincipal principal = (UserPrincipal) auth.getPrincipal();
		return principal.getUserId();
	}
}
