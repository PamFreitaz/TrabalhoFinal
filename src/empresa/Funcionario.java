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

// metodo antigo do Hugo pra calcular INSS	
//	public double calcularINSS(double retorno) {
//		//Cinss = (salarioBruto-ALinss) - Dinss
//	    if (salarioBruto <= 1518.00) {
//	        return retorno = (salarioBruto * 0.075) - 0;
//	    } else if (salarioBruto <= 2793.88) {
//	        return retorno = (salarioBruto * 0.09) - 22.77;
//	    } else if (salarioBruto <= 4190.83) {
//	        return retorno = (salarioBruto * 0.12) - 106.59;
//	    } else if (salarioBruto <= 8157.41) {
//	        return retorno = (salarioBruto * 0.14) - 190.40;
//	    } else {
//	        return retorno = (8157.41 * 0.14) - 190.40;
//	    }   
//	}

	
//calcula o INSS vindo do enum FaixaINSS
		public double calcularINSS() {		    
		    for (FaixaINSS faixa : FaixaINSS.values()) { //ve as faixas do enum FaixaINSS		       
		        if (salarioBruto <= faixa.getLimite()) { //se o valor da faixa for maior ou igual usa a formula
		            return (salarioBruto * faixa.getAliquota()) - faixa.getDeducao(); //formula: (salário × alíquota) − dedução
		        }
		    }
		    
		    FaixaINSS ultimaFaixa = FaixaINSS.FAIXA4; //se o salário bruto for maior que 8157.41 aplica o valor maximo da última faixa
		    return (ultimaFaixa.getLimite() * ultimaFaixa.getAliquota()) - ultimaFaixa.getDeducao();
		}
	
	//calcula o IR do enum FaixaIR
	public double calcularIR(double valorINSS) {	    
	    double baseCalculo = salarioBruto  //salário bruto - INSS - dedução por dependentes(usando o size, pra pegar quantos dependentes cada funcionario tem)
	                       - valorINSS 
	                       - (dependentes.size() * FaixaIR.VALOR_DEPENDENTE);
	    //se a base for menor ou igual a 0, nao tem imposto
	    if (baseCalculo <= 0) {
	        return 0.0;
	    }    
	    for (FaixaIR faixa : FaixaIR.values()) { //percorre as faixas do enum FaixaIR
	        //qd encontra a faixa que o limite maior ou igual ao valores da FaixaIR aplica a formula
	        if (baseCalculo <= faixa.getLimite()) {
	            
	            double ir = (baseCalculo * faixa.getAliquota()) - faixa.getDeducao(); //formula: (base × alíquota) − dedução
	            	return Math.max(ir, 0.0); //não deixa que o valor seja negativo
	        }
	    }  
	    		return 0.0; //só pra nao dar problema
}

	//Método para adicionar dependente
	public void adicionarDependente(Dependente dependete) throws DependenteException {
	    //validação de CPF duplicado
	    for (Dependente d : dependentes) {
	        if (d.getCpf().equals(dependete.getCpf())) {
	            throw new DependenteException("CPF já cadastrado!");
	        }
	    }
	    // se passou na validação, adiciona o novo dependente
	    this.dependentes.add(dependete);
	}

		
	
	
	

}
