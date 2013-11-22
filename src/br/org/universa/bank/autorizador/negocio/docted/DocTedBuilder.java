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
package br.org.universa.bank.autorizador.negocio.docted;


public class DocTedBuilder {

	private static DocTedBuilder instancia = null;
	private DocTed docTed= new DocTed();
	private DocTedBuilder() {
		// construtor vazio
	}

	public static DocTedBuilder get() {
		if (instancia == null) {
			instancia = new DocTedBuilder();
		}

		return instancia;
	}
	public DocTedBuilder comIdentificador(String identificador){
		docTed.setIdentificadorDaTransacao(identificador);
		return this;
	}
	public DocTedBuilder doBancoDeOrigem(int banco){
		docTed.setBancoDeOrigem(banco);
		return this;
	}
	
	public DocTedBuilder daAgenciaDeOrigem(int agencia){
		docTed.setAgenciaDeOrigem(agencia);
		return this;
	}
	public DocTedBuilder daContaDeOrigem(int conta){
		docTed.setContaDeOrigem(conta);
		return this;
	}
	
	public DocTedBuilder comCPFDoTitularDaContaDeOrigem(String cpf){
		docTed.setCpfDoTitularDaContaDeOrigem(cpf);
		return this;
	}
	
	public DocTedBuilder comCPFDoTitularDaContaFavorecida(String cpf){
		docTed.setCpfDoTitularDaContaFavorecida(cpf);
		return this;
	}
	public DocTed constroi() throws Exception {
		return docTed;
				
	}

}