import java.util.ArrayList;
import java.util.HashMap;
import java.io.Serializable;

public class Medicamentos implements Comparable<Medicamentos>, Serializable {

  private String nome;
  private String marca;
  private String lote;
  private ComponenteAtivo componenteAct;
  // maximo 5 exipientes
  private ArrayList<Excipiente> Excipientes;
  // maximo 3 categorias
  private HashMap<Integer ,Categoria> Categorias;
  private String dosagem;
  private int stock;
  private float preco;
  private int anoFabrico;
  private boolean medicoNecessario;
  private boolean generico;

  public int compareTo(Medicamentos u) {
    return this.nome.compareTo(u.nome);
  }

  public String toString() {
    return "Nome: " + nome + " Marca: " + marca + " Lote: " + lote + " Componente Ativo: " + componenteAct
        + " Dosagem: " + dosagem
        + " Stock: " + stock + " Preco: " + preco + " Ano de Fabrico: " + anoFabrico + " Medico Necessario: "
        + medicoNecessario + " Medicamento Generico: " + generico;
  }

  Medicamentos(
    String nome,
      String aMarca,
      String aLote,
      ComponenteAtivo aComponenteAct,
      String aDosagem,
      int aStock,
      float aPreco,
      int aAnoFabrico,
      boolean aMedicoNecessario,
      ArrayList<Excipiente> aExcipientes,
     HashMap<Integer ,Categoria> aCategorias
      ,
      boolean aGenerico) {
        this.nome = nome;
    this.marca = aMarca;
    this.lote = aLote;
    this.componenteAct = aComponenteAct;
    this.dosagem = aDosagem;
    this.stock = aStock;
    this.preco = aPreco;
    this.anoFabrico = aAnoFabrico;
    this.medicoNecessario = aMedicoNecessario;
    this.Excipientes = aExcipientes;
    this.Categorias = aCategorias;
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

  public String getNome() {
    return this.nome;
  }

  public HashMap<Integer,Categoria> getCategorias() {
    return this.Categorias;
  }

  public ComponenteAtivo getComponenteAct() {
    return this.componenteAct;
  }
  public boolean getGenerico() {
    return this.generico;
  }
}