import java.util.Scanner;

// class User {
//     protected String nome;
//     protected String email;

//     public User(String aNome, String aEmail) {
//         this.nome = aNome;
//         this.email = aEmail;
//     }
// }

// class Gestor extends User {
//     public Gestor(String nome, String email) {
//         super(nome, email);
//     }
// }

public class App {
    public static void main(String[] args) {

        boolean utilizadoresCriados = false;

        if (!utilizadoresCriados) {
            criarGestor();
        }
    }

    public static void criarGestor() {
        Scanner scanner = new Scanner(System.in);
        scanner.close();

        System.out.println("No Gestor");

        System.out.print("Nome do gestor: ");
        String nome = scanner.nextLine();

        System.out.print("Email do gestor: ");
        String email = scanner.nextLine();

        Gestor gestor = new Gestor(nome, email);

        System.out.println("Gestor criado");
    }
}
