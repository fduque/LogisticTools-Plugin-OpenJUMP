package br.puc.rio.zona;

import java.util.Iterator;
import java.util.List;

public class SubZona {

	private int idSubZona;
	private Zona zonaPrincipal;
	private List<Distrito> listaDistritosSubZona;
	private String informacoesMetodoGeracaoSubZona;
	private double areaAdmissivelMeta;
	private Distrito distritoDeposito;

	// variaveis carregadas pela classe
	private double areaTotalSubzona;
	private int qtdDistritosSubZona;

	/*
	 * Essa classe representa uma subzona que pode ser criada por algum método de fomração de subzonas.
	 * Ela tem métodos sobre informações da subzona, como: distritos dentro da zona, descrição do método que construiu a zona, etc.
	 * 
	 */	
	
	
	/**
	 * @param idSubZona
	 * @param zonaPrincipal
	 * @param listaDistritosSubZona
	 * @param informacoesMetodoGeracaoSubZona
	 * @param areaAdmissivelMeta
	 * @param distritoDeposito
	 */
	public SubZona(int idSubZona, Zona zonaPrincipal,
			List<Distrito> listaDistritosSubZona,
			String informacoesMetodoGeracaoSubZona, double areaAdmissivelMeta,
			Distrito distritoDeposito) {
		super();
		this.idSubZona = idSubZona;
		this.zonaPrincipal = zonaPrincipal;
		this.listaDistritosSubZona = listaDistritosSubZona;
		this.informacoesMetodoGeracaoSubZona = informacoesMetodoGeracaoSubZona;
		this.areaAdmissivelMeta = areaAdmissivelMeta;
		this.distritoDeposito = distritoDeposito;

		// carregando outras variaveis
		setQtdDistritosSubZona();
		setAreaTotalSubzona();
	}

	public Distrito getDistritoDeposito() {
		return distritoDeposito;
	}

	public double getAreaAdmissivelMeta() {
		return areaAdmissivelMeta;
	}

	public int getIdSubZona() {
		return idSubZona;
	}

	public String getInformacoesMetodoGeracaoSubZona() {
		return informacoesMetodoGeracaoSubZona;
	}

	public Zona getZonaPrincipal() {
		return zonaPrincipal;
	}

	public List<Distrito> getListaDistritosSubZona() {
		return listaDistritosSubZona;
	}

	public double getAreaTotalSubzona() {
		return areaTotalSubzona;
	}

	public void setAreaTotalSubzona() {
		double areatotal = 0;
		Iterator<Distrito> iterar = listaDistritosSubZona.iterator();
		while (iterar.hasNext()) {
			Distrito d = (Distrito) iterar.next();
			areatotal = areatotal + d.getAreaDistrito();
		}
		this.areaTotalSubzona = areatotal;
	}

	public void setQtdDistritosSubZona() {
		this.qtdDistritosSubZona = listaDistritosSubZona.size();
	}

	public int getQtdDistritosSubZona() {
		return qtdDistritosSubZona;
	}

	@Override
	public String toString() {
		return "SubZona [idSubZona=" + idSubZona + ", zonaPrincipal="
				+ zonaPrincipal.getIdZona() + ", qtd listaDistritosSubZona="
				+ listaDistritosSubZona.size() + ", areaTotalSubzona="
				+ areaTotalSubzona + "]";
	}
}
