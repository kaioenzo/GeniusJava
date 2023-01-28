import controller.AlbumController;
import controller.ArtistasController;
import controller.MusicaController;
import controller.ProdutorController;
import exceptions.MusicaJaFazParteDeAlbumException;
import model.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

import static controller.BaseController.bd;
import static org.junit.jupiter.api.Assertions.*;

public class TestAlbumController {
    static ProdutorController produtorController;
    static MusicaController musicaController;
    static ArtistasController artistasController;
    static AlbumController albumController;
    static String produtorNomeInicial = "NO ID";
    static String musicaNomeInicial = "DayLight";
    static String albumNomeInicial = "Lover";
    static int idInicial = 1;
    static String artistaNomeInicial = "Taylor Swift";


    @BeforeEach
    void inicia() {
        produtorController = new ProdutorController();
        musicaController = new MusicaController();
        artistasController = new ArtistasController();
        albumController = new AlbumController();

        var produtor = new Produtor(idInicial, produtorNomeInicial, LocalDate.now(), "Muito bom",
                Atribuicao.EXECUTIVO);
        produtorController.adicionar(produtor);
        var artista = new Artista(idInicial, artistaNomeInicial, LocalDate.now(), "descricao", "genero");
        artistasController.adicionar(artista);
        var musica = new Musica(idInicial, musicaNomeInicial, new HashSet<>(Collections.singleton("genero")), "letra",
                new HashSet<>(Collections.singleton(artista)),
                new HashSet<>(Collections.singleton(produtorController.get(idInicial))));
        musicaController.adicionar(musica);
        var album = new Album(idInicial, albumNomeInicial, LocalDate.now(), new ArrayList<>(musicaController.get()));
        albumController.adicionar(album);
    }

    @AfterEach
    public void tearDown() {
        bd.clear();
    }


    @Test
    public void TestaBuscaDeAlbumPeloNomeDeveriaRetornarNomeDoAlbumPadrao() {
        var pesquisa = albumController.get(albumNomeInicial);
        assertEquals(albumNomeInicial, pesquisa.get(0).getNome());
    }

    @Test
    public void TestaExcluirDeveriaRetornarIdInicial() {
        var id = albumController.excluir(idInicial);
        assertEquals(idInicial, id);
        assertEquals(0, albumController.get().size());
    }

    @Test
    public void TestaEditarDeveriaAtualizarInformacoesDoAlbum() {
        var albumAtualizado = albumController.editar(idInicial, new Album(1, "1985", LocalDate.now(),
                new ArrayList<>(musicaController.get())));
        assertEquals("1985", albumAtualizado.getNome());
        assertEquals(musicaController.get(), albumAtualizado.getMusicas());

    }

    @Test
    public void TesteGetProximoIdDeveriaRetornar2() {
        assertEquals(2, albumController.getProximoId());
    }

    @Test
    public void TestaGetMusicasAssocidasDeveriaRetornarListaComUmaMusica() {
        var musicas = musicaController.get();
        var musicasAssociadas = albumController.getMusicasAssociadas(idInicial);
        assertEquals(musicas, musicasAssociadas);
        assertEquals(musicaNomeInicial, musicas.get(0).getNome());
        assertEquals(1, musicasAssociadas.size());
    }

    @Test
    public void TestaGetQtdMusicasDeveriaRetornar1() {
        assertEquals(1, albumController.getQtdMusicas(albumController.get(idInicial)));
    }

    @Test
    public void TestaArtistasStringDeveriaRetornarNomeArtistaInicial() {
        assertEquals(artistaNomeInicial, albumController.artistasStrings(albumController.get(idInicial)));
    }

    @Test
    public void TestaProdutoresStringDeveriaRetonarNomeProdutorInicial() {
        assertEquals(produtorNomeInicial, albumController.produtoresString(albumController.get(idInicial)));
    }

    @Test
    public void TestaAdicionarDeveriaLancarExceptionDeMusicaQueJaFazParteDeAlbum() {
        assertThrows(MusicaJaFazParteDeAlbumException.class, () -> albumController.adicionar(
                new Album(albumController.getProximoId(),
                        "AlbumComMusicaInvalida",
                        LocalDate.now(),
                        new ArrayList<>(musicaController.get()))))
        ;
    }
}
