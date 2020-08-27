package br.com.financas.controller;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.financas.Exeptions.ErroAuthentication;
import br.com.financas.Exeptions.RegraNegocioException;
import br.com.financas.model.Usuario;
import br.com.financas.model.dto.UsuarioDTO;
import br.com.financas.services.LancamentoService;
import br.com.financas.servicesImpl.LancamentoServicesImpl;
import br.com.financas.servicesImpl.UsuarioServiceImpl;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

	@Autowired
	private UsuarioServiceImpl service;

	@Autowired
	private LancamentoServicesImpl lancamentoService;
	
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
	
	@GetMapping("{id}/saldo")
	public ResponseEntity obterSaldo( @PathVariable("id") Long id ) {
		Optional<Usuario> usuario = service.obterPorId(id);
		
		if(!usuario.isPresent()) {
			return new ResponseEntity( HttpStatus.NOT_FOUND );
		}
		
		BigDecimal saldo = lancamentoService.obterSaldoPorUsuario(id);
		return ResponseEntity.ok(saldo);
	}


}
