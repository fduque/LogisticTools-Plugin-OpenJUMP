package br.puc.rio.telas;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import br.puc.rio.utilities.GeoUtilidades;
import br.puc.rio.utilities.PlugInUploadFile;

public class TelaCriarLayerDeArquivo extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7272148584960162088L;
	/**
	 * 
	 */

	private JTextField textField_diretorioArquivo;
	PlugInUploadFile ioPlugIn;
	private File fileUploaded;
	private JComboBox <Object> comboBox_coordenadaX;
	private JComboBox <Object> comboBox_nomePonto;
	private JComboBox <Object> comboBox_coordenadaY;
	private JComboBox <Object> comboBox_cargaPonto;
	private JComboBox <Object> comboBox_Tempo;
	private JComboBox <Object> comboBox_valorCarga;
	private JComboBox <Object> comboBox_Observacao;
	
	public TelaCriarLayerDeArquivo(PlugInUploadFile aPlugIn) {

		super(aPlugIn.getPlugInContext().getWorkbenchFrame(), "", false);
		setResizable(false);
		setTitle("Criar Layer de Pontos");
		this.ioPlugIn = aPlugIn;

		setBounds(100, 100, 533, 394);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 73, 28, 85, 115, 80, 102,
				61, 0, 0, 29, 0 };
		gridBagLayout.rowHeights = new int[] { 23, 26, 0, 115, 0, 0, 30, 0, 0, 0, 0, 0, 0, 0, 0, 27, 0,
				0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 0.0, 0.0, 1.0, 1.0,
				1.0, 1.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 1.0, 0.0, 0.0,
				0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		getContentPane().setLayout(gridBagLayout);
		
		JLabel lblSelecioneUmArquivo = new JLabel("Selecione um arquivo com extens\u00E3o .txt ou .csv para criar uma Layer:");
		lblSelecioneUmArquivo.setForeground(Color.BLACK);
		lblSelecioneUmArquivo.setBackground(Color.ORANGE);
		GridBagConstraints gbc_lblSelecioneUmArquivo = new GridBagConstraints();
		gbc_lblSelecioneUmArquivo.anchor = GridBagConstraints.WEST;
		gbc_lblSelecioneUmArquivo.gridwidth = 8;
		gbc_lblSelecioneUmArquivo.insets = new Insets(0, 0, 5, 5);
		gbc_lblSelecioneUmArquivo.gridx = 1;
		gbc_lblSelecioneUmArquivo.gridy = 4;
		getContentPane().add(lblSelecioneUmArquivo, gbc_lblSelecioneUmArquivo);

		JLabel lblDefina = new JLabel("1 - Selecione o Arquivo");
		lblDefina.setForeground(Color.BLACK);
		lblDefina.setBackground(Color.ORANGE);
		GridBagConstraints gbc_lblDefina = new GridBagConstraints();
		gbc_lblDefina.anchor = GridBagConstraints.WEST;
		gbc_lblDefina.gridwidth = 2;
		gbc_lblDefina.insets = new Insets(0, 0, 5, 5);
		gbc_lblDefina.gridx = 1;
		gbc_lblDefina.gridy = 5;
		getContentPane().add(lblDefina, gbc_lblDefina);

		JButton btnSelecione = new JButton("Selecione");
		btnSelecione.setToolTipText("Clique para selecionar o arquivo deve ter extens\u00E3o .txt ou .csv.");

		btnSelecione.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				JFileChooser chooser = new JFileChooser();
				int returnVal = chooser.showOpenDialog(null);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					textField_diretorioArquivo.setText(chooser
							.getSelectedFile().getName());
					fileUploaded = chooser.getSelectedFile();

					try {
						setComboCamposArquivo(comboBox_nomePonto, fileUploaded);
						setComboCamposArquivo(comboBox_coordenadaX, fileUploaded);
						setComboCamposArquivo(comboBox_coordenadaY, fileUploaded);
						setComboCamposArquivo(comboBox_cargaPonto, fileUploaded);
						setComboCamposArquivo(comboBox_Tempo, fileUploaded);
						setComboCamposArquivo(comboBox_valorCarga, fileUploaded);
						setComboCamposArquivo(comboBox_Observacao, fileUploaded);
						
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}

			}
		});

		GridBagConstraints gbc_btnSelecione = new GridBagConstraints();
		gbc_btnSelecione.gridwidth = 2;
		gbc_btnSelecione.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnSelecione.insets = new Insets(0, 0, 5, 5);
		gbc_btnSelecione.gridx = 3;
		gbc_btnSelecione.gridy = 5;
		getContentPane().add(btnSelecione, gbc_btnSelecione);

		textField_diretorioArquivo = new JTextField();
		textField_diretorioArquivo.setText("Nenhum Arquivo Selecionado");
		textField_diretorioArquivo.setEditable(false);
		GridBagConstraints gbc_textField_diretorioArquivo = new GridBagConstraints();
		gbc_textField_diretorioArquivo.gridwidth = 5;
		gbc_textField_diretorioArquivo.insets = new Insets(0, 0, 5, 5);
		gbc_textField_diretorioArquivo.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_diretorioArquivo.gridx = 5;
		gbc_textField_diretorioArquivo.gridy = 5;
		getContentPane().add(textField_diretorioArquivo,
				gbc_textField_diretorioArquivo);
		textField_diretorioArquivo.setColumns(10);
						
						JLabel lblSelecione = new JLabel("2 - Selecione os campos do arquivo correspondentes:");
						GridBagConstraints gbc_lblSelecione = new GridBagConstraints();
						gbc_lblSelecione.anchor = GridBagConstraints.WEST;
						gbc_lblSelecione.gridwidth = 5;
						gbc_lblSelecione.insets = new Insets(0, 0, 5, 5);
						gbc_lblSelecione.gridx = 1;
						gbc_lblSelecione.gridy = 6;
						getContentPane().add(lblSelecione, gbc_lblSelecione);
						
						JLabel lblIdPonto = new JLabel("ID ponto:");
						GridBagConstraints gbc_lblIdPonto = new GridBagConstraints();
						gbc_lblIdPonto.anchor = GridBagConstraints.EAST;
						gbc_lblIdPonto.insets = new Insets(0, 0, 5, 5);
						gbc_lblIdPonto.gridx = 2;
						gbc_lblIdPonto.gridy = 7;
						getContentPane().add(lblIdPonto, gbc_lblIdPonto);
						
						 comboBox_nomePonto = new JComboBox<Object>();
						GridBagConstraints gbc_comboBox_nomePonto = new GridBagConstraints();
						gbc_comboBox_nomePonto.gridwidth = 3;
						gbc_comboBox_nomePonto.insets = new Insets(0, 0, 5, 5);
						gbc_comboBox_nomePonto.fill = GridBagConstraints.HORIZONTAL;
						gbc_comboBox_nomePonto.gridx = 3;
						gbc_comboBox_nomePonto.gridy = 7;
						getContentPane().add(comboBox_nomePonto, gbc_comboBox_nomePonto);
						
						JLabel lblCoordenadaX = new JLabel("Coordenada X:");
						GridBagConstraints gbc_lblCoordenadaX = new GridBagConstraints();
						gbc_lblCoordenadaX.anchor = GridBagConstraints.EAST;
						gbc_lblCoordenadaX.insets = new Insets(0, 0, 5, 5);
						gbc_lblCoordenadaX.gridx = 2;
						gbc_lblCoordenadaX.gridy = 8;
						getContentPane().add(lblCoordenadaX, gbc_lblCoordenadaX);
						
						comboBox_coordenadaX = new JComboBox<Object>();
						
						GridBagConstraints gbc_comboBox_coordenadaX = new GridBagConstraints();
						gbc_comboBox_coordenadaX.gridwidth = 3;
						gbc_comboBox_coordenadaX.insets = new Insets(0, 0, 5, 5);
						gbc_comboBox_coordenadaX.fill = GridBagConstraints.HORIZONTAL;
						gbc_comboBox_coordenadaX.gridx = 3;
						gbc_comboBox_coordenadaX.gridy = 8;
						getContentPane().add(comboBox_coordenadaX, gbc_comboBox_coordenadaX);
						
						JLabel lblCoordenadaY = new JLabel("Coordenada Y:");
						GridBagConstraints gbc_lblCoordenadaY = new GridBagConstraints();
						gbc_lblCoordenadaY.anchor = GridBagConstraints.EAST;
						gbc_lblCoordenadaY.insets = new Insets(0, 0, 5, 5);
						gbc_lblCoordenadaY.gridx = 2;
						gbc_lblCoordenadaY.gridy = 9;
						getContentPane().add(lblCoordenadaY, gbc_lblCoordenadaY);
						
						comboBox_coordenadaY = new JComboBox<Object>();
						GridBagConstraints gbc_comboBox_coordenadaY = new GridBagConstraints();
						gbc_comboBox_coordenadaY.gridwidth = 3;
						gbc_comboBox_coordenadaY.insets = new Insets(0, 0, 5, 5);
						gbc_comboBox_coordenadaY.fill = GridBagConstraints.HORIZONTAL;
						gbc_comboBox_coordenadaY.gridx = 3;
						gbc_comboBox_coordenadaY.gridy = 9;
						getContentPane().add(comboBox_coordenadaY, gbc_comboBox_coordenadaY);
						
						JLabel lblCarga = new JLabel("Carga:");
						GridBagConstraints gbc_lblCarga = new GridBagConstraints();
						gbc_lblCarga.anchor = GridBagConstraints.EAST;
						gbc_lblCarga.insets = new Insets(0, 0, 5, 5);
						gbc_lblCarga.gridx = 2;
						gbc_lblCarga.gridy = 10;
						getContentPane().add(lblCarga, gbc_lblCarga);
						
						comboBox_cargaPonto = new JComboBox<Object>();
						GridBagConstraints gbc_comboBox_cargaPonto = new GridBagConstraints();
						gbc_comboBox_cargaPonto.gridwidth = 3;
						gbc_comboBox_cargaPonto.insets = new Insets(0, 0, 5, 5);
						gbc_comboBox_cargaPonto.fill = GridBagConstraints.HORIZONTAL;
						gbc_comboBox_cargaPonto.gridx = 3;
						gbc_comboBox_cargaPonto.gridy = 10;
						getContentPane().add(comboBox_cargaPonto, gbc_comboBox_cargaPonto);
						
						JLabel lblTempoDeOperao = new JLabel("Tempo de Opera\u00E7\u00E3o:");
						GridBagConstraints gbc_lblTempoDeOperao = new GridBagConstraints();
						gbc_lblTempoDeOperao.anchor = GridBagConstraints.EAST;
						gbc_lblTempoDeOperao.insets = new Insets(0, 0, 5, 5);
						gbc_lblTempoDeOperao.gridx = 2;
						gbc_lblTempoDeOperao.gridy = 11;
						getContentPane().add(lblTempoDeOperao, gbc_lblTempoDeOperao);
						
						comboBox_Tempo = new JComboBox<Object>();
						GridBagConstraints gbc_comboBox_Tempo = new GridBagConstraints();
						gbc_comboBox_Tempo.gridwidth = 3;
						gbc_comboBox_Tempo.insets = new Insets(0, 0, 5, 5);
						gbc_comboBox_Tempo.fill = GridBagConstraints.HORIZONTAL;
						gbc_comboBox_Tempo.gridx = 3;
						gbc_comboBox_Tempo.gridy = 11;
						getContentPane().add(comboBox_Tempo, gbc_comboBox_Tempo);
						
						JLabel lblValorDaCarga = new JLabel("Valor da Carga:");
						GridBagConstraints gbc_lblValorDaCarga = new GridBagConstraints();
						gbc_lblValorDaCarga.anchor = GridBagConstraints.EAST;
						gbc_lblValorDaCarga.insets = new Insets(0, 0, 5, 5);
						gbc_lblValorDaCarga.gridx = 2;
						gbc_lblValorDaCarga.gridy = 12;
						getContentPane().add(lblValorDaCarga, gbc_lblValorDaCarga);
						
						 comboBox_valorCarga= new JComboBox<Object>();
						GridBagConstraints gbc_comboBox_valorCarga = new GridBagConstraints();
						gbc_comboBox_valorCarga.gridwidth = 3;
						gbc_comboBox_valorCarga.insets = new Insets(0, 0, 5, 5);
						gbc_comboBox_valorCarga.fill = GridBagConstraints.HORIZONTAL;
						gbc_comboBox_valorCarga.gridx = 3;
						gbc_comboBox_valorCarga.gridy = 12;
						getContentPane().add(comboBox_valorCarga, gbc_comboBox_valorCarga);
						
						JLabel lblObservao = new JLabel("Observa\u00E7\u00E3o:");
						GridBagConstraints gbc_lblObservao = new GridBagConstraints();
						gbc_lblObservao.anchor = GridBagConstraints.EAST;
						gbc_lblObservao.insets = new Insets(0, 0, 5, 5);
						gbc_lblObservao.gridx = 2;
						gbc_lblObservao.gridy = 13;
						getContentPane().add(lblObservao, gbc_lblObservao);
						
						comboBox_Observacao = new JComboBox<Object>();
						GridBagConstraints gbc_comboBox_Observacao = new GridBagConstraints();
						gbc_comboBox_Observacao.gridwidth = 3;
						gbc_comboBox_Observacao.insets = new Insets(0, 0, 5, 5);
						gbc_comboBox_Observacao.fill = GridBagConstraints.HORIZONTAL;
						gbc_comboBox_Observacao.gridx = 3;
						gbc_comboBox_Observacao.gridy = 13;
						getContentPane().add(comboBox_Observacao, gbc_comboBox_Observacao);
				
						JLabel lblCarregar = new JLabel("3 - Carregue a Camada");
						GridBagConstraints gbc_lblCarregar = new GridBagConstraints();
						gbc_lblCarregar.anchor = GridBagConstraints.WEST;
						gbc_lblCarregar.gridwidth = 2;
						gbc_lblCarregar.insets = new Insets(0, 0, 5, 5);
						gbc_lblCarregar.gridx = 1;
						gbc_lblCarregar.gridy = 15;
						getContentPane().add(lblCarregar, gbc_lblCarregar);
						
								JButton btnCarregaArquivo = new JButton("Carregar");
								btnCarregaArquivo.setToolTipText("Clique para gerar a Layer com base no arquivo carregado.");
								GridBagConstraints gbc_btnCarregaArquivo = new GridBagConstraints();
								gbc_btnCarregaArquivo.gridwidth = 3;
								gbc_btnCarregaArquivo.fill = GridBagConstraints.HORIZONTAL;
								gbc_btnCarregaArquivo.insets = new Insets(0, 0, 5, 5);
								gbc_btnCarregaArquivo.gridx = 3;
								gbc_btnCarregaArquivo.gridy = 15;
								getContentPane().add(btnCarregaArquivo, gbc_btnCarregaArquivo);

		btnCarregaArquivo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				try{
						if (fileUploaded != null) {
							GeoUtilidades g = new GeoUtilidades();
							g.desenhaPontosNaCamada(lerArquivoCSV(
									fileUploaded),
									ioPlugIn.getPlugInContext(),
									comboBox_coordenadaX.getSelectedItem().toString(),
									comboBox_coordenadaY.getSelectedItem().toString(),
									comboBox_nomePonto.getSelectedItem().toString(),
									comboBox_cargaPonto.getSelectedItem().toString(),
									comboBox_Tempo.getSelectedItem().toString(),
									comboBox_valorCarga.getSelectedItem().toString(),
									comboBox_Observacao.getSelectedItem().toString());
							
							JOptionPane.showMessageDialog(null,
									"Arquivo Carregado com Sucesso!");
							dispose();
						} else {
							JOptionPane.showMessageDialog(null,
									"Selecione o arquivo primeiro.");
						}
						}
				catch (Exception e) {
					fileUploaded = null;
					textField_diretorioArquivo.setText("");
				}
				
			}
		});
	}

	
	private void setComboCamposArquivo(JComboBox<Object> combo,File arquivo	)  throws Exception {
		try {
		
			List<String> listaString = lerArquivoCSV(arquivo);
			String [] arrayCabecalho = listaString.get(0).split(";");//pegando o cabecalho
			
			((DefaultComboBoxModel<Object>) combo.getModel())
					.removeAllElements();

			((DefaultComboBoxModel<Object>) combo.getModel()).addElement("Selecione");
			for (String i : arrayCabecalho) {
				 {
					((DefaultComboBoxModel<Object>) combo.getModel())
							.addElement(i);
				}
			}
		
		
		}
		catch (Exception e) {
			e.printStackTrace();
			JOptionPane
			.showMessageDialog(
					null,
					"A camada selecionada n�o � v�lida.");
			throw new Exception();
		}
	}

	
	
	public List<String> lerArquivoCSV(File file) {
		List<String> linhasInfoPontos = null;
		Path path = Paths.get(file.getPath());
		try {
			linhasInfoPontos = Files.readAllLines(path, StandardCharsets.UTF_8);
		} catch (IOException e) {
			System.out.println("Erro na leitura do Arquivo");
			e.printStackTrace();
		}
		return linhasInfoPontos;
	}

}
