public class Medicamento {

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

  Medicamento(
    String aMarca,
    String aLote,
    String aComponenteAct,
    String aDosagem,
    String aStock,
    String aPreco,
    int aAnoFabrico,
    boolean aMedicoNecessario,
    boolean aGenerico
  ) {
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


}
