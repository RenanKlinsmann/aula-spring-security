package com.example.demo.dtos;

public class CredenciaisDTO {
	
	private String login;
	private String senha;
	
	public CredenciaisDTO() {}

	public CredenciaisDTO(String login, String senha) {
		super();
		this.login = login;
		this.senha = senha;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	

}
