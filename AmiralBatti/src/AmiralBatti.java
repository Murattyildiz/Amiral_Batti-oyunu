import java.util.Scanner;

public class AmiralBatti {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Zorluk seviyesini kullanıcıdan al
        System.out.println("Zorluk Seviyesini Secin:");
        System.out.println("1. Kolay (Tahta Boyutu 6)");
        System.out.println("2. Orta (Tahta Boyutu 8)");
        System.out.println("3. Zor (Tahta Boyutu 10)");
        System.out.print("Seciminiz: ");
        int zorluk = scanner.nextInt();
        scanner.nextLine();

        int tahtaBoyutu = 10; 
        switch (zorluk) {
            case 1 -> tahtaBoyutu = 6;
            case 2 -> tahtaBoyutu = 8;
            case 3 -> tahtaBoyutu = 10;
            default -> System.out.println("Geçersiz seçim, varsayılan olarak Zor mod seçildi.");
        }

        // Oyun modunu seç
        System.out.println("1. Bilgisayara Karsi Oyna");
        System.out.println("2. Kullaniciya Karsi Oyna");
        System.out.print("Oyun Modu Secin: ");
        int secim = scanner.nextInt();
        scanner.nextLine(); 

        switch (secim) {
            case 1 -> bilgisayaraKarsiOyna(scanner, tahtaBoyutu); 
            case 2 -> kullaniciyaKarsiOyna(scanner, tahtaBoyutu); 
            default -> System.out.println("Geçersiz seçim!");
        }

        scanner.close();
    }

    // Kullanıcılar arası oyun başlatma
    private static void kullaniciyaKarsiOyna(Scanner scanner, int tahtaBoyutu) {
        OyunTahtasi oyuncu1Tahtasi = new OyunTahtasi(tahtaBoyutu);
        OyunTahtasi oyuncu2Tahtasi = new OyunTahtasi(tahtaBoyutu);

        gemileriYerlestir(scanner, oyuncu1Tahtasi, "Murat"); 
        gemileriYerlestir(scanner, oyuncu2Tahtasi, "Ahmet");

        oyunuBaslat(scanner, oyuncu1Tahtasi, oyuncu2Tahtasi, false);
    }

    // Bilgisayara karşı oyun başlatma
    private static void bilgisayaraKarsiOyna(Scanner scanner, int tahtaBoyutu) {
        OyunTahtasi kullaniciTahtasi = new OyunTahtasi(tahtaBoyutu);
        OyunTahtasi bilgisayarTahtasi = new OyunTahtasi(tahtaBoyutu);

        gemileriYerlestir(scanner, kullaniciTahtasi, "Kullanıcı");
        bilgisayarTahtasi.gemileriRastgeleYerlestir();

        oyunuBaslat(scanner, kullaniciTahtasi, bilgisayarTahtasi, true);
    }

    // Gemileri yerleştirme işlemi
    private static void gemileriYerlestir(Scanner scanner, OyunTahtasi tahta, String oyuncuAdi) {
        System.out.println(oyuncuAdi + " icin tahta:");
        tahta.tahtayiGoster();
        System.out.println(oyuncuAdi + " icin gemileri yerlestirin:");
        tahta.gemileriYerlestir(scanner);
    }

    // Oyunun başlatılması ve devam ettirilmesi
    private static void oyunuBaslat(Scanner scanner, OyunTahtasi oyuncu1Tahtasi, OyunTahtasi oyuncu2Tahtasi, boolean bilgisayarMi) {
        int siradakiOyuncu = 1;
        boolean oyunDevamEdiyor = true;

        while (oyunDevamEdiyor) {  
            System.out.println("Enter'a basın ve devam edin...");
            scanner.nextLine(); 

            if (bilgisayarMi && siradakiOyuncu == 2) {
                System.out.println("Sıra Bilgisayarda:");
            } else {
                System.out.println("Sıra Oyuncu " + siradakiOyuncu + "'de:");
            }

            OyunTahtasi aktifTahta;
            if(siradakiOyuncu==1)
            {
                aktifTahta=oyuncu2Tahtasi;
            }
            else
            {
                aktifTahta=oyuncu1Tahtasi;
            }
            aktifTahta.tahtayiGoster();
           

            Oyuncu oyuncu = new Oyuncu(aktifTahta, bilgisayarMi && siradakiOyuncu == 2);
            oyuncu.tahminYap(scanner);

            if (aktifTahta.oyunBittiMi()) {
                if (bilgisayarMi && siradakiOyuncu == 2) {
                    System.out.println("Bilgisayar kazandı!");
                } else {
                    System.out.println("Tebrikler! Oyuncu " + siradakiOyuncu + " kazandı!");
                }
                oyunDevamEdiyor = false;
            }

            if(siradakiOyuncu==1)
            {
                siradakiOyuncu=2;
            }
            else
            {
                siradakiOyuncu=1;
            }
              
        }
    }
}
