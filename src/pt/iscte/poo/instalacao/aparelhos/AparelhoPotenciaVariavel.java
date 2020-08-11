package pt.iscte.poo.instalacao.aparelhos;

import pt.iscte.poo.instalacao.Aparelho;

public abstract class AparelhoPotenciaVariavel extends Aparelho{

	public AparelhoPotenciaVariavel(String nome, double potencia) {
		super(nome, 0, potencia);
	}
	
	public void aumenta(double i) {
		setConsumo(potenciaAtual() + i);
	}
	
	public void diminuir(double i) {
		setConsumo(potenciaAtual() - i);
	}

}
