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
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;
import br.org.universa.bank.autorizador.negocio.autorizacao.CanalDeAtendimento;
import br.org.universa.bank.autorizador.negocio.comum.UtilHelper;

public class Transacao {

	@Getter
	private String identificador;

	@Getter
	private Date dataHoraCriacao;

	@Getter
	@Setter
	private CanalDeAtendimento canalDeAtendimento;

	@Getter
	@Setter
	private TipoDaTransacao tipo;

	@Getter
	@Setter
	private int agencia;

	@Getter
	@Setter
	private int conta;

	@Getter
	@Setter
	private double valor;

	public Transacao() {
		identificador = UUID.randomUUID().toString();
		dataHoraCriacao = new Date();
	}

	protected boolean validaDados() {
		return UtilHelper.isCampoPreenchido(canalDeAtendimento)
				&& UtilHelper.isCampoPreenchido(tipo)
				&& UtilHelper.isCampoPreenchido(agencia)
				&& UtilHelper.isCampoPreenchido(conta)
				&& UtilHelper.isCampoPreenchido(valor);
	}
}
