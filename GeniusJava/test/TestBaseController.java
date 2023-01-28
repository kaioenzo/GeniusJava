import controller.BaseController;
import controller.MusicaController;
import controller.ProdutorController;
import model.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestBaseController {
    static BaseController<Produtor> controller = new ProdutorController();
    static ProdutorController produtorController = new ProdutorController();
    static MusicaController musicaController = new MusicaController();
    static String nomeProdutorInicial = "NO ID";
    static int produtorIdInicial = 1;
    @BeforeAll
    static void inicia() {
        produtorController.adicionar(
                new Produtor(produtorController.getProximoId(), nomeProdutorInicial, LocalDate.now(),
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
    @Test
    public void TestaFormatadorDeDatasPadraoDeveriaFormatarDataNoFormatoPadrao(){
        var formatter = controller.formatter();
        var data = LocalDate.of(2023, 1, 1).format(formatter);
        assertEquals("01/01/2023", data);
     }

     @Test
    public void TestaGetTodasAsMusicasDeveriaRetornarListaComUmaMusica() {
        var musisas = controller.getALlMusicas();
        assertEquals(1,musisas.size());
        assertEquals(musicaController.get(),musisas);
     }

}
