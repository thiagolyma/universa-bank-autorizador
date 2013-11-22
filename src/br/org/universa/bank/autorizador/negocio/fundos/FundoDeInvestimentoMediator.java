package br.org.universa.bank.autorizador.negocio.fundos;

public class FundoDeInvestimentoMediator {

	private static FundoDeInvestimentoMediator instancia = null;
	private FundoDeInvestimentoMediator(){

	}
	public static FundoDeInvestimentoMediator get() {
		if (instancia == null){
			instancia= new FundoDeInvestimentoMediator();
		}
		return instancia;
	}

	public double calculaRentabilidade(TipoDoFundo tipo, double valor){
		FundoDeInvestimento fundo = criaFundoDeInvestimento(tipo);
		double rentabilidadeBruta = fundo.calculaRentabilidade(valor);
		double rentabilidadeLiquida = rentabilidadeBruta * 0.75;
		return rentabilidadeLiquida;
	}
	
	private FundoDeInvestimento criaFundoDeInvestimento(TipoDoFundo tipo){
		FundoDeInvestimento fundo= FundoDeInvestimentoFactory.get().cria(tipo);
		return fundo;
	}
}
