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

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;

import br.puc.rio.pontoCentral.PontoCentralPlugIn;
import br.puc.rio.utilities.GeoUtilidades;

import com.vividsolutions.jump.feature.AttributeType;
import com.vividsolutions.jump.feature.Feature;
import com.vividsolutions.jump.feature.FeatureSchema;
import com.vividsolutions.jump.workbench.model.Layer;

public class TelaPontoCentralAddLayer extends JDialog {

	private static final long serialVersionUID = 7873224945328218193L;
	PontoCentralPlugIn analiseEspacialPlugIn;
	TelaPontoCentral telaPontoCentral;
	br.puc.rio.openJumpExt.CarregamentoRedeExtension classeExtensao;
	private List<String> listString = new ArrayList<>();

	public List<String> getListString() {
		return listString;
	}

	public void setListString(List<String> listString) {
		this.listString = listString;
	}

	JComboBox<Object> comboBox_selecioneID;
	JComboBox<Object> comboBox_selecioneEixoX;
	JComboBox<Object> comboBox_selecionePeso;
	JComboBox<Object> comboBox_selecioneEixoY;

	public TelaPontoCentralAddLayer(PontoCentralPlugIn aPlugIn, TelaPontoCentral tela) {

		super(aPlugIn.getPlugInContext().getWorkbenchFrame(),
				"Adicionar Layer", false);
		setResizable(false);
		this.analiseEspacialPlugIn = aPlugIn;
		this.telaPontoCentral = tela;

		setBounds(100, 100, 548, 297);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 93, 61, 0, 39, 29, 0 };
		gridBagLayout.rowHeights = new int[] { 23, 26, 0, 0, 0, 0, 26, 0, 30,
				0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 1.0, 1.0, 1.0, 0.0,
				0.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		getContentPane().setLayout(gridBagLayout);

		JLabel lblDefina = new JLabel("Selecione a Layer:");
		lblDefina.setForeground(Color.BLACK);
		lblDefina.setBackground(Color.ORANGE);
		GridBagConstraints gbc_lblDefina = new GridBagConstraints();
		gbc_lblDefina.anchor = GridBagConstraints.EAST;
		gbc_lblDefina.insets = new Insets(0, 0, 5, 5);
		gbc_lblDefina.gridx = 1;
		gbc_lblDefina.gridy = 1;
		getContentPane().add(lblDefina, gbc_lblDefina);

		GeoUtilidades g = new GeoUtilidades();
		final JComboBox<Object> comboBox_SelecioneLayer = new JComboBox<>(
				g.getLayers(analiseEspacialPlugIn.getPlugInContext()));

		comboBox_SelecioneLayer.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				setComboAttributes(comboBox_selecioneID,
						comboBox_SelecioneLayer.getSelectedItem());
				setComboAttributes(comboBox_selecioneEixoX,
						comboBox_SelecioneLayer.getSelectedItem());
				setComboAttributes(comboBox_selecioneEixoY,
						comboBox_SelecioneLayer.getSelectedItem());
				setComboAttributes(comboBox_selecionePeso,
						comboBox_SelecioneLayer.getSelectedItem());

			}
		});

		GridBagConstraints gbc_comboBox_SelecioneLayer = new GridBagConstraints();

		gbc_comboBox_SelecioneLayer.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox_SelecioneLayer.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox_SelecioneLayer.gridx = 2;
		gbc_comboBox_SelecioneLayer.gridy = 1;
		getContentPane().add(comboBox_SelecioneLayer,
				gbc_comboBox_SelecioneLayer);

		JLabel lblSelecioneOCampo = new JLabel("Selecione o campo ID:");
		lblSelecioneOCampo.setForeground(Color.BLACK);
		lblSelecioneOCampo.setBackground(Color.ORANGE);
		GridBagConstraints gbc_lblSelecioneOCampo = new GridBagConstraints();
		gbc_lblSelecioneOCampo.anchor = GridBagConstraints.EAST;
		gbc_lblSelecioneOCampo.insets = new Insets(0, 0, 5, 5);
		gbc_lblSelecioneOCampo.gridx = 1;
		gbc_lblSelecioneOCampo.gridy = 2;
		getContentPane().add(lblSelecioneOCampo, gbc_lblSelecioneOCampo);

		comboBox_selecioneID = new JComboBox<Object>();
		GridBagConstraints gbc_comboBox_selecioneID = new GridBagConstraints();
		gbc_comboBox_selecioneID.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox_selecioneID.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox_selecioneID.gridx = 2;
		gbc_comboBox_selecioneID.gridy = 2;
		getContentPane().add(comboBox_selecioneID, gbc_comboBox_selecioneID);

		JLabel lblSelecioneOCampo_1 = new JLabel("Selecione o campo Eixo-X:");
		lblSelecioneOCampo_1.setForeground(Color.BLACK);
		lblSelecioneOCampo_1.setBackground(Color.ORANGE);
		GridBagConstraints gbc_lblSelecioneOCampo_1 = new GridBagConstraints();
		gbc_lblSelecioneOCampo_1.anchor = GridBagConstraints.EAST;
		gbc_lblSelecioneOCampo_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblSelecioneOCampo_1.gridx = 1;
		gbc_lblSelecioneOCampo_1.gridy = 3;
		getContentPane().add(lblSelecioneOCampo_1, gbc_lblSelecioneOCampo_1);

		comboBox_selecioneEixoX = new JComboBox<Object>();
		GridBagConstraints gbc_comboBox_selecioneEixoX = new GridBagConstraints();
		gbc_comboBox_selecioneEixoX.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox_selecioneEixoX.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox_selecioneEixoX.gridx = 2;
		gbc_comboBox_selecioneEixoX.gridy = 3;
		getContentPane().add(comboBox_selecioneEixoX,
				gbc_comboBox_selecioneEixoX);

		JLabel lblSelecioneOCampo_2 = new JLabel("Selecione o campo Eixo-Y:");
		lblSelecioneOCampo_2.setForeground(Color.BLACK);
		lblSelecioneOCampo_2.setBackground(Color.ORANGE);
		GridBagConstraints gbc_lblSelecioneOCampo_2 = new GridBagConstraints();
		gbc_lblSelecioneOCampo_2.anchor = GridBagConstraints.EAST;
		gbc_lblSelecioneOCampo_2.insets = new Insets(0, 0, 5, 5);
		gbc_lblSelecioneOCampo_2.gridx = 1;
		gbc_lblSelecioneOCampo_2.gridy = 4;
		getContentPane().add(lblSelecioneOCampo_2, gbc_lblSelecioneOCampo_2);

		comboBox_selecioneEixoY = new JComboBox<Object>();
		GridBagConstraints gbc_comboBox_selecioneEixoY = new GridBagConstraints();
		gbc_comboBox_selecioneEixoY.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox_selecioneEixoY.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox_selecioneEixoY.gridx = 2;
		gbc_comboBox_selecioneEixoY.gridy = 4;
		getContentPane().add(comboBox_selecioneEixoY,
				gbc_comboBox_selecioneEixoY);

		JLabel lblPaaso = new JLabel("Selecione o campo Peso:");
		GridBagConstraints gbc_lblPaaso = new GridBagConstraints();
		gbc_lblPaaso.anchor = GridBagConstraints.EAST;
		gbc_lblPaaso.insets = new Insets(0, 0, 5, 5);
		gbc_lblPaaso.gridx = 1;
		gbc_lblPaaso.gridy = 5;
		getContentPane().add(lblPaaso, gbc_lblPaaso);

		comboBox_selecionePeso = new JComboBox<Object>();
		GridBagConstraints gbc_comboBox_selecionePeso = new GridBagConstraints();
		gbc_comboBox_selecionePeso.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox_selecionePeso.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox_selecionePeso.gridx = 2;
		gbc_comboBox_selecionePeso.gridy = 5;
		getContentPane()
				.add(comboBox_selecionePeso, gbc_comboBox_selecionePeso);

		JButton btnOKAddLayer = new JButton("OK");

		btnOKAddLayer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					try{
				listString = createListPontos(comboBox_SelecioneLayer
						.getSelectedItem().toString(), comboBox_selecioneID
						.getSelectedItem().toString(), comboBox_selecioneEixoX
						.getSelectedItem().toString(), comboBox_selecioneEixoY
						.getSelectedItem().toString(), comboBox_selecionePeso
						.getSelectedItem().toString());

				Iterator<String> iterar = listString.iterator();
				while (iterar.hasNext()) {

					String[] linhaExplodida = ((String) iterar.next())
							.split(";");
					String id = (linhaExplodida[0]);
					String eixoX = (linhaExplodida[1]);
					String eixoY = (linhaExplodida[2]);
					String peso = (linhaExplodida[3]);
					// adicionando os pontos da camada na lista
					telaPontoCentral.adicionarRegistrosLista(id, eixoX, eixoY,
							peso);

					dispose();
				}
			}
			catch (Exception e1) {
				e1.printStackTrace();
			}
			}
		});
		GridBagConstraints gbc_btnOKAddLayer = new GridBagConstraints();
		gbc_btnOKAddLayer.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnOKAddLayer.insets = new Insets(0, 0, 5, 5);
		gbc_btnOKAddLayer.gridx = 3;
		gbc_btnOKAddLayer.gridy = 7;
		getContentPane().add(btnOKAddLayer, gbc_btnOKAddLayer);

		JSeparator separator = new JSeparator();
		GridBagConstraints gbc_separator = new GridBagConstraints();
		gbc_separator.insets = new Insets(0, 0, 5, 5);
		gbc_separator.gridx = 1;
		gbc_separator.gridy = 8;
		getContentPane().add(separator, gbc_separator);

	}

	private void setComboAttributes(JComboBox<Object> combo,
			Object aSelectedLayer) {
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

	public List<String> createListPontos(String nomeCamada, String atributoID,
			String atributoEixoX, String atributoEixoY, String atributoPeso)  throws Exception{
		try{
		ArrayList<String> listaPontos = new ArrayList<>();

		System.out.println("Criando lista de pontos...");

		if (analiseEspacialPlugIn.getPlugInContext().getLayerManager()
				.getLayers() != null) {

			Layer camadaSelecionada = analiseEspacialPlugIn.getPlugInContext()
					.getLayerManager().getLayer(nomeCamada);

			for (Iterator<?> iterator = camadaSelecionada
					.getFeatureCollectionWrapper().iterator(); iterator
					.hasNext();) {
				Feature aFeature = (Feature) iterator.next();

				// ordem de leitura da lista de ponto
				// 1 - ID
				// 2 - Eixo X
				// 3 - Eixo Y
				// 4 - Peso
				// FORMATO DE GRAVACAO CONCATENADO = ID ; EIXOX; EIXOY; PESO

				// 1-ID
				// capturando o ID de cada ponto
				String codPonto = (aFeature.getAttribute(atributoID).toString());

				// 2 E 3 - EIXOS X E Y
				// capturando as coord x e y de cada ponto
				String[] coordPonto = separaCoordenadaString(aFeature
						.getAttribute("GEOMETRY").toString());
				String coordPontoX = coordPonto[0];
				String coordPontoY = coordPonto[1];

				// 4 - PESO

				// capturando o peso do ponto
				String cargaPonto = (aFeature.getAttribute(atributoPeso)
						.toString());

				// Criando a lista de pontos
				String linha = codPonto + ";" + coordPontoX + ";" + coordPontoY
						+ ";" + cargaPonto;
				listaPontos.add(linha);

			}// end for
		}
		return listaPontos;
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

}
