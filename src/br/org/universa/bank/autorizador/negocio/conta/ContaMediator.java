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

import br.org.universa.bank.autorizador.negocio.comum.Mensagens;
import br.org.universa.bank.autorizador.persistencia.dao.ContaDAO;

public class ContaMediator {
	private static ContaMediator instancia = null;

	private ContaMediator() {
		// Construtor privado
	}

	public static ContaMediator get() {
		if (instancia == null) {
			instancia = new ContaMediator();
		}

		return instancia;
	}

	public Conta consultaConta(Integer agencia, Integer numero)
			throws Exception {
		Conta conta = ContaDAO.get().consulta(agencia, numero);
		if(conta == null){
			throw new Exception(Mensagens.NAO_EXISTE_CONTA_PARA_AGENCIA_NUMERO);
		}
		return conta;
	}
}
