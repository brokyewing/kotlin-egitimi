# Odev3 - Android Burger App

Bu proje, Kotlin ve Android Jetpack kullanılarak geliştirilmiş bir burger sipariş uygulamasıdır. Uygulama, kullanıcıların hamburger malzemelerini seçebileceği ve sepete ekleyebileceği bir arayüz sunar.

## 📱 Proje Hakkında

Bu uygulama, "getir" teması altında geliştirilmiş bir burger sipariş uygulamasıdır. Kullanıcılar hamburger malzemelerini seçebilir ve sepete ekleyebilir.

### Özellikler
- 🍔 Hamburger görseli ve açıklaması
- 🥬 Malzeme seçimi (soğan, marul, mayonez, domates, turşu, ketçap, sos)
- 🛒 Sepete ekleme butonu
- 💰 Fiyat gösterimi
- 🎨 Modern Material Design arayüzü

## 🛠️ Teknolojiler

- **Kotlin** - Programlama dili
- **Android SDK** - Platform
- **AndroidX** - Modern Android kütüphaneleri
- **Material Design** - UI bileşenleri
- **ConstraintLayout** - Layout sistemi
- **Gradle** - Build sistemi

## 📁 Proje Yapısı

```
Odev3/
├── app/
│   ├── build.gradle.kts                    # App-level build konfigürasyonu
│   ├── proguard-rules.pro                 # ProGuard kuralları
│   └── src/
│       ├── androidTest/                    # Android test dosyaları
│       │   └── java/com/example/odev3/
│       │       └── ExampleInstrumentedTest.kt
│       ├── main/                           # Ana kaynak kodları
│       │   ├── AndroidManifest.xml        # Uygulama manifest dosyası
│       │   ├── java/com/example/odev3/
│       │   │   └── MainActivity.kt        # Ana aktivite
│       │   └── res/                       # Kaynak dosyaları
│       │       ├── drawable/              # Görsel dosyalar
│       │       │   ├── getir.png          # Uygulama ikonu
│       │       │   ├── hamburger.jpg      # Hamburger görseli
│       │       │   ├── ic_launcher_background.xml
│       │       │   └── ic_launcher_foreground.xml
│       │       ├── layout/
│       │       │   └── activity_main.xml  # Ana layout dosyası
│       │       ├── mipmap-*/              # Uygulama ikonları
│       │       ├── values/
│       │       │   ├── colors.xml         # Renk tanımları
│       │       │   ├── strings.xml        # Metin tanımları
│       │       │   └── themes.xml         # Tema tanımları
│       │       ├── values-night/          # Gece modu
│       │       ├── values-tr/             # Türkçe dil desteği
│       │       └── xml/                   # XML konfigürasyon dosyaları
│       └── test/                          # Unit test dosyaları
│           └── java/com/example/odev3/
│               └── ExampleUnitTest.kt
├── build.gradle.kts                       # Proje-level build konfigürasyonu
├── gradle/
│   ├── libs.versions.toml                 # Dependency versiyonları
│   └── wrapper/                           # Gradle wrapper
├── gradlew                                # Gradle wrapper script (Unix)
├── gradlew.bat                            # Gradle wrapper script (Windows)
├── gradle.properties                      # Gradle özellikleri
├── local.properties                       # Yerel konfigürasyon
├── settings.gradle.kts                    # Proje ayarları
├── .gitignore                             # Git ignore kuralları
└── .gitattributes                        # Git özellikleri
```

## 🚀 Kurulum ve Çalıştırma

### Gereksinimler
- Android Studio (en son sürüm)
- Android SDK (API 24+)
- Java 11 veya üzeri
- Gradle 8.0+

### Kurulum Adımları

1. **Projeyi klonlayın:**
   ```bash
   git clone [repository-url]
   cd Odev3
   ```

2. **Android Studio'da açın:**
   - Android Studio'yu açın
   - "Open an existing project" seçeneğini tıklayın
   - `Odev3` klasörünü seçin

3. **Gradle sync yapın:**
   - Android Studio otomatik olarak Gradle sync yapacaktır
   - Eğer yapmazsa, "File > Sync Project with Gradle Files" seçeneğini kullanın

4. **Uygulamayı çalıştırın:**
   - Bir Android cihazı veya emülatör bağlayın
   - "Run" butonuna tıklayın veya `Shift + F10` tuşlarına basın

### Build Komutları

```bash
# Debug APK oluşturma
./gradlew assembleDebug

# Release APK oluşturma
./gradlew assembleRelease

# Test çalıştırma
./gradlew test

# Android test çalıştırma
./gradlew connectedAndroidTest
```

## 📱 Uygulama Özellikleri

### Ana Ekran
- **Hamburger Görseli**: Üst kısımda hamburger görseli
- **Başlık**: "Chicken Burger and Fries"
- **Açıklama**: Hamburger içeriği hakkında detaylı bilgi
- **Malzeme Seçimi**: 7 farklı malzeme seçeneği (Chip bileşenleri)
- **Fiyat**: ₺175,00
- **Sepete Ekle Butonu**: "ADD TO CART" butonu

### Malzeme Seçenekleri
- 🧅 Crispy Onion (Soğan)
- 🥬 Iceberg Lettuce (Marul)
- 🥄 Mayonnaise (Mayonez)
- 🍅 Tomato (Domates)
- 🥒 Pickles (Turşu)
- 🍅 Ketchup (Ketçap)
- 🥣 Dip Sauce (Sos)

## 🔧 Konfigürasyon

### Build Konfigürasyonu
- **Minimum SDK**: 24 (Android 7.0)
- **Target SDK**: 35 (Android 15)
- **Compile SDK**: 35
- **Java Version**: 11

### Dependencies
```kotlin
implementation(libs.androidx.core.ktx)
implementation(libs.androidx.appcompat)
implementation(libs.material)
implementation(libs.androidx.activity)
implementation(libs.androidx.constraintlayout)
```

## 🧪 Test

### Unit Testler
- `ExampleUnitTest.kt`: Temel unit test örneği

### Instrumented Testler
- `ExampleInstrumentedTest.kt`: UI test örneği

Test çalıştırma:
```bash
./gradlew test                    # Unit testler
./gradlew connectedAndroidTest    # Instrumented testler
```

## 📦 APK Oluşturma

### Debug APK
```bash
./gradlew assembleDebug
```
APK dosyası: `app/build/outputs/apk/debug/app-debug.apk`

### Release APK
```bash
./gradlew assembleRelease
```
APK dosyası: `app/build/outputs/apk/release/app-release.apk`

## 🌐 Dil Desteği

- **Türkçe**: `values-tr/strings.xml`
- **İngilizce**: `values/strings.xml` (varsayılan)

## 🎨 Tema ve Renkler

Renk tanımları `app/src/main/res/values/colors.xml` dosyasında bulunur:
- `yaziRenk1`: Metin rengi 1
- `yaziRenk2`: Metin rengi 2  
- `yaziRenk3`: Arka plan rengi

## 📝 Lisans

Bu proje eğitim amaçlı geliştirilmiştir.

## 🤝 Katkıda Bulunma

1. Fork yapın
2. Feature branch oluşturun (`git checkout -b feature/AmazingFeature`)
3. Commit yapın (`git commit -m 'Add some AmazingFeature'`)
4. Push yapın (`git push origin feature/AmazingFeature`)
5. Pull Request oluşturun

**Not**: Bu README dosyası proje yapısını ve kurulum adımlarını detaylı bir şekilde açıklamaktadır. Projeyi çalıştırmadan önce tüm gereksinimlerin karşılandığından emin olun. 