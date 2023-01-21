package main;

import model.*;

import java.time.LocalDate;
import java.util.ArrayList;

public class Main  {

    public static void main(String[] args) {
        iniciarGeniusJava();
        MainFrame frame = new MainFrame("GeniusJava");
    }

    public static void iniciarGeniusJava(){
        // Única instancia do objeto PortalDeMusica
        PortalDeMusica bd = PortalDeMusica.getInstance();

        // cadastra o genero e recupera pelo nome, e cadastra artista
        bd.cadastrarGenero("Pop", "Bom pra chorar");
        var genero = bd.getGeneroPeloNome("Pop");
        genero.
                ifPresent(generoMusical ->
                        bd.cadastrarArtista(
                                "Taylor Swift",
                                LocalDate.now(),
                                "A loirinha manda muito",
                                "pop"));

        // recupera artista pelo nome
        var artista = bd.getArtistasPeloNome("Taylor Swift");

        // cadastra produtor e recupera pelo nome
        bd.cadastrarProdutor("Shellback", LocalDate.now(), "Karl Johan Schuster, known famously as Shellback, is a Swedish songwriter, record producer, and musician.", Atribuicao.EXECUTIVO);
        bd.cadastrarProdutor("metro", LocalDate.now(), "Karl Johan Schuster, known famously as Shellback, is a Swedish songwriter, record producer, and musician.", Atribuicao.EXECUTIVO);
        var produtor = bd.getProdutoresPeloNome("Shellback");


        // cadastra música
        ArrayList<Artista> artistas = new ArrayList<>(artista);
        ArrayList<Produtor> produtores = new ArrayList<>(produtor);

        bd.cadastrarMusica("Shake it off", genero.get(), """

                        I stay out too late
                        Got nothing in my brain
                        That's what people say
                        That's what people say
                        I go on too many dates
                        But I can't make them stay
                        At least that's what people say
                        That's what people say
                        But I keep cruising
                        Can't stop, won't stop moving
                        It's like I got this music in my mind
                        Saying it's gonna be alright
                        I never miss a beat
                        I'm lightning on my feet
                        And that's what they don't see
                        That's what they don't see
                        Players gonna play, play, play, play, play
                        And the haters gonna hate, hate, hate, hate, hate (haters gonna hate)
                        Baby, I'm just gonna shake, shake, shake, shake, shake
                        I shake it off, I shake it off
                        Heartbreakers gonna break
                        Fakers gonna fake
                        I'm just gonna shake
                        I shake it off, I shake it off
                        I shake it off, I shake it off
                        I, I, I shake it off, I shake it off
                        I, I, I shake it off, shake it off
                        I, I, I shake it off, I shake it off
                        I, I, I shake it off, I shake it off
                        I, I, I shake it off, I shake it off
                        I, I, I, shake it off, I shake it off
                        I, I, I, shake it off, I shake it off""",
                artistas,
                produtores);
        var musica = bd.getMusicaPeloNome("Shake it off");

        var musicas = new ArrayList<Musica>();
        musicas.add(musica.get());

        // cadastra album e recupera pelo nome
        bd.cadastrarAlbum("1989", LocalDate.now(), musicas);
        var album = bd.getAlbumPeloNome("1989");
    }

}
