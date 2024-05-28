public class Medicamentos {

  private String nome;
  private String marca;
  private String lote;
  private String componenteAct;
  private String dosagem;
  private int stock;
  private float preco;
  private int anoFabrico;
  private boolean medicoNecessario;
  private boolean generico;

  public String toString() {
    return "Nome: " + nome + " Marca: " + marca + " Lote: " + lote + " Componente Ativo: " + componenteAct
        + " Dosagem: " + dosagem
        + " Stock: " + stock + " Preco: " + preco + " Ano de Fabrico: " + anoFabrico + " Medico Necessario: "
        + medicoNecessario + " Medicamento Generico: " + generico;
  }

  Medicamentos(
      String aMarca,
      String aLote,
      String aComponenteAct,
      String aDosagem,
      int aStock,
      float aPreco,
      int aAnoFabrico,
      boolean aMedicoNecessario,
      boolean aGenerico) {
    this.marca = aMarca;
    this.lote = aLote;
    this.componenteAct = aComponenteAct;
    this.dosagem = aDosagem;
    this.stock = aStock;
    this.preco = aPreco;
    this.anoFabrico = aAnoFabrico;
    this.medicoNecessario = aMedicoNecessario;
    this.generico = aGenerico;
  }
  public boolean setStock(int aStock) {
    if (aStock >= 0) {
      this.stock = aStock;
      return true;
    }
    return false;
  }
  public int getStock() {
    return this.stock;
  }


}
