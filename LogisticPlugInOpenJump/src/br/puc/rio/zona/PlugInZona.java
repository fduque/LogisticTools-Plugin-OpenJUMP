package br.puc.rio.zona;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import br.puc.rio.telas.TelaZonaFormacaoDeZonas;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jump.feature.AttributeType;
import com.vividsolutions.jump.feature.BasicFeature;
import com.vividsolutions.jump.feature.Feature;
import com.vividsolutions.jump.feature.FeatureCollection;
import com.vividsolutions.jump.feature.FeatureDataset;
import com.vividsolutions.jump.feature.FeatureSchema;
import com.vividsolutions.jump.workbench.WorkbenchContext;
import com.vividsolutions.jump.workbench.model.Layer;
import com.vividsolutions.jump.workbench.plugin.AbstractPlugIn;
import com.vividsolutions.jump.workbench.plugin.EnableCheckFactory;
import com.vividsolutions.jump.workbench.plugin.MultiEnableCheck;
import com.vividsolutions.jump.workbench.plugin.PlugInContext;
import com.vividsolutions.jump.workbench.ui.renderer.style.BasicStyle;
import com.vividsolutions.jump.workbench.ui.renderer.style.ColorThemingStyle;

public class PlugInZona extends AbstractPlugIn {

	private PlugInContext context;
	private TelaZonaFormacaoDeZonas telaZonaFormacaoDeZonas;

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
	 Nesse caso o método cria a Tela para entrada de dados do procedimento de formação de subzonas.
*/	
public boolean execute(PlugInContext context) throws Exception {
		this.context = context;

		this.telaZonaFormacaoDeZonas = new TelaZonaFormacaoDeZonas(this);
		telaZonaFormacaoDeZonas.setVisible(true);
		return true;
	}

	public PlugInContext getPlugInContext() {
		return this.context;
	}

	/*
	Esse método cria uma lista de distritos a partir de uma layer selecionada.
	*/	
	public List<Distrito> createListDistrito(String nomeLayerComDistritos) throws Exception { // Esse
																				// metodo
																				// le
																				// as
																				// informacoes
																				// da
																				// layer
																				// selecionada,
																				// cria
																				// o
																				// objeto
																				// Zona
																				// e
																				// coloca
																				// numa
																				// Lista
																				// de
																				// Zonas
try{
		ArrayList<Distrito> listaDistrito = new ArrayList<>();
		System.out.println("Criando lista de distritos...");

		if (context.getLayerManager().getLayers() != null) {
			Layer camadaSelecionada = context.getLayerManager().getLayer(
					nomeLayerComDistritos);
			for (Iterator<?> iterator = camadaSelecionada
					.getFeatureCollectionWrapper().iterator(); iterator
					.hasNext();) {
				Feature aFeature = (Feature) iterator.next();
				// Adicionando as propriedades do distrito
				Geometry geom = aFeature.getGeometry();
				String idDistrito = aFeature.getAttribute("Zona").toString();
				// String area = aFeature.getAttribute("Area").toString();
				// criando um novo distrito
				Distrito novo = new Distrito();
				novo.setIdDistrito(idDistrito);
				novo.setGeometryDistrito(geom);

				// adicionando zona a lista
				listaDistrito.add(novo);
			}
		}
		System.out.println("Lista de Distritos Criada!");
		System.out.println("Quantidade de distritos na lista: "
				+ listaDistrito.size());
		return listaDistrito;
}
catch (Exception e) {
	e.printStackTrace();
	throw new Exception();
}
	}

	/*
	Esse método cria uma camada (layer) para uma determinada subzona e com os distritos que pertencem a mesma.
	A camada possui diversos atributos como: nome da subzona, distrito deoposito, distritos da subzona, etc.
	Para cada subzona criada é atribuída uma cor aleatória.
	*/	
	public void criarLayerSubZona(SubZona subZona, String nomeLayer,
			String nomeDaNovaLayer) {

		Layer camadaOriginal = context.getLayerManager().getLayer(nomeLayer);
		HashMap<String, BasicStyle> aMap = new HashMap<String, BasicStyle>();
		HashMap<String, String> aMapToLabel = new HashMap<String, String>();

		System.out.println("Gerando Layer ZonePainted...");

		FeatureSchema fs1 = new FeatureSchema();
		fs1.addAttribute("geometry", AttributeType.GEOMETRY); // 1
		fs1.addAttribute("IdSubZona", AttributeType.STRING);
		fs1.addAttribute("IdDistrito", AttributeType.STRING);
		fs1.addAttribute("InfoGeracaoSubZona", AttributeType.STRING);
		fs1.addAttribute("AreaDistrito", AttributeType.STRING);
		fs1.addAttribute("AreaTotalSubZona", AttributeType.STRING);
		fs1.addAttribute("AreaAdmissivelMeta", AttributeType.STRING);
		fs1.addAttribute("QtdDistritos", AttributeType.STRING);
		fs1.addAttribute("DistritoDeposito", AttributeType.STRING);
		fs1.addAttribute("InfoZona", AttributeType.STRING);
		fs1.addAttribute("InfoDistrito", AttributeType.STRING);

		List<Distrito> listaDeDistritosDaSubZona = subZona
				.getListaDistritosSubZona();

		FeatureCollection fc1 = new FeatureDataset(fs1);

		Iterator<Distrito> iterar = listaDeDistritosDaSubZona.iterator();
		while (iterar.hasNext()) {

			Distrito d = (Distrito) iterar.next();

			if (d.getIdDistrito() != null) {

				Feature feature1 = new BasicFeature(fs1);
				feature1.setGeometry(d.getGeometryDistrito()); // 1
				feature1.setAttribute("IdSubZona",
						String.valueOf(subZona.getIdSubZona()));
				feature1.setAttribute("IdDistrito",
						String.valueOf(d.getIdDistrito()));
				feature1.setAttribute("InfoGeracaoSubZona",
						(subZona.getInformacoesMetodoGeracaoSubZona()));
				feature1.setAttribute("AreaDistrito",
						(String.valueOf(d.getAreaDistrito())));
				feature1.setAttribute("AreaTotalSubZona",
						String.valueOf(subZona.getAreaTotalSubzona()));
				feature1.setAttribute("AreaAdmissivelMeta",
						String.valueOf(subZona.getAreaAdmissivelMeta()));
				feature1.setAttribute("QtdDistritos",
						String.valueOf(subZona.getQtdDistritosSubZona()));
				feature1.setAttribute("DistritoDeposito", String
						.valueOf(subZona.getDistritoDeposito().getIdDistrito()));
				feature1.setAttribute("InfoZona",
						String.valueOf(subZona.getZonaPrincipal().getIdZona()));
				feature1.setAttribute("InfoDistrito",
						String.valueOf(d.toString()));

				fc1.add(feature1); // ADICIONANDO A COLECAO

				BasicStyle aBasicStyle1 = new BasicStyle();
				System.out.println("Cor Aleatorio Gerada");
				aBasicStyle1.setFillColor(getColorAleatory());
				aMap.put(d.getIdDistrito(), aBasicStyle1);
				aMapToLabel.put(d.getIdDistrito(), d.getIdDistrito());

			}
		}

		ColorThemingStyle color = new ColorThemingStyle("SubZona", aMap,
				aMapToLabel, new BasicStyle());

		// adicionando os objetos a camada

		Layer newlayer = new Layer(
		// tem q passar 4 argumentos para criar a sublayer
				nomeDaNovaLayer, // argumento 1 = nome da layer = para nomear a
									// nova sublayer ele pega nome da superlayer
									// e adiciona uma trecho
				context.getLayerManager().generateLayerFillColor(), // argumento
																	// 2 = chama
																	// os
																	// padrões
																	// de cores
																	// e layout
																	// ja usados
																	// pela
																	// layer
																	// principal
				fc1, // argumento 3 = ?
				context.getLayerManager() // argumento 4 = chama o gerenciador
											// de layout do super layout
		);

		context.getLayerManager().addLayer("Carregamentos", newlayer); // insere
																		// a
																		// nova
																		// layer

		boolean firingEvents = newlayer.getLayerManager().isFiringEvents();
		newlayer.getLayerManager().setFiringEvents(false);

		try {
			newlayer.removeStyle(ColorThemingStyle.get(newlayer));
			newlayer.addStyle(color);
			ColorThemingStyle.get(newlayer).setAlpha(
					camadaOriginal.getBasicStyle().getAlpha()); // Alpha = nivel
																// de
																// transparencia
																// da cor
			ColorThemingStyle.get(newlayer).setEnabled(true);
			newlayer.getBasicStyle().setEnabled(true);
		} finally {
			newlayer.getLayerManager().setFiringEvents(firingEvents);
		}
		newlayer.fireAppearanceChanged();

		System.out.println("Layer PaintedZone gerada com sucesso!");

	}

	/*
	Esse método gerar cores aleatórias. Ele é usado para atribuir cores a novas subzonas.
	*/	
	public Color getColorAleatory() {
		Random rand = new Random();

		float r = rand.nextFloat();
		float g = rand.nextFloat();
		float b = rand.nextFloat();

		Color randomColor = new Color(r, g, b);
		randomColor.brighter();

		return randomColor;
	}
	/*
	Esse método retorna um objeto distrito a partir de um id.
	*/	
	public Distrito getDistritoPorId(String idDistrito,
			List<Distrito> listDistrito) {
		Distrito distrito = new Distrito();
		Iterator<Distrito> iterar = listDistrito.iterator();
		while (iterar.hasNext()) {
			Distrito dis = (Distrito) iterar.next();

			if (dis.getIdDistrito().equals(idDistrito)) {
				distrito = dis;
			}
		}
		return distrito;
	}

}
