package br.puc.rio.zona;

import br.puc.rio.utilities.Matematica;

public class CalcAreaServico {

	private Zona zona;
	private double coefAjustDistancia;
	private double coefAjustTempo;
	private double areaServPorTempo;
	private double areaServPorCarga;
	private double tempoJornadaMinutos;
	private double tempoMedioInOutZonaMinutos;
	private double nivelDeServico;
	private double capacidadeVeiculo;

	
	/*
Essa classe � especializada para calcular a �rea admiss�vel por tempo ou por capacidade f�sica (carga).
		*/	
	
	/**
	 * @param zona
	 * @param coefAjustDistancia
	 * @param coefAjustTempo
	 * @param areaServPorTempo
	 * @param areaServPorCarga
	 * @param tempoJornadaMinutos
	 * @param tempoMedioInOutZonaMinutos
	 * @param varTempoInOutZonaMinutos
	 * @param nivelDeServico
	 */
	public CalcAreaServico(Zona zona, double coefAjustDistancia,
			double coefAjustTempo, double tempoJornadaMinutos,
			double tempoMedioInOutZonaMinutos, double nivelDeServico,
			double capacidadeVeiculo) {
		super();
		this.zona = zona;
		this.coefAjustDistancia = coefAjustDistancia;
		this.coefAjustTempo = coefAjustTempo;
		this.tempoJornadaMinutos = tempoJornadaMinutos;
		this.tempoMedioInOutZonaMinutos = tempoMedioInOutZonaMinutos;
		this.nivelDeServico = 1 - nivelDeServico; // ATENCAO!!!! - Foi colocado
													// o -1 na frente pois sera
													// usada a funcao
													// acumulada!!!!
		this.capacidadeVeiculo = capacidadeVeiculo;

		this.calcAreaPorTempo();
		this.calcAreaPorCarga();
	}

	public double calcK1() {
		double k1 = ((Math.sqrt(zona.getLambda()) * coefAjustDistancia * 60) / zona
				.getVeloMedia()) + (zona.getLambda() * zona.getMediatparada());
		System.out.println("Valor de k1: " + k1);
		return k1;
	}

	public double calcK2() {
		double k2 = (coefAjustTempo * 3600)
				/ (Math.pow(zona.getVeloMedia(), 2))
				+ (zona.getLambda() * (Math.pow(zona.getMediatparada(), 2) + zona
						.getVartparada()));
		System.out.println("Valor de k2: " + k2);
		return k2;
	}

	public double calcAreaPorTempo() {
		System.out
				.println("Iniciando Calculo de Area Admissivel por Tempo... ");
		double etaNiveldeServico = Math.abs(Matematica.calcEta(nivelDeServico));
		System.out.println("Valor eta: " + etaNiveldeServico);
		double k1 = calcK1();
		double k2 = calcK2();
		double a = Math.pow(k1, 2);
		System.out.println("Valor de a: " + a);
		double b = -1
				* ((2 * k1 * (tempoJornadaMinutos - 2*tempoMedioInOutZonaMinutos)) + (Math
						.pow(etaNiveldeServico, 2) * k2));
		System.out.println("Valor de b: " + b);
		double c = (Math.pow(
				(tempoJornadaMinutos - 2*tempoMedioInOutZonaMinutos), 2))
				- ((Math.pow(etaNiveldeServico, 2) * 2 * zona.getVartparada()));
		System.out.println("Valor de c: " + c);
		areaServPorTempo = (-b - (Math.sqrt((Math.pow(b, 2) - (4 * a * c)))))
				/ (2 * a);
		System.out.println("Area por Tempo: " + areaServPorTempo);
		return areaServPorTempo;
	}

	public double getAreaServPorTempo() {
		return areaServPorTempo;
	}

	public double getAreaServPorCarga() {
		return areaServPorCarga;
	}

	public double calcAreaPorCarga() {
		System.out
				.println("Iniciando Calculo de Area Admissivel por Carga... ");
		double etaNiveldeServico = Matematica.calcEta(nivelDeServico);
		System.out.println("Valor eta: " + etaNiveldeServico);
		double a = Math.pow(zona.getMediaCarga(), 2);
		System.out.println("Valor de a: " + a);
		double b = -1
				* (Math.pow(etaNiveldeServico, 2)
						* (zona.getVarCarga() + Math.pow(zona.getMediaCarga(),
								2)) + (2 * capacidadeVeiculo * zona
						.getMediaCarga()));
		System.out.println("Valor de b: " + b);
		double c = Math.pow(capacidadeVeiculo, 2);
		System.out.println("Valor de c: " + c);
		double N = (-b - (Math.sqrt((Math.pow(b, 2) - (4 * a * c))))) / (2 * a);
		System.out.println("Valor de N: " + N);
		areaServPorCarga = N / zona.getLambda();
		System.out.println("Area por Carga: " + areaServPorCarga);
		return areaServPorCarga;
	}

}
