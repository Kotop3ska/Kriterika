package ru.kriterika.kriterikaserver.security;

public class UserPrincipal {

	private final Integer userId;
	private final String username;

	public UserPrincipal(Integer userId, String username) {
		this.userId = userId;
		this.username = username;
	}

	public Integer getUserId() {
		return userId;
	}

	public String getUsername() {
		return username;
	}
}
