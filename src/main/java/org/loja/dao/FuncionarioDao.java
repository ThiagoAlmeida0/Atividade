package org.loja.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.loja.db.ConnectionHelper;
import org.loja.domain.Funcionario;

public class FuncionarioDao {

    public Funcionario findByCpf(String cpf) {

        String sql = "SELECT * FROM FUNCIONARIO WHERE cpf = ?";
        Funcionario funcionario = null;

        try {

            Connection connection = ConnectionHelper.getConnection();
            PreparedStatement pst = connection.prepareStatement(sql);

            pst.setString(1, cpf);

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {

                String nome     = rs.getString("NOME");
                String endereco = rs.getString("ENDERECO");
                String telefone = rs.getString("TELEFONE");

                funcionario = new Funcionario(cpf, nome, endereco, telefone);

            }

            pst.close();
            connection.close();

        }
        catch (ClassNotFoundException e){
            throw new RuntimeException(e);
        } catch (SQLException e){
            new RuntimeException(e);
        }
        return funcionario;
    }
}
