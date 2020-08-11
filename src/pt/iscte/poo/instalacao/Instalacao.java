package pt.iscte.poo.instalacao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Stack;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Instalacao extends Observable{

	private static Instalacao unica = new Instalacao();
	private List<Linha> linhas;
	private Stack<Evento> eventos;
	
	public Instalacao() {
		eventos = new Stack<Evento>();
		linhas = new ArrayList<Linha>();
	}
	
	/**
	 * Devole uma Instancia Unica da Class Instalacao
	 * @return Instalacao
	 */
	public static Instalacao getInstanciaUnica() {
		return unica;
	}

	/**
	 * Adiciona uma nova linha a instalacao (nao permite duplicacao de nomes)
	 * @param nome
	 * @param n_tomadas
	 */
	public void novaLinha(String nome, long n_tomadas) {
		if(isIdLivre(nome))
			linhas.add(new Linha(nome, n_tomadas));
		else
			System.out.println("> Existe uma linha com o mesmo nome (" + nome + ")!" );
	}

	private boolean isIdLivre(String nome){
		for(Linha l : linhas)
			if(l.getNome().equals(nome))
				return false;
		return true;
	}
	
	/**
	 * Adiciona um Ligavel/Aparelho a uma tomada livre da linha.
	 * @param nome_linha
	 * @param ligavel
	 */
	public void ligaAparelhoATomadaLivre(String nome_linha, Ligavel ligavel) {
		Linha linha = getLinha(nome_linha);
		if(linha != null){
			if(linha.haveAparelho(ligavel.getId())){
				System.out.println("> Ja existe um aparelho com o mesmo id na linha!");
			}else{
				if(linha.isFree())
					linha.ligaAparelho(ligavel);
				else
					System.out.println("> " + linha.getNome() + " FULL! - " + ligavel);
			}
		}
	}
	
	/**
	 * Devolve a linha que possui um determinada nome
	 * @param nome
	 * @return Linha
	 */
	private Linha getLinha(String nome){
		for(Linha l : linhas)
			if(l.getNome().equals(nome))
				return l;
		return null;
	}
	
	/**
	 * Devolve uma tomada livre da linha
	 * @param nome
	 * @return Tomada
	 */
	public Tomada getTomadaLivre(String nome) {
		Tomada x = getLinha(nome).getFree();
		if(x == null)
			System.out.println("> A linha "+ nome + " tem todas as tomadas ocupadas!");
		return x;
	}
	
	/**
	 * Devolve o Ligavel/Aparelho com o mesmo nome se existir
	 * @param nome_aparelho
	 * @return Ligavel
	 */
	public Ligavel getAparelho(String nome_aparelho) {
		for(Linha l : linhas)
			if(l.haveAparelho(nome_aparelho))
				return l.getAparelho(nome_aparelho);
		return null;
	}

	/**
	 * Devolve a potencia de uma linha (caso nao exista a linha devolve 0)
	 * @param nome_linha
	 * @return potencia_da_linha
	 */
	public double potenciaNaLinha(String nome_linha) {
		for(Linha l : linhas)
			if(l.getNome().equals(nome_linha))
				return l.getConsumo();
		return 0.0;
	}

	/**
	 * Remove todas as Linhas da Instalacao
	 */
	public void removeTodasAsLinhas() {
		linhas = new ArrayList<Linha>();
	}
	
	/**
	 * Inicializa o objecto Instalacao atraves de um objecto JSONArray
	 * @param JSONArray_objects
	 */
	public void init(JSONArray objectos) {
		for(int i = 0; i < objectos.size(); i++){
			JSONObject obj = (JSONObject) objectos.get(i);
			if(obj.containsKey("nome") && obj.containsKey("tomadas")){
				novaLinha((String)obj.get("nome"), (long)obj.get("tomadas"));
			}
		}
	}

	/**
	 * Recebe um JSONArray e revolve todos os ligaveis numa lista
	 * @param JSONArray_lista
	 * @return List<Ligavel>
	 */
	public List<Ligavel> lerAparelhos(JSONArray lista) {
		List<Ligavel> ligaveis = new ArrayList<Ligavel>();
		for(int i = 0; i < lista.size(); i++){
			JSONObject obj = (JSONObject) lista.get(i);
			ligaveis.add(Aparelho.novoAparelho(obj));
		}
		return ligaveis;
	}

	/**
	 * Recebe um JSONArray e uma lista de aparelhos, e liga cada um dos aparelhos a respectiva linha
	 * @param JSONArray_listaLigacoes
	 * @param List<Ligavel> aparelhos
	 */
	public void lerLigacoes(JSONArray listaLigacoes, List<Ligavel> aparelhos) {
		System.out.println("Ligar:");
		for(int i = 0; i < listaLigacoes.size(); i++){
			JSONObject ligacao = (JSONObject)listaLigacoes.get(i);
			if(ligacao.containsKey("aparelho") && ligacao.containsKey("ligadoA")){
				for(Ligavel l : aparelhos){
					if(l.getId().equals(ligacao.get("aparelho"))){
						ligaAparelhoATomadaLivre((String)ligacao.get("ligadoA"), l);
						System.out.println( "\t" + (String)ligacao.get("ligadoA") + " > " + l.getId());
					}
				}
			}
		}
		System.out.println();
	}
	
	/**
	 * Le eventos que vao ocorrer na simulacao (estes sao convertidos de JSONObject para um objecto Evento)
	 * Estes Eventos criados sao adicionados a um Lista/Stack para serem usados na simulacao
	 * (Nota: Sao ordenados todos os eventos consoante o seu tempo)
	 * @param listaEventos
	 */
	public void lerEventos(JSONArray listaEventos) {
		for(int i = 0; i < listaEventos.size(); i++){
			JSONObject evento = (JSONObject) listaEventos.get(i);
			if(evento.containsKey("accao") && evento.containsKey("idAparelho") && evento.containsKey("tempo")){
				Aparelho aparelho = (Aparelho)getAparelho((String)evento.get("idAparelho"));
				if(aparelho != null){
					if(evento.containsKey("valor")){
						Evento e = new Evento((String)evento.get("accao"), aparelho, (double) evento.get("valor") ,(long) evento.get("tempo"));
						eventos.push(e);
					}else{
						if(evento.containsKey("programa")){
							Evento e = new Evento((String)evento.get("accao"), aparelho ,(long) evento.get("tempo"), (String) evento.get("programa"));
							eventos.push(e);
						}else{
							Evento e = new Evento((String)evento.get("accao"), aparelho, (long) evento.get("tempo"));
							eventos.push(e);
						}
					}
				}else{
					System.out.println("> Nao existe aparelho: " + evento.get("idAparelho"));
				}
			}
		}
		Collections.sort(eventos);
	}
	
	/**
	 * Recebe um numero de ciclos que a simulacao ir percorrer
	 * Responsavel por simular o ambiente da Instalacao
	 * @param tempo
	 */
	public void simula(long tempo){
		System.out.println(this);
		while(Relogio.getInstanciaUnica().getTempoAtual() < tempo){
			while(!eventos.isEmpty() && eventos.peek().getTempo() == Relogio.getInstanciaUnica().getTempoAtual()){
				eventos.pop().action();
			}
			for(Linha l : linhas)
				l.updateAparelhos();
			
			Map<String, Double> potencias = new HashMap<String,Double>();
			for(Linha l : linhas)
				potencias.put(l.getNome(), l.getConsumo());
			setChanged();
			notifyObservers(potencias);
			System.out.println(this);
			Relogio.getInstanciaUnica().tique();
		}
	}


	public String toString() {
		String text = "\n" + "--------- t=" 
							+ Relogio.getInstanciaUnica().getTempoAtual() 
							+ "--------- \n";
		for(Linha l : linhas)
			text += l + "\n";
		return text;
	}
	
}
