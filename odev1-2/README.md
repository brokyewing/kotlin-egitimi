# Kotlin Ödev 1-2

Bu proje, Kotlin programlama dili ile temel değişken kullanımı, fonksiyon yazımı ve algoritma geliştirme becerilerini pekiştirmek amacıyla hazırlanmış iki ödevden oluşmaktadır.

## İçerik

- **Odev1.kt:** Temel değişken tanımlamaları ve ekrana yazdırma işlemleri.
- **Odev2.kt:** Fonksiyonlarla algoritma geliştirme (matematiksel ve günlük hayattan örnekler).
- **Odev2Main.kt:** Odev2.kt içindeki fonksiyonların örnek kullanımı ve test edilmesi.

---

## Odev1.kt
Kişisel bilgiler, müşteri bilgileri ve çeşitli örnek değişkenler tanımlanıp ekrana yazdırılır. Kodun amacı, değişken tanımlama ve `println` ile çıktı alma pratiği kazandırmaktır.

### Örnek Çıktı:
```
Şehir : Hatay
Ülke : Türkiye
Telefon : +90 0101010101
... (devamı kodda)
```

---

## Odev2.kt
Yedi farklı fonksiyon ile çeşitli algoritmalar çözülmüştür:

1. **Fahrenheit'dan Celsius'a Dönüştürme**
2. **Dikdörtgen Çevresi Hesaplama**
3. **Faktöriyel Hesaplama**
4. **Bir Kelimedeki 'a' Harfi Sayısı**
5. **Çokgenin İç Açıları Toplamı**
6. **Çalışma Saati ve Ücret Hesaplama**
7. **İnternet Kota Ücreti Hesaplama**

Her fonksiyonun örnek kullanımı ve çıktısı Odev2Main.kt dosyasında gösterilmiştir.

---

## Odev2Main.kt
`Odev2` sınıfındaki fonksiyonların örneklerle nasıl kullanılacağını gösteren ana dosyadır. Her fonksiyon için örnek değerlerle çağrılar yapılır ve sonuçlar ekrana yazdırılır.

---

## Projeyi Çalıştırma

1. **IDE ile:**
   - Projeyi [IntelliJ IDEA](https://www.jetbrains.com/idea/) veya [Android Studio](https://developer.android.com/studio) ile açın.
   - `Odev1.kt` veya `Odev2Main.kt` dosyasındaki `main` fonksiyonuna sağ tıklayıp `Run` seçeneğiyle çalıştırabilirsiniz.

2. **Komut Satırı ile:**
   - Kotlin yüklü ise terminalde aşağıdaki komutları kullanabilirsiniz:
     ```sh
     kotlinc Odev1.kt -include-runtime -d Odev1.jar
     java -jar Odev1.jar
     
     kotlinc Odev2.kt Odev2Main.kt -include-runtime -d Odev2.jar
     java -jar Odev2.jar
     ```

---

## Kısa Yollar & İpuçları

- `main` fonksiyonunu çalıştırmak için dosya üzerinde sağ tıklayıp `Run` seçeneğini kullanabilirsiniz.
- Fonksiyonları test etmek için Odev2Main.kt dosyasını düzenleyebilirsiniz.
- Kodlarınızda değişiklik yaptıktan sonra tekrar derleyip çalıştırmayı unutmayın.

---
