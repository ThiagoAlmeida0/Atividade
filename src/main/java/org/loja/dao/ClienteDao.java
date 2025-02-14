package org.loja.dao;

import org.loja.db.ConnectionHelper;
import org.loja.domain.Cliente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClienteDao {

    public void save(Cliente cliente) {

        String sql = "INSERT INTO CLIENTE VALUES (?,?,?,?);";

        try {

            Connection connection = ConnectionHelper.getConnection();
            PreparedStatement pst = connection.prepareStatement(sql);

            pst.setString(1, cliente.getCpf());
            pst.setString(2, cliente.getNome());
            pst.setString(3, cliente.getEndereco());
            pst.setString(4, cliente.getTelefone());

            pst.execute();

            pst.close();
            connection.close();

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Cliente findByCpf(String cpf) {

        String sql = "SELECT * FROM CLIENTE WHERE cpf = ?";
        Cliente cliente = null;

        try{

            Connection connection = ConnectionHelper.getConnection();
            PreparedStatement pst = connection.prepareStatement(sql);

            pst.setString(1, cpf);

            ResultSet rs = pst.executeQuery();

            if(rs.next()) {

                String nome     = rs.getString("nome");
                String endereco = rs.getString("endereco");
                String telefone = rs.getString("telefone");

                cliente = new Cliente(cpf, nome, endereco, telefone);

            }

            pst.close();
            connection.close();

        } catch (ClassNotFoundException e){
            throw new RuntimeException(e);
        } catch (SQLException e){
            new RuntimeException(e);
        }
        return cliente;
    }





    public List<Cliente> findAll() {
        String sql = "SELECT * FROM CLIENTE;";

        List<Cliente> lista = new ArrayList<>();
        try {
            Connection connection = ConnectionHelper.getConnection();
            PreparedStatement pst = connection.prepareStatement(sql);

            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                String cpf      = rs.getString("CPF");
                String nome     = rs.getString("NOME");
                String endereco = rs.getString("ENDERECO");
                String telefone = rs.getString("TELEFONE");

                Cliente cliente = new Cliente(cpf, nome, endereco, telefone);
                lista.add(cliente);
            }

            pst.close();
            connection.close();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return lista;
    }
}