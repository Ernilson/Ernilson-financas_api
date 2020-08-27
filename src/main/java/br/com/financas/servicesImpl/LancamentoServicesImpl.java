package br.com.financas.servicesImpl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.financas.Exeptions.RegraNegocioException;
import br.com.financas.model.Lancamento;
import br.com.financas.model.Enuns.StatusLancamento;
import br.com.financas.model.Enuns.TipoLancamento;
import br.com.financas.repositories.LancamentoRepository;
import br.com.financas.services.LancamentoService;

@Service
public class LancamentoServicesImpl implements LancamentoService {

	@Autowired
	private LancamentoRepository lancamentoRep;

	@Override
	@Transactional
	public Lancamento salvar(Lancamento lancamento) {

		lancamento.setStatus(StatusLancamento.PENDENTE);
		return lancamentoRep.save(lancamento);
	}

	@Override
	@Transactional
	public Lancamento atualizar(Lancamento lancamento) {
		Objects.requireNonNull(lancamento.getId());
		validar(lancamento);
		return lancamentoRep.save(lancamento);
	}

	@Override
	@Transactional
	public void deletar(Lancamento lancamento) {
		Objects.requireNonNull(lancamento.getId());
		lancamentoRep.delete(lancamento);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Lancamento> buscar(Lancamento lancamentoFiltro) {
		Example example = Example.of(lancamentoFiltro,
				ExampleMatcher.matching().withIgnoreCase().withStringMatcher(StringMatcher.CONTAINING));
		return lancamentoRep.findAll(example);
	}

	@Override
	public void atualizarStatus(Lancamento lancamento, StatusLancamento status) {
		lancamento.setStatus(status);
		atualizar(lancamento);

	}

	@Override
	public void validar(Lancamento lancamento) {
		if (lancamento.getDescricao() == null || lancamento.getDescricao().trim().equals("")) {
			throw new RegraNegocioException("Informe uma descricão valida!!!");
		}

		if (lancamento.getMes() == null || lancamento.getMes() < 1 || lancamento.getMes() > 12) {
			throw new RegraNegocioException("Informe uma mês valido!!!");
		}

		if (lancamento.getAno() == null || lancamento.getAno().toString().length() != 4) {
			throw new RegraNegocioException("Informe um ano valido!!!");
		}

		if (lancamento.getUsuario() == null || lancamento.getId() == null) {
			throw new RegraNegocioException("Informe um usuário !!!");
		}

		if (lancamento.getValor() == null || lancamento.getValor().compareTo(BigDecimal.ZERO) < 1) {
			throw new RegraNegocioException("Informe um valor valido !!!");
		}

		if (lancamento.getTipo() == null) {
			throw new RegraNegocioException("Informe um tipo de lancamento !!!");
		}

	}

	@Override
	public Optional<Lancamento> obterPorId(Long id) {
		return lancamentoRep.findById(id);
	}
        
         public Lancamento getOne(Long id) {
            return lancamentoRep.getOne(id);
         }

		@Override
		@Transactional(readOnly = true)
		public BigDecimal obterSaldoPorUsuario(Long id) {
		BigDecimal receitas = lancamentoRep.obterSaldoPorTipoLancamentoEUsuarioEStatus(id, TipoLancamento.RECEITA,StatusLancamento.EFETIVADO);
		BigDecimal despesas = lancamentoRep.obterSaldoPorTipoLancamentoEUsuarioEStatus(id, TipoLancamento.DESPESA,StatusLancamento.EFETIVADO);
		
		if (receitas == null) {
			receitas = BigDecimal.ZERO;
		}
		if (despesas == null) {
			despesas = BigDecimal.ZERO;
		}	
		
		return receitas.subtract(despesas);
		}
}
