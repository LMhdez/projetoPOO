import java.io.Serializable;

public class Gestor extends User implements Serializable{

    public Gestor(String login, String password, String nome, String email, boolean ativo) {
        super(login, password, nome, email, ativo);
    }
}