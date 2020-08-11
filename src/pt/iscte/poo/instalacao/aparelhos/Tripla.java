package pt.iscte.poo.instalacao.aparelhos;

import pt.iscte.poo.instalacao.Ligavel;
import pt.iscte.poo.instalacao.Linha;
import pt.iscte.poo.instalacao.Tomada;

public class Tripla implements Ligavel{

	private double potenciaMax;
	private Linha linha;
	
	public Tripla(String id, double potenciaMax, long tomadas) {
		this.potenciaMax=potenciaMax;
		this.linha = new Linha(id,tomadas);
	}
	
	public void add(Ligavel a){
		if(linha.isFree() && (a.potenciaMaxima() +linha.potenciaMaxTotal() <= potenciaMax)){
			linha.ligaAparelho(a);
		}
		// else new Exception("<Tripla> Aparelho excede a potencia maxima da tripla!");
	}
	
	public boolean haveAparelho(String nome) {
		return linha.haveAparelho(nome);
	}
	
	public Ligavel getAparelho(String nome) {
		return linha.getAparelho(nome);
	}
	
	public boolean isFree(){
		return linha.isFree();
	}
	
	public Tomada getFree(){
		return linha.getFree();
	}
	
	@Override
	public boolean isTripla() {
		return true;
	}

	@Override
	public String getId() {
		return linha.getNome();
	}

	@Override
	public void liga() {
		
	}

	@Override
	public void desliga() {
		
	}

	@Override
	public boolean estaLigado() {
		return true;
	}

	@Override
	public double potenciaAtual() {
		return linha.getConsumo();
	}

	@Override
	public double potenciaMaxima() {
		return potenciaMax;
	}

	@Override
	public void update() {
		linha.updateAparelhos();
	}
	
	@Override
	public String toString() {
		String txt = this.getId() + "\t" + linha.getConsumo() + "/"+potenciaMax+" W\n"; 
		for(Tomada t : linha.getTomadas())
			if(t.getLigavel() != null)
				txt += "\t\t" + t.getLigavel() + "\n";
			else
				txt += "\t\t< empty >\n";
		return txt;
	}
	
}
