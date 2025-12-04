# 🎓 CampusShare - Application de Partage Étudiant

## 📋 Projet INFO 732

Application de partage de biens et services entre étudiants d'un campus universitaire.

---

## 🎯 Objectif

Concevoir et réaliser une application permettant aux étudiants de :
- **Publier** des annonces (biens à prêter/louer, services, dons)
- **Rechercher** et filtrer les annonces disponibles
- **Réserver** des biens ou services
- **Évaluer** les autres utilisateurs après une transaction

---

## 🏗️ Design Patterns Implémentés

### 1. 🏭 Simple Factory (`AnnonceFactory`)
**Objectif** : Centraliser la création des différents types d'annonces.

```java
// Utilisation
Annonce annonce = AnnonceFactory.creerAnnonce(TypeAnnonce.BIEN, titre, desc, user, cat);
```

**Avantages** :
- Encapsulation de la logique de création
- Le client ne connaît pas les classes concrètes
- Facile d'ajouter de nouveaux types

### 2. 👁️ Observer (`Observable` / `Observer`)
**Objectif** : Notifier automatiquement les utilisateurs lors de changements.

```java
// Attachement
annonce.attach(new EmailObserver(utilisateur));
annonce.attach(new PushObserver(utilisateur));

// Notification automatique
annonce.setPrixBase(newPrice); // Déclenche notifyObservers()
```

**Avantages** :
- Découplage entre sujet et observateurs
- Ajout facile de nouveaux canaux (Email, Push, SMS)
- Abonnement/désabonnement dynamique

### 3. 🎯 Strategy (`StrategyTarification`)
**Objectif** : Permettre différents algorithmes de calcul de prix.

```java
// Différentes stratégies
Reservation r1 = service.reserverAvecTarifHoraire(annonce, user, debut, fin);
Reservation r2 = service.reserverAvecTarifJournalier(annonce, user, debut, fin);
Reservation r3 = service.reserverGratuit(annonce, user, debut, fin);
```

**Stratégies disponibles** :
- `TarifGratuit` : Prêt solidaire gratuit
- `TarifHoraire` : Facturation à l'heure
- `TarifJournalier` : Facturation à la journée (avec réduction longue durée)
- `TarifForfaitaire` : Prix fixe

### 4. 🔌 Adapter (`GoogleCalendarAdapter`)
**Objectif** : Intégrer des systèmes de calendrier externes.

```java
// Notre code utilise l'interface Calendrier
Calendrier cal = new GoogleCalendarAdapter(apiKey, calendarId);
cal.ajouterEvenement(reservation);

// L'adaptateur traduit vers l'API Google
```

**Avantages** :
- Découplage du code métier et de l'API externe
- Facile de changer de fournisseur (Google → Outlook)
- Testabilité améliorée

---

## 📁 Structure du Projet

```
CampusShare/
├── src/main/java/com/campusshare/
│   ├── CampusShareApp.java          # Point d'entrée + démo
│   ├── model/
│   │   ├── Utilisateur.java
│   │   ├── Categorie.java
│   │   ├── Evaluation.java
│   │   ├── Reservation.java
│   │   ├── StatutAnnonce.java
│   │   ├── StatutReservation.java
│   │   └── annonce/
│   │       ├── Annonce.java         # Classe abstraite
│   │       ├── BienAnnonce.java     # Bien à prêter/louer
│   │       ├── ServiceAnnonce.java  # Service à proposer
│   │       ├── DonAnnonce.java      # Don gratuit
│   │       └── TypeAnnonce.java     # Enum
│   ├── factory/
│   │   └── AnnonceFactory.java      # Pattern Simple Factory
│   ├── observer/
│   │   ├── Observable.java          # Interface Subject
│   │   ├── Observer.java            # Interface Observer
│   │   ├── EmailObserver.java
│   │   ├── PushObserver.java
│   │   └── SMSObserver.java
│   ├── strategy/
│   │   ├── StrategyTarification.java # Interface Strategy
│   │   ├── TarifGratuit.java
│   │   ├── TarifHoraire.java
│   │   ├── TarifJournalier.java
│   │   └── TarifForfaitaire.java
│   ├── adapter/
│   │   ├── Calendrier.java          # Interface cible
│   │   ├── GoogleCalendarAPI.java   # Classe adaptée
│   │   └── GoogleCalendarAdapter.java # Adaptateur
│   └── service/
│       ├── AnnonceService.java
│       ├── ReservationService.java
│       └── EvaluationService.java
└── docs/
    ├── diagrammes/
    │   ├── classes.puml
    │   ├── sequence_reservation.puml
    │   └── cas_utilisation.puml
    └── README.md
```

---

## 🚀 Compilation et Exécution

### Prérequis
- Java JDK 11 ou supérieur

### Compilation
```bash
# Linux/Mac
./compile.sh

# Windows
compile.bat

# Ou manuellement
mkdir -p target/classes
javac -d target/classes $(find src -name "*.java")
```

### Exécution
```bash
# Linux/Mac
./run.sh

# Windows
run.bat

# Ou manuellement
java -cp target/classes com.campusshare.CampusShareApp
```

---

## 📊 Diagrammes UML

Les diagrammes sont disponibles dans le dossier `docs/diagrammes/` au format PlantUML.

### Diagramme de Classes (simplifié)
```
┌─────────────────┐     ┌─────────────────┐
│   Utilisateur   │     │   Categorie     │
├─────────────────┤     ├─────────────────┤
│ - id            │     │ - id            │
│ - nom           │     │ - nom           │
│ - email         │     │ - description   │
│ - reputation    │     └─────────────────┘
└────────┬────────┘              │
         │ 1                     │ 1
         │                       │
         ▼ *                     ▼ *
┌─────────────────────────────────────────┐
│           <<abstract>>                  │
│             Annonce                     │
│           <<Observable>>                │
├─────────────────────────────────────────┤
│ - titre, description, prixBase          │
│ - statut, localisation                  │
├─────────────────────────────────────────┤
│ + attach(Observer)                      │
│ + detach(Observer)                      │
│ + notifyObservers(message)              │
└────────────────┬────────────────────────┘
                 │
     ┌───────────┼───────────┐
     ▼           ▼           ▼
┌─────────┐ ┌─────────┐ ┌─────────┐
│  Bien   │ │ Service │ │   Don   │
│ Annonce │ │ Annonce │ │ Annonce │
└─────────┘ └─────────┘ └─────────┘

┌─────────────────┐         ┌──────────────────────┐
│   Reservation   │────────▶│ <<interface>>        │
├─────────────────┤         │ StrategyTarification │
│ - dateDebut     │         ├──────────────────────┤
│ - dateFin       │         │ + calculerPrix()     │
│ - prixTotal     │         └──────────┬───────────┘
└─────────────────┘                    │
                           ┌───────────┼───────────┐
                           ▼           ▼           ▼
                    ┌──────────┐ ┌──────────┐ ┌──────────┐
                    │ Gratuit  │ │ Horaire  │ │Journalier│
                    └──────────┘ └──────────┘ └──────────┘
```

---

## 👥 Équipe

Projet réalisé dans le cadre du cours INFO 732.

---

## 📝 Licence

Projet académique - Usage éducatif uniquement.
