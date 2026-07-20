package ru.kriterika.kriterikaserver.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RegisterRequest {

	@NotBlank(message = "Email не может быть пустым")
	@Email(message = "Некорректный формат email")
	private String email;

	@NotBlank(message = "Username не может быть пустым")
	@Size(min = 3, max = 50, message = "Username должен быть от 3 до 50 символов")
	private String username;

	@NotBlank(message = "Пароль не может быть пустым")
	@Size(min = 6, message = "Пароль должен быть не менее 6 символов")
	private String password;

	public RegisterRequest() {
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
