package br.puc.rio.utilities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.swing.JOptionPane;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.LinearRing;
import com.vividsolutions.jts.geom.Polygon;
import com.vividsolutions.jts.geom.impl.CoordinateArraySequence;
import com.vividsolutions.jump.feature.AttributeType;
import com.vividsolutions.jump.feature.BasicFeature;
import com.vividsolutions.jump.feature.Feature;
import com.vividsolutions.jump.feature.FeatureCollection;
import com.vividsolutions.jump.feature.FeatureDataset;
import com.vividsolutions.jump.feature.FeatureSchema;
import com.vividsolutions.jump.workbench.model.Layer;
import com.vividsolutions.jump.workbench.model.StandardCategoryNames;
import com.vividsolutions.jump.workbench.plugin.PlugInContext;
import com.vividsolutions.jump.workbench.ui.cursortool.CoordinateListMetrics;


public class GeoUtilidades {

	/*
	 * Essa classe contém diferentes tipos de métodos relacionados a geografia.
	 * Eles foram concentrados numa classe específica para promover o reuso e facilidade de manutenção do código, já que possui metodos bem específicos.
	 */	
	
	
	public double distanciaEntrePontos(Pontos p1, Pontos p2){
		
		
		List<Coordinate> pontosDist = new ArrayList<>();
		Coordinate c1 = new Coordinate(p1.getEixoXponto(),p1.getEixoYponto());
		Coordinate c2 = new Coordinate(p2.getEixoXponto(),p2.getEixoYponto());
		pontosDist.add(c1);
		pontosDist.add(c2);
		double distancia = CoordinateListMetrics.distance(pontosDist);
		return distancia;
	}
	public void desenhaPonto (double cX, double cY,String nomePonto,String pesoPonto, PlugInContext context) {
		 GeometryFactory gf = new GeometryFactory(); 
		 Coordinate c1 = new Coordinate(cX,cY); 
		 com.vividsolutions.jts.geom.Point point = gf.createPoint(c1);
		 
		 FeatureSchema fs1 = new FeatureSchema();
		 fs1.addAttribute("geometry", AttributeType.GEOMETRY);
			fs1.addAttribute("coordenadaX", AttributeType.DOUBLE);
			fs1.addAttribute("coordenadaY", AttributeType.DOUBLE);
			fs1.addAttribute("NomePonto", AttributeType.STRING);
			fs1.addAttribute("pesoPonto", AttributeType.STRING);
		 
		 Feature feature1 = new BasicFeature(fs1);
		 
		 feature1.setAttribute("coordenadaX", cX);
			feature1.setAttribute("coordenadaY", cY);
			feature1.setAttribute("NomePonto", nomePonto);
			feature1.setAttribute("pesoPonto", pesoPonto);
		 feature1.setGeometry(point); 
		 
		
		 FeatureCollection fc1 = new FeatureDataset(fs1);
		 fc1.add(feature1);

		 if(fc1.size() > 0){
		 context.addLayer(StandardCategoryNames.WORKING, nomePonto , fc1);
		 }
	   }
	/*
	Esse método é cria uma nova camada (Layer) que contenha um ponto com as coordenadas passadas e que contenha os campos com 
*/
	public void desenhaPonto(double cX, double cY, String nomePonto,int cargaPonto, String tipoPonto,PlugInContext context) {

		// definindo o tipo de geometria
		GeometryFactory gf = new GeometryFactory();
		Coordinate c1 = new Coordinate(cX, cY);
		com.vividsolutions.jts.geom.Point point = gf.createPoint(c1);

		FeatureSchema fs1 = new FeatureSchema();
		// definindo os atributos para cada geometria
		fs1.addAttribute("GEOMETRY", AttributeType.GEOMETRY);
		fs1.addAttribute("coordenadaX", AttributeType.DOUBLE);
		fs1.addAttribute("coordenadaY", AttributeType.DOUBLE);
		// fs1.addAttribute("CARGA", AttributeType.INTEGER);
		// fs1.addAttribute("Tipo", AttributeType.STRING);

		Feature feature1 = new BasicFeature(fs1);
		// preenchendo os atributos do ponto
		feature1.setAttribute("coordenadaX", cX);
		feature1.setAttribute("coordenadaY", cY);
		// feature1.setAttribute("CARGA", cargaPonto);
		// feature1.setAttribute("Tipo", tipoPonto);

		// definindo o tipo de geometria do feature construido
		feature1.setGeometry(point);

		// criando uma coleção de features
		FeatureCollection fc1 = new FeatureDataset(fs1);
		// inserindo o feature na coleção de features
		fc1.add(feature1);

		if (fc1.size() > 0) {
			context.addLayer(StandardCategoryNames.WORKING, nomePonto, fc1);
		}
	}
	public LineString rotacionarLinhaPorGraus(LineString linha,
			double quantidadeGraus) {

		// pegando os eixos do ponto variavel
		double eixoXpontoVariavel = linha.getEndPoint().getX();// o GETENDPOINT
																// e o ponto
																// variavel
		double eixoYpontoVariavel = linha.getEndPoint().getY();// o GETENDPOINT
																// e o ponto
																// variavel

		// pegando os eixos do ponto fixo = deposito
		double eixoXpontoFixo = linha.getStartPoint().getX();
		double eixoYpontoFixo = linha.getStartPoint().getY();

		double raio = linha.getLength();

		// angulo entre retas
		// double num = eixoXpontoVariavel - eixoXpontoFixo;
		// double den = eixoYpontoFixo - eixoYpontoVariavel;
		// double anguloEntreRetas = Math.atan(num/den);

		double passoEmGraus = quantidadeGraus;
		double passoEmRadiano = Math.toRadians(passoEmGraus);

		eixoXpontoVariavel = eixoXpontoFixo + raio * Math.cos(passoEmRadiano);
		eixoYpontoVariavel = eixoYpontoFixo + raio * Math.sin(passoEmRadiano);

		LineString linhaNova = criarUmaLinha(eixoXpontoFixo, eixoYpontoFixo,
				eixoXpontoVariavel, eixoYpontoVariavel);

		return linhaNova;
	}
	public LineString criarUmaLinha(double cX, double cY, double dX, double dY) {

		GeometryFactory gf = new GeometryFactory(); // this is a new line
		Coordinate c1 = new Coordinate(cX, cY);
		Coordinate c2 = new Coordinate(dX, dY);
		Collection<Coordinate> listCord = new ArrayList<Coordinate>();
		listCord.add(c1);
		listCord.add(c2);
		Coordinate[] coords = (Coordinate[]) listCord
				.toArray(new Coordinate[listCord.size()]); //
		LineString line = gf.createLineString(coords);

		return line;
	}
	

	public Polygon criarPoligono (List<Pontos> listaPontos) {
		
		Coordinate [] arr = new Coordinate [listaPontos.size()+1];
		Iterator<Pontos> iterar = listaPontos.iterator();
		int contador = 0;
		while(iterar.hasNext()){
			Pontos p = (Pontos) iterar.next();
			arr[contador]= new Coordinate(p.getEixoXponto(),p.getEixoYponto());
			contador++;
		}
		Coordinate c = arr[0];
		arr[contador]= new Coordinate(c.x, c.y);
		
		GeometryFactory factory = new GeometryFactory();
		CoordinateArraySequence coords = new CoordinateArraySequence(arr);
		LinearRing ring = new LinearRing(coords, factory);
		Polygon p = new Polygon(ring, null, factory);
		return p;
	}
	
	public Polygon criarPoligonoParaRoteiro (List<Pontos> listaPontos,Pontos pontoDeposito) {
		
		Coordinate [] arr = new Coordinate [listaPontos.size()+1+1];//tem q somar dois pois sao incluidos os pontos de deposito e o o ponto de retorno que tb e o depoisto
		int contador = 0;
		arr[contador]= new Coordinate(pontoDeposito.getEixoXponto(),pontoDeposito.getEixoYponto());//definindo como o primeiro ponto o ponto deposito
		Iterator<Pontos> iterar = listaPontos.iterator();
		contador++;
		while(iterar.hasNext()){
			Pontos p = (Pontos) iterar.next();
			arr[contador]= new Coordinate(p.getEixoXponto(),p.getEixoYponto());
			contador++;
		}
		arr[contador]= new Coordinate(pontoDeposito.getEixoXponto(),pontoDeposito.getEixoYponto());//definindo como o ultimo ponto o ponto deposito
		
		GeometryFactory factory = new GeometryFactory();
		CoordinateArraySequence coords = new CoordinateArraySequence(arr);
		LinearRing ring = new LinearRing(coords, factory);
		Polygon p = new Polygon(ring, null, factory);
		return p;
	}
	

	public Polygon criarTrianguloIsosceles(double extreX1, double extreY1,
			double extreX2, double extreY2, double baseX, double baseY) {
		Coordinate c1 = new Coordinate(extreX1, extreY1);
		Coordinate c2 = new Coordinate(extreX2, extreY2);
		Coordinate c3 = new Coordinate(baseX, baseY);
		Coordinate c4 = c1; // fim do poligono = emenda

		GeometryFactory factory = new GeometryFactory();
		CoordinateArraySequence coords = new CoordinateArraySequence(
				new Coordinate[] { c1, c2, c3, c4 });
		LinearRing ring = new LinearRing(coords, factory);
		Polygon p = new Polygon(ring, null, factory);
		return p;
	}

	
	public List<Matriz> createListMatrizDistanciasOrdenada(String nomeCamada,
			String nomeAtributo, List<Pontos> listaPontos) {
		List<Matriz> listaDistancia = new ArrayList<>();
		System.out.println("Criando matriz de distâncias...");

		int qtdElementosAcombinar = listaPontos.size();
		qtdElementosAcombinar--; // descontando 1 pois a posição zero existe
		for (int i = qtdElementosAcombinar; i > 0; i--) {

			for (int j = i - 1; j > -1; j--) {

				Pontos p1 = listaPontos.get(i);
				Pontos p2 = listaPontos.get(j);

				// calculando a distancia entre pontos
				double distancia = distanciaEntrePontos(p1, p2);

				// adicionando distancia a matriz de distancia
				Matriz novaDistancia = new Matriz();
				novaDistancia.setI(p1);
				novaDistancia.setJ(p2);
				novaDistancia.setDistanciaIJ(distancia);
				listaDistancia.add(novaDistancia);
			}
		}

		System.out.println(listaDistancia.size());
		// System.out.println(listaDistancia.toString());

		// ordenando a matriz de arestas em ordem crescente
		MatrizComparator crescente = new MatrizComparator();
		System.out.println("Ordenando a matriz em ordem crescente...");
		Collections.sort(listaDistancia, crescente);
		// System.out.println(listaDistancia);
		System.out.println("Matriz Ordenada!");

		return listaDistancia;
	}
	public void getAttributLayer(String nomeCamada, PlugInContext context) { // esse
																				// método
																				// imprime
																				// os
		// nomes e tipos dos
		// atributos de uma cada
		// especificada

		if (context.getLayerManager().getLayers() != null) {

			Layer camadaSelecionada = context.getLayerManager().getLayer(
					nomeCamada);

			for (int i = 0; i < camadaSelecionada.getFeatureCollectionWrapper()
					.getFeatureSchema().getAttributeCount(); i++) {
				System.out.println("Nome: "
						+ camadaSelecionada.getFeatureCollectionWrapper()
								.getFeatureSchema().getAttributeName(i)
						+ " Tipo Atributo: "
						+ camadaSelecionada.getFeatureCollectionWrapper()
								.getFeatureSchema().getAttributeType(i));

			}
		}
	}

	public void desenhaTrianguloNumaLayer(double extreX1, double extreY1, double extreX2,
			double extreY2, double baseX, double baseY,PlugInContext context) {

		Coordinate c1 = new Coordinate(extreX1, extreY1);
		Coordinate c2 = new Coordinate(extreX2, extreY2);
		Coordinate c3 = new Coordinate(baseX, baseY);
		Coordinate c4 = c1; // fim do poligono

		GeometryFactory factory = new GeometryFactory();
		CoordinateArraySequence coords = new CoordinateArraySequence(
				new Coordinate[] { c1, c2, c3, c4 });
		LinearRing ring = new LinearRing(coords, factory);
		Polygon p = new Polygon(ring, null, factory);
		FeatureSchema fs1 = new FeatureSchema();
		fs1.addAttribute("geometry", AttributeType.GEOMETRY);
		Feature feature1 = new BasicFeature(fs1);
		feature1.setGeometry(p);
		FeatureCollection fc1 = new FeatureDataset(fs1);
		fc1.add(feature1);
		if (fc1.size() > 0) {
			context.addLayer(StandardCategoryNames.WORKING, "Geometria", fc1);
		}

	}
	
public List<Pontos> createListPontosCoordenadaNome(String nomeCamada, String nomeAtributo,PlugInContext context) throws Exception {
		
		try{
		ArrayList<Pontos> listaPontos = new ArrayList<>();

		System.out.println("Criando lista de pontos...");

		if (context.getLayerManager().getLayers() != null) {

			Layer camadaSelecionada = context.getLayerManager().getLayer(
					nomeCamada);

			for (Iterator<?> iterator = camadaSelecionada
					.getFeatureCollectionWrapper().iterator(); iterator
					.hasNext();) {
				Feature aFeature = (Feature) iterator.next();

				// capturando as coord x e y de cada ponto
				GeoUtilidades g =  new GeoUtilidades();
				String[] coordPonto = g.separaCoordenadaString(aFeature
						.getAttribute("GEOMETRY").toString());
				String coordPontoX = coordPonto[0];
				String coordPontoY = coordPonto[1];

				

				// capturando o nome de cada ponto
				String codPonto = (aFeature.getAttribute(nomeAtributo)
						.toString());

				
				// criando os pontoos
				Pontos novoPonto = new Pontos();
				novoPonto.setNomePonto(codPonto);
				novoPonto.setEixoXponto(Double.parseDouble(coordPontoX));
				novoPonto.setEixoYponto(Double.parseDouble(coordPontoY));
				
				// adicionando ponto a lista
				listaPontos.add(novoPonto);

			}// end for
		}
		System.out.println("Lista de Pontos Criada!");
		System.out.println("Quantidade de pontos na lista: "
				+ listaPontos.size());
		return listaPontos;
		
		}
		catch (Exception e) {
			e.printStackTrace();
			JOptionPane
			.showMessageDialog(
					null,
					"A camada selecionada não é válida.");
			throw new Exception();
		}
		
	}
	
	
	public List<Pontos> createListPontos(String nomeCamada, String nomeAtributo,PlugInContext context) throws Exception {
		
		try{
		ArrayList<Pontos> listaPontos = new ArrayList<>();

		System.out.println("Criando lista de pontos...");

		if (context.getLayerManager().getLayers() != null) {

			Layer camadaSelecionada = context.getLayerManager().getLayer(
					nomeCamada);

			for (Iterator<?> iterator = camadaSelecionada
					.getFeatureCollectionWrapper().iterator(); iterator
					.hasNext();) {
				Feature aFeature = (Feature) iterator.next();

				// capturando as coord x e y de cada ponto
				GeoUtilidades g =  new GeoUtilidades();
				String[] coordPonto = g.separaCoordenadaString(aFeature
						.getAttribute("GEOMETRY").toString());
				String coordPontoX = coordPonto[0];
				String coordPontoY = coordPonto[1];

				// capturando o peso do ponto
				 String cargaPonto =
				 aFeature.getAttribute("CARGA").toString();

				 //capturando o tempo do ponto
				 String tempoPonto =
				 aFeature.getAttribute("TEMPO").toString();

				// capturando o nome de cada ponto
				String codPonto = (aFeature.getAttribute(nomeAtributo)
						.toString());

				//capturando o valor carga do ponto
				String valorPonto = aFeature.getAttribute("VALOR").toString();
				
				// criando os pontoos
				Pontos novoPonto = new Pontos();
				novoPonto.setNomePonto(codPonto);
				novoPonto.setEixoXponto(Double.parseDouble(coordPontoX));
				novoPonto.setEixoYponto(Double.parseDouble(coordPontoY));
				novoPonto.setQuantidadePonto(Double.parseDouble(cargaPonto));
				novoPonto.setTempoPonto(Double.parseDouble(tempoPonto));
				novoPonto.setValorCargaPonto(Double.parseDouble(valorPonto));
				// adicionando ponto a lista
				listaPontos.add(novoPonto);

			}// end for
		}
		System.out.println("Lista de Pontos Criada!");
		System.out.println("Quantidade de pontos na lista: "
				+ listaPontos.size());
		return listaPontos;
		
		}
		catch (Exception e) {
			e.printStackTrace();
			JOptionPane
			.showMessageDialog(
					null,
					"A camada selecionada não é válida.");
			throw new Exception();
		}
		
	}

	public double[] converteCoordenadaStringEmDouble(String infoPonto) {

		double[] infoPontoXY = new double[2];

		String novaStringSemPoint = infoPonto.substring(7);

		String[] novaStringSeparada = novaStringSemPoint.split(" ");
		String cX = novaStringSeparada[0];
		String cYParentes = novaStringSeparada[1];

		int qtdCaracte = cYParentes.length() - 1;
		String cY = cYParentes.substring(0, qtdCaracte);

		infoPontoXY[0] = Double.parseDouble(cX); // valor de x
		infoPontoXY[1] = Double.parseDouble(cY);// valor de y

		return infoPontoXY;

	}
	public String[] separaCoordenadaString(String infoPonto) {

		String[] infoPontoXY = new String[2];

		String novaStringSemPoint = infoPonto.substring(7);

		String[] novaStringSeparada = novaStringSemPoint.split(" ");
		String cX = novaStringSeparada[0];
		String cYParentes = novaStringSeparada[1];

		int qtdCaracte = cYParentes.length() - 1;
		String cY = cYParentes.substring(0, qtdCaracte);

		infoPontoXY[0] = (cX); // valor de x
		infoPontoXY[1] = (cY);// valor de y

		return infoPontoXY;

	}
	
	public void desenhaLinha (double cX,double cY,double dX, double dY,PlugInContext context) {
		 GeometryFactory gf = new GeometryFactory();
		 Coordinate c1 = new Coordinate(cX,cY); 
		 Coordinate c2 = new Coordinate(dX,dY);
		 final Collection<Coordinate> listCord = new ArrayList<Coordinate>();
		 listCord.add(c1);
		 listCord.add(c2);
		 Coordinate[] coords = (Coordinate[]) listCord.toArray(new Coordinate[listCord.size()]); 
		 LineString line = gf.createLineString(coords); 
		 FeatureSchema fs1 = new FeatureSchema();
		 fs1.addAttribute("geometry", AttributeType.GEOMETRY);
		 Feature feature1 = new BasicFeature(fs1);
		 feature1.setGeometry(line);
		 FeatureCollection fc1 = new FeatureDataset(fs1);
		 fc1.add(feature1);
		 if(fc1.size() > 0){
		 context.addLayer(StandardCategoryNames.WORKING, "Conexões" , fc1);
		 }
	   
	}
	
	
public void desenhaPontosNaCamadaNomeCoordenada(List<String> listaPontos,PlugInContext context) throws Exception   {

		
		try{
		
		FeatureSchema fs1 = new FeatureSchema();
		// definindo os atributos para cada geometria
		fs1.addAttribute("GEOMETRY", AttributeType.GEOMETRY);
		fs1.addAttribute("coordenadaX", AttributeType.DOUBLE);
		fs1.addAttribute("coordenadaY", AttributeType.DOUBLE);
		fs1.addAttribute("nomePonto", AttributeType.STRING);
		
		FeatureCollection fc1 = new FeatureDataset(fs1);

		Iterator<String> iterar = listaPontos.iterator();
		while (iterar.hasNext()) {

			String[] linha = ((String) iterar.next()).split(";");

			// LAYOUT ARQUIVO - POSIÇÕES DE CADA CAMPO
			// 0 - EIXO X
			// 1 - EIXO Y
			// 2 - NOME PONTO
	

			double cX = Double.parseDouble(linha[0]);
			double cY = Double.parseDouble(linha[1]);
			String nomePonto = linha[2];
			

			// definindo o tipo de geometria
			GeometryFactory gf = new GeometryFactory();
			Coordinate c1 = new Coordinate(cX, cY);
			com.vividsolutions.jts.geom.Point point = gf.createPoint(c1);

			Feature feature1 = new BasicFeature(fs1);
			// preenchendo os atributos do ponto
			feature1.setAttribute("coordenadaX", cX);
			feature1.setAttribute("coordenadaY", cY);
			feature1.setAttribute("nomePonto", nomePonto);

			// definindo o tipo de geometria do feature construido
			feature1.setGeometry(point);

			// inserindo o feature na coleção de features
			fc1.add(feature1);
		}// fim iterator

		if (fc1.size() > 0) {
			context.addLayer(StandardCategoryNames.WORKING, "CamadaPontos", fc1);
		}
		}
		catch (Exception e) {
			e.printStackTrace();
			System.out.println("Falha na leitura do arquivo.");
			JOptionPane.showMessageDialog(null,
					"Falha na leitura do arquivo. Verifique se arquivo está formatado conforme o padrão.");
			throw new Exception();
		}
	}
	
	
	public void desenhaPontosNaCamada(List<String> listaPontos,PlugInContext context,String campoEixoX,String campoEixoY, String campoNomePonto,String campoCarga,String campoTempo, String campoValorCarga, String campoObs) throws Exception   {

		
		try{
		
		FeatureSchema fs1 = new FeatureSchema();
		// definindo os atributos para cada geometria
		fs1.addAttribute("GEOMETRY", AttributeType.GEOMETRY);
		fs1.addAttribute("coordenadaX", AttributeType.DOUBLE);
		fs1.addAttribute("coordenadaY", AttributeType.DOUBLE);
		 fs1.addAttribute("CARGA", AttributeType.INTEGER);
		 fs1.addAttribute("TEMPO", AttributeType.INTEGER);
		 fs1.addAttribute("obs", AttributeType.STRING);
		fs1.addAttribute("nomePonto", AttributeType.STRING);
		fs1.addAttribute("VALOR", AttributeType.STRING);
		
		
		FeatureCollection fc1 = new FeatureDataset(fs1);

		int posicaoCampoEixoX = 9999;
		int posicaoCampoEixoY = 9999;
		int posicaoCampoNomePonto = 9999;
		int posicaoCampoCarga = 9999;
		int posicaoCampoTempo = 9999;
		int posicaoCampoValor = 9999;		
		int posicaoCampoObs = 9999;
		
		String [] linhaDeCampos = listaPontos.get(0).split(";");
		String [] linhaCabecalho = new String [7];
		int cont = 0;
		for (@SuppressWarnings("unused") String info : linhaCabecalho) {
			linhaCabecalho[cont] = "";
			cont++;
		}
		int cont1 = 0;
		for (@SuppressWarnings("unused") String info: linhaDeCampos) {
				linhaCabecalho [cont1] = linhaDeCampos[cont1];				
				cont1++;
		}
		
		int contador = 0;
		for (int i =0 ; i<7;i++) {
			
			if(linhaCabecalho[i].equals(campoEixoX)){
				posicaoCampoEixoX=contador;
			}
			if(linhaCabecalho[i].equals(campoEixoY)){
				posicaoCampoEixoY=contador;
			}
			if(linhaCabecalho[i].equals(campoNomePonto)){
				posicaoCampoNomePonto=contador;
			}
			if(linhaCabecalho[i].equals(campoCarga)){
				posicaoCampoCarga=contador;
			}
			if(linhaCabecalho[i].equals(campoTempo)){
				posicaoCampoTempo=contador;
			}
			if(linhaCabecalho[i].equals(campoValorCarga)){
				posicaoCampoValor=contador;
			}
			if(linhaCabecalho[i].equals(campoObs)){
				posicaoCampoObs=contador;
			}
			contador++;
		}
		
		
		int linhaAtual = 0;
		Iterator<String> iterar = listaPontos.iterator();
		while (iterar.hasNext()) {
			
			
					
						String[] linha = ((String) iterar.next()).split(";");
			
			if(linhaAtual != 0 ) { //colocando um if para pular a primeira linha de cabecalho
						
						// LAYOUT ARQUIVO - POSIÇÕES DE CADA CAMPO
						// 0 - EIXO X
						// 1 - EIXO Y
						// 2 - NOME PONTO
						// 3 - CARGA PONTO
						// 4 - TEMPO PONTO
						// 5 - VALOR PONTO
						// 6 - OBS PONTO
				
				String nomePonto = "Vazio";
						double cX = 0;
						double cY = 0;
						double cargaPonto =0;
						double tempoPonto = 0;
						double valorPonto =0;
						String obs = "Vazio";
			if(posicaoCampoNomePonto<=linhaCabecalho.length){
						 nomePonto = linha[posicaoCampoNomePonto];
			}
			if(posicaoCampoEixoX<=linhaCabecalho.length){
						 cX = Double.parseDouble(linha[posicaoCampoEixoX]);
			}
			if(posicaoCampoEixoY<=linhaCabecalho.length){
						 cY = Double.parseDouble(linha[posicaoCampoEixoY]);
			}
			if(posicaoCampoCarga<=linhaCabecalho.length){
						 cargaPonto = Double.parseDouble(linha[posicaoCampoCarga]);
			}
			if(posicaoCampoTempo<=linhaCabecalho.length){
						 tempoPonto = Double.parseDouble(linha[posicaoCampoTempo]);
			}
			if(posicaoCampoValor<=linhaCabecalho.length){
						 valorPonto = Double.parseDouble(linha[posicaoCampoValor]);
			}
			if(posicaoCampoObs<=linhaCabecalho.length){
				 obs = linha[posicaoCampoObs];
			}
					
			
						// definindo o tipo de geometria
						GeometryFactory gf = new GeometryFactory();
						Coordinate c1 = new Coordinate(cX, cY);
						com.vividsolutions.jts.geom.Point point = gf.createPoint(c1);
			
						Feature feature1 = new BasicFeature(fs1);
						// preenchendo os atributos do ponto
						feature1.setAttribute("coordenadaX", cX);
						feature1.setAttribute("coordenadaY", cY);
						feature1.setAttribute("nomePonto", nomePonto);
						feature1.setAttribute("CARGA", cargaPonto);
						feature1.setAttribute("TEMPO", tempoPonto);
						feature1.setAttribute("VALOR", valorPonto);
						feature1.setAttribute("obs", obs);
						// definindo o tipo de geometria do feature construido
						feature1.setGeometry(point);
			
						// inserindo o feature na coleção de features
						fc1.add(feature1);
			}
			linhaAtual++;
		}// fim iterator

		if (fc1.size() > 0) {
			context.addLayer(StandardCategoryNames.WORKING, "CamadaPontos", fc1);
		}
		}
		catch (Exception e) {
			e.printStackTrace();
			System.out.println("Falha na leitura do arquivo.");
			JOptionPane.showMessageDialog(null,
					"Falha na leitura do arquivo. Verifique se arquivo está formatado conforme o padrão.");
			throw new Exception();
		}
	}
	
	
	public Vector<Object> getLayers(PlugInContext context) { 
		Vector<Object> novoVector = new Vector<Object>();
		for (int i = 0; i < context.getLayerManager().getLayers().size(); i++) {
			novoVector.add(context.getLayerManager().getLayers().get(i));
		}
		return novoVector;
	}
}
