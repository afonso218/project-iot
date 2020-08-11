package pt.iscte.poo.instalacao.teste;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import pt.iscte.poo.instalacao.Instalacao;
import pt.iscte.poo.instalacao.aparelhos.Lampada;

public class TesteTomada {
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
	public void testeInstalacao2Tomadas() {
		assertNotNull(instalacao.getTomadaLivre("cozinha"));
		instalacao.ligaAparelhoATomadaLivre("cozinha", new Lampada("lampada1", 50));
		assertNotNull(instalacao.getTomadaLivre("cozinha"));
		instalacao.ligaAparelhoATomadaLivre("cozinha", new Lampada("lampada2", 50));
		assertNull(instalacao.getTomadaLivre("cozinha"));
	}

	
}
