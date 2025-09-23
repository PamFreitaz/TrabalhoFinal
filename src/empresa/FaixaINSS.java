package empresa;

public enum FaixaINSS {
    FAIXA1(1518.00, 0.075, 0.00),   //até 1518.00, alq 7.5%, sem desconto
    FAIXA2(2793.88, 0.09, 22.77),   //de 1518.01 até 2793.88, alq 9%, desconto de 22.77
    FAIXA3(4190.83, 0.12, 106.59),  //de 2793.89 até 4190.83, alq 12%, desconto de 106.59
    FAIXA4(8157.41, 0.14, 190.40);  //de 4190.84 até 8157.41, alq 14%, desconto de 190.40
    //acima de 8157.41 não existe faixa maior, então usa o limite da FAIXA4

  
    private final double limite;   //valor máximo da faixa
    private final double aliquota; //% de contribuição aplicada
    private final double deducao;  //parcela fixa a descontar

    //construtor
    FaixaINSS(double limite, double aliquota, double deducao) {
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