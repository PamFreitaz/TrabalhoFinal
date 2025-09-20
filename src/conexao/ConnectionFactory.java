package conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
	
	private String url = "gondola.proxy.rlwy.net";
	private String usuario = "postgres";
	private String senha = "qOBKRgQvSXGVfyRCOKNAnoPGgUqPqnXv";
	private Connection connection;
	
	public Connection getConnection() {
		System.out.println("Conectando ao banco de dados...");
		
		try {
			connection = DriverManager.getConnection(url,usuario, senha);
			if(connection != null) {
				System.out.println("Conectado com sucesso");
			}
		} catch (SQLException e) {
			System.out.println("Não foi possível conectar");
		}
		return connection;
	}
}	
	
