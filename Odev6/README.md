# 6. Ödev - Kotlin Android Uygulaması

Bu proje, Kotlin programlama dili kullanılarak geliştirilmiş bir Android uygulamasıdır. Uygulama, film listesi gösterimi ve detay sayfaları içeren modern bir arayüze sahiptir.

## 📁 Proje Yapısı

```
Odev6/
├── .git/                          # Git versiyon kontrol dosyaları
├── .idea/                         # IntelliJ IDEA IDE ayarları
├── app/                           # Ana uygulama modülü
│   ├── build.gradle.kts          # Uygulama seviyesi Gradle yapılandırması
│   └── src/
│       └── main/
│           ├── java/
│           │   └── com/example/a6odev/
│           │       ├── MainActivity.kt           # Ana aktivite
│           │       ├── AnasayfaFragment.kt      # Ana sayfa fragment'i
│           │       ├── DetayFragment.kt         # Detay sayfası fragment'i
│           │       ├── Filmler.kt               # Film veri sınıfı
│           │       └── FilmlerAdapter.kt        # RecyclerView adapter'ı
│           ├── res/
│           │   ├── drawable/                     # Çizim dosyaları
│           │   ├── layout/
│           │   │   ├── activity_main.xml        # Ana aktivite layout'u
│           │   │   ├── fragment_anasayfa.xml    # Ana sayfa layout'u
│           │   │   ├── fragment_detay.xml       # Detay sayfası layout'u
│           │   │   └── card_tasarim.xml         # Film kartı layout'u
│           │   ├── menu/                         # Menü dosyaları
│           │   ├── mipmap-*/                     # Uygulama ikonları
│           │   ├── navigation/                   # Navigation graph dosyaları
│           │   │   └── main_activity_nav.xml    # Navigation graph
│           │   ├── values/
│           │   │   ├── colors.xml               # Renk tanımları
│           │   │   ├── strings.xml              # Metin tanımları
│           │   │   └── themes.xml               # Tema tanımları
│           │   └── values-night/                # Gece modu tema dosyaları
│           └── AndroidManifest.xml              # Uygulama manifest dosyası
├── gradle/                        # Gradle wrapper dosyaları
├── build.gradle.kts              # Proje seviyesi Gradle yapılandırması
├── settings.gradle.kts           # Proje ayarları
├── gradle.properties             # Gradle özellikleri
├── gradlew                       # Gradle wrapper (Linux/Mac)
├── gradlew.bat                   # Gradle wrapper (Windows)
└── .gitignore                    # Git ignore dosyası
```

## 🚀 Özellikler

- **Modern UI/UX**: Material Design bileşenleri kullanılarak geliştirilmiş arayüz
- **Navigation Component**: Android Navigation Component ile sayfa geçişleri
- **Search Functionality**: Film arama özelliği
- **Bottom Navigation**: Alt navigasyon menüsü
- **RecyclerView**: Film listesi için RecyclerView kullanımı
- **ViewBinding**: Güvenli view erişimi için ViewBinding
- **Fragment-based Architecture**: Fragment tabanlı mimari

## 🛠️ Teknolojiler

- **Kotlin**: Ana programlama dili
- **Android SDK**: Android geliştirme platformu
- **Gradle**: Build sistemi
- **Navigation Component**: Sayfa geçişleri
- **Material Design**: UI bileşenleri
- **ViewBinding**: View erişimi

## 📋 Gereksinimler

- Android Studio Arctic Fox veya üzeri
- JDK 11
- Android SDK API Level 24+ (Android 7.0+)
- Gradle 8.0+

## 🔧 Kurulum

1. **Projeyi klonlayın:**
   ```bash
   git clone [repository-url]
   cd Odev6
   ```

2. **Android Studio'da açın:**
   - Android Studio'yu açın
   - "Open an existing project" seçeneğini tıklayın
   - `Odev6` klasörünü seçin

3. **Gradle sync yapın:**
   - Android Studio otomatik olarak Gradle sync işlemini başlatacaktır
   - Eğer manuel olarak yapmak isterseniz: `File > Sync Project with Gradle Files`

4. **Uygulamayı çalıştırın:**
   - Bir Android cihaz veya emülatör bağlayın
   - `Run` butonuna tıklayın veya `Shift + F10` tuşlarına basın

## 📱 Uygulama Özellikleri

### Ana Sayfa (AnasayfaFragment)
- Film listesi görüntüleme
- Arama fonksiyonu
- Film kartları ile modern tasarım

### Detay Sayfası (DetayFragment)
- Seçilen filmin detaylı bilgileri
- Film görseli ve açıklaması

### Navigasyon
- Alt navigasyon menüsü ile kolay erişim
- 5 ana sekme: Ana Sayfa, Arama, Canlı, İndirilenler, Profil

## 🏗️ Mimari

Proje, modern Android geliştirme prensiplerine uygun olarak tasarlanmıştır:

- **Single Activity Architecture**: Tek aktivite, çoklu fragment yaklaşımı
- **Fragment-based Navigation**: Navigation Component ile fragment geçişleri
- **Data Classes**: Kotlin data class'ları ile veri modelleme
- **Adapter Pattern**: RecyclerView için adapter implementasyonu

## 📄 Dosya Açıklamaları

### Kotlin Dosyaları
- `MainActivity.kt`: Uygulamanın ana giriş noktası
- `AnasayfaFragment.kt`: Ana sayfa fragment'i ve film listesi yönetimi
- `DetayFragment.kt`: Film detay sayfası fragment'i
- `Filmler.kt`: Film veri modeli (data class)
- `FilmlerAdapter.kt`: RecyclerView için adapter sınıfı

### Layout Dosyaları
- `activity_main.xml`: Ana aktivite layout'u
- `fragment_anasayfa.xml`: Ana sayfa fragment layout'u
- `fragment_detay.xml`: Detay sayfası fragment layout'u
- `card_tasarim.xml`: Film kartı layout'u

### Yapılandırma Dosyaları
- `build.gradle.kts`: Proje ve uygulama seviyesi bağımlılıklar
- `settings.gradle.kts`: Proje ayarları ve modül tanımları
- `AndroidManifest.xml`: Uygulama manifest dosyası

## 🔍 Arama Özelliği

Uygulama, SearchView bileşeni kullanarak gerçek zamanlı film arama özelliği sunar:
- Ana sayfada bulunan SearchView ile film arama
- Gerçek zamanlı filtreleme
- Fragment'lar arası iletişim

## 🎨 UI/UX Özellikleri

- **Material Design**: Google'ın Material Design prensiplerine uygun tasarım
- **Bottom Navigation**: Kolay erişim için alt navigasyon
- **Card Layout**: Film bilgileri için kart tasarımı
- **Responsive Design**: Farklı ekran boyutlarına uyumlu tasarım

## 📱 Desteklenen Android Sürümleri

- **Minimum SDK**: API Level 24 (Android 7.0)
- **Target SDK**: API Level 35 (Android 14)
- **Compile SDK**: API Level 35

## 🚀 Build ve Deployment

### Debug Build
```bash
./gradlew assembleDebug
```

### Release Build
```bash
./gradlew assembleRelease
```

### APK Konumu
- Debug APK: `app/build/outputs/apk/debug/app-debug.apk`
- Release APK: `app/build/outputs/apk/release/app-release.apk`

## 📝 Lisans

Bu proje eğitim amaçlı geliştirilmiştir.

## 👨‍💻 Geliştirici

Bu proje Kotlin eğitimi kapsamında geliştirilmiştir.

---