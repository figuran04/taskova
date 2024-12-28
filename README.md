### 1. **Mendownload dan Mengekstrak File Repository**

1. **Buka Repository GitHub:**
   - Pergi ke halaman repository GitHub terkait.
2. **Tekan Tombol "Code":**
   - Cari tombol hijau bertuliskan **"Code"** di bagian atas kanan daftar file di repository.
   - Klik tombol tersebut, lalu pilih **"Download ZIP"**.
3. **Download File ZIP:**
   - File akan diunduh dalam format ZIP, biasanya di folder `Downloads`.
4. **Ekstrak ZIP:**
   - Buka file ZIP yang telah diunduh (klik kanan > "Extract All" atau "Ekstrak di Sini").
   - Simpan hasil ekstraksi di lokasi yang mudah ditemukan, misalnya di folder `Documents` atau `Desktop`.

---

### 2. **Menyalakan XAMPP**

1. **Buka XAMPP:**
   - Jalankan aplikasi XAMPP dari komputer Anda.
2. **Start Modul Apache dan MySQL:**
   - Di panel XAMPP, cari modul **Apache** dan **MySQL**.
   - Klik tombol **Start** pada kedua modul tersebut hingga statusnya berubah menjadi **Running**.

---

### 3. **Membuat Database di phpMyAdmin**

1. **Buka phpMyAdmin:**
   - Buka browser dan ketikkan alamat: **`http://localhost/phpmyadmin`**.
2. **Buat Database Baru:**
   - Di bagian atas, klik tab **"Database"**.
   - Masukkan nama database baru: **`taskova`** pada kolom input, lalu klik **Create**.

---

### 4. **Menjalankan SQL Script**

1. **Buka Tab SQL:**
   - Pilih database **`taskova`** dari daftar di sidebar kiri.
   - Di bagian atas, klik menu **"SQL"**.
2. **Masukkan Perintah SQL:**
   - Copy dan paste kode berikut ke editor SQL:

```sql
CREATE TABLE tasks (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    category ENUM('Work', 'Personal', 'Other'),
    due_date DATE,
    priority ENUM('Important and urgent', 'Important but not urgent', 'Urgent but not important', 'Not urgent not important'),
    is_done ENUM('in progress', 'done') DEFAULT 'in progress',
    time_created TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    bio TEXT(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

ALTER TABLE tasks ADD COLUMN user_id INT;
ALTER TABLE tasks ADD FOREIGN KEY (user_id) REFERENCES users(user_id);
```

3. **Eksekusi SQL:**
   - Klik tombol **"Go"** atau **"Execute"** untuk menjalankan perintah SQL.
   - Jika berhasil, tabel **`tasks`** dan **`users`** akan dibuat dalam database **`taskova`**.

---

### 5. **Mengimpor Proyek ke NetBeans**

1. **Buka NetBeans:**
   - Jalankan aplikasi **NetBeans IDE** di komputer Anda.
2. **Import Proyek:**
   - Di menu atas, klik **"File"** > **"Import Project"** > **"From ZIP"**.
3. **Pilih File ZIP:**
   - Cari dan pilih file ZIP yang sudah diekstrak tadi, yaitu **`Taskovaa.zip`**.
   - Klik **OK** atau **Next** untuk melanjutkan.
4. **Selesaikan Impor:**
   - Setelah proses impor selesai, proyek akan muncul di daftar proyek di NetBeans.

---

### 6. **Menambahkan Modul Eksternal ke Proyek (Install Artifact Manually)**

1. **Buka Dependencies Proyek:**

   - Di tab **Projects**, cari proyek **Taskovaa** yang telah diimpor.
   - Klik ikon segitiga di sebelah nama proyek untuk memperluas strukturnya.
   - Temukan folder **Dependencies** dan klik ikon segitiga di sebelahnya.

2. **Install Artifact Secara Manual:**

   - Klik kanan pada **mysql-connector-j** di dalam folder **Dependencies**.
   - Pilih opsi **"Install Artifact Manually"**.

3. **Pilih File JAR:**
   - Akan muncul jendela dialog untuk memilih file JAR.
   - Arahkan ke lokasi file **mysql-connector-j-9.1.0.jar** yang ada di dalam folder projek yang telah diimport yaitu **Taskovaa.zip**, jika tidak menemukan maka extrak saja file **Taskovaa.zip** ke tempat yang mudah ditemui.
   - Klik **Open** untuk menambahkan artifact tersebut.
