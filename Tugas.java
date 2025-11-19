import java.util.Scanner;

public class Tugas {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int[][] dataSurvei = new int[10][6];
        String[] namaResponden = new String[10];
        String[] pertanyaan = {
            "Bagaimana kualitas produk/layanan yang Anda terima?",
            "Seberapa responsif tim customer service dalam menangani keluhan?",
            "Apakah harga yang ditawarkan sesuai dengan kualitas produk/layanan?",
            "Seberapa mudah proses pembelian/transaksi yang Anda lakukan?",
            "Bagaimana tingkat kepercayaan Anda terhadap perusahaan ini?",
            "Seberapa besar kemungkinan Anda merekomendasikan perusahaan ini kepada orang lain?"
        };


        System.out.println("=== DATA SURVEI KEPUASAN PELANGGAN ===\n");
        System.out.println("Masukkan data responden dan nilai survei (nilai 1-5):");
        System.out.println("=".repeat(50));
        
        
        for (int i = 0; i < 10; i++) {
            System.out.println("\nResponden " + (i + 1) + ":");
            System.out.print("  Masukkan nama: ");
            namaResponden[i] = input.nextLine();
            
            System.out.println("\n  Silakan berikan penilaian (1 = Sangat Tidak Puas, 5 = Sangat Puas):");
            for (int j = 0; j < 6; j++) {
                System.out.println("  " + (j + 1) + ". " + pertanyaan[j]);
                System.out.print("     Nilai (1-5): ");
                dataSurvei[i][j] = input.nextInt();
                // Validasi input
                while (dataSurvei[i][j] < 1 || dataSurvei[i][j] > 5) {
                    System.out.print("     Nilai harus antara 1-5! Masukkan lagi: ");
                    dataSurvei[i][j] = input.nextInt();
                }
            }
            input.nextLine(); // Membersihkan buffer setelah input.nextInt()
        }
        
        
        
        // 1. Menampilkan nilai rata-rata untuk setiap responden
        System.out.println("\n1. NILAI RATA-RATA UNTUK SETIAP RESPONDEN:");
        System.out.println("-".repeat(50));
        for (int i = 0; i < 10; i++) {
            double total = 0;
            for (int j = 0; j < 6; j++) {
                total += dataSurvei[i][j];
            }
            double rataRata = total / 6;
            System.out.printf("%-20s: %.2f\n", namaResponden[i], rataRata);
        }
        
        
        
        // 2. Menampilkan nilai rata-rata untuk setiap pertanyaan
        System.out.println("\n2. NILAI RATA-RATA UNTUK SETIAP PERTANYAAN:");
        System.out.println("-".repeat(50));
        for (int j = 0; j < 6; j++) {
            double total = 0;
            for (int i = 0; i < 10; i++) {
                total += dataSurvei[i][j];
            }
            double rataRata = total / 10;
            System.out.printf("Pertanyaan %d: %.2f\n", (j + 1), rataRata);
            System.out.println("  " + pertanyaan[j]);
            System.out.println();
        }
        
        // 3. Menampilkan nilai rata-rata secara keseluruhan
        System.out.println("\n3. NILAI RATA-RATA SECARA KESELURUHAN:");
        System.out.println("-".repeat(50));
        double totalKeseluruhan = 0;
        int jumlahData = 0;
        
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 6; j++) {
                totalKeseluruhan += dataSurvei[i][j];
                jumlahData++;
            }
        }
        
        double rataRataKeseluruhan = totalKeseluruhan / jumlahData;
        System.out.printf("Rata-rata Keseluruhan: %.2f\n", rataRataKeseluruhan);

        input.close();
    }
}
