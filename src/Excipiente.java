public class Excipiente {
    String designacao;
    String classificacao;
    int quantidade;

    Excipiente(String aDesignacao, String aClassificacao, int aQuantidade) {
        this.designacao = aDesignacao;
        this.classificacao = aClassificacao;
        this.quantidade = aQuantidade;
    }

    public String toString() {
        return "Designacao: " + designacao + " Classificacao: " + classificacao + " Quantidade: " + quantidade;
    }

}