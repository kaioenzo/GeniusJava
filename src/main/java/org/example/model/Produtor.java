package org.example.model;

import java.time.LocalDate;

/**
 * Classe que representa um produtor e extende a classe Pessoa. Com descrição e uma atribuição.
 *
 * @author Kaio Enzo Salgado
 * @version 1.0
 * @see Pessoa
 * @see Atribuicao
 */
public class Produtor extends Pessoa {
    final private int id;
    private String descricao;
    private Atribuicao funcao;

    /**
     * @param id que representa o ID único do produtor
     * @param nome que representa o nome do produtor
     * @param dataDeNascimento que representa a data de nascimento do produtor
     * @param descricao que representa a descrição do produtor
     * @param funcao que representa a função do produtor
     */
    public Produtor(int id, String nome, LocalDate dataDeNascimento, String descricao, Atribuicao funcao) {
        super(id, nome, dataDeNascimento); this.id = id; this.descricao = descricao; this.funcao = funcao;
    }

    /**
     * Este método retorna as informações do produtor
     *
     * @return ‘string’ com as informações do produtor.
     */
    public String getInfoProdutor() {
        return "Produtor{" + super.getInfoPessoa() + "id=" + id + ", descricao='" + descricao + '\'' + ", atribuicao=" + funcao + '}';
    }

    @Override
    public String toString() {
        return "Produtor{" + "id=" + id + ", descricao='" + descricao + '\'' + ", funcao=" + funcao + '}';
    }

    public int getId() {
        return id;
    }

    public Atribuicao getFuncao() {
        return funcao;
    }

    public String getDescricao() {
        return descricao;
    }


    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setFuncao(Atribuicao funcao) {
        this.funcao = funcao;
    }
}
