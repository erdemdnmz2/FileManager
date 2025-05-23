Dosya Yönetim Sistemi(The English version is below)
Bu proje, kullanıcıların güvenli bir şekilde dosya yükleyip yönetebileceği, JWT tabanlı kimlik doğrulama ve yetkilendirme mekanizmasına sahip bir dosya yönetim sistemidir.


Özellikler
Kullanıcı kaydı ve girişi (JWT ile güvenli oturum yönetimi)
Dosya yükleme, indirme, silme ve listeleme
Dosya içeriklerinin byte dizisi olarak veritabanında saklanması
Dosya türüne göre uygun önizleme (ör. resim, PDF)
Temel hata yönetimi


Kullanılan Teknolojiler
Java 17+
Spring Boot
Spring Security (JWT)
Maven
MySQL (veya tercih edilen bir veritabanı)
Kurulum
Depoyu klonlayın:

git clone https://github.com/erdemdnmz2/filemanager.git
cd filemanager

Bağımlılıkları yükleyin:


mvn clean install

Kişisel yapılandırma için src/main/resources/application.properties dosyasını düzenleyin:

spring.datasource.url=jdbc:mysql://localhost:3306/veritabani_adi
spring.datasource.username=kullanici_adi
spring.datasource.password=sifre

# JWT ayarları
jwt.secret=kendinize_ozel_gizli_anahtar
jwt.expiration=86400000
veritabani_adi, kullanici_adi ve sifre alanlarını kendi MySQL bilgilerinizle değiştirin.
jwt.secret kısmına güçlü ve gizli bir anahtar girin.

Uygulamayı başlatın:

mvn spring-boot:run
Uygulama varsayılan olarak http://localhost:8080 adresinde çalışacaktır.

Yapılacaklar / Gelecek Çalışmalar
Rol tabanlı yetkilendirme
Dosya paylaşımı
Gelişmiş dosya önizleme
Dosya sürümleme
Kapsamlı hata yönetimi ve loglama
Dosya formatı dönüştürme desteği (ör. DOCX'ten PDF'e)
Performans iyileştirmeleri

Lisans
Bu proje şu anda herhangi bir açık kaynak lisansı ile lisanslanmamıştır. Tüm hakları saklıdır.

File Management System
This project is a file management system that allows users to securely upload and manage files, featuring JWT-based authentication and authorization mechanisms.


Features
User registration and login (secure session management with JWT)
File upload, download, delete, and listing
Storing file contents as byte arrays in the database
Appropriate file preview based on file type (e.g., image, PDF)
Basic error handling

Technologies Used
Java 17+
Spring Boot
Spring Security (JWT)
Maven
MySQL (or preferred database)

Installation
Clone the repository:

git clone https://github.com/erdemdnmz2/filemanager.git
cd filemanager
Install dependencies:

mvn clean install
Edit the src/main/resources/application.properties file for personal configuration:

spring.datasource.url=jdbc:mysql://localhost:3306/database_name
spring.datasource.username=username
spring.datasource.password=password

# JWT settings
jwt.secret=your_custom_secret_key
jwt.expiration=86400000
Replace database_name, username, and password with your own MySQL credentials.
Enter a strong and secret key for jwt.secret.

Start the application:

mvn spring-boot:run
The application will run by default at http://localhost:8080.

To Do / Future Work
Role-based authorization
File sharing
Advanced file preview
File versioning
Comprehensive error handling and logging
File format conversion support (e.g., DOCX to PDF)
Performance improvements

License
This project is currently not licensed under any open source license. All rights reserved.
