package br.puc.rio.utilities;


public class Matriz {

	Pontos i;
	Pontos j;
	double distanciaIJ;
	double ganhoIJ;
	String nomeCluster;
	
	/*
	 * Essa classe tem o objetivo de criar objetos que simulam uma posição dentro de uma matriz.
	 */	
	
	public double getGanhoIJ() {
		return ganhoIJ;
	}
	public void setGanhoIJ(double ganhoIJ) {
		this.ganhoIJ = ganhoIJ;
	}
	public String getNomeCluster() {
		return nomeCluster;
	}
	public void setNomeCluster(String nomeCluster) {
		this.nomeCluster = nomeCluster;
	}
	public Pontos getI() {
		return i;
	}
	public void setI(Pontos i) {
		this.i = i;
	}
	public Pontos getJ() {
		return j;
	}
	public void setJ(Pontos j) {
		this.j = j;
	}
	public double getDistanciaIJ() {
		return distanciaIJ;
	}
	public void setDistanciaIJ(double distanciaIJ) {
		this.distanciaIJ = distanciaIJ;
	}
	@Override
	public String toString() {
		return "Matriz [i=" + i.getNomePonto() + ", j=" + j.getNomePonto() + ", distanciaIJ=" + distanciaIJ
				+ "]";
	}
	
}
