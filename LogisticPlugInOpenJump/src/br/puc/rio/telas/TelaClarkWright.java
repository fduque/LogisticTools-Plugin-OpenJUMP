package br.puc.rio.telas;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import br.puc.rio.clarkWright.ClarkWrightPlugIn;
import br.puc.rio.clarkWright.Ganho;
import br.puc.rio.clarkWright.Roteiro;
import br.puc.rio.opt2.Metodo2opt;
import br.puc.rio.utilities.GeoUtilidades;
import br.puc.rio.utilities.JNumericField;
import br.puc.rio.utilities.Pontos;

import com.vividsolutions.jump.feature.AttributeType;
import com.vividsolutions.jump.feature.Feature;
import com.vividsolutions.jump.feature.FeatureSchema;
import com.vividsolutions.jump.workbench.model.Layer;

import javax.swing.JCheckBox;

public class TelaClarkWright extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3052962885340058412L;
	/**
	 * 
	 */

	ClarkWrightPlugIn cwPlugIn;
	private JNumericField txtEmKg;
	private JNumericField txtEmMin;
	private JNumericField txtEmR;
	private JNumericField txtValorAlfa;
	private JComboBox<Object> comboBox_selecioneCamada;
	private JComboBox<Object> comboBox_selecioneIDcamada;
	private JComboBox<Object> comboBox_tipocw;
	private JComboBox<Object> comboBox_nomeDeposito;
	private JNumericField textVelocidadeMedia;
	private JCheckBox checkOpt ;
	private JNumericField textField_qtdCiclosOPT;
	
	public TelaClarkWright(ClarkWrightPlugIn aPlugIn) {

		super(aPlugIn.getPlugInContext().getWorkbenchFrame(), "", false);
		setResizable(false);
		setTitle("Clark & Wright");
		this.cwPlugIn= aPlugIn;

		setBounds(100, 100, 463, 405);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 28, 52, 44, 76, 29, 0 };
		gridBagLayout.rowHeights = new int[] { 23, 26, 0, 0, 0, 115, 0, 0, 0,
				0, 0, 0, 0, 30, 0, 0, 0, 27, 0, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 0.0, 1.0, 1.0, 1.0,
				0.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 1.0,
				0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				0.0, 0.0, 0.0, Double.MIN_VALUE };
		getContentPane().setLayout(gridBagLayout);

		JLabel lblSelecioneACamada = new JLabel("Selecione a Camada:");
		GridBagConstraints gbc_lblSelecioneACamada = new GridBagConstraints();
		gbc_lblSelecioneACamada.anchor = GridBagConstraints.WEST;
		gbc_lblSelecioneACamada.insets = new Insets(0, 0, 5, 5);
		gbc_lblSelecioneACamada.gridx = 2;
		gbc_lblSelecioneACamada.gridy = 4;
		getContentPane().add(lblSelecioneACamada, gbc_lblSelecioneACamada);
		
		
		

		GeoUtilidades g = new GeoUtilidades();
		comboBox_selecioneCamada = new JComboBox<Object>(g.getLayers(cwPlugIn.getPlugInContext()));

		GridBagConstraints gbc_comboBox_selecioneCamada = new GridBagConstraints();
		gbc_comboBox_selecioneCamada.gridwidth = 2;
		gbc_comboBox_selecioneCamada.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox_selecioneCamada.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox_selecioneCamada.gridx = 3;
		gbc_comboBox_selecioneCamada.gridy = 4;
		getContentPane().add(comboBox_selecioneCamada,
				gbc_comboBox_selecioneCamada);

		JLabel lblSelecioneOId = new JLabel("Selecione o ID:");
		GridBagConstraints gbc_lblSelecioneOId = new GridBagConstraints();
		gbc_lblSelecioneOId.anchor = GridBagConstraints.WEST;
		gbc_lblSelecioneOId.insets = new Insets(0, 0, 5, 5);
		gbc_lblSelecioneOId.gridx = 2;
		gbc_lblSelecioneOId.gridy = 6;
		getContentPane().add(lblSelecioneOId, gbc_lblSelecioneOId);

		comboBox_selecioneIDcamada = new JComboBox<Object>();
		

		GridBagConstraints gbc_comboBox_selecioneIDcamada = new GridBagConstraints();
		gbc_comboBox_selecioneIDcamada.gridwidth = 2;
		gbc_comboBox_selecioneIDcamada.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox_selecioneIDcamada.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox_selecioneIDcamada.gridx = 3;
		gbc_comboBox_selecioneIDcamada.gridy = 6;
		getContentPane().add(comboBox_selecioneIDcamada,
				gbc_comboBox_selecioneIDcamada);

		JLabel lblInformaoPontoOrigem = new JLabel(
				"Informa\u00E7\u00E3o Ponto Origem:");
		GridBagConstraints gbc_lblInformaoPontoOrigem = new GridBagConstraints();
		gbc_lblInformaoPontoOrigem.anchor = GridBagConstraints.WEST;
		gbc_lblInformaoPontoOrigem.insets = new Insets(0, 0, 5, 5);
		gbc_lblInformaoPontoOrigem.gridx = 2;
		gbc_lblInformaoPontoOrigem.gridy = 7;
		getContentPane()
				.add(lblInformaoPontoOrigem, gbc_lblInformaoPontoOrigem);

		
		
		
		JLabel lblNome = new JLabel("Nome Deposito:");
		GridBagConstraints gbc_lblNome = new GridBagConstraints();
		gbc_lblNome.anchor = GridBagConstraints.EAST;
		gbc_lblNome.insets = new Insets(0, 0, 5, 5);
		gbc_lblNome.gridx = 2;
		gbc_lblNome.gridy = 8;
		getContentPane().add(lblNome, gbc_lblNome);

		comboBox_nomeDeposito = new JComboBox<Object>();
		

		comboBox_selecioneCamada.addActionListener(new ActionListener() {

			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
				setComboAttributes(comboBox_selecioneIDcamada,
						comboBox_selecioneCamada.getSelectedItem());
				
			}
			
			catch (Exception e) {
				e.printStackTrace();
			}
			}
		});
		
		comboBox_selecioneIDcamada.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
						setComboNomeDeposito(comboBox_nomeDeposito,
								comboBox_selecioneCamada.getSelectedItem(),comboBox_selecioneIDcamada.getSelectedItem());
				}
				catch (Exception e) {
						e.printStackTrace();
					}
			}
		});

		GridBagConstraints gbc_comboBox_nomeDeposito = new GridBagConstraints();
		gbc_comboBox_nomeDeposito.gridwidth = 2;
		gbc_comboBox_nomeDeposito.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox_nomeDeposito.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox_nomeDeposito.gridx = 3;
		gbc_comboBox_nomeDeposito.gridy = 8;
		getContentPane().add(comboBox_nomeDeposito, gbc_comboBox_nomeDeposito);

		JLabel lblTipoDeClculo = new JLabel(
				"Tipo de C\u00E1lculo Clark&Wright:");
		GridBagConstraints gbc_lblTipoDeClculo = new GridBagConstraints();
		gbc_lblTipoDeClculo.anchor = GridBagConstraints.WEST;
		gbc_lblTipoDeClculo.insets = new Insets(0, 0, 5, 5);
		gbc_lblTipoDeClculo.gridx = 2;
		gbc_lblTipoDeClculo.gridy = 9;
		getContentPane().add(lblTipoDeClculo, gbc_lblTipoDeClculo);

		comboBox_tipocw = new JComboBox<Object>();
		setComboTiposCW(comboBox_tipocw);

		GridBagConstraints gbc_comboBox_tipocw = new GridBagConstraints();
		gbc_comboBox_tipocw.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox_tipocw.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox_tipocw.gridx = 3;
		gbc_comboBox_tipocw.gridy = 9;
		getContentPane().add(comboBox_tipocw, gbc_comboBox_tipocw);

		txtValorAlfa = new JNumericField();
		txtValorAlfa.setMaxLength(2);
		txtValorAlfa.setPrecision(1);
		txtValorAlfa.setAllowNegative(false);
		txtValorAlfa.setEditable(false);
		txtValorAlfa.setText("valor alfa");

		comboBox_tipocw.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (comboBox_tipocw.getItemAt(
						comboBox_tipocw.getSelectedIndex()).equals("Ajustado")) {
					txtValorAlfa.setText("1");
					txtValorAlfa.setEditable(true);
				} else {
					txtValorAlfa.setText("");
					txtValorAlfa.setEditable(false);
				}

			}
		});

		GridBagConstraints gbc_txtValorAlfa = new GridBagConstraints();
		gbc_txtValorAlfa.insets = new Insets(0, 0, 5, 5);
		gbc_txtValorAlfa.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtValorAlfa.gridx = 4;
		gbc_txtValorAlfa.gridy = 9;
		getContentPane().add(txtValorAlfa, gbc_txtValorAlfa);
		txtValorAlfa.setColumns(10);

		JLabel lblSelecioneAsRestries = new JLabel(
				"Par\u00E2metros do Ve\u00EDculo ou Zona:");
		GridBagConstraints gbc_lblSelecioneAsRestries = new GridBagConstraints();
		gbc_lblSelecioneAsRestries.anchor = GridBagConstraints.WEST;
		gbc_lblSelecioneAsRestries.insets = new Insets(0, 0, 5, 5);
		gbc_lblSelecioneAsRestries.gridx = 2;
		gbc_lblSelecioneAsRestries.gridy = 10;
		getContentPane()
				.add(lblSelecioneAsRestries, gbc_lblSelecioneAsRestries);

		JLabel lblVelocidadeMdia = new JLabel("Velocidade M\u00E9dia");
		GridBagConstraints gbc_lblVelocidadeMdia = new GridBagConstraints();
		gbc_lblVelocidadeMdia.anchor = GridBagConstraints.EAST;
		gbc_lblVelocidadeMdia.insets = new Insets(0, 0, 5, 5);
		gbc_lblVelocidadeMdia.gridx = 2;
		gbc_lblVelocidadeMdia.gridy = 11;
		getContentPane().add(lblVelocidadeMdia, gbc_lblVelocidadeMdia);

		textVelocidadeMedia = new JNumericField();
		textVelocidadeMedia.setMaxLength(3);
		textVelocidadeMedia.setPrecision(2);
		textVelocidadeMedia.setAllowNegative(false);
		GridBagConstraints gbc_textVelocidadeMedia = new GridBagConstraints();
		gbc_textVelocidadeMedia.insets = new Insets(0, 0, 5, 5);
		gbc_textVelocidadeMedia.fill = GridBagConstraints.HORIZONTAL;
		gbc_textVelocidadeMedia.gridx = 3;
		gbc_textVelocidadeMedia.gridy = 11;
		getContentPane().add(textVelocidadeMedia, gbc_textVelocidadeMedia);
		textVelocidadeMedia.setColumns(10);

		JLabel lblKmPorHora = new JLabel("Km por Hora");
		GridBagConstraints gbc_lblKmPorHora = new GridBagConstraints();
		gbc_lblKmPorHora.anchor = GridBagConstraints.WEST;
		gbc_lblKmPorHora.insets = new Insets(0, 0, 5, 5);
		gbc_lblKmPorHora.gridx = 4;
		gbc_lblKmPorHora.gridy = 11;
		getContentPane().add(lblKmPorHora, gbc_lblKmPorHora);

		JLabel lblPesoMximo = new JLabel("Peso M\u00E1ximo");
		GridBagConstraints gbc_lblPesoMximo = new GridBagConstraints();
		gbc_lblPesoMximo.insets = new Insets(0, 0, 5, 5);
		gbc_lblPesoMximo.anchor = GridBagConstraints.EAST;
		gbc_lblPesoMximo.gridx = 2;
		gbc_lblPesoMximo.gridy = 12;
		getContentPane().add(lblPesoMximo, gbc_lblPesoMximo);

		txtEmKg = new JNumericField(10,3);
		txtEmKg.setPrecision(2);
		txtEmKg.setAllowNegative(false);
		txtEmKg.setText("5000");
		GridBagConstraints gbc_txtEmKg = new GridBagConstraints();
		gbc_txtEmKg.insets = new Insets(0, 0, 5, 5);
		gbc_txtEmKg.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtEmKg.gridx = 3;
		gbc_txtEmKg.gridy = 12;
		getContentPane().add(txtEmKg, gbc_txtEmKg);
		txtEmKg.setColumns(10);

		JLabel lblKg = new JLabel("Kg");
		GridBagConstraints gbc_lblKg = new GridBagConstraints();
		gbc_lblKg.anchor = GridBagConstraints.WEST;
		gbc_lblKg.insets = new Insets(0, 0, 5, 5);
		gbc_lblKg.gridx = 4;
		gbc_lblKg.gridy = 12;
		getContentPane().add(lblKg, gbc_lblKg);

		JLabel lblTempoMximo = new JLabel("Tempo M\u00E1ximo");
		GridBagConstraints gbc_lblTempoMximo = new GridBagConstraints();
		gbc_lblTempoMximo.insets = new Insets(0, 0, 5, 5);
		gbc_lblTempoMximo.anchor = GridBagConstraints.EAST;
		gbc_lblTempoMximo.gridx = 2;
		gbc_lblTempoMximo.gridy = 13;
		getContentPane().add(lblTempoMximo, gbc_lblTempoMximo);

		txtEmMin = new JNumericField(10,3);
		txtEmMin.setPrecision(2);
		txtEmMin.setAllowNegative(false);
		txtEmMin.setText("250");
		GridBagConstraints gbc_txtEmMin = new GridBagConstraints();
		gbc_txtEmMin.insets = new Insets(0, 0, 5, 5);
		gbc_txtEmMin.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtEmMin.gridx = 3;
		gbc_txtEmMin.gridy = 13;
		getContentPane().add(txtEmMin, gbc_txtEmMin);
		txtEmMin.setColumns(10);

		JLabel lblMinutos = new JLabel("Minutos");
		GridBagConstraints gbc_lblMinutos = new GridBagConstraints();
		gbc_lblMinutos.anchor = GridBagConstraints.WEST;
		gbc_lblMinutos.insets = new Insets(0, 0, 5, 5);
		gbc_lblMinutos.gridx = 4;
		gbc_lblMinutos.gridy = 13;
		getContentPane().add(lblMinutos, gbc_lblMinutos);

		JLabel lblValorDeCarga = new JLabel("Valor de Carga M\u00E1ximo");
		GridBagConstraints gbc_lblValorDeCarga = new GridBagConstraints();
		gbc_lblValorDeCarga.insets = new Insets(0, 0, 5, 5);
		gbc_lblValorDeCarga.anchor = GridBagConstraints.EAST;
		gbc_lblValorDeCarga.gridx = 2;
		gbc_lblValorDeCarga.gridy = 14;
		getContentPane().add(lblValorDeCarga, gbc_lblValorDeCarga);

		txtEmR =new JNumericField(10,3);
		txtEmR.setPrecision(2);
		txtEmR.setAllowNegative(false);

		txtEmR.setText("10000");
		GridBagConstraints gbc_txtEmR = new GridBagConstraints();
		gbc_txtEmR.insets = new Insets(0, 0, 5, 5);
		gbc_txtEmR.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtEmR.gridx = 3;
		gbc_txtEmR.gridy = 14;
		getContentPane().add(txtEmR, gbc_txtEmR);
		txtEmR.setColumns(10);

		JButton btnRun = new JButton("Run");
		checkOpt = new JCheckBox("Executar 2-Opt");
		
		checkOpt.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(checkOpt.isSelected()==true){
					textField_qtdCiclosOPT.setEditable(true);
				}
				
			}
		});
		
		GridBagConstraints gbc_checkOpt = new GridBagConstraints();
		gbc_checkOpt.insets = new Insets(0, 0, 5, 5);
		gbc_checkOpt.gridx = 2;
		gbc_checkOpt.gridy = 15;
		getContentPane().add(checkOpt, gbc_checkOpt);
		
		textField_qtdCiclosOPT = new JNumericField(4,1);
		textField_qtdCiclosOPT.setEditable(false);
		textField_qtdCiclosOPT.setPrecision(0);
		textField_qtdCiclosOPT.setAllowNegative(false);
		textField_qtdCiclosOPT.setText("1");
		
		GridBagConstraints gbc_textField_qtdCiclosOPT = new GridBagConstraints();
		gbc_textField_qtdCiclosOPT.insets = new Insets(0, 0, 5, 5);
		gbc_textField_qtdCiclosOPT.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_qtdCiclosOPT.gridx = 3;
		gbc_textField_qtdCiclosOPT.gridy = 15;
		getContentPane().add(textField_qtdCiclosOPT, gbc_textField_qtdCiclosOPT);
		textField_qtdCiclosOPT.setColumns(10);
		
		JLabel lblQtdRodadas = new JLabel("Qtd Rodadas");
		GridBagConstraints gbc_lblQtdRodadas = new GridBagConstraints();
		gbc_lblQtdRodadas.anchor = GridBagConstraints.WEST;
		gbc_lblQtdRodadas.insets = new Insets(0, 0, 5, 5);
		gbc_lblQtdRodadas.gridx = 4;
		gbc_lblQtdRodadas.gridy = 15;
		getContentPane().add(lblQtdRodadas, gbc_lblQtdRodadas);
		GridBagConstraints gbc_btnRun = new GridBagConstraints();
		gbc_btnRun.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnRun.insets = new Insets(0, 0, 5, 5);
		gbc_btnRun.gridx = 4;
		gbc_btnRun.gridy = 16;
		getContentPane().add(btnRun, gbc_btnRun);
		btnRun.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				try{
				// TODO Auto-generated method stub
				GeoUtilidades g = new GeoUtilidades();
				double valorAlpha = 0;
				if (comboBox_tipocw.getSelectedItem().equals("Ajustado")) {
					valorAlpha = Double.parseDouble(txtValorAlfa.getText());
				}

				
				if (validaInfoFormulario(textVelocidadeMedia, txtEmKg,
						txtEmMin, txtEmR) == true) {
					
					List<Pontos> litPontos = g.createListPontos(
							comboBox_selecioneCamada.getSelectedItem()
									.toString(), comboBox_selecioneIDcamada
									.getSelectedItem().toString(),cwPlugIn.getPlugInContext());
					List<Ganho> listaGanhos = cwPlugIn
							.createListaGanhoOrdenada(litPontos,
									comboBox_nomeDeposito.getSelectedItem()
											.toString(), valorAlpha);

					List<Roteiro> listRoteiros = cwPlugIn.calcClarkWright(
							listaGanhos, litPontos,
							Double.parseDouble(textVelocidadeMedia.getText()),
							Double.parseDouble(txtEmKg.getText()),
							Double.parseDouble(txtEmMin.getText()),
							Double.parseDouble(txtEmR.getText()));
					
					cwPlugIn.geraLayerCWPorArco(listRoteiros,null,null);
					cwPlugIn.geraLayerCWPoligono(listRoteiros,null,null);
					cwPlugIn.geraLayerCWPoligonoRoteiro(listRoteiros,null,Double.parseDouble(txtEmKg.getText()),Double.parseDouble(txtEmMin.getText()),Double.parseDouble(txtEmR.getText()),null);
					cwPlugIn.geraLayerListRoteiros(listRoteiros, null,Double.parseDouble(txtEmKg.getText()),Double.parseDouble(txtEmMin.getText()),Double.parseDouble(txtEmR.getText()),null);
					
						if (checkOpt.isSelected() == true) {
									int qtdDeMelhoriasLimite = Integer.valueOf(Integer
											.valueOf(textField_qtdCiclosOPT.getText()));
									Metodo2opt opt = new Metodo2opt();
									List<Roteiro> novaListRoteiro = new ArrayList<>();
									Iterator<Roteiro> iterar = listRoteiros.iterator();
									while (iterar.hasNext()) {
										Roteiro r = (Roteiro) iterar.next();
										novaListRoteiro.add(opt.aplicar2optRoteiro(r,
												qtdDeMelhoriasLimite));
									}
									cwPlugIn.geraLayerCWPoligono(novaListRoteiro,
											"2opt",null);
									cwPlugIn.geraLayerCWPorArco(novaListRoteiro, "2opt",null);
						}
			
					
					dispose();
				}
				}
				catch (Exception e) {
					e.printStackTrace();
					
				}
				}
			
		});

		JLabel lblUnidadeMonetria = new JLabel("Unidade Monet\u00E1ria");
		GridBagConstraints gbc_lblUnidadeMonetria = new GridBagConstraints();
		gbc_lblUnidadeMonetria.anchor = GridBagConstraints.WEST;
		gbc_lblUnidadeMonetria.insets = new Insets(0, 0, 5, 5);
		gbc_lblUnidadeMonetria.gridx = 4;
		gbc_lblUnidadeMonetria.gridy = 14;
		getContentPane().add(lblUnidadeMonetria, gbc_lblUnidadeMonetria);
		
		

	}

	private void setComboAttributes(JComboBox<Object> combo,
			Object aSelectedLayer)  throws Exception {
		try {
		if (aSelectedLayer != null) {
			FeatureSchema schema = ((Layer) aSelectedLayer)
					.getFeatureCollectionWrapper().getFeatureSchema();

			((DefaultComboBoxModel<Object>) combo.getModel())
					.removeAllElements();

			for (int i = 0; i < schema.getAttributeCount(); i++) {
				if (schema.getAttributeType(i) == AttributeType.INTEGER
						|| schema.getAttributeType(i) == AttributeType.STRING
						|| schema.getAttributeType(i) == AttributeType.DOUBLE
						|| schema.getAttributeType(i) == AttributeType.OBJECT) {
					((DefaultComboBoxModel<Object>) combo.getModel())
							.addElement(schema.getAttributeName(i));
				}
			}
		}
		}
		catch (Exception e) {
			e.printStackTrace();
			JOptionPane
			.showMessageDialog(
					null,
					"A camada selecionada náo é válida.");
			throw new Exception();
		}
	}

	private void setComboNomeDeposito(JComboBox<Object> combo,
			Object aSelectedLayer,Object nomeDoCampoDeposito) throws Exception {

		
		try{
		((DefaultComboBoxModel<Object>) combo.getModel()).removeAllElements();// zerando
																				// o
																				// combo
																				// box
		String nomeDoCampo = String.valueOf(nomeDoCampoDeposito);
		Layer camadaSelecionada = ((Layer) aSelectedLayer); // pegando a camada

		for (Iterator<?> iterator = camadaSelecionada
				.getFeatureCollectionWrapper().iterator(); iterator.hasNext();) {
			Feature aFeature = (Feature) iterator.next();

			if ((aFeature.getAttribute(nomeDoCampo).toString()) != null) {
				String nomePonto = aFeature.getAttribute(nomeDoCampo)
						.toString();
				((DefaultComboBoxModel<Object>) combo.getModel())
						.addElement(nomePonto);
			}
		}
		}
		catch (Exception e) {
			e.printStackTrace();
			JOptionPane
			.showMessageDialog(
					null,
					"A camada selecionada náo é válida.");
			throw new Exception();
		}
	}

	private void setComboTiposCW(JComboBox<Object> combo) {
		((DefaultComboBoxModel<Object>) combo.getModel()).removeAllElements();// zerando
																				// o
																				// combo
																				// box
		((DefaultComboBoxModel<Object>) combo.getModel()).addElement("Padrão");
		((DefaultComboBoxModel<Object>) combo.getModel())
				.addElement("Ajustado");
	}

	public boolean validaInfoFormulario(JTextField textVelocidadeMedia,
			JTextField txtEmKg, JTextField txtEmMin, JTextField txtEmR) {
		int qtd = 0;
		boolean resposta = false;
		if (textVelocidadeMedia.getText().isEmpty() == false) {
			qtd++;
		}
		if (txtEmKg.getText().isEmpty() == false) {
			qtd++;
		}
		if (txtEmMin.getText().isEmpty() == false) {
			qtd++;
		}
		if (txtEmR.getText().isEmpty() == false) {
			qtd++;
		}

		if (qtd < 4) {
			JOptionPane
					.showMessageDialog(
							null,
							"Não pode haver valor null nos campos de -Parâmetro de Veículo ou Zona-.\n O valor mínimo em cada campo é zero.");
			resposta = false;
		} else {
			resposta = true;
		}

		return resposta;
	}

}
