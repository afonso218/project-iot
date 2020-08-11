package pt.iscte.poo.instalacao.teste;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import pt.iscte.poo.instalacao.Instalacao;
import pt.iscte.poo.instalacao.aparelhos.Lampada;

public class TesteInstalacao {
	Instalacao instalacao = null;
	
	@BeforeClass
	public static void preparacaoInicial() {
	}

	@Before
	public void preparacaoDeTeste() {
		instalacao = Instalacao.getInstanciaUnica();
		instalacao.removeTodasAsLinhas();
		instalacao.novaLinha("cozinha", 2); // cozinha, 2 tomadas
		instalacao.novaLinha("sala", 2); // sala, 2 tomadas
		instalacao.novaLinha("quartos", 2); // quartos, 2 tomadas		
	}
	
	@Test
	public void testeInstalacaoLinhas() {
		assertNotNull(instalacao.getTomadaLivre("cozinha"));
		assertNotNull(instalacao.getTomadaLivre("sala"));
		assertNotNull(instalacao.getTomadaLivre("quartos"));				
	}

	@Test
	public void testeInstalacaoPotenciaNasLinhas() {
		Lampada l1 = new Lampada("lampada1", 50);
		Lampada l2 = new Lampada("lampada2", 25);
		Lampada l3 = new Lampada("lampada3", 100);
		Lampada l4 = new Lampada("lampada4", 150);
		instalacao.ligaAparelhoATomadaLivre("cozinha", l1);
		instalacao.ligaAparelhoATomadaLivre("cozinha", l2);
		instalacao.ligaAparelhoATomadaLivre("sala", l3);
		instalacao.ligaAparelhoATomadaLivre("quartos", l4);
		assertEquals(instalacao.potenciaNaLinha("cozinha"), 0.0, 0.00001);
		assertEquals(instalacao.potenciaNaLinha("sala"), 0.0, 0.00001);
		assertEquals(instalacao.potenciaNaLinha("quartos"), 0.0, 0.00001);
		l1.liga();
		l2.liga();
		l3.liga();
		l4.liga();
		assertEquals(instalacao.potenciaNaLinha("cozinha"), 75.0, 0.00001);
		assertEquals(instalacao.potenciaNaLinha("sala"), 100.0, 0.00001);
		assertEquals(instalacao.potenciaNaLinha("quartos"), 150.0, 0.00001);
}

	@Test
	public void testeInstalacao2Tomadas() {
		assertNotNull(instalacao.getTomadaLivre("cozinha"));
		instalacao.ligaAparelhoATomadaLivre("cozinha", new Lampada("lampada1", 50));
		assertNotNull(instalacao.getTomadaLivre("cozinha"));
		instalacao.ligaAparelhoATomadaLivre("cozinha", new Lampada("lampada2", 50));
		assertNull(instalacao.getTomadaLivre("cozinha"));
	}

	@Test
	public void testeInstalacaoLampada() {
		Lampada lampada = new Lampada("lampada3", 50);
		instalacao.ligaAparelhoATomadaLivre("sala", lampada);
		assertSame(lampada, instalacao.getAparelho("lampada3"));		
	}

	@Test
	public void testeLampadaLigada() {
		Lampada lampada = new Lampada("lampada4", 50);		
		instalacao.ligaAparelhoATomadaLivre("quartos", lampada);
		lampada.liga();
		assertSame(lampada, instalacao.getAparelho("lampada4"));
		assertTrue(instalacao.getAparelho("lampada4").estaLigado());		
	}

	
}
