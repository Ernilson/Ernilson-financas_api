package br.com.financas;

import java.math.BigDecimal;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.financas.model.Lancamento;
import br.com.financas.model.Enuns.StatusLancamento;
import br.com.financas.model.Enuns.TipoLancamento;
import br.com.financas.model.Usuario;
import br.com.financas.servicesImpl.LancamentoServicesImpl;
import br.com.financas.servicesImpl.UsuarioServiceImpl;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class testeFinacas {
		
	@Autowired
	private LancamentoServicesImpl service;
        
        @Autowired
        private UsuarioServiceImpl usi;
	
	 @Test
	public void salvarLancamento() {
	
		Lancamento l = new Lancamento();
		l.setDescricao("criação");
		l.setMes(7);
		l.setAno(2020);
		l.setStatus(StatusLancamento.PENDENTE);
		l.setTipo(TipoLancamento.RECEITA);
		l.setValor(new BigDecimal(2.00));
		
//		service.salvar(l);	
	}
	 
	 @Test
	 public void updateLancamento() {
		 Lancamento l = new Lancamento();
                 Long codigo = 1L;
                 Usuario use = new Usuario();
                 Optional<Usuario> user = usi.findByOne(codigo);
         
		 	l.setId(codigo);
			l.setDescricao("funcionou");
			l.setMes(11);
			l.setAno(2019);	
//			l.setUsuario(user);
			l.setStatus(StatusLancamento.PENDENTE);
			l.setTipo(TipoLancamento.RECEITA);
			l.setValor(new BigDecimal(4.00));
				
			service.atualizar(l);
			}
}
