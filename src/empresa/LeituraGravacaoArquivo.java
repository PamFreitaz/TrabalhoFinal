package empresa;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import persintence.FuncionarioDao;
import persintence.DependenteDao;
import persintence.FolhaPagamentoDao;
import entity.Dependente;
import empresa.Parentesco;
import empresa.DependenteException;
import entity.Funcionario;

public class LeituraGravacaoArquivo {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Informe o local do arquivo:\n");
		String nomeArquivo = sc.nextLine();

		// LEITURA DO ARQUIVO
		File file = new File(nomeArquivo);
		try {
			Scanner sc2 = new Scanner(file);
			Set<Funcionario> funcionarios = new HashSet<>();
			Funcionario ultimoFuncionario = null; // guarda o ultimo funcionario lido pra quando vier o dependente ele
													// saber que o dependente pertence ao ultimoFuncionario lido.

			/*
			 *********************** ADICIONADO PARA REJEIÇÃO ************** hugo fez
			 */
			Set<String> cpfsUsados = new HashSet<>(); // armazena CPFs já aceitos
			Set<String> linhasRejeitadas = new HashSet<>(); // armazena linhas rejeitadas por duplicidade

			/****************************************************************/

			while (sc2.hasNext()) {
				String linha = sc2.nextLine();
				if (!linha.isEmpty()) {

					String[] dados = linha.split(";");
					String cpf = dados[1]; // posição 1 sempre é CPF

					/*
					 *********************** VERIFICA DUPLICADO ********************
					 */
					if (!cpfsUsados.add(cpf)) {
						linhasRejeitadas.add(linha); // linha rejeitada
						continue; // pula processamento
					}
					/****************************************************************/

					try {
						// tenta ler a pessoa como funcionario usando o 'dados[3]' que é o salario, pra
						// diferenciar de dependente, pq dependente nao tem salario e sim o enum de
						// parentesco
						double salario = Double.parseDouble(dados[3]);
						Funcionario f = new Funcionario(dados[0], dados[1], LocalDate.parse(dados[2]), // LocalDate.parse
																										// muda o
																										// LocalDate
																										// declarado na
																										// data de
																										// nascimento la
																										// na classe
																										// Funcionario
																										// para String
								salario);
						funcionarios.add(f);
						ultimoFuncionario = f;

						FuncionarioDao funcionarioDao = new FuncionarioDao();
						funcionarioDao.inserir(f); // insere no banco e gera o ID do funcionario

					} catch (NumberFormatException e) {
						// Se ele nao achar um double(salario no dados[3]) da linha do arquivo de
						// entrada, o codigo vai entender que aquela pessoa é um dependente.
						if (ultimoFuncionario != null) {
							try {
								String parStr = dados[3].trim().toUpperCase(); // faz a string corresponder ao enum de
																				// parentesco
								Parentesco parentesco = Parentesco.valueOf(parStr);

								Dependente d = new Dependente(dados[0], dados[1], LocalDate.parse(dados[2]),
										parentesco); // parentesco e nao o double salario
								ultimoFuncionario.getDependentes().add(d); // adiciona o dependente dentor do ultimo
																			// funcionario lido.

								DependenteDao dependenteDao = new DependenteDao();
								dependenteDao.inserir(d, ultimoFuncionario); // insere os dependentes no banco

							} catch (IllegalArgumentException e1) { // se o valueOf falhou o parentesco ta invalido
								System.err.println("Parentesco inválido no arquivo: '" + dados[3] + "'");
							} catch (DependenteException e2) { // validação do Dependente (Se é maior de 18 anos ou nao)
								System.err.println("Erro ao criar dependente: " + e2.getMessage());
							}
						} else {
							System.err.println("Funcionario não encontrado." + linha);
						}
					}
				}
			}

			// imprime funcionário
			System.out.println("----Leitura Arquivo----");
			for (Funcionario f : funcionarios) {
				System.out.printf("%s;%s;%.2f;%.2f;%.2f%n", // pra formatação ficar certa
						f.getNome(), f.getCpf(), f.calcularINSS(), f.calcularIR(f.calcularINSS()),
						f.calcularSalarioLiquido());
				// imprime dependente
				for (Dependente d : f.getDependentes()) {
					System.out.printf("%s;%s;%s;%s%n", // pra formatação ficar certa
							d.getNome(), d.getCpf(), d.getDataNascimento(), d.getParentesco());
				}
				System.out.println("");

				FolhaPagamentoDao folhaDao = new FolhaPagamentoDao();

				empresa.FolhaPagamento folha = new empresa.FolhaPagamento(0, f, LocalDate.now(), f.calcularINSS(),
						f.calcularIR(f.calcularINSS()), f.calcularSalarioLiquido());

				folhaDao.inserir(folha, f);
				System.out.println("Folha de pagamento inserida para o funcionário: " + f.getNome());
				// insere no banco
				folhaDao.inserir(folha, f);

			}

			// GRAVAÇÃO DO ARQUIVO
			System.out.println("----Gravação Arquivo----");
			try (FileWriter caminho = new FileWriter("ArquivoSaida.csv");
					PrintWriter gravar = new PrintWriter(caminho)) {

				for (Funcionario f : funcionarios) { // grava funcionário
					gravar.printf("%s;%s;%.2f;%.2f;%.2f%n", f.getNome(), f.getCpf(), f.calcularINSS(),
							f.calcularIR(f.calcularINSS()), f.calcularSalarioLiquido());

					for (Dependente d : f.getDependentes()) { // grava dependente
						gravar.printf("%s;%s;%s;%s%n", d.getNome(), d.getCpf(), d.getDataNascimento(),
								d.getParentesco());
					}
					gravar.println("");
				}

				System.out.println("Gerando Arquivo de Saida.");
				System.out.println("Arquivo de saida '.CSV' gerado com sucesso!");
			} catch (IOException e1) {
				System.err.println("Arquivo de saída com problemas: " + e1.getMessage());
			}

			/*
			 *********************** GRAVAÇÃO REJEITADOS ******************
			 */

			try (FileWriter caminho = new FileWriter("Rejeitados.csv"); PrintWriter gravar = new PrintWriter(caminho)) {

				for (String linhaRejeitada : linhasRejeitadas) {
					gravar.println(linhaRejeitada);
				}
				System.out.println(
						"Foram encontradas duplicidades nas informações.\nInformações duplicadas serão rejeitadas");
				System.out.println("Arquivo 'Rejeitados.csv' gerado com sucesso!");
			} catch (IOException e1) {
				System.err.println("Erro ao gerar arquivo Rejeitados: " + e1.getMessage());
			}

			sc2.close();
		} catch (FileNotFoundException e) {
			System.err.println("Arquivo não encontrado.");
		}
	}
}
