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
package br.org.universa.bank.autorizador.servico;

import java.util.Date;
import java.util.List;

import br.org.universa.bank.autorizador.negocio.autorizacao.Autorizacao;
import br.org.universa.bank.autorizador.negocio.conta.Conta;
import br.org.universa.bank.autorizador.negocio.conta.ContaMediator;
import br.org.universa.bank.autorizador.negocio.transacao.AbstractTransacaoMediator;
import br.org.universa.bank.autorizador.negocio.transacao.Transacao;
import br.org.universa.bank.autorizador.negocio.transacao.TransacaoFactory;
import br.org.universa.bank.autorizador.negocio.transacao.TransacaoMediator;

public class AutorizadorFacade {
	private static AutorizadorFacade instancia;

	private AutorizadorFacade() {
		// Construtor privado
	}

	public static AutorizadorFacade get() {
		if (instancia == null) {
			instancia = new AutorizadorFacade();
		}

		return instancia;
	}

	public Autorizacao executa(Transacao transacao) {
		AbstractTransacaoMediator tratador = TransacaoFactory.get().cria(transacao);
		Autorizacao autorizacao = tratador.executa(transacao);
		return autorizacao;
	}

	public Conta consultaConta(Integer agencia, Integer numero)
			throws Exception {
		Conta conta = ContaMediator.get().consultaConta(agencia, numero);
		return conta;
	}

	public List<Transacao> consultaTransacoes(Date dataDeReferencia) {
		return TransacaoMediator.get()
				.consultaTransacoesNoDia(dataDeReferencia);
	}
}