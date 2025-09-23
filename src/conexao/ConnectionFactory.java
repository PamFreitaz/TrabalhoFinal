
package conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
    
    private static String url = "jdbc:postgresql://gondola.proxy.rlwy.net:34293/railway";
    private static String usuario = "postgres";
    private static String senha = "qOBKRgQvSXGVfyRCOKNAnoPGgUqPqnXv";    
    private static Connection connection;
    
    public static Connection getConnection() {
        if (connection == null) { // conecta uma vez no banco de dados
            try {
                System.out.println("Conectando ao Banco de Dados...");
                connection = DriverManager.getConnection(url, usuario, senha);
                System.out.println("Conectado com sucesso!");
            } catch (SQLException e) {
                System.err.println("Não foi possível conectar: " + e.getMessage());
            }
        }
        return connection;
    }
}
