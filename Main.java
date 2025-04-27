import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        System.out.println("Iniciando a simulação da Padaria do Seu Joaquim!");

        Banheiro banheiro = new Banheiro();
        List<Thread> threads = new ArrayList<>();
        int totalPessoas = 15; // threads

        for (int i = 0; i < totalPessoas; i++) {
            Sexo sexoPessoa = (i % 2 == 0) ? Sexo.MASCULINO : Sexo.FEMININO; // Alternado
            Pessoa pessoa = new Pessoa(sexoPessoa, banheiro);
            Thread threadPessoa = new Thread(pessoa, "Thread-" + pessoa.getId() + "-" + sexoPessoa.name().substring(0,1));
            threads.add(threadPessoa);
        }

        System.out.println("\nIniciando as threads das pessoas...");
        for (Thread t : threads) { // Inicia as threads
            t.start();
        }
        
        System.out.println("\nSimulação em andamento (pressione Ctrl+C para parar)...");
    }
}