package br.com.financas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.financas.model.Lancamento;
import br.com.financas.model.Usuario;

public interface LancamentoRepository extends JpaRepository<Lancamento, Long> {

}
