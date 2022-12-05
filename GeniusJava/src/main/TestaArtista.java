package main;

import model.Artista;
import model.PortalDeMusica;
import model.GeneroMusical;

import java.time.LocalDate;

public class TestaArtista {
    public static void main(String[] args) {
        PortalDeMusica bd = PortalDeMusica.getInstance();
        bd.cadastrarGenero("Hip-Hop", "Gennêro pica");
        var genero = bd.getGeneroPeloNome("Hip-Hop");
        genero.
                ifPresent(generoMusical ->
                        bd.cadastrarArtista("Kanye", LocalDate.now(), "É o cara né", generoMusical));
        var artista = bd.getArtistaPeloNome("Kanye");

        System.out.println(artista.get().getInfoArtista());
    }
}
