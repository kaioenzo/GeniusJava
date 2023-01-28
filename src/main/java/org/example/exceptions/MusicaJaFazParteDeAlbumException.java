package org.example.exceptions;

import org.example.controller.AlbumController;
import org.example.model.Album;
import org.example.model.Produtor;

/**
 * Esta classe, é uma unchecked exception. Ela existe para prover informações sobre a tentativa de cadastrar uma
 * música a mais de um álbum. O seu uso está presente em {@link AlbumController#adicionar(Album)}
 *
 * @author Kaio Enzo Salgado
 * @version 1.0
 * @see Produtor
 */
public class MusicaJaFazParteDeAlbumException extends RuntimeException {
    String albumNome;
    String musicaNome;

    public MusicaJaFazParteDeAlbumException(String musicaNome, String albumNome) {
        super("A " + musicaNome + "música já faz parte do álbum: " + albumNome); this.albumNome = albumNome;
        this.musicaNome = musicaNome;
    }

    /**
     * @return string com informações da música que já esta associada a um álbum, e o usuário está tentando associar
     * a outro.
     */
    @Override
    public String toString() {
        return "A música " + musicaNome + " já faz parte do álbum: " + albumNome;
    }
}
