import java.io.Serializable;

public class InfoSistema implements Serializable {
    private static  int totalExecucoes;
    private static String ultimoUsuario;
    

    public int getTotalExecucoes() {
        return totalExecucoes;
    }

    public void incrementarExecucoes() {
        InfoSistema.totalExecucoes++;
    }

    public String getUltimoUsuario() {
        return ultimoUsuario;
    }

    public void setUltimoUsuario(String ultimoUsuario) {
        InfoSistema.ultimoUsuario = ultimoUsuario;
    }
}
