package pt.iscte.poo.instalacao;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import pt.iscte.poo.instalacao.aparelhos.*;
import pt.iscte.poo.instalacao.aparelhos.extras.Programa;

public class Aparelho implements Ligavel{
	
	private String id;
	private double potencia;
	private double potenciaMax;
	private boolean estado;
	
	public Aparelho(String id, double potencia) {
		this.estado = false;
		this.potencia = potencia;
		this.potenciaMax = potencia;
		this.id=id;
	}
	
	public Aparelho(String id, double potenciaInicial, double potenciaMax) {
		this.estado = false;
		this.potencia = potenciaInicial;
		this.potenciaMax = potenciaMax;
		this.id=id;
	}
	
	public String getId() {
		return id;
	}
	
	public void liga() {
		estado = true;
	}

	public void desliga() {
		estado = false;
	}
	
	public void update(){
		
	}
	
	public boolean estaLigado(){
		return estado;
	}

	public double potenciaAtual() {
		if(estado)
			return potencia;
		else
			return 0.0;
	}
	
	public double potenciaMaxima() {
		return potenciaMax;
	}
	
	@Override
	public boolean isTripla() {
		return false;
	}
	
	public void setConsumo(double consumo) {
		if(consumo < 0){
			potencia=0;
		}else{
			if(consumo > potenciaMax){
				potencia=potenciaMax;
			}else{
				potencia = consumo;
			}
		}
	}
	
	public static Ligavel novoAparelho(JSONObject obj) {
		try{
			if(obj.containsKey("tipo") && obj.containsKey("id")){
				
				if(obj.get("tipo").equals("computador") && obj.containsKey("potenciaMaxima"))
					return new Computador((String)obj.get("id"), (double)obj.get("potenciaMaxima"));
				
				if(obj.get("tipo").equals("frigorifico") && obj.containsKey("potencia")){
					return new Frigorifico((String)obj.get("id"), (double)obj.get("potencia"));
				}
				if(obj.get("tipo").equals("microOndas") && obj.containsKey("potenciaMaxima")){
					return new Frigorifico((String)obj.get("id"), (double)obj.get("potenciaMaxima"));
				}
				
				if(obj.get("tipo").equals("lampada") && obj.containsKey("potencia")){
					return new Lampada((String)obj.get("id"), (double)obj.get("potencia"));
				}
				if(obj.get("tipo").equals("lampadaVariavel") && obj.containsKey("potenciaMaxima")){
					return new LampadaVariavel((String)obj.get("id"), (double)obj.get("potenciaMaxima"));
				}
				
				if(obj.get("tipo").equals("tripla") && obj.containsKey("potenciaMaxima") && obj.containsKey("nTomadas")){
					return new Tripla((String)obj.get("id"), (double)obj.get("potenciaMaxima"), (long) obj.get("nTomadas"));
				}
				
				if(obj.get("tipo").equals("torradeira") && obj.containsKey("potencia")){
					return new Torradeira((String)obj.get("id"), (double)obj.get("potencia"));
				}
				
				if(obj.get("tipo").equals("maqLavarRoupa") && obj.containsKey("potenciaMaxima") && obj.containsKey("programas")){
					List<Programa> programas = new ArrayList<Programa>();
					JSONArray objProg = (JSONArray)obj.get("programas");
					for(int i = 0; i < objProg.size() ; i++){
						JSONObject prog = (JSONObject) objProg.get(i);
						Programa p = new Programa((String)prog.get("id"));
						JSONArray ciclos = (JSONArray) prog.get("ciclos");
						for(int j = 0; j < ciclos.size(); j++){
							JSONObject o = (JSONObject) ciclos.get(j);
							p.addCiclo((long)o.get("duracao"),(double)o.get("potencia"));
						}
						programas.add(p);
					}
					return new MaqLavarRoupa((String)obj.get("id"), (double)obj.get("potenciaMaxima"), programas);
				}
			}
		}catch(Exception e){
			System.err.println("novoAparelho não conseguiu ler JSONObject: " + obj);
		}
		System.out.println("<ALERTA> JSON nao possui formato adequado!");
		return null;
	}

	@Override
	public String toString() {
		return id + " " + potencia + " W ("+ (estado?"on":"off") + ")";
	}
	
}
