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

import br.org.universa.bank.autorizador.negocio.docted.DocTed;
import br.org.universa.bank.autorizador.negocio.transacao.Transacao;

public class SPBAdapter {

	private static SPBAdapter instancia = null;

	private SPBAdapter() {
		// construtor vazio
	}

	public static SPBAdapter get() {
		if (instancia == null) {
			instancia = new SPBAdapter();
		}

		return instancia;
	}

	public void notificaTransacao(Transacao transacao) throws Exception {
		// TODO - Implementar
	}

	@SuppressWarnings("unused")
	private void analisaCodigoDeRetornoDoSPB(int codigoDeRetorno)
			throws Exception {
		switch (codigoDeRetorno) {

		default:
			break;
		}
	}

	public void registraTED(DocTed docTed) throws Exception {
		// TODO - Implementar
	}

	@SuppressWarnings("unused")
	private void analisaCodigoDeRetornoDeTedDoSPB(int codigoDeRetorno)
			throws Exception {
		switch (codigoDeRetorno) {

		default:
			break;
		}
	}
}