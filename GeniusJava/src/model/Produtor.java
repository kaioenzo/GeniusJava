package model;

import java.time.LocalDate;

public class Produtor extends Pessoa {
    final private int id;
    private String descricao;
    private Atribuicao funcao;

    public Produtor(int id, String nome, LocalDate dataDeNascimento, String descricao, Atribuicao funcao) {
        super(id, nome, dataDeNascimento);
        this.id = id;
        this.descricao = descricao;
        this.funcao = funcao;
    }
    public String getInfoProdutor() {
        return "Produtor{" +
                super.getInfoPessoa() +
                "id=" + id +
                ", descricao='" + descricao + '\'' +
                ", atribuicao=" + funcao +
                '}';
    }

    @Override
    public String toString() {
        return "Produtor{" +
                "id=" + id +
                ", descricao='" + descricao + '\'' +
                ", funcao=" + funcao +
                '}';
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
