package main;

import model.*;

import java.time.LocalDate;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
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
                                generoMusical));
        System.out.println(genero.get());

        // recupera artista pelo nome
        var artista = bd.getArtistaPeloNome("Taylor Swift");
        System.out.println(artista.get().getInfoArtista());

        // cadastra produtor e recupera pelo nome
        bd.cadastrarProdutor("Shellback", LocalDate.now(), "Karl Johan Schuster, known famously as Shellback, is a Swedish songwriter, record producer, and musician.", Atribuicao.Executivo);
        var produtor = bd.getProdutorPeloNome("Shellback");
        System.out.println(produtor.get().getInfoProdutor());


        // cadastra música
        ArrayList<Artista> artistas = new ArrayList<>();
        artistas.add(artista.get());
        ArrayList<Produtor> produtores = new ArrayList<>();
        produtores.add(produtor.get());

        bd.cadastrarMusica("Shake it off", genero.get(), "\n" +
                        "I stay out too late\n" +
                        "Got nothing in my brain\n" +
                        "That's what people say\n" +
                        "That's what people say\n" +
                        "I go on too many dates\n" +
                        "But I can't make them stay\n" +
                        "At least that's what people say\n" +
                        "That's what people say\n" +
                        "But I keep cruising\n" +
                        "Can't stop, won't stop moving\n" +
                        "It's like I got this music in my mind\n" +
                        "Saying it's gonna be alright\n" +
                        "I never miss a beat\n" +
                        "I'm lightning on my feet\n" +
                        "And that's what they don't see\n" +
                        "That's what they don't see\n" +
                        "Players gonna play, play, play, play, play\n" +
                        "And the haters gonna hate, hate, hate, hate, hate (haters gonna hate)\n" +
                        "Baby, I'm just gonna shake, shake, shake, shake, shake\n" +
                        "I shake it off, I shake it off\n" +
                        "Heartbreakers gonna break\n" +
                        "Fakers gonna fake\n" +
                        "I'm just gonna shake\n" +
                        "I shake it off, I shake it off\n" +
                        "I shake it off, I shake it off\n" +
                        "I, I, I shake it off, I shake it off\n" +
                        "I, I, I shake it off, shake it off\n" +
                        "I, I, I shake it off, I shake it off\n" +
                        "I, I, I shake it off, I shake it off\n" +
                        "I, I, I shake it off, I shake it off\n" +
                        "I, I, I, shake it off, I shake it off\n" +
                        "I, I, I, shake it off, I shake it off",
                artistas,
                produtores);
        var musica = bd.getMusicaPeloNome("Shake it off");
        System.out.println(musica.get().getMusicaInfo());

        var musicas = new ArrayList<Musica>();
        musicas.add(musica.get());

        // cadastra album e recupera pelo nome
        bd.cadastrarAlbum("1989", LocalDate.now(), musicas);
        var album = bd.getAlbumPeloNome("1989");
        System.out.println(album.get().getAlbumInfo());
    }
}
