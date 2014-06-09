package br.puc.rio.clarkWright;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import br.puc.rio.telas.TelaClarkWright;
import br.puc.rio.utilities.GeoUtilidades;
import br.puc.rio.utilities.Pontos;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.Polygon;
import com.vividsolutions.jump.feature.AttributeType;
import com.vividsolutions.jump.feature.BasicFeature;
import com.vividsolutions.jump.feature.Feature;
import com.vividsolutions.jump.feature.FeatureCollection;
import com.vividsolutions.jump.feature.FeatureDataset;
import com.vividsolutions.jump.feature.FeatureSchema;
import com.vividsolutions.jump.workbench.WorkbenchContext;
import com.vividsolutions.jump.workbench.model.StandardCategoryNames;
import com.vividsolutions.jump.workbench.plugin.AbstractPlugIn;
import com.vividsolutions.jump.workbench.plugin.EnableCheckFactory;
import com.vividsolutions.jump.workbench.plugin.MultiEnableCheck;
import com.vividsolutions.jump.workbench.plugin.PlugInContext;

public class ClarkWrightPlugIn extends AbstractPlugIn {

	private PlugInContext context;
	private TelaClarkWright telaClarkWright;
	
	/*
	Esse método é nativo da classe AbstractPlugin e define que para carregar esse plugin é necessário ter ao menos um camada (Layer) carregada no OpenJUMP.
*/	
	public static MultiEnableCheck createEnableCheck(
			WorkbenchContext workbenchContext) {
		EnableCheckFactory checkFactory = new EnableCheckFactory(
				workbenchContext);

		return new MultiEnableCheck().add(
				checkFactory.createAtLeastNLayersMustExistCheck(1)).add(
				checkFactory.createTaskWindowMustBeActiveCheck());
	}
	/*
	Esse método é nativo da classe AbstractPlugin e possui comportamento similar ao descrito para outras classes de plugin.
*/
	public boolean execute(PlugInContext context) throws Exception {
		this.context = context;
		this.telaClarkWright = new TelaClarkWright(this);
		telaClarkWright.setVisible(true);
		return true;
	}

	public PlugInContext getPlugInContext() {
		return context;

	}
	

public void geraLayerCWPoligonoRoteiro (List<Roteiro> listaRoteiros,String textoAdicionalNomeCamada,double cargaMax,double tempoMax,double valorMax,PlugInContext contextOut ){
	
	if(context==null){
		context = contextOut;
	}
		
		if(textoAdicionalNomeCamada==null){
			textoAdicionalNomeCamada = "";
		}
		
		FeatureSchema fs1 = new FeatureSchema();
		fs1.addAttribute("geometry", AttributeType.GEOMETRY);
		fs1.addAttribute("ROTEIRO", AttributeType.STRING);
		 fs1.addAttribute("TEMPO", AttributeType.DOUBLE);
		 fs1.addAttribute("TEMPO_MAX", AttributeType.DOUBLE);		 
		 fs1.addAttribute("CARGA", AttributeType.DOUBLE);
		 fs1.addAttribute("CARGA_MAX", AttributeType.DOUBLE);
		 fs1.addAttribute("VALOR", AttributeType.DOUBLE);
		 fs1.addAttribute("VALOR_MAX", AttributeType.DOUBLE);
		 fs1.addAttribute("DEPOSITO", AttributeType.STRING);
		 fs1.addAttribute("DISTANCIA", AttributeType.DOUBLE);
		 fs1.addAttribute("SEQUÊNCIA", AttributeType.STRING);
		 fs1.addAttribute("VELOCIDADE_MEDIA", AttributeType.STRING);
		 
		 FeatureCollection fc1 = new FeatureDataset(fs1);
			
			Iterator<Roteiro> iterar = listaRoteiros.iterator();
			while(iterar.hasNext()){
				
				Roteiro roteiro = (Roteiro) iterar.next();
			
			if(roteiro.getNome()!=null){
				
				GeoUtilidades g = new GeoUtilidades();
				
				Polygon p = g.criarPoligonoParaRoteiro(roteiro.getPontosEmFormatoList(),roteiro.getPontoOrigem());
				
				Feature feature1 = new BasicFeature(fs1);
				feature1.setAttribute("TEMPO", roteiro.calcTempoTotalRoteiro());
				feature1.setAttribute("CARGA", roteiro.calcPesoTotal());			
				feature1.setAttribute("VALOR", roteiro.calcValorTotal());
				feature1.setAttribute("DEPOSITO", roteiro.getPontoOrigem().getNomePonto());
				feature1.setAttribute("ROTEIRO", roteiro.getNome());
				feature1.setAttribute("DISTANCIA", roteiro.calcDistTotalRoteiro());
				feature1.setAttribute("VELOCIDADE_MEDIA", roteiro.getVelocidadeKmPorHora());	
				feature1.setAttribute("TEMPO_MAX", tempoMax);	
				feature1.setAttribute("CARGA_MAX", cargaMax);	
				feature1.setAttribute("VALOR_MAX", valorMax);	
				feature1.setAttribute("SEQUÊNCIA", roteiro.getNomePontosNaLista());
				feature1.setGeometry(p);
				fc1.add(feature1);	//ADICIONANDO A COLECAO
			}
		
	}
			//adicionando os objetos a camada
			context.addLayer(StandardCategoryNames.WORKING, "Layer Roteiros Clark & Wright - Visão de Poligono Roteiro -" + textoAdicionalNomeCamada, fc1);
			System.out.println("Layer CW gerada com sucesso!");
	}
	

	public void geraLayerListRoteiros(List<Roteiro> listRoteiro,
			String textoAdicionalNomeCamada, double cargaMax, double tempoMax,
			double valorMax,PlugInContext contextOut ){
		
		if(context==null){
			context = contextOut;
		}
		
		Iterator<Roteiro> iterar = listRoteiro.iterator();
		while (iterar.hasNext()) {

			Roteiro roteiro = (Roteiro) iterar.next();
			geraLayerPorRoteiro(roteiro,roteiro.getNome(), cargaMax,
					tempoMax, valorMax,context);

		}
	}


public void geraLayerPorRoteiro (Roteiro roteiro, String textoAdicionalNomeCamada,double cargaMax,double tempoMax,double valorMax,PlugInContext contextOut ){
	
	if(context==null){
		context = contextOut;
	}
	
	if(textoAdicionalNomeCamada==null){
		textoAdicionalNomeCamada = "";
	}
	
	FeatureSchema fs1 = new FeatureSchema();
	// definindo os atributos para cada geometria
	
	fs1.addAttribute("geometry", AttributeType.GEOMETRY);
	fs1.addAttribute("ROTEIRO", AttributeType.STRING);
	 fs1.addAttribute("TEMPO_ROTEIRO", AttributeType.DOUBLE);
	 fs1.addAttribute("TEMPO_MAX", AttributeType.DOUBLE);		 
	 fs1.addAttribute("CARGA_ROTEIRO", AttributeType.DOUBLE);
	 fs1.addAttribute("CARGA_MAX", AttributeType.DOUBLE);
	 fs1.addAttribute("VALOR_ROTEIRO", AttributeType.DOUBLE);
	 fs1.addAttribute("VALOR_MAX", AttributeType.DOUBLE);
	 fs1.addAttribute("DEPOSITO", AttributeType.STRING);
	 fs1.addAttribute("DISTANCIA_ROTEIRO", AttributeType.DOUBLE);
	 fs1.addAttribute("SEQUÊNCIA_ROTEIRO", AttributeType.STRING);
	 fs1.addAttribute("VELOCIDADE_MEDIA_ROTEIRO", AttributeType.STRING);
	
	fs1.addAttribute("coordenadaX", AttributeType.DOUBLE);
	fs1.addAttribute("coordenadaY", AttributeType.DOUBLE);
	 fs1.addAttribute("CARGA_PONTO", AttributeType.INTEGER);
	 fs1.addAttribute("TEMPO_PONTO", AttributeType.INTEGER);
	 fs1.addAttribute("OBS_PONTO", AttributeType.STRING);
	fs1.addAttribute("NOME_PONTO", AttributeType.STRING);
	fs1.addAttribute("VALOR_PONTO", AttributeType.STRING);
	
	
	 FeatureCollection fc1 = new FeatureDataset(fs1);
		
				
					
					for (Map.Entry<Integer, Pontos> entry : roteiro
							.getListaPontosRoteiro().entrySet()) {
						Feature feature1 = new BasicFeature(fs1);
						Pontos ponto = entry.getValue();
					
									// definindo o tipo de geometria
									GeometryFactory gf = new GeometryFactory();
									Coordinate c1 = new Coordinate(ponto.getEixoXponto(), ponto.getEixoYponto());
									com.vividsolutions.jts.geom.Point point = gf.createPoint(c1);
								
									feature1.setGeometry(point);
									feature1.setAttribute("coordenadaX", ponto.getEixoXponto());
									feature1.setAttribute("coordenadaY", ponto.getEixoYponto());
									feature1.setAttribute("NOME_PONTO", ponto.getNomePonto());
									feature1.setAttribute("CARGA_PONTO", ponto.getQuantidadePonto());
									feature1.setAttribute("TEMPO_PONTO", ponto.getTempoPonto());
									feature1.setAttribute("VALOR_PONTO", ponto.getValorCargaPonto());
									feature1.setAttribute("OBS_PONTO", ponto.getObservacao());
							
									feature1.setAttribute("TEMPO_ROTEIRO", roteiro.calcTempoTotalRoteiro());
									feature1.setAttribute("CARGA_ROTEIRO", roteiro.calcPesoTotal());			
									feature1.setAttribute("VALOR_ROTEIRO", roteiro.calcValorTotal());
									feature1.setAttribute("DEPOSITO", roteiro.getPontoOrigem().getNomePonto());
									feature1.setAttribute("ROTEIRO", roteiro.getNome());
									feature1.setAttribute("DISTANCIA_ROTEIRO", roteiro.calcDistTotalRoteiro());
									feature1.setAttribute("VELOCIDADE_MEDIA_ROTEIRO", roteiro.getVelocidadeKmPorHora());	
									feature1.setAttribute("TEMPO_MAX", tempoMax);	
									feature1.setAttribute("CARGA_MAX", cargaMax);	
									feature1.setAttribute("VALOR_MAX", valorMax);	
									feature1.setAttribute("SEQUÊNCIA_ROTEIRO", roteiro.getNomePontosNaLista());
							
					fc1.add(feature1);	//ADICIONANDO A COLECAO
					}
					
					//adicionando o ponto deposito a layer
					
						Feature featureDeposito = new BasicFeature(fs1);
						
						// definindo o tipo de geometria
						GeometryFactory gf = new GeometryFactory();
						Coordinate c1 = new Coordinate(roteiro.getPontoOrigem().getEixoXponto(),  roteiro.getPontoOrigem().getEixoYponto());
						com.vividsolutions.jts.geom.Point point = gf.createPoint(c1);
					
						featureDeposito.setGeometry(point);
						featureDeposito.setAttribute("coordenadaX", roteiro.getPontoOrigem().getEixoXponto());
						featureDeposito.setAttribute("coordenadaY", roteiro.getPontoOrigem().getEixoYponto());
						featureDeposito.setAttribute("NOME_PONTO", roteiro.getPontoOrigem().getNomePonto());
						featureDeposito.setAttribute("CARGA_PONTO", 0);
						featureDeposito.setAttribute("TEMPO_PONTO", 0);
						featureDeposito.setAttribute("VALOR_PONTO", 0);
						featureDeposito.setAttribute("OBS_PONTO", "PONTO_DEPOSITO");
				
						featureDeposito.setAttribute("TEMPO_ROTEIRO", roteiro.calcTempoTotalRoteiro());
						featureDeposito.setAttribute("CARGA_ROTEIRO", roteiro.calcPesoTotal());			
						featureDeposito.setAttribute("VALOR_ROTEIRO", roteiro.calcValorTotal());
						featureDeposito.setAttribute("DEPOSITO", roteiro.getPontoOrigem().getNomePonto());
						featureDeposito.setAttribute("ROTEIRO", roteiro.getNome());
						featureDeposito.setAttribute("DISTANCIA_ROTEIRO", roteiro.calcDistTotalRoteiro());
						featureDeposito.setAttribute("VELOCIDADE_MEDIA_ROTEIRO", roteiro.getVelocidadeKmPorHora());	
						featureDeposito.setAttribute("TEMPO_MAX", 0);	
						featureDeposito.setAttribute("CARGA_MAX", 0);	
						featureDeposito.setAttribute("VALOR_MAX", 0);	
						featureDeposito.setAttribute("SEQUÊNCIA_ROTEIRO", roteiro.getNomePontosNaLista());
						
						
					fc1.add(featureDeposito);	//ADICIONANDO A COLECAO
					
					//adicionando os objetos a camada
					context.addLayer(StandardCategoryNames.WORKING, "Layer Roteiros Clark & Wright - Roteiro - " + textoAdicionalNomeCamada, fc1);
					System.out.println("Layer CW gerada com sucesso!");
	
}

	public void geraLayerCWPoligono (List<Roteiro> listaRoteiros,String textoAdicionalNomeCamada,PlugInContext contextOut ){
		
		if(context==null){
			context = contextOut;
		}
		
		if(textoAdicionalNomeCamada==null){
			textoAdicionalNomeCamada = "";
		}
		
		FeatureSchema fs1 = new FeatureSchema();
		fs1.addAttribute("geometry", AttributeType.GEOMETRY);
		 fs1.addAttribute("ROTEIRO", AttributeType.STRING);
		 fs1.addAttribute("DISTANCIA", AttributeType.DOUBLE);
		 fs1.addAttribute("TEMPO", AttributeType.DOUBLE);
		 fs1.addAttribute("CARGA", AttributeType.DOUBLE);
		 fs1.addAttribute("VALOR", AttributeType.DOUBLE);
		 fs1.addAttribute("DEPOSITO", AttributeType.STRING);
		
		
		 FeatureCollection fc1 = new FeatureDataset(fs1);
			
			Iterator<Roteiro> iterar = listaRoteiros.iterator();
			while(iterar.hasNext()){
				
				Roteiro roteiro = (Roteiro) iterar.next();
			
			if(roteiro.getNome()!=null){
				
				GeoUtilidades g = new GeoUtilidades();
				Polygon p = g.criarPoligono(roteiro.getPontosEmFormatoList());
				
				Feature feature1 = new BasicFeature(fs1);
				feature1.setAttribute("ROTEIRO", roteiro.getNome());
				feature1.setAttribute("DISTANCIA", roteiro.calcDistTotalRoteiro());
				feature1.setAttribute("TEMPO", roteiro.calcTempoTotalRoteiro());
				feature1.setAttribute("CARGA", roteiro.calcPesoTotal());			
				feature1.setAttribute("VALOR", roteiro.calcValorTotal());
				feature1.setAttribute("DEPOSITO", roteiro.getPontoOrigem().getNomePonto());
			
				feature1.setGeometry(p);
				fc1.add(feature1);	//ADICIONANDO A COLECAO
			}
		
	}
			//adicionando os objetos a camada
			context.addLayer(StandardCategoryNames.WORKING, "Layer Roteiros Clark & Wright - Visão de Poligono Zona -" + textoAdicionalNomeCamada, fc1);
			System.out.println("Layer CW gerada com sucesso!");
	}
	public void geraLayerCWPorArco (List<Roteiro> listaRoteiros,String textoAdicionalNomeCamada,PlugInContext contextOut ){
		
		if(context==null){
			context = contextOut;
		}
		
		if(textoAdicionalNomeCamada==null){
			textoAdicionalNomeCamada = "";
		}
		GeoUtilidades g = new GeoUtilidades();
		System.out.println("Gerando Layer Result CW...");
		
					FeatureSchema fs1 = new FeatureSchema();
					fs1.addAttribute("geometry", AttributeType.GEOMETRY);
					 fs1.addAttribute("TEMPO TOTAL ROTEIRO", AttributeType.DOUBLE);
					 fs1.addAttribute("CARGA TOTAL ROTEIRO", AttributeType.DOUBLE);
					 fs1.addAttribute("VALOR TOTAL ROTEIRO", AttributeType.DOUBLE);
					 fs1.addAttribute("DEPOSITO ROTEIRO", AttributeType.STRING);
					 fs1.addAttribute("ARCO",AttributeType.STRING);
					 fs1.addAttribute("COMPRIMENTO ARCO",AttributeType.STRING);
					 fs1.addAttribute("DISTANCIA TOTAL ROTEIRO",AttributeType.STRING);
					 fs1.addAttribute("NOME DO ROTEIRO", AttributeType.STRING);
					
					 
					 
					FeatureCollection fc1 = new FeatureDataset(fs1);
					
					Iterator<Roteiro> iterar = listaRoteiros.iterator();
					while(iterar.hasNext()){
						
						Roteiro roteiro = (Roteiro) iterar.next();
					
					if(roteiro.getNome()!=null){
						int qtdArcosNoRoteiro = roteiro.getListaPontosRoteiro().size()-1;
						for (int i=0;i<qtdArcosNoRoteiro;i++){
							Pontos p1 = roteiro.getListaPontosRoteiro().get(i);
							Pontos p2 = roteiro.getListaPontosRoteiro().get(i+1);
						
							
							GeometryFactory gf = new GeometryFactory();
							Coordinate c1 = new Coordinate(p1.getEixoXponto(), p1.getEixoYponto());
							Coordinate c2 = new Coordinate(p2.getEixoXponto(), p2.getEixoYponto());
						    Collection<Coordinate> listCord = new ArrayList<Coordinate>();
							listCord.add(c1);
							listCord.add(c2);
							Coordinate[] coords = (Coordinate[]) listCord.toArray(new Coordinate[listCord.size()]);
							LineString line = gf.createLineString(coords); 
							
							Feature feature1 = new BasicFeature(fs1); //ADICIONANDO UM REGISTRO
							feature1.setGeometry(line);
							feature1.setAttribute("TEMPO TOTAL ROTEIRO", roteiro.calcTempoTotalRoteiro());
							feature1.setAttribute("CARGA TOTAL ROTEIRO", roteiro.calcPesoTotal());			
							feature1.setAttribute("VALOR TOTAL ROTEIRO", roteiro.calcValorTotal());
							feature1.setAttribute("DEPOSITO ROTEIRO", roteiro.getPontoOrigem().getNomePonto());
							feature1.setAttribute("ARCO", p1.getNomePonto()+" + "+p2.getNomePonto());
							feature1.setAttribute("COMPRIMENTO ARCO", g.distanciaEntrePontos(p1,p2));
							feature1.setAttribute("DISTANCIA TOTAL ROTEIRO", roteiro.calcDistTotalRoteiro());
	
							feature1.setAttribute("NOME DO ROTEIRO", roteiro.getNome());
							
							fc1.add(feature1);	//ADICIONANDO A COLECAO
						}	
					}
					//adicionanco os arcos de entrada e saida
					
					//arco de ida
					GeometryFactory gf = new GeometryFactory();
					Coordinate c1 = new Coordinate(roteiro.getPontoOrigem().getEixoXponto(), roteiro.getPontoOrigem().getEixoYponto());
					Coordinate c2 = new Coordinate(roteiro.getListaPontosRoteiro().get(0).getEixoXponto(), roteiro.getListaPontosRoteiro().get(0).getEixoYponto());
				    Collection<Coordinate> listCord = new ArrayList<Coordinate>();
					listCord.add(c1);
					listCord.add(c2);
					Coordinate[] coords = (Coordinate[]) listCord.toArray(new Coordinate[listCord.size()]);
					LineString line = gf.createLineString(coords); 
					
					Feature feature1 = new BasicFeature(fs1); //ADICIONANDO UM REGISTRO
					feature1.setGeometry(line);
					feature1.setAttribute("TEMPO TOTAL ROTEIRO", roteiro.calcTempoTotalRoteiro());
					feature1.setAttribute("CARGA TOTAL ROTEIRO", roteiro.calcPesoTotal());			
					feature1.setAttribute("VALOR TOTAL ROTEIRO", roteiro.calcValorTotal());
					feature1.setAttribute("DEPOSITO ROTEIRO", roteiro.getPontoOrigem().getNomePonto());
					feature1.setAttribute("COMPRIMENTO ARCO", g.distanciaEntrePontos( roteiro.getPontoOrigem(),roteiro.getListaPontosRoteiro().get(0)));
					feature1.setAttribute("DISTANCIA TOTAL ROTEIRO", roteiro.calcDistTotalRoteiro());
					feature1.setAttribute("NOME DO ROTEIRO", roteiro.getNome());
					feature1.setAttribute("ARCO","ENT-SAI: "+ roteiro.getPontoOrigem().getNomePonto()+" + "+roteiro.getListaPontosRoteiro().get(0).getNomePonto());
					feature1.setAttribute("NOME DO ROTEIRO", roteiro.getNome());
					fc1.add(feature1);	//ADICIONANDO A COLECAO
					
					//arco de volta
					
					Pontos ultimoPontoRoteiro = roteiro.getListaPontosRoteiro().get(roteiro.getListaPontosRoteiro().size()-1);
					
					GeometryFactory gf1 = new GeometryFactory();
					Coordinate c3 = new Coordinate(roteiro.getPontoOrigem().getEixoXponto(), roteiro.getPontoOrigem().getEixoYponto());
					Coordinate c4 = new Coordinate(ultimoPontoRoteiro.getEixoXponto(), ultimoPontoRoteiro.getEixoYponto());
				    Collection <Coordinate> listCord1 = new ArrayList<Coordinate>();
					listCord1.add(c3);
					listCord1.add(c4);
					Coordinate[] coords1 = (Coordinate[]) listCord1.toArray(new Coordinate[listCord1.size()]);
					LineString line1 = gf1.createLineString(coords1); 
					
					Feature feature2 = new BasicFeature(fs1); //ADICIONANDO UM REGISTRO
					feature2.setGeometry(line1);
					feature2.setAttribute("TEMPO TOTAL ROTEIRO", roteiro.calcTempoTotalRoteiro());
					feature2.setAttribute("CARGA TOTAL ROTEIRO", roteiro.calcPesoTotal());			
					feature2.setAttribute("VALOR TOTAL ROTEIRO", roteiro.calcValorTotal());
					feature2.setAttribute("DEPOSITO ROTEIRO", roteiro.getPontoOrigem().getNomePonto());
					feature2.setAttribute("COMPRIMENTO ARCO", g.distanciaEntrePontos(ultimoPontoRoteiro,roteiro.getPontoOrigem()));
					feature2.setAttribute("DISTANCIA TOTAL ROTEIRO", roteiro.calcDistTotalRoteiro());
					feature2.setAttribute("NOME DO ROTEIRO", roteiro.getNome());
					feature2.setAttribute("ARCO","ENT-SAI: "+ ultimoPontoRoteiro.getNomePonto()+" + "+roteiro.getPontoOrigem().getNomePonto());
					fc1.add(feature2);	//ADICIONANDO A COLECAO
					}
					
					//adicionando os objetos a camada
					context.addLayer(StandardCategoryNames.WORKING, "Layer Roteiros Clark & Wright - Visão de Arco-" + textoAdicionalNomeCamada, fc1);
					System.out.println("Layer CW gerada com sucesso!");
	}

	/*
	Esse método recebe a lista de pontos e o ponto-depósito e cria uma lista ordenada de ganhos de acordo com a fórmula de ganho proposto por Clarke e Wright.
	Caso o usuário selecione o método que adiciona o parâmetro de forma a fórmula, ele também é considerado para elaborar a lista de ganhos.
*/
	
	public List<Ganho> createListaGanhoOrdenada(List<Pontos> listaPontos,
			String pontoDeposito, double valorAlfa) {

		List<Ganho> listaArcosGanhos = new ArrayList<>();

		Pontos pontoDep = verificaPontoNaListaPontos(listaPontos, pontoDeposito);

		listaPontos.remove(pontoDep); // reomvendo ponto de deposito da lista

		// DEFINIR PONTO DE DEPÓSITO
		// APLICAR A FORMULA - PARA CADA PONTO i DEVE-SE VARRER TODOS OS PONTOS
		// - EXCECAO QUANDO i=i - ver logica kruskal
		// POPULAR A LISTAARCOSGANHOS

		System.out.println("Criando matriz de ganhos...");

		int qtdElementosAcombinar = listaPontos.size();
		qtdElementosAcombinar--; // descontando 1 pois a posição zero existe
		for (int i = qtdElementosAcombinar; i > 0; i--) {

			for (int j = i - 1; j > -1; j--) {

				Pontos p1 = listaPontos.get(i);
				Pontos p2 = listaPontos.get(j);

				// calculando ganhos para os pontos selecionados
				double ganho = calcGanho(p1, p2, pontoDep, valorAlfa);

				// adicionando calculo do arco
				Ganho novoArcoGanho = new Ganho();
				novoArcoGanho.setDeposito(pontoDep);
				novoArcoGanho.setPontoI(p1);
				novoArcoGanho.setPontoJ(p2);
				novoArcoGanho.setGanho(ganho);
				listaArcosGanhos.add(novoArcoGanho);
			}
		}
		System.out.println("Lista de Ganhos Calculada!");
		System.out.println("Quantidade de arcos-ganhos calculados: "
				+ listaArcosGanhos.size());
		// ordenando em ordem decrescente
		GanhoComparator decrescente = new GanhoComparator();
		System.out
				.println("Ordenando a lista de ganhos em ordem decrescente...");
		Collections.sort(listaArcosGanhos, decrescente);
		Collections.reverse(listaArcosGanhos); // colocando em ordem
												// decrescente, revertendo a
												// lista, do maior ganho para o
												// menor ganho

		// so paqra teste - EXCUIIIRRR
		Iterator<Ganho> iterar = listaArcosGanhos.iterator();
		int qtd = 0;
		while (iterar.hasNext()) {
			Ganho ganho = (Ganho) iterar.next();
			System.out.println(ganho.toString());
			if (qtd == 20) {
				break;
			}
		}

		System.out.println("Matriz Ordenada!");

		return listaArcosGanhos;
	}

	/*
	Esse método realiza o cálculo de ganho para dois pontos quaisquer.
*/
	public double calcGanho(Pontos pontoI, Pontos pontoJ, Pontos pontodeposito,
			double alfaValue) {
		GeoUtilidades g = new GeoUtilidades();
		double ganho = 0;
		double distDepositoPontoI = g.distanciaEntrePontos(pontodeposito, pontoI);
		double distDepositoPontoJ = g.distanciaEntrePontos(pontodeposito, pontoJ);
		double distPontoIPontoJ = g.distanciaEntrePontos(pontoI, pontoJ);
		if (alfaValue == 0) {
			ganho = distDepositoPontoI + distDepositoPontoJ - distPontoIPontoJ;
		} else {
			ganho = distDepositoPontoI + distDepositoPontoJ
					- (distPontoIPontoJ * alfaValue);
		}
		return ganho;
	}

	/*
  Esse é o método principal da classe, pois ele a lista de todos os possíveis ganhos já ordenada e todos os parâmetros de roteirização definidos pelo usuário. Ele tem como
saída uma lista com todos os roteiros formados pelo método.
*/
	
	public List<Roteiro> calcClarkWright(List<Ganho> listaGanhos,
			List<Pontos> listPontos, double velocidadeKmPorHora,
			double limitePeso, double limiteTempo, double limiteValor) {

		List<Roteiro> listaRoteiros = new ArrayList<>();
		int nomeRoteiro = 0;

		Iterator<Ganho> iterar = listaGanhos.iterator();
		while (iterar.hasNext()) {

			Ganho ganho = (Ganho) iterar.next();

			Pontos p1 = ganho.getPontoI();
			Pontos p2 = ganho.getPontoJ();
			System.out.println("Nova iteracao - Arco de Entrada: "
					+ p1.getNomePonto() + " - " + p2.getNomePonto());
			int p1Alocado = 0;
			int p2Alocado = 0;
			if (verificarPontoNaListaRoteiros(listaRoteiros, p1) == true) {
				p1Alocado = 1;
				System.out.println("p1 esta na lista!" + p1.getNomePonto());
			}
			if (verificarPontoNaListaRoteiros(listaRoteiros, p2) == true) {
				p2Alocado = 1;
				System.out.println("p2 esta na lista!" + p2.getNomePonto());
			}

			// se nenhum dos 2 potnos alocados
			if ((p1Alocado + p2Alocado) == 0) {
				System.out.println("p1+p2 =0");
				// CRIANDO O ROTEIRO APARTIR DE DOIS PONTOS
				Roteiro testeRoteiro = new Roteiro();
				testeRoteiro.setPontoOrigem(ganho.getDeposito());
				testeRoteiro.setNome(Integer.toString(nomeRoteiro));
				testeRoteiro.setVelocidadeKmPorHora(velocidadeKmPorHora);
				testeRoteiro.addPontoRoteiro(p1);
				testeRoteiro.addPontoRoteiro(p2);

				System.out.println(testeRoteiro.getListaPontosRoteiro()
						.values());

				// VERIFICANDO SE O ROTEIRO É VIÁVEL
				int testePeso = 0;
				if (isRestricaoPesoOK(testeRoteiro, limitePeso) == true) {
					System.out.println("Aprovador em Peso");
					testePeso = 1;
				}
				int testeTempo = 0;
				if (isRestricaoTempoOK(testeRoteiro, limiteTempo) == true) {
					testeTempo = 1;
					System.out.println("Aprovador em Tempo");
				}
				int testeValor = 0;
				if (isRestricaoValorOK(testeRoteiro, limiteValor) == true) {
					testeValor = 1;
					System.out.println("Aprovador em Valor");
				}
				if (testeValor + testePeso + testeTempo == 3) { // adiciona
																// roteiro a
																// lista
					listaRoteiros.add(testeRoteiro);
					System.out.println("Roteiro Adicionado a lista ! = "
							+ testeRoteiro.toString());
					nomeRoteiro++;
				}
			} else {
				System.out.println("p1+p2 >0");

				// pegar quem é o ponto sem roteiro e quem é o ponto no roteiro
				if ((p1Alocado + p2Alocado) == 1) {
					System.out.println("=1");
					Pontos pontoSemRoteiro;
					Roteiro roteiroCandidato;
					Pontos pontoNoRoteiroCandidato;
					if (p1Alocado == 1) {
						pontoSemRoteiro = p2;
						roteiroCandidato = verificarRoteiroDoPonto(
								listaRoteiros, p1);
						pontoNoRoteiroCandidato = p1;
					} else {
						roteiroCandidato = verificarRoteiroDoPonto(
								listaRoteiros, p2);
						pontoSemRoteiro = p1;
						pontoNoRoteiroCandidato = p2;
					}

					// VERIFICAR se o Ponto que está no roteiro é extremo
					if (verificarPontoExtremosNosRoteiros(listaRoteiros,
							pontoNoRoteiroCandidato) == true) {
						System.out.println("o ponto no roteiro é extremo...");
						Roteiro testeRoteiro = new Roteiro();
						testeRoteiro.setNome(Integer.toString(nomeRoteiro));
						testeRoteiro.setPontoOrigem(ganho.getDeposito());
						testeRoteiro
								.setVelocidadeKmPorHora(velocidadeKmPorHora);
						testeRoteiro.addPontoRoteiroNaSequencia(
								roteiroCandidato, pontoNoRoteiroCandidato,
								pontoSemRoteiro);

						// VERIFICANDO SE O ROTEIRO É VIÁVEL
						int testePeso = 0;
						if (isRestricaoPesoOK(testeRoteiro, limitePeso) == true) {
							System.out.println("Aprovado em Peso");
							testePeso = 1;
						}
						int testeTempo = 0;
						if (isRestricaoTempoOK(testeRoteiro, limiteTempo) == true) {
							testeTempo = 1;
							System.out.println("Aprovado em Tempo");
						}
						int testeValor = 0;
						if (isRestricaoValorOK(testeRoteiro, limiteValor) == true) {
							testeValor = 1;
							System.out.println("Aprovado em Valor");
						}

						if (testeValor + testePeso + testeTempo == 3) { // adiciona
																		// roteiro
																		// a
																		// lista
							listaRoteiros.add(testeRoteiro);
							listaRoteiros.remove(roteiroCandidato);
							System.out
									.println("Composicao do Roteiro Pre-existente:"
											+ roteiroCandidato.toString()
											+ "Ponto que vai entrar:"
											+ pontoSemRoteiro);
							System.out
									.println("Roteiro Ponto adicionado a um roteiro existente - Adicionado a lista ! = "
											+ testeRoteiro.toString());
							nomeRoteiro++;
						}
					}
				}

				if ((p1Alocado + p2Alocado) == 2) {

					System.out.println("=2");

					// PEGAR O ROTEIRO RELATIVO A CADA PONTO
					Roteiro roteiroP1 = verificarRoteiroDoPonto(listaRoteiros,
							p1);
					Roteiro roteiroP2 = verificarRoteiroDoPonto(listaRoteiros,
							p2);
					System.out.println("roteiro 1: "
							+ roteiroP1.getListaPontosRoteiro());
					System.out.println("roteiro 2: "
							+ roteiroP2.getListaPontosRoteiro());

					if ((roteiroP1 == roteiroP2) == false) {// verificar se os
															// pontos
															// selecionados
															// estao no mesmo
															// roteiro
						if (verificarPontoExtremosNosRoteiros(listaRoteiros, p1) == true) { // verificar
																							// se
																							// ambos
																							// são
																							// extremos
							if (verificarPontoExtremosNosRoteiros(
									listaRoteiros, p2) == true) { // verificar
																	// se ambos
																	// são
																	// extremos
								System.out
										.println("...p1 e p2 sao extremos e estao em roteiros diferentes..");

								// CRIANDO O ROTEIRO A PARTIR DE DOIS ROTEIROS
								Roteiro testeRoteiro = configRoteiroAPartirDeDois(
										roteiroP1, roteiroP2, p1, p2,
										Integer.toString(nomeRoteiro));

								System.out
										.println("Lista de Pontos no roteiro teste: "
												+ testeRoteiro
														.getListaPontosRoteiro()
														.toString());

								// VERIFICANDO SE O ROTEIRO É VIÁVEL
								int testePeso = 0;
								if (isRestricaoPesoOK(testeRoteiro, limitePeso) == true) {
									System.out.println("Aprovado em Peso");
									testePeso = 1;
								}
								int testeTempo = 0;
								if (isRestricaoTempoOK(testeRoteiro,
										limiteTempo) == true) {
									testeTempo = 1;
									System.out.println("Aprovado em Tempo");
								}
								int testeValor = 0;
								if (isRestricaoValorOK(testeRoteiro,
										limiteValor) == true) {
									testeValor = 1;
									System.out.println("Aprovado em Valor");
								}
								if (testeValor + testePeso + testeTempo == 3) { // adiciona
																				// roteiro
																				// a
																				// lista
									listaRoteiros.add(testeRoteiro);
									listaRoteiros.remove(roteiroP2);
									listaRoteiros.remove(roteiroP1);
									System.out.println("Composicao Roteiro1:"
											+ roteiroP1.toString()
											+ "Composicao Roteiro2:"
											+ roteiroP2.toString());
									System.out
											.println("Roteiro Criado a Partir de Dois roteiros Adicionado a lista ! = "
													+ testeRoteiro.toString());
									nomeRoteiro++;
								}
							}
						}
					}
				}
			}

			// verificar se pontos jah estao alocados
			// se sim
			// verificar se sao extremidados
			// se sao extremidades
			// se nao - nao faz nada
			// se sim - ligar eles e testar restrições
			// se roteiro nao viavel - nao muda o roteiro
			// se novo roteiro viavel - cria e pega proximo par de ganho
			// se nao sao extremidades
			// nao faz nada

		}
		
		return listaRoteiros;
	}

	/*
	Esse é o método verifica se um ponto qualquer está alocado em algum roteiro.
	*/
	public boolean verificarPontoNaListaRoteiros(List<Roteiro> listaRoteiros,
			Pontos ponto) {
		boolean pontoRoteiro = false;
		Iterator<Roteiro> iterarListaRoteiro = listaRoteiros.iterator();
		while (iterarListaRoteiro.hasNext()) {
			Roteiro roteiro = (Roteiro) iterarListaRoteiro.next();
			if (roteiro.verificarPontoNoRoteiro(ponto) == true) {
				pontoRoteiro = true;
				break;
			}
		}
		return pontoRoteiro;
	}

	/*
	Esse é um método de busca de pontos pelo atributo do nome do ponto.
	*/
	public Pontos verificaPontoNaListaPontos(List<Pontos> listPontos,
			String nomePonto) {
		Pontos pontoEncontrado = new Pontos();

		Iterator<Pontos> iterar = listPontos.iterator();
		while (iterar.hasNext()) {
			Pontos ponto = (Pontos) iterar.next();
			if (ponto.getNomePonto().equals(nomePonto)) {
				pontoEncontrado = ponto;
			}
		}

		return pontoEncontrado;
	}

	/*
	Esse método verifica na lista de roteiros formados se um determinado ponto está posicionado em algum dos extremos do roteiro, ou seja, se ele é o primeiro ou o último ponto do roteiro e retorna verdadeiro ou falso.
	*/	
	public boolean verificarPontoExtremosNosRoteiros(
			List<Roteiro> listaRoteiros, Pontos ponto) {
		boolean result = false;
		Iterator<Roteiro> iterar = listaRoteiros.iterator();
		while (iterar.hasNext()) {
			Roteiro roteiro = (Roteiro) iterar.next();
			if (roteiro.isPointBeginOrEnd(ponto) == true) {
				result = true;
				break;
			}
		}
		return result;
	}

	
	/*
	Esse método verifica na lista de roteiros formados se um determinado ponto está posicionado em algum dos extremos do roteiro, ou seja, se ele é o primeiro ou o último ponto do roteiro e retorna o roteiro que ele está alocado.
	*/		
	public Roteiro verificarRoteiroDoPonto(List<Roteiro> listaRoteiros,
			Pontos ponto) {
		Roteiro roteiro = new Roteiro();
		Iterator<Roteiro> iterarListaRoteiro = listaRoteiros.iterator();
		while (iterarListaRoteiro.hasNext()) {
			Roteiro roteiroInflado = (Roteiro) iterarListaRoteiro.next();
			if (roteiroInflado.verificarPontoNoRoteiro(ponto) == true) {
				roteiro = roteiroInflado;
				break;
			}
		}
		return roteiro;
	}

	/*
	Esse método verifica se um determinado roteiro ainda em construção, atende a restrição de peso.
	*/	
	public boolean isRestricaoPesoOK(Roteiro roteiro, double limitePeso) {
		boolean respostaPeso = false;
		if (limitePeso == 0) {
			respostaPeso = true;
		} else {
			respostaPeso = roteiro.calcPesoTotal() <= limitePeso;
		}
		return respostaPeso;
	}
	/*
	Esse método verifica se um determinado roteiro ainda em construção, atende a restrição de valor da mercadoria.
	*/	
	public boolean isRestricaoValorOK(Roteiro roteiro, double limiteValor) {
		boolean respostaValor = false;
		if (limiteValor == 0) {
			respostaValor = true;
		} else {
			respostaValor = roteiro.calcValorTotal() <= limiteValor;
		}
		return respostaValor;
	}
	/*
	Esse método verifica se um determinado roteiro ainda em construção, atende a restrição de tempo máximo.
	*/	
	public boolean isRestricaoTempoOK(Roteiro roteiro, double limiteTempo) {
		boolean respostaTempo = false;
		if (limiteTempo == 0) {
			respostaTempo = true;
		} else {
			respostaTempo = roteiro.calcTempoTotalRoteiro() <= limiteTempo;
		}
		return respostaTempo;
	}
	/*
	Esse método faz a união de dois roteiros de acordo com cada ponto de extremidade definido em cada roteiro.
	*/	
	public Roteiro configRoteiroAPartirDeDois(Roteiro rot1, Roteiro rot2,
			Pontos pontoRot1, Pontos pontoRot2, String nomeRoteiro) {

		Roteiro novoRoteiro = new Roteiro();
		novoRoteiro.setPontoOrigem(rot1.getPontoOrigem());
		novoRoteiro.setNome(nomeRoteiro);
		novoRoteiro.setVelocidadeKmPorHora(rot1.getVelocidadeKmPorHora());

		// Unindo pontos do roteiro
		Pontos primeiroPontoRoteiro1 = rot1.getListaPontosRoteiro().get(0);
		Pontos ultimoPontoRoteiro1 = rot1.getListaPontosRoteiro().get(
				rot1.getListaPontosRoteiro().size() - 1);
		Pontos primeiroPontoRoteiro2 = rot2.getListaPontosRoteiro().get(0);
		Pontos ultimoPontoRoteiro2 = rot2.getListaPontosRoteiro().get(
				rot2.getListaPontosRoteiro().size() - 1);

		System.out.println("primeiro ponto no rot1..."
				+ primeiroPontoRoteiro1.getNomePonto());
		System.out.println("ultimoponto ponto no rot1..."
				+ ultimoPontoRoteiro1.getNomePonto());
		System.out.println("primeiro ponto no rot2..."
				+ primeiroPontoRoteiro2.getNomePonto());
		System.out.println("ultimo ponto no rot1..."
				+ ultimoPontoRoteiro2.getNomePonto());

		int posicaoPontoLista = 0;
		if (primeiroPontoRoteiro1 == pontoRot1) {
			if (ultimoPontoRoteiro2 == pontoRot2) {
				System.out
						.println("entrando no if... primeiro ponto rot1 e ultimo ponto rot2...");
				for (Map.Entry<Integer, Pontos> entry : rot2
						.getListaPontosRoteiro().entrySet()) {
					Pontos ponto = entry.getValue();
					novoRoteiro.getListaPontosRoteiro().put(posicaoPontoLista,
							ponto);
					posicaoPontoLista++;
				}
				for (Map.Entry<Integer, Pontos> entry : rot1
						.getListaPontosRoteiro().entrySet()) {
					Pontos ponto = entry.getValue();
					novoRoteiro.getListaPontosRoteiro().put(posicaoPontoLista,
							ponto);
					posicaoPontoLista++;
				}

			} else {
				if (primeiroPontoRoteiro2 == pontoRot2) {
					posicaoPontoLista = rot1.getListaPontosRoteiro().size() - 1;
					System.out
							.println("entrando no if... primeiro ponto rot1 e primeiro ponto rot2...");
					for (Map.Entry<Integer, Pontos> entry : rot1
							.getListaPontosRoteiro().entrySet()) {
						Pontos ponto = entry.getValue();
						novoRoteiro.getListaPontosRoteiro().put(
								posicaoPontoLista, ponto);
						posicaoPontoLista--;
					}
					posicaoPontoLista = rot1.getListaPontosRoteiro().size();
					for (Map.Entry<Integer, Pontos> entry : rot2
							.getListaPontosRoteiro().entrySet()) {
						Pontos ponto = entry.getValue();
						novoRoteiro.getListaPontosRoteiro().put(
								posicaoPontoLista, ponto);
						posicaoPontoLista++;
					}

				}
			}
		}
		if (ultimoPontoRoteiro1 == pontoRot1) {
			if (primeiroPontoRoteiro2 == pontoRot2) {
				System.out
						.println("entrando no if... ult ponto rot1 e primeiro ponto rot2...");
				for (Map.Entry<Integer, Pontos> entry : rot2
						.getListaPontosRoteiro().entrySet()) {
					Pontos ponto = entry.getValue();
					novoRoteiro.getListaPontosRoteiro().put(posicaoPontoLista,
							ponto);
					posicaoPontoLista++;
				}
				for (Map.Entry<Integer, Pontos> entry : rot1
						.getListaPontosRoteiro().entrySet()) {
					Pontos ponto = entry.getValue();
					novoRoteiro.getListaPontosRoteiro().put(posicaoPontoLista,
							ponto);
					posicaoPontoLista++;
				}
			} else {
				if (ultimoPontoRoteiro2 == pontoRot2) {
					System.out
							.println("entrando no if... ult ponto rot1 e ultimo ponto rot2...");
					for (Map.Entry<Integer, Pontos> entry : rot1
							.getListaPontosRoteiro().entrySet()) {
						Pontos ponto = entry.getValue();
						novoRoteiro.getListaPontosRoteiro().put(
								posicaoPontoLista, ponto);
						posicaoPontoLista++;
					}
					posicaoPontoLista = rot2.getListaPontosRoteiro().size() - 1
							+ rot1.getListaPontosRoteiro().size() - 1;

					for (Map.Entry<Integer, Pontos> entry : rot2
							.getListaPontosRoteiro().entrySet()) {
						Pontos ponto = entry.getValue();
						novoRoteiro.getListaPontosRoteiro().put(
								posicaoPontoLista, ponto);
						posicaoPontoLista--;
					}
				}
			}
		}
		return novoRoteiro;
	}

}
