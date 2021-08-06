package sudoko;

/**
 *
 * @author Matheus Baltazar Ramos
 * Jogos Digitais :)
 * declaro que:

todas as respostas são fruto de nosso próprio trabalho,

não copiamos respostas de colegas externos à equipe,

não disponibilizamos nossas respostas para colegas externos à equipe e

não realizamos quaisquer outras atividades desonestas para nos beneficiar ou prejudicar outros.
 */
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.Math;

public class SuDoko {
    public static void main(String[] args) throws IOException {

        game();
    }
    public static void game() throws FileNotFoundException, IOException {
        Scanner ler = new Scanner(System.in);
        char[][] tabuleiro = initialize();
        char[][] tabuleiroFixo = initialize();
        boolean ativo = true;
        int limpar = 0, escolha, linha, coluna, numero;
        String nome;
        System.out.print("Olá Bem vindo ao Sudoku do Balta!!! \nMe fale seu nome: ");
        nome = ler.next();//guarda o nome do jogador para o avisar
        print(tabuleiro);
        //GAME LOOP
        while (ativo) {
            if (status(tabuleiro) == true) {//caso tenha terminado o jogo!
                System.out.println("Muito BOM "+ nome +" !!!");
                System.out.println("Você e Incrivel!!!");
                ativo = false;
            } else {//o pergunta o que fara agora no jogo ate completar
                System.out.println(nome+"! você vai quere Limpar ou Adicionar um número?");
                System.out.println("1. Limpar"
                        + "\n2. Adicionar");

                escolha = ler.nextInt();

                if (escolha < 1 || escolha > 2) {//caso o usuario tenter escolher outra coisa
                    System.out.println("Opção não encontrada. Tente de novo.");

                    System.out.println(nome+"! você vai quere Limpar ou Adicionar um número?");
                    System.out.println("1. Limpar"
                            + "\n2.Adicionar");

                    escolha = ler.nextInt();
                } else {
                    switch (escolha) {
                        case 1://para limpar uma '_' do tabuleiro
                            numero = '_';
                            limpar = 1;
                            System.out.print(nome+"! Qual Linha você quer limpar:");
                            linha = ler.nextInt();
                            System.out.print(nome+"! Qual Coluna você quer limpar:");
                            coluna = ler.nextInt();
                            if (step(tabuleiro, tabuleiroFixo, linha, coluna, (char) numero, limpar) == -1) {
                                System.out.println("Ação invalida!");
                            } else {
                                print(tabuleiro);
                            }
                            break;
                        case 2://para colocar um numero no tabuleiro
                            System.out.print("Presado "+nome+" escolha a Linha:");
                            linha = ler.nextInt();
                            System.out.print("Escolha a Coluna:");
                            coluna = ler.nextInt();
                            System.out.print("Escolha o Numero:");
                            numero = ler.next().charAt(0);

                            switch (step(tabuleiro, tabuleiroFixo, linha, coluna, (char) numero, limpar)) {
                                case 0:
                                    print(tabuleiro);
                                    System.out.println("Querido "+ nome +" Ja tem um numero nessa linha coluna ou região!!!");
                                    break;
                                case 1:
                                    tabuleiro[linha][coluna] = (char) numero;
                                    print(tabuleiro);
                                    System.out.println(nome+"! Foi uma jogada permitida! Continue!");
                                    break;
                                case -1:
                                    print(tabuleiro);
                                    System.out.println(nome +", voce tentou colocar em um lugar que não pode!");
                                    System.out.println("tente de novo");
                                    break;
                            }

                            break;
                    }
                }
            }

        }

    }
    public static char[][] initialize() throws FileNotFoundException, IOException {
        //função para iniciar o tabuleiro
        FileReader le = new FileReader("tabuleiro.txt");
        BufferedReader leBuffer = new BufferedReader(le);

        char tabuleiro[][] = new char[9][9];

        for (int i = 0; i < 9; i++) {
            String grade = leBuffer.readLine();
            String vetorString[] = grade.split(" ");
            for (int j = 0; j < vetorString.length; j++) {
                tabuleiro[i][j] = vetorString[j].charAt(0);
            }//aqui e lido o tabuleiro 9x9 e imprimido e retornado para o game
        }
        //print(tabuleiro);
        return tabuleiro;
    }
    public static void print(char tabuleiro[][]) {
        //Aqui printa o tabuleiro para o jogador poder ver.
        System.out.println("  |0  1  2| 3  4  5| 6  7  8");
        System.out.println("-----------------------------");//aqui saiu a parte numerica de coluna do tabuleiro

        for (int i = 0; i < tabuleiro.length; i++) {

            if (i == 3 || i == 6) {//aqui divide o lado esquerdo em 3 do tabuleiro
                System.out.println("-----------------------------");
            }
            System.out.print(i + " |");//aqui a numerica de linhas

            for (int j = 0; j < tabuleiro.length; j++) {
                System.out.print(tabuleiro[i][j]);
                if (j == 2 || j == 5) {//e aqui divide o lado direito em 3 do tabuleiro
                    System.out.print("| ");
                } else {
                    System.out.print("  ");
                }

            }
            System.out.println("");
        }
    }
    public static int step(char[][] tabuleiro, char[][] tabuleiroFixo, int linha, int coluna, char numero, int limpar) {
        //esse switch caso o usuario queira limpar ou jogar(e se jogar testar a jogada)
        switch (limpar) {
            case 0:
                 //esse If testara se não existe na matrix ou esta ocupa
                if (linha < 0 || coluna < 0 || linha > tabuleiro.length || coluna > tabuleiro[0].length) {
                    return -1;
                } else if (tabuleiro[linha][coluna] != '_') {
                    return -1;
                }

                //Para ler linha, colunas e regial
                //esse for ira ler linhas
                for (int x = 0; x < tabuleiro.length; x++) {
                    if (tabuleiro[x][coluna] == numero) {
                        return 0;
                    }
                }
                //esse for  ira ler colunas
                for (int x = 0; x < tabuleiro[0].length; x++) {
                    if (tabuleiro[linha][x] == numero) {
                        return 0;
                    }
                }
                //esse for vai ler uma nova matrix 3x3 e vai ver se não esxiste 
                //um numero igual na região
                char matrixsub[][] = new char[3][3];
                int subLinha,
                 subColuna;
                for (int x = 0; x < matrixsub.length; x++) {
                    for (int y = 0; y < matrixsub[0].length; y++) {
                        subLinha = Math.abs(((linha / 3) * 3)) + x;
                        subColuna = Math.abs(((coluna / 3) * 3)) + y;
                        matrixsub[x][y] = tabuleiro[subLinha][subColuna];
                    }
                }
                if (região(matrixsub, numero) == false) {
                    return 0;
                }
                return 1;
            case 1://limpa uma cassa q o jogador quiser
                if (linha < 0 || coluna < 0 || linha > tabuleiro.length || coluna > tabuleiro[0].length) {
                    return -1;
                } else if (tabuleiroFixo[linha][coluna] != '_') {
                    return -1;
                } else {
                    tabuleiro[linha][coluna] = numero;
                    return 1;
                }
        }
        return -1;
    }
    public static boolean região(char matrixsub[][], char numero) {
        for (int x = 0; x < matrixsub.length; x++) {
            for (int y = 0; y < matrixsub[0].length; y++) {
                if (numero == matrixsub[x][y]) {
                    return false;//essa função le a matrix 3x3 que se transforma na região em que o jogador
                                 //decidiu jogars seu numero para testar se existe um numero parecido
                }
            }    
        }
        return true;
    }
    public static boolean status(char[][] tabuleiro) {
        for (int x = 0; x < tabuleiro.length; x++) {
            for (int y = 0; y < tabuleiro[0].length; y++) {
                if (tabuleiro[x][y] == '_') {
                    return false;
                }//testa para ver se o jogador completou o jogo
            }
        }
        return true;
    }
}