package entity;

public enum FaixaIR {
    FAIXA1(2259.00, 0.0,   0.00),   //até 2259.00 tem isenção do desconto, ent~ao tudo zerado
    FAIXA2(2826.65, 0.075, 169.44), //de 2259.21 até 2826.65, alq 7.5, desconto de 169.44 
    FAIXA3(3751.05, 0.15,  381.44), //de 2826.66 até 3751.05, alq 15, desconto de 381.44 
    FAIXA4(4664.68, 0.225, 662.77), //de 3751.06 até 4664.68, alq 22.5, desconto de 662.77 
    FAIXA5(Double.MAX_VALUE, 0.275, 896.00);// acima de 4664.68, alq 27.5, desconto de 896.00 (Escolhi usar o Double.MAX_VALUE para usar o "valor maximo" como um limite infinito de salario, ja que nao tem mais um teto depois dali)

	//Valor fixo por dependente
    public static final double VALOR_DEPENDENTE = 189.59;
	
 // Atributos de cada faixa
    private final double limite;   //valor maximo da faixa
    private final double aliquota; //% de imposto aplicado
    private final double deducao;  //Quantidade fixa do desconto 

//Construtor    
    FaixaIR(double limite, double aliquota, double deducao) {
        this.limite = limite;
        this.aliquota = aliquota;
        this.deducao = deducao;
    }

 //getters
    public double getLimite() {
        return limite;
    }

    public double getAliquota() {
        return aliquota;
    }

    public double getDeducao() {
        return deducao;
    }

}