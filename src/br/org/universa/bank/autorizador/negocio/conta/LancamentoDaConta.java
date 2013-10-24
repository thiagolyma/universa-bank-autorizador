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
package br.org.universa.bank.autorizador.negocio.conta;

import java.util.Date;

import lombok.Getter;

public class LancamentoDaConta {

	@Getter
	private Date dataDoLancamento;

	@Getter
	private TipoDoLancamento tipoDoLancamento;

	@Getter
	private String descricao;

	@Getter
	private double valor;

	public LancamentoDaConta(TipoDoLancamento tipoDoLancamento,
			String descricao, double valor) {
		super();
		this.dataDoLancamento = new Date();
		this.tipoDoLancamento = tipoDoLancamento;
		this.descricao = descricao;
		this.valor = valor;
	}
}
