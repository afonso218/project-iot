package pt.iscte.poo.instalacao.aparelhos.extras;

public class Ciclo{

	private long duracao;
	private double potencia;
	
	public Ciclo(long duracao, double d) {
		this.duracao=duracao;
		this.potencia=d;
	}
	
	public long getDuracao() {
		return duracao;
	}
	
	public double getPotencia() {
		return potencia;
	}	

	@Override
	public String toString() {
		return "Ciclo: D=" + duracao + " P=" + potencia ;
	}
	
}
