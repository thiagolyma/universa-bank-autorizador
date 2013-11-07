package br.org.universa.bank.autorizador.negocio.fundos;

public class Conservador implements FundoDeInvestimento {

	@Override
	public double calculaRentabilidade(double valor) {
		double rentabilidade = (valor*0.008);
		return rentabilidade;
	}

}
