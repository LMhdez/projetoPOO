import java.io.Serializable;
public class Categoria implements Serializable {
    String designacao;
    String classificacao;
    
    String fornecedor;

    Categoria(String aDesignacao, String aClassificacao, String aFornecedor) {
        this.designacao = aDesignacao;
        this.classificacao = aClassificacao;
        
        this.fornecedor = aFornecedor;

    }

    public String toString() {
        return "Designacao: " + designacao + " Classificacao: " + classificacao + " Fornecedor: "
                + fornecedor;
    }
    public String getDesignacao() {
        return designacao;
    }


}
