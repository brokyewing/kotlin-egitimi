# 🍕 Broky Food - Android Yemek Sipariş Uygulaması

Broky Food, modern Android geliştirme teknolojileri kullanılarak geliştirilmiş bir yemek sipariş uygulamasıdır. Uygulama, kullanıcıların yemekleri görüntüleyebilmesi, sepete ekleyebilmesi ve sipariş verebilmesi için tasarlanmıştır.

## 📱 Uygulama Özellikleri

- **Çok Dilli Destek**: Türkçe ve İngilizce dil desteği
- **Modern UI/UX**: Material Design prensiplerine uygun arayüz
- **Sepet Yönetimi**: Yemek ekleme, çıkarma ve miktar kontrolü
- **Gerçek Zamanlı API**: PHP backend ile entegrasyon
- **Responsive Tasarım**: Farklı ekran boyutlarına uyumlu
- **Offline Destek**: Temel işlevler için offline çalışma

## 🛠️ Kullanılan Teknolojiler

### Core Technologies
- **Kotlin**: Ana programlama dili
- **Android SDK**: 34 (API Level 24+)
- **Gradle**: Build sistemi

### Architecture & Design Patterns
- **MVVM (Model-View-ViewModel)**: Mimari pattern
- **Repository Pattern**: Veri yönetimi
- **Dependency Injection**: Hilt ile DI
- **Navigation Component**: Fragment yönetimi

### Libraries & Dependencies
- **Retrofit 2.9.0**: HTTP API client
- **OkHttp 4.12.0**: HTTP client
- **Gson**: JSON serialization
- **Glide 4.16.0**: Image loading
- **Hilt 2.50**: Dependency injection
- **Navigation Component 2.7.7**: Navigation
- **Lifecycle Components 2.7.0**: Lifecycle management
- **Coroutines 1.7.3**: Asynchronous programming
- **Lottie 6.3.0**: Animasyonlar
- **Google Maps 18.2.0**: Harita entegrasyonu

## 📁 Proje Yapısı

```
app/
├── src/
│   ├── main/
│   │   ├── java/com/brokyfood/app/
│   │   │   ├── data/
│   │   │   │   ├── api/
│   │   │   │   │   └── FoodApiService.kt          # API servisleri
│   │   │   │   ├── model/
│   │   │   │   │   └── Food.kt                    # Veri modelleri
│   │   │   │   └── repository/
│   │   │   │       └── FoodRepository.kt          # Repository katmanı
│   │   │   ├── di/
│   │   │   │   └── NetworkModule.kt               # Dependency injection
│   │   │   ├── ui/
│   │   │   │   ├── home/
│   │   │   │   │   ├── HomeFragment.kt           # Ana sayfa
│   │   │   │   │   ├── HomeViewModel.kt          # Ana sayfa ViewModel
│   │   │   │   │   └── FoodAdapter.kt            # Yemek listesi adapter
│   │   │   │   ├── detail/
│   │   │   │   │   ├── DetailFragment.kt         # Detay sayfası
│   │   │   │   │   └── DetailViewModel.kt        # Detay ViewModel
│   │   │   │   └── cart/
│   │   │   │       ├── CartFragment.kt           # Sepet sayfası
│   │   │   │       ├── CartViewModel.kt          # Sepet ViewModel
│   │   │   │       └── CartAdapter.kt            # Sepet adapter
│   │   │   ├── util/
│   │   │   │   └── Constants.kt                  # Sabitler
│   │   │   ├── BrokyFoodApp.kt                   # Application sınıfı
│   │   │   └── MainActivity.kt                   # Ana aktivite
│   │   ├── res/
│   │   │   ├── layout/
│   │   │   │   ├── activity_main.xml             # Ana aktivite layout
│   │   │   │   ├── fragment_home.xml             # Ana sayfa layout
│   │   │   │   ├── fragment_detail.xml           # Detay sayfası layout
│   │   │   │   ├── fragment_cart.xml             # Sepet layout
│   │   │   │   ├── item_food.xml                 # Yemek item layout
│   │   │   │   └── item_cart.xml                 # Sepet item layout
│   │   │   ├── values/
│   │   │   │   ├── strings.xml                   # İngilizce metinler
│   │   │   │   ├── colors.xml                    # Renk tanımları
│   │   │   │   ├── themes.xml                    # Tema tanımları
│   │   │   │   └── styles.xml                    # Stil tanımları
│   │   │   ├── values-tr/
│   │   │   │   └── strings.xml                   # Türkçe metinler
│   │   │   ├── drawable/
│   │   │   │   ├── ic_*.xml                      # İkon dosyaları
│   │   │   │   └── *.xml                         # Drawable kaynakları
│   │   │   ├── navigation/
│   │   │   │   └── nav_graph.xml                 # Navigation graph
│   │   │   ├── menu/
│   │   │   │   ├── bottom_nav_menu.xml          # Alt navigasyon menüsü
│   │   │   │   └── main_menu.xml                 # Ana menü
│   │   │   ├── font/
│   │   │   │   ├── lobster_regular.ttf          # Özel font
│   │   │   │   └── lobster.xml                   # Font tanımı
│   │   │   └── anim/
│   │   │       └── slide_out_left.xml            # Animasyon dosyası
│   │   └── AndroidManifest.xml                   # Uygulama manifesti
│   ├── test/                                      # Unit testler
│   └── androidTest/                               # Instrumentation testler
├── build.gradle.kts                               # App level build config
└── proguard-rules.pro                            # ProGuard kuralları
```

## 🚀 Kurulum ve Çalıştırma

### Gereksinimler
- Android Studio Arctic Fox veya üzeri
- JDK 8 veya üzeri
- Android SDK API Level 24+
- Gradle 8.0+

### Kurulum Adımları

1. **Projeyi klonlayın**
   ```bash
   git clone [repository-url]
   cd Bitirme
   ```

2. **Gradle sync yapın**
   ```bash
   ./gradlew build
   ```

3. **Uygulamayı çalıştırın**
   - Android Studio'da projeyi açın
   - Bir Android cihaz veya emulator bağlayın
   - "Run" butonuna tıklayın

### Build Komutları

```bash
# Debug build
./gradlew assembleDebug

# Release build
./gradlew assembleRelease

# Test çalıştırma
./gradlew test

# Lint kontrolü
./gradlew lint
```

## 📊 API Entegrasyonu

Uygulama, PHP backend ile RESTful API üzerinden iletişim kurar:

### Endpoints
- `GET /tumYemekleriGetir.php` - Tüm yemekleri getir
- `POST /sepettekiYemekleriGetir.php` - Sepetteki yemekleri getir
- `POST /sepeteYemekEkle.php` - Sepete yemek ekle
- `POST /sepettenYemekSil.php` - Sepetten yemek sil

### Veri Modelleri
```kotlin
data class Food(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val price: Int
)

data class CartFood(
    val cartId: Int,
    val name: String,
    val imageUrl: String,
    val price: Int,
    val quantity: Int,
    val username: String
)
```

## 🎨 UI/UX Özellikleri

### Tasarım Prensipleri
- **Material Design 3**: Modern tasarım dili
- **Responsive Layout**: Farklı ekran boyutlarına uyum
- **Accessibility**: Erişilebilirlik desteği
- **Dark/Light Theme**: Tema desteği

### Özel Bileşenler
- **Custom Fonts**: Lobster font ailesi
- **Animations**: Lottie animasyonları
- **Gradient Overlays**: Görsel efektler
- **Bottom Navigation**: Modern navigasyon

## 🌐 Çok Dilli Destek

Uygulama Türkçe ve İngilizce dillerini destekler:

### Dil Dosyaları
- `values/strings.xml` - İngilizce metinler
- `values-tr/strings.xml` - Türkçe metinler

### Dil Değiştirme
- Menüden dil değiştirme özelliği
- Runtime dil değişimi
- Locale-based string yönetimi

## 🧪 Test Stratejisi

### Test Türleri
- **Unit Tests**: ViewModel ve Repository testleri
- **Integration Tests**: API entegrasyon testleri
- **UI Tests**: Espresso ile UI testleri

### Test Çalıştırma
```bash
# Unit testler
./gradlew test

# Instrumentation testler
./gradlew connectedAndroidTest
```

## 📱 Ekran Görüntüleri

### Ana Özellikler
- **Ana Sayfa**: Yemek listesi ve arama
- **Detay Sayfası**: Yemek detayları ve sepete ekleme
- **Sepet**: Sepet yönetimi ve sipariş özeti

## 🔧 Konfigürasyon

### Build Variants
- **Debug**: Geliştirme için
- **Release**: Production için

### ProGuard
- Release build'de kod küçültme
- Obfuscation desteği

## 📈 Performans Optimizasyonları

- **Image Caching**: Glide ile resim önbellekleme
- **Network Caching**: OkHttp ile ağ önbellekleme
- **Memory Management**: Efficient memory usage
- **Background Processing**: Coroutines ile async işlemler

## 🛡️ Güvenlik

- **HTTPS**: Güvenli ağ iletişimi
- **Input Validation**: Kullanıcı girdisi doğrulama
- **Error Handling**: Güvenli hata yönetimi

## 📝 Lisans

Bu proje [MIT License](LICENSE) altında lisanslanmıştır.

## 🤝 Katkıda Bulunma

1. Fork yapın
2. Feature branch oluşturun (`git checkout -b feature/amazing-feature`)
3. Commit yapın (`git commit -m 'Add amazing feature'`)
4. Push yapın (`git push origin feature/amazing-feature`)
5. Pull Request oluşturun