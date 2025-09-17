package empresa;

import java.time.LocalDate;
import java.util.ArrayList;

public class Funcionario extends Pessoa{
	private double salarioBruto;
	private double descontoInss;
	private double descontoIr;
	private ArrayList<Dependente> dependentes;
		
	
	public Funcionario(String nome, String cpf, LocalDate dataNascimento, double salarioBruto) {
		super(nome, cpf, dataNascimento);
		this.salarioBruto = salarioBruto;
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
	
	//Método para adicionar dependente
	public void adicionarDependente(Dependente dependente) throws DependenteException {
		//validação de CPF duplicado
		for (Dependente d : dependentes) {
			//comparando o CPF do dependente atual com o CPF do novo dependente
			if(d.getCpf().equals(dependente.getCpf())) {
				throw new DependenteException("\nCPF de dependente já cadastrado!");
				
			}
		}
		//Passando na validação, adiciona o novo dependente
		this.dependentes.add(dependente);
	}
		
	
	

}
