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

            if (criarGestor(login, password, nome, email, ativo)!=null) {
                System.out.println("Gestor criado com sucesso!");
            }
            else{
                System.out.println("Gestor não criado!");
            }
        }
        
       
    }

    private static String leDados(String aMensagem) {
        System.out.println(aMensagem);
        Scanner teclado = new Scanner(System.in);
        String input = teclado.nextLine();
        return input;
    }

    public static Gestor criarGestor(String aLogin, String aPassword, String aNome, String aEmail, boolean aAtivo,
            boolean aUtilizadoresCriados) {

        Gestor gestor = new Gestor(aLogin, aPassword, aNome, aEmail, aAtivo);
        return gestor;

    }
}
