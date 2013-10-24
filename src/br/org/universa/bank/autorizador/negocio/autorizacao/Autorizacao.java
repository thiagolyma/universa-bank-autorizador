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
package br.org.universa.bank.autorizador.negocio.autorizacao;

import lombok.Getter;
import lombok.Setter;
import br.org.universa.bank.autorizador.negocio.transacao.Transacao;

public class Autorizacao {

	@Getter
	@Setter
	private Transacao transacao;

	@Getter
	private String motivoDaNegacao;

	@Getter
	private EstadoDaAutorizacao estado;

	public void autorizada() {
		estado = EstadoDaAutorizacao.AUTORIZADA;
	}

	public void negada(String motivoDaNegacao) {
		this.motivoDaNegacao = motivoDaNegacao;
		estado = EstadoDaAutorizacao.NEGADA;
	}
}