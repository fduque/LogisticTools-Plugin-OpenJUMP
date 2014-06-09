package br.puc.rio.zona;

import com.vividsolutions.jts.geom.Geometry;

public class Distrito {

	// variaveis carregadas via arquivo-camada de distritos
	private String idDistrito; // obs: o IDDISTRITO tem que conter na camada q sera carregada com as informacoes geograficas
	private boolean isAlocadoSubZona;
	private double mediaTC;
	private double desvPadTC;
	private double mediatparada;
	private double desvPadtparada;
	private double mediaCarga;
	private double desvPadCarga;
	private double veloMedia;
	// variaveis carregadas pela propria classe - ou seja, set customizado
	private double areaDistrito;
	private Geometry geometryDistrito;
	// essas variaveis so serao carregadas em alguns casos
	private double varCarga;
	private double vartparada;
	private double varTC;

	private double distanciaAteDistritoDeposito;

	
	/*
	 * Essa classe representa um distrito que faz parte de uma zona qualquer.
	 * Ela tem métodos sobre informações do distrito, como: velocidade média, geometria do distrito, área do distrito, etc.
	 * 
	 */	
	
	public double getDistanciaAteDistritoDeposito() {
		return distanciaAteDistritoDeposito;
	}

	public void setDistanciaAteDistritoDeposito(
			double distanciaAteDistritoDeposito) {
		this.distanciaAteDistritoDeposito = distanciaAteDistritoDeposito;
	}

	public String getIdDistrito() {
		return idDistrito;
	}

	public void setIdDistrito(String idDistrito) {
		this.idDistrito = idDistrito;
	}

	public double getMediaTC() {
		return mediaTC;
	}

	public boolean isAlocadoSubZona() {
		return isAlocadoSubZona;
	}

	public void setAlocadoSubZona(boolean isAlocadoSubZona) {
		this.isAlocadoSubZona = isAlocadoSubZona;
	}

	public void setMediaTC(double mediaTC) {
		this.mediaTC = mediaTC;
	}

	public double getDesvPadTC() {
		return desvPadTC;
	}

	public void setDesvPadTC(double desvPadTC) {
		this.desvPadTC = desvPadTC;
	}

	public double getMediatparada() {
		return mediatparada;
	}

	public void setMediatparada(double mediatparada) {
		this.mediatparada = mediatparada;
	}

	public double getDesvPadtparada() {
		return desvPadtparada;
	}

	public void setDesvPadtparada(double desvPadtparada) {
		this.desvPadtparada = desvPadtparada;
	}

	public double getMediaCarga() {
		return mediaCarga;
	}

	public void setMediaCarga(double mediaCarga) {
		this.mediaCarga = mediaCarga;
	}

	public double getDesvPadCarga() {
		return desvPadCarga;
	}

	public void setDesvPadCarga(double desvPadCarga) {
		this.desvPadCarga = desvPadCarga;
	}

	public double getVeloMedia() {
		return veloMedia;
	}

	public void setVeloMedia(double veloMedia) {
		this.veloMedia = veloMedia;
	}

	public double getAreaDistrito() {
		return areaDistrito;
	}

	public Geometry getGeometryDistrito() {
		return geometryDistrito;
	}

	public void setGeometryDistrito(Geometry geometryDistrito) {
		this.geometryDistrito = geometryDistrito;

		areaDistrito = geometryDistrito.getArea(); // definindo a area do
													// distrito atraves do
													// objeto geometry passado

	}

	public double getVarCarga() {
		return varCarga;
	}

	public void setVarCarga(double varCarga) {
		this.varCarga = varCarga;
	}

	public double getVartparada() {
		return vartparada;
	}

	public void setVartparada(double vartparada) {
		this.vartparada = vartparada;
	}

	public double getVarTC() {
		return varTC;
	}

	public void setVarTC(double varTC) {
		this.varTC = varTC;
	}

	@Override
	public String toString() {
		return "Distrito [idDistrito=" + idDistrito + ", isAlocadoSubZona="
				+ isAlocadoSubZona + ", mediaTC=" + mediaTC + ", desvPadTC="
				+ desvPadTC + ", mediatparada=" + mediatparada
				+ ", desvPadtparada=" + desvPadtparada + ", mediaCarga="
				+ mediaCarga + ", desvPadCarga=" + desvPadCarga
				+ ", veloMedia=" + veloMedia + ", areaDistrito=" + areaDistrito
				+ ", geometryDistrito=" + geometryDistrito + ", varCarga="
				+ varCarga + ", vartparada=" + vartparada + ", varTC=" + varTC
				+ ", distanciaAteDistritoDeposito="
				+ distanciaAteDistritoDeposito + "]";
	}

}
