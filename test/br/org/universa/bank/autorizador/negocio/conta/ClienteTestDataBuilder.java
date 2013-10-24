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

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.org.universa.bank.autorizador.negocio.conta.Cliente;

public class ClienteTestDataBuilder {

	private Cliente cliente = null;

	static {
		Fixture.of(Cliente.class).addTemplate("valido", new Rule() {
			{
				add("cpf", random("77276469115", "04693433110"));
				add("nome",
						random("Santos Dummont", "Clarice Lispector",
								"Carlos Drummont de Andrade", "Padre Cícero",
								"Dom Heder Câmara"));
			}
		});
	}

	public ClienteTestDataBuilder() {
		cliente = Fixture.from(Cliente.class).gimme("valido");
	}

	public ClienteTestDataBuilder comCpf(String cpf) {
		cliente.setCpf(cpf);

		return this;
	}

	public ClienteTestDataBuilder deNome(String nome) {
		cliente.setNome(nome);

		return this;
	}

	public Cliente constroi() {
		return cliente;
	}
}
