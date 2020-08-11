package pt.iscte.poo.instalacao;

public class Relogio {

	private static Relogio relogio = new Relogio();
	private int tempo = 0; 
	
	public void tique(){
		tempo++;
	}
	
	public static Relogio getInstanciaUnica() {
		return relogio;
	}

	public int getTempoAtual() {
		return tempo;
	}

}
