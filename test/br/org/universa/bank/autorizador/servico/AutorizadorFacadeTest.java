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

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.org.universa.bank.autorizador.negocio.autorizacao.CanalDeAtendimento;
import br.org.universa.bank.autorizador.negocio.cestadeservicos.TipoDaCestaDeServicos;
import br.org.universa.bank.autorizador.negocio.comum.EntradaDeConta;
import br.org.universa.bank.autorizador.negocio.conta.Conta;
import br.org.universa.bank.autorizador.negocio.transacao.TipoDaTransacao;
import br.org.universa.bank.autorizador.negocio.transacao.Transacao;
import br.org.universa.bank.autorizador.servico.AutorizadorFacade;

public class AutorizadorFacadeTest {

	@Before
	public void populaConta() {
		EntradaDeConta.get().insere(3, 3, "Nicole Cruz", "77276469115",
				1890.00, TipoDaCestaDeServicos.ESPECIAL);
	}

	@Test
	public void deveRealizarTransacoes() {
		Transacao transacao = new Transacao();
		transacao.setCanalDeAtendimento(CanalDeAtendimento.INTERNET_BANKING);
		transacao.setTipo(TipoDaTransacao.DEPOSITO_EM_CONTA);
		transacao.setAgencia(3);
		transacao.setConta(3);
		transacao.setValor(10.00);

		try {
			AutorizadorFacade.get().executa(transacao);

			Conta conta = AutorizadorFacade.get().consultaConta(3, 3);
			Assert.assertEquals(1900.00, conta.getSaldo(), 0.001);
		} catch (Exception e) {
			Assert.fail();
		}
	}
}
