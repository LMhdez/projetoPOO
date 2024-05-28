public class Categoria {
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

}
