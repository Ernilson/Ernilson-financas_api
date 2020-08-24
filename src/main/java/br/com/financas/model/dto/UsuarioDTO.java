package br.com.financas.model.dto;

import br.com.financas.model.Usuario;

public class UsuarioDTO {
	
	private String email;
	private String nome;
	private String senha;
	
	public static UsuarioDTO converter(Usuario u) {
		UsuarioDTO user = new UsuarioDTO();
		user.setEmail(user.getEmail());
		user.setNome(user.getNome());
		user.setSenha(user.getSenha());
		return user;
		
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	

}
