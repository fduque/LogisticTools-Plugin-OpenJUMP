package br.puc.rio.opt2;

import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;

import br.puc.rio.clarkWright.Roteiro;
import br.puc.rio.utilities.Matematica;
import br.puc.rio.utilities.Pontos;

public class Metodo2opt  {

	
	public Roteiro aplicar2optRoteiro (Roteiro roteiroOriginal,int numMaxDeMelhorias) {
		System.out.println("Aplicando 2opt no Roteiro..." + roteiroOriginal.getNome());
		System.out.println("Qtd Limite de Melhorias definida:" + numMaxDeMelhorias);
		Roteiro roteiroProcessado  = new Roteiro();
		roteiroProcessado.setNome(roteiroOriginal.getNome());
		roteiroProcessado.setVelocidadeKmPorHora(roteiroOriginal.getVelocidadeKmPorHora());
		roteiroProcessado.setPontoOrigem(roteiroOriginal.getPontoOrigem());
		roteiroProcessado.setListaPontosRoteiro(roteiroOriginal.getListaPontosRoteiro());
		
		double distTotalParaPercorrerRoteiroOriginal = roteiroOriginal.calcDistTotalRoteiro();
		System.out.println("Dist Total do roteiro original=" + distTotalParaPercorrerRoteiroOriginal);
		double distTotalAtualRoteiro = distTotalParaPercorrerRoteiroOriginal;
		//colocando o ponto de deposito como o ponto zero na lista de pontos no roteiro
		HashMap<Integer, Pontos> listaPontosRoteiro = new HashMap<>();
		listaPontosRoteiro.putAll(roteiroOriginal.getListaPontosRoteiro());
		
		int qtdElementosAcombinar = listaPontosRoteiro.size()-1;//supondo que isso seja uma lista...deve-se sempre descontar 1 pois na lista existe a posicao zero
		int qtdPosicoesAseremTrocadas=2;
		int contadorMelhoria=0;
		int contadorRepeticoes=0;
		int qtdCombinacoesSemRepeticao = Matematica.calcQtdCombinacoes(qtdElementosAcombinar+1,qtdPosicoesAseremTrocadas);
		boolean exitTotal = false;
		//os loops abaixo geram todas as combinacoes 2 a 2 sem repeticao
		loop1:
		for (int i =0;i<=numMaxDeMelhorias;i++){
			System.out.println("Rodada atual: " + i);
			if(exitTotal==true){
				break;
			}
			
			for (int posicaoIroteiro = qtdElementosAcombinar; posicaoIroteiro > 0; posicaoIroteiro--) {
				loop:	//esse loop delimita o perimetro do break
					for (int posicaoJroteiro = posicaoIroteiro - 1; posicaoJroteiro > -1; posicaoJroteiro--) {	
						
						System.out.println(posicaoIroteiro+","+posicaoJroteiro);
								
								contadorRepeticoes++;
						
								HashMap<Integer, Pontos>  listaComPontosTrocados = trocarPosicaoPontosNumRoteiro(posicaoIroteiro, posicaoJroteiro, roteiroProcessado.getListaPontosRoteiro());
								Roteiro roteiroTeste = new Roteiro();
								roteiroTeste.setPontoOrigem(roteiroOriginal.getPontoOrigem());
								roteiroTeste.setListaPontosRoteiro(listaComPontosTrocados);
									
								if (roteiroTeste.calcDistTotalRoteiro()<distTotalAtualRoteiro){
										System.out.println("Troca 2opt realizada com sucesso!");
										System.out.println("dist total roteiro antigo=" + distTotalAtualRoteiro);
										distTotalAtualRoteiro=roteiroTeste.calcDistTotalRoteiro();
										roteiroProcessado.setListaPontosRoteiro(listaComPontosTrocados);	
										System.out.println("dist total roteiro novo=" + roteiroProcessado.calcDistTotalRoteiro());
										contadorMelhoria++;
										contadorRepeticoes = 0;
										posicaoIroteiro=qtdElementosAcombinar+1;//recomecando a combinacao para o novo roteiro
										if (contadorMelhoria==numMaxDeMelhorias){
											System.out.println("Limite de melhorias atingido!");
											exitTotal=true;
											break loop1;
										}
										System.out.println("Testando combinacoes novo roteiro...");
										break loop;	
									}
								if (contadorRepeticoes==qtdCombinacoesSemRepeticao){
									System.out.println("Limite de Combinacoes Possiveis Atingido!");
									exitTotal=true;
									break loop1;
								}
									
							
			}
		}
		}
		System.out.println("PosicaoDeCombinacaoParada:" + contadorRepeticoes + " QuantidadeMaxCombinacao:" + qtdCombinacoesSemRepeticao);
		System.out.println("QtdTotalDeMelhorias=" + contadorMelhoria);
		
		JOptionPane.showMessageDialog(null, contadorMelhoria + " melhoria(s) 2-Opt realizada(s) no Roteiro: " + roteiroProcessado.getNome() + ".");
		System.out.println("Roteirooriginal=" + roteiroOriginal.toString());
		System.out.println("Roteiroprocessado=" + roteiroProcessado.toString());
		return roteiroProcessado;
	}
	
	public HashMap<Integer, Pontos> trocarPosicaoPontosNumRoteiro (int indicePosicao1,int indicePosicao2,HashMap<Integer, Pontos> lista){
		System.out.println("ponto1 2opt=" + indicePosicao1 );
		System.out.println("ponto2 2opt=" + indicePosicao2 );
		System.out.println("tamanho da lista 2opt=" + lista.size() );
		System.out.println("Lista antes da troca=" + montarSequenciaPontos(lista));
		Pontos ponto1 = lista.get(indicePosicao1);
		Pontos ponto2 = lista.get(indicePosicao2);
		
		HashMap<Integer, Pontos> listaProcessada = new HashMap<>();
		listaProcessada.putAll(lista);
		
		listaProcessada.remove(indicePosicao1);
		listaProcessada.put(indicePosicao1, ponto2);
	
		listaProcessada.remove(indicePosicao2);
		listaProcessada.put(indicePosicao2, ponto1);
		System.out.println("Lista depois da troca=" + montarSequenciaPontos(listaProcessada));
		
		return listaProcessada;
	
	}
	
	public String montarSequenciaPontos (HashMap<Integer,Pontos> listPontos){
		String seq = "";
		for (Map.Entry<Integer, Pontos> entry : listPontos.entrySet()) {
			Pontos p = entry.getValue(); // aqui me ret
			seq = seq + "," + p.getNomePonto();
		}
		return seq;
	}
	 
}
