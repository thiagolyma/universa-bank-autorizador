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

import lombok.Getter;
import lombok.Setter;
import br.org.universa.bank.autorizador.negocio.comum.UtilHelper;
import br.org.universa.bank.autorizador.negocio.fundos.TipoDoFundo;

public class TransacaoDeInvestimentoEmFundo extends Transacao {

	@Getter
	@Setter
	private TipoDoFundo tipoDoFundo;

	@Override
	protected boolean validaDados() {
		return super.validaDados() && UtilHelper.isCampoPreenchido(tipoDoFundo);
	}
}
