package org.loja.domain;

public class Produto {
    private int id;
    private String nome;
    private double valorUnit;
    private int quantidade;

    public Produto(int id, String nome, double valor_unit, int quantidade){
        this.id         = id;
        this.nome       = nome;
        this.valorUnit = valor_unit;
        this.quantidade = quantidade;
    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getNome(){
        return nome;
    }
    public void setNome(String nome){
        this.nome = nome;
    }
    public double getValorUnit(){
        return valorUnit;
    }

    public void setValorUnit(int valorUnit){
        this.valorUnit = valorUnit;
    }
    public int getQuantidade(){
        return quantidade;
    }
    public void setQuantidade( int quantidade){
        this.quantidade = quantidade;
    }

    @Override
    public String toString() {
        return "Produto{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", valorUnit=" + valorUnit +
                ", quantidade=" + quantidade +
                '}';
    }
}

