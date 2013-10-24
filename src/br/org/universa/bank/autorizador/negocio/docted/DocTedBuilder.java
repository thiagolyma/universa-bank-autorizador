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
package br.org.universa.bank.autorizador.negocio.docted;

public class DocTedBuilder {

	private static DocTedBuilder instancia = null;

	private DocTedBuilder() {
		// construtor vazio
	}

	public static DocTedBuilder get() {
		if (instancia == null) {
			instancia = new DocTedBuilder();
		}

		return instancia;
	}

	public DocTed constroi() throws Exception {
		// TODO - Implementar

		return null;
	}

}