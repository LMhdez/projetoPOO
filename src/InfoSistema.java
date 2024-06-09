import java.io.Serializable;

public class InfoSistema implements Serializable {
    private static  int totalExecucoes;
    private static String ultimoUser;
    

    public int getTotalExecucoes() {
        return totalExecucoes;
    }

    public void incrementarExecucoes() {
        InfoSistema.totalExecucoes++;
    }

    public String getUltimoUser() {
        return ultimoUser;
    }

    public void setUltimoUser(String ultimoUser) {
        InfoSistema.ultimoUser = ultimoUser;
    }
}
