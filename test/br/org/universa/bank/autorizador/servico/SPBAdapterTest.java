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

import br.org.universa.bank.autorizador.negocio.cestadeservicos.TipoDaCestaDeServicos;
import br.org.universa.bank.autorizador.negocio.comum.EntradaDeConta;
import br.org.universa.bank.autorizador.negocio.comum.Mensagens;
import br.org.universa.bank.autorizador.negocio.docted.DocTed;
import br.org.universa.bank.autorizador.negocio.docted.TipoDoDocTed;
import br.org.universa.bank.autorizador.negocio.transacao.TipoDaTransacao;
import br.org.universa.bank.autorizador.negocio.transacao.Transacao;
import br.org.universa.bank.autorizador.servico.SPBAdapter;

public class SPBAdapterTest {
	private static final String CPF_772 = "77276469115";
	private static final String CPF_027 = "02728594430";
	private static final String NICOLE_CRUZ = "Nicole Cruz";
	private DocTed docTed;

	@Before
	public void populaTED() {
		docTed = new DocTed();

		docTed.setIdentificadorDaTransacao("12345678900");
		docTed.setTipoDoDocTed(TipoDoDocTed.C);
		docTed.setValor(3000.00);
		docTed.setBancoDeOrigem(111);
		docTed.setAgenciaDeOrigem(2222);
		docTed.setContaDeOrigem(3333333);
		docTed.setCpfDoTitularDaContaDeOrigem(CPF_772);
		docTed.setBancoFavorecido(444);
		docTed.setAgenciaFavorecida(5555);
		docTed.setContaFavorecida(6666666);
		docTed.setCpfDoTitularDaContaFavorecida(CPF_027);
	}

	@Test
	public void naoDeveRegistrarTEDComDadosInsuficientes() {
		docTed.setIdentificadorDaTransacao("");

		try {
			SPBAdapter.get().registraTED(docTed);
			Assert.fail();
		} catch (Exception e) {
			Assert.assertEquals(Mensagens.DADOS_INSUFICIENTES_REGISTRO_TED,
					e.getMessage());
		}
	}

	@Test
	public void naoDeveRegistrarTEDComTipoDaTEDInvalida() {
		docTed.setTipoDoDocTed(TipoDoDocTed.E);

		try {
			SPBAdapter.get().registraTED(docTed);
			Assert.fail();
		} catch (Exception e) {
			Assert.assertEquals(Mensagens.TIPO_TED_INVALIDA, e.getMessage());
		}
	}

	@Test
	public void naoDeveRegistrarTEDComCPFDoTitularDaContaFavorecidaInvalido() {
		docTed.setCpfDoTitularDaContaFavorecida("12134");

		try {
			SPBAdapter.get().registraTED(docTed);
			Assert.fail();
		} catch (Exception e) {
			Assert.assertEquals(
					Mensagens.CPF_TITULAR_CONTA_FAVORECIDA_INVALIDO,
					e.getMessage());
		}
	}

	@Test
	public void naoDeveRegistrarTEDComValorMenorQueOPermitido() {
		docTed.setValor(2999.99);

		try {
			SPBAdapter.get().registraTED(docTed);
			Assert.fail();
		} catch (Exception e) {
			Assert.assertEquals(Mensagens.VALOR_MENOR_QUE_PERMITIDO_TED,
					e.getMessage());
		}
	}

	@Test
	public void naoDeveNotificarTransacaoComDadosInsuficientes() {
		EntradaDeConta.get().insere(3, 3, NICOLE_CRUZ, "", 1890.00,
				TipoDaCestaDeServicos.ESPECIAL);

		Transacao transacao = new Transacao();
		transacao.setTipo(TipoDaTransacao.DEPOSITO_EM_CONTA);
		transacao.setValor(3000.00);
		transacao.setAgencia(3);
		transacao.setConta(3);

		try {
			SPBAdapter.get().notificaTransacao(transacao);
			Assert.fail();
		} catch (Exception e) {
			Assert.assertEquals(Mensagens.DADOS_INSUFICIENTES_SPB,
					e.getMessage());
		}
	}

	@Test
	public void naoDeveNotificarTransacaoComCpfDoTitularInvalido() {
		EntradaDeConta.get().insere(3, 3, NICOLE_CRUZ, "02728494430", 1890.00,
				TipoDaCestaDeServicos.ESPECIAL);

		Transacao transacao = new Transacao();
		transacao.setTipo(TipoDaTransacao.DEPOSITO_EM_CONTA);
		transacao.setValor(3000.00);
		transacao.setAgencia(3);
		transacao.setConta(3);

		try {
			SPBAdapter.get().notificaTransacao(transacao);
			Assert.fail();
		} catch (Exception e) {
			Assert.assertEquals(Mensagens.CPF_TITULAR_CONTA_INVALIDO,
					e.getMessage());
		}
	}
}
