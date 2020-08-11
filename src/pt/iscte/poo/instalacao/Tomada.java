package pt.iscte.poo.instalacao;
import pt.iscte.poo.instalacao.aparelhos.Tripla;

public class Tomada {

	private Ligavel ligavel;
	
	public boolean isFree() {
		if(ligavel == null){
			return true;
		}else{
			if(ligavel.isTripla()){
				return ((Tripla)ligavel).isFree();
			}else
				return false;
		}
	}

	public Tomada getFree(){
		if(ligavel == null){
			return this;
		}else{
			if(ligavel.isTripla()){
				return ((Tripla)ligavel).getFree();
			}else
				return null;
		}
	}

	public boolean haveAparelho(String nome) {
		if(ligavel == null){
			return false;
		}else{
			if(ligavel.isTripla()){
				return ((Tripla)ligavel).haveAparelho(nome);
			}else
				return ligavel.getId().equals(nome);
		}
	}
	
	// TEM
	public Ligavel getAparelho(String nome) {
		if(ligavel == null)
			return null;
		else{
			if(ligavel.isTripla()){
				return ((Tripla)ligavel).getAparelho(nome);
			}else{
				if(ligavel.getId().equals(nome)){
					return ligavel;
				}else{
					return null;
				}
			}
		}
	}

	// TEM
	public boolean estaLigado() {
		if(ligavel != null)
			return ligavel.estaLigado();
		else
			return false;
	}


	// TEM
	public void liga(Ligavel ligavel) {
		this.ligavel = ligavel;
	}
	
	public Ligavel getLigavel(){
		return ligavel;
	}

	public String getId() {
		if(ligavel == null)
			return "";
		else
			return ligavel.getId();
	}

}
