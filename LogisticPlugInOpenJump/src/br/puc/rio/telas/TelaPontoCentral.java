package br.puc.rio.telas;

import java.awt.Color;
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
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import br.puc.rio.pontoCentral.CalcPCentralEuclidiano;
import br.puc.rio.pontoCentral.CalcPCentralRetangularDerivada;
import br.puc.rio.pontoCentral.CalcPCentralRetangularFibonacci;
import br.puc.rio.pontoCentral.PontoCentralPlugIn;
import br.puc.rio.utilities.GeoUtilidades;
import br.puc.rio.utilities.JNumericField;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jump.feature.AttributeType;
import com.vividsolutions.jump.feature.BasicFeature;
import com.vividsolutions.jump.feature.Feature;
import com.vividsolutions.jump.feature.FeatureCollection;
import com.vividsolutions.jump.feature.FeatureDataset;
import com.vividsolutions.jump.feature.FeatureSchema;
import com.vividsolutions.jump.workbench.model.StandardCategoryNames;
import com.vividsolutions.jump.workbench.plugin.PlugInContext;

public class TelaPontoCentral extends JDialog {

	private static final long serialVersionUID = 7873224945328218193L;
	PontoCentralPlugIn analiseEspacialPlugIn;
	TelaPontoCentralAddLayer telaAddLayer;
	private TelaPontoCentral telaPontoCentralContext;
	private JButton btnAdicionarLista;
	br.puc.rio.openJumpExt.CarregamentoRedeExtension classeExtensao;
	private JNumericField textField_eixoX;
	private JNumericField textField_eixoY;
	private JNumericField textField_peso;
	private List<String> listString = new ArrayList<>();
	private JTable table;
	private JNumericField textField_tipoFibonacci;
	private JComboBox<Object> comboBox_tipoFibonacci;
	private JNumericField txtPrecisaoEuclidiano;
	private JTextField txtNomePonto;
	private JTextField lblNewLabel_resultEixoX;
	private JTextField lblNewLabel_resultEixoY;


	public TelaPontoCentral(PontoCentralPlugIn aPlugIn) {

		super(aPlugIn.getPlugInContext().getWorkbenchFrame(),
				"Métodos para calcular Ponto Central", false);
		setResizable(false);
		setTitle("Procedimentos para Calcular Ponto Central");
		this.analiseEspacialPlugIn = aPlugIn;
		this.telaPontoCentralContext = this;

		setBounds(100, 100, 746, 536);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 93, 120, 100, 80, 102,
				124, 0, 93, 29, 0 };
		gridBagLayout.rowHeights = new int[] { 23, 26, 0, 0, 0, 0, 0, 115, 26, 0, 0, 0, 27, 0, 0, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 1.0, 1.0, 1.0,
				1.0, 1.0, 1.0, 1.0, 0.0, 0.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		getContentPane().setLayout(gridBagLayout);

		JLabel lblDefina = new JLabel("1 - Preencha a lista de Coordenadas");
		lblDefina.setForeground(Color.BLACK);
		lblDefina.setBackground(Color.ORANGE);
		GridBagConstraints gbc_lblDefina = new GridBagConstraints();
		gbc_lblDefina.anchor = GridBagConstraints.WEST;
		gbc_lblDefina.gridwidth = 2;
		gbc_lblDefina.insets = new Insets(0, 0, 5, 5);
		gbc_lblDefina.gridx = 1;
		gbc_lblDefina.gridy = 1;
		getContentPane().add(lblDefina, gbc_lblDefina);

		JLabel lblInformaoDaCoordenada = new JLabel(
				"Insira os pontos manualmente (Add Point) OU selecione uma Layer depois pontos (Add Layer): ");
		lblInformaoDaCoordenada.setBackground(Color.ORANGE);
		GridBagConstraints gbc_lblInformaoDaCoordenada = new GridBagConstraints();
		gbc_lblInformaoDaCoordenada.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblInformaoDaCoordenada.gridwidth = 8;
		gbc_lblInformaoDaCoordenada.insets = new Insets(0, 0, 5, 5);
		gbc_lblInformaoDaCoordenada.gridx = 1;
		gbc_lblInformaoDaCoordenada.gridy = 2;
		getContentPane().add(lblInformaoDaCoordenada,
				gbc_lblInformaoDaCoordenada);


		JLabel lblNomePonto = new JLabel("ID:");
		lblNomePonto.setBackground(Color.ORANGE);
		GridBagConstraints gbc_lblNomePonto = new GridBagConstraints();
		gbc_lblNomePonto.insets = new Insets(0, 0, 5, 5);
		gbc_lblNomePonto.gridx = 1;
		gbc_lblNomePonto.gridy = 3;
		getContentPane().add(lblNomePonto, gbc_lblNomePonto);
				
						JLabel lblEixoX = new JLabel("Eixo - x:");
						lblEixoX.setBackground(Color.ORANGE);
						GridBagConstraints gbc_lblEixoX = new GridBagConstraints();
						gbc_lblEixoX.insets = new Insets(0, 0, 5, 5);
						gbc_lblEixoX.gridx = 2;
						gbc_lblEixoX.gridy = 3;
						getContentPane().add(lblEixoX, gbc_lblEixoX);
		
				JLabel lblEixoY = new JLabel("Eixo - y:");
				lblEixoY.setBackground(Color.ORANGE);
				GridBagConstraints gbc_lblEixoY = new GridBagConstraints();
				gbc_lblEixoY.insets = new Insets(0, 0, 5, 5);
				gbc_lblEixoY.gridx = 3;
				gbc_lblEixoY.gridy = 3;
				getContentPane().add(lblEixoY, gbc_lblEixoY);

		JLabel lblPeso = new JLabel("Peso:");
		lblPeso.setBackground(Color.ORANGE);
		GridBagConstraints gbc_lblPeso = new GridBagConstraints();
		gbc_lblPeso.insets = new Insets(0, 0, 5, 5);
		gbc_lblPeso.gridx = 4;
		gbc_lblPeso.gridy = 3;
		getContentPane().add(lblPeso, gbc_lblPeso);

		txtNomePonto = new JTextField();
		txtNomePonto.setToolTipText("Insira um identificador \u00FAnico para o ponto. Exemplo: PontoCliente17.");
		txtNomePonto.setColumns(10);
		txtNomePonto.setBackground(Color.ORANGE);
		GridBagConstraints gbc_txtNomePonto = new GridBagConstraints();
		gbc_txtNomePonto.insets = new Insets(0, 0, 5, 5);
		gbc_txtNomePonto.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtNomePonto.gridx = 1;
		gbc_txtNomePonto.gridy = 4;
		getContentPane().add(txtNomePonto, gbc_txtNomePonto);
				
						textField_eixoX = new JNumericField();
						textField_eixoX.setMaxLength(50);
						textField_eixoX.setPrecision(20);
						textField_eixoX.setAllowNegative(true);
						textField_eixoX.setText("0.000");
						
						textField_eixoX.setToolTipText("Insira o valor da coordenada do eixo X do ponto. Exemplo: 35.83");
						textField_eixoX.setBackground(Color.ORANGE);
						GridBagConstraints gbc_textField_eixoX = new GridBagConstraints();
						gbc_textField_eixoX.insets = new Insets(0, 0, 5, 5);
						gbc_textField_eixoX.fill = GridBagConstraints.HORIZONTAL;
						gbc_textField_eixoX.gridx = 2;
						gbc_textField_eixoX.gridy = 4;
						getContentPane().add(textField_eixoX, gbc_textField_eixoX);
						textField_eixoX.setColumns(10);
		
				textField_eixoY = new JNumericField();
				textField_eixoY.setMaxLength(50);
				textField_eixoY.setPrecision(20);
				textField_eixoY.setAllowNegative(true);
				textField_eixoY.setText("0.000");
				
				textField_eixoY.setToolTipText("Insira o valor da coordenada do eixo Y do ponto. Exemplo: 55.98");
				textField_eixoY.setBackground(Color.ORANGE);
				GridBagConstraints gbc_textField_eixoY = new GridBagConstraints();
				gbc_textField_eixoY.insets = new Insets(0, 0, 5, 5);
				gbc_textField_eixoY.fill = GridBagConstraints.HORIZONTAL;
				gbc_textField_eixoY.gridx = 3;
				gbc_textField_eixoY.gridy = 4;
				getContentPane().add(textField_eixoY, gbc_textField_eixoY);
				textField_eixoY.setColumns(10);

		textField_peso = new JNumericField();
		textField_peso.setMaxLength(10);
		textField_peso.setPrecision(3);
		textField_peso.setAllowNegative(false);
		textField_peso.setText("0");
		
		textField_peso.setToolTipText("Insira o atribuito a ser minizado pelo ponto central. ");
		textField_peso.setBackground(Color.ORANGE);
		GridBagConstraints gbc_textField_peso = new GridBagConstraints();
		gbc_textField_peso.insets = new Insets(0, 0, 5, 5);
		gbc_textField_peso.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_peso.gridx = 4;
		gbc_textField_peso.gridy = 4;
		getContentPane().add(textField_peso, gbc_textField_peso);
		textField_peso.setColumns(10);

		btnAdicionarLista = new JButton();
		btnAdicionarLista.setToolTipText("Clique para registrar os dados do ponto cadastrado.");

		btnAdicionarLista.setText("Add Point");
		GridBagConstraints gbc_botao = new GridBagConstraints();
		gbc_botao.insets = new Insets(0, 0, 5, 5);
		gbc_botao.fill = GridBagConstraints.BOTH;
		gbc_botao.gridx = 5;
		gbc_botao.gridy = 4;
		getContentPane().add(btnAdicionarLista, gbc_botao);

		btnAdicionarLista.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String registro = null;

				registro = txtNomePonto.getText() + ";"
						+ textField_eixoX.getText() + ";"
						+ textField_eixoY.getText() + ";"
						+ textField_peso.getText();
				listString.add(registro);
				getTableModelConsulta();
				
				GeoUtilidades g = new GeoUtilidades();
				g.desenhaPonto((Double
						.parseDouble(textField_eixoX.getText().replaceAll(",",
								"."))), (Double.parseDouble(textField_eixoY
						.getText().replaceAll(",", "."))), txtNomePonto
						.getText(),textField_peso.getText(),analiseEspacialPlugIn.getPlugInContext());
			}

		});

		JButton btnAddLayer = new JButton();
		btnAddLayer.setToolTipText("Clique para selecionar uma camada (Layer) previamente carregada no OpenJump.");
		btnAddLayer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				telaAddLayer = new TelaPontoCentralAddLayer(analiseEspacialPlugIn,
						telaPontoCentralContext);
				telaAddLayer.setVisible(true);

			}
		});
		
		JLabel lblSelecionarLayer = new JLabel("Selecionar Layer:");
		GridBagConstraints gbc_lblSelecionarLayer = new GridBagConstraints();
		gbc_lblSelecionarLayer.gridwidth = 2;
		gbc_lblSelecionarLayer.anchor = GridBagConstraints.EAST;
		gbc_lblSelecionarLayer.insets = new Insets(0, 0, 5, 5);
		gbc_lblSelecionarLayer.gridx = 6;
		gbc_lblSelecionarLayer.gridy = 4;
		getContentPane().add(lblSelecionarLayer, gbc_lblSelecionarLayer);
		btnAddLayer.setText("Add Layer");
		GridBagConstraints gbc_btnAddLayer = new GridBagConstraints();
		gbc_btnAddLayer.insets = new Insets(0, 0, 5, 5);
		gbc_btnAddLayer.gridx = 8;
		gbc_btnAddLayer.gridy = 4;
		getContentPane().add(btnAddLayer, gbc_btnAddLayer);

		JLabel lblPaaso = new JLabel("Lista de Pontos :");
		GridBagConstraints gbc_lblPaaso = new GridBagConstraints();
		gbc_lblPaaso.gridwidth = 2;
		gbc_lblPaaso.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblPaaso.insets = new Insets(0, 0, 5, 5);
		gbc_lblPaaso.gridx = 1;
		gbc_lblPaaso.gridy = 6;
		getContentPane().add(lblPaaso, gbc_lblPaaso);

		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridwidth = 8;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 1;
		gbc_scrollPane.gridy = 7;
		getContentPane().add(scrollPane, gbc_scrollPane);

		table = new JTable();
		scrollPane.setViewportView(table);
		getTableModelConsulta();

		JLabel lblCalcular = new JLabel(
				"2 - Clique no m\u00E9todo de c\u00E1lculo desejado");
		GridBagConstraints gbc_lblCalcular = new GridBagConstraints();
		gbc_lblCalcular.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblCalcular.gridwidth = 5;
		gbc_lblCalcular.insets = new Insets(0, 0, 5, 5);
		gbc_lblCalcular.gridx = 1;
		gbc_lblCalcular.gridy = 8;
		getContentPane().add(lblCalcular, gbc_lblCalcular);

		JButton btnRetangularDerivada = new JButton("Derivada");
		btnRetangularDerivada.setToolTipText("Clique para calcular o ponto central pelo m\u00E9todo da Derivada.");

		btnRetangularDerivada.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				CalcPCentralRetangularDerivada novo = new CalcPCentralRetangularDerivada();
				novo.calcule(listString);
				lblNewLabel_resultEixoX.setText(novo.getResultX());
				lblNewLabel_resultEixoY.setText(novo.getResultY());
				GeoUtilidades g = new GeoUtilidades();
		
				try {
					geraCamadaRespostaPontoCentral(novo.getResultXDoub(),novo.getResultYDoub(), "Método Derivada - Retangular", listString, analiseEspacialPlugIn.getPlugInContext());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				Iterator<String> iterar = listString.iterator();
				while (iterar.hasNext()) {
					String[] linhaExplodida = ((String) iterar.next())
							.split(";");
					double cX = Double.valueOf(linhaExplodida[1]);
					double cY = Double.valueOf(linhaExplodida[2]);
					double dX = novo.getResultXDoub();
					double dY = novo.getResultYDoub();
					g.desenhaLinha(cX, cY, dX, dY,analiseEspacialPlugIn.getPlugInContext());

				}
			}
		});

		GridBagConstraints gbc_btnRetangular = new GridBagConstraints();
		gbc_btnRetangular.gridwidth = 2;
		gbc_btnRetangular.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnRetangular.insets = new Insets(0, 0, 5, 5);
		gbc_btnRetangular.gridx = 1;
		gbc_btnRetangular.gridy = 9;
		getContentPane().add(btnRetangularDerivada, gbc_btnRetangular);
		
				JButton btnCalcEuclidiano = new JButton("Euclidiano");
				btnCalcEuclidiano.setToolTipText("Clique para calcular o ponto central pelo m\u00E9todo da Euclidiano (Weisfield).");
				GridBagConstraints gbc_btnCalcular = new GridBagConstraints();
				gbc_btnCalcular.gridwidth = 2;
				gbc_btnCalcular.fill = GridBagConstraints.HORIZONTAL;
				gbc_btnCalcular.insets = new Insets(0, 0, 5, 5);
				gbc_btnCalcular.gridx = 1;
				gbc_btnCalcular.gridy = 10;
				getContentPane().add(btnCalcEuclidiano, gbc_btnCalcular);
				
						btnCalcEuclidiano.addActionListener(new ActionListener() {
				
							@Override
							public void actionPerformed(ActionEvent arg0) {
								
								
							if(txtPrecisaoEuclidiano.getText().isEmpty()==false && Double.valueOf(txtPrecisaoEuclidiano.getText())>0){
								
										CalcPCentralEuclidiano novo = new CalcPCentralEuclidiano();
										novo.calcule(listString, txtPrecisaoEuclidiano.getText());
										lblNewLabel_resultEixoX.setText(novo.getResultX());
										lblNewLabel_resultEixoY.setText(novo.getResultY());
						
										try {
											geraCamadaRespostaPontoCentral(novo.getResultXDoub(),novo.getResultYDoub(), "Método Weisfield - Euclidiano " + "/Precisão = " + txtPrecisaoEuclidiano.getText() , listString, analiseEspacialPlugIn.getPlugInContext());
										} catch (Exception e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
										GeoUtilidades g = new GeoUtilidades();
										Iterator<String> iterar = listString.iterator();
										while (iterar.hasNext()) {
											String[] linhaExplodida = ((String) iterar.next())
													.split(";");
											double cX = Double.valueOf(linhaExplodida[1]);
											double cY = Double.valueOf(linhaExplodida[2]);
											double dX = novo.getResultXDoub();
											double dY = novo.getResultYDoub();
											g.desenhaLinha(cX, cY, dX, dY,analiseEspacialPlugIn.getPlugInContext());
										}
							} else {
								JOptionPane.showMessageDialog(null,"Favor inserir um valor maior do que zero.");
							}
							}
						});
		
				JLabel lblPreciso = new JLabel("Precis\u00E3o %  :");
				GridBagConstraints gbc_lblPreciso = new GridBagConstraints();
				gbc_lblPreciso.insets = new Insets(0, 0, 5, 5);
				gbc_lblPreciso.anchor = GridBagConstraints.EAST;
				gbc_lblPreciso.gridx = 3;
				gbc_lblPreciso.gridy = 10;
				getContentPane().add(lblPreciso, gbc_lblPreciso);
		
				txtPrecisaoEuclidiano = new JNumericField();
				txtPrecisaoEuclidiano.setMaxLength(10);
				txtPrecisaoEuclidiano.setPrecision(5);
				txtPrecisaoEuclidiano.setAllowNegative(false);
				txtPrecisaoEuclidiano.setText("0.001");
				
				txtPrecisaoEuclidiano.setToolTipText("Insira a precis\u00E3o desejada. Exemplo: 0.01 = 1%.");
				GridBagConstraints gbc_txtPrecisaoEuclidiano = new GridBagConstraints();
				gbc_txtPrecisaoEuclidiano.gridwidth = 2;
				gbc_txtPrecisaoEuclidiano.insets = new Insets(0, 0, 5, 5);
				gbc_txtPrecisaoEuclidiano.fill = GridBagConstraints.BOTH;
				gbc_txtPrecisaoEuclidiano.gridx = 4;
				gbc_txtPrecisaoEuclidiano.gridy = 10;
				getContentPane().add(txtPrecisaoEuclidiano, gbc_txtPrecisaoEuclidiano);
				txtPrecisaoEuclidiano.setColumns(10);
		
				JButton btnRetangularFibona = new JButton("Fibonacci");
				btnRetangularFibona.setToolTipText("Clique para calcular o ponto central pelo m\u00E9todo de Fibonacci.");
				btnRetangularFibona.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {
						
						if (validarInfoFibonacci(comboBox_tipoFibonacci, textField_tipoFibonacci)==true) {
							
								CalcPCentralRetangularFibonacci novo = new CalcPCentralRetangularFibonacci();
								novo.calcule(listString, (String) comboBox_tipoFibonacci
										.getItemAt(comboBox_tipoFibonacci.getSelectedIndex()),
										textField_tipoFibonacci.getText());
								lblNewLabel_resultEixoX.setText(novo.getResultX());
								lblNewLabel_resultEixoY.setText(novo.getResultY());
		
								GeoUtilidades g = new GeoUtilidades();
								try {
									
									geraCamadaRespostaPontoCentral(novo.getResultXDoub(),novo.getResultYDoub(), "Método Fibonacci-Retangular " + " Tipo: " + comboBox_tipoFibonacci.getSelectedItem().toString() + " Parâmetro = " + textField_tipoFibonacci.getText(), listString, analiseEspacialPlugIn.getPlugInContext());
									
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								
								Iterator<String> iterar = listString.iterator();
								while (iterar.hasNext()) {
									String[] linhaExplodida = ((String) iterar.next())
											.split(";");
									double cX = Double.valueOf(linhaExplodida[1]);
									double cY = Double.valueOf(linhaExplodida[2]);
									double dX = novo.getResultXDoub();
									double dY = novo.getResultYDoub();
									g.desenhaLinha(cX, cY, dX, dY,analiseEspacialPlugIn.getPlugInContext());
								}
						
						} else {
							JOptionPane.showMessageDialog(null,"Favor selecionar um tipo e o valor correspondente.");
						}
					}
				});
				GridBagConstraints gbc_btnRetangularFibona = new GridBagConstraints();
				gbc_btnRetangularFibona.fill = GridBagConstraints.HORIZONTAL;
				gbc_btnRetangularFibona.gridwidth = 2;
				gbc_btnRetangularFibona.insets = new Insets(0, 0, 5, 5);
				gbc_btnRetangularFibona.gridx = 1;
				gbc_btnRetangularFibona.gridy = 11;
				getContentPane().add(btnRetangularFibona, gbc_btnRetangularFibona);

		JLabel lblTipo = new JLabel("Tipo:");
		GridBagConstraints gbc_lblTipo = new GridBagConstraints();
		gbc_lblTipo.insets = new Insets(0, 0, 5, 5);
		gbc_lblTipo.anchor = GridBagConstraints.EAST;
		gbc_lblTipo.gridx = 3;
		gbc_lblTipo.gridy = 11;
		getContentPane().add(lblTipo, gbc_lblTipo);

		comboBox_tipoFibonacci = new JComboBox<Object>();
		comboBox_tipoFibonacci.setToolTipText("Selecione o tipo a ser usado para finalizar o m\u00E9todo iterativo.");
		comboBox_tipoFibonacci.insertItemAt("POR_PRECISAO", 0);
		comboBox_tipoFibonacci.insertItemAt("QUANTIDADE_ITERACOES", 1);
		GridBagConstraints gbc_comboBox_tipoFibonacci = new GridBagConstraints();
		gbc_comboBox_tipoFibonacci.gridwidth = 2;
		gbc_comboBox_tipoFibonacci.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox_tipoFibonacci.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox_tipoFibonacci.gridx = 4;
		gbc_comboBox_tipoFibonacci.gridy = 11;
		getContentPane()
				.add(comboBox_tipoFibonacci, gbc_comboBox_tipoFibonacci);
		
		JLabel lblParmetroDeTipo = new JLabel("Par\u00E2metro de Tipo:");
		GridBagConstraints gbc_lblParmetroDeTipo = new GridBagConstraints();
		gbc_lblParmetroDeTipo.insets = new Insets(0, 0, 5, 5);
		gbc_lblParmetroDeTipo.anchor = GridBagConstraints.EAST;
		gbc_lblParmetroDeTipo.gridx = 6;
		gbc_lblParmetroDeTipo.gridy = 11;
		getContentPane().add(lblParmetroDeTipo, gbc_lblParmetroDeTipo);

		textField_tipoFibonacci = new JNumericField();
		textField_tipoFibonacci.setMaxLength(10);
		textField_tipoFibonacci.setPrecision(3);
		textField_tipoFibonacci.setAllowNegative(false);
		textField_tipoFibonacci.setText("0.000");
		
		textField_tipoFibonacci.setToolTipText("Insira o p\u00E2metro de acordo com o tipo selecionado ao lado. Exemplo: Tipo Qtd de Itera\u00E7\u00F5es Par\u00E2metro = 20. Tipo Precis\u00E3o Par\u00E2metro = 0.01");
		GridBagConstraints gbc_textField_tipoFibonacci = new GridBagConstraints();
		gbc_textField_tipoFibonacci.insets = new Insets(0, 0, 5, 5);
		gbc_textField_tipoFibonacci.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_tipoFibonacci.gridx = 8;
		gbc_textField_tipoFibonacci.gridy = 11;
		getContentPane().add(textField_tipoFibonacci,
				gbc_textField_tipoFibonacci);
		textField_tipoFibonacci.setColumns(10);

		JLabel lblResultado = new JLabel("3 - Observe os Resultados");
		GridBagConstraints gbc_lblResultado = new GridBagConstraints();
		gbc_lblResultado.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblResultado.gridwidth = 5;
		gbc_lblResultado.insets = new Insets(0, 0, 5, 5);
		gbc_lblResultado.gridx = 1;
		gbc_lblResultado.gridy = 12;
		getContentPane().add(lblResultado, gbc_lblResultado);

		JLabel lblEixoX_1 = new JLabel("Eixo X:");
		lblEixoX_1.setBackground(Color.ORANGE);
		lblEixoX_1.setForeground(Color.BLACK);
		GridBagConstraints gbc_lblEixoX_1 = new GridBagConstraints();
		gbc_lblEixoX_1.anchor = GridBagConstraints.EAST;
		gbc_lblEixoX_1.gridwidth = 2;
		gbc_lblEixoX_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblEixoX_1.gridx = 1;
		gbc_lblEixoX_1.gridy = 13;
		getContentPane().add(lblEixoX_1, gbc_lblEixoX_1);
		
		lblNewLabel_resultEixoX = new JTextField();
		lblNewLabel_resultEixoX.setEditable(false);
		lblNewLabel_resultEixoX.setToolTipText("Insira o valor da coordenada do eixo X do ponto. Exemplo: 35.83");
		lblNewLabel_resultEixoX.setColumns(10);
		lblNewLabel_resultEixoX.setBackground(Color.ORANGE);
		GridBagConstraints gbc_lblNewLabel_resultEixoX = new GridBagConstraints();
		gbc_lblNewLabel_resultEixoX.gridwidth = 2;
		gbc_lblNewLabel_resultEixoX.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_resultEixoX.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblNewLabel_resultEixoX.gridx = 3;
		gbc_lblNewLabel_resultEixoX.gridy = 13;
		getContentPane().add(lblNewLabel_resultEixoX, gbc_lblNewLabel_resultEixoX);

		JLabel lblEixoY_1 = new JLabel("Eixo Y:");
		lblEixoY_1.setBackground(Color.ORANGE);
		lblEixoY_1.setForeground(Color.BLACK);
		GridBagConstraints gbc_lblEixoY_1 = new GridBagConstraints();
		gbc_lblEixoY_1.anchor = GridBagConstraints.EAST;
		gbc_lblEixoY_1.gridwidth = 2;
		gbc_lblEixoY_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblEixoY_1.gridx = 1;
		gbc_lblEixoY_1.gridy = 14;
		getContentPane().add(lblEixoY_1, gbc_lblEixoY_1);
		
		lblNewLabel_resultEixoY = new JTextField();
		lblNewLabel_resultEixoY.setEditable(false);
		lblNewLabel_resultEixoY.setToolTipText("Insira o valor da coordenada do eixo X do ponto. Exemplo: 35.83");
		lblNewLabel_resultEixoY.setColumns(10);
		lblNewLabel_resultEixoY.setBackground(Color.ORANGE);
		GridBagConstraints gbc_lblNewLabel_resultEixoY = new GridBagConstraints();
		gbc_lblNewLabel_resultEixoY.gridwidth = 2;
		gbc_lblNewLabel_resultEixoY.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_resultEixoY.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblNewLabel_resultEixoY.gridx = 3;
		gbc_lblNewLabel_resultEixoY.gridy = 14;
		getContentPane().add(lblNewLabel_resultEixoY, gbc_lblNewLabel_resultEixoY);

	}

	public DefaultTableModel getTableModelConsulta() {
		Object[] nomeColunas = { "ID", "Eixo-X", "Eixo-Y", "Peso" };
		Object[][] linhasLidasJTable = null;
		int qtdCamposTabela = 4;
		int qtdLinhas = listString.size();
		Object[][] lista = new Object[qtdLinhas][qtdCamposTabela];
		Iterator<String> iterar = listString.iterator();
		while (iterar.hasNext()) {
			for (int i = 0; i < qtdLinhas; i++) {
				String[] linha = ((String) iterar.next()).split(";");
				for (int j = 0; j < qtdCamposTabela; j++) {
					lista[i][j] = linha[j];
				}
			}
		}
		linhasLidasJTable = lista;
		table.setModel(new DefaultTableModel(linhasLidasJTable, nomeColunas));
		DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
		return tableModel;
	}

	public void adicionarRegistrosLista(String id, String eixoX, String eixoY,
			String peso) {

		String linhaRegistro = id + ";" + eixoX + ";" + eixoY + ";" + peso;
		listString.add(linhaRegistro);
		getTableModelConsulta();
		GeoUtilidades g = new GeoUtilidades();
		g.desenhaPonto(
				(Double.parseDouble(eixoX.replaceAll(",", "."))),
				(Double.parseDouble(eixoY.replaceAll(",", "."))),
				id,
				peso,
				analiseEspacialPlugIn.getPlugInContext());
	}
	
public void geraCamadaRespostaPontoCentral (double eixoX,double eixoY, String nomeMetodo,List<String> listaPontos, PlugInContext context) throws Exception   {

		
		try{
		
		FeatureSchema fs1 = new FeatureSchema();
		// definindo os atributos para cada geometria
		fs1.addAttribute("GEOMETRY", AttributeType.GEOMETRY);
		fs1.addAttribute("coordenadaX", AttributeType.DOUBLE);
		fs1.addAttribute("coordenadaY", AttributeType.DOUBLE);
		fs1.addAttribute("nomePonto", AttributeType.STRING);
		fs1.addAttribute("infoPontos", AttributeType.STRING);
		
		FeatureCollection fc1 = new FeatureDataset(fs1);
			// LAYOUT ARQUIVO - POSIÇÕES DE CADA CAMPO
			// 0 - EIXO X
			// 1 - EIXO Y
			// 2 - NOME PONTO
			// 3 - INFO PONTO

			double cX = (eixoX);
			double cY = eixoY;
			String nomePonto = nomeMetodo;
			String infoPontos = listaPontos.toString();

			// definindo o tipo de geometria
			GeometryFactory gf = new GeometryFactory();
			Coordinate c1 = new Coordinate(cX, cY);
			com.vividsolutions.jts.geom.Point point = gf.createPoint(c1);

			Feature feature1 = new BasicFeature(fs1);
			// preenchendo os atributos do ponto
			feature1.setAttribute("coordenadaX", cX);
			feature1.setAttribute("coordenadaY", cY);
			feature1.setAttribute("nomePonto", nomePonto);
			feature1.setAttribute("infoPontos", infoPontos);
			// definindo o tipo de geometria do feature construido
			feature1.setGeometry(point);

			// inserindo o feature na coleção de features
			fc1.add(feature1);
		

		if (fc1.size() > 0) {
			context.addLayer(StandardCategoryNames.WORKING, nomeMetodo, fc1);
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

	public boolean validarInfoFibonacci(JComboBox<Object> tipo,
			JNumericField parametroTipo) {
		boolean resposta = false;
		if (tipo.getSelectedIndex() !=-1 && parametroTipo.getText().isEmpty()==false) {
			if(Double.valueOf(parametroTipo.getText())>0){
			resposta = true;
			}
		}
		return resposta;

	}
	
}
