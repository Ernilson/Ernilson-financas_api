package br.com.financas;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.financas.Exeptions.RegraNegocioException;
import br.com.financas.model.Lancamento;
import br.com.financas.model.Usuario;
import br.com.financas.repositories.UsuarioRepository;
import br.com.financas.servicesImpl.UsuarioServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FinancasApplicationTests {

    @Autowired
    private UsuarioRepository urp;

    @Autowired
    private UsuarioServiceImpl usi;

    //@Test(expected = RegraNegocioException.class)
    @Test
    public void contextLoads() {
        //Cenario

        Usuario user = new Usuario();
        user.setNome("validação2");
        user.setEmail("teste2@teste.com");
        user.setSenha("senha");
        user.setData("22/08/2020");
        //l.setUsuario(user);

        //usi.salvar(user);
        //usi.ValidarEmail("teste@teste.com");
        //ação / execução
        //boolean result = urp.existsByEmail("teste@teste.com");
        //verificacao
        //Assertions.assertThat(result).isTrue();
    }

}
