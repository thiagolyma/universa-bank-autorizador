package br.org.universa.bank.autorizador.negocio.transacao;

import br.org.universa.bank.autorizador.negocio.conta.Conta;
import br.org.universa.bank.autorizador.negocio.conta.ContaMediator;
import br.org.universa.bank.autorizador.negocio.conta.LancamentoDaConta;
import br.org.universa.bank.autorizador.persistencia.dao.ContaDAO;

public class TransacaoDeTransferenciaMediator extends AbstractTransacaoMediator {

	@Override
	protected void executaRegrasEspecificas(Transacao transacao)
			throws Exception {
		TransacaoDeTransferencia transacaoTransferencia = (TransacaoDeTransferencia) transacao;
		Conta conta = ContaMediator.get().consultaConta(transacaoTransferencia.getAgencia(),transacaoTransferencia.getConta());
		Conta contaFavorecida = ContaMediator.get().consultaConta(transacaoTransferencia.getAgenciaFavorecida(), transacaoTransferencia.getContaFavorecida());
		conta.debita(transacaoTransferencia.getValor());
		contaFavorecida.credita(transacaoTransferencia.getValor());
		conta.adicionaLancamentoDaConta(new LancamentoDaConta(transacaoTransferencia.getTipo().getTipoDoLancamento(),
				transacaoTransferencia.getTipo().getValor(),
				transacaoTransferencia.getValor()));
		contaFavorecida.adicionaLancamentoDaConta(new LancamentoDaConta(transacaoTransferencia.getTipo().getTipoDoLancamento(),
				transacaoTransferencia.getTipo().getValor(),
				transacaoTransferencia.getValor()));
		ContaDAO.get().atualiza(conta);
		ContaDAO.get().atualiza(contaFavorecida);

	}

}
