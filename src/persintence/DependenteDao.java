package persintence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import conexao.ConnectionFactory;
import entity.Dependente;
import entity.Funcionario;

public class DependenteDao {

	private Connection connection;

	public DependenteDao() {
		connection = new ConnectionFactory().getConnection();
	}

	public void inserir(Dependente dependente, Funcionario funcionario) {
		
		String sqlDependente = "insert into dependente (nome, cpf, data_nascimento, parentesco, funcionario_id ) VALUES (?, ?, ?, ?, ?)";
		try {
			PreparedStatement stmtDependente = connection.prepareStatement(sqlDependente);
			
			stmtDependente.setString(1, dependente.getNome());
			stmtDependente.setString(2, dependente.getCpf());
			stmtDependente.setDate(3, java.sql.Date.valueOf(dependente.getDataNascimento()));
			stmtDependente.setString(4, dependente.getParentesco().name());
			stmtDependente.setInt(5, funcionario.getId()); 
			
			stmtDependente.execute();
			stmtDependente.close();
			connection.close();
			
			
		} catch (SQLException e) {
			System.out.println("Problemas ao gravar registro!");
		}
	}
	
}
