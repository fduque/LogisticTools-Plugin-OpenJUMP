package br.puc.rio.clarkWright;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.puc.rio.utilities.GeoUtilidades;
import br.puc.rio.utilities.Pontos;

public class Roteiro {

	String nome;
	HashMap<Integer, Pontos> listaPontosRoteiro = new HashMap<>();
	Pontos pontoOrigem;
	double velocidadeKmPorHora;
	
	/*
	Essa classe representa um roteiro construído pelo método Clarke e Wright.Ela possui métodos de informações sobre o roteiro como: peso total, tempo total, etc 
	e métodos relacionados a construção e edição do roteiro.
	*/	

	public double calctempoTotalDeParada() {
		double tempoTotal = 0;
		for (Map.Entry<Integer, Pontos> entry : listaPontosRoteiro.entrySet()) {
			Pontos ponto = entry.getValue(); // aqui me retorna o objeto tipo
												// ponto dentro do hashmap
			tempoTotal = tempoTotal + ponto.getTempoPonto();
		}
		return tempoTotal;
	}

	public double calctempoTotalEmMovimento() {
		
		GeoUtilidades g = new GeoUtilidades();
		double tempoTotal = 0;
		double qtdArcos = listaPontosRoteiro.size() - 1;
		for (int i = 0; i < qtdArcos; i++) {
			Pontos pOrigem = listaPontosRoteiro.get(i);
			Pontos pDestino = listaPontosRoteiro.get(i + 1);
			double distanciaArco = g.distanciaEntrePontos(pOrigem,
					pDestino);
			double tempoArco = distanciaArco / velocidadeKmPorHora;
			tempoTotal = tempoTotal + tempoArco;
		}
		
		return tempoTotal;
	}

	
	public double calcDistanciaPercorridaEntrePontos() {
		
		GeoUtilidades g = new GeoUtilidades();
		double distTotalEntrePontos = 0;
		double qtdArcos = listaPontosRoteiro.size() - 1;
		for (int i = 0; i < qtdArcos; i++) {
			Pontos pOrigem = listaPontosRoteiro.get(i);
			Pontos pDestino = listaPontosRoteiro.get(i + 1);
			double distanciaArco = g.distanciaEntrePontos(pOrigem,
					pDestino);
			distTotalEntrePontos = distTotalEntrePontos + distanciaArco;
		}
		
		return distTotalEntrePontos;
	}
	
	public double calcDistanciaChegadaEretornoZona() {
		
		Pontos primeiroPontodaZona = listaPontosRoteiro.get(0);
		Pontos ultimoPontodaZona = listaPontosRoteiro.get(listaPontosRoteiro
				.size() - 1);
		GeoUtilidades g = new GeoUtilidades();
		double distanciaIda = g.distanciaEntrePontos(pontoOrigem,
				primeiroPontodaZona);
		double distanciaVolta = g.distanciaEntrePontos(
				ultimoPontodaZona, pontoOrigem);
		
		return (distanciaIda + distanciaVolta);
	}
	
	public double calcDistTotalRoteiro() {
		return calcDistanciaChegadaEretornoZona()+calcDistanciaPercorridaEntrePontos();
	}
	
	public List<Pontos> getPontosEmFormatoList () {
		List<Pontos> list = new ArrayList<>();
		for (Map.Entry<Integer, Pontos> entry : listaPontosRoteiro.entrySet()) {
			Pontos ponto = entry.getValue(); 
			list.add(ponto);
	}
	
	return list;	
	}
	
	
	public double calcPesoTotal() {
		double pesoTotal = 0;
		for (Map.Entry<Integer, Pontos> entry : listaPontosRoteiro.entrySet()) {
			Pontos ponto = entry.getValue(); // aqui me retorna o objeto tipo
												// ponto dentro do hashmap
			pesoTotal = pesoTotal + ponto.getQuantidadePonto();
		}
		return pesoTotal;
	}

	public double calcValorTotal() {
		double valorTotal = 0;
		for (Map.Entry<Integer, Pontos> entry : listaPontosRoteiro.entrySet()) {
			Pontos ponto = entry.getValue(); // aqui me retorna o objeto tipo
												// ponto dentro do hashmap
			valorTotal = valorTotal + ponto.getValorCargaPonto();
		}
		return valorTotal;
	}

	public double calcTempoChegadaEretornoZona() {
		
		Pontos primeiroPontodaZona = listaPontosRoteiro.get(0);
		Pontos ultimoPontodaZona = listaPontosRoteiro.get(listaPontosRoteiro
				.size() - 1);
		GeoUtilidades g = new GeoUtilidades();
		double distanciaIda = g.distanciaEntrePontos(pontoOrigem,
				primeiroPontodaZona);
		double distanciaVolta = g.distanciaEntrePontos(
				ultimoPontodaZona, pontoOrigem);
		
		return (distanciaIda + distanciaVolta) / velocidadeKmPorHora;
	}

	public double calcTempoTotalRoteiro() {
		return calctempoTotalDeParada() + calctempoTotalEmMovimento()
				+ calcTempoChegadaEretornoZona();
	}

	public void addPontoRoteiro(Pontos ponto) {
		if (listaPontosRoteiro.size() != 0) {
			int posicaoDoNovoPontoNaLista = listaPontosRoteiro.size();
			listaPontosRoteiro.put(posicaoDoNovoPontoNaLista, ponto);
		} else {
			listaPontosRoteiro.put(0, ponto);
		}
	}

	public boolean verificarPontoNoRoteiro(Pontos ponto) {
		boolean result = false;
		if (listaPontosRoteiro.containsValue(ponto) == true) {
			result = true;
		}
		return result;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Pontos getPontoOrigem() {
		return pontoOrigem;
	}

	public void setPontoOrigem(Pontos pontoOrigem) {
		this.pontoOrigem = pontoOrigem;
	}

	public HashMap<Integer, Pontos> getListaPontosRoteiro() {
		return listaPontosRoteiro;
	}

	public void setListaPontosRoteiro(
			HashMap<Integer, Pontos> listaPontosRoteiro) {
		this.listaPontosRoteiro = listaPontosRoteiro;
	}

	public double getVelocidadeKmPorHora() {
		return velocidadeKmPorHora;
	}

	public void setVelocidadeKmPorHora(double velocidadeKmPorHora) {
		this.velocidadeKmPorHora = velocidadeKmPorHora;
	}

	@Override
	public String toString() {
		return "Roteiro [Nome =" + nome + ", Quantidade de Pontos ="
				+ listaPontosRoteiro.size() + ", Pontos na lista = "
				+ getNomePontosNaLista() + ", Tempo total Roteiro ="
				+ calcTempoTotalRoteiro() + ", Carga total Roteiro = "
				+ calcPesoTotal() + ", Valor total Roteiro = "
				+ calcValorTotal() + ", Origem do Roteiro ="
				+ pontoOrigem.getNomePonto() + ", Velocidade no Roteiro ="
				+ velocidadeKmPorHora + "]";
	}

	public String getNomePontosNaLista() {
		String listaNomesPontos = null;
		for (Map.Entry<Integer, Pontos> entry : listaPontosRoteiro.entrySet()) {
			Pontos ponto = entry.getValue(); // aqui me retorna o objeto tipo
												// ponto dentro do hashmap
			listaNomesPontos = listaNomesPontos + ";" + ponto.getNomePonto();
		}
		return listaNomesPontos;
	}

	public boolean isPointBeginOrEnd(Pontos ponto) {
		boolean result = false;
		int qtdPontosNoRoteiro = listaPontosRoteiro.size() - 1;
		Pontos pontoUltimoIndice = listaPontosRoteiro.get(qtdPontosNoRoteiro);
		Pontos pontoPrimeiroIndice = listaPontosRoteiro.get(0);

		if (pontoPrimeiroIndice == ponto) {
			result = true;
		}
		if (pontoUltimoIndice == ponto) {
			result = true;
		}
		return result;
	}

	public void addPontoRoteiroNaSequencia(Roteiro roteiro,
			Pontos pontoNoRoteiro, Pontos pontoAentrarNoRoteiro) {
		Pontos primeiroPontoRoteiro1 = roteiro.getListaPontosRoteiro().get(0);
		Pontos ultimoPontoRoteiro1 = roteiro.getListaPontosRoteiro().get(
				roteiro.getListaPontosRoteiro().size() - 1);

		int posicaoPontoLista = 0;
		if (primeiroPontoRoteiro1 == pontoNoRoteiro) {
			listaPontosRoteiro.put(posicaoPontoLista, pontoAentrarNoRoteiro);
			posicaoPontoLista++;
			for (Map.Entry<Integer, Pontos> entry : roteiro
					.getListaPontosRoteiro().entrySet()) {
				Pontos ponto = entry.getValue();
				listaPontosRoteiro.put(posicaoPontoLista, ponto);
				posicaoPontoLista++;
			}
		} else {
			if (ultimoPontoRoteiro1 == pontoNoRoteiro) {
				for (Map.Entry<Integer, Pontos> entry : roteiro
						.getListaPontosRoteiro().entrySet()) {
					Pontos ponto = entry.getValue();
					listaPontosRoteiro.put(posicaoPontoLista, ponto);
					posicaoPontoLista++;
				}
				listaPontosRoteiro
						.put(posicaoPontoLista, pontoAentrarNoRoteiro);
			}
		}
	}

}
