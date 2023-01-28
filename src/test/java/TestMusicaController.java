import org.example.controller.AlbumController;
import org.example.controller.ArtistasController;
import org.example.controller.MusicaController;
import org.example.controller.ProdutorController;
import org.example.model.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;

import static org.example.controller.BaseController.bd;
import static org.junit.jupiter.api.Assertions.*;

public class TestMusicaController {
    static ProdutorController produtorController;
    static MusicaController musicaController;
    static ArtistasController artistasController;
    static AlbumController albumController;
    static String produtorNomeInicial = "NO ID";
    static String musicaNomeInicial = "Lover";
    static int idInicial = 1;
    static String artistaNomeInicial = "Taylor Swift";


    @BeforeEach
    void inicia() {
        produtorController = new ProdutorController();
        musicaController = new MusicaController();
        artistasController = new ArtistasController();
        albumController = new AlbumController();

        produtorController.adicionar(new Produtor(idInicial, produtorNomeInicial, LocalDate.now(), "Muito bom",
                Atribuicao.EXECUTIVO));

        var artista = new Artista(1, artistaNomeInicial, LocalDate.now(), "descricao", "genero");
        var musica = new Musica(1, musicaNomeInicial, new HashSet<>(Collections.singleton("genero")), "letra",
                new HashSet<>(Collections.singleton(artista)), new HashSet<>(Collections.singleton(produtorController.get(idInicial))));
        artistasController.adicionar(artista);
        musicaController.adicionar(musica);
    }

    @AfterEach
    public void tearDown() {
        bd.clear();
    }


    @Test
    public void TestaGetETestaAdicionarDeveriaRetornarOIdInicialDaMusicaONomeDaMusica() {
        assertEquals(1, musicaController.get(1).getId());
        assertEquals(musicaNomeInicial, musicaController.get(1).getNome());
    }

    @Test
    public void TestaAdicionarDeveriaRetornarArtistaNovo(){
        musicaController.adicionar(new Musica(musicaController.getProximoId(), "Daylight",
                new HashSet<>(Collections.singleton("pop")), "Muito" + " bom!", new HashSet<>(Collections.singleton(artistasController.get().get(0))), new HashSet<>(Collections.singleton(produtorController.get().get(0)))));
        assertEquals(2, musicaController.get(2).getId());
        assertEquals("Daylight", musicaController.get(2).getNome());
    }

    @Test
    public void TestaEditarDeveriaRetornarArtistaAtualizado() {
        musicaController.editar(1, new Musica(musicaController.getProximoId(), "Daylight",
                new HashSet<>(Collections.singleton("pop")), "Letra",
                new HashSet<>(Collections.singleton(artistasController.get().get(0))), new HashSet<>(Collections.singleton(produtorController.get().get(0)))));
        var musicaAtualizada = musicaController.get(idInicial);
        assertEquals("Daylight", musicaAtualizada.getNome());
        assertEquals("Letra", musicaAtualizada.getLetra());
        assertFalse(musicaAtualizada.getFazParteAlbum());
        assertEquals(artistasController.get(), musicaAtualizada.getArtistas());
        assertEquals(produtorController.get(), musicaAtualizada.getProdutores());
        assertEquals("pop", musicaAtualizada.getGeneros().get(0));
    }

    @Test
    public void TestaExcluirDeveriaRetornarIdDaMusicaExlcuida() {
        var idMusicaExcluido = musicaController.excluir(idInicial);
        var produtorBuscado = musicaController.get(idInicial); assertNull(produtorBuscado);
        assertEquals(idInicial, idMusicaExcluido);
    }

    @Test
    public void TestaGetComStringDeveriaRetornarListaDeMusicas() {
        var musicas = musicaController.get(musicaNomeInicial);
        assertEquals(musicaController.get(), musicas);
    }

    @Test
    public void TestaGetDeTodasAsMusicasDeveriaRetornarListaComUmaMusicaEId() {
        var musicas = musicaController.get();
        assertEquals(1, musicas.size());
        assertEquals(musicas.get(0), musicaController.get(idInicial));
    }

    @Test
    public void TestaMetodoGetMusicasAssociadasComRetornoNulo() {
        assertNull(musicaController.getMusicasAssociadas(1));
    }

    @Test
    public void artistasString_DeveriaRetornarNomeArtistaInicial() {
        var musica = musicaController.get(1);
        assertEquals(artistaNomeInicial, musicaController.artistasStrings(musica));
    }

    @Test
    public void produtoresString_DeveriaRetornarNomeProdutorInicial() {
        var musica = musicaController.get(1);
        assertEquals(produtorNomeInicial, musicaController.produtoresString(musica));
    }

    @Test
    public void TestaGetNomeAlbumAssociadoDeveriaRetornarMensagemPadraoSeMusicaNaoEstaAssociadaANenhumAlbum() {
        var nomeAlbum = musicaController.getNomeAlbumAssociado(1);
        assertEquals("Não faz parte de nenhum álbum", nomeAlbum);
    }

    @Test
    public void TestaGetNomeAlbumAssociadoDeveriaRetornarNomeDoAlbumAssociado() {
        String nomeAlbumRetornado = "Lover";
        albumController.adicionar(new Album(1, nomeAlbumRetornado, LocalDate.now(), new ArrayList<>(musicaController.get())));
        var nomeAlbum = musicaController.getNomeAlbumAssociado(1);
        assertEquals("Lover", nomeAlbum);
    }

    @Test
    public void TestaGetAlbumAssociadoDeveriaRetornarOptionalVazio() {
        var nomeAlbum = musicaController.getAlbumAssocieado(1);
        assertEquals(Optional.empty(),nomeAlbum);
    }
    @Test
    public void TestaGetAlbumAssociadoDeveriaRetornarInstaciaDoAlbumAssociado() {
        String nomeAlbumRetornado = "Lover";
        albumController.adicionar(new Album(1, nomeAlbumRetornado, LocalDate.now(), new ArrayList<>(musicaController.get())));
        var nomeAlbum = musicaController.getAlbumAssocieado(1);
        assertEquals(albumController.get(1), nomeAlbum.get());
    }
}
