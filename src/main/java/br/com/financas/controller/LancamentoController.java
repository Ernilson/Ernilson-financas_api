package br.com.financas.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.financas.Exeptions.RegraNegocioException;
import br.com.financas.model.Lancamento;
import br.com.financas.model.Usuario;
import br.com.financas.model.Enuns.StatusLancamento;
import br.com.financas.model.Enuns.TipoLancamento;
import br.com.financas.model.dto.AtualizaStatusDTO;
import br.com.financas.model.dto.LancamentoDTO;
import br.com.financas.services.UsuarioServices;
import br.com.financas.servicesImpl.LancamentoServicesImpl;
import br.com.financas.servicesImpl.UsuarioServiceImpl;

@RestController
@RequestMapping("/api/lancamento")
public class LancamentoController {

	@Autowired
	private LancamentoServicesImpl service;

	@Autowired
	private UsuarioServices usuarioService;

	@GetMapping
	public ResponseEntity buscar(@RequestParam(value = "descricao", required = false) String descricao,
			@RequestParam(value = "mes", required = false) Integer mes,
			@RequestParam(value = "ano", required = false) Integer ano, @RequestParam("usuario") Long idUsuario) {

		Lancamento lancamentoFiltro = new Lancamento();
		lancamentoFiltro.setDescricao(descricao);
		lancamentoFiltro.setMes(mes);
		lancamentoFiltro.setAno(ano);

		Optional<Usuario> usuario = usuarioService.obterPorId(idUsuario);
		if (!usuario.isPresent()) {
			return ResponseEntity.badRequest()
					.body("Não foi possível realizar a consulta.Usuario não encontrado para o id informado.");
		} else {
			lancamentoFiltro.setUsuario(usuario.get());
		}
		List<Lancamento> lancamentos = service.buscar(lancamentoFiltro);
		return ResponseEntity.ok(lancamentos);
	}
	
	@GetMapping("{id}")
	public ResponseEntity obterLancamento( @PathVariable("id") Long id ) {
		return service.obterPorId(id)
					.map( lancamento -> new ResponseEntity(converter2(lancamento), HttpStatus.OK) )
					.orElseGet( () -> new ResponseEntity(HttpStatus.NOT_FOUND) );
	}
	
	@PostMapping
	public ResponseEntity salvarLancamento(@RequestBody LancamentoDTO dto) {
		try {
			Lancamento entidade = converter(dto);
			entidade = service.salvar(entidade);
			service.salvar(entidade);
			return ResponseEntity.ok(entidade);
		
		} catch (RegraNegocioException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}

	}

	@PutMapping("{id}")
	public ResponseEntity atualizar( @PathVariable("id") Long id, @RequestBody LancamentoDTO dto) {
		
		return service.obterPorId(id).map(entity -> {
			try {
				Lancamento lancamento = converter(dto);
				lancamento.setId(entity.getId());
				service.atualizar(lancamento);
				return ResponseEntity.ok(lancamento);
			} catch (RegraNegocioException e) {
				return ResponseEntity.badRequest().body(e.getMessage());
			}

		}).orElseGet(() -> new ResponseEntity("Lancamento não encontrado na base de dados.", HttpStatus.BAD_REQUEST));

	}
	
	@PutMapping("{id}/atualiza-status")
	public ResponseEntity atualizarStatus( @PathVariable("id") Long id , @RequestBody AtualizaStatusDTO dto ) {
		return service.obterPorId(id).map( entity -> {
			StatusLancamento statusSelecionado = StatusLancamento.valueOf(dto.getStatus());
			
			if(statusSelecionado == null) {
				return ResponseEntity.badRequest().body("Não foi possível atualizar o status do lançamento, envie um status válido.");
			}
			
			try {
				entity.setStatus(statusSelecionado);
				service.atualizar(entity);
				return ResponseEntity.ok(entity);
			}catch (RegraNegocioException e) {
				return ResponseEntity.badRequest().body(e.getMessage());
			}
		
		}).orElseGet( () ->
		new ResponseEntity("Lancamento não encontrado na base de Dados.", HttpStatus.BAD_REQUEST) );
	}
	

	@DeleteMapping("{id}")
	public ResponseEntity delete(@PathVariable("id") Long id) {
		return service.obterPorId(id).map(entity ->{
			service.deletar(entity);
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}).orElseGet(() -> 
		new ResponseEntity("Lancamento não encontrado na base de dados.", HttpStatus.BAD_REQUEST));	
	}

	
	private Lancamento converter(LancamentoDTO dto) {
		Lancamento l = new Lancamento();
		l.setId(dto.getId());
		l.setDescricao(dto.getDescricao());
		l.setAno(dto.getAno());
		l.setMes(dto.getMes());
		l.setValor(dto.getValor());	

		Usuario user = usuarioService.obterPorId(dto.getId())
				.orElseThrow(() -> new RegraNegocioException("Usuario não encontrado para o id informado!"));
		
		l.setUsuario(user);		
		
		if (dto.getTipo() != null) {
			l.setTipo(TipoLancamento.valueOf(dto.getTipo()));
		}
		
		if (l.getStatus() != null) {
			l.setStatus(StatusLancamento.valueOf(dto.getStatus()));
		}
		
		return l;
	}
	
        private LancamentoDTO converter2(Lancamento dto2) {
		LancamentoDTO dto = new LancamentoDTO();
		dto2.setId(dto.getId());
		dto2.setDescricao(dto.getDescricao());
		dto2.setAno(dto.getAno());
		dto2.setMes(dto.getMes());
		dto2.setValor(dto.getValor());	

		Usuario user = usuarioService.obterPorId(dto.getUsuario())
				.orElseThrow(() -> new RegraNegocioException("Usuario não encontrado para o id informado!"));
		
		dto2.setUsuario(user);		
		
		if (dto.getTipo() != null) {
			dto2.setTipo(TipoLancamento.valueOf(dto.getTipo()));
		}                
		
		if (dto.getStatus() != null) {
			dto2.setStatus(StatusLancamento.valueOf(dto.getStatus()));
		}
		
		return dto;
	}
        
        /*
    	private LancamentoDTO converter(Lancamento lancamento) {
    		return LancamentoDTO.builder()
    					.id(lancamento.getId())
    					.descricao(lancamento.getDescricao())
    					.valor(lancamento.getValor())
    					.mes(lancamento.getMes())
    					.ano(lancamento.getAno())
    					.status(lancamento.getStatus().name())
    					.tipo(lancamento.getTipo().name())
    					.usuario(lancamento.getUsuario().getId())
    					.build();
    					
    	}
    	*/
        
       
        
       
}
