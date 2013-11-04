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

public abstract class AbstractTransacaoMediator {

	protected Autorizacao autorizacao;

	protected AbstractTransacaoMediator() {
	}

	public Autorizacao executa(Transacao transacao) {
		Autorizacao autorizacao = new Autorizacao();
		autorizacao.setTransacao(transacao);
		try{
			executaRegrasEspecificas(transacao);
			autorizacao.autorizada();
		}catch(Exception e){
			autorizacao.negada(e.getMessage());
		}
		return autorizacao;
	}
	//Usando padrão templat metodo
	protected abstract void executaRegrasEspecificas(Transacao transacao) throws Exception;
}