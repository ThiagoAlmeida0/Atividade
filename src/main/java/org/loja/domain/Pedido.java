package org.loja.domain;



public class Pedido {

    private int id;
    private Cliente cliente;
    private Funcionario funcionario;
    private double valorTotal;

    public Pedido(int id, Cliente cliente, Funcionario funcionario, double valorTotal){
        this.id = id;
        this.cliente = cliente;
        this.funcionario = funcionario;
        this.valorTotal = valorTotal;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public Cliente getCliente() {
        return cliente;
    }
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    public Funcionario getFuncionario(){
        return funcionario;
    }
    public void setFuncionario(Funcionario funcionario){
        this.funcionario = funcionario;
    }
    public double getValorTotal(){
        return valorTotal;
    }
    public void setValorTotal(double valorTotal){
        this.valorTotal = valorTotal;
    }

    @Override
    public String toString() {
        return "Pedido{" +
                "id=" + id  + '\''
                + ", cliente="  + '\'' + cliente.getNome()  + '\''
                + ", funcionario="  + '\'' + funcionario.getNome()
                + ", valorTotal=" + valorTotal + "}";
    }


}
