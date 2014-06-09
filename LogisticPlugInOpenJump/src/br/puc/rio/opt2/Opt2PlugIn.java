package br.puc.rio.opt2;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import br.puc.rio.clarkWright.Roteiro;
import br.puc.rio.telas.Tela2opt;
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

public class Opt2PlugIn extends AbstractPlugIn {

	private PlugInContext context;
	private Tela2opt tela2opt;
	

	public static MultiEnableCheck createEnableCheck(
			WorkbenchContext workbenchContext) {
		EnableCheckFactory checkFactory = new EnableCheckFactory(
				workbenchContext);

		return new MultiEnableCheck().add(
				checkFactory.createAtLeastNLayersMustExistCheck(1)).add(
				checkFactory.createTaskWindowMustBeActiveCheck());
	}

/*
	Esse método é nativo da classe AbstractPlugin. 
	Ele é acionado automaticamente toda vez que o PlugIn é instanciado.
*/
	public boolean execute(PlugInContext context) throws Exception {

		this.context = context;
		this.tela2opt = new Tela2opt(this);
		tela2opt.setVisible(true);

		// this.telaUploadFile = new TelaUploadFile(this);
		// telaUploadFile.setVisible(true);

		return true;
	}
	/*
	Esse método retorna a instância do PlugIn. No caso, o ClusterPlugin.
*/	
	public PlugInContext getPlugInContext() {
		return context;

	}
	public void geraLayerPorRoteiro (Roteiro roteiro, String textoAdicionalNomeCamada,double cargaMax,double tempoMax,double valorMax ){
		
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
	
public void geraLayerCWPoligonoRoteiro (List<Roteiro> listaRoteiros,String textoAdicionalNomeCamada,double cargaMax,double tempoMax,double valorMax) {
		
		
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
			double valorMax) {

		
		Iterator<Roteiro> iterar = listRoteiro.iterator();
		while (iterar.hasNext()) {

			Roteiro roteiro = (Roteiro) iterar.next();
			geraLayerPorRoteiro(roteiro,roteiro.getNome(), cargaMax,
					tempoMax, valorMax);

		}
	}

	
public void geraLayerCWPoligono (Roteiro roteiro,String textoAdicionalNomeCamada) {
		
		
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
			
		
	
			//adicionando os objetos a camada
			context.addLayer(StandardCategoryNames.WORKING, "Layer Roteiros Clark & Wright - Visão de Poligono Zona -" + textoAdicionalNomeCamada, fc1);
			System.out.println("Layer CW gerada com sucesso!");
	}

public void geraLayerCWPorArco (List<Roteiro> listaRoteiros,String textoAdicionalNomeCamada) {
	
	
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
				context.addLayer(StandardCategoryNames.WORKING, "Layer Roteiros Clark & Wright -" + textoAdicionalNomeCamada, fc1);
				System.out.println("Layer CW gerada com sucesso!");
}


}
