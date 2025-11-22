package com.campusshare.test;

import com.campusshare.factory.AnnonceFactory;
import com.campusshare.model.Categorie;
import com.campusshare.model.Etudiant;
import com.campusshare.model.StatutAnnonce;
import com.campusshare.model.Utilisateur;
import com.campusshare.model.annonce.Annonce;
import com.campusshare.model.annonce.BienAnnonce;
import com.campusshare.model.annonce.DonAnnonce;
import com.campusshare.model.annonce.ServiceAnnonce;
import com.campusshare.model.annonce.TypeAnnonce;
import com.campusshare.observer.Observer;

/**
 * Classe de test pour vérifier l'implémentation des tickets T2-01, T2-02 et T2-03.
 * 
 * T2-01: Annonce Hierarchy (Bien with price, Don with price 0)
 * T2-02: Factory Pattern (creerAnnonce returns correct type, Title validation)
 * T2-03: Observer Integration (attach, notify)
 */
public class TestAnnonceFeatures {

    // Mock Observer pour T2-03
    static class MockObserver implements Observer {
        private boolean notified = false;
        private String lastMessage = "";

        @Override
        public void update(Annonce annonce, String message) {
            this.notified = true;
            this.lastMessage = message;
            System.out.println("✅ [Observer] Notification reçue pour l'annonce: " + annonce.getTitre() + " - Message: " + message);
        }

        public boolean isNotified() {
            return notified;
        }

        public String getLastMessage() {
            return lastMessage;
        }
    }

    public static void main(String[] args) {
        System.out.println("=================================================");
        System.out.println("  Démarrage des tests pour les tickets T2-01 à T2-03");
        System.out.println("=================================================");

        // Préparation des données de base
        Etudiant auteur = new Etudiant("E001", "Dupont", "Alice", "alice.dupont@campus.fr", "pass123", "12345", "Campus Ouest");
        Categorie categorieLivre = new Categorie("C01", "Livres", "Livres et manuels", "📚");
        Categorie categorieAide = new Categorie("C02", "Aide", "Aide aux devoirs, tutorat", "🧠");

        // -----------------------------------------------------------------
        // T2-02: Test de la Factory et de la validation du titre
        // -----------------------------------------------------------------
        System.out.println("\n--- T2-02: Test de la Factory et de la validation ---");

        // Test 2.1: Création d'une BienAnnonce
        Annonce bienAnnonce = AnnonceFactory.creerAnnonce(TypeAnnonce.BIEN, "Vélo de ville", "Vélo en bon état", auteur, categorieLivre);
        assert bienAnnonce instanceof BienAnnonce : "❌ T2-02.1: La Factory n'a pas retourné BienAnnonce pour le type BIEN.";
        System.out.println("✅ T2-02.1: Factory retourne BienAnnonce.");

        // Test 2.2: Création d'une ServiceAnnonce
        Annonce serviceAnnonce = AnnonceFactory.creerAnnonce(TypeAnnonce.SERVICE, "Cours de Java", "Tutorat pour débutants", auteur, categorieAide);
        assert serviceAnnonce instanceof ServiceAnnonce : "❌ T2-02.2: La Factory n'a pas retourné ServiceAnnonce pour le type SERVICE.";
        System.out.println("✅ T2-02.2: Factory retourne ServiceAnnonce.");

        // Test 2.3: Création d'une DonAnnonce (Acceptance T2-02)
        Annonce donAnnonce = AnnonceFactory.creerAnnonce(TypeAnnonce.DON, "Cahiers neufs", "Lot de cahiers neufs", auteur, categorieLivre);
        assert donAnnonce instanceof DonAnnonce : "❌ T2-02.3: La Factory n'a pas retourné DonAnnonce pour le type DON.";
        System.out.println("✅ T2-02.3: Factory retourne DonAnnonce (Acceptance T2-02 OK).");

        // Test 2.4: Validation du titre (T2-02)
        try {
            AnnonceFactory.creerAnnonce(TypeAnnonce.BIEN, "", "Description", auteur, categorieLivre);
            System.out.println("❌ T2-02.4: La validation du titre n'a pas fonctionné (pas d'exception levée).");
        } catch (IllegalArgumentException e) {
            if (e.getMessage().contains("Le titre de l'annonce ne peut pas être vide.")) {
                System.out.println("✅ T2-02.4: Validation du titre OK. Exception levée: " + e.getMessage());
            } else {
                System.out.println("❌ T2-02.4: Validation du titre a levé une mauvaise exception: " + e.getMessage());
            }
        }

        // -----------------------------------------------------------------
        // T2-01: Test de l'Annonce Hierarchy (Prix)
        // -----------------------------------------------------------------
        System.out.println("\n--- T2-01: Test de l'Annonce Hierarchy (Prix) ---");

        // Test 1.1: BienAnnonce avec prix (Acceptance T2-01)
        BienAnnonce bienAnnoncePrix = AnnonceFactory.creerAnnonceBien("Livre de Maths", "Manuel d'analyse", auteur, categorieLivre, "Neuf", 15.50);
        assert bienAnnoncePrix.getPrixBase() == 15.50 : "❌ T2-01.1: BienAnnonce n'a pas le prix correct. Attendu: 15.50, Obtenu: " + bienAnnoncePrix.getPrixBase();
        System.out.println("✅ T2-01.1: BienAnnonce peut être instanciée avec un prix (Acceptance T2-01 OK). Prix: " + bienAnnoncePrix.getPrixBase());

        // Test 1.2: DonAnnonce avec prix 0 (Acceptance T2-01)
        DonAnnonce donAnnoncePrix = AnnonceFactory.creerAnnonceDon("Vieux T-Shirt", "À donner", auteur, categorieLivre, "Usé", "Déménagement");
        assert donAnnoncePrix.getPrixBase() == 0.0 : "❌ T2-01.2: DonAnnonce n'a pas le prix 0. Attendu: 0.0, Obtenu: " + donAnnoncePrix.getPrixBase();
        System.out.println("✅ T2-01.2: DonAnnonce est instanciée avec prix 0 (Acceptance T2-01 OK). Prix: " + donAnnoncePrix.getPrixBase());

        // -----------------------------------------------------------------
        // T2-03: Test de l'intégration de l'Observer
        // -----------------------------------------------------------------
        System.out.println("\n--- T2-03: Test de l'intégration de l'Observer ---");

        // Test 3.1: Attacher et notifier (Acceptance T2-03)
        MockObserver mockObserver = new MockObserver();
        bienAnnoncePrix.attach(mockObserver);
        System.out.println("   [Test] Attachement de l'Observer à l'annonce.");

        // Changement de statut qui notifie automatiquement
        bienAnnoncePrix.changerStatut(StatutAnnonce.RESERVEE);
        
        assert mockObserver.isNotified() : "❌ T2-03.1: L'Observer n'a pas été notifié après le changement de statut.";
        assert mockObserver.getLastMessage().contains("passée de ACTIVE à RESERVEE") : "❌ T2-03.1: Le message de notification est incorrect.";
        System.out.println("✅ T2-03.1: L'Observer a été notifié après changerStatut (Acceptance T2-03 OK).");

        // Test 3.2: Notification via setTitre
        mockObserver.notified = false; // Reset
        bienAnnoncePrix.setTitre("Nouveau Titre du Livre");
        assert mockObserver.isNotified() : "❌ T2-03.2: L'Observer n'a pas été notifié après setTitre.";
        assert mockObserver.getLastMessage().contains("Le titre de l'annonce a été modifié") : "❌ T2-03.2: Le message de notification setTitre est incorrect.";
        System.out.println("✅ T2-03.2: L'Observer a été notifié après setTitre.");
        
        // Test 3.3: Détacher l'Observer
        bienAnnoncePrix.detach(mockObserver);
        mockObserver.notified = false; // Reset
        bienAnnoncePrix.setTitre("Titre Final"); // Devrait notifier, mais l'observer est détaché
        assert !mockObserver.isNotified() : "❌ T2-03.3: L'Observer a été notifié après avoir été détaché.";
        System.out.println("✅ T2-03.3: L'Observer a été détaché et n'a pas été notifié.");


        System.out.println("\n=================================================");
        System.out.println("  Tous les tests pour T2-01, T2-02 et T2-03 sont terminés.");
        System.out.println("=================================================");
    }
}
