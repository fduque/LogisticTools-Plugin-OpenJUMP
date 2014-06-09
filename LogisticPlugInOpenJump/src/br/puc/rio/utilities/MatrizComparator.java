package br.puc.rio.utilities;

import java.util.Comparator;


/*
 * Essa classe tem o objetivo de ordenar objetos do tipo Matriz em ordem crescente pelo valor de distância.
 */	

public class MatrizComparator implements Comparator<Matriz> {  
	     public int compare(Matriz p1,Matriz p2) {  
	          return p1.getDistanciaIJ() < p2.getDistanciaIJ() ? -1 : (p1.getDistanciaIJ() > p2.getDistanciaIJ()? +1 : 0);  
	     }  
	}  
	  

