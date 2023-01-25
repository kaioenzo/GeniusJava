package controller;

import model.Musica;
import model.PortalDeMusica;

import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

public interface BaseController<K> {
    PortalDeMusica bd = PortalDeMusica.getInstance();

    K get(int id);
    List<K> get();
    List<K> get(String nome);
    void excluir(int id);

    void adicionar(K pessoa);

    void editar(int id, K infoAtualizada);

    int getProximoId();

    List<Musica> getMusicasAssociadas(int id);

    default List<Musica> getALlMusicas(){return Arrays.stream(bd.getAllMusicas()).toList();}

    default DateTimeFormatter formatter() {
        return DateTimeFormatter.ofPattern("dd/MM/yyyy");
    }



}
