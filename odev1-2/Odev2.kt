package com.example.kotlindersleri.odevler

class Odev2 {
    //1.soru Fahrenheit'dan Celsius'a dönüştürme metodu
    fun cToFahrenheit(derece: Int) {
        val fahrenheit = (derece * 9) / 5 + 32
        println("Fahrenheit : $fahrenheit")
    }

    //2.soru
    fun dikdortgenCevre(uzun: Int, kısa: Int) {
        val cevre = (uzun + kısa) * 2
        println("Çevre : $cevre")
    }

    //3.soru
    fun faktoriyel(sayi: Int): Int {
        var sonuc = 1
        for (i in 1..sayi) {
            sonuc *= i
        }
        return sonuc
    }

    //4.soru
    fun aHarfiSayisi(kelime: String) {
        val sayi = kelime.count { it == 'a' || it == 'A' }
        println("Kelime içinde 'a' harfi sayısı: $sayi")
    }

    //5.soru
    fun kenarSayisi(kenar: Int): Int {
        val sonuc = (kenar - 2) * 180
        return sonuc
    }

    //6.soru
    fun toplamSaat(gün: Int): Int {
        val toplamSaat = gün * 8
        return toplamSaat
    }

    fun ucretHesapla(saat: Int): Int {
        val ucret = when {
            saat <= 160 -> saat * 10
            else -> {
                val ucret1 = 160 * 10
                val ucret2 = (saat - 160) * 20
                ucret1 + ucret2

            }
        }
        return ucret
    }
    //7.soru
    fun KotaHesaplama(gb: Int): Int {
        val kota = when {
            gb <= 50 -> gb * 2
            else -> {
                val kota1 = 50 * 2
                val kota2 = (gb - 50) * 4
                kota1 + kota2
            }
            }
        return kota
    }
}