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

import java.util.ArrayList;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.org.universa.bank.autorizador.negocio.cestadeservicos.TipoDaCestaDeServicos;
import br.org.universa.bank.autorizador.negocio.conta.Cliente;
import br.org.universa.bank.autorizador.negocio.conta.Conta;
import br.org.universa.bank.autorizador.negocio.conta.LancamentoDaConta;

public class ContaTestDataBuilder {

	private Conta conta = null;

	static {
		Fixture.of(Conta.class).addTemplate("valida", new Rule() {
			{
				add("agencia", random(1234, 3456, 6789));
				add("numero", random(Integer.class, range(1, 10000)));
				add("titular", new ClienteTestDataBuilder().constroi());
				add("tipoDaCestaDeServicos",
						random(TipoDaCestaDeServicos.BASICA,
								TipoDaCestaDeServicos.ESPECIAL));
				add("lancamentosDaConta", new ArrayList<LancamentoDaConta>());
			}
		});

		Fixture.of(Conta.class).addTemplate("conta-basica")
				.inherits("valida", new Rule() {
					{
						add("tipoDaCestaDeServicos",
								TipoDaCestaDeServicos.BASICA);
					}
				});

		Fixture.of(Conta.class).addTemplate("conta-especial")
				.inherits("valida", new Rule() {
					{
						add("tipoDaCestaDeServicos",
								TipoDaCestaDeServicos.ESPECIAL);
					}
				});
	}

	public ContaTestDataBuilder() {
		conta = Fixture.from(Conta.class).gimme("valida");
	}

	public ContaTestDataBuilder contaBasica() {
		conta = Fixture.from(Conta.class).gimme("conta-basica");

		return this;
	}

	public ContaTestDataBuilder contaEspecial() {
		conta = Fixture.from(Conta.class).gimme("conta-especial");

		return this;
	}

	public ContaTestDataBuilder deNumero(int numero) {
		conta.setNumero(numero);

		return this;
	}

	public ContaTestDataBuilder daAgencia(int agencia) {
		conta.setAgencia(agencia);

		return this;
	}

	public ContaTestDataBuilder paraOTitular(Cliente titular) {
		conta.setTitular(titular);

		return this;
	}

	public ContaTestDataBuilder comSaldo(double saldo) {
		conta.saldo = saldo;

		return this;
	}

	public ContaTestDataBuilder doTipo(
			TipoDaCestaDeServicos tipoDaCestaDeServicos) {
		conta.setTipoDaCestaDeServicos(tipoDaCestaDeServicos);

		return this;
	}

	public Conta constroi() {
		return conta;
	}
}