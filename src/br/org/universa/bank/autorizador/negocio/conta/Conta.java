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

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import br.org.universa.bank.autorizador.negocio.cestadeservicos.TipoDaCestaDeServicos;
import br.org.universa.bank.autorizador.negocio.comum.Mensagens;
import br.org.universa.bank.autorizador.negocio.comum.UtilHelper;

public class Conta {

	@Getter
	@Setter
	private int agencia;

	@Getter
	@Setter
	private int numero;

	@Getter
	@Setter
	private Cliente titular;

	@Getter
	protected double saldo = 0.0;

	@Getter
	@Setter
	private TipoDaCestaDeServicos tipoDaCestaDeServicos;

	@Getter
	private List<LancamentoDaConta> lancamentosDaConta = new ArrayList<LancamentoDaConta>();

	public String getId() {
		return UtilHelper.getAgenciaFormatada(agencia)
				+ UtilHelper.getContaFormatada(numero);
	}

	public void credita(double valor) {
		saldo = saldo + valor;
	}

	public void debita(double valor) throws Exception {
		if (saldo >= valor) {
			saldo = saldo - valor;
		} else {
			throw new Exception(Mensagens.SALDO_INSUFICIENTE);
		}
	}

	public void adicionaLancamentoDaConta(LancamentoDaConta lancamentoDaConta) {
		this.lancamentosDaConta.add(lancamentoDaConta);
	}
}