package arquivo; 

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import empresa.Dependente;
import empresa.Parentesco;
import empresa.DependenteException;
import entity.Funcionario;

public class LeituraGravacaoArquivo {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Informe o local do arquivo:\n");
        String nomeArquivo = sc.nextLine();
                
        //LEITURA DO ARQUIVO
        File file = new File(nomeArquivo);
        try {
            Scanner sc2 = new Scanner(file);
            Set<Funcionario> funcionarios = new HashSet<>();
            Funcionario ultimoFuncionario = null;  
            

            /*
             ***********************  ADICIONADO PARA REJEIÇÃO  **************
             */
            Set<String> cpfsUsados = new HashSet<>();       // armazena CPFs já aceitos
            Set<String> linhasRejeitadas = new HashSet<>(); // armazena linhas rejeitadas por duplicidade
            
            /****************************************************************/

            while (sc2.hasNext()){
                String linha = sc2.nextLine();
                if (!linha.isEmpty()) {

                    String[] dados = linha.split(";");
                    String cpf = dados[1]; // posição 1 sempre é CPF

                    /*
                     ***********************  VERIFICA DUPLICADO ********************
                     */
                    if (!cpfsUsados.add(cpf)) {
                        linhasRejeitadas.add(linha); // linha rejeitada
                        continue; // pula processamento
                    }
                    /****************************************************************/

                    try {
                        // tenta ler como funcionário
                        double salario = Double.parseDouble(dados[3]);
                        Funcionario f = new Funcionario(
                                dados[0],
                                dados[1], 
                                LocalDate.parse(dados[2]),
                                salario);
                        funcionarios.add(f);      
                        ultimoFuncionario = f;

                    } catch (NumberFormatException e) { 
                        // se não é salário → deve ser dependente
                        if (ultimoFuncionario != null) {
                            try{
                                String parStr = dados[3].trim().toUpperCase(); 
                                Parentesco parentesco = Parentesco.valueOf(parStr);

                                Dependente d = new Dependente(
                                    dados[0],
                                    dados[1],
                                    LocalDate.parse(dados[2]),
                                    parentesco);
                                ultimoFuncionario.getDependentes().add(d);

                            } catch (IllegalArgumentException e1) {
                                System.err.println("Parentesco inválido no arquivo: '" + dados[3] + "'");
                            } catch (DependenteException e2) {
                                System.err.println("Erro ao criar dependente: " + e2.getMessage());
                            }
                        } else {
                            System.err.println("Funcionario não encontrado." + linha);
                        }
                    }
                }       
            }

            // IMPRESSÃO NO CONSOLE
            System.out.println("----Leitura Arquivo----");
            for (Funcionario f : funcionarios) {
                System.out.printf(
                       "%s;%s;%.2f;%.2f;%.2f%n",
                       f.getNome(),
                       f.getCpf(),
                       f.calcularINSS(),
                       f.calcularIR(f.calcularINSS()),
                       f.calcularSalarioLiquido()
                    );

                for (Dependente d : f.getDependentes()) {
                    System.out.printf(
                       "%s;%s;%s;%s%n",
                       d.getNome(),
                       d.getCpf(),
                       d.getDataNascimento(),
                       d.getParentesco()
                        );
                }System.out.println("");
           }
            
            //GRAVAÇÃO DO ARQUIVO
            System.out.println("----Gravação Arquivo----");
            try(FileWriter caminho = new FileWriter("ArquivoSaida.csv");
                    PrintWriter gravar = new PrintWriter(caminho)){
                    
                for (Funcionario f : funcionarios) {
                    gravar.printf(
                           "%s;%s;%.2f;%.2f;%.2f%n",
                           f.getNome(),
                           f.getCpf(),
                           f.calcularINSS(),
                           f.calcularIR(f.calcularINSS()),
                           f.calcularSalarioLiquido()
                        );

                    for (Dependente d : f.getDependentes()) {
                        gravar.printf(
                           "%s;%s;%s;%s%n",
                           d.getNome(),
                           d.getCpf(),
                           d.getDataNascimento(),
                           d.getParentesco()
                            );
                    }gravar.println("");
            }
            
                 System.out.println("Gerando Arquivo de Saida.");
                 System.out.println("Arquivo de saida '.CSV' gerado com sucesso!");
            } catch (IOException e1) {
                System.err.println("Arquivo de saída com problemas: " + e1.getMessage());
            }   

            /*
             ***********************  GRAVAÇÃO REJEITADOS  ******************
             */
            
            try (FileWriter caminho = new FileWriter("Rejeitados.csv");
                 PrintWriter gravar = new PrintWriter(caminho)) {

                for (String linhaRejeitada : linhasRejeitadas) {
                    gravar.println(linhaRejeitada);
                }

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
