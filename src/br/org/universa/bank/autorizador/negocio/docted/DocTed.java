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

import lombok.Getter;
import lombok.Setter;

public class DocTed {

	@Getter
	@Setter
	private String identificadorDaTransacao;

	@Getter
	@Setter
	private int bancoDeOrigem;

	@Getter
	@Setter
	private int agenciaDeOrigem;

	@Getter
	@Setter
	private int contaDeOrigem;

	@Getter
	@Setter
	private String cpfDoTitularDaContaDeOrigem;

	@Getter
	@Setter
	private double valor;

	@Getter
	@Setter
	private int bancoFavorecido;

	@Getter
	@Setter
	private int agenciaFavorecida;

	@Getter
	@Setter
	private int contaFavorecida;

	@Getter
	@Setter
	private String cpfDoTitularDaContaFavorecida;

	@Getter
	@Setter
	private CategoriaDaTransferencia categoriaDaTransferencia;

	@Getter
	@Setter
	private TipoDoDocTed tipoDoDocTed;

}
