package persintence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement; 
import java.util.ArrayList;
import java.util.List;

import conexao.ConnectionFactory;
import entity.Funcionario;

public class FuncionarioDao {


    // Inserir funcionário e retornar o ID gerado
    public int inserir(Funcionario funcionario) {
    	String sqlFuncionario = "INSERT INTO funcionario (nome, cpf, data_nascimento, salario_bruto) VALUES (?, ?, ?, ?)";
    	// PreparedStatement para que os recursos sejam fechados igual o Roni fez na
    	// aula 11

    	 try (Connection connection = new ConnectionFactory().getConnection();
                 PreparedStatement stmt = connection.prepareStatement(sqlFuncionario, Statement.RETURN_GENERATED_KEYS)) {

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

    // NÃO FOI PRECISO ESSA PARTE PARA O TRABALHO FINAL
    
    
	// Para atualizar um funcionario
    public void atualizar(Funcionario funcionario) {
    	String sql = "UPDATE funcionario SET nome=?, cpf=?, data_nascimento=?, salario_bruto=? WHERE id=?";
        try (Connection connection = new ConnectionFactory().getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, funcionario.getNome());
            stmt.setString(2, funcionario.getCpf());
            stmt.setDate(3, java.sql.Date.valueOf(funcionario.getDataNascimento()));
            stmt.setDouble(4, funcionario.getSalarioBruto());
            stmt.setInt(5, funcionario.getId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Problemas ao atualizar! " + e.getMessage());
        }
    }

    //apagar funcionario
    public void apagar(int id) {
        String sql = "DELETE FROM funcionario WHERE id=?";
        try (Connection connection = new ConnectionFactory().getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Problemas ao apagar registro! " + e.getMessage());
        }
    }

    // Listar todos funcionários
    public List<Funcionario> listar() {
        String sql = "SELECT * FROM funcionario";
        List<Funcionario> funcionarios = new ArrayList<>();

        try (Connection connection = new ConnectionFactory().getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                funcionarios.add(new Funcionario(
                        rs.getString("nome"),
                        rs.getString("cpf"),
                        rs.getDate("data_nascimento").toLocalDate(),
                        rs.getInt("id"),
                        rs.getDouble("salario_bruto")
                ));
            }

        } catch (SQLException e) {
            System.out.println("Problemas ao listar registros! " + e.getMessage());
        }
        return funcionarios;
    }
}