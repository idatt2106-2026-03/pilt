# Nettdetektivene — Backend (PILT)

**Team 03** | IDATT2106 — Systemutvikling med smidig prosjekt | NTNU, 2026

> Nettdetektivene er et læringsspill for nettvett for barn og unge 3.–7. trinn. Elever lærer å gjenkjenne og håndtere digital kriminalitet, desinformasjon og kritiske hendelser gjennom engasjerende oppgaver. Lærere oppretter klasserom og følger elevenes progresjon.

---

## Teammedlemmer

| Navn | Rolle |
|------|-------|
| Oleander Tengesdal | Utvikler |
| Alexander Owren Elton | Utvikler |
| *Legg til resten av teamet* | |

---

## Hurtiglenker

| Ressurs | Lenke |
|---------|-------|
| GitHub-repo | [idatt2106-2026-03/pilt](https://github.com/idatt2106-2026-03/pilt) |
| Issue Board | [Issues](https://github.com/idatt2106-2026-03/pilt/issues) |
| CI/CD | [Actions](https://github.com/idatt2106-2026-03/pilt/actions) |
| API-dokumentasjon (Swagger) | `http://localhost:8080/swagger-ui/index.html` (kjørende app) |

---

## Dokumentasjonsstruktur

Strukturen følger kravene i visjonsdokumentet (seksjon 8.1):

### 1. [Krav](./Krav)
- [User Stories — Oversikt](./User-Stories-Oversikt)
- [Domenemodell](./Domenemodell)

### 2. [Brukertester og modeller](./Brukertester-og-modeller)

### 3. System
- [Arkitektur](./Arkitektur)
- [Prosjektstruktur](./Prosjektstruktur)
- [Klassediagram](./Klassediagram)
- [Servertjenester (REST API)](./Servertjenester)
- [Databasemodell](./Databasemodell)
- [Kildekode](./Kildekode)
- [Installasjon](./Installasjon)
- [CI og testing](./CI-og-testing)
- [Sikkerhet](./Sikkerhet)

---

## Teknisk stack

| Lag | Teknologi |
|-----|-----------|
| Språk | Java 21 |
| Rammeverk | Spring Boot 3.5.13 |
| Sikkerhet | Spring Security |
| Database | MySQL (prod), H2 (test) |
| ORM | Spring Data JPA / Hibernate |
| Migreringer | Flyway |
| API-dokumentasjon | SpringDoc OpenAPI (Swagger UI) |
| Byggesystem | Maven |
| CI | GitHub Actions |

---

## Prosjektstruktur (oversikt)

```
src/main/java/ntnu/idi/idatt2106/pilt/
├── PiltApplication.java                 # Applikasjonens inngangspunkt
│
├── core/                                # Kjerneinfrastruktur (delt på tvers av features)
│   ├── dto/
│   │   └── ApiResponse.java             # Generisk API-responswrapper
│   └── exception/
│       ├── ApiException.java            # Basis-exception
│       ├── BadRequestException.java     # 400
│       ├── ForbiddenException.java      # 403
│       ├── ResourceNotFoundException.java # 404
│       ├── UnauthorizedException.java   # 401
│       └── GlobalExceptionHandler.java  # Sentral feilhåndtering
│
└── features/                            # Funksjonsmoduler
    ├── user/
    │   └── model/
    │       ├── User.java                # Abstrakt baseklasse (JOINED-arv)
    │       ├── Student.java             # Elev — level, totalScore, displayName
    │       ├── Teacher.java             # Lærer — schoolEmail
    │       └── Role.java                # Enum: TEACHER, STUDENT
    │
    └── badge/
        └── model/
            ├── Badge.java               # Merke-definisjon (navn, beskrivelse, ikon)
            ├── BadgeType.java           # Enum: STOPPESTED_COMPLETION, WEEKLY_MYSTERY, ...
            └── StudentBadge.java        # Koblingstabell: elev ↔ merke (med tidsstempel)
```

---

## Databasemodell (nåværende)

```
┌──────────────────┐
│     users         │       ┌──────────────────┐
│──────────────────│       │    students       │
│ id (PK)          │◄──────│ id (PK, FK)      │
│ first_name       │       │ level            │
│ last_name        │       │ total_score      │
│ password         │       │ feide_username   │
│ dtype            │       │ display_name     │
└──────────────────┘       └──────────────────┘
        ▲
        │                  ┌──────────────────┐
        └──────────────────│    teachers       │
                           │ id (PK, FK)      │
                           │ school_email     │
                           └──────────────────┘

┌──────────────────┐       ┌──────────────────────┐
│     badges        │       │   student_badges      │
│──────────────────│       │──────────────────────│
│ id (PK)          │◄──────│ id (PK)              │
│ name (UNIQUE)    │       │ badge_id (FK)        │
│ description      │       │ student_id (FK) ────►│ students
│ image_url        │       │ earned_at            │
│ type             │       │ UNIQUE(student,badge)│
└──────────────────┘       └──────────────────────┘
```

---

## Installasjon (hurtigstart)

### Forutsetninger
- Java 21 (JDK)
- Maven 3.9+ (eller bruk medfølgende `./mvnw`)
- MySQL 8+ (eller H2 for lokal utvikling)

### Kjøre applikasjonen
```bash
# Klon repoet
git clone https://github.com/idatt2106-2026-03/pilt.git
cd pilt

# Bygg og kjør
./mvnw spring-boot:run

# Eller bygg JAR og kjør separat
./mvnw clean package
java -jar target/pilt-0.0.1-SNAPSHOT.jar
```

### Kjøre tester
```bash
./mvnw verify
```

### Swagger UI
Etter oppstart, åpne API-dokumentasjonen:
```
http://localhost:8080/swagger-ui/index.html
```

---

## CI/CD

GitHub Actions kjører automatisk ved push/PR mot `main` og `dev`:

- **Checkout** av kode
- **Java 21 Temurin** med Maven-cache
- **`./mvnw -B verify`** — bygger prosjektet og kjører alle tester

Se [Actions-fanen](https://github.com/idatt2106-2026-03/pilt/actions) for status.

---

## Versjon og milepæler

| Versjon | Frist | Innhold |
|---------|-------|---------|
| **V.1 (MVP)** | 20.04.2026 | Autentisering, roller, klasserom, predefinerte stoppesteder, basic progresjon, korktavle, avatar, medaljer, notatblokk |
| **V.2 (Ferdig)** | 27.04.2026 | Egne oppgaver fra lærer, ukens mysterium, notatblokk med erfaring, nivåstyrt algoritme, avansert avatar |
