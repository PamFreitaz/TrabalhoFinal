package conexao;

import java.time.LocalDate;

import entity.Funcionario;
import entity.Dependente;
import empresa.Parentesco;
import persintence.FuncionarioDao;
import persintence.DependenteDao;

public class TesteConexao {
    public static void main(String[] args) {
        try {
            // 1 - Cria um funcionário fictício
            Funcionario funcionario = new Funcionario(
                "João da Silva",
                "12345678900",
                LocalDate.of(1990, 5, 15),
                0, // id ainda não importa, será gerado no banco
                3500.00
            );

            // 2 - Insere o funcionário no banco
            FuncionarioDao funcionarioDao = new FuncionarioDao();
            funcionarioDao.inserir(funcionario);
            System.out.println("Funcionário inserido!");

            // 3 - Cria um dependente desse funcionário
            Dependente dependente = new Dependente(
                "Maria da Silva",
                "98765432100",
                LocalDate.of(2010, 8, 20),
                0, // id não é usado na gravação
                Parentesco.FILHO
            );

            // 4 - Insere o dependente associado ao funcionário
            DependenteDao dependenteDao = new DependenteDao();
            dependenteDao.inserir(dependente, funcionario);
            System.out.println("Dependente inserido!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
