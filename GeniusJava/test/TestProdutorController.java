import controller.MusicaController;
import controller.ProdutorController;
import model.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;

import static controller.BaseController.bd;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class TestProdutorController {
    static ProdutorController produtorController;
    static MusicaController musicaController;
    static String nomeProdutorInicial = "NO ID";
    static int produtorIdInicial = 1;


    @BeforeEach
    void inicia() {
        produtorController = new ProdutorController();
        musicaController = new MusicaController();
        produtorController.adicionar(
                new Produtor(produtorIdInicial, nomeProdutorInicial, LocalDate.now(),
                        "Muito bom", Atribuicao.EXECUTIVO)
        );

        var artista = new Artista(1, "Artista "
                , LocalDate.now(), "descricao", "genero");
        var musica = new Musica(1, "Musica", new HashSet<>(Collections.singleton("genero")), "letra",
                new HashSet<>(Collections.singleton(artista)),
                new HashSet<>(Collections.singleton(produtorController.get(produtorIdInicial)))
        );
        musicaController.adicionar(musica);
    }

    @AfterEach
    public void tearDown() {
        bd.clear();
    }


    @Test
    public void TestaGetProdutorPeloIdDeveriaRetornarProdutorComIdUmENomeIgualAoNomeInicial() {
        assertEquals(1, produtorController.get(1).getId());
        assertEquals(nomeProdutorInicial, produtorController.get(1).getNome());

    }

    @Test
    public void TestaAdicionaDeveriaRetonarUmProdutorCadastrado() {
        produtorController.adicionar(
                new Produtor(produtorController.getProximoId(), "Metro", LocalDate.now(), "Muito" +
                        " bom!", Atribuicao.ENGENHEIRODESOM)
        );
        assertEquals(2, produtorController.get(2).getId());
        assertEquals("Metro", produtorController.get(2).getNome());
    }

    @Test
    public void TestaEditarDeveriaRetornarInformacoesAtualizadaDoProdutor() {
        produtorController.editar(1, new Produtor(produtorIdInicial, "Marie", LocalDate.now(), "teste descricao", Atribuicao.EDITOR));
        var produtorAtualizado = produtorController.get(produtorIdInicial);
        assertEquals("Marie", produtorAtualizado.getNome());
        assertEquals("teste descricao", produtorAtualizado.getDescricao());
        assertEquals(Atribuicao.EDITOR, produtorAtualizado.getFuncao());
        assertEquals(LocalDate.now(), produtorAtualizado.getDataDeNascimento());
        assertEquals(produtorController.get(produtorIdInicial), produtorAtualizado);
    }

    @Test
    public void TesteExcluirDeveriaRetornarIdDoProdutorExcluido() {
        var idProdutorExcluido = produtorController.excluir(produtorIdInicial);
        var produtorBuscado = produtorController.get(produtorIdInicial);
        assertNull(produtorBuscado);
        assertEquals(produtorIdInicial, idProdutorExcluido);
    }

    @Test
    public void TestaBuscaPeloNomeDeProdutorDeveriaRetornarListaDeProdutores() {
        var produtores = produtorController.get(nomeProdutorInicial);
        assertEquals(produtorController.get(), produtores);
    }

    @Test
    public void TestaGetDeTodosOsProdutoresDeveriaRetornarListaComUmProdutor() {
        var produtores = produtorController.get();
        assertEquals(1, produtores.size());
        assertEquals(produtores.get(0), produtorController.get(produtorIdInicial));
    }

    @Test
    public void TestaGetMusicasAssocidasAoProdutorDeveriaRetornarListaComUmaMusica() {
        var musicas = produtorController.getMusicasAssociadas(produtorIdInicial);
        assertEquals(1, musicas.size());
        assertEquals(produtorController.getALlMusicas(), musicas);
        assertEquals(musicaController.get(), musicas);
        assertEquals(musicaController.getALlMusicas(), musicas);
    }

}
