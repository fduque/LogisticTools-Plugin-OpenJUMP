package br.puc.rio.telas;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import br.puc.rio.utilities.GeoUtilidades;
import br.puc.rio.utilities.JNumericField;
import br.puc.rio.zona.Distrito;
import br.puc.rio.zona.MetodosAgrupamento;
import br.puc.rio.zona.PlugInZona;
import br.puc.rio.zona.SubZona;
import br.puc.rio.zona.Zona;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.LinearRing;
import com.vividsolutions.jts.geom.Polygon;
import com.vividsolutions.jts.geom.impl.CoordinateArraySequence;
import com.vividsolutions.jump.feature.AttributeType;
import com.vividsolutions.jump.feature.Feature;
import com.vividsolutions.jump.feature.FeatureSchema;
import com.vividsolutions.jump.workbench.model.Layer;

public class TelaZonaFormacaoDeZonas extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4999998963804170945L;
	/**
	 * 
	 */

	PlugInZona zonaPlugIn;
	JComboBox<Object> comboBox_LayerGeometria;

	private Zona zona;
	private JComboBox<Object> comboBox_listaCamposLayer;
	private JComboBox<Object> comboBox_listaDistritos;
	private JTextField txtNomeprojeto;
	private JNumericField txtAngtriangNaoSetorizado;
	private JNumericField txtAngtriangsetorizado;
	private JNumericField txtQtdsetores;
	private JNumericField txtAreaadmissivel;

	public TelaZonaFormacaoDeZonas(PlugInZona aPlugIn) {

		super(aPlugIn.getPlugInContext().getWorkbenchFrame(), "", false);
		setResizable(false);
		getContentPane().setBackground(Color.WHITE);
		setTitle("Forma\u00E7\u00E3o de Zonas");
		this.zonaPlugIn = aPlugIn;

		setBounds(100, 100, 596, 428);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 158, 49, 141, 115, 92,
				102, 61, 53, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 23, 0, 0, 0, 0, 0, 0, 0, 0, 18, 0, 0, 0,
				0, 0, 30, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 0.0, 1.0, 1.0,
				1.0, 1.0, 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				Double.MIN_VALUE };
		getContentPane().setLayout(gridBagLayout);

		comboBox_listaCamposLayer = new JComboBox<Object>();
		comboBox_listaCamposLayer
				.setToolTipText("Selecione o Campo da Layer que identifica os distritos. Exemplo: id.");

		JLabel lblNomeDaZona = new JLabel("Nome do Projeto:");
		GridBagConstraints gbc_lblNomeDaZona = new GridBagConstraints();
		gbc_lblNomeDaZona.anchor = GridBagConstraints.EAST;
		gbc_lblNomeDaZona.insets = new Insets(0, 0, 5, 5);
		gbc_lblNomeDaZona.gridx = 1;
		gbc_lblNomeDaZona.gridy = 2;
		getContentPane().add(lblNomeDaZona, gbc_lblNomeDaZona);

		txtNomeprojeto = new JTextField();
		txtNomeprojeto.setToolTipText("Insira um nome para o projeto.");
		txtNomeprojeto.setText("nomeProjeto");
		GridBagConstraints gbc_txtNomeprojeto = new GridBagConstraints();
		gbc_txtNomeprojeto.insets = new Insets(0, 0, 5, 5);
		gbc_txtNomeprojeto.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtNomeprojeto.gridx = 3;
		gbc_txtNomeprojeto.gridy = 2;
		getContentPane().add(txtNomeprojeto, gbc_txtNomeprojeto);
		txtNomeprojeto.setColumns(10);

		JLabel lblSelecioneACamadazona = new JLabel(
				"Selecione a Layer Geometry:");
		GridBagConstraints gbc_lblSelecioneACamadazona = new GridBagConstraints();
		gbc_lblSelecioneACamadazona.anchor = GridBagConstraints.EAST;
		gbc_lblSelecioneACamadazona.insets = new Insets(0, 0, 5, 5);
		gbc_lblSelecioneACamadazona.gridx = 1;
		gbc_lblSelecioneACamadazona.gridy = 3;
		getContentPane().add(lblSelecioneACamadazona,
				gbc_lblSelecioneACamadazona);
		GeoUtilidades g = new GeoUtilidades();
		comboBox_LayerGeometria = new JComboBox<Object>(g.getLayers(zonaPlugIn.getPlugInContext()));
		comboBox_LayerGeometria
				.setToolTipText("Selecione a Layer onde est\u00E3o as geometrias, ou seja, o mapa de distritos.");
		GridBagConstraints gbc_comboBox_LayerGeometria = new GridBagConstraints();
		gbc_comboBox_LayerGeometria.gridwidth = 2;
		gbc_comboBox_LayerGeometria.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox_LayerGeometria.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox_LayerGeometria.gridx = 3;
		gbc_comboBox_LayerGeometria.gridy = 3;
		getContentPane().add(comboBox_LayerGeometria,
				gbc_comboBox_LayerGeometria);

		try{
		setComboCamposLayer(comboBox_listaCamposLayer,
				comboBox_LayerGeometria.getSelectedItem());
		}
		catch (Exception e) {
			e.printStackTrace();
			
		}
		JLabel lblSelCampoDistrito = new JLabel("Sel. Campo Distrito:");
		GridBagConstraints gbc_lblSelCampoDistrito = new GridBagConstraints();
		gbc_lblSelCampoDistrito.anchor = GridBagConstraints.EAST;
		gbc_lblSelCampoDistrito.insets = new Insets(0, 0, 5, 5);
		gbc_lblSelCampoDistrito.gridx = 1;
		gbc_lblSelCampoDistrito.gridy = 4;
		getContentPane().add(lblSelCampoDistrito, gbc_lblSelCampoDistrito);

		GridBagConstraints gbc_comboBox_listaCamposLayer = new GridBagConstraints();
		gbc_comboBox_listaCamposLayer.gridwidth = 2;
		gbc_comboBox_listaCamposLayer.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox_listaCamposLayer.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox_listaCamposLayer.gridx = 3;
		gbc_comboBox_listaCamposLayer.gridy = 4;
		getContentPane().add(comboBox_listaCamposLayer,
				gbc_comboBox_listaCamposLayer);

		JLabel lblLocalDeposito = new JLabel("Deposito distrito:");
		GridBagConstraints gbc_lblLocalDeposito = new GridBagConstraints();
		gbc_lblLocalDeposito.anchor = GridBagConstraints.EAST;
		gbc_lblLocalDeposito.insets = new Insets(0, 0, 5, 5);
		gbc_lblLocalDeposito.gridx = 1;
		gbc_lblLocalDeposito.gridy = 5;
		getContentPane().add(lblLocalDeposito, gbc_lblLocalDeposito);

		comboBox_listaDistritos = new JComboBox<Object>();
		comboBox_listaDistritos
				.setToolTipText("Selecione o distrito onde est\u00E1 localizado o dep\u00F3sito.");

		comboBox_listaCamposLayer.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				try{
				// TODO Auto-generated method stub
				setComboNomeDistritos(comboBox_listaDistritos,
						comboBox_LayerGeometria.getSelectedItem(),
						comboBox_listaCamposLayer.getSelectedItem());
				}
			
			catch(Exception e1) {
				e1.printStackTrace();
				JOptionPane
				.showMessageDialog(
						null,
						"A camada selecionada n�o � v�lida.");
			}
			}
		});

		GridBagConstraints gbc_comboBox_listaDistritos = new GridBagConstraints();
		gbc_comboBox_listaDistritos.gridwidth = 2;
		gbc_comboBox_listaDistritos.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox_listaDistritos.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox_listaDistritos.gridx = 3;
		gbc_comboBox_listaDistritos.gridy = 5;
		getContentPane().add(comboBox_listaDistritos,
				gbc_comboBox_listaDistritos);

		JLabel lblreaAdmissvel = new JLabel("\u00C1rea Admiss\u00EDvel:");
		GridBagConstraints gbc_lblreaAdmissvel = new GridBagConstraints();
		gbc_lblreaAdmissvel.anchor = GridBagConstraints.EAST;
		gbc_lblreaAdmissvel.insets = new Insets(0, 0, 5, 5);
		gbc_lblreaAdmissvel.gridx = 1;
		gbc_lblreaAdmissvel.gridy = 6;
		getContentPane().add(lblreaAdmissvel, gbc_lblreaAdmissvel);

		txtAreaadmissivel = new JNumericField(10, 3);
		txtAreaadmissivel.setPrecision(5);
		txtAreaadmissivel.setAllowNegative(false);
		txtAreaadmissivel.setToolTipText("Insira o tamanho da \u00E1rea admiss\u00EDvel.");
		txtAreaadmissivel.setText("areaAdmissivel");
		GridBagConstraints gbc_txtAreaadmissivel = new GridBagConstraints();
		gbc_txtAreaadmissivel.insets = new Insets(0, 0, 5, 5);
		gbc_txtAreaadmissivel.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtAreaadmissivel.gridx = 3;
		gbc_txtAreaadmissivel.gridy = 6;
		getContentPane().add(txtAreaadmissivel, gbc_txtAreaadmissivel);
		txtAreaadmissivel.setColumns(10);

		JButton btnAgruparPorTriangulo = new JButton(
				"Op\u00E7\u00E3o 1");

		btnAgruparPorTriangulo.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {


				try{
					if(validaInfoFormularioNaoSetorizado(txtNomeprojeto, txtAngtriangNaoSetorizado, txtAreaadmissivel)==true){
				// criando a zona
				zona = new Zona();
				zona.setIdZona(txtNomeprojeto.getText());

				List<Distrito> listDistritoSelecionada = zonaPlugIn
						.createListDistrito(comboBox_LayerGeometria
								.getSelectedItem().toString());
				Distrito distritoDepositoSelecionado = zonaPlugIn
						.getDistritoPorId(comboBox_listaDistritos
								.getSelectedItem().toString(),
								listDistritoSelecionada);
				System.out.println(" Distrito Deposito Selecionado: "
						+ distritoDepositoSelecionado.getIdDistrito());

				MetodosAgrupamento ag = new MetodosAgrupamento(
						listDistritoSelecionada, distritoDepositoSelecionado,
						Double.valueOf(txtAreaadmissivel.getText()), zona);
				List<SubZona> listasz = ag.agruparPorVarreduraSobreEixo(Double
						.valueOf(txtAngtriangNaoSetorizado.getText()));
				// plotando subzonas
				for (SubZona s : listasz) {
					zonaPlugIn.criarLayerSubZona(s, comboBox_LayerGeometria
							.getSelectedItem().toString(),
							"SubZona " + String.valueOf(s.getIdSubZona()));
				}

				List<Polygon> listpol = ag.getListaTrianguloIsosceles();
				GeoUtilidades g = new GeoUtilidades();
				for (Polygon p : listpol) {
					Coordinate[] cord = p.getCoordinates();
					g.desenhaTrianguloNumaLayer(cord[0].x, cord[0].y, cord[1].x,
							cord[1].y, cord[2].x, cord[2].y,zonaPlugIn.getPlugInContext());
				}
				dispose();
					}
			}
			catch (Exception e){
				e.printStackTrace();
				
			}
			}
			});

		JLabel lblTiposDeAgrupamento = new JLabel(
				"Op\u00E7\u00F5es de Agrupamento:");
		GridBagConstraints gbc_lblTiposDeAgrupamento = new GridBagConstraints();
		gbc_lblTiposDeAgrupamento.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblTiposDeAgrupamento.gridwidth = 4;
		gbc_lblTiposDeAgrupamento.insets = new Insets(0, 0, 5, 5);
		gbc_lblTiposDeAgrupamento.gridx = 1;
		gbc_lblTiposDeAgrupamento.gridy = 8;
		getContentPane().add(lblTiposDeAgrupamento, gbc_lblTiposDeAgrupamento);

		JLabel lblOpo = new JLabel(
				"Op\u00E7\u00E3o 1 : Varredura sobre eixo.");
		GridBagConstraints gbc_lblOpo = new GridBagConstraints();
		gbc_lblOpo.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblOpo.gridwidth = 5;
		gbc_lblOpo.insets = new Insets(0, 0, 5, 5);
		gbc_lblOpo.gridx = 1;
		gbc_lblOpo.gridy = 10;
		getContentPane().add(lblOpo, gbc_lblOpo);

		JLabel lblnguloDeAbertura = new JLabel("\u00C2ngulo de Abertura:");
		GridBagConstraints gbc_lblnguloDeAbertura = new GridBagConstraints();
		gbc_lblnguloDeAbertura.insets = new Insets(0, 0, 5, 5);
		gbc_lblnguloDeAbertura.gridx = 3;
		gbc_lblnguloDeAbertura.gridy = 11;
		getContentPane().add(lblnguloDeAbertura, gbc_lblnguloDeAbertura);

		txtAngtriangNaoSetorizado = new JNumericField(10, 3);
		txtAngtriangNaoSetorizado.setPrecision(5);
		txtAngtriangNaoSetorizado.setAllowNegative(false);
		txtAngtriangNaoSetorizado
				.setToolTipText("Insira o \u00E2ngulo de abertura do tri\u00E2ngulo.");
		txtAngtriangNaoSetorizado.setText("angTriang");
		GridBagConstraints gbc_txtAngtriangNaoSetorizado = new GridBagConstraints();
		gbc_txtAngtriangNaoSetorizado.insets = new Insets(0, 0, 5, 5);
		gbc_txtAngtriangNaoSetorizado.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtAngtriangNaoSetorizado.gridx = 4;
		gbc_txtAngtriangNaoSetorizado.gridy = 11;
		getContentPane().add(txtAngtriangNaoSetorizado,
				gbc_txtAngtriangNaoSetorizado);
		txtAngtriangNaoSetorizado.setColumns(10);

		GridBagConstraints gbc_btnAgruparPorLinha = new GridBagConstraints();
		gbc_btnAgruparPorLinha.gridwidth = 2;
		gbc_btnAgruparPorLinha.fill = GridBagConstraints.BOTH;
		gbc_btnAgruparPorLinha.insets = new Insets(0, 0, 5, 5);
		gbc_btnAgruparPorLinha.gridx = 5;
		gbc_btnAgruparPorLinha.gridy = 11;
		getContentPane().add(btnAgruparPorTriangulo, gbc_btnAgruparPorLinha);

		JLabel lblOpo_1 = new JLabel("Op\u00E7\u00E3o 2 : Varredura sobre eixo setorizado.");
		GridBagConstraints gbc_lblOpo_1 = new GridBagConstraints();
		gbc_lblOpo_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblOpo_1.gridwidth = 4;
		gbc_lblOpo_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblOpo_1.gridx = 1;
		gbc_lblOpo_1.gridy = 13;
		getContentPane().add(lblOpo_1, gbc_lblOpo_1);

		JLabel label_2 = new JLabel("\u00C2ngulo de Abertura:");
		GridBagConstraints gbc_label_2 = new GridBagConstraints();
		gbc_label_2.insets = new Insets(0, 0, 5, 5);
		gbc_label_2.gridx = 3;
		gbc_label_2.gridy = 14;
		getContentPane().add(label_2, gbc_label_2);

		txtAngtriangsetorizado = new JNumericField(10, 3);
		txtAngtriangsetorizado.setPrecision(5);
		txtAngtriangsetorizado.setAllowNegative(false);
		txtAngtriangsetorizado
				.setToolTipText("Insira o \u00E2ngulo de abertura do tri\u00E2ngulo.");
		txtAngtriangsetorizado.setText("angTriangSetorizado");
		GridBagConstraints gbc_txtAngtriangsetorizado = new GridBagConstraints();
		gbc_txtAngtriangsetorizado.insets = new Insets(0, 0, 5, 5);
		gbc_txtAngtriangsetorizado.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtAngtriangsetorizado.gridx = 4;
		gbc_txtAngtriangsetorizado.gridy = 14;
		getContentPane()
				.add(txtAngtriangsetorizado, gbc_txtAngtriangsetorizado);
		txtAngtriangsetorizado.setColumns(10);

		JButton btnTringuloGiratrioSetorizado = new JButton("Op\u00E7\u00E3o 2");

		btnTringuloGiratrioSetorizado.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent arg0) {
				try{
					if (validaInfoFormularioSetorizado(txtNomeprojeto, txtAngtriangsetorizado, txtQtdsetores, txtAreaadmissivel)==true){
								// criando a zona
								zona = new Zona();
								zona.setIdZona(txtNomeprojeto.getText());
				
								List<Distrito> listDistritoSelecionada = zonaPlugIn
										.createListDistrito(comboBox_LayerGeometria
												.getSelectedItem().toString());
								Distrito distritoDepositoSelecionado = zonaPlugIn
										.getDistritoPorId(comboBox_listaDistritos
												.getSelectedItem().toString(),
												listDistritoSelecionada);
								System.out.println(" Distrito Deposito Selecionado: "
										+ distritoDepositoSelecionado.getIdDistrito());
								MetodosAgrupamento ag = new MetodosAgrupamento(
										listDistritoSelecionada, distritoDepositoSelecionado,
										Double.valueOf(txtAreaadmissivel.getText()), zona);
								List<SubZona> listasz = ag
										.agruparPorVarreduraSobreEixoSetorizado(Double
												.valueOf(txtQtdsetores.getText()), Double
												.valueOf(txtAngtriangsetorizado.getText()));
								// plotando subzonas
								for (SubZona s : listasz) {
									zonaPlugIn.criarLayerSubZona(s, comboBox_LayerGeometria
											.getSelectedItem().toString(),
											"SubZona " + String.valueOf(s.getIdSubZona()));
								}
								List<Polygon> listpol = ag
										.getListaTrianguloIsoscelesSetorizado();
								GeoUtilidades g = new GeoUtilidades();
								for (Polygon p : listpol) {
									Coordinate[] cord = p.getCoordinates();
									g.desenhaTrianguloNumaLayer(cord[0].x, cord[0].y, cord[1].x,
											cord[1].y, cord[2].x, cord[2].y,zonaPlugIn.getPlugInContext());
								}
								dispose();
					}
				}
				catch (Exception e){
					e.printStackTrace();
					JOptionPane
					.showMessageDialog(
							null,
							"A camada selecionada n�o � v�lida.");
				}
							}
						});
		GridBagConstraints gbc_btnTringuloGiratrioSetorizado = new GridBagConstraints();
		gbc_btnTringuloGiratrioSetorizado.gridwidth = 2;
		gbc_btnTringuloGiratrioSetorizado.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnTringuloGiratrioSetorizado.insets = new Insets(0, 0, 5, 5);
		gbc_btnTringuloGiratrioSetorizado.gridx = 5;
		gbc_btnTringuloGiratrioSetorizado.gridy = 14;
		getContentPane().add(btnTringuloGiratrioSetorizado,
				gbc_btnTringuloGiratrioSetorizado);

		JLabel lblComprimentoSetor = new JLabel("Qtd de Setores:");
		GridBagConstraints gbc_lblComprimentoSetor = new GridBagConstraints();
		gbc_lblComprimentoSetor.insets = new Insets(0, 0, 5, 5);
		gbc_lblComprimentoSetor.gridx = 3;
		gbc_lblComprimentoSetor.gridy = 15;
		getContentPane().add(lblComprimentoSetor, gbc_lblComprimentoSetor);

		txtQtdsetores = new JNumericField(10, 2);
		txtQtdsetores.setAllowNegative(false);
		txtQtdsetores
				.setToolTipText("Insira a quantidade de setores a serem gerados. Obs: Somente numeros inteiros.");
		txtQtdsetores.setText("qtdSetores");
		GridBagConstraints gbc_txtQtdsetores = new GridBagConstraints();
		gbc_txtQtdsetores.insets = new Insets(0, 0, 5, 5);
		gbc_txtQtdsetores.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtQtdsetores.gridx = 4;
		gbc_txtQtdsetores.gridy = 15;
		getContentPane().add(txtQtdsetores, gbc_txtQtdsetores);
		txtQtdsetores.setColumns(10);

		JLabel lblNewLabel_3 = new JLabel("");
		GridBagConstraints gbc_lblNewLabel_3 = new GridBagConstraints();
		gbc_lblNewLabel_3.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_3.gridx = 1;
		gbc_lblNewLabel_3.gridy = 16;
		getContentPane().add(lblNewLabel_3, gbc_lblNewLabel_3);

	}

	private void setComboCamposLayer(JComboBox<Object> combo,
			Object aSelectedLayer) throws Exception{
		
		try{
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
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane
			.showMessageDialog(
					null,
					"A camada selecionada n�o � v�lida.");
			throw new Exception();
		}
	}

	private void setComboNomeDistritos(JComboBox<Object> combo,
			Object aSelectedLayer, Object campoNomeDistrito) throws Exception {
try{
		((DefaultComboBoxModel<Object>) combo.getModel()).removeAllElements();// zerando
																				// o
																				// combo
																				// box

		Layer camadaSelecionada = ((Layer) aSelectedLayer); // pegando a camada

		for (java.util.Iterator<?> iterator = camadaSelecionada
				.getFeatureCollectionWrapper().iterator(); iterator.hasNext();) {
			Feature aFeature = (Feature) iterator.next();
			if ((aFeature.getAttribute(campoNomeDistrito.toString()).toString()) != null) {
				((DefaultComboBoxModel<Object>) combo.getModel())
						.addElement(aFeature.getAttribute(campoNomeDistrito
								.toString()));
			}
		}
}
catch(Exception e) {
	e.printStackTrace();
	JOptionPane
	.showMessageDialog(
			null,
			"A camada selecionada n�o � v�lida.");
	throw new Exception();
}
	}

	public LineString criarUmaLinha(double cX, double cY, double dX, double dY) {

		GeometryFactory gf = new GeometryFactory(); // this is a new line
		Coordinate c1 = new Coordinate(cX, cY);
		Coordinate c2 = new Coordinate(dX, dY);
		Collection<Coordinate> listCord = new ArrayList<Coordinate>();
		listCord.add(c1);
		listCord.add(c2);
		Coordinate[] coords = listCord.toArray(new Coordinate[listCord.size()]); //
		LineString line = gf.createLineString(coords);

		return line;
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

	public double getAreaTotalListaDistritos(List<Distrito> listaDistritos) {

		double areaTodosDistritos = 0.0;
		java.util.Iterator<Distrito> iterar = listaDistritos.iterator();
		while (iterar.hasNext()) {
			Distrito novo = iterar.next();
			areaTodosDistritos = areaTodosDistritos + novo.getAreaDistrito();
		}

		return areaTodosDistritos;
	}

	public List<Polygon> criarListDeTriangulosIsosceles(double extremX,
			double extremY, double baseX, double baseY, double anguloEmGraus) {
		List<Polygon> listaTringulos = new ArrayList<>();
		double qtdTriangulos = 360 / anguloEmGraus;
		int contador = 1;
		double anguloMenor = 0;
		double anguloMaior = anguloEmGraus;
		// gerando linhas do triangulo inicial
		LineString novaLinha = criarUmaLinha(baseX, baseY, extremX, extremY);
		LineString linha1 = rotacionarLinhaPorGraus(novaLinha, anguloMenor);
		LineString linha2 = rotacionarLinhaPorGraus(novaLinha, anguloMaior);
		// pegando as coordenadas do triangulo inicial
		Coordinate cPontoFixo = new Coordinate(baseX, baseY);
		Coordinate cPontoVar1 = new Coordinate(linha1.getEndPoint().getX(),
				linha1.getEndPoint().getY());
		Coordinate cPontoVar2 = new Coordinate(linha2.getEndPoint().getX(),
				linha2.getEndPoint().getY());
		Coordinate cEmenda = cPontoFixo; // essa redundancia e necessaria para
											// criar o poligono triangulo

		while (contador <= qtdTriangulos) {

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
			LineString linhaBase = criarUmaLinha(cPontoVar2.x, cPontoVar2.y,
					cPontoVar1.x, cPontoVar1.y);
			double comprimentoLadoMaior = linha1.getLength();
			double altura = Math.sqrt(Math.pow(comprimentoLadoMaior, 2)
					- (Math.pow(linhaBase.getLength(), 2) / 4));
			double areaTriangulo = linhaBase.getLength() * altura / 2;
			System.out.println("Area triangulo = " + areaTriangulo);

			// gerando linhas do novo triangulo
			anguloMenor = anguloMenor + anguloEmGraus;
			linha1 = rotacionarLinhaPorGraus(novaLinha, anguloMenor);
			anguloMaior = anguloMaior + anguloEmGraus;
			linha2 = rotacionarLinhaPorGraus(novaLinha, anguloMaior);
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

		return listaTringulos;
	}

	public boolean validaInfoFormularioNaoSetorizado(
			 JTextField txtNomeprojeto,
			 JNumericField txtAngtriangNaoSetorizado,
			 JNumericField txtAreaadmissivel) {
		int qtd = 0;
		boolean resposta = false;
		if (txtNomeprojeto.getText().isEmpty() == false) {
			qtd++;
		}
		if (txtAngtriangNaoSetorizado.getText().isEmpty() == false) {
			qtd++;
		}
		if (txtAreaadmissivel.getText().isEmpty() == false) {
			qtd++;
		}

		if (qtd < 3) {
			JOptionPane
					.showMessageDialog(
							null,
							"Par�metros pendentes de preenchimento.");
			resposta = false;
		} else {
			resposta = true;
		}

		return resposta;
	}
	public boolean validaInfoFormularioSetorizado(
			 JTextField txtNomeprojeto,
			 JNumericField txtAngtriangsetorizado,
			 JNumericField txtQtdsetores,
			 JNumericField txtAreaadmissivel) {
		int qtd = 0;
		boolean resposta = false;
		if (txtNomeprojeto.getText().isEmpty() == false) {
			qtd++;
		}
		if (txtAngtriangsetorizado.getText().isEmpty() == false) {
			qtd++;
		}
		if (txtAreaadmissivel.getText().isEmpty() == false) {
			qtd++;
		}
		if (txtQtdsetores.getText().isEmpty() == false) {
			qtd++;
		}

		if (qtd < 4) {
			JOptionPane
					.showMessageDialog(
							null,
							"Par�metros pendentes de preenchimento.");
			resposta = false;
		} else {
			resposta = true;
		}

		return resposta;
	}
}
