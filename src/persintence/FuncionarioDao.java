
package persintence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement; 

import conexao.ConnectionFactory;
import entity.Funcionario;

public class FuncionarioDao {

    private final Connection connection;

    public FuncionarioDao() {        
        this.connection = ConnectionFactory.getConnection();// usa a mesma conexão da ConnectionFactory 
    }

    // Inserir funcionário e retornar o ID gerado
    public int inserir(Funcionario funcionario) {
        String sqlFuncionario = "INSERT INTO funcionario (nome, cpf, data_nascimento, salario_bruto) VALUES (?, ?, ?, ?)";
        // PreparedStatement para que os recursos sejam fechados igual o Roni fez na aula 11

        try (PreparedStatement stmt = connection.prepareStatement(sqlFuncionario, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, funcionario.getNome());
            stmt.setString(2, funcionario.getCpf());
            stmt.setDate(3, java.sql.Date.valueOf(funcionario.getDataNascimento()));
            stmt.setDouble(4, funcionario.getSalarioBruto());

            int affected = stmt.executeUpdate();
            if (affected == 0) {
                throw new SQLException("Inserção falhou, nenhuma linha afetada.");
            }

            // pega o id gerado pelo banco e grava no objeto
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    int idGerado = rs.getInt(1);
                    funcionario.setId(idGerado);
                    return idGerado;
                }
            }

        } catch (SQLException e) {
            System.err.println("Problemas ao gravar funcionario: " + e.getMessage());
        }

        return -1;
    }
}
