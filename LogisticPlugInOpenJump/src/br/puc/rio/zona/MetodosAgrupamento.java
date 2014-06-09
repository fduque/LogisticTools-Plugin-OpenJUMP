package br.puc.rio.zona;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.puc.rio.utilities.GeoUtilidades;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.LinearRing;
import com.vividsolutions.jts.geom.Polygon;
import com.vividsolutions.jts.geom.impl.CoordinateArraySequence;

public class MetodosAgrupamento {

	// essa classe e especializada em processar metodos que agrupam distritos de
	// diferentes maneiras

	private double areaAdmissivel;
	private List<Distrito> listaDistritos;
	private List<Distrito> listaDistritosAlocados = new ArrayList<Distrito>();
	private List<Polygon> listaTrianguloIsosceles = new ArrayList<>();
	private List<Polygon> listaTrianguloIsoscelesSetorizado = new ArrayList<>();
	private Distrito depositoDistrito;
	private Zona zonaPrincipal;
	private List<SubZona> listaSubZonasPorTrianguloGiratoria;
	private int contadorDeSubZonasGeradas = 0;

	
	/*
Essa classe é responsável por implementar os métodos necessários para realizar o procedimento de formação de subzonas.
Há dois métodos de formação de zona implementados: agruparPorTrianguloGiratório e agruparPorTrianguloGiratorioSetorizaso. O funcionamento de ambos procedimentos foram
mostrados anteriormente.

		*/	
	
	/*
	Esse método construtor da classe, já recebe todos os parâmetros necessários para executar a formação de zonas.

			*/	
	public MetodosAgrupamento(List<Distrito> listaDistritos,
			Distrito depositoDistrito, double areaAdmissivel, Zona zonaPrincipal) {
		super();
		this.listaDistritos = listaDistritos;
		this.depositoDistrito = depositoDistrito;
		this.areaAdmissivel = areaAdmissivel;
		this.zonaPrincipal = zonaPrincipal;
	}

	
	/*
	Esse método executa o procedimento de formação de zonas pelo método do triângulo giratório setorizado e retorna a lista de subzonas criadas.

			*/	
	public List<SubZona> agruparPorVarreduraSobreEixoSetorizado(
			double qtdDeSetores, double angulo) {

		String infoMetodo = "Método de Geração: Triângulo Rotacionado dentro de Setores. Parâmetros: Ângulo="
				+ angulo
				+ ", QtdDeSetores="
				+ qtdDeSetores
				+ ". Polígono Interseção: ";

		listaTrianguloIsosceles = new ArrayList<>();
		listaTrianguloIsoscelesSetorizado = new ArrayList<Polygon>();
		contadorDeSubZonasGeradas = 0;
		// verificando qual e o distrito mais distante do distrito deposito para
		// tracar a linha inicial
		Distrito distritoMaisDistanteDeposito = getDistritoMaisDistante(
				depositoDistrito, listaDistritos);

		// criando o poligono inicial
		int contadorIteracaoPoligono = 0; // poligono = setor= circunferencia
		double xDeposito = depositoDistrito.getGeometryDistrito().getCentroid()
				.getX(); // ponto fixo
		double yDeposito = depositoDistrito.getGeometryDistrito().getCentroid()
				.getY(); // ponto fixo
		double x1 = distritoMaisDistanteDeposito.getGeometryDistrito()
				.getCentroid().getX(); // ponto variavel
		double y1 = distritoMaisDistanteDeposito.getGeometryDistrito()
				.getCentroid().getY(); // ponto variavel

		List<Polygon> listpol = criarListDeTriangulosIsoscelesSetorizado(x1,
				y1, xDeposito, yDeposito, angulo, qtdDeSetores);

		// verificar os distritos que tem area maior ou igua a area admissivel e
		// gerar uma subzona exclusiva para eles
		listaSubZonasPorTrianguloGiratoria = gerarSubZonasDeDistritosComAreaMaiorIgualQueAreaAdmissivel(); // esse
																											// metodo
																											// tb
																											// preenche
																											// a
																											// listadedistritosjahalocados

		while ((listaDistritosAlocados.size() == listaDistritos.size()) == false
				&& (contadorIteracaoPoligono < listpol.size())) { // sai do loop
																	// o que for
																	// atingindo
																	// primeiro.
																	// Ou se tds
																	// os
																	// distritos
																	// forem
																	// alocados
																	// Ou se o
																	// triangulo
																	// ja girou
																	// 360
																	// graus.

			// criando lista com distritos q tocam no triangulo
			List<Distrito> listaDistritosTocamNaLinha = getDistritosTocamNoTriangulo(
					listaDistritos, listpol.get(contadorIteracaoPoligono),
					depositoDistrito);
			// ordenando a listaDistritosTocamNotriangulo por distancia ate o
			// distrito deposito.
			DistanciaDepositoComparator decrescente = new DistanciaDepositoComparator();
			System.out
					.println("Ordenando a lista de distancias em ordem crescente...");
			Collections.sort(listaDistritosTocamNaLinha, decrescente);
			// chamando o metodo para gerar subzonas de acordo com o triangulo
			// atual
			List<SubZona> listaSubZonasGeradasNoTrianguloAtual = gerarSubZonasDentroDoTriangulo(
					listaDistritosTocamNaLinha, contadorIteracaoPoligono,
					infoMetodo);
			// adicionando as subzonas na list de resposta
			listaSubZonasPorTrianguloGiratoria
					.addAll(listaSubZonasGeradasNoTrianguloAtual);

			contadorIteracaoPoligono++;

		}// end while
		System.out.println("SubZonas Criadas Geradas com Sucesso. ");
		for (SubZona s : listaSubZonasPorTrianguloGiratoria) {
			System.out.println(s.toString());
		}
		System.out.println("Qtd de SubZonas Criadas: "
				+ listaSubZonasPorTrianguloGiratoria.size());
		return listaSubZonasPorTrianguloGiratoria;
	}

	/*
	Esse método executa o procedimento de formação de zonas pelo método do triângulo giratório e retorna a lista de subzonas criadas.

			*/	
	public List<SubZona> agruparPorVarreduraSobreEixo(double anguloTriangulo) {

		String infoMetodo = "Método de Geração: Triângulo Rotacionado sobre Eixo. Parâmetros: Ângulo="
				+ anguloTriangulo + ". Polígono Interseção: ";
		listaTrianguloIsosceles = new ArrayList<>();
		listaTrianguloIsoscelesSetorizado = new ArrayList<Polygon>();
		contadorDeSubZonasGeradas = 0;
		// verificando qual e o distrito mais distante do distrito deposito para
		// tracar a linha inicial
		Distrito distritoMaisDistanteDeposito = getDistritoMaisDistante(
				depositoDistrito, listaDistritos);

		// criando o poligono inicial
		int contadorIteracaoPoligono = 0; // poligono = triangulo
		double xDeposito = depositoDistrito.getGeometryDistrito().getCentroid()
				.getX(); // ponto fixo
		double yDeposito = depositoDistrito.getGeometryDistrito().getCentroid()
				.getY(); // ponto fixo
		double x1 = distritoMaisDistanteDeposito.getGeometryDistrito()
				.getCentroid().getX(); // ponto variavel
		double y1 = distritoMaisDistanteDeposito.getGeometryDistrito()
				.getCentroid().getY(); // ponto variavel
		List<Polygon> listpol = criarListDeTriangulosIsosceles(x1, y1,
				xDeposito, yDeposito, anguloTriangulo);

		// verificar os distritos que tem area maior ou igua a area admissivel e
		// gerar uma subzona exclusiva para eles
		listaSubZonasPorTrianguloGiratoria = gerarSubZonasDeDistritosComAreaMaiorIgualQueAreaAdmissivel(); // esse
																											// metodo
																											// tb
																											// preenche
																											// a
																											// listadedistritosjahalocados

		while ((listaDistritosAlocados.size() == listaDistritos.size()) == false
				&& (contadorIteracaoPoligono < listpol.size())) { // sai do loop
																	// o que for
																	// atingindo
																	// primeiro.
																	// Ou se tds
																	// os
																	// distritos
																	// forem
																	// alocados
																	// Ou se o
																	// triangulo
																	// ja girou
																	// 360
																	// graus.

			// criando lista com distritos q tocam no triangulo
			List<Distrito> listaDistritosTocamNaLinha = getDistritosTocamNoTriangulo(
					listaDistritos, listpol.get(contadorIteracaoPoligono),
					depositoDistrito);

			// ordenando a listaDistritosTocamNotriangulo por distancia ate o
			// distrito deposito.
			DistanciaDepositoComparator decrescente = new DistanciaDepositoComparator();
			System.out
					.println("Ordenando a lista de distancias em ordem crescente...");
			Collections.sort(listaDistritosTocamNaLinha, decrescente);
			// chamando o metodo para gerar subzonas de acordo com o triangulo
			// atual
			List<SubZona> listaSubZonasGeradasNoTrianguloAtual = gerarSubZonasDentroDoTriangulo(
					listaDistritosTocamNaLinha, contadorIteracaoPoligono,
					infoMetodo);
			// adicionando as subzonas na list de resposta
			listaSubZonasPorTrianguloGiratoria
					.addAll(listaSubZonasGeradasNoTrianguloAtual);

			contadorIteracaoPoligono++;

		}// end while
		System.out.println("SubZonas Criadas: ");
		for (SubZona s : listaSubZonasPorTrianguloGiratoria) {
			System.out.println(s.toString());
		}
		System.out.println("Qtd de SubZonas Criadas: "
				+ listaSubZonasPorTrianguloGiratoria.size());
		return listaSubZonasPorTrianguloGiratoria;
	}

	/*
	Esse método cria todos os triângulos que serão usados no método de formação de zonas por triângulo giratório setorizado e retorna 
	uma lista de polígonos.
			*/	
	public List<Polygon> criarListDeTriangulosIsoscelesSetorizado(
			double extremX, double extremY, double baseX, double baseY,
			double anguloEmGraus, double qtdSetores) {
		List<Polygon> listaTriangulosSetorizadoOrdenados = new ArrayList<>();
		int contador = 1;
		double incrementoPorSetorX = ((Math.abs(extremX) - Math.abs(baseX)) / qtdSetores);
		double incrementoPorSetorY = ((Math.abs(extremY) - Math.abs(baseY)) / qtdSetores);
		double extremXsetor = 0;
		double extremYsetor = 0;
		while (contador <= qtdSetores) {
			extremXsetor = (incrementoPorSetorX * contador) + baseX;// a cada
																	// iteracao
																	// aumenta-se
																	// o
																	// comprimento
																	// do
																	// triangulo
																	// a ser
																	// rotacionado
			extremYsetor = (incrementoPorSetorY * contador) + baseY; // a cada
																		// iteracao
																		// aumenta-se
																		// o
																		// comprimento
																		// do
																		// triangulo
																		// a ser
																		// rotacionado
			listaTriangulosSetorizadoOrdenados
					.addAll(criarListDeTriangulosIsosceles(extremXsetor,
							extremYsetor, baseX, baseY, anguloEmGraus));
			contador++;
		}
		System.out
				.println("Lista de Triângulos Isósceles Setorizado gerada. Tamanho da lista : "
						+ listaTriangulosSetorizadoOrdenados.size());
		listaTrianguloIsoscelesSetorizado
				.addAll(listaTriangulosSetorizadoOrdenados);
		return listaTriangulosSetorizadoOrdenados;
	}

	/*
	Esse método cria todos os triângulos que serão usados no método de formação de zonas por triângulo giratório e retorna 
	uma lista de polígonos.
			*/
	public List<Polygon> criarListDeTriangulosIsosceles(double extremX,
			double extremY, double baseX, double baseY, double anguloEmGraus) {
		List<Polygon> listaTringulos = new ArrayList<>();
		double qtdTriangulos = 360 / anguloEmGraus;
		int contador = 1;
		double anguloMenor = 0;
		double anguloMaior = anguloEmGraus;
		// gerando linhas do triangulo inicial
		GeoUtilidades g = new GeoUtilidades();
		LineString novaLinha = g.criarUmaLinha(baseX, baseY, extremX, extremY);
		LineString linha1 = g.rotacionarLinhaPorGraus(novaLinha, anguloMenor);
		LineString linha2 = g.rotacionarLinhaPorGraus(novaLinha, anguloMaior);
		// pegando as coordenadas do triangulo inicial
		Coordinate cPontoFixo = new Coordinate(baseX, baseY);
		Coordinate cPontoVar1 = new Coordinate(linha1.getEndPoint().getX(),
				linha1.getEndPoint().getY());
		Coordinate cPontoVar2 = new Coordinate(linha2.getEndPoint().getX(),
				linha2.getEndPoint().getY());
		Coordinate cEmenda = cPontoFixo; // essa redundancia e necessaria para
											// criar o poligono triangulo

		// arredondando a qtd de triangulos para cima
		    int decimalPlace = 0;  // qtd de casas decimais
		    BigDecimal bd = new BigDecimal(qtdTriangulos);  
		    bd = bd.setScale(decimalPlace,BigDecimal.ROUND_UP);  
		    qtdTriangulos = bd.doubleValue();  
		    
		while (contador <= (int)(qtdTriangulos)) {

			GeometryFactory factory = new GeometryFactory();
			CoordinateArraySequence coords = new CoordinateArraySequence(
					new Coordinate[] { cPontoFixo, cPontoVar1, cPontoVar2,
							cEmenda });

			// criando o poligono triangulo
			LinearRing ring = new LinearRing(coords, factory);
			Polygon p = new Polygon(ring, null, factory);
			// adicionando o poligono triangulo a lista
			listaTringulos.add(p);

			// Calculando altura do triangulo
			LineString linhaBase = g.criarUmaLinha(cPontoVar2.x, cPontoVar2.y,
					cPontoVar1.x, cPontoVar1.y);
			double comprimentoLadoMaior = linha1.getLength();
			double altura = Math.sqrt(Math.pow(comprimentoLadoMaior, 2)
					- (Math.pow(linhaBase.getLength(), 2) / 4));
			double areaTriangulo = linhaBase.getLength() * altura / 2;
			System.out.println("Area triangulo = " + areaTriangulo);

			// gerando linhas do novo triangulo
			anguloMenor = anguloMenor + anguloEmGraus;
			linha1 = g.rotacionarLinhaPorGraus(novaLinha, anguloMenor);
			anguloMaior = anguloMaior + anguloEmGraus;
			linha2 = g.rotacionarLinhaPorGraus(novaLinha, anguloMaior);
			// pegando as coordenadas do novo triangulo
			cPontoFixo = new Coordinate(baseX, baseY);
			cPontoVar1 = new Coordinate(linha1.getEndPoint().getX(), linha1
					.getEndPoint().getY());
			cPontoVar2 = new Coordinate(linha2.getEndPoint().getX(), linha2
					.getEndPoint().getY());
			cEmenda = cPontoFixo; // essa redundancia e necessaria para criar o
									// poligono triangulo

			contador++;
		}
		System.out
				.println("Lista de Triângulos Isósceles gerada. Tamanho da lista : "
						+ listaTringulos.size());
		listaTrianguloIsosceles.addAll(listaTringulos);
		return listaTringulos;
	}

	public List<Polygon> getListaTrianguloIsosceles() {
		return listaTrianguloIsosceles;
	}

	/*
	Esse método forma subzonas agrupando distritos que possuem interseção com o triângulo projetado.
			*/
	public List<SubZona> gerarSubZonasDentroDoTriangulo(
			List<Distrito> listaDistritosQueTocamNaLinhaOrdenados,
			int contadorIteracaoLinhas, String infoMetodo) {

		System.out.println("Gerando nova Lista de Subzonas...");
		List<SubZona> listaSubZonasGeradas = new ArrayList<>();
		int qtdDistritosVerificadosAcumulado = 0;
		
		// String nomeDeSubZonas = "SubZona IteLinha: " + contadorIteracaoLinhas
		// + " Num: " + contadorDeSubZonas;

		while ((qtdDistritosVerificadosAcumulado < listaDistritosQueTocamNaLinhaOrdenados
				.size())) {
			int qtdDistritosVerificadosNaRodada = 0;
			double tamanhoAreZonaCriada = 0;
			List<Distrito> listaDistritosNaSubZona = new ArrayList<>();
			while ((tamanhoAreZonaCriada <= areaAdmissivel)
					&& ((qtdDistritosVerificadosAcumulado + qtdDistritosVerificadosNaRodada) < listaDistritosQueTocamNaLinhaOrdenados
							.size())) {
				int soma = qtdDistritosVerificadosAcumulado
						+ qtdDistritosVerificadosNaRodada;
				System.out.println("valor contAcumulado.."
						+ qtdDistritosVerificadosAcumulado);
				System.out.println("Posicao do distrito na lista..." + soma
						+ " Tamamnho da lista :"
						+ listaDistritosQueTocamNaLinhaOrdenados.size());
				Distrito distritoCandidato = listaDistritosQueTocamNaLinhaOrdenados
						.get(soma);
				if (distritoCandidato.getAreaDistrito() <= areaAdmissivel) {
					listaDistritosNaSubZona.add(distritoCandidato);
					tamanhoAreZonaCriada = tamanhoAreZonaCriada
							+ distritoCandidato.getAreaDistrito();
					// adicionando o distrito a lista de jah alocados
					listaDistritosAlocados.add(distritoCandidato);
				}
				qtdDistritosVerificadosNaRodada++;
			}
			// atualizando a qtd de distritos verificados
			qtdDistritosVerificadosAcumulado = qtdDistritosVerificadosAcumulado
					+ qtdDistritosVerificadosNaRodada;
			// criando a subzona e inserindo na lista

			SubZona subZona = new SubZona(contadorDeSubZonasGeradas,
					zonaPrincipal, listaDistritosNaSubZona, infoMetodo
							+ contadorIteracaoLinhas, areaAdmissivel,
					depositoDistrito);

			listaSubZonasGeradas.add(subZona);
			contadorDeSubZonasGeradas++;
		}

		return listaSubZonasGeradas;
	}

	/*
	Esse método verifica se um determinado distrito está alocado em alguma subzona e retorna verdadeiro ou falso.*/	
	public boolean distritoJaAlocado(Distrito d) {

		return listaDistritosAlocados.contains(d);
	}

	/*
	Esse é executado antes dos métodos de formação de subzonas. 
	Ele identifica os distritos que possuem tamanho igual ou maior a área admissível definida e cria uma subzona dedicada.
			*/
	public List<SubZona> gerarSubZonasDeDistritosComAreaMaiorIgualQueAreaAdmissivel() {

		List<SubZona> listaDeSubZonasComDistritoMaiorouIgualAareaAdmissivel = new ArrayList<>();
		String infoMetodo = "Método de Geração: Distritos com Área >= Área Admissível. ";
		
		java.util.Iterator<Distrito> iterar = listaDistritos.iterator();
		while (iterar.hasNext()) {
			Distrito d = (Distrito) iterar.next();
			if (d.getAreaDistrito() >= areaAdmissivel) {
				List<Distrito> listaDeDistritosDaSubZona = new ArrayList<>();
				listaDeDistritosDaSubZona.add(d);
				// gerando uma subzona exclusiva para o superdistrito
				SubZona subZona = new SubZona(contadorDeSubZonasGeradas,
						zonaPrincipal, listaDeDistritosDaSubZona, infoMetodo,
						areaAdmissivel, depositoDistrito);

				listaDeSubZonasComDistritoMaiorouIgualAareaAdmissivel
						.add(subZona);
				// inserir esses SUPERDISTRITOS na lista de distritos jah
				// alocados
				listaDistritosAlocados.add(d);
				contadorDeSubZonasGeradas++;
			}
		}
		System.out
				.println("SubZonas criadas por Distritos que tem area maior ou igual a area admissivel:");
		for (SubZona sz : listaDeSubZonasComDistritoMaiorouIgualAareaAdmissivel) {
			System.out.println(sz.toString());
		}
		System.out
				.println("Total de SubZonas criadas por Distritos que tem area maior ou igual a area admissivel: "
						+ listaDeSubZonasComDistritoMaiorouIgualAareaAdmissivel
								.size());
		return listaDeSubZonasComDistritoMaiorouIgualAareaAdmissivel;
	}

	/*
	Esse método cria uma lista de distritos que tem interseção com o triângulo.
			*/
	public List<Distrito> getDistritosTocamNoTriangulo(
			List<Distrito> listaDistritos, Polygon triangulo,
			Distrito depositoDistrito) {

		List<Distrito> listaDistritosTocamNoTriangulo = new ArrayList<>(); // ira
																			// guardar
																			// a
																			// lista
																			// de
																			// distritos
																			// q
																			// tocam
																			// na
																			// linha
		java.util.Iterator<Distrito> iterar = listaDistritos.iterator();
		while (iterar.hasNext()) {
			Distrito d = (Distrito) iterar.next();

			if (d.getIdDistrito().equals(depositoDistrito.getIdDistrito()) == false) {// retirando
																						// a
																						// possibilidade
																						// do
																						// distrito
																						// deposito
																						// entrar
																						// na
																						// lista

				if (distritoJaAlocado(d) == false) { // se distrito jah esta
														// alocado em outra
														// subzona ele nao vai
														// entrar

					if (triangulo.intersects(d.getGeometryDistrito()) == true) {
						// colocnado o distrito que toca na lista
						listaDistritosTocamNoTriangulo.add(d);
						// setando a distancia do distrito ate o distrito
						// deposito
						d.setDistanciaAteDistritoDeposito(d
								.getGeometryDistrito().distance(
										depositoDistrito.getGeometryDistrito()));

					}
				}
			}
		}
		return listaDistritosTocamNoTriangulo;
	}

	/*
	Esse método identifica qual é o distrito mais distante de um determinado distrito através da distância entre os centróides de cada distrito.
	*/
	public Distrito getDistritoMaisDistante(Distrito distritoBase,
			List<Distrito> listDistrito) {
		Distrito distritoMaisDistante = null;
		double maiorDistancia = 0.0;
		java.util.Iterator<Distrito> iterar = listDistrito.iterator();
		while (iterar.hasNext()) {
			Distrito d = (Distrito) iterar.next();
			if (distritoBase.getGeometryDistrito().distance(
					d.getGeometryDistrito()) > maiorDistancia) {
				maiorDistancia = distritoBase.getGeometryDistrito().distance(
						d.getGeometryDistrito());
				distritoMaisDistante = d;
				System.out.println("Distancia..."
						+ distritoBase.getGeometryDistrito().distance(
								d.getGeometryDistrito()));
			}
		}
		System.out.println("Maior distancia..." + maiorDistancia);
		return distritoMaisDistante;
	}

	
	public List<Polygon> getListaTrianguloIsoscelesSetorizado() {
		return listaTrianguloIsoscelesSetorizado;
	}

}
