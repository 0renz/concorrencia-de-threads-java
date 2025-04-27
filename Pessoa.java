import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Pessoa implements Runnable {
    private static int contadorId = 0; // IDs únicos
    private final int id;
    private final Sexo sexo;
    private final Banheiro banheiro;
    private final Random random = new Random();

    public Pessoa(Sexo sexo, Banheiro banheiro) {
        this.id = ++contadorId;
        this.sexo = sexo;
        this.banheiro = banheiro;
    }

    public Sexo getSexo() {
        return sexo;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Pessoa [id=" + id + ", sexo=" + sexo + "]";
    }

    @Override
    public void run() {
        try {
            while (true) {
                
                // Pessoa tentando usar o banheiro periodicamente
                System.out.println(this + " quer entrar no banheiro."); // Tenta entrar no banheiro
                banheiro.entrar(this);
                int tempoUso = random.nextInt(500) + 100; // entre 100 e 599 ms
                System.out.println("   " + this + " está usando o banheiro por " + tempoUso + "ms...");
                TimeUnit.MILLISECONDS.sleep(tempoUso);
                banheiro.sair(this);

                // Tempo fora do banheiro antes de tentar de novo
                int tempoFora = random.nextInt(2000) + 500; // Espera entre 500 e 2499 ms
                TimeUnit.MILLISECONDS.sleep(tempoFora);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println(this + " foi interrompida.");
        }
    }
}