import java.io.Serializable;

public class ComponenteAtivo implements Serializable{
    private String designacao;
    private String classificacao;
    private int quantidade;

    ComponenteAtivo(String aDesignacao, String aClassificacao, int aQuantidade) {
        this.designacao = aDesignacao;
        this.classificacao = aClassificacao;
        this.quantidade = aQuantidade;
    }

    public String toString() {
        return "Designacao: " + designacao + " Classificacao: " + classificacao + " Quantidade: " + quantidade;
    }
    public String getDesignacao() {
        return designacao;
    }

}