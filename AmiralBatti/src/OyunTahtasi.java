
import java.util.Random;
import java.util.Scanner;

public class OyunTahtasi {

    private final char[][] tahta;
    private final int boyut;
    private static final Random random = new Random();

    public int getBoyut() {
        return boyut;
    }

    public char[][] getTahta() {
        return tahta;
    }

    // Oyun tahtası sınıfının kurucusu
    public OyunTahtasi(int boyut) {
        this.boyut = boyut; // Kurucuya verilen boyut değeri ile tahtanın boyutunu ayarla
        this.tahta = new char[boyut][boyut]; // Boyuta göre 2D dizi oluştur
        tahtayiSifirla(); //
    }

    // Oyun tahtasını başlangıç durumuna getirir
    private void tahtayiSifirla() {
        for (int i = 0; i < boyut; i++) {
            for (int j = 0; j < boyut; j++) {
                tahta[i][j] = '~';
            }
        }
    }

    // Oyun tahtasını konsola yazdırır
    public void tahtayiGoster() {
        System.out.print("   ");
        for (int i = 1; i <= boyut; i++) {
            System.out.print(" " + i + "  "); // Sütun numaralarını yazdır
        }
        System.out.println();

        System.out.print("  +");
        for (int i = 1; i <= boyut; i++) {
            System.out.print("---+"); // Üst çerçeve
        }
        System.out.println();

        for (int i = 0; i < boyut; i++) {
            System.out.print((char) ('A' + i) + " |"); // Satır harflerini yazdır

            for (int j = 0; j < boyut; j++) {
                char hucre = tahta[i][j] == 'O' ? 'O' : tahta[i][j]; // Gemi varsa gizle
                System.out.print(" " + hucre + " |"); // Hücre içeriğini yazdır
            }
            System.out.println();

            System.out.print("  +");
            for (int j = 1; j <= boyut; j++) {
                System.out.print("---+");
            }
            System.out.println();
        }
    }

    public boolean oyunBittiMi() {
        for (char[] satir : tahta) {
            for (char hucre : satir) {
                if (hucre == 'O') { // Eğer bir gemi hücresi varsa, oyun bitmemiştir
                    return false;
                }
            }
        }
        return true; // Tüm gemiler vurulduysa oyunu bitir
    }

    // Kullanıcıdan gemileri yerleştirmesi için koordinatlar alır
    public void gemileriYerlestir(Scanner scanner) {
        int[] gemiBoyutlari = {5, 4, 3, 2, 2};
        for (int gemiBoyutu : gemiBoyutlari) {
            boolean gemiYerlestirildi = false;
            while (!gemiYerlestirildi) {
                System.out.print(gemiBoyutu + " birim gemiyi yerlestirmek için başlangıç koordinatı ve yön girin (örnek: A5 H): ");
                String giris = scanner.nextLine().toUpperCase();
                if (giris.length() < 3) {
                    System.out.println("Gecersiz giris, lütfen tekrar deneyin.");
                    continue;
                }

                int x = giris.charAt(0) - 'A';
                int y = Integer.parseInt(giris.substring(1, giris.length() - 2).trim()) - 1;
                boolean yatay = giris.charAt(giris.length() - 1) == 'H';

                gemiYerlestirildi = gemiYerlestir(x, y, gemiBoyutu, yatay);
                if (!gemiYerlestirildi) {
                    System.out.println("Gemi yerletirilemedi, lütfen farklı bir konum veya yön deneyin.");
                }
            }
        }
    }

    // Belirli bir konuma gemi yerleştirir
    private boolean gemiYerlestir(int x, int y, int boyut, boolean yatay) {
        if (yatay) {
            if (y + boyut > this.boyut) {
                return false;
            }
            for (int i = y; i < y + boyut; i++) {
                if (tahta[x][i] != '~') {
                    return false;
                }
            }
            for (int i = y; i < y + boyut; i++) {
                tahta[x][i] = 'O'; // Gemi yerleştir
            }
        } else {
            if (x + boyut > this.boyut) {
                return false;
            }
            for (int i = x; i < x + boyut; i++) {
                if (tahta[i][y] != '~') {
                    return false;
                }
            }
            for (int i = x; i < x + boyut; i++) {
                tahta[i][y] = 'O'; // Gemi yerleştir
            }
        }
        return true;
    }

    // Gemileri rastgele yerleştiren metod
    public void gemileriRastgeleYerlestir() {
        int[] gemiBoyutlari = {5, 4, 3, 2, 2}; // Gemilerin boyutları
        for (int gemiBoyutu : gemiBoyutlari) {
            boolean gemiYerlestirildi = false;
            while (!gemiYerlestirildi) {
                int x = random.nextInt(this.boyut);
                int y = random.nextInt(this.boyut);
                boolean yatay = Math.random() < 0.5;
                gemiYerlestirildi = gemiYerlestir(x, y, gemiBoyutu, yatay);
            }
        }
    }
}
