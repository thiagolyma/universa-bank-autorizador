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

import br.com.phd.bank.spb.servico.SPBFacade;
import br.com.phd.bank.spb.servico.TED;
import br.com.phd.bank.spb.servico.TransacaoSPB;
import br.org.universa.bank.autorizador.negocio.comum.Mensagens;
import br.org.universa.bank.autorizador.negocio.conta.Conta;
import br.org.universa.bank.autorizador.negocio.conta.ContaMediator;
import br.org.universa.bank.autorizador.negocio.docted.DocTed;
import br.org.universa.bank.autorizador.negocio.transacao.Transacao;

public class SPBAdapter {

	private static SPBAdapter instancia = null;

	private SPBAdapter() {
		// construtor vazio
	}

	public static SPBAdapter get() {
		if (instancia == null) {
			instancia = new SPBAdapter();
		}

		return instancia;
	}

	public void notificaTransacao(Transacao transacao) throws Exception {
		Conta conta = ContaMediator.get().consultaConta(transacao.getAgencia(),transacao.getConta());
		int retornoSPB = 0;
		TransacaoSPB transacaoSPB = null;
		transacaoSPB = SPBFacade.get().criaTransacaoSPB();
		transacaoSPB.setAgencia(conta.getAgencia());
		transacaoSPB.setBanco(1);
		transacaoSPB.setConta(conta.getNumero());
		transacaoSPB.setCpfDoTitular(conta.getTitular().getCpf());
		transacaoSPB.setIdentificadorDaTransacaoDeOrigem(transacao.getIdentificador());
		transacaoSPB.setTipoDaTransacao(transacao.getTipo().getTipoDoLancamento().getChave());
		transacaoSPB.setValor(transacao.getValor());
		
		retornoSPB = SPBFacade.get().notificaTransacao(transacaoSPB);
		switch(retornoSPB)
		{
		case 1:
			throw new Exception(Mensagens.DADOS_INSUFICIENTES_SPB);
		case 2:
			throw new Exception(Mensagens.CPF_TITULAR_CONTA_INVALIDO);
		}
	}

	@SuppressWarnings("unused")
	private void analisaCodigoDeRetornoDoSPB(int codigoDeRetorno)
			throws Exception {
		switch (codigoDeRetorno) {

		default:
			break;
		}
	}

	public void registraTED(DocTed docTed) throws Exception {
		TED tedSPB = SPBFacade.get().criaTED();
		tedSPB.setAgenciaDeOrigem(docTed.getAgenciaDeOrigem());
		tedSPB.setAgenciaFavorecida(docTed.getAgenciaFavorecida());
		tedSPB.setBancoDeOrigem(docTed.getBancoDeOrigem());
		tedSPB.setBancoFavorecido(docTed.getBancoFavorecido());
		tedSPB.setContaDeOrigem(docTed.getContaDeOrigem());
		tedSPB.setContaFavorecida(docTed.getContaFavorecida());
		tedSPB.setCpfDaContaDeOrigem(docTed.getCpfDoTitularDaContaDeOrigem());
		tedSPB.setCpfDaContaFavorecida(docTed.getCpfDoTitularDaContaFavorecida());
		tedSPB.setIdDaTransacaoDeOrigem(docTed.getIdentificadorDaTransacao());
		tedSPB.setTipoDaTED(docTed.getTipoDoDocTed().getValor().charAt(0));
		tedSPB.setValorDaTransacao(docTed.getValor());
		int retornoSPB = SPBFacade.get().registraTED(tedSPB);
		switch(retornoSPB)
		{
		case 1:
			throw new Exception(Mensagens.DADOS_INSUFICIENTES_REGISTRO_TED);
		case 2:
			throw new Exception(Mensagens.CPF_TITULAR_CONTA_FAVORECIDA_INVALIDO);
		case 3:
			throw new Exception(Mensagens.TIPO_TED_INVALIDA);
		case 4:
			throw new Exception(Mensagens.VALOR_MENOR_QUE_PERMITIDO_TED);
		}
	}

	@SuppressWarnings("unused")
	private void analisaCodigoDeRetornoDeTedDoSPB(int codigoDeRetorno)
			throws Exception {
		switch (codigoDeRetorno) {

		default:
			break;
		}
	}
}
