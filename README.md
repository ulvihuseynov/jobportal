# JobPortal

JobPortal iş axtaran namizədləri və işəgötürən şirkətləri bir platformada birləşdirən production-oriented iş və vakansiya idarəetmə sistemidir.

Layihə real biznes tələblərinə uyğun olaraq sprint-sprint hazırlanır. Məqsəd yalnız CRUD əməliyyatları təqdim edən sadə tətbiq deyil, təhlükəsizlik, məlumat bütövlüyü, test, audit, monitoring və deployment imkanları olan genişlənə bilən sistem qurmaqdır.

## Layihənin əsas məqsədləri

JobPortal aşağıdakı problemləri həll etməyi hədəfləyir:

* Şirkətlərin vakansiya yaratması və idarə etməsi
* Namizədlərin profil və CV yaratması
* Vakansiyaların axtarılması və filtrlənməsi
* Namizədlərin vakansiyalara müraciət etməsi
* İşəgötürənlərin müraciətləri idarə etməsi
* Müraciət prosesinin mərhələlərlə izlənməsi
* Rol əsaslı giriş və təhlükəsizlik
* Bildiriş və audit mexanizmləri
* Production monitorinqi və sağlamlıq yoxlamaları

## Arxitektura

Layihə **Modular Monolith** arxitekturası ilə hazırlanır.

Sistem vahid Spring Boot tətbiqi kimi deploy olunur, lakin biznes funksionallıqları bir-birindən ayrılmış modullar daxilində təşkil edilir.

Planlaşdırılan əsas modullar:

* Auth və Security
* User
* Candidate
* Employer
* Company
* Vacancy
* Application
* CV və Document
* Notification
* Audit
* Administration

Modulların sərhədləri qorunacaq və biznes məntiqi controller qatında deyil, service və domain qatlarında yerləşdiriləcək.

## Texnologiyalar

* Java 25
* Spring Boot 4.1.0
* Spring Web MVC
* Spring Data JPA
* Spring Security
* Spring Validation
* Spring Boot Actuator
* PostgreSQL
* H2 Database — test mühiti üçün
* Flyway — database migration idarəetməsi üçün
* Hibernate
* Maven
* Lombok
* JUnit
* Git və GitHub

## Tələblər

Layihəni lokal kompüterdə işə salmaq üçün aşağıdakılar tələb olunur:

* Java 25
* PostgreSQL
* Git
* Maven Wrapper layihənin daxilində mövcuddur
* IntelliJ IDEA və ya başqa Java IDE

Versiyaları yoxlamaq üçün:

```bash
java -version
git --version
```

Layihədə Maven Wrapper istifadə edildiyi üçün Maven-i ayrıca quraşdırmaq məcburi deyil.

## Repository-ni klonlamaq

```bash
git clone https://github.com/ulvihuseynov/jobportal.git
cd jobportal
```

## Spring profilləri

Layihədə müxtəlif mühitlər üçün ayrıca Spring profilləri istifadə olunur.

| Profil  | Məqsəd                              | Database     |
| ------- | ----------------------------------- | ------------ |
| `local` | Developer kompüterində lokal işləmə | PostgreSQL   |
| `dev`   | Development mühiti                  | PostgreSQL   |
| `test`  | Avtomatik testlər                   | H2 in-memory |
| `prod`  | Production mühiti                   | PostgreSQL   |

Əsas konfiqurasiya faylı:

```text
src/main/resources/application.yml
```

Profil faylları:

```text
src/main/resources/application-local.yml
src/main/resources/application-dev.yml
src/main/resources/application-prod.yml
src/test/resources/application-test.yml
```

Hazırda default profil `local` profilidir.

Profili açıq şəkildə seçmək üçün:

```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=local
```

Windows Command Prompt üçün:

```cmd
mvnw.cmd spring-boot:run -Dspring-boot.run.profiles=local
```

## Lokal PostgreSQL hazırlığı

PostgreSQL-də lokal database yarat:

```sql
CREATE DATABASE job_portal;
```

Lokal profil aşağıdakı environment dəyişənlərindən istifadə edir:

| Dəyişən       | Təyinat               | Default dəyər  |
| ------------- | --------------------- | -------------- |
| `DB_HOST`     | PostgreSQL host       | `localhost`    |
| `DB_PORT`     | PostgreSQL port       | `5432`         |
| `DB_NAME`     | Database adı          | `job_portal`   |
| `DB_USERNAME` | Database istifadəçisi | Default yoxdur |
| `DB_PASSWORD` | Database şifrəsi      | Default yoxdur |

Git Bash daxilində müvəqqəti environment dəyişənləri:

```bash
export DB_USERNAME=postgres
export DB_PASSWORD=your_local_password
```

Windows Command Prompt:

```cmd
set DB_USERNAME=postgres
set DB_PASSWORD=your_local_password
```

PowerShell:

```powershell
$env:DB_USERNAME="postgres"
$env:DB_PASSWORD="your_local_password"
```

Environment dəyişənlərini IntelliJ IDEA daxilində də təyin etmək mümkündür:

```text
Run
→ Edit Configurations
→ Environment variables
```

## Tətbiqi işə salmaq

Git Bash və ya terminal:

```bash
./mvnw spring-boot:run
```

Windows Command Prompt:

```cmd
mvnw.cmd spring-boot:run
```

Tətbiqin default portu:

```text
http://localhost:8080
```

## Build

Layihəni compile və package etmək üçün:

```bash
./mvnw clean package
```

Testləri işlətmədən package etmək üçün:

```bash
./mvnw clean package -DskipTests
```

Bu əmr yalnız xüsusi hallarda istifadə edilməlidir. Normal build zamanı testlər keçirilməlidir.

## Testlər

Test mühitində H2 in-memory database istifadə olunur.

Testlər lokal PostgreSQL username və password məlumatlarından asılı deyil. Test başladıqda H2 database yaddaşda yaradılır, test bitdikdən sonra isə silinir.

Testləri işə salmaq üçün:

```bash
./mvnw clean test
```

Windows Command Prompt:

```cmd
mvnw.cmd clean test
```

Test profili:

```text
src/test/resources/application-test.yml
```

H2 PostgreSQL compatibility mode ilə işlədilir. Bununla belə H2 real PostgreSQL-in tam əvəzi deyil. PostgreSQL-ə məxsus query, data type və migration-lar gələcəkdə PostgreSQL Testcontainers ilə yoxlanılacaq.

## Database migration

Database strukturu Flyway vasitəsilə idarə olunur.

Migration faylları aşağıdakı qovluqda saxlanılır:

```text
src/main/resources/db/migration
```

Migration adlandırma nümunəsi:

```text
V1__init.sql
V2__create_users_table.sql
V3__create_companies_table.sql
```

Qaydalar:

* Mövcud migration faylı sonradan dəyişdirilməməlidir
* Hər schema dəyişikliyi yeni migration ilə edilməlidir
* Production mühitində Hibernate schema yaratmamalıdır
* Hibernate `ddl-auto: validate` vasitəsilə entity və database uyğunluğunu yoxlamalıdır

## Təhlükəsizlik

Real şifrələr və secret məlumatlar repository-yə commit edilməməlidir.

Aşağıdakılar GitHub-a göndərilməməlidir:

```text
.env
.env.*
.run/
.idea/
target/
```

Environment variable nümunələrində real şifrə istifadə edilməməlidir.

Production mühitində aşağıdakı məlumatlar server environment və ya secret management sistemi vasitəsilə verilməlidir:

```text
SPRING_PROFILES_ACTIVE
DB_HOST
DB_PORT
DB_NAME
DB_USERNAME
DB_PASSWORD
```

Production konfiqurasiyasında database credential-ları üçün təhlükəli default dəyərlər istifadə edilmir. Tələb olunan environment dəyişəni olmadıqda tətbiq başlamamalıdır.

## Git branch strategiyası

Layihədə aşağıdakı branch strategiyası istifadə olunur:

* `main` — stabil və release üçün hazır kod
* `develop` — tamamlanmış sprintlərin inteqrasiya branch-ı
* `feature/*` — ayrıca sprint və funksionallıqlar üçün branch

Nümunə:

```text
feature/sprint-1-project-setup
feature/sprint-2-domain-model
```

İş prosesi:

```text
feature branch
      ↓
Pull Request
      ↓
develop
      ↓
release hazır olduqda
      ↓
main
```

Birbaşa `main` branch-a development kodu push edilməməlidir.

## Commit mesajları

Layihədə aydın və məqsədyönlü commit mesajları istifadə olunur.

Nümunələr:

```text
chore: initialize Spring Boot project
chore: configure application profiles
test: configure H2 database for test profile
feat: add vacancy creation flow
fix: prevent duplicate job applications
docs: update project README
security: remove committed credentials
```

## Sprint roadmap

Layihə mərhələli şəkildə inkişaf etdirilir.

### Sprint 1 — Project foundation

* Spring Boot layihəsinin yaradılması
* Java və Maven konfiqurasiyası
* Spring profilləri
* PostgreSQL bağlantısı
* H2 test konfiqurasiyası
* Flyway hazırlığı
* Environment variable strukturu
* İlkin test və build yoxlaması

### Növbəti mərhələlər

* Domain və modul strukturu
* Candidate və Employer modelləri
* Company və Vacancy idarəetməsi
* Job Application workflow
* Authentication və authorization
* CV və fayl idarəetməsi
* Notification sistemi
* Audit və logging
* Integration testlər
* Docker və Testcontainers
* GitHub Actions CI/CD
* Monitoring və production deployment

## Hazırkı vəziyyət

Layihənin ilkin konfiqurasiya mərhələsi hazırlanır.

Mövcud funksionallıqlar:

* Spring Boot project foundation
* Maven build sistemi
* Local və production profilləri
* Lokal PostgreSQL bağlantısı
* H2 əsaslı test profili
* Flyway inteqrasiyası
* Hibernate schema validation
* Actuator dependency-si
* İlkin application context testi

Biznes modulları növbəti sprintlərdə əlavə ediləcək.

## Müəllif

**Ulvi Huseynov**

GitHub:

```text
https://github.com/ulvihuseynov
```
