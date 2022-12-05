package model;

import java.time.LocalDate;

public class Produtor extends Pessoa {
    private int id;
    private String descricao;
    private Atribuicao funcao;

    public Produtor(int id, String nome, LocalDate dataDeNascimento, String descricao, Atribuicao funcao) {
        super(id, nome, dataDeNascimento);
        this.descricao = descricao;
        this.funcao = funcao;
    }
    public String getInfoProdutor(){
        return super.getInfoPessoa() + " "+ descricao + " " + funcao.toString().toLowerCase();
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
}
