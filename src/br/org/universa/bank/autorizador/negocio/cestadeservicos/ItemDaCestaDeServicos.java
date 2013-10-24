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
package br.org.universa.bank.autorizador.negocio.cestadeservicos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import br.org.universa.bank.autorizador.negocio.transacao.TipoDaTransacao;

@AllArgsConstructor
public class ItemDaCestaDeServicos {

	@Getter
	private TipoDaTransacao tipoDaTransacao;

	@Getter
	private int quantidadeContratada;

	@Getter
	private double tarifaPorExcedente;
}