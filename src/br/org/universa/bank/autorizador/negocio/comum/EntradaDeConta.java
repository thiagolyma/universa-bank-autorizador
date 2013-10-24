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
package br.org.universa.bank.autorizador.negocio.comum;

import br.org.universa.bank.autorizador.negocio.cestadeservicos.TipoDaCestaDeServicos;
import br.org.universa.bank.autorizador.negocio.comum.UtilHelper;
import br.org.universa.bank.autorizador.negocio.conta.Cliente;
import br.org.universa.bank.autorizador.negocio.conta.Conta;
import br.org.universa.bank.autorizador.persistencia.dao.ContaDAO;

public class EntradaDeConta {

	private static EntradaDeConta instancia = null;

	private EntradaDeConta() {
		// Construtor privado
	}

	public static EntradaDeConta get() {
		if (instancia == null) {
			instancia = new EntradaDeConta();
		}

		return instancia;
	}

	public void insere(int agencia, int numero, String nomeDoTitular,
			String cpfDoTitular, double saldo,
			TipoDaCestaDeServicos tipoDaCestaDeServicos) {
		Conta conta = new Conta();
		conta.setAgencia(agencia);
		conta.setNumero(numero);
		conta.setTipoDaCestaDeServicos(tipoDaCestaDeServicos);

		if (UtilHelper.isCampoPreenchido(nomeDoTitular)) {
			Cliente titular = new Cliente();

			titular.setNome(nomeDoTitular);
			titular.setCpf(cpfDoTitular);

			conta.setTitular(titular);
		}

		if (UtilHelper.isCampoPreenchido(saldo)) {
			conta.credita(saldo);
		} else {
			conta.credita(0.0);
		}

		ContaDAO.get().insere(conta);
	}
}
