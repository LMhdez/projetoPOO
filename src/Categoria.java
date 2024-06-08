import java.io.Serializable;
public class Categoria implements Serializable {
    String designacao;
    String classificacao;
    int codigo;
    String fornecedor;

    Categoria(String aDesignacao, String aClassificacao, int aCodigo, String aFornecedor) {
        this.designacao = aDesignacao;
        this.classificacao = aClassificacao;
        this.codigo = aCodigo;
        this.fornecedor = aFornecedor;

    }

    public String toString() {
        return "Designacao: " + designacao + " Classificacao: " + classificacao + " Codigo: " + codigo + " Fornecedor: "
                + fornecedor;
    }
    public String getDesignacao() {
        return designacao;
    }
    public int getCodigo() {
        return codigo;
    }

}
