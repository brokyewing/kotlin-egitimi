# LezzetKapıda — Design System & Frontend Direction Contract

## 1. ECC `frontend-design-direction` Contract

- **Purpose**: Provide an immediate, high-density Turkish food & beverage ordering marketplace (inspired by **Yemeksepeti** and **Trendyol Yemek**) where customers can order hot meals, chilled/hot drinks, and desserts from separate kitchen/beverage stations, apply campaign coupons, and preview Customer Auth (`Giriş Yap`, `Üye Ol`, `Şifre Sıfırla`) and Merchant Portal (`İş Yeri / Restoran Girişi`) flows.
- **Audience**: Hungry customers scanning for meals + beverages quickly, and restaurant partners accessing the top merchant portal bar.
- **Tone**: Utilitarian, appetizing, dense, high-contrast warm stone (`#FAF8F5`) + flame terracotta (`#EA580C`) for hot meals + crisp frost blue (`#0284C7`) for the dedicated Beverage Station + dark slate (`#0B0F19`) for the top Merchant Partner strip.
- **Memorable Detail**: **Dual-Track "Yemek Ayrı · İçecek Ayrı" Split Board** visible in the very first desktop viewport — hot meals use compact horizontal Yemeksepeti/Trendyol-style row cards on the left track while chilled/hot beverages live in their own frost-accented **İçecek İstasyonu** column on the right, paired with smart cross-sell drink chips inside the sticky cart whenever a meal is added without a drink.
- **Target Viewports**: `["390x844", "1440x900"]`

### Content States Matrix

| State | Trigger | Visible Feedback & Safe Next Action |
|---|---|---|
| **Loading** | Initial `fetchFoodsAndCart()` in progress | `.state-card` inside `#menuBoardContainer` (`aria-live="polite"`) preserves layout stability. |
| **Empty** | Search query or category filter returns 0 items, or Cart has 0 items | Menu shows `.state-card` with a one-click `Tüm Menüyü Göster` reset button; Cart shows `.cart-empty-box`. |
| **Error** | Network/SQLite API unreachable or invalid form input | Menu displays `role="alert"` error card; modal forms mark invalid inputs with `aria-invalid="true"` and connected `role="alert"` field errors. |
| **Partial** | Meal added to cart without any beverage (`!hasDrinkInCart`) | Sticky cart activates `#cartDrinkUpsell` (`Yemeğin Yanına Soğuk İçecek Ekle: + Ayran · + Fanta · + Su`). |
| **Success** | Adding item, toggling `LEZZET40` coupon, or completing checkout | Inline card stepper updates (`− 1 +`), cart recalculates with `tabular-nums`, and `#toastBanner` (`role="status"`) confirms the action. |
| **Permission** | Submitting Customer Login, Register, Password Reset, or Merchant Portal forms | Displays `#modalPreviewFeedback` (`role="status"`) explaining that Auth/Merchant portals are interactive UI preview buttons while Cart/SQLite API runs live. |

---

## 2. Three-Layer Token Architecture (`tokens.css`)

1. **Layer 1 — Primitive Tokens**:
   - Warm Stone (`--color-stone-50` .. `--color-stone-300`), Slate (`--color-slate-950` .. `--color-slate-100`), Flame Orange (`--color-flame-50` .. `--color-flame-800`), Frost Cyan (`--color-frost-50` .. `--color-frost-800`), Amber (`--color-amber-50` .. `--color-amber-800`), Emerald, Ruby.
   - 4px Spacing grid (`--space-1` to `--space-24`), Typography scale (`--font-size-2xs` to `--font-size-2xl`), Radii (`--radius-xs` to `--radius-pill`).
2. **Layer 2 — Semantic Tokens**:
   - `--surface-page`, `--surface-card`, `--surface-subtle`, `--surface-partner`, `--brand-primary`, `--cat-food-*`, `--cat-drink-*`, `--cat-sweet-*`.
3. **Layer 3 — Component Tokens**:
   - `--partner-bar-bg`, `--dish-card-bg`, `--drink-station-bg`, `--cart-panel-bg`, `--cart-pairing-bg`.
   - Verified via `validate-tokens.cjs`: **0 hardcoded hex/rgb/px/rem violations** in `styles.css` and `app.js`.

---

## 3. Logo Design (`logo.svg`)

- Built on an integer `viewBox="0 0 256 256"` grid using a single compound `fill-rule="evenodd"` vector path (`#EA580C`).
- Combines a **Chef's Cloche Dome** on top with a **Serving Bowl (Yemek)** on bottom-left and a **Beverage Tumbler (İçecek)** on bottom-right.
- Verified via `svg_audit.py`: **100/100 production-readiness score, 0 issues**.
