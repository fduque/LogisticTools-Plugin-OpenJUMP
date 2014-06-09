package br.puc.rio.pontoCentral;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class CalcPCentralRetangularDerivada {

	private Double xfinal; // variável que possui o valor ótimo para o eixo X
	private Double yfinal; // variável que possui o valor ótimo para o eixo Y

	public void calcule(List<String> list) // método que calcula o ponto central
											// RETANGULAR a partir de um grupo
											// de pontos e pesos definidos
	{

		double DIF = 0;
		int qtdPontos = list.size(); // verificando a quantidade de pontos na
										// List recebida
		int qtdAtributosPorPonto = 3; // Os 3 atributos de um ponto são: Valor
										// do eixo X, valor do eixo Y e peso do
										// ponto.
		Double[][] dados = new Double[qtdPontos][qtdAtributosPorPonto]; // passando
																		// as
																		// informações
																		// da
																		// List
																		// para
																		// um
																		// array
																		// bidimensional

		int contador = 0;
		Iterator<String> iterar = list.iterator();

		// preenchendo o array dados com as informações da List
		while (iterar.hasNext()) {
			String[] linha = ((String) iterar.next()).split(";");

			// Alimentando o array principal
			dados[contador][0] = Double.valueOf(linha[1]); // passando atributo
															// valor eixo X
			dados[contador][1] = Double.valueOf(linha[2]); // passando atributo
															// valor eixo Y
			dados[contador][2] = Double.valueOf(linha[3]); // passando atributo
															// peso do ponto

			// Calculando o DIF = Somatório dos Pesos dos pontos multiplicado
			// por "-1"
			DIF = DIF + dados[contador][2];

			contador++;
		}

		DIF = -1 * (DIF); // Multiplicando o valor calculado do DIF por "-1".

		System.out.println("O valor do DIF: " + DIF);
		System.out.println("Organização da Lista Antes da Ordenação:");
		for (int m = 0; m < dados.length; m++) // imprimindo o array
		{
			System.out.println(dados[m][0] + ", " + dados[m][1] + ", "
					+ dados[m][2]);
		}

		// Parte 1 - Calculando o valor ótimo para o eixo X

		// ordenando o array por ordem de valor crescente do eixo x
		Arrays.sort(dados, new Comparator<Double[]>() {
			@Override
			public int compare(Double[] o1, Double[] o2) {
				return o1[0].compareTo(o2[0]); // ordenando em ordem crescente
												// pelo eixo X
			}
		});

		System.out
				.println("Organização da Lista Depois da Ordenação pelo eixo X:");
		for (int m = 0; m < dados.length; m++) // imprimindo a lista
		{
			System.out.println(dados[m][0] + ", " + dados[m][1] + ", "
					+ dados[m][2]);
		}

		int i = 0;
		double valorDIFparaX = DIF;
		while (valorDIFparaX < 0) // enquanto o valorDIFdeX for menor que
									// zero... Quando o valorDIFparaX for igual
									// ou maior que zero, significa que o
									// candidato "i" nessa iteração é melhor ao
									// eixo X.
		{
			valorDIFparaX = valorDIFparaX + (2 * (dados[i][2])); // iterando
			xfinal = dados[i][0]; // armazenando o valor candidato "i" ao eixo X
									// caso a condição do While seja atendida.
			i++;
		}
		System.out.println("MelhorX tem o valor de: " + xfinal);

		// Parte 2 - Calculando o valor ótimo para o eixo y

		// ordenando o array por ordem de valor crescente do eixo Y
		Arrays.sort(dados, new Comparator<Double[]>() {
			@Override
			public int compare(Double[] o1, Double[] o2) {
				return o1[1].compareTo(o2[1]); // ordenando em ordem crescente
												// pelo eixo Y
			}
		});

		System.out
				.println("Organização da Lista Depois da Ordenação pelo eixo Y:");
		for (int m = 0; m < dados.length; m++) // imprimindo a lista
		{
			System.out.println(dados[m][0] + ", " + dados[m][1] + ", "
					+ dados[m][2]);
		}

		int j = 0;
		double valorDIFparaY = DIF;
		while (valorDIFparaY < 0) // enquanto o valorDIFdeX for menor que
									// zero... Quando o valorDIFparaY for igual
									// ou maior que zero, significa que o
									// candidato "i" nessa iteração é melhor ao
									// eixo Y.
		{
			valorDIFparaY = valorDIFparaY + (2 * (dados[j][2])); // iterando
			yfinal = dados[j][1]; // armazenando o valor candidato "i" ao eixo Y
									// caso a condição do While seja atendida.
			j++;
		}
		System.out.println("MelhorY tem o valor de: " + yfinal);
	}

	public String getResultX() {
		java.text.NumberFormat nf = java.text.NumberFormat.getNumberInstance();
		nf.setMinimumFractionDigits(2);
		nf.setMaximumFractionDigits(2);
		String xf = nf.format(xfinal);
		return xf;
	}

	public String getResultY() {
		java.text.NumberFormat nf = java.text.NumberFormat.getNumberInstance();
		nf.setMinimumFractionDigits(2);
		nf.setMaximumFractionDigits(2);
		String yf = nf.format(yfinal);
		return yf;
	}

	public double getResultXDoub() {
		return xfinal;
	}

	public double getResultYDoub() {
		return yfinal;
	}
}
