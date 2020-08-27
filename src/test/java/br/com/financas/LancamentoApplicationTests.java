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
import br.com.financas.repositories.LancamentoRepository;
import br.com.financas.repositories.UsuarioRepository;
import br.com.financas.servicesImpl.LancamentoServicesImpl;
import br.com.financas.servicesImpl.UsuarioServiceImpl;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LancamentoApplicationTests {

    @Autowired
    private LancamentoServicesImpl service;

    @Autowired
    private UsuarioServiceImpl usi;
   
    @Test
    public void salvarLancamento() {
        Long codigo = 2L;
        Lancamento l = new Lancamento();
        Usuario use = new Usuario();
        use = usi.getOne(codigo);     
        l.setDescricao("criação");
        l.setMes(7);
        l.setAno(2020);
        l.setUsuario(use);
        l.setStatus(StatusLancamento.PENDENTE);
        l.setTipo(TipoLancamento.RECEITA);
        l.setValor(new BigDecimal(2.00));

//        service.salvar(l);
    }

    @Test
    public void updateLancamento() {
        Lancamento l = new Lancamento();
        Long codigo = 2L;
        Usuario use = new Usuario();
        use = usi.getOne(codigo);

        l.setId(1L);
        l.setDescricao("update");
        l.setMes(11);
        l.setAno(2019);
        l.setUsuario(use);
        l.setStatus(StatusLancamento.EFETIVADO);
        l.setTipo(TipoLancamento.RECEITA);
        l.setValor(new BigDecimal(140.00));

        service.atualizar(l);
    }
}
