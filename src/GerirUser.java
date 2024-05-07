import java.util.ArrayList;

public class GerirUser {

    private ArrayList<User> lista;

    public GerirUser() {
        lista = new ArrayList<User>();
    }

    public boolean adicionaUser(User aUser) {
        if (lista != null)
            return lista.add(aUser);
        return false;
    }

    
}
