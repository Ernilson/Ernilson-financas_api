package br.com.financas.services;

import java.util.Optional;

import org.springframework.http.ResponseEntity;

import br.com.financas.model.Usuario;

public interface UsuarioServices {
	
	Usuario autenticar(String email, String senha);
	
	Usuario salvar(Usuario usuario);
	
	void ValidarEmail(String email);
	
	Optional<Usuario> obterPorId(Long id);
	
	Optional<Usuario>findByOne(Usuario usuario);

}
