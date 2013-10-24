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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import br.org.universa.bank.autorizador.negocio.comum.Mensagens;
import br.org.universa.bank.autorizador.negocio.conta.Conta;
import br.org.universa.bank.autorizador.negocio.conta.ContaMediator;
import br.org.universa.bank.autorizador.persistencia.dao.ContaDAO;

public class ContaMediatorTest {

	@Before
	public void setUp() {
		Conta conta1 = new ContaTestDataBuilder() //
				.deNumero(3) //
				.daAgencia(3) //
				.comSaldo(1890.0) //
				.constroi();
		ContaDAO.get().insere(conta1);

		Conta conta2 = new ContaTestDataBuilder() //
				.deNumero(4) //
				.daAgencia(4) //
				.constroi();
		ContaDAO.get().insere(conta2);
	}

	@Test(expected = Exception.class)
	public void naoDeveSalvarContaNula() {
		ContaDAO.get().insere(null);
	}

	@Test
	public void naoDeveConsultarContaInexistente() {
		try {
			ContaMediator.get().consultaConta(1, 2);

			fail("Deve falhar pois a exceção não foi lançada já que o conta não existe");
		} catch (Exception e) {
			assertEquals(Mensagens.NAO_EXISTE_CONTA_PARA_AGENCIA_NUMERO,
					e.getMessage());
		}
	}

	@Test
	public void deveConsultarConta() {
		try {
			Conta conta = ContaMediator.get().consultaConta(3, 3);

			assertNotNull(conta);
			assertEquals(3, conta.getNumero());
			assertEquals(1890.0, conta.getSaldo(), 0.001);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
}