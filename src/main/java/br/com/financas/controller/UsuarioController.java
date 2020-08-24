package br.com.financas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.financas.Exeptions.ErroAuthentication;
import br.com.financas.Exeptions.RegraNegocioException;
import br.com.financas.model.Usuario;
import br.com.financas.model.dto.UsuarioDTO;
import br.com.financas.servicesImpl.UsuarioServiceImpl;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

	@Autowired
	private UsuarioServiceImpl service;

	@PostMapping("/autenticar")
	public ResponseEntity autenticar(@RequestBody UsuarioDTO dto) {
		try {
			service.autenticar(dto.getEmail(), dto.getSenha());
			return ResponseEntity.ok("Usuario Autenticad");

		} catch (ErroAuthentication e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}

	}

	@PostMapping
	public ResponseEntity salvar(@RequestBody UsuarioDTO dto) {
		Usuario u = new Usuario();

		u.setEmail(dto.getEmail());
		u.setNome(dto.getNome());
		u.setSenha(dto.getSenha());

		try {
			Usuario user = service.salvar(u);
			return new ResponseEntity(user, HttpStatus.CREATED);
		} catch (RegraNegocioException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}

	}

}
