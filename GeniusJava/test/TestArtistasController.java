
import controller.ArtistasController;
import controller.MusicaController;
import controller.ProdutorController;
import model.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

import static controller.BaseController.bd;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class TestArtistasController {

    static ProdutorController produtorController;
    static MusicaController musicaController;
    static ArtistasController artistasController;
    static String nomeProdutorInicial = "NO ID";
    static String nomeArtistaInicial = "Taylor Swift";
    static int produtorIdInicial = 1;
    static int artistaIdInicial = 1;

    @BeforeEach
    void inicia() {
        produtorController = new ProdutorController();
        musicaController = new MusicaController();
        artistasController = new ArtistasController();
        produtorController.adicionar(
                new Produtor(produtorIdInicial, nomeProdutorInicial, LocalDate.now(),
                        "Muito bom", Atribuicao.EXECUTIVO)
        );

        var artista = new Artista(artistaIdInicial, nomeArtistaInicial
                , LocalDate.now(), "descricao", "genero");
        var musica = new Musica(1, "Musica", new HashSet<>(Collections.singleton("genero")), "letra",
                new HashSet<>(Collections.singleton(artista)),
                new HashSet<>(Collections.singleton(produtorController.get(produtorIdInicial)))
        );
        artistasController.adicionar(artista);
        musicaController.adicionar(musica);
    }

    @AfterEach
    public void tearDown() {
        bd.clear();
    }

    @Test
    public void TestaSeExisteUmArtistaECadastraUmNovoArtista() {
        assertEquals(1, artistasController.get(1).getId());
        assertEquals(nomeArtistaInicial, artistasController.get(1).getNome());
        artistasController.adicionar(
                new Artista(artistasController.getProximoId(), "Metro", LocalDate.now(), "Muito" +
                        " bom!", "pop")
        );
        assertEquals(2, artistasController.get(2).getId());
        assertEquals("Metro", artistasController.get(2).getNome());
    }

    @Test
    public void TestaAEdicaoDeInformacoesDoArtista() {
        artistasController.editar(1, new Artista(produtorIdInicial, "Marie", LocalDate.now(), "teste descricao",
                "pop"));
        var produtorAtualizado = artistasController.get(produtorIdInicial);
        assertEquals("Marie", produtorAtualizado.getNome());
        assertEquals("teste descricao", produtorAtualizado.getDescricao());
        assertEquals("pop", produtorAtualizado.getGeneroMusical());
        assertEquals(LocalDate.now(), produtorAtualizado.getDataDeNascimento());
        assertEquals(artistasController.get(produtorIdInicial), produtorAtualizado);
    }

    @Test
    public void TesteDelecaoDeArtista() {
        var idArtistaExcluido = artistasController.excluir(produtorIdInicial);
        var produtorBuscado = artistasController.get(produtorIdInicial);
        assertNull(produtorBuscado);
        assertEquals(produtorIdInicial, idArtistaExcluido);
    }

    @Test
    public void TestaBuscaPeloNomeDeArtista() {
        var artistas = artistasController.get(nomeArtistaInicial);
        assertEquals(artistasController.get(), artistas);
    }

    @Test
    public void TestaGetDeTodosOsArtistaes() {
        var produtores = artistasController.get();
        assertEquals(1, produtores.size());
        assertEquals(produtores.get(0), artistasController.get(produtorIdInicial));
    }

    @Test
    public void TestaMusicasAssocidasAoArtista() {
        var musicas = artistasController.getMusicasAssociadas(produtorIdInicial);
        assertEquals(1, musicas.size());
        assertEquals(artistasController.getALlMusicas(), musicas);
        assertEquals(musicaController.get(), musicas);
        assertEquals(Arrays.stream(bd.getAllMusicas()).toList(), musicas);
    }

}
