package pt.iscte.poo.instalacao.teste;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import pt.iscte.poo.instalacao.Aparelho;
import pt.iscte.poo.instalacao.Instalacao;
import pt.iscte.poo.instalacao.Ligavel;
import pt.iscte.poo.instalacao.aparelhos.AparelhoPotenciaFixa;
import pt.iscte.poo.instalacao.aparelhos.AparelhoPotenciaVariavel;
import pt.iscte.poo.instalacao.aparelhos.Lampada;
import pt.iscte.poo.instalacao.aparelhos.MicroOndas;
import pt.iscte.poo.instalacao.aparelhos.Torradeira;

public class TesteAparelhos {

	private Instalacao instalacao;

	@BeforeClass
	public static void preparacaoInicial() {
	}

	@Before
	public void preparacaoDeTeste() {
		instalacao = Instalacao.getInstanciaUnica();
		instalacao.removeTodasAsLinhas();
		instalacao.novaLinha("cozinha", 10); // cozinha, 10 tomadas

	}

	@Test
	public void testeInstalacaoAparelhos() {
		Lampada lamp1 = new Lampada("lampada1", 50);
		instalacao.ligaAparelhoATomadaLivre("cozinha", lamp1);
		lamp1.liga();
		assertEquals(50.0, instalacao.potenciaNaLinha("cozinha"), 0.0000001);
		Torradeira torradeira = new Torradeira("torradeira1", 100);
		instalacao.ligaAparelhoATomadaLivre("cozinha", torradeira);
		torradeira.liga();
		assertEquals(150, instalacao.potenciaNaLinha("cozinha"), 0.0000001);
		MicroOndas m = new MicroOndas("micro1", 900);
		instalacao.ligaAparelhoATomadaLivre("cozinha", m);
		assertEquals(150, instalacao.potenciaNaLinha("cozinha"), 0.0000001);
		m.aumenta(500);
		assertEquals(150, instalacao.potenciaNaLinha("cozinha"), 0.0000001);
		m.liga();
		assertEquals(650, instalacao.potenciaNaLinha("cozinha"), 0.0000001);
		m.aumenta(500); // s� 400 efetivo
		assertEquals(1050, instalacao.potenciaNaLinha("cozinha"), 0.0000001);

	}

	@Test
	public void testeHierarquiaAparelhos() {
		Lampada lamp1 = new Lampada("lampada1", 50);
		Torradeira torradeira = new Torradeira("torradeira1", 100);
		@SuppressWarnings("unused")
		AparelhoPotenciaFixa a = lamp1;
		a = torradeira;
		MicroOndas m = new MicroOndas("micro1", 900);
		AparelhoPotenciaVariavel b = m;
		Set<Aparelho> aparelhos = new HashSet<Aparelho>();
		aparelhos.add(lamp1);
		aparelhos.add(torradeira);
		aparelhos.add(b);
		Set<Ligavel> ligaveisATomadas = new HashSet<Ligavel>();
		ligaveisATomadas.add(lamp1);
		ligaveisATomadas.add(torradeira);
		ligaveisATomadas.add(b);

	}

}
