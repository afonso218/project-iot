package pt.iscte.poo;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import pt.iscte.poo.instalacao.Aparelho;
import pt.iscte.poo.instalacao.Instalacao;
import pt.iscte.poo.instalacao.Relogio;
import pt.iscte.poo.instalacao.aparelhos.Computador;
import pt.iscte.poo.instalacao.aparelhos.Frigorifico;
import pt.iscte.poo.instalacao.aparelhos.Lampada;
import pt.iscte.poo.instalacao.aparelhos.MicroOndas;
import pt.iscte.poo.instalacao.aparelhos.Torradeira;

public class MainIntercalar {
	
	private static final int END_T = 1000;

	public static void main(String[] args) {
		Instalacao casa = Instalacao.getInstanciaUnica();
		casa.novaLinha("cozinha", 10); // Linha cozinha, 10 tomadas
		casa.novaLinha("quartos", 4); // Linha quartos, 4 tomadas
		casa.novaLinha("sala", 6); // Linha sala, 6 tomadas
		
		Lampada lamp1 = new Lampada("lamp1", 20); // L�mpada simples, 20W
		casa.ligaAparelhoATomadaLivre("cozinha", lamp1); // Liga aparelho a uma tomada livre na linha "cozinha"
		
		System.out.println(casa); // Imprime estado da casa (tempo + consumo por linha)
		
		int t = 0;
		for (; t != END_T / 4; t++) {
			Relogio.getInstanciaUnica().tique(); // Avan�a uma unidade de tempo	
		}

		lamp1.liga(); // Acende a l�mpada
		System.out.println(casa);
		
		for (; t != END_T / 2; t++) {
			Relogio.getInstanciaUnica().tique();
		}

		Torradeira torradeira = new Torradeira("torradeira1", 500); // Cria torradeira
		casa.ligaAparelhoATomadaLivre("cozinha", torradeira); // Liga a tomada livre
		torradeira.liga(); // Liga a torradeira (On)
		
		JSONParser json = new JSONParser();
		JSONObject obj = null;
		try {
			obj = (JSONObject)json.parse(new BufferedReader(new FileReader(Main.PATH + "frigorifico.json")));
			@SuppressWarnings("unused")
			Frigorifico frigo = (Frigorifico) Aparelho.novoAparelho(obj);

			obj = (JSONObject)json.parse(new BufferedReader(new FileReader(Main.PATH + "computador.json")));
			@SuppressWarnings("unused")
			Computador computador = (Computador) Aparelho.novoAparelho(obj);
		
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Lampada lamp2 = new Lampada("lamp2", 30);
		casa.ligaAparelhoATomadaLivre("cozinha", lamp2);
		lamp2.liga();
		
		Lampada lamp3 = new Lampada("lamp3", 40);
		casa.ligaAparelhoATomadaLivre("quartos", lamp3);
		lamp3.liga();
		
		System.out.println(casa);
		
		for (; t != 3 * END_T / 4; t++) {
			Relogio.getInstanciaUnica().tique();
		}

		torradeira.desliga();
		
		MicroOndas microOndas = new MicroOndas("microOndas", 900); // Cria micro-ondas, com 900W de pot�ncia m�xima
		casa.ligaAparelhoATomadaLivre("cozinha", microOndas);
		microOndas.aumenta(500); // Regula a pot�ncia a que vai cozinhar
		
		lamp2.desliga(); // desliga l�mpada
		System.out.println(casa);
		
		for (; t != END_T; t++) {
			Relogio.getInstanciaUnica().tique();
		}
		
		microOndas.liga(); // Liga micro-ondas
		System.out.println(casa);
	}

}
