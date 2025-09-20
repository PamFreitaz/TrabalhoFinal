package persintence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import conexao.ConnectionFactory;
import entity.Funcionario;

public class FuncionarioDao {

	// variável para a conexao com banco
	private Connection connection;

	public FuncionarioDao() {
		connection = new ConnectionFactory().getConnection();
	}

	// inserindo os funcionario
	public void inserir(Funcionario funcionario) {

		// Insert para um novo funcionario na tabela do banco de dados
		String sqlFuncionario = "insert into funcionario (nome, cpf, dataNascimento, salarioBruto) VALUES (?, ?, ?, ?)";

		try {
			// PreparedStatement, para que os recursos sejam fechados igual o Roni fez na
			// aula 11

			PreparedStatement stmtFuncionario = connection.prepareStatement(sqlFuncionario);

			// Valores para o comando SQL do funcionário
			stmtFuncionario.setString(1, funcionario.getNome());
			stmtFuncionario.setString(2, funcionario.getCpf());
			// tive que fazer conversão de Localdate para date linguagem SQL
			stmtFuncionario.setDate(3, java.sql.Date.valueOf(funcionario.getDataNascimento()));
			stmtFuncionario.setDouble(4, funcionario.getSalarioBruto());

			// executar o comando SQL
			stmtFuncionario.execute();
			stmtFuncionario.close();
			connection.close();

		} catch (SQLException e) {
			System.out.println("Problemas ao gravar registro!");
		}
	}

	// Para atualizar um funcionario
	public void atualizar(Funcionario funcionario) {
		String sql = "Update funcionario SET nome=?, cpf=?, dataNascimento=?, salarioBruto=?, WHERE cpf=?";
		try {
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setString(1, funcionario.getNome());
			stmt.setString(2, funcionario.getCpf());
			stmt.setDate(3, java.sql.Date.valueOf(funcionario.getDataNascimento()));
			stmt.setDouble(4, funcionario.getSalarioBruto());
			stmt.setString(5, funcionario.getCpf());

			stmt.execute();
			stmt.close();
			connection.close();

		} catch (SQLException e) {
			System.out.println("Problemas ao gravar!");
		}
	}

	public void apagar(int id) {
		String sql = "delete from cliente where id=?";
		try {
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setInt(1, id);
			stmt.execute();
			stmt.close();
			connection.close();
		} catch (SQLException e) {
			System.out.println("Problemas ao apagar registro!");
			e.printStackTrace();
		}
	}

	public List<Funcionario> listar() {
		String sql = "select * from funcionario";
		List<Funcionario> funcionario = new ArrayList<>();
		try {
			PreparedStatement stmt = connection.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				funcionario.add(new Funcionario(rs.getString("nome"), rs.getString("cpf"),
						rs.getDate("dataNascimento").toLocalDate(), rs.getInt("funcionario_id"),
						rs.getDouble("salarioBruto")));
			}
			stmt.close();
			connection.close();

		} catch (SQLException e) {
			System.out.println("Problemas ao listar registros!");
			e.printStackTrace();
		}
		return funcionario;
	}
}
