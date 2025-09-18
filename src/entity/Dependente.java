package entity;

import java.time.LocalDate;

import empresa.DependenteException;
import empresa.Parentesco;
import empresa.Pessoa;

public class Dependente extends Pessoa {
	
	private Parentesco parentesco;

	public Dependente(String nome, String cpf, LocalDate dataNascimento, Parentesco parentesco)throws DependenteException {
		
		super(nome, cpf, dataNascimento);
		this.parentesco = parentesco;
		if(LocalDate.now().minusYears(18).isBefore(dataNascimento)) {
		}else {
			throw new DependenteException("Dependente tem que ser menor de 18 anos!");
		}
					
	}

	public Parentesco getParentesco() {
		return parentesco;
	}

	public void setParentesco(Parentesco parentesco) {
		this.parentesco = parentesco;
	}
	
		
}
