package br.com.financas;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.financas.model.Lancamento;
import br.com.financas.model.Usuario;
import br.com.financas.model.Enuns.StatusLancamento;
import br.com.financas.model.Enuns.TipoLancamento;
import br.com.financas.repositories.LancamentoRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@ActiveProfiles("test")
public class LancamentoRepositoryTeste {

	@Autowired
	LancamentoRepository repository;
	
	@Autowired
	TestEntityManager entityManager;	
		
	@Test
	public void salvar() {
		Long codigo = 2L;
        Lancamento l = new Lancamento();
        Usuario use = new Usuario();
        
              // Lancamento d = Lancamento.;
        l.setDescricao("teste");
        l.setMes(7);
        l.setAno(2020);
        l.setStatus(StatusLancamento.PENDENTE);
        l.setTipo(TipoLancamento.RECEITA);
        l.setValor(new BigDecimal(2.00));
        l.setDataCadastro(LocalDate.now());

		l = repository.save(l);
		
		Assertions.assertThat(l.getId()).isNotNull();
	}
}
