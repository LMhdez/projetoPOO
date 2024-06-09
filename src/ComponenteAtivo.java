import java.io.Serializable;

public class ComponenteAtivo implements Serializable{
    private String designacao;
    private int codigo;
    private int quantidade;

    ComponenteAtivo(String aDesignacao, int aCodigo, int aQuantidade) {
        this.designacao = aDesignacao;
        this.codigo = aCodigo;
        this.quantidade = aQuantidade;
    }

    public String toString() {
        return "Designacao: " + designacao + " Classificacao: " + codigo + " Quantidade: " + quantidade;
    }
    public String getDesignacao() {
        return designacao;
    }

}