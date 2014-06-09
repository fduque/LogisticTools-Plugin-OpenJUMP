package br.puc.rio.zona;

import java.util.Comparator;

//deixa a lista em ordem crescente


/*
Essa classe implementa um método que ordena uma lista de distrito pelo critério de distância do distrito até o depósito em ordem crescente.
*/	

public class DistanciaDepositoComparator implements Comparator<Distrito> {  
    public int compare(Distrito p1,Distrito p2) {  
        return p1.getDistanciaAteDistritoDeposito() < p2.getDistanciaAteDistritoDeposito() ? -1 : (p1.getDistanciaAteDistritoDeposito() > p2.getDistanciaAteDistritoDeposito()? +1 : 0);  
   }
}  

