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
        String sql = "INSERT INTO folha_pagamento (funcionario_id, data_pagamento, desconto_inss, desconto_ir, salario_liquido) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, funcionario.getId());
            stmt.setDate(2, java.sql.Date.valueOf(folha.getDataPagamento()));
            stmt.setDouble(3, folha.getDescontoINSS());
            stmt.setDouble(4, folha.getDescontoIR());
            stmt.setDouble(5, folha.getSalarioLiquido());
            stmt.execute();
        } catch (SQLException e) {
            System.out.println("Problemas ao gravar folha de pagamento!");
            e.printStackTrace();
        }
    }
}
