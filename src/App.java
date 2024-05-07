import java.util.Scanner;

public class App {
    public static void main(String[] args) {

        String login;
        String password;
        String nome;
        String email;
        boolean ativo;
        String NIF = null;
        String morada = null;
        String contato = null;

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

        String menu = "1-Login\n2-Criar Conta";
        int op = leDadosInt(menu);
        switch (op) {
            case 1:
                login = leDados("Introduza o seu username: ");
                password = leDados("Introduza a sua password: ");

                if (gerirUser.logar(login, password)) {
                    System.out.println("Bem-vindo " + login);
                } else {
                    System.out.println("Login ou password errados!");
                }
                break;
            case 2:
                int tipo = leDadosInt(
                        "Qual é o tipo de conta que gostaria de criar?\n 1-Farmaceutico\n 2-Cliente\n 3-Gestor\n");
                login = leDados("Introduza o seu username: ");
                password = leDados("Introduza a sua password: ");
                nome = leDados("Introduza o seu nome: ");
                email = leDados("Introduza o seu email: ");
                ativo = true;
                if (tipo == 1 || tipo == 2) {
                    NIF = leDados("Introduza o seu NIF: ");
                    morada = leDados("Introduza a sua morada: ");
                    contato = leDados("Introduza o seu contato: ");

                }
                switch (tipo) {
                    case 1:
                        gerirUser.criarFarmaceutico(login, password, nome, email, ativo, NIF, morada, contato);

                        break;
                    case 2:
                        gerirUser.criarCliente(login, password, nome, email, ativo, NIF, morada, contato);
                        break;
                    case 3:
                        gerirUser.criarGestor(login, password, nome, email, ativo);

                    default:
                        System.out.println("Tipo de Conta invalida!");
                        break;
                }

                if (gerirUser.logar(login, password)) {
                    System.out.println("Bem-vindo " + login);
                } else {
                    System.out.println("Login ou password errados!");
                }

                break;
            case 3:
                System.out.println("Adeus");
                System.exit(0);

                break;
            case 4:

                break;
            case 5:

                break;

            default:
                break;
        }

    }

    private static String leDados(String aMensagem) {
        System.out.println(aMensagem);
        Scanner teclado = new Scanner(System.in);
        String input = teclado.nextLine();
        return input;
    }

    private static int leDadosInt(String aMensagem) {
        System.out.println(aMensagem);
        Scanner teclado = new Scanner(System.in);
        int input = teclado.nextInt();
        return input;
    }

}
