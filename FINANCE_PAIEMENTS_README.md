# 💳 Module Finance & Paiements - CampusShare

## Strategy Pattern #1 (Member 3)

Ce module implémente le système de paiement de l'application CampusShare en utilisant le **Strategy Pattern** pour permettre différents modes de paiement interchangeables.

---

## 📋 Tickets Implémentés

### ✅ T3-01: Interface IPaiementStrategy et PaiementGratuit
- **Interface**: `IPaiementStrategy` définit le contrat pour toutes les stratégies de paiement
- **Implémentation**: `PaiementGratuit` pour les dons et échanges gratuits
- **Critère d'acceptation**: ✓ PaiementGratuit retourne toujours `true` et affiche un log

### ✅ T3-02: Stratégies PaiementPoints et PaiementCarteSimule
- **PaiementPoints**: Paiement par points campus (monnaie virtuelle)
  - ✓ Échec si l'étudiant a 0 points
  - ✓ Succès si l'étudiant a 100 points ou plus (selon montant)
  - ✓ Débite l'émetteur et crédite le receveur

- **PaiementCarteSimule**: Simulation de paiement par carte bancaire
  - ✓ Simulation d'API de paiement externe
  - ✓ Taux de réussite de 95%
  - ✓ Attribution de points bonus au vendeur (1 point / 10€)

### ✅ T3-03: Modèle Transaction et TransactionService
- **Transaction**: Modèle représentant une transaction financière
  - Associe une stratégie de paiement
  - Gère les statuts (EN_ATTENTE, VALIDE, REFUSE, ANNULE)
  - Génère une référence unique

- **TransactionService**: Orchestration du flux de paiement complet
  - ✓ Prend Annonce + Acheteur + Stratégie
  - ✓ Exécute le paiement
  - ✓ Crée un enregistrement Transaction
  - ✓ Met à jour le solde de l'étudiant
  - ✓ Marque l'annonce comme non disponible
  - ✓ Historique des transactions

---

## 🏗️ Architecture

```
com.campusshare/
├── model/
│   ├── Etudiant.java                    # Hérite de Utilisateur, gère les points
│   ├── Transaction.java                 # Modèle de transaction
│   └── StatutTransaction.java           # Enum des statuts
├── paiement/                            # Strategy Pattern
│   ├── IPaiementStrategy.java           # Interface Strategy
│   ├── PaiementGratuit.java            # Stratégie pour dons
│   ├── PaiementPoints.java             # Stratégie par points
│   └── PaiementCarteSimule.java        # Stratégie carte bancaire
└── service/
    └── TransactionService.java          # Orchestration des transactions
```

---

## 🎯 Design Pattern: Strategy

Le **Strategy Pattern** permet de :
- Définir une famille d'algorithmes de paiement
- Encapsuler chaque algorithme dans une classe séparée
- Rendre les algorithmes interchangeables à l'exécution

### Diagramme de classe simplifié

```
                    ┌─────────────────────────┐
                    │  IPaiementStrategy      │
                    │  <<interface>>          │
                    ├─────────────────────────┤
                    │ + payer()               │
                    │ + validerPaiement()     │
                    └──────────▲──────────────┘
                               │
                ┌──────────────┼──────────────┐
                │              │              │
      ┌─────────┴────────┐    │    ┌─────────┴──────────┐
      │ PaiementGratuit  │    │    │ PaiementPoints     │
      ├──────────────────┤    │    ├────────────────────┤
      │ + payer()        │    │    │ + payer()          │
      │ + valider()      │    │    │ + valider()        │
      └──────────────────┘    │    │ - verifierSolde()  │
                              │    └────────────────────┘
                    ┌─────────┴──────────────┐
                    │ PaiementCarteSimule    │
                    ├────────────────────────┤
                    │ + payer()              │
                    │ + valider()            │
                    │ - simulerPaiementCB()  │
                    └────────────────────────┘
```

---

## 💻 Utilisation

### Exemple 1: Paiement Gratuit (Don)

```java
// Création des étudiants
Etudiant alice = new Etudiant("E001", "Martin", "Alice", ...);
Etudiant bob = new Etudiant("E002", "Dupont", "Bob", ...);

// Création d'une annonce de don
DonAnnonce don = new DonAnnonce(...);

// Paiement gratuit
IPaiementStrategy strategie = new PaiementGratuit();
TransactionService service = new TransactionService();
Transaction tx = service.effectuerTransaction(don, bob, strategie);
// → Résultat: true (toujours)
```

### Exemple 2: Paiement par Points

```java
// Création d'une annonce
BienAnnonce velo = new BienAnnonce(...);
velo.setPrixBase(50.0); // 50 points

// Paiement par points
IPaiementStrategy strategie = new PaiementPoints();
Transaction tx = service.effectuerTransaction(velo, acheteur, strategie);
// → Résultat: true si acheteur.getSoldePoints() >= 50
//            false sinon
```

### Exemple 3: Paiement par Carte Bancaire

```java
// Création d'une annonce de service
ServiceAnnonce cours = new ServiceAnnonce(...);
cours.setPrixBase(25.0); // 25 EUR

// Paiement par carte
IPaiementStrategy strategie = new PaiementCarteSimule();
Transaction tx = service.effectuerTransaction(cours, acheteur, strategie);
// → Simulation bancaire avec 95% de réussite
// → Points bonus crédités au vendeur (2 points pour 25€)
```

---

## 🧪 Tests

Exécuter les tests complets :

```bash
# Compilation
javac -d out -sourcepath src/main/java src/main/java/com/campusshare/TestPaiements.java

# Exécution
java -cp out com.campusshare.TestPaiements
```

### Résultats attendus

```
✓ T3-01: IPaiementStrategy & PaiementGratuit
  → PaiementGratuit retourne toujours true

✓ T3-02: PaiementPoints
  → Échec si étudiant a 0 points
  → Succès si étudiant a >= 100 points

✓ T3-02: PaiementCarteSimule
  → Simulation bancaire fonctionnelle

✓ T3-03: Transaction & TransactionService
  → Flux complet validé
  → Historique enregistré
```

---

## 📊 Classes Créées

### Modèle
- **Etudiant** (extends Utilisateur)
  - `soldePoints: int` - Solde en points campus
  - `debiterPoints(int): boolean` - Débite les points
  - `crediterPoints(int): void` - Crédite les points

- **Transaction**
  - `id, dateTransaction, montant, statut, reference`
  - `executerTransac(): boolean` - Exécute la transaction
  - `annuler(): boolean` - Annule si EN_ATTENTE

- **StatutTransaction** (enum)
  - `EN_ATTENTE, VALIDE, REFUSE, ANNULE`

### Stratégies de Paiement
- **IPaiementStrategy** (interface)
  - `payer(montant, emetteur, receveur): boolean`
  - `validerPaiement(transaction): boolean`

- **PaiementGratuit**
  - Pour les dons et échanges gratuits
  - Retourne toujours `true`

- **PaiementPoints**
  - Monnaie virtuelle campus
  - Vérifie le solde avant transfert
  - Transfert atomique (débit + crédit)

- **PaiementCarteSimule**
  - Simulation d'API bancaire
  - Taux de réussite: 95%
  - Points bonus: 1 pt / 10€

### Service
- **TransactionService**
  - `effectuerTransaction(annonce, acheteur, strategie): Transaction`
  - `getHistoriqueTransactions(): List<Transaction>`
  - `getTransactionsEtudiant(etudiant): List<Transaction>`
  - `afficherHistorique(): void`

---

## ✨ Fonctionnalités Avancées

### 1. Génération de Références Uniques
Chaque transaction génère une référence unique :
```
Format: TXN-[DATE]-[UUID]
Exemple: TXN-20251204162525-BD3B719F
```

### 2. Logs Détaillés
Tous les paiements affichent des logs formatés :
```
═══════════════════════════════════════════════
   EXECUTION DE LA TRANSACTION TXN-...
═══════════════════════════════════════════════
Emetteur: Alice Martin
Receveur: Bob Dupont
Montant: 50.0
Stratégie: PaiementPoints
───────────────────────────────────────────────
[PAIEMENT POINTS] Tentative de paiement
→ Vérification: OK
✓ Transaction VALIDEE
═══════════════════════════════════════════════
```

### 3. Historique des Transactions
Le `TransactionService` maintient un historique complet :
- Liste chronologique de toutes les transactions
- Filtrage par étudiant
- Statistiques (montant total, nombre de transactions)

### 4. Points Bonus (Carte Bancaire)
Pour encourager les vendeurs, 1 point bonus est attribué pour chaque tranche de 10€ :
- 25€ → 2 points bonus
- 100€ → 10 points bonus

---

## 🔄 Flux de Transaction Complet

```
1. Création de la transaction
   └─> TransactionService.effectuerTransaction()

2. Vérification
   └─> Acheteur ≠ Vendeur
   └─> Annonce disponible

3. Exécution du paiement
   └─> Transaction.executerTransac()
   └─> Stratégie.payer(montant, emetteur, receveur)

4. Mise à jour
   └─> Soldes étudiants modifiés
   └─> Annonce marquée indisponible
   └─> Transaction enregistrée dans l'historique

5. Retour
   └─> Transaction (ou null si échec)
```

---

## 📝 Notes d'Implémentation

### Sécurité
- Vérification du solde avant débit
- Transferts atomiques (rollback si échec partiel)
- Validation de l'émetteur ≠ receveur

### Extensibilité
Le pattern Strategy permet d'ajouter facilement de nouvelles méthodes :
- `PaiementPayPal`
- `PaiementStripe`
- `PaiementCrypto`
- etc.

Il suffit d'implémenter `IPaiementStrategy` !

### Évolutions Futures
- Intégration API bancaire réelle
- Système de remboursement
- Paiements en plusieurs fois
- Facturation automatique
- Notifications de paiement

---

## 👥 Crédits

**Member 3**: Finance & Payments (Strategy Pattern #1)
- Raouf HAOUCHINE (conception et implémentation)
- Date: 4 Décembre 2025
- Version: 1.0

---

## 📚 Références

- **Design Pattern**: Strategy Pattern (GoF)
- **Projet**: CampusShare - Application de partage pour étudiants
- **Diagramme de classe**: Voir `diagramme_classes_campusshare_final.puml`
