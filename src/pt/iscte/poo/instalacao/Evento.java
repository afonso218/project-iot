package pt.iscte.poo.instalacao;

import pt.iscte.poo.instalacao.aparelhos.AparelhoPotenciaVariavel;
import pt.iscte.poo.instalacao.aparelhos.MaqLavarRoupa;


public class Evento implements Comparable<Evento>{

	private Aparelho aparelho;
	private String nome;
	private long tempo;
	private double valor;
	private String programa;
	
	public Evento(String nome, Aparelho aparelho, long tempo) {
		this.aparelho = aparelho;
		this.nome = nome;
		this.tempo=tempo;
	}
	
	public Evento(String nome, Aparelho aparelho, double valor, long tempo) {
		this.aparelho = aparelho;
		this.nome = nome;
		this.tempo=tempo;
		this.valor = valor;
	}
	
	public Evento(String nome, Aparelho aparelho, long tempo, String programa) {
		this.aparelho = aparelho;
		this.nome = nome;
		this.tempo=tempo;
		this.programa = programa;
	}
	
	public long getTempo() {
		return tempo;
	}

	public void action(){
		if(Relogio.getInstanciaUnica().getTempoAtual() == tempo){
			if(nome.equals("LIGA"))
				aparelho.liga();
			if(nome.equals("DESLIGA"))
				aparelho.desliga();
			if(nome.equals("AUMENTA"))
				((AparelhoPotenciaVariavel)aparelho).aumenta(valor);
			if(nome.equals("PROGRAMA"))
				((MaqLavarRoupa)aparelho).activa(programa);
			System.out.println(this);
		}
	}

	@Override
	public String toString() {
		return "Evento " + tempo + ": " + nome + " - " + aparelho;
	}

	@Override
	public int compareTo(Evento e) {
		return (int) (e.getTempo() - tempo);
	}
	
}

