package persintence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import conexao.ConnectionFactory;
import entity.Dependente;
import entity.Funcionario;

public class DependenteDao {

	private final Connection connection;

    public DependenteDao() {
        this.connection = new ConnectionFactory().getConnection(); // usa a conexão de ConnectionFactory
    }	
    public void inserir(Dependente dependente, Funcionario funcionario) {
        String sql = "INSERT INTO dependente (nome, cpf, data_nascimento, parentesco, funcionario_id) " +
                     "VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, dependente.getNome());
            stmt.setString(2, dependente.getCpf());
            stmt.setDate(3, java.sql.Date.valueOf(dependente.getDataNascimento()));
            stmt.setString(4, dependente.getParentesco().name());
            stmt.setInt(5, funcionario.getId()); // só o id do funcionario é necessário

            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Problemas ao gravar dependente! " + e.getMessage());
        }
    }
}
