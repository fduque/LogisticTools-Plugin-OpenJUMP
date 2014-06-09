package br.puc.rio.clarkWright;

import java.util.Comparator;

//deixa a lista em ordem crescente

/*
Essa classe implementa um método para ordenar os valores de ganho em ordem crscente.
*/	

public class GanhoComparator implements Comparator<Ganho> {
	public int compare(Ganho p1, Ganho p2) {
		return p1.getGanho() < p2.getGanho() ? -1 : (p1.getGanho() > p2
				.getGanho() ? +1 : 0);
	}
}
