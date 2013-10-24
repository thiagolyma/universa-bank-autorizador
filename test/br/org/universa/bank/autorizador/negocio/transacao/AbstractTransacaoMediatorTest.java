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

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.org.universa.bank.autorizador.negocio.autorizacao.Autorizacao;
import br.org.universa.bank.autorizador.negocio.autorizacao.CanalDeAtendimento;
import br.org.universa.bank.autorizador.negocio.autorizacao.EstadoDaAutorizacao;
import br.org.universa.bank.autorizador.negocio.cestadeservicos.TipoDaCestaDeServicos;
import br.org.universa.bank.autorizador.negocio.comum.EntradaDeConta;
import br.org.universa.bank.autorizador.negocio.comum.Mensagens;
import br.org.universa.bank.autorizador.negocio.comum.UtilHelper;
import br.org.universa.bank.autorizador.negocio.conta.Conta;
import br.org.universa.bank.autorizador.negocio.conta.ContaMediator;
import br.org.universa.bank.autorizador.negocio.conta.TipoDoLancamento;
import br.org.universa.bank.autorizador.negocio.fundos.TipoDoFundo;
import br.org.universa.bank.autorizador.negocio.transacao.AbstractTransacaoMediator;
import br.org.universa.bank.autorizador.negocio.transacao.TipoDaTransacao;
import br.org.universa.bank.autorizador.negocio.transacao.Transacao;
import br.org.universa.bank.autorizador.negocio.transacao.TransacaoDeDocTed;
import br.org.universa.bank.autorizador.negocio.transacao.TransacaoDeInvestimentoEmFundo;
import br.org.universa.bank.autorizador.negocio.transacao.TransacaoDeTransferencia;
import br.org.universa.bank.autorizador.negocio.transacao.TransacaoFactory;
import br.org.universa.bank.autorizador.negocio.transacao.TransacaoMediator;
import br.org.universa.bank.autorizador.persistencia.dao.ContaDAO;

public class AbstractTransacaoMediatorTest {

	@Before
	public void setUp() {
		ContaDAO.get().removeTodos();

		// Cliente penelope = new ClienteTestDataBuilder() //
		// .deNome("Penolope Cruz") //
		// .comCpf("77276469115") //
		// .constroi();

		// Conta contaDaPenolope = new ContaTestDataBuilder() //
		// .deNumero(3) //
		// .daAgencia(3) //
		// .paraOTitular(penelope) //
		// .comSaldo(1890.0) //
		// .doTipo(TipoDaCestaDeServicos.ESPECIAL) //
		// .constroi();

		// ContaDAO.get().insere(contaDaPenolope);

		// Cliente nicole = new ClienteTestDataBuilder() //
		// .deNome("Nicole Kidman") //
		// .comCpf("02728594430") //
		// .constroi();

		// Conta contaDaNicole = new ContaTestDataBuilder() //
		// .deNumero(4) //
		// .daAgencia(4) //
		// .paraOTitular(nicole) //
		// .comSaldo(3010.0) //
		// .doTipo(TipoDaCestaDeServicos.BASICA) //
		// .constroi();

		// ContaDAO.get().insere(contaDaNicole);

		EntradaDeConta.get().insere(3, 3, "Penelope Cruz", "77276469115",
				1890.00, TipoDaCestaDeServicos.ESPECIAL);

		EntradaDeConta.get().insere(4, 4, "Nicole Kidman", "02728594430",
				3010.00, TipoDaCestaDeServicos.BASICA);
	}

	@Test(expected = RuntimeException.class)
	public void naoDeveAceitarUmaTransacaoNula() {
		TransacaoFactory.get().cria(null);
	}

	@Test(expected = RuntimeException.class)
	public void naoDeveAceitarTransacaoSemDadosInformados() {
		Transacao transacao = new Transacao();

		TransacaoFactory.get().cria(transacao);
	}

	@Test
	public void naoDeveAutorizarTransacaoComDadosInsuficientes() {
		Transacao transacao = new Transacao();
		transacao.setTipo(TipoDaTransacao.DEPOSITO_EM_CONTA);

		AbstractTransacaoMediator tratadorDaTransacao = TransacaoFactory.get()
				.cria(transacao);

		Autorizacao autorizacao = tratadorDaTransacao.executa(transacao);
		Assert.assertEquals(EstadoDaAutorizacao.NEGADA, autorizacao.getEstado());
		Assert.assertEquals(Mensagens.DADOS_INSUFICIENTES_REALIZAR_TRANSACAO,
				autorizacao.getMotivoDaNegacao());
	}

	@Test
	public void naoDeveAutorizarTransacaoComValorZero() {
		Transacao transacao = new Transacao();
		transacao.setCanalDeAtendimento(CanalDeAtendimento.INTERNET_BANKING);
		transacao.setTipo(TipoDaTransacao.DEPOSITO_EM_CONTA);
		transacao.setAgencia(1);
		transacao.setConta(1);
		transacao.setValor(0.0);

		AbstractTransacaoMediator tratadorDaTransacao = TransacaoFactory.get()
				.cria(transacao);

		Autorizacao autorizacao = tratadorDaTransacao.executa(transacao);
		Assert.assertEquals(EstadoDaAutorizacao.NEGADA, autorizacao.getEstado());
		Assert.assertEquals(Mensagens.DADOS_INSUFICIENTES_REALIZAR_TRANSACAO,
				autorizacao.getMotivoDaNegacao());
	}

	@Test
	public void naoDeveAutorizarTransacaoDeInvestimentoEmFundoComDadosInsuficientes() {
		TransacaoDeInvestimentoEmFundo transacao = new TransacaoDeInvestimentoEmFundo();
		transacao.setCanalDeAtendimento(CanalDeAtendimento.INTERNET_BANKING);
		transacao.setTipo(TipoDaTransacao.INVESTIMENTO_EM_FUNDO);
		transacao.setAgencia(1);
		transacao.setConta(1);
		transacao.setValor(1.00);

		AbstractTransacaoMediator tratadorDaTransacao = TransacaoFactory.get()
				.cria(transacao);

		Autorizacao autorizacao = tratadorDaTransacao.executa(transacao);
		Assert.assertEquals(EstadoDaAutorizacao.NEGADA, autorizacao.getEstado());
		Assert.assertEquals(Mensagens.DADOS_INSUFICIENTES_REALIZAR_TRANSACAO,
				autorizacao.getMotivoDaNegacao());
	}

	@Test
	public void naoDeveAutorizarTransacaoDeTransferenciaComDadosInsuficientes() {
		TransacaoDeTransferencia transacao = new TransacaoDeTransferencia();
		transacao.setCanalDeAtendimento(CanalDeAtendimento.INTERNET_BANKING);
		transacao
				.setTipo(TipoDaTransacao.TRANSFERENCIA_ENTRE_CONTAS);
		transacao.setAgencia(1);
		transacao.setConta(1);
		transacao.setValor(1.00);
		transacao.setAgenciaFavorecida(0);

		AbstractTransacaoMediator tratadorDaTransacao = TransacaoFactory.get()
				.cria(transacao);

		Autorizacao autorizacao = tratadorDaTransacao.executa(transacao);
		Assert.assertEquals(EstadoDaAutorizacao.NEGADA, autorizacao.getEstado());
		Assert.assertEquals(Mensagens.DADOS_INSUFICIENTES_REALIZAR_TRANSACAO,
				autorizacao.getMotivoDaNegacao());
	}

	@Test
	public void naoDeveAutorizarTransacaoDeDocTedComDadosInsuficientes() {
		TransacaoDeDocTed transacao = new TransacaoDeDocTed();
		transacao.setCanalDeAtendimento(CanalDeAtendimento.INTERNET_BANKING);
		transacao.setTipo(TipoDaTransacao.DOC_TED_ENTRE_BANCOS);
		transacao.setAgencia(1);
		transacao.setConta(1);
		transacao.setValor(1.00);
		transacao.setBancoFavorecido(0);
		transacao.setAgenciaFavorecida(0);
		transacao.setContaFavorecida(0);
		transacao.setCpfDoTitularDaContaFavorecida("");

		AbstractTransacaoMediator tratadorDaTransacao = TransacaoFactory.get()
				.cria(transacao);

		Autorizacao autorizacao = tratadorDaTransacao.executa(transacao);
		Assert.assertEquals(EstadoDaAutorizacao.NEGADA, autorizacao.getEstado());
		Assert.assertEquals(Mensagens.DADOS_INSUFICIENTES_REALIZAR_TRANSACAO,
				autorizacao.getMotivoDaNegacao());
	}

	@Test
	public void naoDeveAutorizarTransancaoDeSaqueEmContaSemSaldoSuficiente() {
		Transacao transacao = criaTransacaoDeSaque(1891.00);

		AbstractTransacaoMediator tratadorDaTransacao = TransacaoFactory.get()
				.cria(transacao);

		Autorizacao autorizacao = tratadorDaTransacao.executa(transacao);
		Assert.assertEquals(EstadoDaAutorizacao.NEGADA, autorizacao.getEstado());
		Assert.assertEquals(Mensagens.SALDO_INSUFICIENTE,
				autorizacao.getMotivoDaNegacao());
	}

	@Test
	public void deveAutorizarTransacaoDeSaqueEmContaComSaldo() {
		Transacao transacao = criaTransacaoDeSaque(90.00);

		AbstractTransacaoMediator tratadorDaTransacao = TransacaoFactory.get()
				.cria(transacao);

		Autorizacao autorizacao = tratadorDaTransacao.executa(transacao);
		Assert.assertEquals(EstadoDaAutorizacao.AUTORIZADA,
				autorizacao.getEstado());

		try {
			Conta conta = ContaMediator.get().consultaConta(3, 3);
			Assert.assertEquals(1800.00, conta.getSaldo(), 0.001);
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void deveGerarLancamentoDeDebitoParaTransacaoDeSaque() {
		Transacao transacao = criaTransacaoDeSaque(90.00);

		AbstractTransacaoMediator tratadorDaTransacao = TransacaoFactory.get()
				.cria(transacao);

		Autorizacao autorizacao = tratadorDaTransacao.executa(transacao);
		Assert.assertEquals(EstadoDaAutorizacao.AUTORIZADA,
				autorizacao.getEstado());

		try {
			Conta conta = ContaMediator.get().consultaConta(3, 3);
			if (!conta.getLancamentosDaConta().isEmpty()) {
				Assert.assertEquals(90.00, conta.getLancamentosDaConta().get(0)
						.getValor(), 0.001);
				Assert.assertEquals(TipoDoLancamento.DEBITO, conta
						.getLancamentosDaConta().get(0).getTipoDoLancamento());
				Assert.assertEquals(TipoDaTransacao.SAQUE_EM_CONTA.getValor(),
						conta.getLancamentosDaConta().get(0).getDescricao());
			}
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void deveAutorizarTransacaoDeDeposito() {
		Transacao transacao = criaTransacaoDeDeposito(99.99);

		AbstractTransacaoMediator tratadorDaTransacao = TransacaoFactory.get()
				.cria(transacao);

		Autorizacao autorizacao = tratadorDaTransacao.executa(transacao);
		Assert.assertEquals(EstadoDaAutorizacao.AUTORIZADA,
				autorizacao.getEstado());

		try {
			Conta conta = ContaMediator.get().consultaConta(3, 3);
			Assert.assertEquals(1989.99, conta.getSaldo(), 0.001);
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void deveGerarLancamentoDeCreditoParaTransacaoDeDeposito() {
		Transacao transacao = criaTransacaoDeDeposito(99.99);

		AbstractTransacaoMediator tratadorDaTransacao = TransacaoFactory.get()
				.cria(transacao);

		Autorizacao autorizacao = tratadorDaTransacao.executa(transacao);
		Assert.assertEquals(EstadoDaAutorizacao.AUTORIZADA,
				autorizacao.getEstado());

		try {
			Conta conta = ContaMediator.get().consultaConta(3, 3);
			if (!conta.getLancamentosDaConta().isEmpty()) {
				Assert.assertEquals(99.99, conta.getLancamentosDaConta().get(0)
						.getValor(), 0.001);
				Assert.assertEquals(TipoDoLancamento.CREDITO, conta
						.getLancamentosDaConta().get(0).getTipoDoLancamento());
				Assert.assertEquals(
						TipoDaTransacao.DEPOSITO_EM_CONTA.getValor(), conta
								.getLancamentosDaConta().get(0).getDescricao());
			}
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void naoDeveAutorizarTransacaoDeTransfereEntreContasSemSaldoSuficiente() {
		TransacaoDeTransferencia transacao = criaTransacaoDeTransferencia(1890.01);

		AbstractTransacaoMediator tratadorDaTransacao = TransacaoFactory.get()
				.cria(transacao);

		Autorizacao autorizacao = tratadorDaTransacao.executa(transacao);
		Assert.assertEquals(EstadoDaAutorizacao.NEGADA, autorizacao.getEstado());
		Assert.assertEquals(Mensagens.SALDO_INSUFICIENTE,
				autorizacao.getMotivoDaNegacao());

	}

	@Test
	public void deveAutorizarTransacaoDeTransfereEntreContasComSaldo() {
		TransacaoDeTransferencia transacao = criaTransacaoDeTransferencia(99.99);

		AbstractTransacaoMediator tratadorDaTransacao = TransacaoFactory.get()
				.cria(transacao);

		Autorizacao autorizacao = tratadorDaTransacao.executa(transacao);
		Assert.assertEquals(EstadoDaAutorizacao.AUTORIZADA,
				autorizacao.getEstado());

		try {
			Conta contaDeDebito = ContaMediator.get().consultaConta(3, 3);
			Assert.assertEquals(1790.01, contaDeDebito.getSaldo(), 0.001);

			Conta contaFavorecida = ContaMediator.get().consultaConta(4, 4);
			Assert.assertEquals(3109.99, contaFavorecida.getSaldo(), 0.001);
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void deveGerarLancamentosDeCreditoEDebitoParaTransacaoDeTransferenciaEntreContas() {
		TransacaoDeTransferencia transacao = criaTransacaoDeTransferencia(90.00);

		AbstractTransacaoMediator tratadorDaTransacao = TransacaoFactory.get()
				.cria(transacao);

		Autorizacao autorizacao = tratadorDaTransacao.executa(transacao);
		Assert.assertEquals(EstadoDaAutorizacao.AUTORIZADA,
				autorizacao.getEstado());

		try {
			Conta contaDeDebito = ContaMediator.get().consultaConta(3, 3);
			if (contaDeDebito.getLancamentosDaConta().isEmpty()) {
				Assert.fail();
			} else {
				Assert.assertEquals(90.00, contaDeDebito
						.getLancamentosDaConta().get(0).getValor(), 0.001);
				Assert.assertEquals(TipoDoLancamento.DEBITO, contaDeDebito
						.getLancamentosDaConta().get(0).getTipoDoLancamento());
				Assert.assertEquals(
						TipoDaTransacao.TRANSFERENCIA_ENTRE_CONTAS.getValor(),
						contaDeDebito.getLancamentosDaConta().get(0)
								.getDescricao());
			}

			Conta contaFavorecida = ContaMediator.get().consultaConta(4, 4);
			if (contaFavorecida.getLancamentosDaConta().isEmpty()) {
				Assert.fail();
			} else {
				Assert.assertEquals(90.00, contaFavorecida
						.getLancamentosDaConta().get(0).getValor(), 0.001);
				Assert.assertEquals(TipoDoLancamento.CREDITO, contaFavorecida
						.getLancamentosDaConta().get(0).getTipoDoLancamento());
				Assert.assertEquals(
						TipoDaTransacao.TRANSFERENCIA_ENTRE_CONTAS.getValor(),
						contaFavorecida.getLancamentosDaConta().get(0)
								.getDescricao());
			}
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void naoDeveAutorizarTransacaoDeInvestimentoEmFundoSemSaldoSuficiente() {
		TransacaoDeInvestimentoEmFundo transacao = criaTransacaoDeInvestimentoEmFundo(
				1891.00, TipoDoFundo.CONSERVADOR);

		AbstractTransacaoMediator tratadorDaTransacao = TransacaoFactory.get()
				.cria(transacao);

		Autorizacao autorizacao = tratadorDaTransacao.executa(transacao);
		Assert.assertEquals(EstadoDaAutorizacao.NEGADA, autorizacao.getEstado());
		Assert.assertEquals(Mensagens.SALDO_INSUFICIENTE,
				autorizacao.getMotivoDaNegacao());

	}

	private void investeEmFundoDeInvestimento(double valor,
			TipoDoFundo tipoDoFundo) {
		TransacaoDeInvestimentoEmFundo transacao = criaTransacaoDeInvestimentoEmFundo(
				valor, tipoDoFundo);

		AbstractTransacaoMediator tratadorDaTransacao = TransacaoFactory.get()
				.cria(transacao);

		Autorizacao autorizacao = tratadorDaTransacao.executa(transacao);
		Assert.assertEquals(EstadoDaAutorizacao.AUTORIZADA,
				autorizacao.getEstado());
	}

	@Test
	public void deveInvestirEmFundoDeInvestimentoConservador() {
		investeEmFundoDeInvestimento(1890.00, TipoDoFundo.CONSERVADOR);

		try {
			Conta conta = ContaMediator.get().consultaConta(3, 3);

			double rentabilidadeLiquida = UtilHelper.arredonda(
					conta.getSaldo() - 1890.00, 2);

			Assert.assertEquals(11.34, rentabilidadeLiquida, 0.001);

			if (conta.getLancamentosDaConta().isEmpty()) {
				Assert.fail();
			} else {
				Assert.assertEquals(rentabilidadeLiquida, conta
						.getLancamentosDaConta().get(0).getValor(), 0.001);
				Assert.assertEquals(TipoDoLancamento.CREDITO, conta
						.getLancamentosDaConta().get(0).getTipoDoLancamento());
				Assert.assertEquals(
						TipoDaTransacao.INVESTIMENTO_EM_FUNDO.getValor() + " "
								+ TipoDoFundo.CONSERVADOR.getValor(), conta
								.getLancamentosDaConta().get(0).getDescricao());
			}
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void deveInvestirEmFundoDeInvestimentoModerado() {
		investeEmFundoDeInvestimento(1890.00, TipoDoFundo.MODERADO);

		try {
			Conta conta = ContaMediator.get().consultaConta(3, 3);

			double rentabilidadeLiquida = UtilHelper.arredonda(
					conta.getSaldo() - 1890.00, 2);

			if (rentabilidadeLiquida == 8.51 || (rentabilidadeLiquida == 21.26)) {
				Assert.assertTrue(true);
			} else {
				Assert.assertTrue(false);
			}

			if (conta.getLancamentosDaConta().isEmpty()) {
				Assert.fail();
			} else {
				Assert.assertEquals(rentabilidadeLiquida, UtilHelper.arredonda(
						conta.getLancamentosDaConta().get(0).getValor(), 2),
						0.1);
				Assert.assertEquals(TipoDoLancamento.CREDITO, conta
						.getLancamentosDaConta().get(0).getTipoDoLancamento());
				Assert.assertEquals(
						TipoDaTransacao.INVESTIMENTO_EM_FUNDO.getValor() + " "
								+ TipoDoFundo.MODERADO.getValor(), conta
								.getLancamentosDaConta().get(0).getDescricao());
			}
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void deveCreditarRentabilidadeAoSaldoDaContaAposAplicacaoEmFundoAgressivo() {
		investeEmFundoDeInvestimento(1890.00, TipoDoFundo.AGRESSIVO);

		try {
			Conta conta = ContaMediator.get().consultaConta(3, 3);

			double rentabilidadeLiquida = UtilHelper.arredonda(
					conta.getSaldo() - 1890.00, 2);

			if (rentabilidadeLiquida == 5.67 || rentabilidadeLiquida == 17.01
					|| rentabilidadeLiquida == 28.35) {
				Assert.assertTrue(true);
			} else {
				System.out.println(rentabilidadeLiquida);
				Assert.assertTrue(false);
			}

			if (conta.getLancamentosDaConta().isEmpty()) {
				Assert.fail();
			} else {
				Assert.assertEquals(rentabilidadeLiquida, UtilHelper.arredonda(
						conta.getLancamentosDaConta().get(0).getValor(), 2),
						0.001);
				Assert.assertEquals(TipoDoLancamento.CREDITO, conta
						.getLancamentosDaConta().get(0).getTipoDoLancamento());
				Assert.assertEquals(
						TipoDaTransacao.INVESTIMENTO_EM_FUNDO.getValor() + " "
								+ TipoDoFundo.AGRESSIVO.getValor(), conta
								.getLancamentosDaConta().get(0).getDescricao());
			}
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void deveAutorizarTransacaoDeDOC() {
		TransacaoDeDocTed transacao = new TransacaoDeDocTed();
		transacao.setCanalDeAtendimento(CanalDeAtendimento.INTERNET_BANKING);
		transacao.setTipo(TipoDaTransacao.DOC_TED_ENTRE_BANCOS);
		transacao.setAgencia(3);
		transacao.setConta(3);
		transacao.setValor(890.00);
		transacao.setBancoFavorecido(444);
		transacao.setAgenciaFavorecida(5555);
		transacao.setContaFavorecida(6666666);
		transacao.setCpfDoTitularDaContaFavorecida("77276469115");

		AbstractTransacaoMediator tratadorDaTransacao = TransacaoFactory.get()
				.cria(transacao);

		Autorizacao autorizacao = tratadorDaTransacao.executa(transacao);
		Assert.assertEquals(EstadoDaAutorizacao.AUTORIZADA,
				autorizacao.getEstado());

		try {
			Conta conta = ContaMediator.get().consultaConta(3, 3);
			Assert.assertEquals(1000.00, conta.getSaldo(), 0.001);

			if (conta.getLancamentosDaConta().isEmpty()) {
				Assert.fail();
			} else {
				Assert.assertEquals(890.00, conta.getLancamentosDaConta()
						.get(0).getValor(), 0.001);
				Assert.assertEquals(TipoDoLancamento.DEBITO, conta
						.getLancamentosDaConta().get(0).getTipoDoLancamento());
				Assert.assertEquals("DOC-C para a conta 444:5555:6666666",
						conta.getLancamentosDaConta().get(0).getDescricao());
			}
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void deveAutorizarTransacaoDeTED() {
		TransacaoDeDocTed transacao = new TransacaoDeDocTed();
		transacao.setCanalDeAtendimento(CanalDeAtendimento.INTERNET_BANKING);
		transacao.setTipo(TipoDaTransacao.DOC_TED_ENTRE_BANCOS);
		transacao.setAgencia(4);
		transacao.setConta(4);
		transacao.setValor(3000.00);
		transacao.setBancoFavorecido(444);
		transacao.setAgenciaFavorecida(5555);
		transacao.setContaFavorecida(6666666);
		transacao.setCpfDoTitularDaContaFavorecida("77276469115");

		AbstractTransacaoMediator tratadorDaTransacao = TransacaoFactory.get()
				.cria(transacao);

		Autorizacao autorizacao = tratadorDaTransacao.executa(transacao);
		Assert.assertEquals(EstadoDaAutorizacao.AUTORIZADA,
				autorizacao.getEstado());

		try {
			Conta conta = ContaMediator.get().consultaConta(4, 4);
			Assert.assertEquals(5.50, conta.getSaldo(), 0.001);

			if (conta.getLancamentosDaConta().isEmpty()) {
				Assert.fail();
			} else {
				Assert.assertEquals(3000.00,
						conta.getLancamentosDaConta().get(0).getValor(), 0.001);
				Assert.assertEquals(TipoDoLancamento.DEBITO, conta
						.getLancamentosDaConta().get(0).getTipoDoLancamento());
				Assert.assertEquals("TED-D para a conta 444:5555:6666666",
						conta.getLancamentosDaConta().get(0).getDescricao());
			}
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void deveTarifarPorValorDaTransacaoParaContaEspecial() {
		Transacao transacao = criaTransacaoDeDeposito(3000.00);

		AbstractTransacaoMediator tratadorDaTransacao = TransacaoFactory.get()
				.cria(transacao);

		Autorizacao autorizacao = tratadorDaTransacao.executa(transacao);
		Assert.assertEquals(EstadoDaAutorizacao.AUTORIZADA,
				autorizacao.getEstado());

		try {
			Conta conta = ContaMediator.get().consultaConta(3, 3);

			if (!conta.getLancamentosDaConta().isEmpty()) {
				Assert.assertEquals(3.00, conta.getLancamentosDaConta().get(1)
						.getValor(), 0.001);
				Assert.assertEquals(TipoDoLancamento.DEBITO, conta
						.getLancamentosDaConta().get(1).getTipoDoLancamento());
				Assert.assertEquals("Tarifação - Depósito em Conta", conta
						.getLancamentosDaConta().get(1).getDescricao());
			}
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void deveTarifarPorValorDaTransacaoParaContaBasica() {
		Transacao transacao = new Transacao();
		transacao.setCanalDeAtendimento(CanalDeAtendimento.INTERNET_BANKING);
		transacao.setTipo(TipoDaTransacao.DEPOSITO_EM_CONTA);
		transacao.setAgencia(4);
		transacao.setConta(4);
		transacao.setValor(3000.00);

		AbstractTransacaoMediator tratadorDaTransacao = TransacaoFactory.get()
				.cria(transacao);

		Autorizacao autorizacao = tratadorDaTransacao.executa(transacao);
		Assert.assertEquals(EstadoDaAutorizacao.AUTORIZADA,
				autorizacao.getEstado());

		try {
			Conta conta = ContaMediator.get().consultaConta(4, 4);

			if (!conta.getLancamentosDaConta().isEmpty()) {
				Assert.assertEquals(4.50, conta.getLancamentosDaConta().get(1)
						.getValor(), 0.001);
				Assert.assertEquals(TipoDoLancamento.DEBITO, conta
						.getLancamentosDaConta().get(1).getTipoDoLancamento());
				Assert.assertEquals("Tarifação - Depósito em Conta", conta
						.getLancamentosDaConta().get(1).getDescricao());
			}
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void deveTarifarorCestaDeServicosParaContaEspecial() {
		TransacaoDeDocTed transacao = new TransacaoDeDocTed();
		transacao.setCanalDeAtendimento(CanalDeAtendimento.INTERNET_BANKING);
		transacao.setTipo(TipoDaTransacao.DOC_TED_ENTRE_BANCOS);
		transacao.setAgencia(3);
		transacao.setConta(3);
		transacao.setValor(900.00);
		transacao.setBancoFavorecido(123);
		transacao.setAgenciaFavorecida(1234);
		transacao.setContaFavorecida(1234567);
		transacao.setCpfDoTitularDaContaFavorecida("02728594430");

		AbstractTransacaoMediator tratadorDaTransacao = TransacaoFactory.get()
				.cria(transacao);

		Autorizacao autorizacao = tratadorDaTransacao.executa(transacao);
		Assert.assertEquals(EstadoDaAutorizacao.AUTORIZADA,
				autorizacao.getEstado());

		try {
			/* Executa a segunda transa��o para testar cesta de servi�os */
			tratadorDaTransacao.executa(transacao);

			Conta conta = ContaMediator.get().consultaConta(3, 3);

			if (!conta.getLancamentosDaConta().isEmpty()) {
				Assert.assertEquals(5.50, conta.getLancamentosDaConta().get(2)
						.getValor(), 0.001);
				Assert.assertEquals(TipoDoLancamento.DEBITO, conta
						.getLancamentosDaConta().get(2).getTipoDoLancamento());
				Assert.assertEquals("Tarifação - DOC/TED entre Bancos", conta
						.getLancamentosDaConta().get(2).getDescricao());
			}
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void deveTarifarPorCestaDeServicosParaContaBasica() {
		TransacaoDeDocTed transacao = new TransacaoDeDocTed();
		transacao.setCanalDeAtendimento(CanalDeAtendimento.INTERNET_BANKING);
		transacao.setTipo(TipoDaTransacao.DOC_TED_ENTRE_BANCOS);
		transacao.setAgencia(4);
		transacao.setConta(4);
		transacao.setValor(1000.00);
		transacao.setBancoFavorecido(123);
		transacao.setAgenciaFavorecida(1234);
		transacao.setContaFavorecida(1234567);
		transacao.setCpfDoTitularDaContaFavorecida("02728594430");

		AbstractTransacaoMediator tratadorDaTransacao = TransacaoFactory.get()
				.cria(transacao);

		Autorizacao autorizacao = tratadorDaTransacao.executa(transacao);
		Assert.assertEquals(EstadoDaAutorizacao.AUTORIZADA,
				autorizacao.getEstado());

		try {
			/* Executa a segunda transa��o para testar cesta de servi�os */
			tratadorDaTransacao.executa(transacao);

			Conta conta = ContaMediator.get().consultaConta(4, 4);

			Assert.assertEquals(1003.00, conta.getSaldo(), 0.001);

			if (!conta.getLancamentosDaConta().isEmpty()) {
				Assert.assertEquals(7.00, conta.getLancamentosDaConta().get(2)
						.getValor(), 0.001);
				Assert.assertEquals(TipoDoLancamento.DEBITO, conta
						.getLancamentosDaConta().get(2).getTipoDoLancamento());
				Assert.assertEquals("Tarifação - DOC/TED entre Bancos", conta
						.getLancamentosDaConta().get(2).getDescricao());
			}
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void deveTarifarPorQuantidadeDeTransacoesParaContaBasica() {
		Transacao transacao = new Transacao();
		transacao.setCanalDeAtendimento(CanalDeAtendimento.INTERNET_BANKING);
		transacao.setTipo(TipoDaTransacao.DEPOSITO_EM_CONTA);
		transacao.setAgencia(4);
		transacao.setConta(4);
		transacao.setValor(100.00);

		AbstractTransacaoMediator tratadorDaTransacao = TransacaoFactory.get()
				.cria(transacao);

		Autorizacao autorizacao = tratadorDaTransacao.executa(transacao);
		Assert.assertEquals(EstadoDaAutorizacao.AUTORIZADA,
				autorizacao.getEstado());

		try {
			/* Executa mais 2 transa��es para testar quantidade */
			tratadorDaTransacao.executa(transacao);
			tratadorDaTransacao.executa(transacao);

			Conta conta = ContaMediator.get().consultaConta(4, 4);

			if (!conta.getLancamentosDaConta().isEmpty()) {
				Assert.assertEquals(2.00, conta.getLancamentosDaConta().get(3)
						.getValor(), 0.001);
				Assert.assertEquals(TipoDoLancamento.DEBITO, conta
						.getLancamentosDaConta().get(3).getTipoDoLancamento());
				Assert.assertEquals("Tarifação - Depósito em Conta", conta
						.getLancamentosDaConta().get(3).getDescricao());
			}
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void deveTarifarPorQuantidadeDeTransacoesParaContaEspecial() {
		Transacao transacao = criaTransacaoDeDeposito(100.00);

		AbstractTransacaoMediator tratadorDaTransacao = TransacaoFactory.get()
				.cria(transacao);

		Autorizacao autorizacao = tratadorDaTransacao.executa(transacao);
		Assert.assertEquals(EstadoDaAutorizacao.AUTORIZADA,
				autorizacao.getEstado());

		try {
			/* Executa mais 4 transações para testar quantidade */
			tratadorDaTransacao.executa(transacao);
			tratadorDaTransacao.executa(transacao);
			tratadorDaTransacao.executa(transacao);
			tratadorDaTransacao.executa(transacao);

			Conta conta = ContaMediator.get().consultaConta(3, 3);

			if (!conta.getLancamentosDaConta().isEmpty()) {
				Assert.assertEquals(1.50, conta.getLancamentosDaConta().get(5)
						.getValor(), 0.001);
				Assert.assertEquals(TipoDoLancamento.DEBITO, conta
						.getLancamentosDaConta().get(5).getTipoDoLancamento());
				Assert.assertEquals("Tarifação - Depósito em Conta", conta
						.getLancamentosDaConta().get(5).getDescricao());
			}
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void deveGravarATransacaoRealiada() {
		Transacao transacao = criaTransacaoDeDeposito(99.99);

		AbstractTransacaoMediator tratadorDaTransacao = TransacaoFactory.get()
				.cria(transacao);

		Autorizacao autorizacao = tratadorDaTransacao.executa(transacao);
		Assert.assertEquals(EstadoDaAutorizacao.AUTORIZADA,
				autorizacao.getEstado());

		try {
			Conta conta = ContaMediator.get().consultaConta(3, 3);

			List<Transacao> transacoes = TransacaoMediator.get()
					.consultaTransacoesDaContaNoDia(conta);

			Assert.assertEquals(TipoDaTransacao.DEPOSITO_EM_CONTA.getChave(),
					transacoes.get(0).getTipo().getChave());
			Assert.assertEquals(CanalDeAtendimento.TERMINAL_CAIXA.getChave(),
					transacoes.get(0).getCanalDeAtendimento().getChave());
			Assert.assertEquals(99.99, transacoes.get(0).getValor(), 0.001);
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	private Transacao criaTransacaoDeSaque(double valor) {
		Transacao transacao = new Transacao();
		transacao.setCanalDeAtendimento(CanalDeAtendimento.TERMINAL_CAIXA);
		transacao.setTipo(TipoDaTransacao.SAQUE_EM_CONTA);
		transacao.setAgencia(3);
		transacao.setConta(3);
		transacao.setValor(valor);

		return transacao;
	}

	private Transacao criaTransacaoDeDeposito(double valor) {
		Transacao transacao = new Transacao();
		transacao.setCanalDeAtendimento(CanalDeAtendimento.TERMINAL_CAIXA);
		transacao.setTipo(TipoDaTransacao.DEPOSITO_EM_CONTA);
		transacao.setAgencia(3);
		transacao.setConta(3);
		transacao.setValor(valor);

		return transacao;
	}

	private TransacaoDeTransferencia criaTransacaoDeTransferencia(double valor) {
		TransacaoDeTransferencia transacao = new TransacaoDeTransferencia();
		transacao.setCanalDeAtendimento(CanalDeAtendimento.INTERNET_BANKING);
		transacao
				.setTipo(TipoDaTransacao.TRANSFERENCIA_ENTRE_CONTAS);
		transacao.setAgencia(3);
		transacao.setConta(3);
		transacao.setValor(valor);
		transacao.setAgenciaFavorecida(4);
		transacao.setContaFavorecida(4);

		return transacao;
	}

	private TransacaoDeInvestimentoEmFundo criaTransacaoDeInvestimentoEmFundo(
			double valor, TipoDoFundo tipoDoFundo) {
		TransacaoDeInvestimentoEmFundo transacao = new TransacaoDeInvestimentoEmFundo();

		transacao.setCanalDeAtendimento(CanalDeAtendimento.INTERNET_BANKING);
		transacao.setTipo(TipoDaTransacao.INVESTIMENTO_EM_FUNDO);
		transacao.setAgencia(3);
		transacao.setConta(3);
		transacao.setValor(valor);
		transacao.setTipoDoFundo(tipoDoFundo);

		return transacao;
	}
}