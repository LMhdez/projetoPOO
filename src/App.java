import java.util.Scanner;

public class App {
    public static void main(String[] args) {

        String login;
        String password;
        String nome;
        String email;
        boolean ativo;

        boolean utilizadoresCriados = false;

        if (!utilizadoresCriados) {
            login = leDados("Introduza o seu login: ");
            password = leDados("Introduza a sua password: ");
            nome = leDados("Introduza o seu nome: ");
            email = leDados("Introduza o seu email: ");
            ativo = true;
            criarGestor(login, password, nome, email, ativo, utilizadoresCriados);
        }
    }

    private static String leDados(String aMensagem) {
        System.out.println(aMensagem);
        Scanner teclado = new Scanner(System.in);
        String input = teclado.nextLine();
        return input;
    }

    public static Gestor criarGestor(String aLogin, String aPassword, String aNome, String aEmail, boolean aAtivo, boolean aUtilizadoresCriados) {

        Gestor gestor = new Gestor(aLogin, aPassword, aNome, aEmail, aAtivo, aUtilizadoresCriados);

        return gestor;

    }
}
