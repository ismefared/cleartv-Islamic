# Clear TV Islamic — Pelancar Android TV

Repositori kerja peribadi untuk pelancar Android TV berasaskan projek huluan [Pokka99/cleartv-Islamic](https://github.com/Pokka99/cleartv-Islamic) yang dilesenkan di bawah MIT.

## Fungsi

- Skrin utama Android TV dan pelancar aplikasi
- Waktu solat JAKIM e-Solat dan sokongan azan
- Screensaver Islam dengan tarikh Hijri dan ayat Al-Quran
- Konfigurasi tema dan alat sokongan

## Bina dan uji

Jalankan dari direktori akar repositori:

```sh
cd launcher
./gradlew --no-daemon testDebugUnitTest assembleDebug
./gradlew --no-daemon testReleaseUnitTest assembleRelease
```

Fail binaan diletakkan di bawah `launcher/app/build/outputs/apk/`. GitHub Actions menjalankan pemeriksaan format, unit test serta binaan Debug/Release. Aliran kerja ini tidak memasang APK pada TV.

## Tema waktu solat

Fail `themes/islamik/config.json` ditetapkan kepada zon solat `WLY01`, dengan `azan` dan `reduced_motion` diaktifkan. Semak konfigurasi tema/peranti setempat sebelum menggunakan tema; jangan anggap tetapan TV yang sedang berfungsi patut ditindih.

## Keselamatan peranti

Binaan digunakan untuk pengesahan sahaja. Jangan pasang semula atau naik taraf aplikasi pada TV sehingga identiti tandatangan APK disahkan sepadan dengan pakej yang telah dipasang. Penambahbaikan UI ini tidak sepatutnya mengubah tetapan sistem TV, pemetaan butang Home, Wi-Fi, aksesibiliti atau pakej debloat.

## Lesen

Rujuk fail [LICENSE](LICENSE).