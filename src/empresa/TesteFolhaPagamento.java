package empresa;

import java.time.LocalDate;
import java.util.ArrayList;       //VALORES TAO SAINDO SEM FORMATAÇÃO DE CASA DECIMAL, PERGUNTAR AO RONI COMO ARRUMAR

public class TesteFolhaPagamento {

    public static void main(String[] args) {
        
        ArrayList<Funcionario> funcionarios = new ArrayList<>();

        Funcionario funcionario1 = new Funcionario("Pam", "11611155508", LocalDate.of(1992,4,25), 2000.00);
        Funcionario funcionario2 = new Funcionario("Raquel", "01256567545", LocalDate.of(1985,6,10), 8000.00);
        Funcionario funcionario3 = new Funcionario("Maria", "01234567890", LocalDate.of(1990,3,15), 3500.00);

        try {
            Dependente dep1 = new Dependente("Arthur", "11144455501", LocalDate.of(2009,8,24), Parentesco.OUTROS);
            funcionario1.adicionarDependente(dep1);

            Dependente dep2 = new Dependente("Lucas", "22233344455", LocalDate.of(2010,5,12), Parentesco.FILHO);
            funcionario2.adicionarDependente(dep2);
            

        } catch (Exception e) {
            e.printStackTrace();
        }
        
        funcionarios.add(funcionario1);
        funcionarios.add(funcionario2);
        funcionarios.add(funcionario3);

        for (Funcionario f : funcionarios) {
            double inss = f.calcularINSS();
            double ir = f.calcularIR(inss);
            double salarioLiquido = f.getSalarioBruto() - inss - ir;

            //funcionario
            System.out.println(f.getNome() + ";" 
                             + f.getCpf() + ";" 
                             + inss + ";" 
                             + ir + ";" 
                             + salarioLiquido);
            //dependentes
            for (Dependente d : f.getDependentes()) {
                System.out.println(d.getNome() + ";" 
                                 + d.getCpf() + ";" 
                                 + d.getDataNascimento() + ";" 
                                 + d.getParentesco());
            }
            //linha em branco separando os funcionarios e dependentes de cada um
            System.out.println();
        }
    }
}