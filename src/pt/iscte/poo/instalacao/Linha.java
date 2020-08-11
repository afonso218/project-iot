package pt.iscte.poo.instalacao;

import java.util.ArrayList;
import java.util.List;

public class Linha {

	private String nome;
	private List<Tomada> tomadas;
	
	public Linha(String nome, long x) {
		this.nome = nome;
		tomadas = new ArrayList<Tomada>();
		for(int i = 0; i < x; i++){
			tomadas.add(new Tomada());
		}
	}
	
	public String getNome() {
		return nome;
	}
	
	public List<Tomada> getTomadas() {
		return tomadas;
	}
	
	public double getConsumo() {
		double consumo = 0;
		for(Tomada t : tomadas)
			if(t.estaLigado())
				consumo += t.getLigavel().potenciaAtual();
		return consumo;
	}
	
	public boolean isFree() {
		for(Tomada t : tomadas){
			if(t.isFree())
				return true;
		}
		return false;
	}
	
	public Tomada getFree(){
		for(Tomada t : tomadas)
			if(t.isFree()){
				return t.getFree();
			}
		return null;
	}
	
	public void ligaAparelho(Ligavel aparelho) {
		getFree().liga(aparelho);
	}

	public boolean haveAparelho(String nome) {
		for(Tomada t : tomadas)
			if(t.haveAparelho(nome))
				return true;
		return false;
	}
	
	public Ligavel getAparelho(String nome) {
		for(Tomada t : tomadas)
			if(t.haveAparelho(nome)){
				return t.getAparelho(nome);
			}
		return null;
	}

	public double potenciaMaxTotal() {
		int p = 0;
		for(Tomada t : tomadas)
			p += t.getLigavel().potenciaMaxima();
		return p;
	}

	public void updateAparelhos() {
		for(Tomada t : tomadas)
			if(!t.isFree())
				t.getLigavel().update();
	}
	
	public String toString() {
		String tomadasText = "";
		for(Tomada t : tomadas)
			if(t.getLigavel() != null)
				tomadasText += t.getLigavel() + "\n\t";
			else
				tomadasText += "< empty >\n\t";
		return nome + "\t" + getConsumo() + " W\n\t" + tomadasText;
	}
	
}
