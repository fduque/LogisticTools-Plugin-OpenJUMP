package br.puc.rio.telas;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import br.puc.rio.kruskal.ClusterPlugIn;
import br.puc.rio.utilities.GeoUtilidades;

import com.vividsolutions.jump.feature.AttributeType;
import com.vividsolutions.jump.feature.Feature;
import com.vividsolutions.jump.feature.FeatureSchema;
import com.vividsolutions.jump.workbench.model.Layer;

public class TelaCluster extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7873224945328218193L;
	ClusterPlugIn clusterPlugIn;
	JComboBox<Object> comboBox_selecioneID;
	JComboBox<Object> comboBox_selecioneCamada;
	JComboBox<Object> comboBox_NdeClusters;

	public TelaCluster(ClusterPlugIn aPlugIn) {

		super(aPlugIn.getPlugInContext().getWorkbenchFrame(),
				"Importar Arquivo", false);
		setResizable(false);
		setTitle("Procedimento para Forma\u00E7\u00E3o de Clusters");
		this.clusterPlugIn = aPlugIn;

		setBounds(100, 100, 560, 236);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 28, 52, 44, 76, 57, 0, 0,
				29, 0 };
		gridBagLayout.rowHeights = new int[] { 23, 26, 0, 0, 0, 115, 0, 0, 0, 0, 13, 0, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 0.0, 1.0, 1.0, 1.0,
				1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 1.0,
				0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		getContentPane().setLayout(gridBagLayout);
		
		JLabel lblInsiraAs = new JLabel("Insira as informa\u00E7\u00F5es abaixo para executar o procedimento:");
		GridBagConstraints gbc_lblInsiraAs = new GridBagConstraints();
		gbc_lblInsiraAs.anchor = GridBagConstraints.WEST;
		gbc_lblInsiraAs.gridwidth = 5;
		gbc_lblInsiraAs.insets = new Insets(0, 0, 5, 5);
		gbc_lblInsiraAs.gridx = 1;
		gbc_lblInsiraAs.gridy = 3;
		getContentPane().add(lblInsiraAs, gbc_lblInsiraAs);

		JLabel lblDefina = new JLabel("1 - Selecione a camada");
		lblDefina.setForeground(Color.BLACK);
		lblDefina.setBackground(Color.ORANGE);
		GridBagConstraints gbc_lblDefina = new GridBagConstraints();
		gbc_lblDefina.anchor = GridBagConstraints.WEST;
		gbc_lblDefina.gridwidth = 3;
		gbc_lblDefina.insets = new Insets(0, 0, 5, 5);
		gbc_lblDefina.gridx = 1;
		gbc_lblDefina.gridy = 4;
		getContentPane().add(lblDefina, gbc_lblDefina);

		GeoUtilidades g = new GeoUtilidades();
		final JComboBox<Object> comboBox_selecioneCamada = new JComboBox<>(
				g.getLayers(clusterPlugIn.getPlugInContext()));
		comboBox_selecioneCamada.setToolTipText("Selecione a camada (Layer) onde est\u00E3o os dados.");

		GridBagConstraints gbc_comboBox_selecioneCamada = new GridBagConstraints();
		gbc_comboBox_selecioneCamada.gridwidth = 2;
		gbc_comboBox_selecioneCamada.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox_selecioneCamada.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox_selecioneCamada.gridx = 4;
		gbc_comboBox_selecioneCamada.gridy = 4;
		getContentPane().add(comboBox_selecioneCamada,
				gbc_comboBox_selecioneCamada);

		JLabel lblSelecione = new JLabel("2 - Selecione o ID do ponto *");
		lblSelecione.setForeground(Color.BLACK);
		lblSelecione.setBackground(Color.ORANGE);
		GridBagConstraints gbc_lblSelecione = new GridBagConstraints();
		gbc_lblSelecione.anchor = GridBagConstraints.WEST;
		gbc_lblSelecione.gridwidth = 2;
		gbc_lblSelecione.insets = new Insets(0, 0, 5, 5);
		gbc_lblSelecione.gridx = 1;
		gbc_lblSelecione.gridy = 6;
		getContentPane().add(lblSelecione, gbc_lblSelecione);

		comboBox_selecioneID = new JComboBox<Object>();
		comboBox_selecioneID.setToolTipText("Selecione o nome do campo identificador (refer\u00EAncia \u00FAnica) dos pontos.");
		

		comboBox_selecioneCamada.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
			
				try {
					setComboAttributes(comboBox_selecioneID,
							comboBox_selecioneCamada.getSelectedItem());
					setComboMaxNumberCluster(comboBox_NdeClusters,
							comboBox_selecioneCamada.getSelectedItem());
				} catch (Exception e) {
					comboBox_selecioneID.setSelectedItem("");
					comboBox_NdeClusters.setSelectedItem("");
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		GridBagConstraints gbc_comboBox_selecioneID = new GridBagConstraints();
		gbc_comboBox_selecioneID.gridwidth = 2;
		gbc_comboBox_selecioneID.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox_selecioneID.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox_selecioneID.gridx = 4;
		gbc_comboBox_selecioneID.gridy = 6;
		getContentPane().add(comboBox_selecioneID, gbc_comboBox_selecioneID);

		JLabel lblN_NdeCluster = new JLabel("3 - N\u00FAmero de Clusters");
		lblN_NdeCluster.setForeground(Color.BLACK);
		lblN_NdeCluster.setBackground(Color.ORANGE);
		GridBagConstraints gbc_lblN_NdeCluster = new GridBagConstraints();
		gbc_lblN_NdeCluster.anchor = GridBagConstraints.WEST;
		gbc_lblN_NdeCluster.gridwidth = 2;
		gbc_lblN_NdeCluster.insets = new Insets(0, 0, 5, 5);
		gbc_lblN_NdeCluster.gridx = 1;
		gbc_lblN_NdeCluster.gridy = 7;
		getContentPane().add(lblN_NdeCluster, gbc_lblN_NdeCluster);

		comboBox_NdeClusters = new JComboBox<Object>();
		comboBox_NdeClusters.setToolTipText("Selecione a quantidade de clusters a serem formados. Obs: A quantidade de clusters \u00E9 pr\u00E9-definida de acordo com a camada selecionada.");

		GridBagConstraints gbc_comboBox_NdeClusters = new GridBagConstraints();
		gbc_comboBox_NdeClusters.gridwidth = 2;
		gbc_comboBox_NdeClusters.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox_NdeClusters.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox_NdeClusters.gridx = 4;
		gbc_comboBox_NdeClusters.gridy = 7;
		getContentPane().add(comboBox_NdeClusters, gbc_comboBox_NdeClusters);

		JLabel lblExecute = new JLabel("4 - Execute o procedimento");
		lblExecute.setForeground(Color.BLACK);
		lblExecute.setBackground(Color.ORANGE);
		GridBagConstraints gbc_lblExecute = new GridBagConstraints();
		gbc_lblExecute.gridwidth = 2;
		gbc_lblExecute.anchor = GridBagConstraints.WEST;
		gbc_lblExecute.insets = new Insets(0, 0, 5, 5);
		gbc_lblExecute.gridx = 1;
		gbc_lblExecute.gridy = 8;
		getContentPane().add(lblExecute, gbc_lblExecute);

		JButton btn_gravarPontos = new JButton("Gerar Clusters");
		btn_gravarPontos.setToolTipText("Clique para executar o procedimento de forma\u00E7\u00E3o de clusters.");
		GridBagConstraints gbc_btn_gravarPontos = new GridBagConstraints();
		gbc_btn_gravarPontos.fill = GridBagConstraints.HORIZONTAL;
		gbc_btn_gravarPontos.gridwidth = 2;
		gbc_btn_gravarPontos.insets = new Insets(0, 0, 5, 5);
		gbc_btn_gravarPontos.gridx = 4;
		gbc_btn_gravarPontos.gridy = 8;
		getContentPane().add(btn_gravarPontos, gbc_btn_gravarPontos);

		JLabel lblDeveseEscolher = new JLabel(
				"* Deve-se escolher um campo que possua ID exclusivo (refer\u00EAncia \u00FAnica). ");
		GridBagConstraints gbc_lblDeveseEscolher = new GridBagConstraints();
		gbc_lblDeveseEscolher.anchor = GridBagConstraints.WEST;
		gbc_lblDeveseEscolher.gridwidth = 8;
		gbc_lblDeveseEscolher.insets = new Insets(0, 0, 5, 0);
		gbc_lblDeveseEscolher.gridx = 1;
		gbc_lblDeveseEscolher.gridy = 9;
		getContentPane().add(lblDeveseEscolher, gbc_lblDeveseEscolher);

		btn_gravarPontos.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				if (validaFormulario(comboBox_selecioneCamada,
						comboBox_selecioneID, comboBox_NdeClusters) == true) {
					clusterPlugIn.createClusters(comboBox_selecioneCamada
							.getSelectedItem().toString(), comboBox_selecioneID
							.getSelectedItem().toString(), Integer
							.parseInt(comboBox_NdeClusters.getSelectedItem()
									.toString()));
					dispose();
				}
			}
		});

	}

	private void setComboAttributes(JComboBox<Object> combo, Object aSelectedLayer)throws Exception {
		
		try{
		if (aSelectedLayer != null) {
			FeatureSchema schema = ((Layer) aSelectedLayer)
					.getFeatureCollectionWrapper().getFeatureSchema();

			((DefaultComboBoxModel<Object>) combo.getModel()).removeAllElements();

			for (int i = 0; i < schema.getAttributeCount(); i++) {
				if (schema.getAttributeType(i) == AttributeType.INTEGER
						|| schema.getAttributeType(i) == AttributeType.STRING
						|| schema.getAttributeType(i) == AttributeType.DOUBLE
						|| schema.getAttributeType(i) == AttributeType.OBJECT) {
					((DefaultComboBoxModel<Object>) combo.getModel()).addElement(schema
							.getAttributeName(i));
				}
			}
		}
		}
		catch (Exception e) {
			JOptionPane.showMessageDialog(null,
					"A camada selecionada náo é válida.");
			throw new Exception();
		}
		
	}

	private void setComboMaxNumberCluster(JComboBox<Object> combo, Object aSelectedLayer)  throws Exception {
		
		try{
		int qtdPontosNaCamada = 0; // qtd max de clusters é igual a qtd total de
									// pontos na camada
		((DefaultComboBoxModel<Object>) combo.getModel()).removeAllElements();// zerando
																		// o
																		// combo
																		// box

		Layer camadaSelecionada = ((Layer) aSelectedLayer); // pegando a camada

		for (Iterator<?> iterator = camadaSelecionada
				.getFeatureCollectionWrapper().iterator(); iterator.hasNext();) {
			Feature aFeature = (Feature) iterator.next();

			if ((aFeature.getAttribute("GEOMETRY").toString()) != null) {
				qtdPontosNaCamada++;
				((DefaultComboBoxModel<Object>) combo.getModel())
						.addElement(qtdPontosNaCamada);
			}
		}
		}
		catch (Exception e) {
			
			JOptionPane.showMessageDialog(null,
					"A camada selecionada náo é válida.");
			throw new Exception();
		}
	}

	public boolean validaFormulario(JComboBox<Object> comboBox_selecioneCamada,
			JComboBox<Object> comboBox_selecioneID, JComboBox<Object> comboBox_NdeClusters) {
		boolean resposta = false;
		int qtd = 0;
		if (comboBox_NdeClusters.getSelectedItem() != null) {
			qtd++;
		}
		if (comboBox_selecioneCamada.getSelectedItem() != null) {
			qtd++;
		}
		if (comboBox_selecioneID.getSelectedItem() != null) {
			qtd++;
		}

		if (qtd < 3) {
			resposta = false;
			JOptionPane
					.showMessageDialog(
							null,
							"Não é possível aplicar o cálculo sobre a camada selecionada.\nA camada não é válida.");
		} else {
			resposta = true;
		}
		return resposta;
	}

}
