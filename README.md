# Koçluk Merkezi Uygulaması - Backend Reposu

Bu repo, öğrencilerin akademik süreçlerini yönetmek, öğretmenlerin ve koçların öğrenci performansını takip etmesini kolaylaştırmayı sağlamak için hazırlıyor olduğumuz Koçluk App projemizin backend kodlarını kapsamaktadır.

## Kullanılan Teknolojiler
- **Backend Framework:** Spring Boot
- **Veritabanı:** H2 Database 
- **Proje Yönetimi:** Maven
- **Versiyon Kontrol:** Git + GitHub

## H2 Database Erişimi

- Uygulama çalıştırıldığında, H2 konsolu otomatik olarak aktif olur. Erişmek için tarayıcınız üzerinden aşağıdaki adımları takip ediniz:
- **Erişim URL'si:**  
  ```
  http://localhost:8080/h2-console
  ```

- **Bağlantı Bilgileri:**

  | Alan | Değer |
  |-----|------|
  | Driver Class | `org.h2.Driver` |
  | JDBC URL | `jdbc:h2:file:./memory_persist/koclukApp` |
  | User Name | `root` |
  | Password | `root` |

- H2 konsolunda `JDBC URL` kısmını yukarıdaki gibi doldurarak bağlanabilirsiniz.

- Eğer bağlantı başarısız olursa, `application.properties` dosyanızın aşağıdaki gibi olduğundan emin olun:

  ```properties
  spring.application.name=koclukApp
  spring.datasource.driverClassName=org.h2.Driver
  spring.datasource.url=jdbc:h2:file:./memory_persist/koclukApp
  spring.datasource.username=root
  spring.datasource.password=root
  spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
  spring.h2.console.enabled=true
  spring.jpa.properties.javax.persistence.validation.mode = none
  spring.jpa.hibernate.ddl-auto= update
  spring.h2.console.settings.web-allow-others=true
  server.port=8080
  ```

## Projeyi Çalıştırmak

```bash
mvn spring-boot:run
```

Uygulama ayağa kalktıktan sonra hem API'lere hem de H2 Database'e erişebilirsiniz.
