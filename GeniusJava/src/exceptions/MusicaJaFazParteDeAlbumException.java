package exceptions;

public class MusicaJaFazParteDeAlbumException extends RuntimeException {
    String albumNome;
    String musicaNome;

    public MusicaJaFazParteDeAlbumException(String musicaNome, String albumNome) {
        super("A " + musicaNome + "música já faz parte do álbum: " + albumNome);
        this.albumNome = albumNome;
        this.musicaNome = musicaNome;
    }

    @Override
    public String toString() {
        return "A música " + musicaNome + " já faz parte do álbum: " + albumNome;
    }
}
