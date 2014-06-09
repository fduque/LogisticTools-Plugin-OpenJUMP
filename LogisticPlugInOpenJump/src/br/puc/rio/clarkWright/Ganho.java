package br.puc.rio.clarkWright;

import br.puc.rio.utilities.Pontos;

public class Ganho {

	Pontos deposito;
	Pontos pontoI;
	Pontos pontoJ;
	double ganho;
	
	/*
	Essa classe é usada no procedimento de Clarke e Wright e contém as informações sobre cada ganho calculado pelo método como: pontos considerados no ganho, valor do ganho e ponto de depósito.
	*/	

	public Pontos getDeposito() {
		return deposito;
	}

	public void setDeposito(Pontos deposito) {
		this.deposito = deposito;
	}

	public Pontos getPontoI() {
		return pontoI;
	}

	public void setPontoI(Pontos pontoI) {
		this.pontoI = pontoI;
	}

	public Pontos getPontoJ() {
		return pontoJ;
	}

	public void setPontoJ(Pontos pontoJ) {
		this.pontoJ = pontoJ;
	}

	public double getGanho() {
		return ganho;
	}

	public void setGanho(double ganho) {
		this.ganho = ganho;
	}

	@Override
	public String toString() {
		return "Ganho [deposito=" + deposito + ", pontoI=" + pontoI
				+ ", pontoJ=" + pontoJ + ", ganho=" + ganho + "]";
	}

}
