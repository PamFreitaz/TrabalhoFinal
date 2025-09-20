package empresa;

import java.time.LocalDate;
import java.util.Objects;

public abstract class Pessoa {
	protected String nome;
	protected String cpf;
	protected LocalDate dataNascimento;
	protected int id;

	public Pessoa(String nome, String cpf, LocalDate dataNascimento, int id) {
		super();
		this.nome = nome;
		this.cpf = cpf;
		this.dataNascimento = dataNascimento;
		this.id = id;
	}

	@Override
	public String toString() {
		return "Nome:" + nome + ", cpf:" + cpf + ", data Nascimento:" + dataNascimento + "id:" + id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public LocalDate getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		return Objects.hash(cpf);
	}

	// Aqui é para não duplicar o CPF
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pessoa other = (Pessoa) obj;
		return Objects.equals(cpf, other.cpf);
	}

}
