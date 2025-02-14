package org.loja;

import org.loja.dao.*;
import org.loja.domain.*;
import java.util.*;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final ClienteDao clienteDao = new ClienteDao();
    private static final ProdutoDao produtoDao = new ProdutoDao();
    private static final FuncionarioDao funcionarioDao = new FuncionarioDao();
    private static final PedidoDao pedidoDao = new PedidoDao();

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n--- MENU ---");
            System.out.println("1. Cadastrar produto");
            System.out.println("2. Cadastrar cliente");
            System.out.println("3. Buscar produto pelo ID");
            System.out.println("4. Listar todos os produtos");
            System.out.println("5. Efetuar venda");
            System.out.println("6. Listar vendas realizadas");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");

            int opcao = scanner.nextInt();
            scanner.nextLine(); // Limpar buffer

            switch (opcao) {
                case 1: cadastrarProduto(); break;
                case 2: cadastrarCliente(); break;
                case 3: buscarProdutoPorId(); break;
                case 4: listarProdutos(); break;
                case 5: efetuarVenda(); break;
                case 6: listarVendas(); break;
                case 0: System.out.println("Saindo..."); return;
                default: System.out.println("Opção inválida!");
            }
        }
    }

    private static void cadastrarProduto() {
        System.out.print("Nome do produto: ");
        String nome = scanner.nextLine();
        System.out.print("Valor unitário: ");
        double valor = scanner.nextDouble();
        System.out.print("Quantidade em estoque: ");
        int quantidade = scanner.nextInt();

        Produto produto = new Produto(0, nome, valor, quantidade);
        produtoDao.saveProduto(produto);
        System.out.println("Produto cadastrado com sucesso!");
    }

    private static void cadastrarCliente() {
        System.out.print("CPF: ");
        String cpf = scanner.nextLine();
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Endereço: ");
        String endereco = scanner.nextLine();
        System.out.print("Telefone: ");
        String telefone = scanner.nextLine();

        Cliente cliente = new Cliente(cpf, nome, endereco, telefone);
        clienteDao.save(cliente);
        System.out.println("Cliente cadastrado com sucesso!");
    }

    private static void buscarProdutoPorId() {
        System.out.print("Digite o ID do produto: ");
        int id = scanner.nextInt();
        Produto produto = produtoDao.findById(id);

        if (produto != null) {
            System.out.println(produto);
        } else {
            System.out.println("Produto não encontrado!");
        }
    }

    private static void listarProdutos() {
        List<Produto> produtos = produtoDao.findAll();
        if (produtos.isEmpty()) {
            System.out.println("Nenhum produto encontrado!");
        } else {
            for (Produto p : produtos) {
                System.out.println(p);
            }
        }
    }

    private static void efetuarVenda() {
        System.out.print("Digite o CPF do funcionário: ");
        String cpfFuncionario = scanner.nextLine();
        Funcionario funcionario = funcionarioDao.findByCpf(cpfFuncionario);
        if (funcionario == null) {
            System.out.println("Funcionário não encontrado.");
            return;
        }

        System.out.print("Digite o CPF do cliente: ");
        String cpfCliente = scanner.nextLine();
        Cliente cliente = clienteDao.findByCpf(cpfCliente);
        if (cliente == null) {
            System.out.println("Cliente não encontrado.");
            return;
        }

        List<int[]> itensPedido = new ArrayList<>();
        double valorTotal = 0;

        while (true) {
            System.out.print("Digite o ID do produto (ou 0 para finalizar): ");
            int idProduto = scanner.nextInt();
            if (idProduto == 0) break;

            Produto produto = produtoDao.findById(idProduto);
            if (produto == null) {
                System.out.println("Produto não encontrado.");
                continue;
            }

            System.out.print("Digite a quantidade desejada: ");
            int quantidade = scanner.nextInt();
            if (quantidade > produto.getQuantidade()) {
                System.out.println("Quantidade indisponível em estoque.");
                continue;
            }

            double valorItem = produto.getValorUnit() * quantidade;
            itensPedido.add(new int[]{idProduto, quantidade});
            valorTotal += valorItem;
        }

        if (itensPedido.isEmpty()) {
            System.out.println("Pedido cancelado. Nenhum item foi adicionado.");
            return;
        }

        Pedido pedido = new Pedido(0, cliente, funcionario, valorTotal);
        pedidoDao.savePedido(pedido, itensPedido);
        System.out.println("Venda realizada com sucesso!");
    }

    private static void listarVendas() {
        List<Pedido> pedidos = pedidoDao.findALl();
        if (pedidos.isEmpty()) {
            System.out.println("Nenhuma venda encontrada!");
        } else {
            for (Pedido p : pedidos) {
                System.out.println(p);
            }
        }
    }
}
