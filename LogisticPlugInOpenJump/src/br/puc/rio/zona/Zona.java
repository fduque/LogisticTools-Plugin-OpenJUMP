package br.puc.rio.zona;

import java.util.List;

import com.vividsolutions.jts.geom.Geometry;

/**
 * @author Developer
 * F�bio Trindade Duque Estrada Regis / duqueregis@gmail.com / PUC-RIO
 *
 */
public class Zona {

	// variaveis que serao carregadas pelo usuario
	private String idZona; // usuario dara um nome a zona criada
	private double mediaTC;
	private double desvPadTC;
	private double mediatparada;
	private double desvPadtparada;
	private double mediaN;
	private double desvPadN;
	private double lambda;
	private double mediaCarga;
	private double desvPadCarga;
	private double veloMedia;
	// variaveis carregadas pela classe
	private List<Distrito> listaDistritosZona;
	private double areaZona; // e o somatorio de todas as areas dos distritos nazona
	private Geometry geometryZona; // e o agrupamento de todas as geometrias dosdistritos da zona variaveis calculadas a partir de outras variaveis
	private double varCarga;
	private double vartparada;
	private double varTC;

	
	/*
	 * Essa classe representa uma zona e tem m�todos sobre informa��es como: tempo de ciclo m�dio na zona, velocidade m�dia na zona, densidade de pontos de atendimento por unidade de �rea, etc.
	 * Ela � usada como objeto base para os c�lculos de n�vel de servi�o, �rea admiss�vel e forma��o de subzonas.
	 */	
	
	
	public List<Distrito> getListaDistritosZona() {
		return listaDistritosZona;
	}

	public void setListaDistritosZona(List<Distrito> listaDistritosZona) {
		this.listaDistritosZona = listaDistritosZona;
	}

	public String getIdZona() {
		return idZona;
	}

	public void setIdZona(String idZona) {
		this.idZona = idZona;
	}

	public double getAreaZona() {
		return areaZona;
	}

	public void setAreaZona(double areaZona) {
		this.areaZona = areaZona;
	}

	public Geometry getGeometryZona() {
		return geometryZona;
	}

	public void setGeometryZona(Geometry geometryZona) {
		this.geometryZona = geometryZona;
	}

	public double getMediaTC() {
		return mediaTC;
	}

	public void setMediaTC(double mediaTC) {
		this.mediaTC = mediaTC;
	}

	public double getVarTC() {
		return varTC;
	}

	public double getDesvPadTC() {
		return desvPadTC;
	}

	public void setDesvPadTC(double desvPadTC) {
		this.desvPadTC = desvPadTC;

		// carregando a variancia a partir do desvpad
		this.varTC = Math.pow(desvPadTC, 2);
	}

	public double getMediatparada() {
		return mediatparada;
	}

	public void setMediatparada(double mediatparada) {
		this.mediatparada = mediatparada;
	}

	public double getVartparada() {
		return vartparada;
	}

	public double getDesvPadtparada() {
		return desvPadtparada;
	}

	public void setDesvPadtparada(double desvPadtparada) {
		this.desvPadtparada = desvPadtparada;
		// carregando a variancia a partir do desvpad
		this.vartparada = Math.pow(desvPadtparada, 2);
	}

	public double getMediaN() {
		return mediaN;
	}

	public void setMediaN(double mediaN) {
		this.mediaN = mediaN;
	}

	public double getLambda() {
		return lambda;
	}

	public void setLambda(double lambda) {
		this.lambda = lambda;
	}

	public double getMediaCarga() {
		return mediaCarga;
	}

	public void setMediaCarga(double mediaCarga) {
		this.mediaCarga = mediaCarga;
	}

	public double getVarCarga() {
		return varCarga;
	}

	public double getDesvPadCarga() {
		return desvPadCarga;
	}

	public void setDesvPadCarga(double desvPadCarga) {
		this.desvPadCarga = desvPadCarga;
		// carregando a variancia a partir do desvpad
		this.varCarga = Math.pow(desvPadCarga, 2);
	}

	public double getVeloMedia() {
		return veloMedia;
	}

	public void setVeloMedia(double veloMedia) {
		this.veloMedia = veloMedia;
	}

	@Override
	public String toString() {
		return "Zona [idZona=" + idZona + ", areaZona=" + areaZona
				+ ", geometryZona=" + geometryZona.getGeometryType()
				+ ", mediaTC=" + mediaTC + ", varTC=" + varTC + ", desvPadTC="
				+ desvPadTC + ", mediatparada=" + mediatparada
				+ ", vartparada=" + vartparada + ", desvPadtparada="
				+ desvPadtparada + ", mediaN=" + mediaN + ", lambda=" + lambda
				+ ", mediaCarga=" + mediaCarga + ", varCarga=" + varCarga
				+ ", desvPadCarga=" + desvPadCarga + ", veloMedia=" + veloMedia
				+ "]";
	}

	public double getDesvPadN() {
		return desvPadN;
	}

	public void setDesvPadN(double desvPadN) {
		this.desvPadN = desvPadN;
	}

}
