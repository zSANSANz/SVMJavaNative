# SVM Java Native

Proyek **SVMJavaNative** adalah implementasi algoritma **Support Vector Machine (SVM)** secara *native* menggunakan bahasa pemrograman **Java tanpa library machine learning eksternal**. Proyek ini cocok untuk kebutuhan penelitian, skripsi, eksperimen NLP, maupun pembelajaran konsep dasar SVM.

---

## 🚀 Fitur Utama

* Implementasi murni algoritma SVM (tanpa Weka, tanpa LibSVM).
* Preprocessing teks: normalisasi, stopword removal, tokenizing, stemming (jika diterapkan di utilitas).
* Pembobotan fitur (umumnya TF‑IDF atau bag-of-words sederhana).
* Training dan prediksi langsung dari Java.
* Struktur kode mudah dipahami untuk kebutuhan akademik.

---

## 📂 Struktur Folder

```
SVMJavaNative/
├── build/classes/                   # Hasil compile (otomatis)
├── src/svmradikalmetode/            # Package utama
│   ├── main/                        # Main class / entry point
│   ├── model/                       # Struktur model / vector
│   ├── preprocessing/               # Normalisasi dan tokenisasi
│   ├── svm/                         # Implementasi perhitungan SVM
│   └── utility/                     # Helper (parser, file reader, dll)
└── dataset/                         # Dataset training/testing (jika ada)
```

> **Catatan:** folder `build/` jangan pernah di‑commit → masukkan ke `.gitignore`.

---

## 🧠 Cara Kerja SVM Secara Singkat

1. **Preprocessing** → teks dibersihkan, di-tokenize, di-normalisasi.
2. **Ekstraksi fitur** → metode umum seperti TF‑IDF.
3. **Training SVM** → mencari hyperplane pemisah antar kelas.
4. **Testing / Prediksi** → input teks → konversi ke vektor → prediksi kelas.

Implementasi pada repo menggunakan pendekatan matematis manual (dot product, margin, bobot, dll).

---

## 🛠 Cara Menjalankan Project

### 1. Clone repository

```
git clone https://github.com/zSANSANz/SVMJavaNative.git
cd SVMJavaNative
```

### 2. Compile source Java

```
javac -d build/classes src/**/*.java
```

### 3. Jalankan program

```
java -cp build/classes svmradikalmetode.main.Main
```

Atau jika antum punya class main berbeda, sesuaikan nama package/class‑nya.

---

## ⚠️ Catatan Keamanan Penting

* **Jangan commit API key** (seperti kasus Yandex sebelumnya).
* Pastikan `.gitignore` mencakup:

  * `/build/`
  * `*.class`
  * file credential `.env` / `.key`
* Jika pernah terlanjur commit secret, lakukan **rewrite history** sebelum push.

---

## 🤝 Kontribusi

Silakan ajukan **issue** atau **pull request** untuk perbaikan atau penambahan fitur.

---

## 📄 Lisensi

Bebas digunakan untuk pembelajaran, penelitian, dan eksperimen akademik.

---

Kalau antum ingin ditambahkan:

* Flowchart alur SVM
* Penjelasan matematis hyperplane & support vectors
* Contoh dataset & cara preprocessing
* Cara refactor project biar lebih modular

Bilang saja mi, nanti ana tambahkan di README‑nya.
