package pt.iscte.poo.instalacao.aparelhos;

import java.util.Random;

public class Computador extends AparelhoPotenciaFixa {

	public Computador(String nome, double potencia) {
		super(nome, potencia);
	}

	@Override
	public void update() {
		if(estaLigado())
			setConsumo(new Random().nextDouble()*potenciaMaxima());
	}

}
