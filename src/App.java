import java.util.Scanner;

public class App {
    public static void main(String[] args) {

        String login;
        String password;
        String nome;
        String email;
        boolean ativo;

        boolean utilizadoresCriados = false;
        GerirUser gerirUser = new GerirUser();

        if (!utilizadoresCriados) {
            login = leDados("Introduza o seu username: ");
            password = leDados("Introduza a sua password: ");
            nome = leDados("Introduza o seu nome: ");
            email = leDados("Introduza o seu email: ");
            ativo = true;

            

            if (gerirUser.criarGestor(login, password, nome, email, ativo)) {
                System.out.println("Gestor criado com sucesso!");
            } else {
                System.out.println("Gestor nao criado!");
            }
        }

        login = leDados("Introduza o seu username: ");
        password = leDados("Introduza a sua password: ");
        nome = leDados("Introduza o seu nome: ");
        email = leDados("Introduza o seu email: ");
        ativo = true;
        String NIF = leDados("Introduza o seu NIF: ");
        String morada = leDados("Introduza a sua morada: ");
        String contato = leDados("Introduza o seu contato: ");


        if (gerirUser.criarCliente(login, password, nome, email, ativo, NIF, morada, contato)) {
            System.out.println("Cliente criado com sucesso!");
        } else {
            System.out.println("Cliente nao criado!");
        }
    }

    private static String leDados(String aMensagem) {
        System.out.println(aMensagem);
        Scanner teclado = new Scanner(System.in);
        String input = teclado.nextLine();
        return input;
    }

}
