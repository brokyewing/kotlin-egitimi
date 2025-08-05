# Odev7 - Yapılacaklar Uygulaması

Bu proje, Kotlin programlama dili kullanılarak geliştirilmiş bir Android yapılacaklar (todo) uygulamasıdır. Uygulama, modern Android geliştirme pratiklerini kullanarak MVVM mimarisi, Room veritabanı, Hilt dependency injection ve Navigation component ile geliştirilmiştir.

## 📱 Uygulama Özellikleri

- ✅ Yapılacak görevleri ekleme, düzenleme ve silme
- 📋 Görevleri listeleme
- 🔍 Görev detaylarını görüntüleme
- 💾 Room veritabanı ile yerel depolama
- 🎨 Material Design UI
- 🧪 Modern Android mimarisi (MVVM)

## 🛠️ Teknolojiler

- **Kotlin** - Programlama dili
- **Android SDK** - Platform
- **Room Database** - Yerel veritabanı
- **Hilt** - Dependency Injection
- **Navigation Component** - Fragment yönetimi
- **ViewModel & LiveData** - MVVM mimarisi
- **Coroutines** - Asenkron işlemler
- **ViewBinding** - View bağlama
- **Material Design** - UI bileşenleri

## 📁 Proje Yapısı

```
Odev7/
├── app/
│   ├── build.gradle.kts                    # Uygulama bağımlılıkları
│   ├── proguard-rules.pro                  # ProGuard kuralları
│   └── src/
│       └── main/
│           ├── java/com/example/odev7/
│           │   ├── data/
│           │   │   ├── dao/
│           │   │   │   └── YapilacakDao.kt           # Veritabanı erişim nesnesi
│           │   │   ├── entity/
│           │   │   │   └── Yapilacak.kt              # Veritabanı entity sınıfı
│           │   │   ├── repository/
│           │   │   │   └── YapilacakRepository.kt    # Repository sınıfı
│           │   │   └── database/
│           │   │       └── Veritabani.kt             # Room veritabanı
│           │   ├── di/
│           │   │   └── AppModule.kt                   # Hilt modülü
│           │   ├── ui/
│           │   │   ├── adapter/
│           │   │   │   └── YapilacakAdapter.kt       # RecyclerView adapter
│           │   │   ├── fragment/
│           │   │   │   ├── AnasayfaFragment.kt       # Ana sayfa fragment
│           │   │   │   ├── DetayFragment.kt          # Detay fragment
│           │   │   │   └── KayitFragment.kt          # Kayıt fragment
│           │   │   └── viewmodel/
│           │   │       ├── AnasayfaViewModel.kt      # Ana sayfa view model
│           │   │       ├── DetayViewModel.kt         # Detay view model
│           │   │       └── KayitViewModel.kt         # Kayıt view model
│           │   ├── MainActivity.kt                    # Ana aktivite
│           │   └── YapilacakUygulamasi.kt            # Application sınıfı
│           ├── res/
│           │   ├── drawable/                          # Çizim dosyaları
│           │   ├── layout/
│           │   │   ├── activity_main.xml              # Ana aktivite layout
│           │   │   ├── fragment_anasayfa.xml         # Ana sayfa layout
│           │   │   ├── fragment_detay.xml            # Detay layout
│           │   │   ├── fragment_kayit.xml            # Kayıt layout
│           │   │   └── card_yapilacak.xml            # Kart layout
│           │   ├── navigation/
│           │   │   └── nav_graph.xml                  # Navigation graph
│           │   ├── values/
│           │   │   ├── colors.xml                     # Renk tanımları
│           │   │   ├── strings.xml                    # Metin tanımları
│           │   │   └── themes.xml                     # Tema tanımları
│           │   ├── values-night/                      # Gece teması
│           │   └── mipmap-*/                          # Uygulama ikonları
│           └── AndroidManifest.xml                    # Uygulama manifesti
│       ├── test/                                      # Unit testler
│       └── androidTest/                               # UI testler
├── gradle/                                            # Gradle wrapper
├── build.gradle.kts                                   # Proje bağımlılıkları
├── settings.gradle.kts                                # Proje ayarları
├── gradle.properties                                  # Gradle özellikleri
├── gradlew                                            # Gradle wrapper (Unix)
├── gradlew.bat                                        # Gradle wrapper (Windows)
└── .gitignore                                         # Git ignore dosyası
```

## 🚀 Kurulum

### Gereksinimler

- Android Studio Arctic Fox veya üzeri
- Android SDK 34
- Kotlin 1.9.0
- Gradle 8.1.0
- Minimum SDK: 24 (Android 7.0)
- Target SDK: 34 (Android 14)

### Kurulum Adımları

1. **Projeyi klonlayın:**
   ```bash
   git clone <repository-url>
   cd Odev7
   ```

2. **Android Studio'da açın:**
   - Android Studio'yu açın
   - "Open an existing project" seçeneğini tıklayın
   - Proje klasörünü seçin

3. **Bağımlılıkları senkronize edin:**
   - Gradle sync işlemini bekleyin
   - Gerekirse "Sync Now" butonuna tıklayın

4. **Uygulamayı çalıştırın:**
   - Bir Android cihaz veya emülatör bağlayın
   - "Run" butonuna tıklayın (Shift + F10)

## 📋 Kullanım

### Ana Özellikler

1. **Görev Ekleme:**
   - Ana sayfada "+" butonuna tıklayın
   - Görev başlığını ve açıklamasını girin
   - "Kaydet" butonuna tıklayın

2. **Görev Listeleme:**
   - Ana sayfada tüm görevler listelenir
   - Her görev bir kart içinde gösterilir

3. **Görev Detayı:**
   - Bir göreve tıklayarak detaylarını görüntüleyin
   - Görevi düzenleyebilir veya silebilirsiniz

4. **Görev Silme:**
   - Detay sayfasında "Sil" butonuna tıklayın
   - Onay dialogunda "Evet" seçeneğini seçin

## 🏗️ Mimari

Bu proje **MVVM (Model-View-ViewModel)** mimarisi kullanır:

- **Model:** Room veritabanı ve Repository sınıfları
- **View:** Fragment'lar ve Activity'ler
- **ViewModel:** ViewModel sınıfları ve LiveData

### Katmanlar

1. **Data Layer:**
   - `Yapilacak.kt` - Entity sınıfı
   - `YapilacakDao.kt` - Veritabanı erişim nesnesi
   - `Veritabani.kt` - Room veritabanı
   - `YapilacakRepository.kt` - Repository sınıfı

2. **UI Layer:**
   - `MainActivity.kt` - Ana aktivite
   - Fragment'lar (Anasayfa, Detay, Kayıt)
   - Adapter sınıfları

3. **ViewModel Layer:**
   - ViewModel sınıfları (Anasayfa, Detay, Kayıt)

4. **Dependency Injection:**
   - `AppModule.kt` - Hilt modülü
   - `YapilacakUygulamasi.kt` - Application sınıfı

## 🧪 Test

### Unit Testler
```bash
./gradlew test
```

### UI Testler
```bash
./gradlew connectedAndroidTest
```

## 📦 Build

### Debug Build
```bash
./gradlew assembleDebug
```

### Release Build
```bash
./gradlew assembleRelease
```

## 🔧 Konfigürasyon

### Gradle Ayarları
- **compileSdk:** 34
- **minSdk:** 24
- **targetSdk:** 34
- **Kotlin Version:** 1.9.0
- **Gradle Version:** 8.1.0

### Bağımlılıklar
- **AndroidX Core:** 1.12.0
- **Material Design:** 1.10.0
- **Navigation:** 2.7.5
- **Room:** 2.6.0
- **Hilt:** 2.48
- **Coroutines:** 1.7.3

## 📄 Lisans

Bu proje eğitim amaçlı geliştirilmiştir.

## 👨‍💻 Geliştirici

Bu proje Kotlin eğitimi kapsamında geliştirilmiştir.

## 🤝 Katkıda Bulunma

1. Fork edin
2. Feature branch oluşturun (`git checkout -b feature/AmazingFeature`)
3. Commit edin (`git commit -m 'Add some AmazingFeature'`)
4. Push edin (`git push origin feature/AmazingFeature`)
5. Pull Request oluşturun