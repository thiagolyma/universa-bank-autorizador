package br.org.universa.bank.autorizador.negocio.fundos;

public class Agressivo implements FundoDeInvestimento {

	@Override
	public double calculaRentabilidade(double valor) {
		double probabilidade = Math.random();
		double rentabilidade = 0;
		if (probabilidade <= 0.2){
			rentabilidade = (valor * 0.02);
		}else if (probabilidade <= 0.3){
			rentabilidade = (valor * 0.012);
		}else if (probabilidade >= 0.5){
			rentabilidade = (valor * 0.004);
		}
		return rentabilidade;
	}

}
