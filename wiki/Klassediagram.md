# Klassediagram

Diagrammet viser alle domene-entiteter utledet fra visjonsdokumentet.
Klasser markert med `✅` er implementert. Resten er planlagt.

## Fullstendig domenemodell

```mermaid
classDiagram
    direction TB

    %% ─────────────── ENUMS ───────────────

    class Role {
        <<enumeration>>
        TEACHER
        STUDENT
        +getAuthority() String
    }

    class BadgeType {
        <<enumeration>>
        STOPPESTED_COMPLETION
        WEEKLY_MYSTERY
        FINAL_BOSS
        SPECIAL
    }

    class TaskType {
        <<enumeration>>
        NEWS_COMPARISON
        IMAGE_DETECTION
        EMAIL_PHISHING
        PASSWORD
        MARKETPLACE
        SOCIAL_MEDIA
        FINAL_BOSS
    }

    class SubmissionStatus {
        <<enumeration>>
        PENDING
        APPROVED
        REJECTED
    }

    class NotificationType {
        <<enumeration>>
        AREA_COMPLETED
        GAME_COMPLETED
        STUDENT_STUCK
        INAPPROPRIATE_TEXT
        MYSTERY_SUBMITTED
    }

    %% ─────────────── USER HIERARCHY ───────────────

    class User {
        <<abstract>>
        -Long id
        -String firstName
        -String lastName
        -String password
        +getRole()* Role
        +equals(Object) boolean
        +hashCode() int
    }

    class Student {
        -int level
        -int totalScore
        -String feideUsername
        -String displayName
        +getRole() Role
    }

    class Teacher {
        -String schoolEmail
        +getRole() Role
    }

    User <|-- Student : extends
    User <|-- Teacher : extends

    %% ─────────────── CLASSROOM ───────────────

    class Classroom {
        -Long id
        -String title
        -String description
        -String joinCode
        -LocalDateTime createdAt
    }

    Teacher "1" --> "*" Classroom : eier
    Student "*" --> "0..1" Classroom : tilhører
    Classroom "*" --> "*" Teacher : ekstraLærere

    %% ─────────────── AVATAR ───────────────

    class Avatar {
        -Long id
        -String gender
        -String eyeColor
        -String skinColor
        -String hairColor
        -String hairStyle
        -String clothingType
        -String clothingColor
        -String hatColor
        -String accessory
    }

    Student "1" --> "1" Avatar : har

    %% ─────────────── GAME CONTENT ───────────────

    class StoppingPlace {
        -Long id
        -String name
        -String description
        -int orderIndex
        -String imageUrl
    }

    class Task {
        -Long id
        -int orderIndex
        -int difficulty
        -String title
        -String guidanceText
        -String content
        -TaskType type
    }

    StoppingPlace "1" --> "*" Task : inneholder (3 stk)
    Task --> TaskType

    %% ─────────────── PROGRESS ───────────────

    class TaskProgress {
        -Long id
        -boolean completed
        -int score
        -int attempts
        -LocalDateTime completedAt
    }

    Student "1" --> "*" TaskProgress : sporer
    Task "1" --> "*" TaskProgress : sporingsdata
    TaskProgress ..> Student : student_id FK
    TaskProgress ..> Task : task_id FK

    %% ─────────────── BADGES ───────────────

    class Badge {
        -Long id
        -String name
        -String description
        -String imageUrl
        -BadgeType type
    }

    class StudentBadge {
        -Long id
        -LocalDateTime earnedAt
    }

    Badge --> BadgeType
    Badge "*" --> "0..1" StoppingPlace : knyttet til
    Student "1" --> "*" StudentBadge : opptjent
    Badge "1" --> "*" StudentBadge : tildelt
    StudentBadge ..> Student : student_id FK
    StudentBadge ..> Badge : badge_id FK

    %% ─────────────── NOTEBOOK ───────────────

    class NotebookEntry {
        -Long id
        -String tips
        -String reflection
        -LocalDateTime createdAt
        -LocalDateTime updatedAt
    }

    Student "1" --> "*" NotebookEntry : skriver
    StoppingPlace "1" --> "*" NotebookEntry : kategori
    NotebookEntry ..> Student : student_id FK
    NotebookEntry ..> StoppingPlace : stopping_place_id FK

    %% ─────────────── WEEKLY MYSTERY ───────────────

    class MysterySubmission {
        -Long id
        -String message
        -String screenshotUrl
        -SubmissionStatus status
        -LocalDateTime submittedAt
        -LocalDateTime reviewedAt
    }

    Student "1" --> "*" MysterySubmission : sender inn
    Classroom "1" --> "*" MysterySubmission : mottar
    MysterySubmission --> SubmissionStatus
    MysterySubmission ..> Student : student_id FK
    MysterySubmission ..> Classroom : classroom_id FK

    %% ─────────────── NOTIFICATIONS ───────────────

    class Notification {
        -Long id
        -String message
        -NotificationType type
        -boolean read
        -LocalDateTime createdAt
    }

    Teacher "1" --> "*" Notification : mottar
    Classroom "1" --> "*" Notification : kilde
    Notification --> NotificationType
    Notification ..> Teacher : teacher_id FK
    Notification ..> Classroom : classroom_id FK
```

---

## Implementeringsstatus

| Klasse | Status | Pakke |
|--------|--------|-------|
| `User` | ✅ Implementert | `features.user.model` |
| `Student` | ✅ Implementert | `features.user.model` |
| `Teacher` | ✅ Implementert | `features.user.model` |
| `Role` | ✅ Implementert | `features.user.model` |
| `Badge` | ✅ Implementert | `features.badge.model` |
| `BadgeType` | ✅ Implementert | `features.badge.model` |
| `StudentBadge` | ✅ Implementert | `features.badge.model` |
| `Classroom` | ❌ Ikke startet | `features.classroom.model` |
| `Avatar` | ❌ Ikke startet | `features.avatar.model` |
| `StoppingPlace` | ❌ Ikke startet | `features.game.model` |
| `Task` | ❌ Ikke startet | `features.game.model` |
| `TaskType` | ❌ Ikke startet | `features.game.model` |
| `TaskProgress` | ❌ Ikke startet | `features.progress.model` |
| `NotebookEntry` | ❌ Ikke startet | `features.notebook.model` |
| `MysterySubmission` | ❌ Ikke startet | `features.mystery.model` |
| `SubmissionStatus` | ❌ Ikke startet | `features.mystery.model` |
| `Notification` | ❌ Ikke startet | `features.notification.model` |
| `NotificationType` | ❌ Ikke startet | `features.notification.model` |

---

## Relasjoner forklart

### Bruker → Klasserom
- En **lærer eier** ett eller flere klasserom (`Teacher 1 → * Classroom`)
- Andre lærere kan **legges til** i et klasserom (`Classroom * → * Teacher` via ekstraLærere)
- En **elev tilhører** maks ett klasserom om gangen (`Student * → 0..1 Classroom`)
- Klasserom har en unik **tilgangskode** som elever skriver inn for å bli med

### Spillinnhold
- Kartet har **7+ stoppesteder** ordnet etter `orderIndex` (Detektivkontor → Datasenteret)
- Hvert stoppested har **3 oppgaver** med stigende vanskelighetsgrad
- `TaskType` styrer hvilken oppgavevisning frontend bruker (velg nyhetsartikkel, vurder bilde, osv.)

### Progresjon
- `TaskProgress` er **koblingstabell mellom Student og Task** — kjernefakta for all progresjon
- Stoppested-fullføring **utledes**: er alle 3 TaskProgress for det stoppestedet `completed = true`?
- Stoppested-opplåsing **utledes**: er forrige stoppested (etter `orderIndex`) fullført?
- `Student.totalScore` caches fra `SUM(TaskProgress.score)` for rask ledertavle-spørring
- `Student.level` oppdateres av servicelaget når stoppesteder fullføres

### Merker (Badges)
- `Badge` er en **definisjon** (finnes én gang uavhengig av antall elever)
- `StudentBadge` er **koblingstabell** med tidsstempel for når merket ble opptjent
- `Badge` har en **valgfri FK til StoppingPlace** — kun satt for `STOPPESTED_COMPLETION`-merker
- Unikt constraint `(student_id, badge_id)` hindrer at en elev får samme merke to ganger

### Notatblokk
- Én `NotebookEntry` per elev per stoppested
- `tips` fylles **automatisk** når eleven fullfører oppgaver (teori/veiledning)
- `reflection` er elevens **egne fritekst-refleksjoner** (visjonsdok s.13–14)
- Lærere kan **lese** elevenes notatblokker (visjonsdok s.20)

### Ukens mysterium
- Elever sender inn eksempler de har funnet (screenshot + melding)
- Læreren **godkjenner eller forkaster** innsendte eksempler
- Godkjente eksempler vises til hele klasserommet
- Godkjenning gir eleven en `WEEKLY_MYSTERY`-badge

### Varslinger
- Lærere mottar varslinger for: fullført stoppested, fullført spill, elev som sitter fast, upassende tekst, nye mysterium-innsendinger
- Filtreres per klasserom
- Kan markeres som lest/fjernes

---

## Stoppesteder fra visjonsdokumentet

| # | Stoppested | Tema | TaskType |
|---|-----------|------|----------|
| 1 | Detektivkontor | Startpunkt / introduksjon | — |
| 2 | Nyhetskvartalet | Falske nyheter, clickbait | `NEWS_COMPARISON` |
| 3 | Fotografen | KI-genererte / manipulerte bilder | `IMAGE_DETECTION` |
| 4 | Postkontoret | Phishing-epost | `EMAIL_PHISHING` |
| 5 | Passordbanken | Sterke passord | `PASSWORD` |
| 6 | Markedsplassen | Falske nettbutikker | `MARKETPLACE` |
| 7 | Den sosiale møteplassen | Deling, gruppepress, kilder | `SOCIAL_MEDIA` |
| 8 | Datasenteret | Final boss — blanding av alt | `FINAL_BOSS` |
