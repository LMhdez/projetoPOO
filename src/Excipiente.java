import java.io.Serializable;

public class Excipiente implements Serializable {
    private String designacao;
    private String classificacao;
    private int quantidade;

    Excipiente(String aDesignacao, String aClassificacao, int aQuantidade) {
        this.designacao = aDesignacao;
        this.classificacao = aClassificacao;
        this.quantidade = aQuantidade;
    }

    public String toString() {
        return "Designacao: " + designacao + " Classificacao: " + classificacao + " Quantidade: " + quantidade;
    }

}