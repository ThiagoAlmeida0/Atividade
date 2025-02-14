package org.loja.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.loja.db.ConnectionHelper;
import org.loja.domain.Produto;


public class ProdutoDao {

    public void saveProduto(Produto produto) {

        String sql = "INSERT INTO produto VALUES (?,?,?,?);";
        
        try {

            Connection connection = ConnectionHelper.getConnection();
            PreparedStatement pst = connection.prepareStatement(sql);

            pst.setInt(1, produto.getId());
            pst.setString(2, produto.getNome());
            pst.setDouble(3, produto.getValorUnit());
            pst.setInt(4, produto.getQuantidade());

            pst.execute();

            pst.close();
            connection.close();

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    
    
    public Produto findById(int id) {

        String sql = "SELECT * FROM PRODUTO WHERE ID = ?";
        Produto produto = null;

        try{

            Connection connection = ConnectionHelper.getConnection();
            PreparedStatement pst = connection.prepareStatement(sql);

            pst.setInt(1, id);

            ResultSet rs = pst.executeQuery();

            if(rs.next()) {

                String nome      = rs.getString("Nome");
                int quantidade   = rs.getInt("Quantidade");
                double valorUnit = rs.getDouble("VALOR_UNIT");

                produto = new Produto(id,nome,valorUnit,quantidade);

            }

            pst.close();
            connection.close();

        } catch (ClassNotFoundException e){
            throw new RuntimeException(e);
        } catch (SQLException e){
            new RuntimeException(e);
        }
        return produto;
    }


    public List<Produto> findAll(){
        String sql          = "SELECT * FROM PRODUTO;";
        List<Produto> lista = new ArrayList<>();
        try {
            Connection connection = ConnectionHelper.getConnection();
            PreparedStatement pst = connection.prepareStatement(sql);

            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                int id           = rs.getInt("ID");
                String nome      = rs.getString("NOME");
                double valorUnit = rs.getDouble("VALOR_UNIT");
                int quantidade   = rs.getInt("QUANTIDADE");

                Produto produto = new Produto(id,nome,valorUnit,quantidade);
                lista.add(produto);
            }

            pst.close();
            connection.close();
        }
        catch (ClassNotFoundException e){
            throw new RuntimeException(e);
        } catch (SQLException e){
            new RuntimeException(e);
        }
        return lista;
    }
}
