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

import java.io.IOException;
import java.util.Properties;

@SuppressWarnings("unused")
public class TransacaoFactory {

	private static TransacaoFactory instancia;
	private static Properties propriedades;
	private static final String TIPO_TRANSACAO_PROPERTIES = "TipoDaTransacao.properties";
	private static final String TIPO_DA_TRANSACAO_INVALIDO = "O tipo da transação não foi informado.";

	private TransacaoFactory() {
		// Construtor privado
	}

	public static TransacaoFactory get() {
		if (instancia == null) {
			instancia = new TransacaoFactory();
		}

		return instancia;
	}

	public AbstractTransacaoMediator cria(Transacao transacao) {
		AbstractTransacaoMediator tratador = null;
		carregaPropriedades();
		String caminhoDaClasse = propriedades.getProperty(transacao.getTipo().getChave().toString());
		try {
			tratador = (AbstractTransacaoMediator) Class.forName(caminhoDaClasse).newInstance();
		} catch (InstantiationException | IllegalAccessException
				| ClassNotFoundException e) {
			e.printStackTrace();
		}
		return tratador;
	}

	private static void carregaPropriedades() {
		if (propriedades == null) {
			propriedades = new Properties();
		}

		try {
			propriedades.load((AbstractTransacaoMediator.class)
					.getResourceAsStream(TIPO_TRANSACAO_PROPERTIES));
		} catch (IOException e) {
			throw new RuntimeException(
					"Erro ao carregar o arquivo de propriedades '"
							+ TIPO_TRANSACAO_PROPERTIES + "'", e);
		}
	}
}
