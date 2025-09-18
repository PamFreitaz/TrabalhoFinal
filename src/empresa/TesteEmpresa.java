package empresa;

import java.time.LocalDate;

public class TesteEmpresa {

	public static void main(String[] args) {
		
		Funcionario funcionario1 = new Funcionario("Pam", "11611155508", LocalDate.of(1992,4,25), 2000.);
		
		try {
			Dependente dependente1 = new Dependente("Arthur", "11144455501", LocalDate.of(2009,8,24 ), Parentesco.OUTROS);

			//funcionario1
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		//calcula o INSS do funcionario
        double inss = funcionario1.calcularINSS();
        System.out.println("Desconto INSS: " + inss);
        
        //calcula o IR do funcionário com o valor do INSS já calculado
        double ir = funcionario1.calcularIR(inss);
        System.out.println("Desconto IR: " + ir);
        
        //calcula o salário líquido: salário bruto - INSS - IR
        double salarioLiquido = funcionario1.getSalarioBruto() - inss - ir;
        System.out.println("Salário Líquido: " + salarioLiquido);
    }
}

	
