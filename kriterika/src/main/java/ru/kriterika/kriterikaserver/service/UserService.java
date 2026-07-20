package ru.kriterika.kriterikaserver.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kriterika.kriterikaserver.model.User;
import ru.kriterika.kriterikaserver.repository.UserRepository;

import java.util.Optional;

@Service
@Transactional
public class UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	public User register(String email, String username, String password) {
		if (userRepository.existsByEmail(email)) {
			throw new RuntimeException("Email уже используется");
		}
		if (userRepository.existsByUsername(username)) {
			throw new RuntimeException("Username уже занят");
		}
		User user = new User(email, username, passwordEncoder.encode(password));
		return userRepository.save(user);
	}

	public Optional<User> login(String email, String password) {
		return userRepository.findByEmail(email)
				.filter(u -> passwordEncoder.matches(password, u.getPasswordHash()));
	}

	public Optional<User> findById(Integer id) {
		return userRepository.findById(id);
	}

	public Optional<User> findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	public void updateProfile(Integer userId, String email, String username) {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new RuntimeException("Пользователь не найден"));

		if (email != null && !email.equals(user.getEmail())) {
			if (userRepository.existsByEmail(email)) {
				throw new RuntimeException("Email уже используется");
			}
			user.setEmail(email);
		}

		if (username != null && !username.equals(user.getUsername())) {
			if (userRepository.existsByUsername(username)) {
				throw new RuntimeException("Username уже занят");
			}
			user.setUsername(username);
		}

		userRepository.save(user);
	}
}
