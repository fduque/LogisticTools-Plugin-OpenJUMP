package br.puc.rio.pontoCentral;

import java.util.Iterator;
import java.util.List;

public class CalcPCentralRetangularFibonacci {
	private double xfinal; // variável que possui o valor ótimo para o eixo X
	private double yfinal; // variável que possui o valor ótimo para o eixo Y
	private double INVERSO_AUREA = 2 / (1 + (Math.sqrt(5))); // é uma constante.Inverso da razão da seção áurea.

	public void calcule(List<String> listPontos, String tipoCalculo,
			String valorDoTipo) // método que calcula o ponto central euclidiano
								// a partir de um grupo de pontos e pesos
								// definidos
	{
		 double pontoR1= 0; // o segmento vSupX - vInfX dividido em 3 partes  pelos pontos R1 e R2
		 double pontoR2= 0; // o segmento vSupX - vInfX dividido em 3 partes pelos pontos R1 e R2
		 double s1= 0;
		 double s2= 0;
		 double g1= 0; // valor da função da soma dos módulos para determinado x ou y
		 double g2= 0; // valor da função da soma dos módulos para determinado x ou y
		 double[] vX; // array para guardar coordenadas x
		 double[] vY; // array para guardar coordenadas y
		 double[] vP; // array para guardar pesos dos pontos P
		 int quantIteracoes = 0; // quantidade de iterações inseridas pelas usuario
		 double vSupX= 0;
		 double vInfX = 0;
		 double vSupY= 0;
		 double vInfY= 0;
		// verificando a quantidade de pontos dentro da List
		int quantidadePontos = listPontos.size();

		// inicializando arrays double para armazenar os eixos e peso de cada
		// ponto, de acordo com a qtd de pontos recebidos
		vX = new double[quantidadePontos];
		vY = new double[quantidadePontos];
		vP = new double[quantidadePontos];

		// preenchendo os arrays
		int contador = 0;
		Iterator<String> iterar = listPontos.iterator();
		while (iterar.hasNext()) {
			String[] linha = ((String) iterar.next()).split(";");
			linha[1].replaceAll(",", ".");
			vX[contador] = (Double.parseDouble(linha[1]));
			linha[2].replaceAll(",", ".");
			vY[contador] = Double.parseDouble(linha[2]);
			linha[3].replaceAll(",", ".");
			vP[contador] = Double.parseDouble(linha[3]);
			contador++;
		}

		System.out.println("Quantidade de pontos recebidos: "
				+ quantidadePontos);

		// achando menor E o maior valor de X
		for (int i = 0; i < quantidadePontos; i++) {
			if (i == 0) {
				vInfX = vX[i]; // atribuindo um valor inicial as variaves inf e
								// Sup
				vSupX = vX[i];
			}
			if (vX[i] < vInfX) { // guardando o valor se for menor
				vInfX = vX[i];
			}
			if (vX[i] > vSupX) { // guardando o valor se for maior
				vSupX = vX[i];
			}
		}

		System.out.println("Valor extremo inferior de x: " + vSupX);
		System.out.println("Valor extremo superior de x: " + vInfX);

		// achando menor E o maior valor de y
		for (int i = 0; i < quantidadePontos; i++) {
			if (i == 0) {
				vInfY = vY[i]; // atribuindo um valor inicial as variaves inf e
								// Sup
				vSupY = vY[i];
			}
			if (vY[i] < vInfY) { // guardando o valor se for menor
				vInfY = vY[i];
			}
			if (vY[i] > vSupY) { // guardando o valor se for maior
				vSupY = vY[i];
			}
		}

		System.out.println("Valor extremo inferior de Y: " + vSupY);
		System.out.println("Valor extremo superior de Y: " + vInfY);

		// verificando se o calculo será por precisão ou por quantidade de
		// iterações
		if (tipoCalculo == "QUANTIDADE_ITERACOES") {
			quantIteracoes = Integer.parseInt(valorDoTipo);

		} else if (tipoCalculo == "POR_PRECISAO") {
			double qtdIteracoesPorPrecisaoParaX = 0;
			double qtdIteracoesPorPrecisaoParaY = 0;
			double precisaoInserida = Double.valueOf(valorDoTipo);

			qtdIteracoesPorPrecisaoParaX = Math.log(precisaoInserida)
					* Math.log(vSupX - vInfX) / Math.log(INVERSO_AUREA);
			qtdIteracoesPorPrecisaoParaY = Math.log(precisaoInserida)
					* Math.log(vSupY - vInfY) / Math.log(INVERSO_AUREA);

			if (qtdIteracoesPorPrecisaoParaX >= qtdIteracoesPorPrecisaoParaY) {
				quantIteracoes = (int) qtdIteracoesPorPrecisaoParaX;
			} else {
				quantIteracoes = (int) qtdIteracoesPorPrecisaoParaY;
			}
			System.out.println("Quant de iteracoes calculadas ..."
					+ quantIteracoes);

		}

		// Parte1 - Calculando o valor ótimo para o eixo x
		s1 = vInfX;
		s2 = vSupX;
		int contIteracoes = 0;
		while (contIteracoes <= quantIteracoes) {
			System.out.println("Número da Iteracao : " + contIteracoes);
			pontoR1 = ((1 - INVERSO_AUREA) * (s2 - s1)) + s1;
			pontoR2 = (INVERSO_AUREA * (s2 - s1)) + s1;
			System.out.println("Valor Ponto1: " + pontoR1 + " Valor Ponto2: "
					+ pontoR2);

			// calculando o valor da função Soma dos módulos nos pontos R1 e R2
			g1 = 0;
			g2 = 0;
			for (int i = 0; i < quantidadePontos; i++) {

				g1 = g1 + (vP[i] * Math.abs(pontoR1 - vX[i]));
				g2 = g2 + (vP[i] * Math.abs(pontoR2 - vX[i]));
			}
			if (g1 <= g2) {
				s2 = pontoR2;
			} else {
				s1 = pontoR1;
			}
			contIteracoes++;
			System.out.println("Valor das variáveis: " + " g1: " + g1 + " g2: "
					+ g2 + " s1: " + s1 + " s2: " + s2);
		}
		xfinal = (s1 + s2) / 2; // valor ótimo para X
		System.out.println("Valor ótimo eixo X: " + xfinal);

		// Parte 2 - Calculando o valor ótimo para eixo y

		// inicializando e zerando as variáveis para novo uso
		s1 = vInfY;
		s2 = vSupY;
		g1 = 0;
		g2 = 0;
		pontoR1 = 0;
		pontoR2 = 0;
		contIteracoes = 0;
		while (contIteracoes <= quantIteracoes) {
			System.out.println("Número da Iteracao : " + contIteracoes);
			pontoR1 = ((1 - INVERSO_AUREA) * (s2 - s1)) + s1;
			pontoR2 = (INVERSO_AUREA * (s2 - s1)) + s1;

			// calculando o valor da função Soma dos módulos nos pontos R1 e R2
			g1 = 0;
			g2 = 0;
			for (int i = 0; i < quantidadePontos; i++) {
				g1 = g1 + (vP[i] * Math.abs(pontoR1 - vY[i]));
				g2 = g2 + (vP[i] * Math.abs(pontoR2 - vY[i]));
			}
			if (g1 <= g2) {
				s2 = pontoR2;
			} else {
				s1 = pontoR1;
			}
			contIteracoes++;
			System.out.println("Valor das variáveis: " + " g1: " + g1 + " g2: "
					+ g2 + " s1: " + s1 + " s2: " + s2);
		}
		yfinal = (s1 + s2) / 2; // valor ótimo para y
		System.out.println("Valor ótimo eixo Y: " + yfinal);
	}

	public String getResult() {
		java.text.NumberFormat nf = java.text.NumberFormat.getNumberInstance();
		nf.setMinimumFractionDigits(2);
		nf.setMaximumFractionDigits(2);
		String xf = nf.format(xfinal);
		String yf = nf.format(yfinal);
		String resultado = ("X: " + xf + " Y: " + yf);
		return resultado;
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

		