# Ödev 4 - Kotlin Android Uygulaması

Bu proje, Kotlin programlama dili kullanılarak geliştirilmiş bir Android uygulamasıdır. Uygulama, Navigation Component kullanarak farklı sayfalar arasında geçiş yapabilen bir yapıya sahiptir.

## 📁 Proje Yapısı

```
odev4/
├── .git/                          # Git versiyon kontrol dosyaları
├── .idea/                         # IntelliJ IDEA/Android Studio ayarları
├── app/                           # Ana uygulama modülü
│   ├── build.gradle.kts          # Uygulama seviyesi build konfigürasyonu
│   ├── proguard-rules.pro        # ProGuard kuralları
│   └── src/
│       ├── main/
│       │   ├── java/com/example/odev4/
│       │   │   ├── MainActivity.kt           # Ana aktivite
│       │   │   ├── AnasayfaFragment.kt      # Anasayfa fragment'i
│       │   │   ├── SayfaAFragment.kt        # Sayfa A fragment'i
│       │   │   ├── SayfaBFragment.kt        # Sayfa B fragment'i
│       │   │   ├── SayfaXFragment.kt        # Sayfa X fragment'i
│       │   │   └── SayfaYFragment.kt        # Sayfa Y fragment'i
│       │   ├── res/
│       │   │   ├── drawable/                 # Çizim dosyaları
│       │   │   ├── layout/
│       │   │   │   ├── activity_main.xml     # Ana aktivite layout'u
│       │   │   │   ├── fragment_anasayfa.xml # Anasayfa layout'u
│       │   │   │   ├── fragment_sayfa_a.xml  # Sayfa A layout'u
│       │   │   │   ├── fragment_sayfa_b.xml  # Sayfa B layout'u
│       │   │   │   ├── fragment_sayfa_x.xml  # Sayfa X layout'u
│       │   │   │   └── fragment_sayfa_y.xml  # Sayfa Y layout'u
│       │   │   ├── navigation/
│       │   │   │   └── anasayfa_nav.xml     # Navigation graph
│       │   │   ├── values/                   # String, color, style tanımları
│       │   │   ├── values-night/             # Gece modu değerleri
│       │   │   └── mipmap-*/                 # Uygulama ikonları
│       │   └── AndroidManifest.xml           # Uygulama manifest dosyası
│       ├── test/                             # Unit testler
│       └── androidTest/                      # UI testler
├── gradle/                        # Gradle wrapper dosyaları
├── build.gradle.kts              # Proje seviyesi build konfigürasyonu
├── settings.gradle.kts           # Proje ayarları
├── gradle.properties             # Gradle özellikleri
├── gradlew                       # Gradle wrapper (Unix)
├── gradlew.bat                   # Gradle wrapper (Windows)
├── .gitattributes               # Git özellikleri
└── .gitignore                   # Git ignore kuralları
```

## 🚀 Kurulum

### Gereksinimler

- Android Studio Arctic Fox veya daha yeni
- JDK 11
- Android SDK 35
- Minimum SDK: 24 (Android 7.0)

### Kurulum Adımları

1. **Projeyi klonlayın:**
   ```bash
   git clone [repository-url]
   cd odev4
   ```

2. **Android Studio'da açın:**
   - Android Studio'yu açın
   - "Open an existing Android Studio project" seçeneğini tıklayın
   - `odev4` klasörünü seçin

3. **Bağımlılıkları senkronize edin:**
   - Gradle sync işlemini bekleyin
   - Gerekirse "Sync Project with Gradle Files" butonuna tıklayın

4. **Uygulamayı çalıştırın:**
   - Bir Android cihaz veya emülatör bağlayın
   - "Run" butonuna tıklayın veya `Shift + F10` tuşlarına basın

## 📱 Uygulama Özellikleri

### Teknolojiler

- **Dil:** Kotlin
- **UI Framework:** Android Views
- **Navigation:** Navigation Component
- **View Binding:** Aktif
- **Minimum SDK:** 24 (Android 7.0)
- **Target SDK:** 35 (Android 15)

### Sayfalar

1. **Anasayfa** (`AnasayfaFragment.kt`)
   - Uygulamanın ana giriş sayfası
   - Diğer sayfalara yönlendirme butonları

2. **Sayfa A** (`SayfaAFragment.kt`)
   - İlk sayfa seçeneği

3. **Sayfa B** (`SayfaBFragment.kt`)
   - İkinci sayfa seçeneği

4. **Sayfa X** (`SayfaXFragment.kt`)
   - Üçüncü sayfa seçeneği

5. **Sayfa Y** (`SayfaYFragment.kt`)
   - Dördüncü sayfa seçeneği

## 🔧 Build Konfigürasyonu

### Proje Seviyesi (`build.gradle.kts`)
```kotlin
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
}
```

### Uygulama Seviyesi (`app/build.gradle.kts`)
```kotlin
android {
    namespace = "com.example.odev4"
    compileSdk = 35
    
    defaultConfig {
        applicationId = "com.example.odev4"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"
    }
}
```

## 📦 Bağımlılıklar

- `androidx.core:core-ktx` - Kotlin core kütüphanesi
- `androidx.appcompat:appcompat` - AppCompat kütüphanesi
- `com.google.android.material:material` - Material Design bileşenleri
- `androidx.activity:activity` - Activity kütüphanesi
- `androidx.constraintlayout:constraintlayout` - ConstraintLayout
- `androidx.navigation:navigation-fragment-ktx` - Navigation Component
- `androidx.navigation:navigation-ui-ktx` - Navigation UI

## 🧪 Test

### Unit Testler
```bash
./gradlew test
```

### UI Testler
```bash
./gradlew connectedAndroidTest
```

## 📝 Geliştirme Notları

- **View Binding:** Aktif edilmiş durumda, `findViewById` kullanımından kaçının
- **Navigation:** Safe Args kullanılarak tip güvenli navigation sağlanmış
- **Kotlin:** Modern Kotlin özellikleri kullanılmış
- **Material Design:** Material Design bileşenleri kullanılmış

## 🤝 Katkıda Bulunma

1. Bu repository'yi fork edin
2. Yeni bir branch oluşturun (`git checkout -b feature/yeni-ozellik`)
3. Değişikliklerinizi commit edin (`git commit -am 'Yeni özellik eklendi'`)
4. Branch'inizi push edin (`git push origin feature/yeni-ozellik`)
5. Pull Request oluşturun

## 📄 Lisans

Bu proje eğitim amaçlı geliştirilmiştir.

## 👨‍💻 Geliştirici

Bu proje Kotlin eğitimi kapsamında geliştirilmiştir.

---
