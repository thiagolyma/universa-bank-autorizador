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

import br.org.universa.bank.autorizador.negocio.autorizacao.Autorizacao;
import br.org.universa.bank.autorizador.negocio.comum.Mensagens;
import br.org.universa.bank.autorizador.negocio.comum.UtilHelper;
import br.org.universa.bank.autorizador.negocio.conta.Conta;
import br.org.universa.bank.autorizador.negocio.conta.ContaMediator;
import br.org.universa.bank.autorizador.negocio.conta.LancamentoDaConta;
import br.org.universa.bank.autorizador.negocio.conta.TipoDoLancamento;
import br.org.universa.bank.autorizador.negocio.tarifacao.TarifacaoMediator;
import br.org.universa.bank.autorizador.persistencia.dao.ContaDAO;
import br.org.universa.bank.autorizador.servico.SPBAdapter;

public abstract class AbstractTransacaoMediator {

	protected Autorizacao autorizacao;
	private final double VALOR_LIMITE_PARA_INFORMAR_SPB = 3000;

	protected AbstractTransacaoMediator() {
	}

	public Autorizacao executa(Transacao transacao) {
		Autorizacao autorizacao = new Autorizacao();
		autorizacao.setTransacao(transacao);
		try{
			validaTransacao(transacao);
			executaRegrasEspecificas(transacao);
			TransacaoMediator.get().insereTransacaoDaConta(transacao);
			notificaTransacaoSPB(transacao);
			tarifaTransacao(transacao);
			autorizacao.autorizada();
		}catch(Exception e){
			autorizacao.negada(e.getMessage());
		}
		return autorizacao;
	}

	private void validaTransacao(Transacao transacao) throws Exception{
		if(!transacao.validaDados())
			throw new Exception(Mensagens.DADOS_INSUFICIENTES_REALIZAR_TRANSACAO);
	}
	private void notificaTransacaoSPB(Transacao transacao) throws Exception{
		if(transacao.getValor() >= VALOR_LIMITE_PARA_INFORMAR_SPB)
			SPBAdapter.get().notificaTransacao(transacao);
	}

	private void tarifaTransacao(Transacao transacao) throws Exception {
		double tarifa = TarifacaoMediator.get().tarifaTransacao(transacao);
		if(tarifa> 0 ){
			//Sensibilizando a conta com a tarifação
			Conta conta = ContaMediator.get().consultaConta(transacao.getAgencia(),transacao.getConta());
			conta.debita(tarifa);
			conta.adicionaLancamentoDaConta(new LancamentoDaConta(TipoDoLancamento.DEBITO,
					"Tarifação - " + transacao.getTipo().getValor(),tarifa));
			ContaDAO.get().atualiza(conta);
		}
	}
	//Usando padrão templat metodo
	protected abstract void executaRegrasEspecificas(Transacao transacao) throws Exception;
}