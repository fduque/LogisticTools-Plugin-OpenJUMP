package br.puc.rio.utilities;

import java.util.List;

import org.apache.commons.math3.distribution.NormalDistribution;

public class Matematica {
	
	/*
	 * Essa classe possui métodos matemáticos padronizados que são usados por métodos de outras classes.
	 * 
	 */	
	

	public static double desvioPadrao(List<Double> objetos) {
		if (objetos.size() == 1) {
			return 0.0;
		} else {
			double mediaAritimetica = mediaAritimetica(objetos);
			double somatorio = 0l;
			for (int i = 0; i < objetos.size(); i++) {
				double result = objetos.get(i) - mediaAritimetica;
				somatorio = somatorio + result * result;
			}
			return Math.sqrt(((double) 1 / (objetos.size() - 1)) * somatorio);
		}
	}
	
	public static int calcQtdCombinacoes (int qtdElementosAcombinar,int qtdPosicoes) {
		
	return Matematica.calcFatorial(qtdElementosAcombinar)/(Matematica.calcFatorial(qtdPosicoes)*Matematica.calcFatorial(qtdElementosAcombinar-qtdPosicoes));
			
	}
	public static int calcFatorial (int numero) {
		int resposta = numero;
		for(int i=numero-1;i>0;i--){
			resposta = i * resposta;
			System.out.println("Dentro"+resposta);
		
		}
		System.out.println(resposta);
		return resposta;
	}

	public static double variancia(List<Double> objetos) {
		return Math.pow(desvioPadrao(objetos), 2);
	}

	public static double mediaAritimetica(List<Double> objetos) {
		double somatorio = 0l;
		for (Double d : objetos) {
			somatorio += d;
		}
		return somatorio / objetos.size();
	}

	/*
	 * Esse método retorna o valor de probabilidade considerando uma distribuição normal.
	 */		
public static double calcFunEtaAcum(double valorEta) { // achando o valor de
															// f de eta a partir
															// de eta
		// Baseado na API MATH da Apache
		NormalDistribution nd = new NormalDistribution();
		return 1 - nd.cumulativeProbability(valorEta);
	}

/*
 * Esse método retorna inverso do método calcFunEtaAcum. Ele retorna o valor de probabilidade acumulado inverso de uma distribuição normal.
 */	
	public static double calcEta(double valorFuncaoDeEta) { // é o calculo
															// inverso da funcao
															// anterior
		// Baseado na API MATH da Apache
		NormalDistribution nd = new NormalDistribution();
		return nd.inverseCumulativeProbability(valorFuncaoDeEta);
	}

}
