package br.puc.rio.zona;

import org.python.modules.math;

import br.puc.rio.utilities.Matematica;

public class CalcNivelServico {

	// variaveis carregadas via formulario
	Zona zona;
	double capacidadeVeiculo;
	double tempoJornadaNormal;
	double tempoJornadaMaximo;
	double perdaEstiva;
	// variaveis calculadas na classe
	double etaPorTempoHnormal;
	double etaPorTempoMaximo;
	double etaPorCapacidade;
	double nsPorCapacidade;
	double nsPorTempoNormal;
	double nsPorTempoMaximo;
	double melhorNSzona;
	String melhorTipoNSzona;

	/*
Essa classe � especializada para calcular o n�vel de servi�o a partir dos par�metros de zona definidos.
Ela calcula o n�vel de servi�o por capacidade ou por tempo, sendo tempo de jornada normal ou de jornada m�ximo (com horas extras).
		*/	
	
	
	/**
	 * @param zona
	 * @param capacidadeVeiculo
	 * @param tempoJornadaNormal
	 * @param tempoJornadaMaximo
	 * @param perdaEstiva
	 */

	public CalcNivelServico(Zona zona, double capacidadeVeiculo,
			double tempoJornadaNormal, double tempoJornadaMaximo,
			double perdaEstiva) {
		super();
		this.zona = zona;
		this.capacidadeVeiculo = capacidadeVeiculo;
		this.tempoJornadaNormal = tempoJornadaNormal;
		this.tempoJornadaMaximo = tempoJornadaMaximo;
		this.perdaEstiva = perdaEstiva;

		this.calcMelhorNS(); // esse metodo exigira calculos que irao carregar
								// todas as variaveis ainda nao carregadas da
								// classe
	}

	public double porCapacidade() { // calcula o nivel de servico da zona por
									// capacidade

		double valorR = 1 - this.perdaEstiva;
		double eW = this.zona.getMediaN()*this.zona.getMediaCarga();
		double varU = this.zona.getVarCarga();
		double varW = this.zona.getMediaN()*(Math.pow(this.zona.getMediaCarga(),2)+varU);
		double desvpadW = math.sqrt(varW);
		double rCAP = valorR*capacidadeVeiculo;
		this.etaPorCapacidade = (rCAP-eW)/desvpadW;
		this.nsPorCapacidade = 1 - Matematica
				.calcFunEtaAcum(this.etaPorCapacidade);
		System.out.println("NS Por capacidade: " + this.nsPorCapacidade
				+ " valorETA: " + this.etaPorCapacidade);
		return this.nsPorCapacidade;
	}

	public double porTempoHnormal() {// calcula o nivel de servico da zona por
										// tempo da jornada normal

		this.etaPorTempoHnormal = (this.tempoJornadaNormal - this.zona
				.getMediaTC()) / this.zona.getDesvPadTC();
		this.nsPorTempoNormal = 1 - Matematica
				.calcFunEtaAcum(this.etaPorTempoHnormal);
		System.out.println("NS Por tempoNormal: " + this.nsPorTempoNormal
				+ " valorETA: " + this.etaPorTempoHnormal);
		return this.nsPorTempoNormal;
	}

	public double porTempoHmaximo() {// calcula o nivel de servico da zona por
										// tempo da jornada maxima. Ou seja, com
										// horas extras.

		this.etaPorTempoMaximo = (this.tempoJornadaMaximo - this.zona
				.getMediaTC()) / this.zona.getDesvPadTC();
		this.nsPorTempoMaximo = 1 - Matematica
				.calcFunEtaAcum(this.etaPorTempoMaximo);
		System.out.println("NS Por tempoMaximo: " + this.nsPorTempoMaximo
				+ " valorETA: " + this.etaPorTempoMaximo);
		return this.nsPorTempoMaximo;
	}

	public double calcMelhorNS() { // verifica qual e o melhor = MAIOR nivel de
									// servico da zona: por capacidade, por
									// temponormal ou portempomaximo.

		if (porTempoHmaximo() >= porTempoHnormal()) {
			this.melhorTipoNSzona = "PorTempoHmaximo";
			this.melhorNSzona = this.nsPorTempoMaximo;
		} else {
			this.melhorTipoNSzona = "PorTempoHnormal";
			this.melhorNSzona = this.nsPorTempoNormal;
		}

		if (porCapacidade() >= melhorNSzona) {
			this.melhorTipoNSzona = "PorCapacidade";
			this.melhorNSzona = this.nsPorCapacidade;
		}

		System.out.println("Melhor NS: " + this.melhorTipoNSzona + " Valor: "
				+ this.melhorNSzona);
		return this.melhorNSzona;
	}

	public double getNsPorCapacidade() {
		return nsPorCapacidade;
	}

	public void setNsPorCapacidade(double nsPorCapacidade) {
		this.nsPorCapacidade = nsPorCapacidade;
	}

	public double getNsPorTempoNormal() {
		return nsPorTempoNormal;
	}

	public void setNsPorTempoNormal(double nsPorTempoNormal) {
		this.nsPorTempoNormal = nsPorTempoNormal;
	}

	public double getNsPorTempoMaximo() {
		return nsPorTempoMaximo;
	}

	public void setNsPorTempoMaximo(double nsPorTempoMaximo) {
		this.nsPorTempoMaximo = nsPorTempoMaximo;
	}

}
