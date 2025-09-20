package entity;

import java.time.LocalDate;

import empresa.Parentesco;
import empresa.Pessoa;

public class Dependente extends Pessoa {

	private Parentesco parentesco;

	public Dependente(String nome, String cpf, LocalDate dataNascimento, int id, Parentesco parentesco) {
		super(nome, cpf, dataNascimento, id);
		this.parentesco = parentesco;
	}

	public Parentesco getParentesco() {
		return parentesco;
	}

	public void setParentesco(Parentesco parentesco) {
		this.parentesco = parentesco;
	}

}