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
package br.org.universa.bank.autorizador.negocio.tarifacao;

import br.org.universa.bank.autorizador.negocio.transacao.Transacao;

public class TarifacaoMediator {
	private static TarifacaoMediator instancia = null;

	private TarifacaoMediator() {
		// Construtor privado
	}

	public static TarifacaoMediator get() {
		if (instancia == null) {
			instancia = new TarifacaoMediator();
		}

		return instancia;
	}

	public void tarifaTransacao(Transacao transacao) throws Exception {
		// TODO Implementar
	}
}
