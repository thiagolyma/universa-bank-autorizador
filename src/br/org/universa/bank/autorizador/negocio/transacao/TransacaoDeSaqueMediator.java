package br.org.universa.bank.autorizador.negocio.transacao;

import br.org.universa.bank.autorizador.negocio.conta.Conta;
import br.org.universa.bank.autorizador.negocio.conta.ContaMediator;
import br.org.universa.bank.autorizador.negocio.conta.LancamentoDaConta;
import br.org.universa.bank.autorizador.persistencia.dao.ContaDAO;

public class TransacaoDeSaqueMediator extends AbstractTransacaoMediator {

	@Override
	protected void executaRegrasEspecificas(Transacao transacao)
			throws Exception {
		Conta conta = ContaMediator.get().consultaConta(transacao.getAgencia(),transacao.getConta());
		conta.debita(transacao.getValor());
		conta.adicionaLancamentoDaConta(new LancamentoDaConta(transacao.getTipo().getTipoDoLancamento(),
				transacao.getTipo().getValor(),
				transacao.getValor()));
		ContaDAO.get().atualiza(conta);
		
	}

}
