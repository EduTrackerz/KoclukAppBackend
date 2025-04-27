# KoçlukApp

_Hafif bir Spring Boot projesi: Öğrenci ve Öğretmen kayıt/CRUD işlemleri._

## Teknolojiler
- Java 17
- Spring Boot (Spring Web, Spring Data JPA)
- H2 Database (in-memory)
- Lombok
- Jakarta Persistence & Validation
- JUnit (test klasöründe hazır)

## Kurulum & Çalıştırma
```bash
# repoyu klonlayın
git clone <repo-url>
cd koclukApp

# Maven wrapper ile çalıştırın
./mvnw clean install
./mvnw spring-boot:run
```  
Uygulama default 8080 portunda ayağa kalkar.

## Veritabanı (H2)
- H2 Console: http://localhost:8080/h2-console
- JDBC URL: `jdbc:h2:mem:testdb`
- User: `sa` (şifresiz)
- Konsolda tablo yapısı ve veri görebilirsiniz.

## Proje Yapısı
```
src/main/java/com/edutrackerz/koclukApp
├─ controller    (REST API katmanı)
├─ entities      (JPA @Entity sınıfları)
├─ dtos          (Data Transfer Object)
├─ converters    (DTO ⇄ Entity dönüşümleri)
├─ repository    (Spring Data JPA arayüzleri)
├─ enums         (Branch enum)
└─ KoclukAppApplication.java
```  

## API Endpoints
- **Students**  `/students`
  - `POST   /register`       : Yeni öğrenci ekle
  - `GET    /getall`         : Tüm öğrencileri listele
  - `GET    /getbyid?id=`    : Tek öğrenci getir
  - `GET    /getbyusername?username=` : Kullanıcı adına göre getir
  - `PUT    /update`         : Mevcut öğrenciyi güncelle
  - `DELETE /delete?id=`     : Sil

- **Teachers**  `/teachers`
  - Yukarıdaki student endpoints ile paralel
  - `branch` alanı için enum kullanılıyor (EDEBIYAT, MATEMATIK, …)

## Testler
```bash
./mvnw test
```

---
_Diğer geliştiriciler için hızlı referans dokümanı._ 