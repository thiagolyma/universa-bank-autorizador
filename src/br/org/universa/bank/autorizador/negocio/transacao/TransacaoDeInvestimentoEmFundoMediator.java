package br.org.universa.bank.autorizador.negocio.transacao;

import br.org.universa.bank.autorizador.negocio.comum.Mensagens;
import br.org.universa.bank.autorizador.negocio.conta.Conta;
import br.org.universa.bank.autorizador.negocio.conta.ContaMediator;
import br.org.universa.bank.autorizador.negocio.conta.LancamentoDaConta;
import br.org.universa.bank.autorizador.negocio.fundos.FundoDeInvestimentoMediator;
import br.org.universa.bank.autorizador.persistencia.dao.ContaDAO;

public class TransacaoDeInvestimentoEmFundoMediator extends AbstractTransacaoMediator{

	@Override
	protected void executaRegrasEspecificas(Transacao transacao)
			throws Exception {
		Conta conta = ContaMediator.get().consultaConta(transacao.getAgencia(),transacao.getConta());
		validaTransacaoInvestimentoEmFundo (conta, transacao.getValor());
		TransacaoDeInvestimentoEmFundo transacaoDeInvestimento = (TransacaoDeInvestimentoEmFundo) transacao;
		double rentabilidade = FundoDeInvestimentoMediator.get().calculaRentabilidade(transacaoDeInvestimento.getTipoDoFundo(), transacao.getValor());
		conta.credita(rentabilidade);
		conta.adicionaLancamentoDaConta(new LancamentoDaConta(transacao.getTipo().getTipoDoLancamento(),
				transacao.getTipo().getValor() + " " + transacaoDeInvestimento.getTipoDoFundo().getValor(),
				rentabilidade));
		ContaDAO.get().atualiza(conta);
	}
	
	private void validaTransacaoInvestimentoEmFundo(Conta conta, double valor) throws Exception{
		if (conta.getSaldo() < valor){
			throw new Exception(Mensagens.SALDO_INSUFICIENTE);
		}
	}


}
