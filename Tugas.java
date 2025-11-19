import java.util.Scanner;

public class Tugas {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int [][] pertanyaan = new int[5][5];
        for (int i = 0; i < pertanyaan.length; i++) {
            for (int j = 0; j < pertanyaan[i].length; j++) {
                System.out.print("Masukkan nilai pertanyaan ke-" + (i+1) + " baris ke-" + (j+1) + ": ");
                pertanyaan[i][j] = scanner.nextInt();
            }
        }
        for (int i = 0; i < pertanyaan.length; i++) {
            for (int j = 0; j < pertanyaan[i].length; j++) {
                System.out.print(pertanyaan[i][j] + " ");
            }
            System.out.println();
        }

        scanner.close();
    }
}
