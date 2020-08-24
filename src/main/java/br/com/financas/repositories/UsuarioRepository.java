package br.com.financas.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.financas.model.Usuario;


public interface UsuarioRepository extends JpaRepository<Usuario, Long>{

boolean existsByEmail(String email);

Optional<Usuario> findByEmail(String email);

//Optional<Usuario> findByOne (Usuario usuario);


}
