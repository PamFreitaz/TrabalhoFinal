package persintence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import conexao.ConnectionFactory;
import empresa.FolhaPagamento;
import entity.Funcionario;

public class FolhaPagamentoDao {
		
	private Connection connection;
	
	public FolhaPagamentoDao() {
		connection = new ConnectionFactory().getConnection();
	}
	
	public void inserir(FolhaPagamento folha, Funcionario funcionario) {
		
		String sqlFolha = "insert into folhaPagamento (funcionario_id, dataPagamento, descontoInss, descontoIr, salarioLiquido) VALUES (?, ?, ?, ?, ?)";
		
		try {
			PreparedStatement stmtFolha = connection.prepareStatement(sqlFolha);
			
			stmtFolha.setInt(1, funcionario.getId());
			stmtFolha.setDate(2, java.sql.Date.valueOf(folha.getDataPagamento()));
			stmtFolha.setDouble(3, folha.getDescontoINSS());
			stmtFolha.setDouble(4, folha.getDescontoIR());
			stmtFolha.setDouble(5, folha.getSalarioLiquido());
			
			stmtFolha.execute();
			stmtFolha.close();
			connection.close();

		} catch (SQLException e) {
			System.out.println("Problemas ao gravar registro!");
		}
		
	}
}
