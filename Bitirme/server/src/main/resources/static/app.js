(() => {
  'use strict';

  const STORAGE_USER_KEY = 'lezzetkapida_session_user';
  const STORAGE_LANG_KEY = 'lezzetkapida_lang';

  function getOrCreateUser() {
    try {
      let u = localStorage.getItem(STORAGE_USER_KEY);
      if (!u || !/^[a-zA-Z0-9_.-]{1,64}$/.test(u)) {
        const rand = Math.random().toString(36).substring(2, 10);
        u = 'user_' + rand;
        localStorage.setItem(STORAGE_USER_KEY, u);
      }
      return u;
    } catch {
      return 'demo_guest';
    }
  }

  const SESSION_USER = getOrCreateUser();

  const DELIVERY_ZONES = [
    { tr: 'Defne, Hatay · Sümerler', en: 'Defne, Hatay · Sumerler', etaTr: '20-30 dk', etaEn: '20-30 min' },
    { tr: 'Antakya, Hatay · Merkez', en: 'Antakya, Hatay · Center', etaTr: '25-35 dk', etaEn: '25-35 min' },
    { tr: 'İskenderun, Hatay · Sahil', en: 'Iskenderun, Hatay · Coast', etaTr: '30-40 dk', etaEn: '30-40 min' }
  ];

  // Metadata per item: category ('food' | 'drink' | 'sweet')
  const ITEM_META = {
    'Izgara Somon': {
      cat: 'food',
      prepTr: '25-35 dk',
      prepEn: '25-35 min',
      rating: '4.9',
      tagTr: 'Deniz Ürünleri',
      tagEn: 'Seafood',
      descTr: 'Taze baharatlarla marine edilmiş Norveç somonu, ızgara sebze ve limon sosu ile.',
      descEn: 'Fresh herb-marinated Norwegian salmon served with grilled vegetables.'
    },
    'Izgara Tavuk': {
      cat: 'food',
      prepTr: '20-30 dk',
      prepEn: '20-30 min',
      rating: '4.8',
      tagTr: 'Yüksek Protein',
      tagEn: 'High Protein',
      descTr: 'Zeytinyağı ve kekikle dinlendirilmiş ızgara tavuk göğsü, fırın patates eşliğinde.',
      descEn: 'Olive oil and thyme marinated grilled chicken breast with roasted potatoes.'
    },
    'Köfte': {
      cat: 'food',
      prepTr: '20-30 dk',
      prepEn: '20-30 min',
      rating: '4.9',
      tagTr: 'Usta Izgara',
      tagEn: 'Chef Grill',
      descTr: 'Odun ateşinde pişmiş kasap köfte, közlenmiş biber, domates ve tereyağlı pilav.',
      descEn: 'Charcoal-grilled butcher meatballs with roasted peppers and rice pilaf.'
    },
    'Lazanya': {
      cat: 'food',
      prepTr: '25-35 dk',
      prepEn: '25-35 min',
      rating: '4.7',
      tagTr: 'İtalyan Mutfağı',
      tagEn: 'Italian Kitchen',
      descTr: 'El açması makarna katmanları arasında ağır ateşte pişmiş bolonez sos ve mozzarella.',
      descEn: 'Fresh pasta layers with slow-simmered bolognese sauce, bechamel and mozzarella.'
    },
    'Makarna': {
      cat: 'food',
      prepTr: '15-25 dk',
      prepEn: '15-25 min',
      rating: '4.7',
      tagTr: 'Taze Makarna',
      tagEn: 'Fresh Pasta',
      descTr: 'Fesleğenli domates sosu, taze parmesan rendesi ve sızma zeytinyağlı penne.',
      descEn: 'Al dente penne with rich basil tomato sauce and aged parmesan.'
    },
    'Pizza': {
      cat: 'food',
      prepTr: '20-30 dk',
      prepEn: '20-30 min',
      rating: '4.9',
      tagTr: 'Taş Fırın',
      tagEn: 'Stone Oven',
      descTr: 'İnce taş fırın hamuru, suda mozzarella, İtalyan domates sosu ve taze malzemeler.',
      descEn: 'Artisanal stone-oven crust topped with fresh mozzarella and tomato sauce.'
    },
    'Ayran': {
      cat: 'drink',
      prepTr: 'Soğuk +4°C · 300 ml',
      prepEn: 'Chilled +4°C · 300 ml',
      rating: '4.9',
      tagTr: 'Soğuk İçecek',
      tagEn: 'Cold Drink',
      descTr: 'Tam yağlı doğal yoğurttan bol köpüklü yayık ayranı (ayrı soğuk pakette).',
      descEn: 'Traditional frothy chilled yogurt drink in insulated cold packaging.'
    },
    'Fanta': {
      cat: 'drink',
      prepTr: 'Soğuk +4°C · 330 ml',
      prepEn: 'Chilled +4°C · 330 ml',
      rating: '4.8',
      tagTr: 'Gazlı İçecek',
      tagEn: 'Sparkling',
      descTr: 'Buz gibi soğutulmuş portakal aromalı gazlı içecek (330 ml kutu).',
      descEn: 'Ice-cold sparkling orange beverage (330 ml can).'
    },
    'Kahve': {
      cat: 'drink',
      prepTr: 'Sıcak · 250 ml',
      prepEn: 'Hot · 250 ml',
      rating: '4.9',
      tagTr: 'Sıcak Kahve',
      tagEn: 'Hot Coffee',
      descTr: 'Taze çekilmiş %100 Arabica çekirdeklerinden sızdırmaz termal bardakta.',
      descEn: 'Freshly brewed 100% Arabica coffee in a spill-proof thermal cup.'
    },
    'Su': {
      cat: 'drink',
      prepTr: 'Soğuk · 500 ml',
      prepEn: 'Chilled · 500 ml',
      rating: '4.9',
      tagTr: 'Doğal Kaynak',
      tagEn: 'Spring Water',
      descTr: 'Soğutulmuş doğal kaynak suyu (500 ml).',
      descEn: 'Chilled natural mountain spring water (500 ml).'
    },
    'Baklava': {
      cat: 'sweet',
      prepTr: '15-20 dk',
      prepEn: '15-20 min',
      rating: '5.0',
      tagTr: 'Taş Fırın Şerbetli',
      tagEn: 'Pistachio Pastry',
      descTr: 'Boz Antep fıstıklı, sade yağlı incecik kırk kat el açması sıcak baklava.',
      descEn: 'Forty layers of crisp phyllo pastry with roasted Antep pistachios.'
    },
    'Kadayıf': {
      cat: 'sweet',
      prepTr: '15-25 dk',
      prepEn: '15-25 min',
      rating: '4.9',
      tagTr: 'Hatay Usulü',
      tagEn: 'Hatay Style',
      descTr: 'Bol Antep fıstıklı ve çıtır tel kadayıftan günlük hazırlanmış porsiyon.',
      descEn: 'Golden crispy shredded wheat dessert filled with generous pistachios.'
    },
    'Sütlaç': {
      cat: 'sweet',
      prepTr: '15-20 dk',
      prepEn: '15-20 min',
      rating: '4.8',
      tagTr: 'Fırın Sütlü Tatlı',
      tagEn: 'Oven Baked',
      descTr: 'Toprak güveçte üzeri nar gibi kızarmış, kavrulmuş fındıklı fırın sütlaç.',
      descEn: 'Caramelized clay-pot rice pudding topped with crushed roasted hazelnuts.'
    },
    'Tiramisu': {
      cat: 'sweet',
      prepTr: '15-20 dk',
      prepEn: '15-20 min',
      rating: '4.9',
      tagTr: 'Mascarpone & Espresso',
      tagEn: 'Italian Dessert',
      descTr: 'Gerçek mascarpone kreması ve taze espresso ile ıslatılmış kedi dili tatlısı.',
      descEn: 'Classic Italian dessert with mascarpone cream and espresso-soaked ladyfingers.'
    }
  };

  const EN_NAMES = {
    'Ayran': 'Ayran (Chilled Yogurt Drink)',
    'Baklava': 'Pistachio Baklava',
    'Fanta': 'Fanta Orange (330 ml)',
    'Izgara Somon': 'Grilled Norwegian Salmon',
    'Izgara Tavuk': 'Herb Grilled Chicken',
    'Kadayıf': 'Crispy Pistachio Kadayif',
    'Kahve': 'Freshly Brewed Coffee',
    'Köfte': 'Charcoal Butcher Meatballs',
    'Lazanya': 'Classic Beef Lasagna',
    'Makarna': 'Penne Arrabbiata',
    'Pizza': 'Stone-Oven Margherita Pizza',
    'Su': 'Natural Spring Water (500 ml)',
    'Sütlaç': 'Oven-Baked Rice Pudding',
    'Tiramisu': 'Artisanal Tiramisu'
  };

  const I18N = {
    tr: {
      partnerKicker: 'İŞ ORTAĞI & RESTORAN AĞI',
      merchantPortal: 'İş Yeri / Restoran Girişi',
      merchantApply: 'Restoran Ortağımız Olun',
      courierApply: 'Kurye Başvurusu',
      apkDownload: 'Android APK İndir',
      langToggle: 'EN',
      brandSub: 'Yemek & İçecek Sipariş Platformu',
      searchPlaceholder: 'Yemek, soğuk içecek veya tatlı ara (Köfte, Ayran, Baklava...)',
      btnReset: 'Şifre Sıfırla',
      btnLogin: 'Giriş Yap',
      btnRegister: 'Üye Ol',
      storeStatus: 'AÇIK · HIZLI TESLİMAT',
      storeHeading: 'LezzetKapıda Merkez Mutfak & İçecek İstasyonu',
      minOrder: 'Min. Sepet ₺100',
      deliveryFeeMeta: 'Ücretsiz Teslimat',
      packingNote: 'Sıcak Yemek & Soğuk İçecek Ayrı Termal Çanta',
      couponApply: '200₺+ Siparişe 40₺ İndirim Uygula',
      couponApplied: 'LEZZET40 Aktif (-₺40 İndirim)',
      separateNote: 'Yemek Ayrı · İçecek Ayrı İstasyon',
      catAll: 'Tümü · Bölünmüş Görünüm',
      catFood: 'Ana Yemekler',
      catDrink: 'İçecekler',
      catSweet: 'Tatlılar',
      sortBy: 'Sırala:',
      sortRec: 'Önerilen',
      sortAsc: 'Fiyat: Düşükten Yükseğe',
      sortDesc: 'Fiyat: Yüksekten Düşüğe',
      sortName: 'İsme Göre (A-Z)',
      secFoodTitle: 'Ana Yemekler & Izgaralar',
      secFoodSub: 'Sıcak mutfak hattı — sipariş anında hazırlanır',
      secDrinkTitle: 'İçecek İstasyonu (Ayrı Paket)',
      secDrinkSub: 'Yemekten bağımsız soğuk (+4°C) ve sıcak içecek bölümü',
      secSweetTitle: 'Tatlılar & Taş Fırın',
      secSweetSub: 'Günlük taze şerbetli ve sütlü tatlılar',
      itemUnit: 'ürün',
      addFoodBtn: '+ Ekle',
      addDrinkBtn: '+ İçecek',
      cartTitle: 'Sepetim',
      cartEmptyTitle: 'Sepetiniz şu an boş',
      cartEmptySub: 'Sol taraftan sıcak yemek veya sağdaki İçecek İstasyonu\'ndan soğuk içecek ekleyebilirsiniz.',
      upsellTitle: 'Yemeğin Yanına Soğuk İçecek Ekle:',
      subtotal: 'Ara Toplam',
      discount: 'LEZZET40 Kupon İndirimi',
      delivery: 'Teslimat Ücreti',
      free: 'Ücretsiz',
      total: 'Toplam Tutar',
      checkout: 'Siparişi Onayla',
      badgeFood: 'Yemek',
      badgeDrink: 'İçecek',
      badgeSweet: 'Tatlı',
      noMatchTitle: 'Aramanızla eşleşen yemek veya içecek bulunamadı.',
      resetFilterBtn: 'Tüm Menüyü Göster',
      toastAdded: 'sepete eklendi',
      toastOrderOk: 'Siparişiniz onaylandı! Sıcak yemek ve soğuk içecek paketleriniz hazırlanıyor.',
      toastCouponOn: 'LEZZET40 kuponu aktif edildi (200₺ ve üzeri sepetlerde 40₺ indirim).',
      toastCouponOff: 'LEZZET40 kuponu kaldırıldı.',
      toastZoneChanged: 'Teslimat bölgesi güncellendi:',
      previewNote: 'Önizleme Modu: Giriş, Üyelik, Şifre Sıfırlama ve İş Yeri Portalı butonları vitrin önizlemesi olarak aktif bırakılmıştır. Menü, kategori ayrımı ve sepet işlemleri doğrudan canlı Kotlin + SQLite veritabanı üzerinde çalışır.'
    },
    en: {
      partnerKicker: 'MERCHANT & PARTNER NETWORK',
      merchantPortal: 'Merchant / Restaurant Login',
      merchantApply: 'Become a Restaurant Partner',
      courierApply: 'Courier Application',
      apkDownload: 'Download Android APK',
      langToggle: 'TR',
      brandSub: 'Food & Beverage Delivery Platform',
      searchPlaceholder: 'Search meals, chilled drinks or desserts (Pizza, Ayran, Baklava...)',
      btnReset: 'Reset Password',
      btnLogin: 'Log In',
      btnRegister: 'Sign Up',
      storeStatus: 'OPEN · FAST DELIVERY',
      storeHeading: 'LezzetKapıda Central Kitchen & Beverage Station',
      minOrder: 'Min. Order ₺100',
      deliveryFeeMeta: 'Free Delivery',
      packingNote: 'Hot Meals & Cold Drinks Packed Separately',
      couponApply: 'Apply ₺40 Off on Orders ₺200+',
      couponApplied: 'LEZZET40 Applied (-₺40 Off)',
      separateNote: 'Separate Food & Drink Stations',
      catAll: 'All · Split Station View',
      catFood: 'Main Courses',
      catDrink: 'Beverages',
      catSweet: 'Desserts',
      sortBy: 'Sort:',
      sortRec: 'Recommended',
      sortAsc: 'Price: Low to High',
      sortDesc: 'Price: High to Low',
      sortName: 'Name (A-Z)',
      secFoodTitle: 'Main Courses & Grills',
      secFoodSub: 'Hot kitchen line — cooked fresh to order',
      secDrinkTitle: 'Beverage Station (Separate Pack)',
      secDrinkSub: 'Dedicated chilled (+4°C) and hot drinks bar',
      secSweetTitle: 'Desserts & Bakery',
      secSweetSub: 'Freshly baked pastries and milk desserts',
      itemUnit: 'items',
      addFoodBtn: '+ Add',
      addDrinkBtn: '+ Drink',
      cartTitle: 'My Cart',
      cartEmptyTitle: 'Your cart is currently empty',
      cartEmptySub: 'Add a hot dish from the kitchen column or a chilled drink from the Beverage Station.',
      upsellTitle: 'Pair a Chilled Drink with Your Meal:',
      subtotal: 'Subtotal',
      discount: 'LEZZET40 Coupon Discount',
      delivery: 'Delivery Fee',
      free: 'Free',
      total: 'Total Amount',
      checkout: 'Confirm Order',
      badgeFood: 'Meal',
      badgeDrink: 'Drink',
      badgeSweet: 'Sweet',
      noMatchTitle: 'No food or beverage matched your search.',
      resetFilterBtn: 'Show Full Menu',
      toastAdded: 'added to cart',
      toastOrderOk: 'Order confirmed! Your hot food and cold beverage packs are being prepared.',
      toastCouponOn: 'LEZZET40 coupon activated (₺40 off on orders ₺200+).',
      toastCouponOff: 'LEZZET40 coupon removed.',
      toastZoneChanged: 'Delivery zone updated:',
      previewNote: 'Preview Mode: Customer Login, Registration, Password Reset and Merchant Portal buttons are active as interactive UI previews. Menu filtering and Cart operations run live against the Kotlin + SQLite backend.'
    }
  };

  let state = {
    lang: localStorage.getItem(STORAGE_LANG_KEY) === 'en' ? 'en' : 'tr',
    zoneIndex: 0,
    couponActive: false,
    activeCategory: 'all',
    sortBy: 'recommended',
    searchQuery: '',
    foods: [],
    cart: [],
    loading: true,
    error: null,
    previousFocus: null
  };

  const el = id => document.getElementById(id);

  function t(key) {
    return I18N[state.lang][key] || I18N.tr[key] || key;
  }

  function getDisplayName(rawName) {
    if (state.lang === 'en' && EN_NAMES[rawName]) {
      return EN_NAMES[rawName];
    }
    return rawName;
  }

  function getMeta(rawName) {
    return ITEM_META[rawName] || {
      cat: 'food',
      prepTr: '20-30 dk',
      prepEn: '20-30 min',
      rating: '4.8',
      tagTr: 'Özel Seçim',
      tagEn: 'Chef Choice',
      descTr: 'Günlük taze malzemelerle hazırlanır.',
      descEn: 'Prepared daily with fresh ingredients.'
    };
  }

  function getCartEntry(foodName) {
    return state.cart.find(item => item.yemek_adi === foodName) || null;
  }

  let toastTimer = null;
  function showToast(msg) {
    const banner = el('toastBanner');
    if (!banner) return;
    banner.textContent = msg;
    banner.classList.add('visible');
    if (toastTimer) clearTimeout(toastTimer);
    toastTimer = setTimeout(() => {
      banner.classList.remove('visible');
    }, 3200);
  }

  function syncStaticLabels() {
    document.documentElement.lang = state.lang;
    el('partnerKicker').textContent = t('partnerKicker');
    el('lblMerchantPortal').textContent = t('merchantPortal');
    el('lblMerchantApply').textContent = t('merchantApply');
    el('lblCourierApply').textContent = t('courierApply');
    el('lblApkDownload').textContent = t('apkDownload');
    el('lblLangToggle').textContent = t('langToggle');
    el('lblBrandSub').textContent = t('brandSub');

    const zone = DELIVERY_ZONES[state.zoneIndex];
    el('lblAddressText').textContent = state.lang === 'en' ? zone.en : zone.tr;
    el('lblAddressEta').textContent = state.lang === 'en' ? zone.etaEn : zone.etaTr;

    el('searchInput').placeholder = t('searchPlaceholder');
    el('lblBtnReset').textContent = t('btnReset');
    el('lblBtnLogin').textContent = t('btnLogin');
    el('lblBtnRegister').textContent = t('btnRegister');

    el('lblStoreStatus').textContent = t('storeStatus');
    el('storefrontHeading').textContent = t('storeHeading');
    el('lblMinOrder').textContent = t('minOrder');
    el('lblDeliveryFeeMeta').textContent = t('deliveryFeeMeta');
    el('lblPackingNote').textContent = t('packingNote');
    el('lblCouponText').textContent = state.couponActive ? t('couponApplied') : t('couponApply');
    el('btnApplyCoupon').setAttribute('aria-pressed', state.couponActive ? 'true' : 'false');
    el('lblSeparateNote').textContent = t('separateNote');

    el('lblCatAll').textContent = t('catAll');
    el('lblCatFood').textContent = t('catFood');
    el('lblCatDrink').textContent = t('catDrink');
    el('lblCatSweet').textContent = t('catSweet');

    el('lblSortBy').textContent = t('sortBy');
    el('optSortRec').textContent = t('sortRec');
    el('optSortAsc').textContent = t('sortAsc');
    el('optSortDesc').textContent = t('sortDesc');
    el('optSortName').textContent = t('sortName');

    el('lblCartTitle').textContent = t('cartTitle');
    el('lblUpsellTitle').textContent = t('upsellTitle');
    el('lblSubtotal').textContent = t('subtotal');
    el('lblDiscount').textContent = t('discount');
    el('lblDelivery').textContent = t('delivery');
    el('valDelivery').textContent = t('free');
    el('lblTotal').textContent = t('total');
    el('lblBtnCheckout').textContent = t('checkout');
  }

  function sortItems(items) {
    const copy = [...items];
    if (state.sortBy === 'price-asc') {
      copy.sort((a, b) => Number(a.yemek_fiyat) - Number(b.yemek_fiyat));
    } else if (state.sortBy === 'price-desc') {
      copy.sort((a, b) => Number(b.yemek_fiyat) - Number(a.yemek_fiyat));
    } else if (state.sortBy === 'name-asc') {
      copy.sort((a, b) => getDisplayName(a.yemek_adi).localeCompare(getDisplayName(b.yemek_adi), state.lang));
    }
    return copy;
  }

  function filterByQuery(items) {
    const q = state.searchQuery.trim().toLowerCase();
    if (!q) return items;
    return items.filter(item => {
      const m = getMeta(item.yemek_adi);
      const haystack = [
        item.yemek_adi,
         getDisplayName(item.yemek_adi),
        m.tagTr,
        m.tagEn,
        m.descTr,
        m.descEn
      ].join(' ').toLowerCase();
      return haystack.includes(q);
    });
  }

  function buildActionControl(food, isDrink) {
    const cartEntry = getCartEntry(food.yemek_adi);
    if (cartEntry && Number(cartEntry.yemek_siparis_adet) > 0) {
      const qty = Number(cartEntry.yemek_siparis_adet);
      const wrap = document.createElement('div');
      wrap.className = 'card-inline-stepper';

      const btnMinus = document.createElement('button');
      btnMinus.type = 'button';
      btnMinus.className = 'card-step-btn';
      btnMinus.textContent = '−';
      btnMinus.setAttribute('aria-label', getDisplayName(food.yemek_adi) + ' azalt');
      btnMinus.addEventListener('click', () => changeCartQuantity(cartEntry, qty - 1));

      const spanQty = document.createElement('span');
      spanQty.className = 'card-step-qty';
      spanQty.textContent = String(qty);

      const btnPlus = document.createElement('button');
      btnPlus.type = 'button';
      btnPlus.className = 'card-step-btn';
      btnPlus.textContent = '+';
      btnPlus.setAttribute('aria-label', getDisplayName(food.yemek_adi) + ' artır');
      btnPlus.addEventListener('click', () => changeCartQuantity(cartEntry, qty + 1));

      wrap.append(btnMinus, spanQty, btnPlus);
      return wrap;
    }

    const btn = document.createElement('button');
    btn.type = 'button';
    btn.className = isDrink ? 'btn-drink-add' : 'btn-dish-add';
    btn.textContent = isDrink ? t('addDrinkBtn') : t('addFoodBtn');
    btn.addEventListener('click', () => addToCart(food, 1));
    return btn;
  }

  function createDishRowCard(food) {
    const meta = getMeta(food.yemek_adi);
    const inCart = Boolean(getCartEntry(food.yemek_adi));

    const article = document.createElement('article');
    article.className = 'dish-row-card' + (inCart ? ' in-cart' : '');

    const infoCol = document.createElement('div');
    infoCol.className = 'dish-info-col';

    const tagRow = document.createElement('div');
    tagRow.className = 'dish-tag-row';

    const tagSpan = document.createElement('span');
    tagSpan.className = 'dish-tag' + (meta.cat === 'sweet' ? ' sweet' : '');
    tagSpan.textContent = state.lang === 'en' ? meta.tagEn : meta.tagTr;

    const ratingSpan = document.createElement('span');
    ratingSpan.className = 'dish-rating-chip';
    ratingSpan.textContent = '★ ' + meta.rating;

    tagRow.append(tagSpan, ratingSpan);

    const titleEl = document.createElement('h3');
    titleEl.className = 'dish-title';
    titleEl.textContent = getDisplayName(food.yemek_adi);

    const descEl = document.createElement('p');
    descEl.className = 'dish-desc';
    descEl.textContent = state.lang === 'en' ? meta.descEn : meta.descTr;

    const bottomMeta = document.createElement('div');
    bottomMeta.className = 'dish-bottom-meta';

    const priceEl = document.createElement('span');
    priceEl.className = 'dish-price';
    priceEl.textContent = '₺' + food.yemek_fiyat;

    const prepEl = document.createElement('span');
    prepEl.className = 'dish-prep';
    prepEl.textContent = state.lang === 'en' ? meta.prepEn : meta.prepTr;

    bottomMeta.append(priceEl, prepEl);
    infoCol.append(tagRow, titleEl, descEl, bottomMeta);

    const mediaCol = document.createElement('div');
    mediaCol.className = 'dish-media-col';

    const thumbBox = document.createElement('div');
    thumbBox.className = 'dish-thumb-box';

    const img = document.createElement('img');
    img.src = 'yemekler/resimler/' + food.yemek_resim_adi;
    img.alt = getDisplayName(food.yemek_adi);
    img.width = 76;
    img.height = 60;
    img.loading = 'lazy';
    thumbBox.appendChild(img);

    const actionCtrl = buildActionControl(food, false);
    mediaCol.append(thumbBox, actionCtrl);

    article.append(infoCol, mediaCol);
    return article;
  }

  function createDrinkStationCard(food) {
    const meta = getMeta(food.yemek_adi);
    const inCart = Boolean(getCartEntry(food.yemek_adi));

    const card = document.createElement('article');
    card.className = 'drink-card' + (inCart ? ' in-cart' : '');

    const thumb = document.createElement('div');
    thumb.className = 'drink-thumb';
    const img = document.createElement('img');
    img.src = 'yemekler/resimler/' + food.yemek_resim_adi;
    img.alt = getDisplayName(food.yemek_adi);
    img.width = 52;
    img.height = 52;
    img.loading = 'lazy';
    thumb.appendChild(img);

    const info = document.createElement('div');
    info.className = 'drink-info';

    const badges = document.createElement('div');
    badges.className = 'drink-badges';
    const tempBadge = document.createElement('span');
    tempBadge.className = 'drink-temp-badge';
    tempBadge.textContent = state.lang === 'en' ? meta.prepEn : meta.prepTr;
    badges.appendChild(tempBadge);

    const nameEl = document.createElement('h3');
    nameEl.className = 'drink-name';
    nameEl.textContent = getDisplayName(food.yemek_adi);

    const descEl = document.createElement('p');
    descEl.className = 'drink-desc';
    descEl.textContent = state.lang === 'en' ? meta.descEn : meta.descTr;

    info.append(badges, nameEl, descEl);

    const actionCol = document.createElement('div');
    actionCol.className = 'drink-action-col';

    const priceEl = document.createElement('span');
    priceEl.className = 'drink-price';
    priceEl.textContent = '₺' + food.yemek_fiyat;

    const actionCtrl = buildActionControl(food, true);
    actionCol.append(priceEl, actionCtrl);

    card.append(thumb, info, actionCol);
    return card;
  }

  function buildSectionHeader(catType, titleText, subText, count) {
    const header = document.createElement('div');
    header.className = 'section-header';

    const wrap = document.createElement('div');
    wrap.className = 'section-title-wrap';

    const badge = document.createElement('div');
    badge.className = 'section-icon-badge ' + catType;
    badge.setAttribute('aria-hidden', 'true');
    if (catType === 'drink') {
      badge.innerHTML = '<svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><path d="M17 8h1a4 4 0 1 1 0 8h-1"></path><path d="M3 8h14v9a4 4 0 0 1-4 4H7a4 4 0 0 1-4-4Z"></path></svg>';
    } else if (catType === 'sweet') {
      badge.innerHTML = '<svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><path d="M20 21v-8a2 2 0 0 0-2-2H6a2 2 0 0 0-2 2v8"></path><path d="M2 21h20"></path></svg>';
    } else {
      badge.innerHTML = '<svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><path d="M3 2v7c0 1.1.9 2 2 2h4a2 2 0 0 0 2-2V2"></path><path d="M7 2v20"></path><path d="M21 15V2v0a5 5 0 0 0-5 5v6c0 1.1.9 2 2 2h3Zm0 0v7"></path></svg>';
    }

    const textCol = document.createElement('div');
    const h2 = document.createElement('h2');
    h2.className = 'section-heading';
    h2.textContent = titleText;
    const p = document.createElement('p');
    p.className = 'section-subheading';
    p.textContent = subText;
    textCol.append(h2, p);

    wrap.append(badge, textCol);

    const pill = document.createElement('span');
    pill.className = 'section-pill-count';
    pill.textContent = count + ' ' + t('itemUnit');

    header.append(wrap, pill);
    return header;
  }

  function renderMenuBoard() {
    const container = el('menuBoardContainer');
    if (!container) return;
    container.innerHTML = '';

    if (state.loading) {
      const loadingCard = document.createElement('div');
      loadingCard.className = 'state-card';
      loadingCard.textContent = 'Menü yükleniyor...';
      container.appendChild(loadingCard);
      return;
    }

    if (state.error) {
      const errCard = document.createElement('div');
      errCard.className = 'state-card';
      errCard.setAttribute('role', 'alert');
      errCard.textContent = state.error;
      container.appendChild(errCard);
      return;
    }

    const searched = sortItems(filterByQuery(state.foods));
    const foodsList = searched.filter(f => getMeta(f.yemek_adi).cat === 'food');
    const drinksList = searched.filter(f => getMeta(f.yemek_adi).cat === 'drink');
    const sweetsList = searched.filter(f => getMeta(f.yemek_adi).cat === 'sweet');

    // Update category tab counts
    el('countCatAll').textContent = String(searched.length);
    el('countCatFood').textContent = String(foodsList.length);
    el('countCatDrink').textContent = String(drinksList.length);
    el('countCatSweet').textContent = String(sweetsList.length);

    const visibleItems = state.activeCategory === 'all'
      ? searched
      : searched.filter(f => getMeta(f.yemek_adi).cat === state.activeCategory);

    if (visibleItems.length === 0) {
      const emptyBox = document.createElement('div');
      emptyBox.className = 'state-card';
      const msg = document.createElement('p');
      msg.textContent = t('noMatchTitle');
      const resetBtn = document.createElement('button');
      resetBtn.type = 'button';
      resetBtn.className = 'btn-auth btn-auth-register';
      resetBtn.textContent = t('resetFilterBtn');
      resetBtn.addEventListener('click', () => {
        state.searchQuery = '';
        state.activeCategory = 'all';
        el('searchInput').value = '';
        updateCategoryTabsUI();
        renderMenuBoard();
      });
      emptyBox.append(msg, resetBtn);
      container.appendChild(emptyBox);
      return;
    }

    const board = document.createElement('div');
    const isSplitView = state.activeCategory === 'all' && drinksList.length > 0 && (foodsList.length > 0 || sweetsList.length > 0);
    board.className = 'dual-menu-board' + (isSplitView ? '' : ' single-track');

    // Left/Main Track: Food + Desserts
    if (state.activeCategory === 'all' || state.activeCategory === 'food' || state.activeCategory === 'sweet') {
      const foodTrack = document.createElement('div');
      foodTrack.className = 'food-track-column';

      if ((state.activeCategory === 'all' || state.activeCategory === 'food') && foodsList.length > 0) {
        const secFood = document.createElement('section');
        secFood.className = 'menu-section';
        secFood.setAttribute('aria-label', t('secFoodTitle'));
        secFood.appendChild(buildSectionHeader('food', t('secFoodTitle'), t('secFoodSub'), foodsList.length));

        const gridFood = document.createElement('div');
        gridFood.className = 'dish-list-grid';
        foodsList.forEach(item => gridFood.appendChild(createDishRowCard(item)));
        secFood.appendChild(gridFood);
        foodTrack.appendChild(secFood);
      }

      if ((state.activeCategory === 'all' || state.activeCategory === 'sweet') && sweetsList.length > 0) {
        const secSweet = document.createElement('section');
        secSweet.className = 'menu-section';
        secSweet.setAttribute('aria-label', t('secSweetTitle'));
        secSweet.appendChild(buildSectionHeader('sweet', t('secSweetTitle'), t('secSweetSub'), sweetsList.length));

        const gridSweet = document.createElement('div');
        gridSweet.className = 'dish-list-grid';
        sweetsList.forEach(item => gridSweet.appendChild(createDishRowCard(item)));
        secSweet.appendChild(gridSweet);
        foodTrack.appendChild(secSweet);
      }

      if (foodTrack.childNodes.length > 0) {
        board.appendChild(foodTrack);
      }
    }

    // Right Track (or Full Track when drink tab active): Dedicated Beverage Station
    if ((state.activeCategory === 'all' || state.activeCategory === 'drink') && drinksList.length > 0) {
      const drinkStation = document.createElement('section');
      drinkStation.className = 'drink-station-panel';
      drinkStation.setAttribute('aria-label', t('secDrinkTitle'));
      drinkStation.appendChild(buildSectionHeader('drink', t('secDrinkTitle'), t('secDrinkSub'), drinksList.length));

      const drinkListEl = document.createElement('div');
      drinkListEl.className = 'drink-station-list';
      drinksList.forEach(item => drinkListEl.appendChild(createDrinkStationCard(item)));
      drinkStation.appendChild(drinkListEl);
      board.appendChild(drinkStation);
    }

    container.appendChild(board);
  }

  function renderCart() {
    const container = el('cartItemsContainer');
    const summaryBox = el('cartSummaryBox');
    const badge = el('cartCountBadge');
    const upsellBox = el('cartDrinkUpsell');
    const upsellChips = el('cartUpsellChips');

    const totalItems = state.cart.reduce((acc, item) => acc + Number(item.yemek_siparis_adet || 0), 0);
    badge.textContent = totalItems + ' ' + t('itemUnit');
    container.innerHTML = '';

    if (state.cart.length === 0) {
      summaryBox.hidden = true;
      upsellBox.hidden = true;

      const empty = document.createElement('div');
      empty.className = 'cart-empty-box';
      empty.innerHTML =
        '<div class="cart-empty-icon" aria-hidden="true">' +
        '<svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><circle cx="9" cy="21" r="1"></circle><circle cx="20" cy="21" r="1"></circle><path d="M1 1h4l2.68 13.39a2 2 0 0 0 2 1.61h9.72a2 2 0 0 0 2-1.61L23 6H6"></path></svg>' +
        '</div>';
      const titleP = document.createElement('strong');
      titleP.textContent = t('cartEmptyTitle');
      const subP = document.createElement('p');
      subP.textContent = t('cartEmptySub');
      empty.append(titleP, subP);
      container.appendChild(empty);
      return;
    }

    let subtotal = 0;
    let hasDrinkInCart = false;

    state.cart.forEach(item => {
      const qty = Number(item.yemek_siparis_adet || 1);
      const price = Number(item.yemek_fiyat || 0);
      const lineTotal = qty * price;
      subtotal += lineTotal;

      const meta = getMeta(item.yemek_adi);
      if (meta.cat === 'drink') hasDrinkInCart = true;

      const row = document.createElement('div');
      row.className = 'cart-item-row';

      const img = document.createElement('img');
      img.className = 'cart-item-thumb';
      img.src = 'yemekler/resimler/' + item.yemek_resim_adi;
      img.alt = getDisplayName(item.yemek_adi);
      img.width = 48;
      img.height = 48;

      const details = document.createElement('div');
      details.className = 'cart-item-details';

      const titleRow = document.createElement('div');
      titleRow.className = 'cart-item-title-row';

      const catDot = document.createElement('span');
      catDot.className = 'cart-item-cat-dot ' + meta.cat;
      catDot.textContent = meta.cat === 'drink' ? t('badgeDrink') : meta.cat === 'sweet' ? t('badgeSweet') : t('badgeFood');

      const nameEl = document.createElement('span');
      nameEl.className = 'cart-item-name';
      nameEl.textContent = getDisplayName(item.yemek_adi);

      titleRow.append(catDot, nameEl);

      const priceEl = document.createElement('div');
      priceEl.className = 'cart-item-price';
      priceEl.textContent = '₺' + price + ' × ' + qty + ' = ₺' + lineTotal;

      details.append(titleRow, priceEl);

      const stepper = document.createElement('div');
      stepper.className = 'cart-stepper';

      const btnDec = document.createElement('button');
      btnDec.type = 'button';
      btnDec.className = 'cart-step-btn';
      btnDec.textContent = '−';
      btnDec.setAttribute('aria-label', getDisplayName(item.yemek_adi) + ' azalt');
      btnDec.addEventListener('click', () => changeCartQuantity(item, qty - 1));

      const valSpan = document.createElement('span');
      valSpan.className = 'cart-step-val';
      valSpan.textContent = String(qty);

      const btnInc = document.createElement('button');
      btnInc.type = 'button';
      btnInc.className = 'cart-step-btn';
      btnInc.textContent = '+';
      btnInc.setAttribute('aria-label', getDisplayName(item.yemek_adi) + ' artır');
      btnInc.addEventListener('click', () => changeCartQuantity(item, qty + 1));

      stepper.append(btnDec, valSpan, btnInc);
      row.append(img, details, stepper);
      container.appendChild(row);
    });

    // Smart Beverage Pairing Cross-Sell when user has food/sweet but no drink
    if (!hasDrinkInCart) {
      const availableDrinks = state.foods.filter(f => getMeta(f.yemek_adi).cat === 'drink');
      if (availableDrinks.length > 0) {
        upsellChips.innerHTML = '';
        availableDrinks.forEach(drink => {
          const chip = document.createElement('button');
          chip.type = 'button';
          chip.className = 'btn-upsell-chip';
          chip.textContent = '+ ' + drink.yemek_adi + ' (₺' + drink.yemek_fiyat + ')';
          chip.addEventListener('click', () => addToCart(drink, 1));
          upsellChips.appendChild(chip);
        });
        upsellBox.hidden = false;
      } else {
        upsellBox.hidden = true;
      }
    } else {
      upsellBox.hidden = true;
    }

    const discountAmount = state.couponActive && subtotal >= 200 ? 40 : 0;
    const finalTotal = Math.max(0, subtotal - discountAmount);

    el('valSubtotal').textContent = '₺' + subtotal;
    el('rowDiscount').hidden = discountAmount === 0;
    el('valDiscount').textContent = '-₺' + discountAmount;
    el('valTotal').textContent = '₺' + finalTotal;
    summaryBox.hidden = false;
  }

  async function fetchFoodsAndCart() {
    state.loading = true;
    renderMenuBoard();
    try {
      const [foodsRes, cartRes] = await Promise.all([
        fetch('yemekler/tumYemekleriGetir.php'),
        fetch('yemekler/sepettekiYemekleriGetir.php', {
          method: 'POST',
          headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
          body: new URLSearchParams({ kullanici_adi: SESSION_USER })
        })
      ]);

      const foodsData = await foodsRes.json();
      const cartData = await cartRes.json();

      state.foods = Array.isArray(foodsData.yemekler) ? foodsData.yemekler : [];
      state.cart = Array.isArray(cartData.sepet_yemekler) ? cartData.sepet_yemekler : [];
      state.loading = false;
      state.error = null;
    } catch {
      state.loading = false;
      state.error = 'Menü sunucusuna bağlanılamadı.';
    }
    renderMenuBoard();
    renderCart();
  }

  async function refreshCartOnly() {
    try {
      const res = await fetch('yemekler/sepettekiYemekleriGetir.php', {
        method: 'POST',
        headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
        body: new URLSearchParams({ kullanici_adi: SESSION_USER })
      });
      const data = await res.json();
      state.cart = Array.isArray(data.sepet_yemekler) ? data.sepet_yemekler : [];
      renderCart();
      renderMenuBoard();
    } catch {
      // Ignore transient cart refresh error
    }
  }

  async function addToCart(food, quantity) {
    try {
      await fetch('yemekler/sepeteYemekEkle.php', {
        method: 'POST',
        headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
        body: new URLSearchParams({
          yemek_adi: food.yemek_adi,
          yemek_resim_adi: food.yemek_resim_adi,
          yemek_fiyat: String(food.yemek_fiyat),
          yemek_siparis_adet: String(quantity),
          kullanici_adi: SESSION_USER
        })
      });
      await refreshCartOnly();
      showToast(getDisplayName(food.yemek_adi) + ' ' + t('toastAdded'));
    } catch {
      showToast('Sepete eklenirken hata oluştu.');
    }
  }

  async function changeCartQuantity(cartItem, newQty) {
    try {
      if (newQty <= 0) {
        await fetch('yemekler/sepettenYemekSil.php', {
          method: 'POST',
          headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
          body: new URLSearchParams({
            sepet_yemek_id: String(cartItem.sepet_yemek_id),
            kullanici_adi: SESSION_USER
          })
        });
      } else {
        await fetch('yemekler/sepetAdetGuncelle.php', {
          method: 'POST',
          headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
          body: new URLSearchParams({
            sepet_yemek_id: String(cartItem.sepet_yemek_id),
            yemek_siparis_adet: String(newQty),
            kullanici_adi: SESSION_USER
          })
        });
      }
      await refreshCartOnly();
    } catch {
      showToast('Sepet güncellenemedi.');
    }
  }

  async function checkoutCart() {
    if (state.cart.length === 0) return;
    try {
      const res = await fetch('yemekler/siparisTamamla.php', {
        method: 'POST',
        headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
        body: new URLSearchParams({ kullanici_adi: SESSION_USER })
      });
      const data = await res.json();
      if (data.success === 1) {
        await refreshCartOnly();
        showToast(t('toastOrderOk'));
      }
    } catch {
      showToast('Sipariş tamamlanamadı.');
    }
  }

  function updateCategoryTabsUI() {
    const tabs = document.querySelectorAll('.cat-tab');
    tabs.forEach(tab => {
      const isMatch = tab.getAttribute('data-cat') === state.activeCategory;
      tab.classList.toggle('active', isMatch);
      tab.setAttribute('aria-selected', isMatch ? 'true' : 'false');
    });
  }

  // Accessible Modal Management (Focus Trap + Focus Restoration + Validation)
  function switchModalTab(tabId) {
    const tabs = document.querySelectorAll('.modal-tab');
    const panels = {
      login: el('panelModalLogin'),
      register: el('panelModalRegister'),
      reset: el('panelModalReset'),
      merchant: el('panelModalMerchant')
    };

    tabs.forEach(btn => {
      const active = btn.getAttribute('data-tab') === tabId;
      btn.setAttribute('aria-selected', active ? 'true' : 'false');
    });

    Object.entries(panels).forEach(([key, panel]) => {
      if (panel) panel.hidden = key !== tabId;
    });

    const notice = el('modalPreviewFeedback');
    if (notice) notice.hidden = true;
  }

  function openModal(tabId) {
    state.previousFocus = document.activeElement;
    const backdrop = el('authModalBackdrop');
    backdrop.hidden = false;
    switchModalTab(tabId);
    const firstInput = backdrop.querySelector('.modal-panel:not([hidden]) input');
    if (firstInput) {
      firstInput.focus();
    } else {
      el('authModalDialog').focus();
    }
  }

  function closeModal() {
    const backdrop = el('authModalBackdrop');
    backdrop.hidden = true;
    if (state.previousFocus && typeof state.previousFocus.focus === 'function') {
      state.previousFocus.focus();
    }
  }

  function validateFormAndPreview(formEl, fields) {
    let valid = true;
    fields.forEach(({ inputId, errId, minLen }) => {
      const input = el(inputId);
      const err = el(errId);
      const val = input.value.trim();
      const failed = !val || (minLen && val.length < minLen);
      input.setAttribute('aria-invalid', failed ? 'true' : 'false');
      err.hidden = !failed;
      if (failed && valid) {
        input.focus();
        valid = false;
      }
    });

    const notice = el('modalPreviewFeedback');
    notice.textContent = t('previewNote');
    notice.hidden = false;
    if (valid) {
      showToast(t('previewNote'));
    }
  }

  function bindEvents() {
    // Top Partner Bar Buttons
    el('btnMerchantPortal').addEventListener('click', () => openModal('merchant'));
    el('btnMerchantApply').addEventListener('click', () => {
      openModal('merchant');
      const notice = el('modalPreviewFeedback');
      notice.textContent = t('previewNote');
      notice.hidden = false;
    });
    el('btnCourierApply').addEventListener('click', () => {
      showToast(t('previewNote'));
    });

    // Language Toggle
    el('btnLangToggle').addEventListener('click', () => {
      state.lang = state.lang === 'tr' ? 'en' : 'tr';
      try {
        localStorage.setItem(STORAGE_LANG_KEY, state.lang);
      } catch {
        // ignore storage error
      }
      syncStaticLabels();
      renderMenuBoard();
      renderCart();
    });

    // Delivery Zone Switcher
    el('btnAddressSwitch').addEventListener('click', () => {
      state.zoneIndex = (state.zoneIndex + 1) % DELIVERY_ZONES.length;
      syncStaticLabels();
      const zone = DELIVERY_ZONES[state.zoneIndex];
      showToast(t('toastZoneChanged') + ' ' + (state.lang === 'en' ? zone.en : zone.tr));
    });

    // Campaign Coupon Toggle
    el('btnApplyCoupon').addEventListener('click', () => {
      state.couponActive = !state.couponActive;
      syncStaticLabels();
      renderCart();
      showToast(state.couponActive ? t('toastCouponOn') : t('toastCouponOff'));
    });

    // Customer Auth Buttons
    el('btnOpenLogin').addEventListener('click', () => openModal('login'));
    el('btnOpenRegister').addEventListener('click', () => openModal('register'));
    el('btnOpenReset').addEventListener('click', () => openModal('reset'));

    // Modal Controls
    el('btnCloseModal').addEventListener('click', closeModal);
    el('authModalBackdrop').addEventListener('click', e => {
      if (e.target === el('authModalBackdrop')) closeModal();
    });

    document.querySelectorAll('.modal-tab').forEach(tabBtn => {
      tabBtn.addEventListener('click', () => {
        switchModalTab(tabBtn.getAttribute('data-tab'));
      });
    });

    // Keyboard Shortcuts ('/' for search, Escape & Tab trap for modal)
    document.addEventListener('keydown', e => {
      const backdrop = el('authModalBackdrop');
      if (!backdrop.hidden) {
        if (e.key === 'Escape') {
          e.preventDefault();
          closeModal();
          return;
        }
        if (e.key === 'Tab') {
          const focusables = backdrop.querySelectorAll(
            'button:not([hidden]), .modal-panel:not([hidden]) input, [tabindex]:not([tabindex="-1"])'
          );
          if (focusables.length > 0) {
            const first = focusables[0];
            const last = focusables[focusables.length - 1];
            if (e.shiftKey && document.activeElement === first) {
              e.preventDefault();
              last.focus();
            } else if (!e.shiftKey && document.activeElement === last) {
              e.preventDefault();
              first.focus();
            }
          }
        }
        return;
      }

      if (e.key === '/' && document.activeElement !== el('searchInput') && !['INPUT', 'TEXTAREA', 'SELECT'].includes(document.activeElement.tagName)) {
        e.preventDefault();
        el('searchInput').focus();
      }
    });

    // Form Submissions (Accessible Validation + Preview Notice)
    el('formLogin').addEventListener('submit', e => {
      e.preventDefault();
      validateFormAndPreview(el('formLogin'), [
        { inputId: 'loginEmail', errId: 'errLoginEmail', minLen: 3 },
        { inputId: 'loginPassword', errId: 'errLoginPassword', minLen: 4 }
      ]);
    });

    el('formRegister').addEventListener('submit', e => {
      e.preventDefault();
      validateFormAndPreview(el('formRegister'), [
        { inputId: 'regName', errId: 'errRegName', minLen: 2 },
        { inputId: 'regEmail', errId: 'errRegEmail', minLen: 3 },
        { inputId: 'regPassword', errId: 'errRegPassword', minLen: 6 }
      ]);
    });

    el('formReset').addEventListener('submit', e => {
      e.preventDefault();
      validateFormAndPreview(el('formReset'), [
        { inputId: 'resetEmail', errId: 'errResetEmail', minLen: 3 }
      ]);
    });

    el('formMerchant').addEventListener('submit', e => {
      e.preventDefault();
      validateFormAndPreview(el('formMerchant'), [
        { inputId: 'merchantCode', errId: 'errMerchantCode', minLen: 3 },
        { inputId: 'merchantPass', errId: 'errMerchantPass', minLen: 4 }
      ]);
    });

    // Category Filter Tabs
    document.querySelectorAll('.cat-tab').forEach(tab => {
      tab.addEventListener('click', () => {
        state.activeCategory = tab.getAttribute('data-cat');
        updateCategoryTabsUI();
        renderMenuBoard();
      });
    });

    // Sort Select
    el('sortSelect').addEventListener('change', e => {
      state.sortBy = e.target.value;
      renderMenuBoard();
    });

    // Search Input
    el('searchInput').addEventListener('input', e => {
      state.searchQuery = e.target.value;
      renderMenuBoard();
    });

    // Checkout Button
    el('btnCheckout').addEventListener('click', checkoutCart);
  }

  document.addEventListener('DOMContentLoaded', () => {
    syncStaticLabels();
    bindEvents();
    fetchFoodsAndCart();
  });
})();
