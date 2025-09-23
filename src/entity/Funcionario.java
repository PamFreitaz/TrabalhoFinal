package entity;

import java.time.LocalDate;
import java.util.ArrayList;

import empresa.Calculadora;
import empresa.FaixaINSS;
import empresa.FaixaIR;
import empresa.Pessoa;

public class Funcionario extends Pessoa implements Calculadora {
	private double salarioBruto;
	private double descontoInss;
	private double descontoIr;
	private ArrayList<Dependente> dependentes;

	public Funcionario(String nome, String cpf, LocalDate dataNascimento, int id, double salarioBruto) {
	    super(nome, cpf, dataNascimento, id);
	    this.salarioBruto = salarioBruto;
	    this.dependentes = new ArrayList<>(); // garante que não fique vazio
	}

	//  construtor sem id para leitura do arquivo csv
	public Funcionario(String nome, String cpf, LocalDate dataNascimento, double salarioBruto) {
	    super(nome, cpf, dataNascimento); // chama o construtor novo de Pessoa sem o id
	    this.salarioBruto = salarioBruto;
	    this.dependentes = new ArrayList<>();
	}

	
	@Override
	public String toString() {
		return "Funcionario [salarioBruto=" + salarioBruto + ", descontoInss=" + descontoInss + ", descontoIr="
				+ descontoIr + ", dependentes=" + dependentes + "]";
	}

	public double getSalarioBruto() {
		return salarioBruto;
	}

	public void setSalarioBruto(double salarioBruto) {
		this.salarioBruto = salarioBruto;
	}

	public double getDescontoInss() {
		return descontoInss;
	}

	public void setDescontoInss(double descontoInss) {
		this.descontoInss = descontoInss;
	}

	public double getDescontoIr() {
		return descontoIr;
	}

	public void setDescontoIr(double descontoIr) {
		this.descontoIr = descontoIr;
	}

	public ArrayList<Dependente> getDependentes() {
		return dependentes;
	}

	public void setDependentes(ArrayList<Dependente> dependentes) {
		this.dependentes = dependentes;
	}

	@Override
	public double calcularINSS() {
		for (FaixaINSS faixa : FaixaINSS.values()) { // ve as faixas do enum FaixaINSS
			if (salarioBruto <= faixa.getLimite()) { // se o valor da faixa for maior ou igual usa a formula
				return (salarioBruto * faixa.getAliquota()) - faixa.getDeducao(); // formula: (salário × alíquota) −
																					// dedução
			}
		}

		FaixaINSS ultimaFaixa = FaixaINSS.FAIXA4; // se o salário bruto for maior que 8157.41 aplica o valor maximo da
													// última faixa
		return (ultimaFaixa.getLimite() * ultimaFaixa.getAliquota()) - ultimaFaixa.getDeducao();
	}

	@Override
	public double calcularIR(double valorINSS) {
		double baseCalculo = salarioBruto // salário bruto - INSS - dedução por dependentes(usando o size, pra pegar
											// quantos dependentes cada funcionario tem)
				- valorINSS - (dependentes.size() * FaixaIR.VALOR_DEPENDENTE);
		// se a base for menor ou igual a 0, nao tem imposto
		if (baseCalculo <= 0) {
			return 0.0;
		}
		for (FaixaIR faixa : FaixaIR.values()) { // percorre as faixas do enum FaixaIR
			// qd encontra a faixa que o limite maior ou igual ao valores da FaixaIR aplica
			// a formula
			if (baseCalculo <= faixa.getLimite()) {

				double ir = (baseCalculo * faixa.getAliquota()) - faixa.getDeducao(); // formula: (base × alíquota) −
																						// dedução
				return Math.max(ir, 0.0); // não deixa que o valor seja negativo
			}
		}
		return 0.0; // só pra nao dar problema
	}

	@Override
	public double calcularSalarioLiquido() {
		return salarioBruto - calcularIR(calcularINSS());
	}
}