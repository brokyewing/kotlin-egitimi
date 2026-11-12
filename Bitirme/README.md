# Broky Food — Android Yemek Sipariş Uygulaması & Kotlin Sunucu

Broky Food, Kotlin ile geliştirilmiş modern bir Android yemek sipariş uygulaması (`:app`) ve aynı depoda yer alan, kendi kendine yeten Kotlin + SQLite arka uç sunucusu (`:server`) ile canlı web önizleme arayüzünden oluşur.

## Mimari ve Modüller

```
Bitirme/
├── app/                        # Android uygulaması (Kotlin, MVVM, Hilt, Retrofit, ViewBinding)
│   └── src/
│       ├── main/java/com/brokyfood/app/
│       │   ├── data/           # FoodApiService, Food/CartFood modelleri, FoodRepository
│       │   ├── di/             # Hilt NetworkModule & SessionManager bağlamaları
│       │   ├── ui/             # Home, Detail ve Cart ekranları (Fragment + ViewModel + StateFlow)
│       │   └── util/           # Constants ve cihaz bazlı oturum (SessionManager)
│       └── test/               # ViewModel & Repository birim testleri
├── server/                     # Sunucu modülü (Kotlin, JDK HttpServer, SQLite, Gson)
│   └── src/
│       ├── main/kotlin/com/brokyfood/server/
│       │   ├── Database.kt     # SQLite WAL şeması, 14 yemek seed, atomik sepet ve sipariş işlemleri
│       │   └── Main.kt         # REST API + statik görsel sunumu + canlı Web Demo + APK indirme
│       ├── main/resources/static/
│       │   ├── index.html      # Tarayıcıdan denenebilir TR/EN canlı web demosu
│       │   └── resimler/       # 14 yemek görseli (PNG)
│       └── test/               # Sunucu birim ve HTTP entegrasyon testleri
├── Dockerfile.runtime          # ARM64 / x86_64 uyumlu (eclipse-temurin:21-jre, non-root) çalışma imajı
└── docker-compose.yml          # Oracle Cloud Always Free (256 MB RAM limitli) tek komut kurulum
```

## Öne Çıkan Özellikler

- **Kendi Arka Ucu ve Veri Kalıcılığı**: Dış PHP servisine bağımlılık kaldırıldı; SQLite (WAL modu) tabanlı Kotlin sunucusu 14 yemeği ve görsellerini doğrudan sunar.
- **Canlı Web Ön Gösterimi (TR/EN)**: Sunucu kök dizininde (`/`) tarayıcı üzerinden yemek arama, sepete ekleme, adet değiştirme ve sipariş tamamlama akışlarını çalıştıran interaktif web arayüzü bulunur.
- **İzole Kullanıcı Sepeti**: Sabit kullanıcı adı yerine her kurulum/tarayıcı için benzersiz oturum kimliği (`SessionManager`) üretilir; kullanıcıların sepetleri birbirine karışmaz.
- **Atomik Sepet İşlemleri**: Aynı ürünü tekrar ekleme (`ON CONFLICT DO UPDATE`), adet güncelleme (`/sepetAdetGuncelle.php`) ve sipariş tamamlama (`/siparisTamamla.php`) işlemleri tek adımda atomik olarak çalışır.
- **Güvenlik Sertleştirmesi**:
  - Android tarafında `cleartextTrafficPermitted="false"` varsayılan olarak kapalıdır (`network_security_config.xml`).
  - `HttpLoggingInterceptor` yalnızca `DEBUG` derlemelerinde aktiftir; `release` derlemelerinde ağ gövdesi loglanmaz.
  - Sunucu tarafında girdi doğrulama, dizin gezinme (path traversal) koruması ve güvenlik başlıkları (`X-Content-Type-Options`, `X-Frame-Options`, `Referrer-Policy`) uygulanır.

## API Uç Noktaları

| Metot | Yol | Açıklama |
|---|---|---|
| `GET` | `/api/health` | Sağlık kontrolü ve kayıtlı yemek sayısı |
| `GET` | `/yemekler/tumYemekleriGetir.php` | Tüm yemek listesini döner |
| `POST` | `/yemekler/sepettekiYemekleriGetir.php` | Kullanıcının sepetindeki ürünleri döner (`kullanici_adi`) |
| `POST` | `/yemekler/sepeteYemekEkle.php` | Sepete ürün ekler veya mevcut adedi artırır |
| `POST` | `/yemekler/sepetAdetGuncelle.php` | Sepetteki ürün adedini atomik olarak günceller |
| `POST` | `/yemekler/sepettenYemekSil.php` | Sepetten ürün siler (`sepet_yemek_id`, `kullanici_adi`) |
| `POST` | `/yemekler/siparisTamamla.php` | Siparişi kaydeder ve kullanıcının sepetini temizler |
| `GET` | `/yemekler/resimler/{dosya}.png` | Yemek görsellerini sunar |
| `GET` | `/brokyfood.apk` | Derlenmiş Android APK dosyasını indirir |

## Derleme ve Çalıştırma

### 1. Testler, Sunucu JAR ve Android APK Derlemesi

```bash
./gradlew :server:test :server:fatJar :app:testDebugUnitTest :app:assembleDebug
```

Özel bir API adresiyle APK derlemek için:

```bash
./gradlew :app:assembleDebug -PAPI_BASE_URL="https://brokyfood.ibrahimaskeroglu.com/yemekler/"
```

### 2. Docker Compose ile Sunucuda Çalıştırma (Oracle Cloud ARM64 / x86_64)

```bash
docker compose up -d --build
```

Varsayılan olarak `127.0.0.1:18095` portunda ayağa kalkar ve en fazla `256 MB` RAM sınırıyla çalışır:
- Canlı Web Demosu: `http://localhost:18095/`
- Sağlık Kontrolü: `http://localhost:18095/api/health`
- APK İndirme: `http://localhost:18095/brokyfood.apk`
