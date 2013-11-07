package br.org.universa.bank.autorizador.negocio.fundos;

public class Moderado implements FundoDeInvestimento {

	@Override
	public double calculaRentabilidade(double valor) {
		double probabilidade = Math.random();
		double rentabilidade = 0;
		if (probabilidade <= 0.) {
			rentabilidade = (valor * 0.006);
		} else {
			rentabilidade = (valor * 0.015);
		}
		return rentabilidade;
	}

}
