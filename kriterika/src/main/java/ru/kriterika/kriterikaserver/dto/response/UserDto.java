package ru.kriterika.kriterikaserver.dto.response;

import java.time.LocalDateTime;

public class UserDto {

	private Integer id;
	private String email;
	private String username;
	private LocalDateTime createdAt;

	public UserDto() {
	}

	public UserDto(Integer id, String email, String username, LocalDateTime createdAt) {
		this.id = id;
		this.email = email;
		this.username = username;
		this.createdAt = createdAt;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
}
