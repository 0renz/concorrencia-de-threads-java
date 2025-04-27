public class Banheiro {
    private final int capacidadeMaxima = 4;
    private int pessoasDentro = 0;
    private Sexo sexoAtual = Sexo.NENHUM;

    public synchronized void entrar(Pessoa pessoa) throws InterruptedException {
        // Verifica se o banheiro está cheio ou se a pessoa não pode entrar
        while (pessoasDentro == capacidadeMaxima || (sexoAtual != Sexo.NENHUM && sexoAtual != pessoa.getSexo())) {
            System.out.println("    " + pessoa + " ESPERANDO. (Dentro: " + pessoasDentro + "/" + capacidadeMaxima + ", Sexo: " + sexoAtual + ")");
            wait();
        }

        // Se o banheiro estava vazio, define o sexo atual
        if (sexoAtual == Sexo.NENHUM) {
            sexoAtual = pessoa.getSexo();
            System.out.println("    BANHEIRO VAZIO -> Sexo definido para: " + sexoAtual);
        }

        // Incrementa o contador e registra a entrada
        pessoasDentro++;
        System.out.println("    +++ " + pessoa + " ENTROU. (Dentro: " + pessoasDentro + "/" + capacidadeMaxima + ", Sexo: " + sexoAtual + ")");
    }

    public synchronized void sair(Pessoa pessoa) {
        pessoasDentro--;
        System.out.println("    --- " + pessoa + " SAIU.   (Dentro: " + pessoasDentro + "/" + capacidadeMaxima + ", Sexo: " + sexoAtual + ")");

        // Se foi a última pessoa a sair, reseta o sexo do banheiro
        if (pessoasDentro == 0) {
            sexoAtual = Sexo.NENHUM;
            System.out.println("    BANHEIRO VAZIO -> Sexo resetado para: " + sexoAtual);
        }

        // Notifica TODAS as threads que estão esperando (wait()).
        // Se o banheiro ficou vazio, pessoas de AMBOS os sexos podem tentar entrar.
        // Se o banheiro NÃO ficou vazio, mas liberou espaço, pessoas do MESMO sexo podem entrar.
        notifyAll();
    }

    public synchronized int getQuantidadePessoas() {
        return pessoasDentro;
    }

    public synchronized Sexo getSexoAtual() {
        return sexoAtual;
    }

    public synchronized int getCapacidadeMaxima() {
        return capacidadeMaxima;
    }
}