import java.util.Scanner;
import java.util.Random;

public class Oyuncu {
    private final OyunTahtasi rakipTahtasi;
    private final boolean isBilgisayar;
    private static final Random random = new Random();

    public Oyuncu(OyunTahtasi rakipTahtasi, boolean isBilgisayar) {
        this.rakipTahtasi = rakipTahtasi;
        this.isBilgisayar = isBilgisayar;
    }

    public void tahminYap(Scanner scanner) {
        if (isBilgisayar) {
            bilgisayarTahminYap();
        } else {
            kullaniciTahminYap(scanner);
        }
    }

    private void kullaniciTahminYap(Scanner scanner) {
        System.out.print("Koordinat girin (örnek: B3): ");
        String tahmin = scanner.nextLine().toUpperCase().trim();

        if (tahmin.length() < 2 || !Character.isLetter(tahmin.charAt(0)) || !Character.isDigit(tahmin.charAt(1))) {
            System.out.println("Geçersiz koordinat! B3 gibi bir koordinat girin.");
            return;
        }

        int satir = tahmin.charAt(0) - 'A'; // Harfi sayıya çevir (A=0, B=1, C=2, ...)
        int sutun = Integer.parseInt(tahmin.substring(1)) - 1; // Sayısal değeri sütun indeksine dönüştür


        if (!gecerliKoordinatMi(satir, sutun)) {
            System.out.println("Koordinat dışında! Lütfen geçerli bir koordinat girin.");
            return;
        }

        if (hedefZatenSecildiMi(satir, sutun)) {
            System.out.println("Bu koordinata daha önce ateş edildi. Başka bir koordinat seçin.");
            return;
        }

        atesEtVeSonucuGoster(satir, sutun);
    }

    private boolean gecerliKoordinatMi(int satir, int sutun) {
        return satir >= 0 && satir < rakipTahtasi.getBoyut() &&
               sutun >= 0 && sutun < rakipTahtasi.getBoyut();
    }

    private boolean hedefZatenSecildiMi(int satir, int sutun) {
        char durum = rakipTahtasi.getTahta()[satir][sutun];
        return durum == 'X' || durum == '*';
    }

    private void atesEtVeSonucuGoster(int satir, int sutun) {
        char sonuc = rakipTahtasi.getTahta()[satir][sutun];
        if (sonuc == 'O') {
            System.out.println("Bir gemi vuruldu!");
            rakipTahtasi.getTahta()[satir][sutun] = 'X';
        } else {
            System.out.println("İska geçildi.");
            rakipTahtasi.getTahta()[satir][sutun] = '*';
        }
        rakipTahtasi.tahtayiGoster();
    }

    private void bilgisayarTahminYap() {
        int satir, sutun;
        do {
            satir = random.nextInt(rakipTahtasi.getBoyut());
            sutun = random.nextInt(rakipTahtasi.getBoyut());
        } while (hedefZatenSecildiMi(satir, sutun));

        atesEtVeSonucuGoster(satir, sutun);
        System.out.println("Bilgisayar, [" + (char) ('A' + satir) + (sutun + 1) + "] koordinatına ateş etti.");
    }
}
