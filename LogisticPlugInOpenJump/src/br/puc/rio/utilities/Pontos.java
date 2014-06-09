package br.puc.rio.utilities;

import com.vividsolutions.jts.geom.Coordinate;

public class Pontos {

	private String nomePonto;
	private Coordinate coordPonto;
	private double eixoXponto;
	private double eixoYponto;
	private double quantidadePonto;
	private double tempoPonto;
	private String clusterPonto;
	private double valorCargaPonto;
	private String observacao;
	
	/*
	 * Essa classe tem o objetivo de criar um ponto, que pode representar, clientes, depósitos, fornecedores etc.
	 * Ela possui atributos que geralmente são comuns aos exemplos citados anteriormente como: tempo de atendimento, quantidade de carga para coleta ou entrega, coordenadas de localização, etc.
	 */	
	

	public double getValorCargaPonto() {
		return valorCargaPonto;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public void setValorCargaPonto(double valorCargaPonto) {
		this.valorCargaPonto = valorCargaPonto;
	}

	public int getClusterPontoInt() {
		int codigo = Integer.parseInt(this.clusterPonto);
		return codigo;
	}

	public double getTempoPonto() {
		return tempoPonto;
	}

	public void setTempoPonto(double tempoPonto) {
		this.tempoPonto = tempoPonto;
	}

	public String getClusterPonto() {
		return clusterPonto;
	}

	public void setClusterPonto(String clusterPonto) {
		this.clusterPonto = clusterPonto;
	}

	public String getNomePonto() {
		return nomePonto;
	}

	public void setNomePonto(String nomePonto) {
		this.nomePonto = nomePonto;
	}

	/*
	 * @Override public String toString() { return "Pontos [nomePonto=" +
	 * nomePonto + ", coordPonto=" + coordPonto + ", eixoXponto=" + eixoXponto +
	 * ", eixoYponto=" + eixoYponto + ", quantidadePonto=" + quantidadePonto +
	 * ", tempoPonto=" + tempoPonto + ", clusterPonto=" + clusterPonto +
	 * ", indexArrependimento=" + indexArrependimento + "]"; }
	 */
	public String toString() {
		return "Pontos [nomePonto=" + nomePonto + " clusterPonto= "
				+ clusterPonto + " ]";
	}

	public Coordinate getCoordPonto() {
		return coordPonto;
	}

	public void setCoordPonto(Coordinate coordPonto) {
		this.coordPonto = coordPonto;
	}

	public double getEixoXponto() {
		return eixoXponto;
	}

	public void setEixoXponto(double eixoXponto) {
		this.eixoXponto = eixoXponto;
	}

	public double getEixoYponto() {
		return eixoYponto;
	}

	public void setEixoYponto(double eixoYponto) {
		this.eixoYponto = eixoYponto;
	}

	public double getQuantidadePonto() {
		return quantidadePonto;
	}

	public void setQuantidadePonto(double quantidadePonto) {
		this.quantidadePonto = quantidadePonto;
	}

}
