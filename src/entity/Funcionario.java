package entity;

import java.time.LocalDate;
import java.util.ArrayList;

import empresa.Calculadora;
import empresa.Dependente;
import empresa.Pessoa;

public class Funcionario extends Pessoa implements Calculadora {
	private double salarioBruto;
	private double descontoInss;
	private double descontoIr;
	private ArrayList<Dependente> dependentes;

	public Funcionario(String nome, String cpf, LocalDate dataNascimento, double salarioBruto2) {
		super(nome, cpf, dataNascimento);
		this.salarioBruto = salarioBruto2;
		this.dependentes = new ArrayList<>();

	}

	public double getSalarioBruto() {
		return salarioBruto;
	}

	public double getDescontoInss() {
		return descontoInss;
	}

	public double getDescontoIr() {
		return descontoIr;
	}

	public ArrayList<Dependente> getDependentes() {
		return dependentes;
	}

	// Método para adicionar dependente
//	public void adicionarDependente(Dependente dependente) throws DependenteException {
//		// validação de CPF duplicado
//		for (Dependente d : dependentes) {
//			// comparando o CPF do dependente atual com o CPF do novo dependente
//			if (d.getCpf().equals(dependente.getCpf())) {
//				throw new DependenteException("\nCPF de dependente já cadastrado!");
//
//			}
//		}
//		// Passando na validação, adiciona o novo dependente
//		this.dependentes.add(dependente);
//	}

	// YURI FEZ ESSA PARTE DO CALCULO

	
	public double calcularINSS() {
		for (FaixaINSS faixa : FaixaINSS.values()) { // ve as faixas do enum FaixaINSS
			if (salarioBruto <= faixa.getLimite()) { // se o valor da faixa for maior ou igual usa a formula
				return (salarioBruto * faixa.getAliquota()) - faixa.getDeducao(); // formula: (salário × alíquota) − dedução																					
			}
		}

		FaixaINSS ultimaFaixa = FaixaINSS.FAIXA4; // se o salário bruto for maior que 8157.41 aplica o valor maximo da  última faixa
		return (ultimaFaixa.getLimite() * ultimaFaixa.getAliquota()) - ultimaFaixa.getDeducao();
	}

	
	public double calcularIR(double valorINSS) {
		double baseCalculo = salarioBruto // salário bruto - INSS - dedução por dependentes(usando o size, pra pegar
											// 								quantos dependentes cada funcionario tem)
				- valorINSS - (dependentes.size() * FaixaIR.VALOR_DEPENDENTE);
		// se a base for menor ou igual a 0, nao tem imposto
		if (baseCalculo <= 0) {
			return 0.0;
		}
		for (FaixaIR faixa : FaixaIR.values()) { // percorre as faixas do enum FaixaIR
			// qd encontra a faixa que o limite maior ou igual ao valores da FaixaIR aplica a formula			
			if (baseCalculo <= faixa.getLimite()) {

				double ir = (baseCalculo * faixa.getAliquota()) - faixa.getDeducao(); // formula: (base × alíquota) − dedução																						
				return Math.max(ir, 0.0); // não deixa que o valor seja negativo
			}
		}
		return 0.0; // só pra nao dar problema
	}

	public double calcularSalarioLiquido() {
		 
		return salarioBruto - calcularIR(calcularINSS());
	}

	public static void add(Funcionario funcionario) { //deu erro e a lampada pediu pra criar o metodo funcionario aqui
		// TODO Auto-generated method stub
		
	}



	public static void add(String nome, String cpf, String dataNascimento, String salarioBruto2) {
		// TODO Auto-generated method stub
		
	}

}
