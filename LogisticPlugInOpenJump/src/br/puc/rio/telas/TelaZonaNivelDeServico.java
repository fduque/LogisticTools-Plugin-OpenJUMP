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
import br.puc.rio.zona.CalcNivelServico;
import br.puc.rio.zona.Distrito;
import br.puc.rio.zona.PlugInNivelServicoZona;
import br.puc.rio.zona.Zona;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.LinearRing;
import com.vividsolutions.jts.geom.Polygon;
import com.vividsolutions.jts.geom.impl.CoordinateArraySequence;

public class TelaZonaNivelDeServico extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4999998963804170945L;
	/**
	 * 
	 */

	PlugInNivelServicoZona zonaPlugIn;
	private JNumericField txtMediatc;
	private JNumericField txtDesvtc;
	private JNumericField txtMediacarga;
	private JNumericField txtDesvcarga;
	private Zona zona;
	private JNumericField txtCapacid;
	private JNumericField txtHnormal;
	private JNumericField txtHmax;
	private JNumericField txtperdaest;
	private JTextField txtNscapa;
	private JTextField txtNahnorm;
	private JTextField txtNshmx;

	public TelaZonaNivelDeServico(PlugInNivelServicoZona aPlugIn) {

		super(aPlugIn.getPlugInContext().getWorkbenchFrame(), "", false);
		setResizable(false);
		getContentPane().setBackground(Color.WHITE);
		setTitle("C\u00E1lculo do N\u00EDvel de Servi\u00E7o Por Capacidade ou Por Tempo");
		this.zonaPlugIn = aPlugIn;

		setBounds(100, 100, 607, 525);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 35, 158, 49, 141, 115, 120,
				61, 53, 0, 29, 0 };
		gridBagLayout.rowHeights = new int[] { 23, 0, 27, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 0.0, 0.0, 1.0, 1.0,
				1.0, 1.0, 1.0, 1.0, 1.0, 0.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		getContentPane().setLayout(gridBagLayout);

		JLabel lblInformaesParaClculo_1 = new JLabel(
				"Informa\u00E7\u00F5es para C\u00E1lculo de N\u00EDvel de Servi\u00E7o :");
		lblInformaesParaClculo_1.setBackground(Color.WHITE);
		lblInformaesParaClculo_1.setForeground(Color.BLACK);
		GridBagConstraints gbc_lblInformaesParaClculo_1 = new GridBagConstraints();
		gbc_lblInformaesParaClculo_1.anchor = GridBagConstraints.WEST;
		gbc_lblInformaesParaClculo_1.gridwidth = 4;
		gbc_lblInformaesParaClculo_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblInformaesParaClculo_1.gridx = 2;
		gbc_lblInformaesParaClculo_1.gridy = 1;
		getContentPane().add(lblInformaesParaClculo_1,
				gbc_lblInformaesParaClculo_1);

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

		JLabel lblTempo = new JLabel("Tempo de Ciclo :");
		GridBagConstraints gbc_lblTempo = new GridBagConstraints();
		gbc_lblTempo.insets = new Insets(0, 0, 5, 5);
		gbc_lblTempo.gridx = 2;
		gbc_lblTempo.gridy = 3;
		getContentPane().add(lblTempo, gbc_lblTempo);

		txtMediatc = new JNumericField(10,3);
		txtMediatc.setPrecision(3);
		txtMediatc.setAllowNegative(false);
		txtMediatc.setText("6.70833");
		txtMediatc.setToolTipText("Insira o Tempo de Ciclo medio na zona.");
		GridBagConstraints gbc_txtMediatc = new GridBagConstraints();
		gbc_txtMediatc.insets = new Insets(0, 0, 5, 5);
		gbc_txtMediatc.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtMediatc.gridx = 4;
		gbc_txtMediatc.gridy = 3;
		getContentPane().add(txtMediatc, gbc_txtMediatc);
		txtMediatc.setColumns(10);

		txtDesvtc = new JNumericField(10,3);
		txtDesvtc.setPrecision(3);
		txtDesvtc.setAllowNegative(false);
		txtDesvtc.setToolTipText("Insira o Desvio Padrao do Tempo de Ciclo na zona.");
		txtDesvtc.setText("0.901");
		GridBagConstraints gbc_txtDesvtc = new GridBagConstraints();
		gbc_txtDesvtc.insets = new Insets(0, 0, 5, 5);
		gbc_txtDesvtc.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtDesvtc.gridx = 5;
		gbc_txtDesvtc.gridy = 3;
		getContentPane().add(txtDesvtc, gbc_txtDesvtc);
		txtDesvtc.setColumns(10);

		JLabel lblJornadaNormal = new JLabel("Jornada Normal :");
		GridBagConstraints gbc_lblJornadaNormal = new GridBagConstraints();
		gbc_lblJornadaNormal.insets = new Insets(0, 0, 5, 5);
		gbc_lblJornadaNormal.gridx = 2;
		gbc_lblJornadaNormal.gridy = 4;
		getContentPane().add(lblJornadaNormal, gbc_lblJornadaNormal);

		txtHnormal = new JNumericField(10,3);
		txtHnormal.setPrecision(3);
		txtHnormal.setAllowNegative(false);
		txtHnormal
				.setToolTipText("Insira a quantidade de horas na jornada normal.");
		txtHnormal.setText("8");
		GridBagConstraints gbc_txtHnormal = new GridBagConstraints();
		gbc_txtHnormal.insets = new Insets(0, 0, 5, 5);
		gbc_txtHnormal.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtHnormal.gridx = 4;
		gbc_txtHnormal.gridy = 4;
		getContentPane().add(txtHnormal, gbc_txtHnormal);
		txtHnormal.setColumns(10);

		JLabel lblJornadaMxima = new JLabel("Jornada M\u00E1xima :");
		GridBagConstraints gbc_lblJornadaMxima = new GridBagConstraints();
		gbc_lblJornadaMxima.insets = new Insets(0, 0, 5, 5);
		gbc_lblJornadaMxima.gridx = 2;
		gbc_lblJornadaMxima.gridy = 5;
		getContentPane().add(lblJornadaMxima, gbc_lblJornadaMxima);

		txtHmax = new JNumericField(10,3);
		txtHmax.setPrecision(3);
		txtHmax.setAllowNegative(false);
		txtHmax.setToolTipText("Insira a quantidade de horas na jornada maxima.");
		txtHmax.setText("10");
		GridBagConstraints gbc_txtHmax = new GridBagConstraints();
		gbc_txtHmax.insets = new Insets(0, 0, 5, 5);
		gbc_txtHmax.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtHmax.gridx = 4;
		gbc_txtHmax.gridy = 5;
		getContentPane().add(txtHmax, gbc_txtHmax);
		txtHmax.setColumns(10);

		JLabel label_3 = new JLabel("M\u00E9dia");
		GridBagConstraints gbc_label_3 = new GridBagConstraints();
		gbc_label_3.insets = new Insets(0, 0, 5, 5);
		gbc_label_3.gridx = 4;
		gbc_label_3.gridy = 6;
		getContentPane().add(label_3, gbc_label_3);

		JLabel label_4 = new JLabel("DesvioP");
		GridBagConstraints gbc_label_4 = new GridBagConstraints();
		gbc_label_4.insets = new Insets(0, 0, 5, 5);
		gbc_label_4.gridx = 5;
		gbc_label_4.gridy = 6;
		getContentPane().add(label_4, gbc_label_4);

		JLabel lblCarga = new JLabel("Carga :");
		GridBagConstraints gbc_lblCarga = new GridBagConstraints();
		gbc_lblCarga.insets = new Insets(0, 0, 5, 5);
		gbc_lblCarga.gridx = 2;
		gbc_lblCarga.gridy = 7;
		getContentPane().add(lblCarga, gbc_lblCarga);

		txtMediacarga = new JNumericField(10,3);
		txtMediacarga.setPrecision(3);
		txtMediacarga.setAllowNegative(false);
		txtMediacarga
				.setToolTipText("Insira a carga media a ser coletado em cada atendimento.");
		txtMediacarga.setText("9000");
		GridBagConstraints gbc_txtMediacarga = new GridBagConstraints();
		gbc_txtMediacarga.insets = new Insets(0, 0, 5, 5);
		gbc_txtMediacarga.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtMediacarga.gridx = 4;
		gbc_txtMediacarga.gridy = 7;
		getContentPane().add(txtMediacarga, gbc_txtMediacarga);
		txtMediacarga.setColumns(10);

		txtDesvcarga = new JNumericField(10,3);
		txtDesvcarga.setPrecision(3);
		txtDesvcarga.setAllowNegative(false);
		txtDesvcarga
				.setToolTipText("Insira o valor do desvio padrao da carga a ser coletada em cada ponto de atendimento.");
		txtDesvcarga.setText("1687.3055");
		GridBagConstraints gbc_txtDesvcarga = new GridBagConstraints();
		gbc_txtDesvcarga.insets = new Insets(0, 0, 5, 5);
		gbc_txtDesvcarga.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtDesvcarga.gridx = 5;
		gbc_txtDesvcarga.gridy = 7;
		getContentPane().add(txtDesvcarga, gbc_txtDesvcarga);
		txtDesvcarga.setColumns(10);

		JLabel lblCapacidadeVeculo = new JLabel("Capacidade Ve\u00EDculo :");
		GridBagConstraints gbc_lblCapacidadeVeculo = new GridBagConstraints();
		gbc_lblCapacidadeVeculo.insets = new Insets(0, 0, 5, 5);
		gbc_lblCapacidadeVeculo.gridx = 2;
		gbc_lblCapacidadeVeculo.gridy = 8;
		getContentPane().add(lblCapacidadeVeculo, gbc_lblCapacidadeVeculo);

		txtCapacid =  new JNumericField(10,3);
		txtCapacid.setPrecision(3);
		txtCapacid.setAllowNegative(false);
		txtCapacid
				.setToolTipText("Insira a capacidade total do veiculo. Obs: Sem perda.");
		txtCapacid.setText("19842");
		GridBagConstraints gbc_txtCapacid = new GridBagConstraints();
		gbc_txtCapacid.insets = new Insets(0, 0, 5, 5);
		gbc_txtCapacid.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtCapacid.gridx = 4;
		gbc_txtCapacid.gridy = 8;
		getContentPane().add(txtCapacid, gbc_txtCapacid);
		txtCapacid.setColumns(10);

		JLabel lblPerdaPorEstiva = new JLabel("Perda por Estiva :");
		GridBagConstraints gbc_lblPerdaPorEstiva = new GridBagConstraints();
		gbc_lblPerdaPorEstiva.insets = new Insets(0, 0, 5, 5);
		gbc_lblPerdaPorEstiva.gridx = 2;
		gbc_lblPerdaPorEstiva.gridy = 9;
		getContentPane().add(lblPerdaPorEstiva, gbc_lblPerdaPorEstiva);

		txtperdaest = new JNumericField(10,3);
		txtperdaest.setPrecision(3);
		txtperdaest.setAllowNegative(false);
		txtperdaest
				.setToolTipText("Insira o percentual de perda de capacidade do veiculo por quebra de estiva.");
		txtperdaest.setText(".4");
		GridBagConstraints gbc_txtperdaest = new GridBagConstraints();
		gbc_txtperdaest.insets = new Insets(0, 0, 5, 5);
		gbc_txtperdaest.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtperdaest.gridx = 4;
		gbc_txtperdaest.gridy = 9;
		getContentPane().add(txtperdaest, gbc_txtperdaest);
		txtperdaest.setColumns(10);

		JButton btnNvelDeServio = new JButton(
				"Calcular \r\nN\u00EDvel de Servi\u00E7o");
		GridBagConstraints gbc_btnNvelDeServio = new GridBagConstraints();
		gbc_btnNvelDeServio.gridwidth = 5;
		gbc_btnNvelDeServio.fill = GridBagConstraints.BOTH;
		gbc_btnNvelDeServio.insets = new Insets(0, 0, 5, 5);
		gbc_btnNvelDeServio.gridx = 2;
		gbc_btnNvelDeServio.gridy = 10;
		getContentPane().add(btnNvelDeServio, gbc_btnNvelDeServio);

		btnNvelDeServio.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub

				if (validaInfoFormulario(txtMediatc, txtDesvtc, txtMediacarga, txtDesvcarga, txtCapacid, txtHnormal, txtHmax, txtperdaest)==true){
				
				// criando a zona
				zona = new Zona();
				zona.setMediaTC(Double.valueOf(txtMediatc.getText()));
				zona.setDesvPadTC(Double.valueOf(txtDesvtc.getText()));
			
				zona.setMediaCarga(Double.valueOf(txtMediacarga.getText()));
				zona.setDesvPadCarga(Double.valueOf(txtDesvcarga.getText()));

				// passando a zona e os paramentros para calcular os tipos de
				// nivel de servico
				CalcNivelServico ns = new CalcNivelServico(zona, Double
						.valueOf(txtCapacid.getText()), Double
						.valueOf(txtHnormal.getText()), Double.valueOf(txtHmax
						.getText()), Double.valueOf(txtperdaest.getText()));

				// carregando os resultados nos textfields
				txtNscapa.setText(String.valueOf(ns.getNsPorCapacidade()));
				txtNahnorm.setText(String.valueOf(ns.getNsPorTempoNormal()));
				txtNshmx.setText(String.valueOf(ns.getNsPorTempoMaximo()));
				
				}
			}
		});

		JLabel lblResultados = new JLabel("Resultados :");
		GridBagConstraints gbc_lblResultados = new GridBagConstraints();
		gbc_lblResultados.insets = new Insets(0, 0, 5, 5);
		gbc_lblResultados.gridx = 2;
		gbc_lblResultados.gridy = 12;
		getContentPane().add(lblResultados, gbc_lblResultados);

		JLabel lblPorCapacidade = new JLabel("Por Capacidade :");
		GridBagConstraints gbc_lblPorCapacidade = new GridBagConstraints();
		gbc_lblPorCapacidade.anchor = GridBagConstraints.EAST;
		gbc_lblPorCapacidade.gridwidth = 2;
		gbc_lblPorCapacidade.insets = new Insets(0, 0, 5, 5);
		gbc_lblPorCapacidade.gridx = 2;
		gbc_lblPorCapacidade.gridy = 13;
		getContentPane().add(lblPorCapacidade, gbc_lblPorCapacidade);

		txtNscapa = new JTextField();
		
		txtNscapa.setEditable(false);
		txtNscapa.setText("nsCapa");
		GridBagConstraints gbc_txtNscapa = new GridBagConstraints();
		gbc_txtNscapa.gridwidth = 2;
		gbc_txtNscapa.insets = new Insets(0, 0, 5, 5);
		gbc_txtNscapa.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtNscapa.gridx = 4;
		gbc_txtNscapa.gridy = 13;
		getContentPane().add(txtNscapa, gbc_txtNscapa);
		txtNscapa.setColumns(10);

		JLabel lblPorTempo = new JLabel("Por Tempo H Normal :");
		GridBagConstraints gbc_lblPorTempo = new GridBagConstraints();
		gbc_lblPorTempo.anchor = GridBagConstraints.EAST;
		gbc_lblPorTempo.gridwidth = 2;
		gbc_lblPorTempo.insets = new Insets(0, 0, 5, 5);
		gbc_lblPorTempo.gridx = 2;
		gbc_lblPorTempo.gridy = 14;
		getContentPane().add(lblPorTempo, gbc_lblPorTempo);

		txtNahnorm = new JTextField();
		txtNahnorm.setEditable(false);
		txtNahnorm.setText("naHnorm");
		GridBagConstraints gbc_txtNahnorm = new GridBagConstraints();
		gbc_txtNahnorm.gridwidth = 2;
		gbc_txtNahnorm.insets = new Insets(0, 0, 5, 5);
		gbc_txtNahnorm.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtNahnorm.gridx = 4;
		gbc_txtNahnorm.gridy = 14;
		getContentPane().add(txtNahnorm, gbc_txtNahnorm);
		txtNahnorm.setColumns(10);

		JLabel lblPorTempoH = new JLabel("Por Tempo H Max :");
		GridBagConstraints gbc_lblPorTempoH = new GridBagConstraints();
		gbc_lblPorTempoH.gridwidth = 2;
		gbc_lblPorTempoH.insets = new Insets(0, 0, 5, 5);
		gbc_lblPorTempoH.anchor = GridBagConstraints.EAST;
		gbc_lblPorTempoH.gridx = 2;
		gbc_lblPorTempoH.gridy = 15;
		getContentPane().add(lblPorTempoH, gbc_lblPorTempoH);

		txtNshmx = new JTextField();
		txtNshmx.setEditable(false);
		txtNshmx.setText("nsHmx");
		GridBagConstraints gbc_txtNshmx = new GridBagConstraints();
		gbc_txtNshmx.gridwidth = 2;
		gbc_txtNshmx.insets = new Insets(0, 0, 5, 5);
		gbc_txtNshmx.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtNshmx.gridx = 4;
		gbc_txtNshmx.gridy = 15;
		getContentPane().add(txtNshmx, gbc_txtNshmx);
		txtNshmx.setColumns(10);

		JLabel lblNewLabel_3 = new JLabel("");
		GridBagConstraints gbc_lblNewLabel_3 = new GridBagConstraints();
		gbc_lblNewLabel_3.insets = new Insets(0, 0, 0, 5);
		gbc_lblNewLabel_3.gridx = 2;
		gbc_lblNewLabel_3.gridy = 16;
		getContentPane().add(lblNewLabel_3, gbc_lblNewLabel_3);

	}

	public LineString criarUmaLinha(double cX, double cY, double dX, double dY) {

		GeometryFactory gf = new GeometryFactory(); // this is a new line
		Coordinate c1 = new Coordinate(cX, cY);
		Coordinate c2 = new Coordinate(dX, dY);
		Collection<Coordinate> listCord = new ArrayList<Coordinate>();
		listCord.add(c1);
		listCord.add(c2);
		Coordinate[] coords = (Coordinate[]) listCord
				.toArray(new Coordinate[listCord.size()]); //
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
			Distrito novo = (Distrito) iterar.next();
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

	public boolean validaInfoFormulario(
			 JNumericField txtMediatc,
			 JNumericField txtDesvtc,
			 JNumericField txtMediacarga,
			 JNumericField txtDesvcarga,
			 JNumericField txtCapacid,
			 JNumericField txtHnormal,
			 JNumericField txtHmax,
			 JNumericField txtperdaest
		) {
		int qtd = 0;
		boolean resposta = false;
		if (txtMediatc.getText().isEmpty() == false) {
			qtd++;
		}
		if (txtDesvtc.getText().isEmpty() == false) {
			qtd++;
		}
		if (txtMediacarga.getText().isEmpty() == false) {
			qtd++;
		}
		if (txtDesvcarga.getText().isEmpty() == false) {
			qtd++;
		}
		if (txtCapacid.getText().isEmpty() == false) {
			qtd++;
		}
		if (txtHnormal.getText().isEmpty() == false) {
			qtd++;
		}
		if (txtHmax.getText().isEmpty() == false) {
			qtd++;
		}
		if (txtperdaest.getText().isEmpty() == false) {
			qtd++;
		}
		if (qtd < 8) {
			JOptionPane
					.showMessageDialog(
							null, 
							"Não foi possível executar o cálculo. Parâmetros pendentes de preenchimento.");
			resposta = false;
		} else {
			resposta = true;
		}

		return resposta;
	}

}


