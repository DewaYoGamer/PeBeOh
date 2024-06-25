DROP DATABASE IF EXISTS db_pbo;
CREATE DATABASE db_pbo;
USE db_pbo;
CREATE TABLE tb_users (
    id_user INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL,
    password VARCHAR(50) NOT NULL,
    tipe_user ENUM('user', 'perusahaan') NOT NULL
);
CREATE TABLE tb_perusahaan (
    id_perusahaan INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    id_user INT NOT NULL,
    nama_perusahaan VARCHAR(50) NOT NULL,
    tentang_perusahaan TEXT,
    FOREIGN KEY (id_user) REFERENCES tb_users (id_user)
);
CREATE TABLE tb_pengguna (
    id_pengguna INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    id_user INT NOT NULL,
    nama_pengguna VARCHAR(50) NOT NULL,
    alamat TEXT,
    email VARCHAR(50),
    no_telp VARCHAR(15),
    usia INT,
    jenis_kelamin ENUM('Laki-laki', 'Perempuan'),
    FOREIGN KEY (id_user) REFERENCES tb_users (id_user)
);
CREATE TABLE tb_lowongan (
    id_lowongan INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    id_perusahaan INT NOT NULL,
    nama_lowongan VARCHAR(50) NOT NULL,
    gaji INT UNSIGNED,
    kategori enum('IT','Sekretaris', 'Media Sosial Strategis', 'Sales', 'Design Grafis', 'Arsitek'),
    deskripsi TEXT,
    date_posted DATE,
    daerah VARCHAR(50),
    enabled BOOLEAN,
    FOREIGN KEY (id_perusahaan) REFERENCES tb_perusahaan (id_perusahaan)

);
CREATE TABLE tb_lamaran (
    id_lamaran INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    id_lowongan INT NOT NULL,
    id_pengguna INT NOT NULL,
    pengalaman TEXT,
    skill TEXT,
    accepted BOOLEAN,
    FOREIGN KEY (id_lowongan) REFERENCES tb_lowongan (id_lowongan),
    FOREIGN KEY (id_pengguna) REFERENCES tb_pengguna (id_pengguna)
);