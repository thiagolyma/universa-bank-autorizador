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

	public double tarifaTransacao(Transacao transacao) throws Exception {
		Tarifacao tarifacaoPorValor = new TarifacaoPorValor();
		Tarifacao tarifacaoCestaDeServico = new TarifacaoPorCestaDeServico();
		Tarifacao tarifacaoPorQuantidade = new TarifacaoPorQuantidade();
		Tarifacao semTarifacao = new SemTarifacao();
		
		tarifacaoPorValor.setProximaTarifacao(tarifacaoCestaDeServico);
		tarifacaoCestaDeServico.setProximaTarifacao(tarifacaoPorQuantidade);
		tarifacaoPorQuantidade.setProximaTarifacao(semTarifacao);
		
		return tarifacaoPorValor.calculaTarifa(transacao);
		
	}
}
