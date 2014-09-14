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

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import br.puc.rio.utilities.JNumericField;
import br.puc.rio.zona.CalcAreaServico;
import br.puc.rio.zona.Distrito;
import br.puc.rio.zona.PlugInAreaAdmissivelZona;
import br.puc.rio.zona.Zona;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.LinearRing;
import com.vividsolutions.jts.geom.Polygon;
import com.vividsolutions.jts.geom.impl.CoordinateArraySequence;

public class TelaZonaAreaAdmissivel extends JDialog {
	
	private static final long serialVersionUID = -4999998963804170945L;
	
	PlugInAreaAdmissivelZona zonaPlugIn;
	private Zona zona;
	private JNumericField txtMediatparada;
	private JNumericField txtDesvtparada;
	private JNumericField txtMediacarga;
	private JNumericField txtDesvcarga;
	private JNumericField txtVelomed;
	private JNumericField txtCapacid;
	private JNumericField txtLambda;
	private JNumericField txtMediainout;
	private JNumericField txtDesvinout;
	private JTextField txtResulareaPorCarga;
	private JTextField txtResultAreaTempo;
	private JNumericField txtCoefAjusteDistan;
	private JNumericField txtCoefAjusteTempo;
	private JNumericField txtNiveldeServico;
	private JNumericField textField_tempoJornadaEmminutosAreaAdmissivel;
	
	public TelaZonaAreaAdmissivel(PlugInAreaAdmissivelZona aPlugIn) {
		
		super(aPlugIn.getPlugInContext().getWorkbenchFrame(),"", false);
		setResizable(false);
		getContentPane().setBackground(Color.WHITE);
		setTitle("C\u00E1lculo de \u00C1rea Admiss\u00EDvel Por N\u00EDvel de Servi\u00E7o");
		this.zonaPlugIn = aPlugIn;
		
		setBounds(100, 100, 629, 536);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 36, 158, 49, 141, 115, 92, 61, 53, 0, 29, 0};
		gridBagLayout.rowHeights = new int[]{19, 0, 27, 0, 25, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		getContentPane().setLayout(gridBagLayout);
		
		JLabel lblInformaesParaClculo_2 = new JLabel("Informa\u00E7\u00F5es para C\u00E1lculo de \u00C1rea Admiss\u00EDvel:");
		GridBagConstraints gbc_lblInformaesParaClculo_2 = new GridBagConstraints();
		gbc_lblInformaesParaClculo_2.gridheight = 2;
		gbc_lblInformaesParaClculo_2.anchor = GridBagConstraints.WEST;
		gbc_lblInformaesParaClculo_2.gridwidth = 4;
		gbc_lblInformaesParaClculo_2.insets = new Insets(0, 0, 5, 5);
		gbc_lblInformaesParaClculo_2.gridx = 2;
		gbc_lblInformaesParaClculo_2.gridy = 0;
		getContentPane().add(lblInformaesParaClculo_2, gbc_lblInformaesParaClculo_2);
		
		JLabel lblMdia = new JLabel("M\u00E9dia");
		GridBagConstraints gbc_lblMdia = new GridBagConstraints();
		gbc_lblMdia.insets = new Insets(0, 0, 5, 5);
		gbc_lblMdia.gridx = 4;
		gbc_lblMdia.gridy = 2;
		getContentPane().add(lblMdia, gbc_lblMdia);
		
		JLabel lblDesviop = new JLabel("DesvioP");
		GridBagConstraints gbc_lblDesviop = new GridBagConstraints();
		gbc_lblDesviop.insets = new Insets(0, 0, 5, 5);
		gbc_lblDesviop.gridx = 5;
		gbc_lblDesviop.gridy = 2;
		getContentPane().add(lblDesviop, gbc_lblDesviop);
		
		JLabel lblTempoInoutZona = new JLabel("Tempo In ou Out Zona :");
		GridBagConstraints gbc_lblTempoInoutZona = new GridBagConstraints();
		gbc_lblTempoInoutZona.insets = new Insets(0, 0, 5, 5);
		gbc_lblTempoInoutZona.gridx = 2;
		gbc_lblTempoInoutZona.gridy = 3;
		getContentPane().add(lblTempoInoutZona, gbc_lblTempoInoutZona);
		
		txtMediainout =new JNumericField(10,3);
		txtMediainout.setPrecision(3);
		txtMediainout.setAllowNegative(false);
		txtMediainout.setToolTipText("Insira o tempo para entrar ou sair da zona. Obs: O tempo de entrada deverá ser igual ao tempo de saida.");
		txtMediainout.setText("65");
		GridBagConstraints gbc_txtMediainout = new GridBagConstraints();
		gbc_txtMediainout.insets = new Insets(0, 0, 5, 5);
		gbc_txtMediainout.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtMediainout.gridx = 4;
		gbc_txtMediainout.gridy = 3;
		getContentPane().add(txtMediainout, gbc_txtMediainout);
		txtMediainout.setColumns(10);
		
		txtDesvinout = new JNumericField(10,3);
		txtDesvinout.setPrecision(3);
		txtDesvinout.setAllowNegative(false);
		txtDesvinout.setToolTipText("Insira o Desvio Padrao do Tempo para Entrar ou Sair da Zona.");
		txtDesvinout.setText("12.875");
		GridBagConstraints gbc_txtDesvinout = new GridBagConstraints();
		gbc_txtDesvinout.insets = new Insets(0, 0, 5, 5);
		gbc_txtDesvinout.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtDesvinout.gridx = 5;
		gbc_txtDesvinout.gridy = 3;
		getContentPane().add(txtDesvinout, gbc_txtDesvinout);
		txtDesvinout.setColumns(10);
		
		JLabel label_2 = new JLabel("Em minutos.");
		GridBagConstraints gbc_label_2 = new GridBagConstraints();
		gbc_label_2.gridwidth = 3;
		gbc_label_2.insets = new Insets(0, 0, 5, 5);
		gbc_label_2.gridx = 6;
		gbc_label_2.gridy = 3;
		getContentPane().add(label_2, gbc_label_2);
		
		JLabel lblNewLabel = new JLabel("Tempo de Parada :");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 2;
		gbc_lblNewLabel.gridy = 4;
		getContentPane().add(lblNewLabel, gbc_lblNewLabel);
		
		txtMediatparada =  new JNumericField(10,3);
		txtMediatparada.setPrecision(3);
		txtMediatparada.setAllowNegative(false);
		txtMediatparada.setToolTipText("Insira o tempo medio de parada por atendimento.");
		txtMediatparada.setText("10");
		GridBagConstraints gbc_txtMediatparada = new GridBagConstraints();
		gbc_txtMediatparada.insets = new Insets(0, 0, 5, 5);
		gbc_txtMediatparada.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtMediatparada.gridx = 4;
		gbc_txtMediatparada.gridy = 4;
		getContentPane().add(txtMediatparada, gbc_txtMediatparada);
		txtMediatparada.setColumns(10);
		
		txtDesvtparada = new JNumericField(10,3);
		txtDesvtparada.setPrecision(3);
		txtDesvtparada.setAllowNegative(false);
		txtDesvtparada.setToolTipText("Insira o Desvio Padrao do tempo de parada por atendimento.");
		txtDesvtparada.setText("2");
		GridBagConstraints gbc_txtDesvtparada = new GridBagConstraints();
		gbc_txtDesvtparada.insets = new Insets(0, 0, 5, 5);
		gbc_txtDesvtparada.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtDesvtparada.gridx = 5;
		gbc_txtDesvtparada.gridy = 4;
		getContentPane().add(txtDesvtparada, gbc_txtDesvtparada);
		txtDesvtparada.setColumns(10);
		
		JLabel lblEmMinutos = new JLabel("Em minutos.");
		GridBagConstraints gbc_lblEmMinutos = new GridBagConstraints();
		gbc_lblEmMinutos.gridwidth = 3;
		gbc_lblEmMinutos.insets = new Insets(0, 0, 5, 5);
		gbc_lblEmMinutos.gridx = 6;
		gbc_lblEmMinutos.gridy = 4;
		getContentPane().add(lblEmMinutos, gbc_lblEmMinutos);
		
		JLabel lblCarga = new JLabel("Carga :");
		GridBagConstraints gbc_lblCarga = new GridBagConstraints();
		gbc_lblCarga.insets = new Insets(0, 0, 5, 5);
		gbc_lblCarga.gridx = 2;
		gbc_lblCarga.gridy = 5;
		getContentPane().add(lblCarga, gbc_lblCarga);
		
		txtMediacarga = new JNumericField(10,3);
		txtMediacarga.setPrecision(3);
		txtMediacarga.setAllowNegative(false);
		txtMediacarga.setToolTipText("Insira a carga media a ser coletado em cada atendimento.");
		txtMediacarga.setText("9000");
		GridBagConstraints gbc_txtMediacarga = new GridBagConstraints();
		gbc_txtMediacarga.insets = new Insets(0, 0, 5, 5);
		gbc_txtMediacarga.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtMediacarga.gridx = 4;
		gbc_txtMediacarga.gridy = 5;
		getContentPane().add(txtMediacarga, gbc_txtMediacarga);
		txtMediacarga.setColumns(10);
		
		txtDesvcarga = new JNumericField(10,3);
		txtDesvcarga.setPrecision(3);
		txtDesvcarga.setAllowNegative(false);
		txtDesvcarga.setToolTipText("Insira o valor do desvio padrao da carga a ser coletada em cada ponto de atendimento.");
		txtDesvcarga.setText("1687.3055");
		GridBagConstraints gbc_txtDesvcarga = new GridBagConstraints();
		gbc_txtDesvcarga.insets = new Insets(0, 0, 5, 5);
		gbc_txtDesvcarga.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtDesvcarga.gridx = 5;
		gbc_txtDesvcarga.gridy = 5;
		getContentPane().add(txtDesvcarga, gbc_txtDesvcarga);
		txtDesvcarga.setColumns(10);
		
		JLabel lblKgOuM = new JLabel("Kg ou M\u00B3.");
		GridBagConstraints gbc_lblKgOuM = new GridBagConstraints();
		gbc_lblKgOuM.anchor = GridBagConstraints.WEST;
		gbc_lblKgOuM.insets = new Insets(0, 0, 5, 5);
		gbc_lblKgOuM.gridx = 6;
		gbc_lblKgOuM.gridy = 5;
		getContentPane().add(lblKgOuM, gbc_lblKgOuM);
		
		JLabel lblDistncia = new JLabel("Dist\u00E2ncia");
		GridBagConstraints gbc_lblDistncia = new GridBagConstraints();
		gbc_lblDistncia.insets = new Insets(0, 0, 5, 5);
		gbc_lblDistncia.gridx = 4;
		gbc_lblDistncia.gridy = 6;
		getContentPane().add(lblDistncia, gbc_lblDistncia);
		
		JLabel lblTempo_1 = new JLabel("Tempo");
		GridBagConstraints gbc_lblTempo_1 = new GridBagConstraints();
		gbc_lblTempo_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblTempo_1.gridx = 5;
		gbc_lblTempo_1.gridy = 6;
		getContentPane().add(lblTempo_1, gbc_lblTempo_1);
		
		JLabel lblNewLabel_1 = new JLabel("Coeficiente Ajuste :");
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.gridx = 2;
		gbc_lblNewLabel_1.gridy = 7;
		getContentPane().add(lblNewLabel_1, gbc_lblNewLabel_1);
		
		txtCoefAjusteDistan = new JNumericField(10,3);
		txtCoefAjusteDistan.setPrecision(3);
		txtCoefAjusteDistan.setAllowNegative(false);
		txtCoefAjusteDistan.setText("0.765");
		GridBagConstraints gbc_txtCoefAjusteDistan = new GridBagConstraints();
		gbc_txtCoefAjusteDistan.insets = new Insets(0, 0, 5, 5);
		gbc_txtCoefAjusteDistan.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtCoefAjusteDistan.gridx = 4;
		gbc_txtCoefAjusteDistan.gridy = 7;
		getContentPane().add(txtCoefAjusteDistan, gbc_txtCoefAjusteDistan);
		txtCoefAjusteDistan.setColumns(10);
		
		txtCoefAjusteTempo = new JNumericField(10,3);
		txtCoefAjusteTempo.setPrecision(3);
		txtCoefAjusteTempo.setAllowNegative(false);
		txtCoefAjusteTempo.setText("0.745");
		GridBagConstraints gbc_txtCoefAjusteTempo = new GridBagConstraints();
		gbc_txtCoefAjusteTempo.insets = new Insets(0, 0, 5, 5);
		gbc_txtCoefAjusteTempo.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtCoefAjusteTempo.gridx = 5;
		gbc_txtCoefAjusteTempo.gridy = 7;
		getContentPane().add(txtCoefAjusteTempo, gbc_txtCoefAjusteTempo);
		txtCoefAjusteTempo.setColumns(10);
		
		JLabel lblVelocidadeMdia = new JLabel("Velocidade M\u00E9dia :");
		GridBagConstraints gbc_lblVelocidadeMdia = new GridBagConstraints();
		gbc_lblVelocidadeMdia.insets = new Insets(0, 0, 5, 5);
		gbc_lblVelocidadeMdia.gridx = 2;
		gbc_lblVelocidadeMdia.gridy = 8;
		getContentPane().add(lblVelocidadeMdia, gbc_lblVelocidadeMdia);
		
		txtVelomed = new JNumericField(10,3);
		txtVelomed.setPrecision(3);
		txtVelomed.setAllowNegative(false);
		txtVelomed.setToolTipText("Insira a velocidade media do veiculo na zona.");
		txtVelomed.setText("16.12762");
		GridBagConstraints gbc_txtVelomed = new GridBagConstraints();
		gbc_txtVelomed.insets = new Insets(0, 0, 5, 5);
		gbc_txtVelomed.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtVelomed.gridx = 4;
		gbc_txtVelomed.gridy = 8;
		getContentPane().add(txtVelomed, gbc_txtVelomed);
		txtVelomed.setColumns(10);
		
		JLabel lblKmh = new JLabel("km/h");
		GridBagConstraints gbc_lblKmh = new GridBagConstraints();
		gbc_lblKmh.anchor = GridBagConstraints.WEST;
		gbc_lblKmh.insets = new Insets(0, 0, 5, 5);
		gbc_lblKmh.gridx = 5;
		gbc_lblKmh.gridy = 8;
		getContentPane().add(lblKmh, gbc_lblKmh);
		
		JLabel lblCapacidadeVeculo = new JLabel("Capacidade Ve\u00EDculo :");
		GridBagConstraints gbc_lblCapacidadeVeculo = new GridBagConstraints();
		gbc_lblCapacidadeVeculo.insets = new Insets(0, 0, 5, 5);
		gbc_lblCapacidadeVeculo.gridx = 2;
		gbc_lblCapacidadeVeculo.gridy = 9;
		getContentPane().add(lblCapacidadeVeculo, gbc_lblCapacidadeVeculo);
		
		txtCapacid = new JNumericField(10,3);
		txtCapacid.setPrecision(3);
		txtCapacid.setAllowNegative(false);
		txtCapacid.setToolTipText("Insira a capacidade total do veiculo. Obs: Sem perda.");
		txtCapacid.setText("19842");
		GridBagConstraints gbc_txtCapacid = new GridBagConstraints();
		gbc_txtCapacid.insets = new Insets(0, 0, 5, 5);
		gbc_txtCapacid.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtCapacid.gridx = 4;
		gbc_txtCapacid.gridy = 9;
		getContentPane().add(txtCapacid, gbc_txtCapacid);
		txtCapacid.setColumns(10);
		
		JLabel lblMesmaUnidadeDa = new JLabel("Mesma unidade da Carga.");
		GridBagConstraints gbc_lblMesmaUnidadeDa = new GridBagConstraints();
		gbc_lblMesmaUnidadeDa.anchor = GridBagConstraints.WEST;
		gbc_lblMesmaUnidadeDa.gridwidth = 2;
		gbc_lblMesmaUnidadeDa.insets = new Insets(0, 0, 5, 5);
		gbc_lblMesmaUnidadeDa.gridx = 5;
		gbc_lblMesmaUnidadeDa.gridy = 9;
		getContentPane().add(lblMesmaUnidadeDa, gbc_lblMesmaUnidadeDa);
		
		JLabel lblNvelDeServio = new JLabel("N\u00EDvel de Servi\u00E7o :");
		GridBagConstraints gbc_lblNvelDeServio = new GridBagConstraints();
		gbc_lblNvelDeServio.insets = new Insets(0, 0, 5, 5);
		gbc_lblNvelDeServio.gridx = 2;
		gbc_lblNvelDeServio.gridy = 10;
		getContentPane().add(lblNvelDeServio, gbc_lblNvelDeServio);
		
		txtNiveldeServico = new JNumericField(10,3);
		txtNiveldeServico.setPrecision(3);
		txtNiveldeServico.setAllowNegative(false);
		txtNiveldeServico.setToolTipText("Insira o n\u00EDvel de servi\u00E7o desejado. Exemplo: 95% = 0,95.");
		txtNiveldeServico.setText("0.95");
		GridBagConstraints gbc_txtNiveldeServico = new GridBagConstraints();
		gbc_txtNiveldeServico.insets = new Insets(0, 0, 5, 5);
		gbc_txtNiveldeServico.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtNiveldeServico.gridx = 4;
		gbc_txtNiveldeServico.gridy = 10;
		getContentPane().add(txtNiveldeServico, gbc_txtNiveldeServico);
		txtNiveldeServico.setColumns(10);
		
		JLabel lblEmFormatoDecimal = new JLabel("Em formato decimal. Exemplo: 0.95");
		GridBagConstraints gbc_lblEmFormatoDecimal = new GridBagConstraints();
		gbc_lblEmFormatoDecimal.anchor = GridBagConstraints.WEST;
		gbc_lblEmFormatoDecimal.gridwidth = 5;
		gbc_lblEmFormatoDecimal.insets = new Insets(0, 0, 5, 5);
		gbc_lblEmFormatoDecimal.gridx = 5;
		gbc_lblEmFormatoDecimal.gridy = 10;
		getContentPane().add(lblEmFormatoDecimal, gbc_lblEmFormatoDecimal);
		
		JLabel lblLambda = new JLabel("Lambda :");
		GridBagConstraints gbc_lblLambda = new GridBagConstraints();
		gbc_lblLambda.insets = new Insets(0, 0, 5, 5);
		gbc_lblLambda.gridx = 2;
		gbc_lblLambda.gridy = 11;
		getContentPane().add(lblLambda, gbc_lblLambda);
		
		txtLambda =  new JNumericField(10,3);
		txtLambda.setPrecision(3);
		txtLambda.setAllowNegative(false);
		txtLambda.setToolTipText("Insira a densidade m\u00E9dia de pontos por unidade de \u00E1rea.");
		txtLambda.setText("10");
		GridBagConstraints gbc_txtLambda = new GridBagConstraints();
		gbc_txtLambda.insets = new Insets(0, 0, 5, 5);
		gbc_txtLambda.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtLambda.gridx = 4;
		gbc_txtLambda.gridy = 11;
		getContentPane().add(txtLambda, gbc_txtLambda);
		txtLambda.setColumns(10);
		
		JLabel lblPontosDeAtendimento = new JLabel("Pontos de atendimento por Unid de \u00C1rea.");
		GridBagConstraints gbc_lblPontosDeAtendimento = new GridBagConstraints();
		gbc_lblPontosDeAtendimento.anchor = GridBagConstraints.WEST;
		gbc_lblPontosDeAtendimento.gridwidth = 6;
		gbc_lblPontosDeAtendimento.insets = new Insets(0, 0, 5, 0);
		gbc_lblPontosDeAtendimento.gridx = 5;
		gbc_lblPontosDeAtendimento.gridy = 11;
		getContentPane().add(lblPontosDeAtendimento, gbc_lblPontosDeAtendimento);
		
		JLabel lblJornadaemMinutos = new JLabel("Jornada :");
		GridBagConstraints gbc_lblJornadaemMinutos = new GridBagConstraints();
		gbc_lblJornadaemMinutos.insets = new Insets(0, 0, 5, 5);
		gbc_lblJornadaemMinutos.gridx = 2;
		gbc_lblJornadaemMinutos.gridy = 12;
		getContentPane().add(lblJornadaemMinutos, gbc_lblJornadaemMinutos);
		
		textField_tempoJornadaEmminutosAreaAdmissivel =  new JNumericField(10,3);
		textField_tempoJornadaEmminutosAreaAdmissivel.setPrecision(3);
		textField_tempoJornadaEmminutosAreaAdmissivel.setAllowNegative(false);
		textField_tempoJornadaEmminutosAreaAdmissivel.setText("480");
		GridBagConstraints gbc_textField_tempoJornadaEmminutosAreaAdmissivel = new GridBagConstraints();
		gbc_textField_tempoJornadaEmminutosAreaAdmissivel.insets = new Insets(0, 0, 5, 5);
		gbc_textField_tempoJornadaEmminutosAreaAdmissivel.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_tempoJornadaEmminutosAreaAdmissivel.gridx = 4;
		gbc_textField_tempoJornadaEmminutosAreaAdmissivel.gridy = 12;
		getContentPane().add(textField_tempoJornadaEmminutosAreaAdmissivel, gbc_textField_tempoJornadaEmminutosAreaAdmissivel);
		textField_tempoJornadaEmminutosAreaAdmissivel.setColumns(10);
		
		JLabel label_3 = new JLabel("Em minutos.");
		GridBagConstraints gbc_label_3 = new GridBagConstraints();
		gbc_label_3.anchor = GridBagConstraints.WEST;
		gbc_label_3.insets = new Insets(0, 0, 5, 5);
		gbc_label_3.gridx = 5;
		gbc_label_3.gridy = 12;
		getContentPane().add(label_3, gbc_label_3);
		
		JButton btnCalcularreaAdmissvel = new JButton("Calcular \u00C1rea Admiss\u00EDvel");
		GridBagConstraints gbc_btnCalcularreaAdmissvel = new GridBagConstraints();
		gbc_btnCalcularreaAdmissvel.fill = GridBagConstraints.BOTH;
		gbc_btnCalcularreaAdmissvel.gridwidth = 5;
		gbc_btnCalcularreaAdmissvel.insets = new Insets(0, 0, 5, 5);
		gbc_btnCalcularreaAdmissvel.gridx = 2;
		gbc_btnCalcularreaAdmissvel.gridy = 14;
		getContentPane().add(btnCalcularreaAdmissvel, gbc_btnCalcularreaAdmissvel);
		
		btnCalcularreaAdmissvel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				//criando a zona
				if (validaInfoFormulario(txtMediatparada, txtDesvtparada, txtMediacarga, txtDesvcarga, txtVelomed, txtCapacid, txtLambda, txtMediainout, txtDesvinout, txtCoefAjusteDistan, txtCoefAjusteTempo, txtNiveldeServico, textField_tempoJornadaEmminutosAreaAdmissivel)==true){
						zona = new Zona ();
						zona.setMediatparada(Double.valueOf(txtMediatparada.getText()));
						zona.setDesvPadtparada(Double.valueOf(txtDesvtparada.getText()));
						zona.setMediaCarga(Double.valueOf(txtMediacarga.getText()));
						zona.setDesvPadCarga(Double.valueOf(txtDesvcarga.getText()));
						zona.setVeloMedia(Double.valueOf(txtVelomed.getText()));
						zona.setLambda(Double.valueOf(txtLambda.getText()));
						
						CalcAreaServico as = new CalcAreaServico(zona, 
								Double.valueOf(txtCoefAjusteDistan.getText()),
										Double.valueOf(txtCoefAjusteTempo.getText()),
												Double.valueOf(textField_tempoJornadaEmminutosAreaAdmissivel.getText()),
														Double.valueOf(txtMediainout.getText()),
																Double.valueOf(txtNiveldeServico.getText()), 
																		Double.valueOf(txtCapacid.getText()));
					//Carregando os resultados
						txtResulareaPorCarga.setText(String.valueOf(as.getAreaServPorCarga()));
						txtResultAreaTempo.setText(String.valueOf(as.getAreaServPorTempo()));
			}
			}
			
		});
		
		JLabel label = new JLabel("Resultados :");
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.insets = new Insets(0, 0, 5, 5);
		gbc_label.gridx = 2;
		gbc_label.gridy = 16;
		getContentPane().add(label, gbc_label);
		
		JLabel lblPorrea = new JLabel("Por Carga :");
		GridBagConstraints gbc_lblPorrea = new GridBagConstraints();
		gbc_lblPorrea.gridwidth = 2;
		gbc_lblPorrea.anchor = GridBagConstraints.EAST;
		gbc_lblPorrea.insets = new Insets(0, 0, 5, 5);
		gbc_lblPorrea.gridx = 2;
		gbc_lblPorrea.gridy = 17;
		getContentPane().add(lblPorrea, gbc_lblPorrea);
		
		txtResulareaPorCarga = new JTextField();
		txtResulareaPorCarga.setEditable(false);
		txtResulareaPorCarga.setText("resulCarga");
		GridBagConstraints gbc_txtResulareaPorCarga = new GridBagConstraints();
		gbc_txtResulareaPorCarga.gridwidth = 2;
		gbc_txtResulareaPorCarga.insets = new Insets(0, 0, 5, 5);
		gbc_txtResulareaPorCarga.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtResulareaPorCarga.gridx = 4;
		gbc_txtResulareaPorCarga.gridy = 17;
		getContentPane().add(txtResulareaPorCarga, gbc_txtResulareaPorCarga);
		txtResulareaPorCarga.setColumns(10);
		
		JLabel label_1 = new JLabel("Por Tempo :");
		GridBagConstraints gbc_label_1 = new GridBagConstraints();
		gbc_label_1.gridwidth = 2;
		gbc_label_1.anchor = GridBagConstraints.EAST;
		gbc_label_1.insets = new Insets(0, 0, 5, 5);
		gbc_label_1.gridx = 2;
		gbc_label_1.gridy = 18;
		getContentPane().add(label_1, gbc_label_1);
		
		txtResultAreaTempo = new JTextField();
		txtResultAreaTempo.setEditable(false);
		txtResultAreaTempo.setText("resultTempo");
		txtResultAreaTempo.setColumns(10);
		GridBagConstraints gbc_txtResultAreaTempo = new GridBagConstraints();
		gbc_txtResultAreaTempo.gridwidth = 2;
		gbc_txtResultAreaTempo.insets = new Insets(0, 0, 5, 5);
		gbc_txtResultAreaTempo.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtResultAreaTempo.gridx = 4;
		gbc_txtResultAreaTempo.gridy = 18;
		getContentPane().add(txtResultAreaTempo, gbc_txtResultAreaTempo);
		
		JLabel lblNewLabel_3 = new JLabel("");
		GridBagConstraints gbc_lblNewLabel_3 = new GridBagConstraints();
		gbc_lblNewLabel_3.insets = new Insets(0, 0, 0, 5);
		gbc_lblNewLabel_3.gridx = 2;
		gbc_lblNewLabel_3.gridy = 19;
		getContentPane().add(lblNewLabel_3, gbc_lblNewLabel_3);
		
		
	}

	public LineString criarUmaLinha(double cX, double cY, double dX, double dY) {

		GeometryFactory gf = new GeometryFactory(); // this is a new line
		Coordinate c1 = new Coordinate(cX, cY);
		Coordinate c2 = new Coordinate(dX, dY);
	    Collection<Coordinate> listCord = new ArrayList<Coordinate>();
		listCord.add(c1);
		listCord.add(c2);
		Coordinate[] coords = (Coordinate[]) listCord.toArray(new Coordinate[listCord.size()]); // 
		LineString line = gf.createLineString(coords); 

		return line;
	}
	
	public LineString rotacionarLinhaPorGraus (LineString linha,double quantidadeGraus){
		
		//pegando os eixos do ponto variavel
		double eixoXpontoVariavel = linha.getEndPoint().getX();//o GETENDPOINT e o ponto variavel
		double eixoYpontoVariavel = linha.getEndPoint().getY();//o GETENDPOINT e o ponto variavel
		
		//pegando os eixos do ponto fixo = deposito
		double eixoXpontoFixo= linha.getStartPoint().getX();
		double eixoYpontoFixo = linha.getStartPoint().getY();
	
		double raio = linha.getLength();
		
		//angulo entre retas
		// double num = eixoXpontoVariavel - eixoXpontoFixo;
		// double den = eixoYpontoFixo - eixoYpontoVariavel;
		//double anguloEntreRetas = Math.atan(num/den);  
		
		double passoEmGraus = quantidadeGraus;
		double passoEmRadiano = Math.toRadians(passoEmGraus);
		
			eixoXpontoVariavel = eixoXpontoFixo + raio*Math.cos(passoEmRadiano);
			eixoYpontoVariavel = eixoYpontoFixo + raio*Math.sin(passoEmRadiano);
		
		LineString linhaNova = criarUmaLinha(eixoXpontoFixo, eixoYpontoFixo,  eixoXpontoVariavel, eixoYpontoVariavel);
		
		return linhaNova;
	}
	
	public double getAreaTotalListaDistritos (List<Distrito> listaDistritos) {
		
		double areaTodosDistritos = 0.0;
		java.util.Iterator<Distrito> iterar = listaDistritos.iterator();
		while(iterar.hasNext()){
			Distrito novo = (Distrito) iterar.next();
			areaTodosDistritos = areaTodosDistritos + novo.getAreaDistrito();
		}
		
		return areaTodosDistritos;
	}
	
	
	public List<Polygon> criarListDeTriangulosIsosceles (double extremX, double extremY, double baseX, double baseY, double anguloEmGraus) {
		List<Polygon> listaTringulos = new ArrayList<>();
		double qtdTriangulos = 360/anguloEmGraus;
		int contador = 1;
		 double anguloMenor = 0;
		 double anguloMaior = anguloEmGraus;
		//gerando linhas do triangulo inicial
		LineString novaLinha = criarUmaLinha(baseX,baseY,extremX ,extremY);
		LineString linha1 = rotacionarLinhaPorGraus(novaLinha, anguloMenor);
		LineString linha2 = rotacionarLinhaPorGraus(novaLinha, anguloMaior);
		//pegando as coordenadas do triangulo inicial
		Coordinate cPontoFixo = new Coordinate(baseX,baseY);
		Coordinate cPontoVar1 = new Coordinate(linha1.getEndPoint().getX(),linha1.getEndPoint().getY());
		Coordinate cPontoVar2 = new Coordinate(linha2.getEndPoint().getX(),linha2.getEndPoint().getY());
		Coordinate cEmenda = cPontoFixo; //essa redundancia e necessaria para criar o poligono triangulo
		
		while (contador<=qtdTriangulos){
				
				  GeometryFactory factory = new GeometryFactory();
				    CoordinateArraySequence coords = new CoordinateArraySequence(new Coordinate[] { 
				    		cPontoFixo,
				    		cPontoVar1,
				    		cPontoVar2,
				    		cEmenda });
				    
				    //criando o poligono triangulo
				    LinearRing ring = new LinearRing(coords, factory);
				    Polygon p = new Polygon(ring, null, factory);
				    //adicionando o poligono triangulo a lista
				    listaTringulos.add(p);
				    
				    //Calculando altura do triangulo
				    LineString linhaBase = criarUmaLinha(cPontoVar2.x,cPontoVar2.y,cPontoVar1.x ,cPontoVar1.y);
				    double comprimentoLadoMaior = linha1.getLength();
				    double altura = Math.sqrt(Math.pow(comprimentoLadoMaior, 2)-(Math.pow(linhaBase.getLength(), 2)/4));
				    double areaTriangulo = linhaBase.getLength()*altura/2;
				    System.out.println("Area triangulo = " + areaTriangulo);
				    
				  //gerando linhas do novo triangulo 
				    anguloMenor = anguloMenor + anguloEmGraus;
					linha1 = rotacionarLinhaPorGraus(novaLinha,anguloMenor);
					anguloMaior = anguloMaior + anguloEmGraus;
				    linha2 = rotacionarLinhaPorGraus(novaLinha, anguloMaior);
				  //pegando as coordenadas do novo triangulo
					 cPontoFixo = new Coordinate(baseX,baseY);
					 cPontoVar1 = new Coordinate(linha1.getEndPoint().getX(),linha1.getEndPoint().getY());
					 cPontoVar2 = new Coordinate(linha2.getEndPoint().getX(),linha2.getEndPoint().getY());
					 cEmenda = cPontoFixo; //essa redundancia e necessaria para criar o poligono triangulo
				   
				contador++;
		}
				    
		return listaTringulos; 
	}
	
	public boolean validaInfoFormulario(
			 JNumericField txtMediatparada,
			 JNumericField txtDesvtparada,
			 JNumericField txtMediacarga,
			 JNumericField txtDesvcarga,
			 JNumericField txtVelomed,
			 JNumericField txtCapacid,
			 JNumericField txtLambda,
			 JNumericField txtMediainout,
			 JNumericField txtDesvinout,
			 JNumericField txtCoefAjusteDistan,
			 JNumericField txtCoefAjusteTempo,
			 JNumericField txtNiveldeServico,
			 JNumericField textField_tempoJornadaEmminutosAreaAdmissivel) {
		int qtd = 0;
		boolean resposta = false;
		if (txtMediatparada.getText().isEmpty() == false) {
			qtd++;
		}
		if (txtDesvtparada.getText().isEmpty() == false) {
			qtd++;
		}
		if (txtMediacarga.getText().isEmpty() == false) {
			qtd++;
		}
		if (txtDesvcarga.getText().isEmpty() == false) {
			qtd++;
		}
		if (txtVelomed.getText().isEmpty() == false) {
			qtd++;
		}
		if (txtCapacid.getText().isEmpty() == false) {
			qtd++;
		}
		if (txtLambda.getText().isEmpty() == false) {
			qtd++;
		}
		if (txtMediainout.getText().isEmpty() == false) {
			qtd++;
		}
		if (txtDesvinout.getText().isEmpty() == false) {
			qtd++;
		}
		if (txtCoefAjusteDistan.getText().isEmpty() == false) {
			qtd++;
		}
		if (txtCoefAjusteTempo.getText().isEmpty() == false) {
			qtd++;
		}
		if (txtNiveldeServico.getText().isEmpty() == false) {
			qtd++;
		}
		if (textField_tempoJornadaEmminutosAreaAdmissivel.getText().isEmpty() == false) {
			qtd++;
		}
		if (qtd < 13) {
			JOptionPane
					.showMessageDialog(
							null, 
							"N�o foi poss�vel executar o c�lculo. Par�metros pendentes de preenchimento.");
			resposta = false;
		} else {
			resposta = true;
		}

		return resposta;
	}

	
}
