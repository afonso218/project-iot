package pt.iscte.poo.instalacao.aparelhos.extras;

import java.util.ArrayList;
import java.util.List;

public class Programa {

	private String id;
	private int posicao;
	private List<Ciclo> ciclos;
	
	public Programa(String id) {
		this.id=id;
		this.posicao = -1;
		this.ciclos=new ArrayList<Ciclo>();
	}
	
	public String getNome() {
		return id;
	}
	
	public Ciclo next(){
		posicao++;
		if(posicao < ciclos.size()){
			return ciclos.get(posicao);
		}else{
			posicao = -1;
			return null;
		}
	}
	
	public void addCiclo(long duracao, double d){
		ciclos.add(new Ciclo(duracao, d));
	}
	
	@Override
	public String toString() {
		return id + " <" + ciclos.size() + "> " + ciclos;
	}

}
