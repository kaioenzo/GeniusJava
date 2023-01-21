package controller;

import model.Musica;
import model.PortalDeMusica;

import java.time.format.DateTimeFormatter;
import java.util.List;

public interface PessoaBaseController<K> {
    PortalDeMusica bd = PortalDeMusica.getInstance();

    List<K> get();

    void excluir(int id);

    void adicionar(K pessoa);

    void editar(int id, K pessoaAtualizada);

    K get(int id);

    List<K> getListaPeloNome(String nome);

    int getProximoId();

    List<Musica> getMusicas(int id);

    default DateTimeFormatter formatter() {
        return DateTimeFormatter.ofPattern("dd/MM/yyyy");
    }



}
