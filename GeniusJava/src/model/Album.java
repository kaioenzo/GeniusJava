package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe que representa um álbum. Com nome, data de lançamento, e músicas associadas.
 *
 * @author Kaio Enzo Salgado
 * @version 1.0
 */
public class Album {
    private final int id;
    private String nome;
    private LocalDate dataDeLancamento;
    private ArrayList<Musica> musicas;

    /**
     * @param id que representa o ID único do álbum
     * @param nome que representa o nome do álbum
     * @param dataDeLancamento que representa a data de lançamento do álbum
     * @param musicas que representa a lista de músicas do álbum
     */
    public Album(int id, String nome, LocalDate dataDeLancamento, ArrayList<Musica> musicas) {
        this.id = id; this.nome = nome; this.dataDeLancamento = dataDeLancamento; this.musicas = musicas;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public LocalDate getDataDeLancamento() {
        return dataDeLancamento;
    }

    public ArrayList<Musica> getMusicas() {
        return musicas;
    }

    public String getAlbumInfo() {
        var musicas = this.musicas.toString();
        return "Album{" + "id=" + id + ", nome='" + nome + '\'' + ", dataDeLancamento=" + dataDeLancamento + ", musica=" + musicas + '}';
    }

    /**
     * Este método adiciona uma música ao álbum
     *
     * @param musica a ser adicionada
     */
    public void adicionarMusica(Musica musica) {
        musicas.add(musica);
    }

    /**
     * Este método deleta uma música do álbum
     *
     * @param musica a ser deletada
     */
    public void deletarMusica(Musica musica) {
        musicas.remove(musica);
    }

    /**
     * Este método retorna todos os artistas presentes nesse álbum.
     *
     * @return lista de artistas
     */
    public List<Artista> getArtistas() {
        List<Artista> artistasList = new ArrayList<>(); musicas.forEach(m -> artistasList.addAll(m.getArtistas()));
        return artistasList;
    }

    /**
     * Este método retorna todos os produtores presentes nesse álbum.
     *
     * @return lista de produtores do álbum
     */
    public List<Produtor> getProdutores() {
        List<Produtor> produtoresList = new ArrayList<>();
        musicas.forEach(m -> produtoresList.addAll(m.getProdutores())); return produtoresList;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setDataDeLancamento(LocalDate dataDeLancamento) {
        this.dataDeLancamento = dataDeLancamento;
    }

    /**
     * Este método adiciona as músicas ao álbum, antes ele limpa as músicas para evitar que músicas repetidas
     * sejam adicionadas
     *
     * @param musicas lista de músicas do álbum
     */
    public void setMusicas(ArrayList<Musica> musicas) {
        this.musicas.clear(); this.musicas.addAll(musicas);
    }

    /**
     * Este método remove uma música de um álbum.
     *
     * @param musica a ser removida do álbum
     */
    public void removerMusica(Musica musica) {
        musicas.remove(musica);
    }
}
