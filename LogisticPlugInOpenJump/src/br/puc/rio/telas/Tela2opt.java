package br.puc.rio.telas;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;

import br.puc.rio.clarkWright.ClarkWrightPlugIn;
import br.puc.rio.clarkWright.Roteiro;
import br.puc.rio.opt2.Metodo2opt;
import br.puc.rio.opt2.Opt2PlugIn;
import br.puc.rio.utilities.GeoUtilidades;
import br.puc.rio.utilities.JNumericField;
import br.puc.rio.utilities.Pontos;

import com.vividsolutions.jump.feature.Feature;
import com.vividsolutions.jump.workbench.model.Layer;

public class Tela2opt extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7873224945328218193L;
	Opt2PlugIn opt2PlugIn;
	ClarkWrightPlugIn cwpPlugIn;
	private JComboBox <Object> comboBox_camadaRoteiro;
	private JNumericField textField_qtdRodadas;

	public Tela2opt(Opt2PlugIn aPlugIn) {

		super(aPlugIn.getPlugInContext().getWorkbenchFrame(),
				"Importar Arquivo", false);
		setTitle("Procedimento para Melhoria de Roteiros 2-opt");
		this.opt2PlugIn = aPlugIn;

		setBounds(100, 100, 635, 157);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 28, 52, 127, 76, 57, 0, 0,
				29, 0 };
		gridBagLayout.rowHeights = new int[] { 23, 26, 0, 0, 115, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 0.0, 1.0, 1.0, 1.0,
				1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 1.0,
				0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		getContentPane().setLayout(gridBagLayout);
		
		GeoUtilidades g = new GeoUtilidades();
		
		JLabel lblSelecioneACamada = new JLabel("Selecione a camada do Roteiro:");
		GridBagConstraints gbc_lblSelecioneACamada = new GridBagConstraints();
		gbc_lblSelecioneACamada.gridwidth = 3;
		gbc_lblSelecioneACamada.anchor = GridBagConstraints.WEST;
		gbc_lblSelecioneACamada.insets = new Insets(0, 0, 5, 5);
		gbc_lblSelecioneACamada.gridx = 1;
		gbc_lblSelecioneACamada.gridy = 5;
		getContentPane().add(lblSelecioneACamada, gbc_lblSelecioneACamada);
		comboBox_camadaRoteiro = new JComboBox<Object>(g.getLayers(opt2PlugIn.getPlugInContext()));
		
		GridBagConstraints gbc_comboBox_camadaRoteiro = new GridBagConstraints();
		gbc_comboBox_camadaRoteiro.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox_camadaRoteiro.gridwidth = 2;
		gbc_comboBox_camadaRoteiro.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox_camadaRoteiro.gridx = 4;
		gbc_comboBox_camadaRoteiro.gridy = 5;
		getContentPane().add(comboBox_camadaRoteiro, gbc_comboBox_camadaRoteiro);
		
		
	
		JLabel lblQuantidadeDeRodadas = new JLabel("Quantidade  M\u00E1xima de Melhorias");
		GridBagConstraints gbc_lblQuantidadeDeRodadas = new GridBagConstraints();
		gbc_lblQuantidadeDeRodadas.anchor = GridBagConstraints.WEST;
		gbc_lblQuantidadeDeRodadas.gridwidth = 3;
		gbc_lblQuantidadeDeRodadas.insets = new Insets(0, 0, 5, 5);
		gbc_lblQuantidadeDeRodadas.gridx = 1;
		gbc_lblQuantidadeDeRodadas.gridy = 6;
		getContentPane().add(lblQuantidadeDeRodadas, gbc_lblQuantidadeDeRodadas);
		
		textField_qtdRodadas =new JNumericField(4,3);
		textField_qtdRodadas.setPrecision(0);
		textField_qtdRodadas.setAllowNegative(false);	
		textField_qtdRodadas.setToolTipText("Insira a quantidade de re-in\u00EDcio desejada.");
		textField_qtdRodadas.setText("1");
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.insets = new Insets(0, 0, 5, 5);
		gbc_textField.gridx = 5;
		gbc_textField.gridy = 6;
		getContentPane().add(textField_qtdRodadas, gbc_textField);
		textField_qtdRodadas.setColumns(10);
		
		JButton btnBuscarMelhoria = new JButton("Buscar Melhoria");
		
		
		btnBuscarMelhoria.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				Roteiro roteiroSelecionado = getSomeAttributoLayer(comboBox_camadaRoteiro.getSelectedItem());
				
				Metodo2opt opt = new Metodo2opt();
				Roteiro posOptRoteiro = opt.aplicar2optRoteiro(roteiroSelecionado, Integer.valueOf(textField_qtdRodadas.getText().toString()));
				System.out.println("Roteiroantigo" + roteiroSelecionado.getNomePontosNaLista());
				System.out.println("RoteiroNovo" + posOptRoteiro.getNomePontosNaLista());
				cwpPlugIn = new ClarkWrightPlugIn();
				cwpPlugIn.geraLayerPorRoteiro(posOptRoteiro, "PósOpt: Roteiro -"+posOptRoteiro.getNome(), 0, 0, 0,opt2PlugIn.getPlugInContext());
				List<Roteiro> listRoteiro = new ArrayList<>();
				listRoteiro.add(posOptRoteiro);
				cwpPlugIn.geraLayerCWPoligono(listRoteiro,  "PósOpt: Roteiro -"+posOptRoteiro.getNome(),opt2PlugIn.getPlugInContext());
				cwpPlugIn.geraLayerCWPoligonoRoteiro(listRoteiro,  "PósOpt: Roteiro -"+posOptRoteiro.getNome(), 0,0, 0,opt2PlugIn.getPlugInContext());
				cwpPlugIn.geraLayerCWPorArco(listRoteiro, "PósOpt: Roteiro -"+posOptRoteiro.getNome(),opt2PlugIn.getPlugInContext());
			}
			
			});
		
		GridBagConstraints gbc_btnBuscarMelhoria = new GridBagConstraints();
		gbc_btnBuscarMelhoria.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnBuscarMelhoria.insets = new Insets(0, 0, 5, 5);
		gbc_btnBuscarMelhoria.gridx = 5;
		gbc_btnBuscarMelhoria.gridy = 7;
		getContentPane().add(btnBuscarMelhoria, gbc_btnBuscarMelhoria);

	}
	
	
	private Roteiro getSomeAttributoLayer (Object aSelectedLayer) {
		Roteiro roteiro = new Roteiro();
		Layer camadaSelecionada = ((Layer) aSelectedLayer); // pegando a camada
		
		//Informacoes a recuperar na layer
		//Lista de Pontos no roteiro e suas informacoes
		//NomeRoteiro
		//NomeDeposito
		//Velocidade Media
		
		
		//CRIANDO UMA LISTA DE PONTOS
		
		for (Iterator<?> iterator = camadaSelecionada
				.getFeatureCollectionWrapper().iterator(); iterator.hasNext();) {
			Feature aFeature = (Feature) iterator.next();
			
				String nomePonto = (String) aFeature.getAttribute("NOME_PONTO");
				double cargaPonto =  Double.parseDouble(aFeature.getAttribute("CARGA_PONTO").toString());
				double valorPonto = Double.parseDouble( aFeature.getAttribute("VALOR_PONTO").toString());
				double tempoPonto = Double.parseDouble(  aFeature.getAttribute("TEMPO_PONTO").toString());
				String obsPonto = (String) aFeature.getAttribute("OBS_PONTO");
				
				GeoUtilidades g =  new GeoUtilidades();
				String[] coordPonto = g.separaCoordenadaString(aFeature
						.getAttribute("geometry").toString());
				String coordPontoX = coordPonto[0];
				String coordPontoY = coordPonto[1];
				
			Pontos ponto = new Pontos();
				ponto.setNomePonto(nomePonto);
				ponto.setEixoXponto(Double.parseDouble(coordPontoX));
				ponto.setEixoYponto(Double.parseDouble(coordPontoY));
				ponto.setQuantidadePonto(cargaPonto);
				ponto.setValorCargaPonto(valorPonto);
				ponto.setTempoPonto(tempoPonto);
				ponto.setObservacao(obsPonto);
			
			if(ponto.getNomePonto().equals((String)aFeature.getAttribute("DEPOSITO"))){
				roteiro.setPontoOrigem(ponto);
				roteiro.setNome((String)aFeature.getAttribute("ROTEIRO"));
				roteiro.setVelocidadeKmPorHora( Double.parseDouble( aFeature.getAttribute("VELOCIDADE_MEDIA_ROTEIRO").toString()));
			} else {
				roteiro.addPontoRoteiro(ponto);
			}
			
		}
		return roteiro;
	}
	
	
	
	
	
	
}
