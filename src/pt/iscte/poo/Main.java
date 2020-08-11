package pt.iscte.poo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import javax.swing.JOptionPane;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import pt.iscte.poo.graficos.Chart;
import pt.iscte.poo.instalacao.Instalacao;
import pt.iscte.poo.instalacao.Ligavel;

public class Main {

	public static final String PATH = "json/";

	public static void main(String[] args) {
		JSONParser json = new JSONParser();
		try {
			Instalacao instalacao = Instalacao.getInstanciaUnica();

			Chart grafico = new Chart("Potencia por linha");

			instalacao.addObserver(grafico);

			JSONArray objectos = (JSONArray) json.parse(new BufferedReader(new FileReader(PATH + "instalacao.json")));

			instalacao.init(objectos);

			JSONArray listaAparelhos = (JSONArray) json
					.parse(new BufferedReader(new FileReader(PATH + "aparelhos.json")));

			List<Ligavel> aparelhos = instalacao.lerAparelhos(listaAparelhos);

			JSONArray listaLigacoes = (JSONArray) json
					.parse(new BufferedReader(new FileReader(PATH + "ligacoes.json")));
			instalacao.lerLigacoes(listaLigacoes, aparelhos);

			JSONArray listaEventos = (JSONArray) json.parse(new BufferedReader(new FileReader(PATH + "eventos.json")));
			instalacao.lerEventos(listaEventos);

			System.out.println(instalacao);

			// Scanner sc = new Scanner(System.in);
			// long fim = sc.nextInt();

			long fim = Long.valueOf(JOptionPane.showInputDialog("Please enter the end time"));

			grafico.setVisible(true);
			instalacao.simula(fim);

		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}

	}

}
