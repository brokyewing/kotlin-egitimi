const i18n = {
  tr: {
    brandSub: "Kotlin Android Uygulaması & Canlı API Demosu",
    apiStatus: "API Aktif (SQLite)",
    apkBtn: "📱 Android APK İndir",
    heroTitle: "Ne yemek istersiniz?",
    heroDesc: "Bu ekran, Android uygulamasının bağlandığı Kotlin + SQLite arka ucuyla doğrudan konuşur. Yemek arayabilir, sepete ekleyebilir, adet güncelleyebilir ve siparişi tamamlayabilirsiniz.",
    searchPlaceholder: "Yemek ara (örn. Baklava, Pizza, Köfte)...",
    cartTitle: "Sepetim",
    emptyCart: "Sepetiniz boş. Menüden yemek ekleyebilirsiniz.",
    addBtn: "+ Sepete Ekle",
    totalItemsLabel: "Toplam Ürün:",
    totalPriceLabel: "Toplam Tutar:",
    checkoutBtn: "Siparişi Tamamla",
    addedToast: "Sepete eklendi",
    orderDoneToast: "Siparişiniz başarıyla tamamlandı!",
    errorToast: "İşlem sırasında bir hata oluştu",
    itemsUnit: "adet"
  },
  en: {
    brandSub: "Kotlin Android App & Live API Demo",
    apiStatus: "API Online (SQLite)",
    apkBtn: "📱 Download Android APK",
    heroTitle: "What would you like to eat?",
    heroDesc: "This web preview talks directly to the same Kotlin + SQLite backend powering the Android app. Search dishes, add to cart, update quantities, and complete your order.",
    searchPlaceholder: "Search food (e.g. Baklava, Pizza, Köfte)...",
    cartTitle: "My Cart",
    emptyCart: "Your cart is empty. Add items from the menu.",
    addBtn: "+ Add to Cart",
    totalItemsLabel: "Total Items:",
    totalPriceLabel: "Total Price:",
    checkoutBtn: "Complete Order",
    addedToast: "Added to cart",
    orderDoneToast: "Order completed successfully!",
    errorToast: "An error occurred",
    itemsUnit: "items"
  }
};

let lang = localStorage.getItem("broky_lang") || "tr";
let username = localStorage.getItem("broky_user");
if (!username) {
  username = "web_" + Math.random().toString(36).substring(2, 10);
  localStorage.setItem("broky_user", username);
}

let allFoods = [];
let cartItems = [];

function showToast(msg) {
  const el = document.getElementById("toast");
  el.textContent = msg;
  el.classList.add("show");
  setTimeout(() => el.classList.remove("show"), 2200);
}

function applyLang() {
  const t = i18n[lang];
  document.documentElement.lang = lang;
  document.getElementById("langBtn").textContent = lang === "tr" ? "EN" : "TR";
  document.getElementById("brandSub").textContent = t.brandSub;
  document.getElementById("apiStatus").textContent = t.apiStatus;
  document.getElementById("apkBtn").textContent = t.apkBtn;
  document.getElementById("heroTitle").textContent = t.heroTitle;
  document.getElementById("heroDesc").textContent = t.heroDesc;
  document.getElementById("searchInput").placeholder = t.searchPlaceholder;
  document.getElementById("cartTitle").textContent = t.cartTitle;
  document.getElementById("totalItemsLabel").textContent = t.totalItemsLabel;
  document.getElementById("totalPriceLabel").textContent = t.totalPriceLabel;
  document.getElementById("checkoutBtn").textContent = t.checkoutBtn;
  renderFoods();
  renderCart();
}

async function loadFoods() {
  const res = await fetch("yemekler/tumYemekleriGetir.php");
  const data = await res.json();
  allFoods = data.yemekler || [];
  renderFoods();
}

async function loadCart() {
  const body = new URLSearchParams({ kullanici_adi: username });
  const res = await fetch("yemekler/sepettekiYemekleriGetir.php", {
    method: "POST",
    headers: { "Content-Type": "application/x-www-form-urlencoded" },
    body
  });
  const data = await res.json();
  cartItems = data.sepet_yemekler || [];
  renderCart();
}

function renderFoods() {
  const q = document.getElementById("searchInput").value.trim().toLowerCase();
  const filtered = q ? allFoods.filter(f => f.yemek_adi.toLowerCase().includes(q)) : allFoods;
  const grid = document.getElementById("foodGrid");
  grid.innerHTML = "";
  const t = i18n[lang];

  filtered.forEach(food => {
    const card = document.createElement("div");
    card.className = "food-card";
    card.innerHTML = `
      <div class="food-img-wrap">
        <img src="yemekler/resimler/${encodeURIComponent(food.yemek_resim_adi)}" alt="${food.yemek_adi}" loading="lazy" />
      </div>
      <div class="food-body">
        <div class="food-title">${food.yemek_adi}</div>
        <div class="food-footer">
          <span class="price">₺${food.yemek_fiyat}</span>
          <button class="btn btn-primary" type="button">${t.addBtn}</button>
        </div>
      </div>
    `;
    card.querySelector("button").addEventListener("click", () => addToCart(food));
    grid.appendChild(card);
  });
}

function renderCart() {
  const t = i18n[lang];
  const list = document.getElementById("cartList");
  const summary = document.getElementById("cartSummary");
  const totalCount = cartItems.reduce((acc, item) => acc + Number(item.yemek_siparis_adet), 0);
  const totalPrice = cartItems.reduce((acc, item) => acc + Number(item.yemek_siparis_adet) * Number(item.yemek_fiyat), 0);

  document.getElementById("cartBadge").textContent = `${totalCount} ${t.itemsUnit}`;
  list.innerHTML = "";

  if (cartItems.length === 0) {
    list.innerHTML = `<div class="empty-msg">${t.emptyCart}</div>`;
    summary.style.display = "none";
    return;
  }

  summary.style.display = "block";
  document.getElementById("totalItemsVal").textContent = `${totalCount} ${t.itemsUnit}`;
  document.getElementById("totalPriceVal").textContent = `₺${totalPrice}`;

  cartItems.forEach(item => {
    const row = document.createElement("div");
    row.className = "cart-item";
    row.innerHTML = `
      <img src="yemekler/resimler/${encodeURIComponent(item.yemek_resim_adi)}" alt="${item.yemek_adi}" />
      <div class="cart-item-info">
        <div class="cart-item-name">${item.yemek_adi}</div>
        <div class="cart-item-sub">₺${item.yemek_fiyat} × ${item.yemek_siparis_adet} = <strong>₺${item.yemek_fiyat * item.yemek_siparis_adet}</strong></div>
      </div>
      <div class="qty-controls">
        <button class="qty-btn" data-act="dec" type="button">−</button>
        <span style="min-width:18px;text-align:center;font-size:13px;font-weight:600;">${item.yemek_siparis_adet}</span>
        <button class="qty-btn" data-act="inc" type="button">+</button>
      </div>
    `;
    row.querySelector('[data-act="dec"]').addEventListener("click", () => updateQty(item, Number(item.yemek_siparis_adet) - 1));
    row.querySelector('[data-act="inc"]').addEventListener("click", () => updateQty(item, Number(item.yemek_siparis_adet) + 1));
    list.appendChild(row);
  });
}

async function addToCart(food) {
  const t = i18n[lang];
  const body = new URLSearchParams({
    yemek_adi: food.yemek_adi,
    yemek_resim_adi: food.yemek_resim_adi,
    yemek_fiyat: String(food.yemek_fiyat),
    yemek_siparis_adet: "1",
    kullanici_adi: username
  });
  const res = await fetch("yemekler/sepeteYemekEkle.php", {
    method: "POST",
    headers: { "Content-Type": "application/x-www-form-urlencoded" },
    body
  });
  const data = await res.json();
  if (data.success === 1) {
    showToast(`${food.yemek_adi} — ${t.addedToast}`);
    await loadCart();
  } else {
    showToast(t.errorToast);
  }
}

async function updateQty(item, newQty) {
  const body = new URLSearchParams({
    sepet_yemek_id: String(item.sepet_yemek_id),
    kullanici_adi: username,
    yemek_siparis_adet: String(newQty)
  });
  await fetch("yemekler/sepetAdetGuncelle.php", {
    method: "POST",
    headers: { "Content-Type": "application/x-www-form-urlencoded" },
    body
  });
  await loadCart();
}

async function checkout() {
  const t = i18n[lang];
  const body = new URLSearchParams({ kullanici_adi: username });
  const res = await fetch("yemekler/siparisTamamla.php", {
    method: "POST",
    headers: { "Content-Type": "application/x-www-form-urlencoded" },
    body
  });
  const data = await res.json();
  if (data.success === 1) {
    showToast(t.orderDoneToast);
    await loadCart();
  } else {
    showToast(t.errorToast);
  }
}

document.getElementById("langBtn").addEventListener("click", () => {
  lang = lang === "tr" ? "en" : "tr";
  localStorage.setItem("broky_lang", lang);
  applyLang();
});
document.getElementById("searchInput").addEventListener("input", renderFoods);
document.getElementById("checkoutBtn").addEventListener("click", checkout);

applyLang();
loadFoods();
loadCart();
