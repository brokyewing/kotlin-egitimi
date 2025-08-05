# Hesap Makinesi (Calculator) - Android App

Bu proje, Kotlin programlama dili kullanılarak geliştirilmiş basit bir hesap makinesi Android uygulamasıdır.

## 📱 Proje Hakkında

Bu uygulama, temel matematik işlemlerini gerçekleştirebilen kullanıcı dostu bir hesap makinesi sunar. Modern Android geliştirme pratikleri kullanılarak geliştirilmiştir.

### Özellikler
- ✅ Temel matematik işlemleri (toplama)
- ✅ Kullanıcı dostu arayüz
- ✅ Tam ekran modu
- ✅ Modern Material Design

## 🛠️ Teknolojiler

- **Programlama Dili:** Kotlin
- **Platform:** Android
- **Minimum SDK:** 24 (Android 7.0)
- **Target SDK:** 35 (Android 15)
- **Build Tool:** Gradle
- **UI Framework:** Android Views

## 📁 Proje Yapısı

```
Odev5/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/example/hesapmakinesi/
│   │   │   │   └── MainActivity.kt          # Ana aktivite dosyası
│   │   │   ├── res/
│   │   │   │   ├── layout/
│   │   │   │   │   └── activity_main.xml   # Ana layout dosyası
│   │   │   │   ├── values/
│   │   │   │   ├── drawable/
│   │   │   │   └── mipmap-*/
│   │   │   └── AndroidManifest.xml
│   │   ├── test/
│   │   └── androidTest/
│   ├── build.gradle.kts                     # App-level build dosyası
│   └── proguard-rules.pro
├── gradle/
├── build.gradle.kts                         # Project-level build dosyası
├── settings.gradle.kts
├── gradle.properties
├── gradlew
├── gradlew.bat
└── .gitignore
```

## 🚀 Kurulum ve Çalıştırma

### Gereksinimler
- Android Studio (en son sürüm)
- JDK 11 veya üzeri
- Android SDK 35

### Adımlar

1. **Projeyi klonlayın:**
   ```bash
   git clone <repository-url>
   cd Odev5
   ```

2. **Android Studio'da açın:**
   - Android Studio'yu açın
   - "Open an existing project" seçeneğini tıklayın
   - `Odev5` klasörünü seçin

3. **Bağımlılıkları senkronize edin:**
   - Gradle sync işlemini bekleyin
   - Gerekirse "Sync Now" butonuna tıklayın

4. **Uygulamayı çalıştırın:**
   - Bir Android cihaz veya emülatör bağlayın
   - "Run" butonuna tıklayın (▶️)

## 📱 Kullanım

### Arayüz Özellikleri
- **Ekran:** Siyah arka plan üzerinde beyaz yazı
- **Tuşlar:** Siyah arka plan üzerinde beyaz yazı
- **Toolbar:** Mavi arka plan üzerinde "Hesap Makinesi" başlığı

### İşlevler
1. **Sayı Girişi:** 0-9 arası tuşlara basarak sayı girin
2. **Toplama:** `+` tuşuna basarak toplama işlemi yapın
3. **Temizleme:** `C` tuşuna basarak ekranı temizleyin

## 🔧 Geliştirme

### Ana Dosyalar

#### MainActivity.kt
```kotlin
// Ana aktivite sınıfı
class MainActivity : AppCompatActivity() {
    private lateinit var resultText: EditText
    private var firstNumber = 0
    private var isNewNumber = true
    
    // Sayı tuşları ve işlem tuşları için event handler'lar
}
```

#### activity_main.xml
```xml
<!-- Ana layout dosyası -->
<androidx.constraintlayout.widget.ConstraintLayout>
    <!-- Toolbar, EditText ve GridLayout içerir -->
</androidx.constraintlayout.widget.ConstraintLayout>
```

### Yeni Özellik Ekleme

1. **Yeni işlem eklemek için:**
   - `MainActivity.kt` dosyasında `setupOperationButtons()` metoduna yeni listener ekleyin
   - `activity_main.xml` dosyasında yeni buton tanımlayın

2. **UI değişiklikleri için:**
   - `app/src/main/res/layout/activity_main.xml` dosyasını düzenleyin
   - `app/src/main/res/values/` klasöründeki stil dosyalarını kullanın

## 📦 Build Konfigürasyonu

### Gradle Ayarları
- **Compile SDK:** 35
- **Min SDK:** 24
- **Target SDK:** 35
- **Java Version:** 11

### Bağımlılıklar
```kotlin
dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    // Test bağımlılıkları...
}
```

## 🧪 Test

### Unit Testler
```bash
./gradlew test
```

### Instrumented Testler
```bash
./gradlew connectedAndroidTest
```

## 📄 Lisans

Bu proje eğitim amaçlı geliştirilmiştir.

## 🤝 Katkıda Bulunma

1. Fork yapın
2. Feature branch oluşturun (`git checkout -b feature/AmazingFeature`)
3. Commit yapın (`git commit -m 'Add some AmazingFeature'`)
4. Push yapın (`git push origin feature/AmazingFeature`)
5. Pull Request oluşturun