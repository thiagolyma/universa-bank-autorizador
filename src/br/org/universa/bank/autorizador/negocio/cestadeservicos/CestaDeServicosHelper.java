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
package br.org.universa.bank.autorizador.negocio.cestadeservicos;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.org.universa.bank.autorizador.negocio.conta.Conta;
import br.org.universa.bank.autorizador.negocio.transacao.TipoDaTransacao;
import br.org.universa.bank.autorizador.negocio.transacao.Transacao;
import br.org.universa.bank.autorizador.negocio.transacao.TransacaoMediator;

public class CestaDeServicosHelper {

	private static CestaDeServicosHelper instancia = null;
	private static Map<TipoDaCestaDeServicos, List<ItemDaCestaDeServicos>> cestas = new HashMap<TipoDaCestaDeServicos, List<ItemDaCestaDeServicos>>();

	{
		List<ItemDaCestaDeServicos> itensDaCestaDeServicosBasica = new ArrayList<ItemDaCestaDeServicos>();

		itensDaCestaDeServicosBasica.add(new ItemDaCestaDeServicos(
				TipoDaTransacao.SAQUE_EM_CONTA, 2, 1.00));
		itensDaCestaDeServicosBasica.add(new ItemDaCestaDeServicos(
				TipoDaTransacao.TRANSFERENCIA_ENTRE_CONTAS, 1, 1.00));
		itensDaCestaDeServicosBasica.add(new ItemDaCestaDeServicos(
				TipoDaTransacao.DOC_TED_ENTRE_BANCOS, 1, 7.00));

		cestas.put(TipoDaCestaDeServicos.BASICA, itensDaCestaDeServicosBasica);

		List<ItemDaCestaDeServicos> itensDaCestaDeServicosEspecial = new ArrayList<ItemDaCestaDeServicos>();

		itensDaCestaDeServicosEspecial.add(new ItemDaCestaDeServicos(
				TipoDaTransacao.SAQUE_EM_CONTA, 3, 0.50));
		itensDaCestaDeServicosEspecial.add(new ItemDaCestaDeServicos(
				TipoDaTransacao.TRANSFERENCIA_ENTRE_CONTAS, 2, 0.50));
		itensDaCestaDeServicosEspecial.add(new ItemDaCestaDeServicos(
				TipoDaTransacao.DOC_TED_ENTRE_BANCOS, 1, 5.50));

		cestas.put(TipoDaCestaDeServicos.ESPECIAL,
				itensDaCestaDeServicosEspecial);
	}

	private CestaDeServicosHelper() {
		// Construtor privado
	}

	public static CestaDeServicosHelper get() {
		if (instancia == null) {
			instancia = new CestaDeServicosHelper();
		}

		return instancia;
	}

	public boolean isTransacaoPassivelDeTarifacao(Conta conta,
			Transacao transacao) {
		if (cestas.containsKey(conta.getTipoDaCestaDeServicos())) {
			List<ItemDaCestaDeServicos> itensDaCesta = cestas.get(conta
					.getTipoDaCestaDeServicos());

			for (ItemDaCestaDeServicos itemDaCestaDeServicos : itensDaCesta) {
				if (itemDaCestaDeServicos.getTipoDaTransacao().equals(
						transacao.getTipo())) {
					return true;
				}
			}
		}

		return false;
	}

	public boolean isExcedeQuantidadeContratada(Conta conta, Transacao transacao) {
		Date dataDeReferencia = new Date();

		List<Transacao> transacoes = TransacaoMediator.get()
				.consultaTransacoesDaContaNoDiaPorTipoDaTransacao(conta,
						dataDeReferencia, transacao.getTipo());

		if (transacoes.size() + 1 > CestaDeServicosHelper.get()
				.consultaQuantidadeContratada(conta.getTipoDaCestaDeServicos(),
						transacao)) {
			return true;

		}

		return false;
	}

	public int consultaQuantidadeContratada(
			TipoDaCestaDeServicos tipoDaCestaDeServicos, Transacao transacao) {

		if (cestas.containsKey(tipoDaCestaDeServicos)) {
			List<ItemDaCestaDeServicos> itensDaCesta = cestas
					.get(tipoDaCestaDeServicos);

			for (ItemDaCestaDeServicos itemDaCestaDeServicos : itensDaCesta) {
				if (itemDaCestaDeServicos.getTipoDaTransacao().equals(
						transacao.getTipo())) {
					return itemDaCestaDeServicos.getQuantidadeContratada();
				}
			}
		}

		return 0;
	}

	public double consultaTarifaExcedente(Conta conta, Transacao transacao) {

		if (cestas.containsKey(conta.getTipoDaCestaDeServicos())) {
			List<ItemDaCestaDeServicos> itensDaCesta = cestas.get(conta
					.getTipoDaCestaDeServicos());

			for (ItemDaCestaDeServicos itemDaCestaDeServicos : itensDaCesta) {
				if (itemDaCestaDeServicos.getTipoDaTransacao().equals(
						transacao.getTipo())) {
					return itemDaCestaDeServicos.getTarifaPorExcedente();
				}
			}
		}

		return 0.0;
	}
}