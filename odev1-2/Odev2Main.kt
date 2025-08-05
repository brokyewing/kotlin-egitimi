package com.example.kotlindersleri.odevler

fun main() {
    val odev = Odev2()

    //1.soru
    val derece = 100
    odev.cToFahrenheit(derece)

    //2.soru
    odev.dikdortgenCevre(5,10)

    //3.soru
    val sonuc = odev.faktoriyel(5)
    println("Girilen sayının faktöriyeli: $sonuc")

    //4.soru
    val kelime="İbrahim"
    odev.aHarfiSayisi(kelime)


    //5.soru
    val icAcilariToplami = odev.kenarSayisi(4)
    println("İç açılarının toplamı: $icAcilariToplami")

    //6.soru
    val toplamSaat = odev.toplamSaat(21)
    val ucret = odev.ucretHesapla(toplamSaat)
    println("Toplam çalışma saati: $toplamSaat")
    println("Toplam ödenen ücret: $ucret")

    //7.soru
    val kotaHesaplama = odev.KotaHesaplama(51)
    println("Toplam kota: $kotaHesaplama")

}

