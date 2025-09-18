
//Eu fiz main só para testar
package empresa;

import java.time.LocalDate;

import entity.Dependente;
import entity.Funcionario;

public class TesteEmpresa {

	public static void main(String[] args) {
		
		Funcionario funcionario1 = new Funcionario("Pam", "111", LocalDate.of(1989,4,25), 2000.);
		Funcionario funcionario2 = new Funcionario("Daniela", "222", LocalDate.of(1992,12,14), 2500.);
		
		try {
			Dependente dependente1 = new Dependente("Arthur", "333", LocalDate.of(2009,8,24 ), Parentesco.OUTROS);
			
			//Erro ao adicionar um maior de 18 anos
			Dependente dependente2 = new Dependente("Thiago", "444", LocalDate.of(20010,8,25 ), Parentesco.SOBRINHO);
			
			//Erro ao adicionar um dependente com o mesmo CPF
			Dependente dependente3 = new Dependente("Theo", "333", LocalDate.of(2020,9,26), Parentesco.FILHO);
			

//			funcionario1.adicionarDependente(dependente1);
//			funcionario2.adicionarDependente(dependente2);
//			funcionario1.adicionarDependente(dependente3);
		
		} catch (DependenteException e) {
			System.out.println("Erro ao adicionar um dependente!" + e.getMessage());
			e.printStackTrace();
		}
	}

}
