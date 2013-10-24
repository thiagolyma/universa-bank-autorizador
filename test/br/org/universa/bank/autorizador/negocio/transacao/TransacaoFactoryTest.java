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

import org.junit.Assert;
import org.junit.Test;

import br.org.universa.bank.autorizador.negocio.transacao.TipoDaTransacao;
import br.org.universa.bank.autorizador.negocio.transacao.Transacao;
import br.org.universa.bank.autorizador.negocio.transacao.TransacaoFactory;

public class TransacaoFactoryTest {

	@Test(expected = RuntimeException.class)
	public void naoDeveTratarTransacoesNaoPrevistas() {
		Transacao transacao = new Transacao();
		transacao.setTipo(TipoDaTransacao.PAGAMENTO_DE_TITULOS);

		TransacaoFactory.get().cria(transacao);
		Assert.fail();
	}

}
