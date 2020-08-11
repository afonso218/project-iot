package pt.iscte.poo.instalacao.aparelhos;

import java.util.List;

import pt.iscte.poo.instalacao.Relogio;
import pt.iscte.poo.instalacao.aparelhos.extras.Ciclo;
import pt.iscte.poo.instalacao.aparelhos.extras.Programa;

public class MaqLavarRoupa extends AparelhoPotenciaVariavel {

	private long start_time;
	private long end_time;
	private Ciclo ciclo_actual;
	private Programa programa_actual;
	private List<Programa> programas;
	
	public MaqLavarRoupa(String nome, double potencia, List<Programa> programas) {
		super(nome, potencia);
		this.programas = programas;
		start_time = -1;
		end_time = -1;
	}

	public void activa(String programa){
		for(Programa x : programas){
			if(x.getNome().equals(programa)){
				programa_actual = x;
				this.liga();
				arrancaCiclo();
			}
		}
	}
	
	public void update(){
		// pode se remover
		if(start_time != -1 && end_time != -1){
			if(Relogio.getInstanciaUnica().getTempoAtual() == end_time){
				this.diminuir(ciclo_actual.getPotencia());
				arrancaCiclo();
			}
		}
	}
	
	private void arrancaCiclo(){
		ciclo_actual = programa_actual.next();
		if(ciclo_actual != null){
			System.out.println(ciclo_actual);
			start_time = Relogio.getInstanciaUnica().getTempoAtual();
			end_time = start_time + ciclo_actual.getDuracao();
			this.aumenta(ciclo_actual.getPotencia());
		}else{
			this.desliga();
			start_time = -1;
			end_time = -1;
		}
	}
	
}
