package pt.iscte.poo.instalacao.teste;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import pt.iscte.poo.instalacao.aparelhos.Lampada;

public class TesteLampada {
	
	private Lampada lampada1;
	
	@BeforeClass
	public static void preparacaoInicial() {
		
	}

	@Before
	public void preparacaoDeTeste() {
		lampada1 = new Lampada("lampada1", 50);
	}

	@Test
	public void testeAtributos() {
		assertEquals("lampada1", lampada1.getId());
		assertEquals(50.0, lampada1.potenciaMaxima(), 0.0001);
		assertEquals(0.0, lampada1.potenciaAtual(), 0.0001);
	}
	
	@Test
	public void testeAcender() {
		assertFalse(lampada1.estaLigado());
		lampada1.liga();
		assertTrue(lampada1.estaLigado());
		assertEquals(50.0, lampada1.potenciaAtual(), 0.0001);
	}

	@Test
	public void testeApagar() {
		lampada1.liga();
		assertTrue(lampada1.estaLigado());
		lampada1.desliga();
		assertFalse(lampada1.estaLigado());
	}
	
}
