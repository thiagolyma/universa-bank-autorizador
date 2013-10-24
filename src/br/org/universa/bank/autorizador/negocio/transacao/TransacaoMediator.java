/*
 * PhD Software do Brasil.
 * Autor: Flávio Roberto (professor.flavio.roberto@gmail.com)
 *
 * Autorizador Bancário
 *
 * OBS: Todos os códigos estão sendo oferecidos com a intenção única de
 * estimular o aprendizado. Não podem ser usados com fins comerciais sem
 * autorização prévia do autor. Se redistribuídos para outros sites, o autor e
 * a fonte devem ser sempre citados.
 */
package br.org.universa.bank.autorizador.negocio.transacao;

import java.util.Date;
import java.util.List;

import br.org.universa.bank.autorizador.negocio.conta.Conta;
import br.org.universa.bank.autorizador.negocio.conta.ContaMediator;
import br.org.universa.bank.autorizador.persistencia.dao.TransacaoDAO;

public class TransacaoMediator {
	private static TransacaoMediator instancia = null;

	private TransacaoMediator() {
		// Construtor privado
	}

	public static TransacaoMediator get() {
		if (instancia == null) {
			instancia = new TransacaoMediator();
		}

		return instancia;
	}

	public List<Transacao> consultaTransacoesDaContaNoDia(Conta conta) {
		return TransacaoDAO.get().consulta(conta, new Date());
	}

	public int consultaQuantidadeDeTransacoesDaContaNoDia(Conta conta) {
		List<Transacao> transacoesDoDia = TransacaoMediator.get()
				.consultaTransacoesDaContaNoDia(conta);

		return transacoesDoDia.size();
	}

	public List<Transacao> consultaTransacoesDaContaNoDiaPorTipoDaTransacao(
			Conta conta, Date dataDeReferencia, TipoDaTransacao tipoDaTransacao) {
		return TransacaoDAO.get().consulta(conta, dataDeReferencia,
				tipoDaTransacao);
	}

	public List<Transacao> consultaTransacoesNoDia(Date dataDeReferencia) {
		return TransacaoDAO.get().consulta(dataDeReferencia);
	}

	public void insereTransacaoDaConta(Transacao transacao) throws Exception {
		Conta conta = ContaMediator.get().consultaConta(transacao.getAgencia(),
				transacao.getConta());

		TransacaoDAO.get().insere(conta, transacao);
	}
}
