import java.util.Arrays;
import java.util.Random;

public class Rainhas {

    public static int objetivo(int[] estado) {
        // Retorna o número de pares de rainhas que se atacam
        int ataque = 0;
        for (int i = 0; i < estado.length; i++) {
            for (int j = i + 1; j < estado.length; j++) {
                if (estado[i] == estado[j] || Math.abs(estado[i] - estado[j]) == j - i) {
                    ataque++;
                }
            }
        }
        return ataque;
    }

    public static int[] subidaEncosta(int reinicios) {
        // Faz a busca subida da encosta com reinício aleatório
        Random rand = new Random();
        for (int r = 0; r < reinicios; r++) {
            int[] estado = new int[8];
            for (int i = 0; i < estado.length; i++) {
                estado[i] = rand.nextInt(8);
            }
            int custo = objetivo(estado);
            while (true) {
                int[][] vizinhos = new int[8 * 7][8];
                int k = 0;
                for (int col = 0; col < 8; col++) {
                    for (int lin = 0; lin < 8; lin++) {
                        if (estado[col] != lin) {
                            int[] vizinho = Arrays.copyOf(estado, 8);
                            vizinho[col] = lin;
                            vizinhos[k++] = vizinho;
                        }
                    }
                }
                if (k == 0) {
                    break;
                }
                int[] vizinhoCustos = new int[k];
                for (int i = 0; i < k; i++) {
                    vizinhoCustos[i] = objetivo(vizinhos[i]);
                }
                int melhorCusto = Integer.MAX_VALUE;
                int melhorVizinho = -1;
                for (int i = 0; i < k; i++) {
                    if (vizinhoCustos[i] < melhorCusto) {
                        melhorCusto = vizinhoCustos[i];
                        melhorVizinho = i;
                    }
                }
                if (melhorCusto >= custo) {
                    break;
                }
                estado = vizinhos[melhorVizinho];
                custo = melhorCusto;
            }
            if (custo == 0) {
                return estado;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        // Exemplo de uso:
        int[] solucao = subidaEncosta(1000);
        if (solucao != null) {
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if (solucao[j] == i) {
                        System.out.print("Q ");
                    } else {
                        System.out.print(". ");
                    }
                }
                System.out.println();
            }
        }
        int execucoes = 1000;
        int rebound = 100;
        int total = 0;

        for (int i = 0; i < execucoes; i++) {
            int[] solucao1 = subidaEncosta(rebound);
            if (solucao != null) {
                int reinicios = solucao[solucao.length - 1];
                total += reinicios;
            }
        }
        System.out.println("Número médio de reinicios: " + (double) total / execucoes);
    }
}
