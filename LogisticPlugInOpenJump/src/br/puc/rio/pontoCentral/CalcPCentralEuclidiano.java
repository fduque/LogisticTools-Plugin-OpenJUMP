package br.puc.rio.pontoCentral;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class CalcPCentralEuclidiano 
 {
	private double xfinal; // variável que possui o valor ótimo para o eixo X
	private double yfinal; // variável que possui o valor ótimo para o eixo Y



	/*
	Esse metodo recebe a lista de pontos inseridas pelo usuário e a precisão requerida e executa o
	 método de Weisfield para encontrar o ponto central. Ao encontrar o ponto central, 
	 o método também carrega as variáveis xfinal e yfinal com as respostas.
	*/
	public void calcule(List<String> listPontos, String valorPrecisao) // método
																		// que
																		// calcula
																		// o
																		// ponto
																		// central
																		// euclidiano
																		// a
																		// partir
																		// de um
																		// grupo
																		// de
																		// pontos
																		// e
																		// pesos
																		// definidos
	{
		 double[] vX; // array para guardar coordenadas x
		 double[] vY; // array para guardar coordenadas y
		 double[] vP; // array para guardar pesos dos pontos P
		 double vTPX = 0; // somatório do produto PesodoPonto * CoordenadaX
		 double vTPY = 0; // somatório do produto PesodoPonto * CoordenadaY
		 double vTP = 0; // somatório do produto PesodoPonto

		 double vInicialX; // valor candidato inicial a coordenada x
		 double vInicialY; // valor candidato inicial a coordenada Y

		 double vPrecisao; // valor de precisao que ira parar a iteracao
		// verificando a quantidade de pontos dentro da List
		int quantidadePontos = listPontos.size();

		// inicializando arrays double para armazenar os eixos e peso de cada
		// ponto, de acordo com a qtd de pontos recebidos
		vX = new double[quantidadePontos];
		vY = new double[quantidadePontos];
		vP = new double[quantidadePontos];

		// transformando o valor precisao em double
		vPrecisao = Double.parseDouble(valorPrecisao);

		// preenchendo os arrays
		int contador = 0;
		Iterator<String> iterar = listPontos.iterator();
		while (iterar.hasNext()) {
			String[] linha = ((String) iterar.next()).split(";");
			linha[0].replaceAll(",", ".");
			vX[contador] = (Double.parseDouble(linha[1]));
			linha[1].replaceAll(",", ".");
			vY[contador] = Double.parseDouble(linha[2]);
			linha[2].replaceAll(",", ".");
			vP[contador] = Double.parseDouble(linha[3]);
			contador++;
		}

		// etapa 1 - Calculando os candidatos iniciais pela média ponderada.

		System.out.println("Quantidade de pontos recebidos: "
				+ quantidadePontos);

		for (int i = 0; i < quantidadePontos; i++) {
			vTPX = vTPX + vP[i] * vX[i]; // vTPX = é o somatório do produto Peso
											// vezes ValorX do eixo
			vTPY = vTPY + vP[i] * vY[i]; // vTPY = é o somatório do produto Peso
											// vezes ValorY do eixo
			vTP = vTP + vP[i]; // vTP = é o somatório dos Pesos
		}

		vInicialX = vTPX / vTP; // É primeiro candidado ao valor do eixo X. É
								// simplesmente uma média ponderada.
		vInicialY = vTPY / vTP; // É primeiro candidado ao valor do eixo Y. É
								// simplesmente uma média ponderada.

		System.out.println("Valor inicial de x: " + vInicialX);
		System.out.println("Valor inicial de y: " + vInicialY);

		// etapa 2
		HashMap<Integer, Double> listaIteracoesX = new HashMap<>();
		HashMap<Integer, Double> listaIteracoesY = new HashMap<>();
		int contadorDeIteracoes = 0;
		double xSolucao = 0;
		double ySolucao = 0;
		
		if (contadorDeIteracoes == 0) {
			// Definindo como solucao inicial a media ponderada quando para a
			// primeira iteracao.
			xSolucao = vInicialX;
			ySolucao = vInicialY;
			listaIteracoesX.put(contadorDeIteracoes, xSolucao);// armazenando a
																// solucao
																// inicial x
			listaIteracoesY.put(contadorDeIteracoes, ySolucao);// armazenando a
																// solucao
																// inicial y

		}
		
		
		double distanciaTotalIteracaoAnterior = 0;
		double distanciaTotalAtual = 0;
		do {
						contadorDeIteracoes++;
						// calculando os candidatos a ponto central
						double numeradorXIteracao = 0;
						double numeradorYIteracao = 0;
						double denominadorIteracao = 0;
						
						distanciaTotalIteracaoAnterior = distanciaTotalAtual;
						distanciaTotalAtual = 0;
						
						for (int i = 0; i < quantidadePontos; i++) {
							double distEuclidianaIteracaoAtual = calculeDistanciaEuclidiana(
									xSolucao, ySolucao, vX[i], vY[i]);
							System.out.println("Distancia Euclidiana Ponto:" + i
									+ " valor:" + distEuclidianaIteracaoAtual);
							//calculando a distanciaTotalPara SOLUCAO ATUAL
							distanciaTotalAtual = distanciaTotalAtual + distEuclidianaIteracaoAtual;
							
							// candidatos eixo X
							double numeradorXIteracaoAtual = (vP[i] * vX[i] / distEuclidianaIteracaoAtual);
							numeradorXIteracao = numeradorXIteracao
									+ numeradorXIteracaoAtual;
							System.out.println("Numerador pontoX:" + i + "valor:"
									+ numeradorXIteracaoAtual);
							// candidatos eixo y
							double numeradorYIteracaoAtual = (vP[i] * vY[i] / distEuclidianaIteracaoAtual);
							numeradorYIteracao = numeradorYIteracao
									+ numeradorYIteracaoAtual;
							System.out.println("Numerador pontoY:" + i + "valor:"
									+ numeradorYIteracaoAtual);
							// calaculado denomiador
							double denominadorIteracaoAtual = (vP[i] / distEuclidianaIteracaoAtual);
							denominadorIteracao = denominadorIteracao
									+ denominadorIteracaoAtual;
							System.out.println("denomiador ponto:" + i + "valor:"
									+ denominadorIteracaoAtual);
							
							
						}
						listaIteracoesX.put(contadorDeIteracoes, numeradorXIteracao
								/ denominadorIteracao);
						listaIteracoesY.put(contadorDeIteracoes, numeradorYIteracao
								/ denominadorIteracao);
			
						xSolucao = numeradorXIteracao / denominadorIteracao;
						ySolucao = numeradorYIteracao / denominadorIteracao;
						System.out.println("Distancia total Anterior= " + distanciaTotalIteracaoAnterior );
						System.out.println("Distancia total atual= " + distanciaTotalAtual );
						System.out.println("Número da Iteracao : " + contadorDeIteracoes
								+ " Valor X= " + listaIteracoesX.get(contadorDeIteracoes)
								+ " Valor Y= " + listaIteracoesY.get(contadorDeIteracoes));
						
		}while (isPrecisaoFuncaoOk(distanciaTotalAtual, distanciaTotalIteracaoAnterior, vPrecisao,contadorDeIteracoes)!= true);
						
						
						/*} while ((isPrecisaoOk(listaIteracoesX.get(contadorDeIteracoes),
				listaIteracoesY.get(contadorDeIteracoes),
				listaIteracoesX.get(contadorDeIteracoes - 1),
				listaIteracoesY.get(contadorDeIteracoes - 1), vPrecisao)) != true);
*/
		xfinal = listaIteracoesX.get(contadorDeIteracoes); //
		yfinal = listaIteracoesY.get(contadorDeIteracoes); //

	}

	/*
	Esse metodo verifica se a diferença entre o valor da distancia total para a solucao atual e a solucao anterior é menor ou igual a precisao requerida pelo usuario.
	. Se maior retorna falso, se igual ou menor retorna falso.
	*/

	public boolean isPrecisaoFuncaoOk(double distanciaEuclidianaAtual,double distanciaEuclidianaAnterior,double precisaoEmPercentual,int contad) {
		boolean result = false;
		System.out.println("contador..."+contad);
				if(contad>1){
					System.out.println("Checando Precisão Por Diferença entre Distancias...");
					
					double precisaoAtual = Math.abs(Math.abs(distanciaEuclidianaAtual)-Math.abs(distanciaEuclidianaAnterior))/Math.abs(distanciaEuclidianaAnterior);
					if(precisaoAtual<=precisaoEmPercentual){
						System.out.println("Precisão Atingida!!");
						System.out.println("Precisão Atual=" + precisaoAtual);
						System.out.println("Precisão Meta=" + precisaoEmPercentual);
						result = true;
					} else {
						result = false;
					}
				}
	return result;
	}
	/*
	 * Esse método recebe os eixos de dois pontos quaisquer e retorna a distância euclidiana entre eles.
	 */
	public double calculeDistanciaEuclidiana(double valorX1, double valorY1,
			double valorX2, double valorY2) {

		return Math.sqrt(Math.pow((valorX1 - valorX2), 2)
				+ Math.pow((valorY1 - valorY2), 2));

	}

	/*
	 * Esse concatena os eixos do ponto central encontrado e retorna em formato de texto.
	 */
	public String getResult() {
		java.text.NumberFormat nf = java.text.NumberFormat.getNumberInstance();
		nf.setMinimumFractionDigits(2);
		nf.setMaximumFractionDigits(2);
		String xf = nf.format(xfinal);
		String yf = nf.format(yfinal);
		String resultado = ("X: " + xf + " Y: " + yf);
		return resultado;
	}
	/*
	 * Esse método transforma o eixo X do ponto central encontrado em formato de texto.
	 */
	public String getResultX() {
		java.text.NumberFormat nf = java.text.NumberFormat.getNumberInstance();
		nf.setMinimumFractionDigits(2);
		nf.setMaximumFractionDigits(2);
		String xf = nf.format(xfinal);

		return xf;
	}
	/*
	 * Esse método transforma o eixo Y do ponto central encontrado em formato de texto.
	 */
	public String getResultY() {
		java.text.NumberFormat nf = java.text.NumberFormat.getNumberInstance();
		nf.setMinimumFractionDigits(2);
		nf.setMaximumFractionDigits(2);
		String yf = nf.format(yfinal);

		return yf;
	}
	/*
	 * Esse método retorna o eixo X do ponto central encontrado.
	 */
	public double getResultXDoub() {
		return xfinal;
	}
	/*
	 * Esse método retorna o eixo Y do ponto central encontrado.
	 */
	public double getResultYDoub() {
		return yfinal;
	}
}


		