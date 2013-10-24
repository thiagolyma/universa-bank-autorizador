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

/**
 * Classe que contém as mensagens do sistema.
 * 
 * @author Flávio Roberto (professor.flavio.roberto@gmail.com)
 */
public final class Mensagens {
	/* Mensagens de Negócio */

	public static final String NAO_EXISTE_CONTA_PARA_AGENCIA_NUMERO = "Não existe conta para esta agência e número.";

	public static final String DADOS_INSUFICIENTES_REGISTRO_TED = "Dados insuficientes para registro da TED.";

	public static final String TIPO_TED_INVALIDA = "Tipo da TED inválida.";

	public static final String CPF_TITULAR_CONTA_FAVORECIDA_INVALIDO = "CPF do titular da conta favorecida inválido.";

	public static final String VALOR_MENOR_QUE_PERMITIDO_TED = "Valor menor que o permitido para TED.";

	public static final String DADOS_INSUFICIENTES_SPB = "Dados insuficientes para notificação da transação.";

	public static final String CPF_TITULAR_CONTA_INVALIDO = "CPF do titular da conta inválido.";

	public static final String SALDO_INSUFICIENTE = "Conta com saldo insuficiente para a transação.";

	public static final String DADOS_INSUFICIENTES_REALIZAR_TRANSACAO = "Dados insuficientes para realizar a transação.";
}