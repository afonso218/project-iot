package pt.iscte.poo.instalacao;

public interface Ligavel {

	String getId();
	void liga();
	void desliga();
	boolean isTripla();
	boolean estaLigado();
	double potenciaAtual();
	double potenciaMaxima();
	void update();
	
}
