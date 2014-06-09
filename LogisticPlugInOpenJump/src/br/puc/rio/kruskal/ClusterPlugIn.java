package br.puc.rio.kruskal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.swing.JOptionPane;

import br.puc.rio.telas.TelaCluster;
import br.puc.rio.utilities.GeoUtilidades;
import br.puc.rio.utilities.Matriz;
import br.puc.rio.utilities.Pontos;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LineString;
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

public class ClusterPlugIn extends AbstractPlugIn {

	private PlugInContext context;
	private TelaCluster telaCluster;
	
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
	Esse método é nativo da classe AbstractPlugin. 
	Ele é acionado automaticamente toda vez que o PlugIn é instanciado.
	 Nesse caso o método cria a Tela para entrada de dados do procedimento de formação de clusters.
*/
	public boolean execute(PlugInContext context) throws Exception {

		this.context = context;
		this.telaCluster = new TelaCluster(this);
		telaCluster.setVisible(true);

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

	/*
	Esse método é o principal método da classe. 
	Ele recebe a lista de pontos, lista do tipo matriz ordenada por distância e a quantidade de cluster requerida pelo usuário.
	O método executa o procedimento de kruskal, criando uma árvore geradora mínima e depois retira a quantidade de arcos com os maiores pesos até atingir a quantidade de cluster requerida.
	
*/	

	public List<Matriz> createKruskal(List<Pontos> listaDePontos,
			List<Matriz> listaMatrizOrdenada, int qtdClustersAserCriado) {
		System.out.println("Formando os Clusters...");
		int codNomeCluster = 0; // essa var usada para nomear os clusters
		int qtdDeArcosAGerar = listaDePontos.size() - qtdClustersAserCriado; // o
																				// valor
																				// mínimo
																				// de
																				// entraga
																				// é
																				// 1
		int contador = 0;
		Iterator<Matriz> iterar = listaMatrizOrdenada.iterator();
		while (iterar.hasNext()) {
			contador++;
			Matriz arco = (Matriz) iterar.next();
			Pontos p1 = arco.getI();
			Pontos p2 = arco.getJ();
			int p1Existe = 0;
			int p2Existe = 0;
			System.out.println("Distancia: " + arco.getDistanciaIJ());
			if (p1.getClusterPonto() != null) {
				p1Existe++;
			}
			if (p2.getClusterPonto() != null) {
				p2Existe++;
			}

			System.out.println("P1: " + arco.getI().getNomePonto() + " P2: "
					+ arco.getJ().getNomePonto() + " NomeCluster: "
					+ arco.getNomeCluster());

			// TESTE CENARIOS 1,2

			if (p1Existe + p2Existe == 2) {
				// CENARIO 1
				if (p1.getClusterPontoInt() == p2.getClusterPontoInt()) {
					// faça nada
					System.out.println("P1P2 existem e estão no mesmo cluster");
				}
				// CENARIO 2
				if (p1.getClusterPontoInt() != p2.getClusterPontoInt()) {

					// IGUALA O NOME DOS CLUSTERS COM BASE NO MENOR VALOR
					if (p1.getClusterPontoInt() < p2.getClusterPontoInt()) {
						System.out
								.println("P1P1 existem e são renomeados com base no cluster de p1 = "
										+ p1.getClusterPonto());
						arco.setNomeCluster(p1.getClusterPonto());
						listaDePontos = trocarNomesClusterNaListaPontos(
								p1.getClusterPonto(), listaDePontos,
								p2.getClusterPonto());
						listaMatrizOrdenada = trocarNomesClusterNaListaMatriz(
								p1.getClusterPonto(), listaMatrizOrdenada,
								p2.getClusterPonto());

					} else {
						if (p2.getClusterPontoInt() < p1.getClusterPontoInt()) {
							System.out
									.println("P1P1 existem e são renomeados com base no cluster de p2 = "
											+ p2.getClusterPonto());
							arco.setNomeCluster(p2.getClusterPonto());
							listaDePontos = trocarNomesClusterNaListaPontos(
									p2.getClusterPonto(), listaDePontos,
									p1.getClusterPonto());
							listaMatrizOrdenada = trocarNomesClusterNaListaMatriz(
									p2.getClusterPonto(), listaMatrizOrdenada,
									p1.getClusterPonto());

						}
					}

				}
			}
			// TESTE CENÁRIO 3,4,5

			// CENARIO 5
			if (p1Existe + p2Existe == 0) {
				codNomeCluster++;
				p1.setClusterPonto(Integer.toString(codNomeCluster));
				p2.setClusterPonto(Integer.toString(codNomeCluster));
				arco.setNomeCluster(Integer.toString(codNomeCluster));
				System.out.println("P1P2 nao existem, nome do cluster novo: "
						+ codNomeCluster);
			}
			// CENARIO 3 e 4
			if (p1Existe + p2Existe == 1) {
				if (p1Existe == 1) {
					p2.setClusterPonto(p1.getClusterPonto());
					arco.setNomeCluster(p1.getClusterPonto());

					System.out
							.println("P1 existe, e o cluster de p2 passar a ser igual ao de p1 = "
									+ p1.getClusterPonto());
				} else {
					if (p2Existe == 1) {
						p1.setClusterPonto(p2.getClusterPonto());
						arco.setNomeCluster(p2.getClusterPonto());

						System.out
								.println("P2 existe, e o cluster de p1 passar a ser igual ao de p2 = "
										+ p2.getClusterPonto());

					}
				}
			}

			System.out.println("P1: " + arco.getI().getNomePonto() + " P2: "
					+ arco.getJ().getNomePonto() + " NomeCluster: "
					+ arco.getNomeCluster());
			System.out.println(" ");
			if (getQtdArcosComCluster(listaMatrizOrdenada) == qtdDeArcosAGerar) {
				System.out.println("Qtd de arcos: "
						+ listaMatrizOrdenada.size() + " Arco de parada: "
						+ contador);
				listaMatrizOrdenada = igualarNomesClustersNaLista(
						listaMatrizOrdenada, listaDePontos);

				System.out.println("Lista de pontos: "
						+ listaDePontos.toString());

				// nome

				break;
			}

		}
		System.out.println("Clusters Criados!");
		return listaMatrizOrdenada;
	}

	/*
Esse método atribui o nome de arco entre dois pontos o mesmo nome do cluster a que os pontos pertencem.
*/	
	public List<Matriz> igualarNomesClustersNaLista(List<Matriz> listaDist,
			List<Pontos> listaPont) {

		for (int i = 0; i < listaPont.size(); i++) {

			Pontos ponto = listaPont.get(i);

			Iterator<Matriz> iterar = listaDist.iterator();
			while (iterar.hasNext()) {
				Matriz arco = (Matriz) iterar.next();
				Pontos p1 = arco.getI();
				Pontos p2 = arco.getJ();

				if (arco.getNomeCluster() != null) {

					if (ponto.getNomePonto().equals(p1.getNomePonto())) {
						arco.setNomeCluster(ponto.getClusterPonto());

					}

					if (ponto.getNomePonto().equals(p2.getNomePonto())) {
						arco.setNomeCluster(ponto.getClusterPonto());
					}
				}
			}

		}

		return listaDist;
	}

	/*
Esse é o método principal da classe. Ele recebe os pârâmetros de criação dos clusters definidos pelo usuário e organiza todos os métodos da classe até a criação efetiva do cluster.
*/		
	public void createClusters(String nomeDaCamada, String nomeDoAtributo,
			int qtdClusters) {

		try {
		GeoUtilidades g = new GeoUtilidades();
		List<Pontos> listaPontosNaCamada = g.createListPontosCoordenadaNome(nomeDaCamada,
				nomeDoAtributo,context);
		List<Matriz> listaDeArcosEntrePontosOrdenada = g.createListMatrizDistanciasOrdenada(
				nomeDaCamada, nomeDoAtributo, listaPontosNaCamada);
		List<Matriz> listaKruskal = createKruskal(listaPontosNaCamada,
				listaDeArcosEntrePontosOrdenada, qtdClusters);
		geraLayerArcosKruskal(listaKruskal);
		geraLayerPontosKruskal(listaPontosNaCamada);
		}
		catch (Exception e) {
			e.printStackTrace();
			JOptionPane
			.showMessageDialog(
					null,
					"A camada selecionada não é válida.");
		}
	}
	/*
Esse método faz o somatório da quantidade de arcos alocados em todos os clusters.
*/	
	
	public int getQtdArcosComCluster(List<Matriz> listaMatriz) {
		int qtdDeArcosComClusterAlocado = 0;

		Iterator<Matriz> iterar = listaMatriz.iterator();
		while (iterar.hasNext()) {
			Matriz matriz = (Matriz) iterar.next();
			if (matriz.getNomeCluster() != null) {
				qtdDeArcosComClusterAlocado++;
			}
		}

		return qtdDeArcosComClusterAlocado;
	}

	/*
O método padroniza os nomes dos clusters na lista Matriz.
*/	
	public List<Matriz> trocarNomesClusterNaListaMatriz(
			String nomeAserInserido, List<Matriz> listaAserPercorrida,
			String nomeAserTrocado) {

		for (int i = 0; i < listaAserPercorrida.size(); i++) {
			if (listaAserPercorrida.get(i).getNomeCluster() != null) {

				if (listaAserPercorrida.get(i).getNomeCluster()
						.equals(nomeAserTrocado)) {
					listaAserPercorrida.get(i).setNomeCluster(nomeAserInserido);
				}
			}
		}
		return listaAserPercorrida;
	}
	/*
Esse método padronizada os nomes de clusters na lista de pontos.
*/	
	public List<Pontos> trocarNomesClusterNaListaPontos(
			String nomeAserInserido, List<Pontos> listaAserPercorrida,
			String nomeAserTrocado) {

		for (int i = 0; i < listaAserPercorrida.size(); i++) {
			if (listaAserPercorrida.get(i).getClusterPonto() != null) {
				if (listaAserPercorrida.get(i).getClusterPonto()
						.equals(nomeAserTrocado)) {
					listaAserPercorrida.get(i)
							.setClusterPonto(nomeAserInserido);
				}
			}
		}
		return listaAserPercorrida;
	}

	/*
Esse método cria a camada (Layer) e plota os arcos dos clusters gerados com os seguintes atributos: comprimento do arco, nome do cluster, nome do arco, coordenadas x e y.
*/	
	public void geraLayerArcosKruskal(List<Matriz> listaMatriz) {

		System.out.println("Gerando Layer Result...");
		FeatureSchema fs1 = new FeatureSchema();
		fs1.addAttribute("geometry", AttributeType.GEOMETRY);
		fs1.addAttribute("distanciaIJ", AttributeType.DOUBLE);
		fs1.addAttribute("nomeCluster", AttributeType.STRING);
		fs1.addAttribute("nomeAresta", AttributeType.STRING);
		fs1.addAttribute("pontoI", AttributeType.STRING);
		fs1.addAttribute("pontoJ", AttributeType.STRING);

		FeatureCollection fc1 = new FeatureDataset(fs1);

		Iterator<Matriz> iterar = listaMatriz.iterator();
		while (iterar.hasNext()) {

			Matriz aresta = (Matriz) iterar.next();
			// System.out.println(aresta.getNomeCluster());

			if (aresta.getNomeCluster() != null) {

				GeometryFactory gf = new GeometryFactory();
				Coordinate c1 = new Coordinate(aresta.getI().getEixoXponto(),
						aresta.getI().getEixoYponto());
				Coordinate c2 = new Coordinate(aresta.getJ().getEixoXponto(),
						aresta.getJ().getEixoYponto());
				Collection<Coordinate> listCord = new ArrayList<Coordinate>();
				listCord.add(c1);
				listCord.add(c2);
				Coordinate[] coords = (Coordinate[]) listCord
						.toArray(new Coordinate[listCord.size()]);
				LineString line = gf.createLineString(coords);

				Feature feature1 = new BasicFeature(fs1); // ADICIONANDO UM
															// REGISTRO
				feature1.setGeometry(line);
				feature1.setAttribute("distanciaIJ", aresta.getDistanciaIJ());
				feature1.setAttribute("nomeCluster", aresta.getNomeCluster());
				String nomeAresta = aresta.getI().getNomePonto() + "-"
						+ aresta.getJ().getNomePonto();
				feature1.setAttribute("nomeAresta", nomeAresta);
				feature1.setAttribute("pontoI", aresta.getI().getNomePonto());
				feature1.setAttribute("pontoJ", aresta.getJ().getNomePonto());

				fc1.add(feature1); // ADICIONANDO A COLECAO
			}
		}

		// adicionando os objetos a camada
		context.addLayer(StandardCategoryNames.WORKING, "Layer Cluster - Arcos",
				fc1);
	}

	/*
	Esse método cria a camada (Layer) e plota os pontos que fazem parte dos clusters gerados com os seguintes atributos: nome do cluster e nome do ponto.
	*/	
	public void geraLayerPontosKruskal(List<Pontos> listaPontos) {

		System.out.println("Gerando Layer Pontos Result...");
		FeatureSchema fs1 = new FeatureSchema();
		fs1.addAttribute("geometry", AttributeType.GEOMETRY);
		fs1.addAttribute("nomeCluster", AttributeType.STRING);
		fs1.addAttribute("nomePonto", AttributeType.STRING);

		FeatureCollection fc1 = new FeatureDataset(fs1);

		Iterator<Pontos> iterar = listaPontos.iterator();
		while (iterar.hasNext()) {

			Pontos ponto = (Pontos) iterar.next();
			GeometryFactory gf = new GeometryFactory();
			Coordinate c1 = new Coordinate(ponto.getEixoXponto(),
					ponto.getEixoYponto());
			com.vividsolutions.jts.geom.Point point = gf.createPoint(c1);

			Feature feature1 = new BasicFeature(fs1); // ADICIONANDO UM REGISTRO

			feature1.setGeometry(point);
			if (ponto.getClusterPonto() != null) {
				feature1.setAttribute("nomeCluster", ponto.getClusterPonto());
			} else {
				feature1.setAttribute("nomeCluster", "Cluster1Ponto");
			}

			feature1.setAttribute("nomePonto", ponto.getNomePonto());

			fc1.add(feature1); // ADICIONANDO A COLECAO

		}

		// adicionando os objetos a camada
		context.addLayer(StandardCategoryNames.WORKING,
				"Layer Cluster - Pontos ", fc1);
	}

}
