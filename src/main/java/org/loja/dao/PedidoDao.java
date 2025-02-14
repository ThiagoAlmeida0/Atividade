package org.loja.dao;

import org.loja.domain.Pedido;
import org.loja.domain.Cliente;
import org.loja.domain.Funcionario;
import org.loja.domain.Produto;
import org.loja.db.ConnectionHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PedidoDao {

    public void savePedido(Pedido pedido, List<int[]> itensPedido) {

        String sql= "INSERT INTO PEDIDO (CPF_CLIENTE_FK, CPF_FUNCIONARIO_FK, VALOR_TOTAL) VALUES (?, ?, ?)";

        try {

            Connection connection = ConnectionHelper.getConnection();
            PreparedStatement pstPedido = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

            pstPedido.setString(1, pedido.getCliente().getCpf());
            pstPedido.setString(2, pedido.getFuncionario().getCpf());
            pstPedido.setDouble(3, pedido.getValorTotal());

            pstPedido.executeUpdate();

            ResultSet rs = pstPedido.getGeneratedKeys();
            int pedidoId = -1;
            if (rs.next()) {
                pedidoId = rs.getInt(1);
            }

            rs.close();
            pstPedido.close();
            connection.close();

            createItemPedido(itensPedido,pedidoId);

        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void createItemPedido(List<int[]> itensPedido, int pedidoId){

        String sql= "INSERT INTO ITEM_PEDIDO (ID_PEDIDO_FK, ID_PRODUTO_FK, QUANTIDADE, VALOR) VALUES (?, ?, ?, ?)";
        String sqlEstoque = "UPDATE PRODUTO SET QUANTIDADE = QUANTIDADE - ? WHERE ID = ?";

        try {

            Connection connection = ConnectionHelper.getConnection();
            PreparedStatement pst = connection.prepareStatement(sql);
            PreparedStatement pstEstoque = connection.prepareStatement(sqlEstoque);

            for (int[] item : itensPedido) {

                Produto produto = new ProdutoDao().findById(item[0]);
                double valor = produto.getValorUnit() * item[1];

                pst.setInt(1, pedidoId);
                pst.setInt(2, item[0]);
                pst.setInt(3, item[1]);
                pst.setDouble(4, valor);

                pst.executeUpdate();

                pstEstoque.setInt(1, item[1]);
                pstEstoque.setInt(2, item[0]);
                pstEstoque.executeUpdate();
            }

            pst.close();
            pstEstoque.close();
            connection.close();

        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Pedido> findALl(){
        String sql          = "SELECT * FROM PEDIDO";
        List<Pedido> lista  = new ArrayList<>();

        try{
            Connection connection = ConnectionHelper.getConnection();
            PreparedStatement pst = connection.prepareStatement(sql);

            ResultSet rs = pst.executeQuery();

            while (rs.next()) {

                int id = rs.getInt("ID");
                String clienteCpf = rs.getString("CPF_CLIENTE_FK");
                String funcionarioCpf = rs.getString("CPF_FUNCIONARIO_FK");
                double valorTotal = rs.getDouble("Valor_total");

                ClienteDao clienteDao = new ClienteDao();
                Cliente cliente = clienteDao.findByCpf(clienteCpf);

                FuncionarioDao funcionarioDao = new FuncionarioDao();
                Funcionario funcionario = funcionarioDao.findByCpf(funcionarioCpf);

                Pedido pedido = new Pedido(id,cliente,funcionario,valorTotal);

                lista.add(pedido);
            }

            pst.close();
            connection.close();
        } catch (ClassNotFoundException e){
            throw new RuntimeException(e);
        } catch (SQLException e){
            new RuntimeException(e);
        }
        return lista;
    }

}

