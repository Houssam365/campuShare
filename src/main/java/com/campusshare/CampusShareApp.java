package com.campusshare;

import com.campusshare.adapter.GoogleCalendarAdapter;
import com.campusshare.factory.AnnonceFactory;
import com.campusshare.model.*;
import com.campusshare.model.annonce.*;
import com.campusshare.observer.*;
import com.campusshare.service.*;
import com.campusshare.strategy.*;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * ═══════════════════════════════════════════════════════════════════════════════
 *                            CAMPUS SHARE
 *          Application de partage de biens et services entre étudiants
 * ═══════════════════════════════════════════════════════════════════════════════
 * 
 * Cette application démontre l'utilisation de 4 Design Patterns:
 * 
 * 1. SIMPLE FACTORY (AnnonceFactory)
 *    → Création centralisée des différents types d'annonces
 *    
 * 2. OBSERVER (Observable/Observer)
 *    → Système de notifications pour les changements d'annonces
 *    
 * 3. STRATEGY (StrategyTarification)
 *    → Calcul flexible du prix selon différentes stratégies
 *    
 * 4. ADAPTER (GoogleCalendarAdapter)
 *    → Intégration avec des systèmes de calendrier externes
 * 
 * @author Equipe CampusShare - INFO 732
 * @version 1.0
 */
public class CampusShareApp {
    
    // Services
    private AnnonceService annonceService;
    private ReservationService reservationService;
    private EvaluationService evaluationService;
    
    // Données de démonstration
    private List<Categorie> categories;
    private List<Utilisateur> utilisateurs;
    
    /**
     * Constructeur - initialise les services.
     */
    public CampusShareApp() {
        this.annonceService = new AnnonceService();
        this.evaluationService = new EvaluationService();
        
        // Création du calendrier avec l'adaptateur
        GoogleCalendarAdapter calendrierAdapter = new GoogleCalendarAdapter(
                "API_KEY_DEMO", "campusshare_calendar");
        this.reservationService = new ReservationService(calendrierAdapter);
        
        // Initialisation des données
        initialiserCategories();
        initialiserUtilisateurs();
    }
    
    /**
     * Point d'entrée de l'application.
     */
    public static void main(String[] args) {
        System.out.println("\n" +
                "╔═══════════════════════════════════════════════════════════════════════╗\n" +
                "║                                                                       ║\n" +
                "║     ██████╗ █████╗ ███╗   ███╗██████╗ ██╗   ██╗███████╗               ║\n" +
                "║    ██╔════╝██╔══██╗████╗ ████║██╔══██╗██║   ██║██╔════╝               ║\n" +
                "║    ██║     ███████║██╔████╔██║██████╔╝██║   ██║███████╗               ║\n" +
                "║    ██║     ██╔══██║██║╚██╔╝██║██╔═══╝ ██║   ██║╚════██║               ║\n" +
                "║    ╚██████╗██║  ██║██║ ╚═╝ ██║██║     ╚██████╔╝███████║               ║\n" +
                "║     ╚═════╝╚═╝  ╚═╝╚═╝     ╚═╝╚═╝      ╚═════╝ ╚══════╝               ║\n" +
                "║                                                                       ║\n" +
                "║              ███████╗██╗  ██╗ █████╗ ██████╗ ███████╗                 ║\n" +
                "║              ██╔════╝██║  ██║██╔══██╗██╔══██╗██╔════╝                 ║\n" +
                "║              ███████╗███████║███████║██████╔╝█████╗                   ║\n" +
                "║              ╚════██║██╔══██║██╔══██║██╔══██╗██╔══╝                   ║\n" +
                "║              ███████║██║  ██║██║  ██║██║  ██║███████╗                 ║\n" +
                "║              ╚══════╝╚═╝  ╚═╝╚═╝  ╚═╝╚═╝  ╚═╝╚══════╝                 ║\n" +
                "║                                                                       ║\n" +
                "║         Application de partage entre étudiants - INFO 732            ║\n" +
                "╚═══════════════════════════════════════════════════════════════════════╝\n");
        
        CampusShareApp app = new CampusShareApp();
        app.executerDemonstration();
    }
    
    /**
     * Exécute une démonstration complète des fonctionnalités.
     */
    public void executerDemonstration() {
        System.out.println("\n📋 DÉMONSTRATION DES DESIGN PATTERNS\n");
        System.out.println("═".repeat(70));
        
        // 1. Démonstration Simple Factory
        demonstrationFactory();
        
        // 2. Démonstration Observer
        demonstrationObserver();
        
        // 3. Démonstration Strategy
        demonstrationStrategy();
        
        // 4. Démonstration Adapter
        demonstrationAdapter();
        
        // 5. Scénario complet
        demonstrationScenarioComplet();
        
        // Résumé final
        afficherResume();
    }
    
    /**
     * Démonstration du Pattern SIMPLE FACTORY.
     */
    private void demonstrationFactory() {
        System.out.println("\n" +
                "┌─────────────────────────────────────────────────────────────────────┐\n" +
                "│  🏭 PATTERN SIMPLE FACTORY - Création d'annonces                    │\n" +
                "└─────────────────────────────────────────────────────────────────────┘\n");
        
        Utilisateur alice = utilisateurs.get(0);
        Utilisateur bob = utilisateurs.get(1);
        Utilisateur charlie = utilisateurs.get(2);
        
        System.out.println("La Factory permet de créer différents types d'annonces");
        System.out.println("sans que le code client connaisse les classes concrètes.\n");
        
        // Création via la Factory
        System.out.println("→ Création d'une annonce de BIEN (vélo):");
        BienAnnonce annonceBien = annonceService.publierBien(
                "Vélo VTT Rockrider",
                "VTT Rockrider 520, idéal pour se déplacer sur le campus. " +
                "Freins à disque, 21 vitesses.",
                alice,
                categories.get(0), // Transport
                "Bon état",
                5.0 // 5€/jour
        );
        annonceBien.setMarque("Rockrider");
        annonceBien.setMontantCaution(50.0);
        annonceBien.setLocalisation("Résidence A, Bâtiment 3");
        
        System.out.println("\n→ Création d'une annonce de SERVICE (cours de maths):");
        ServiceAnnonce annonceService = this.annonceService.publierService(
                "Cours particuliers de Mathématiques",
                "Étudiant en L3 Maths, je propose des cours de soutien en algèbre " +
                "et analyse. Tous niveaux jusqu'à L2.",
                bob,
                categories.get(3), // Études
                "Tutorat",
                15.0, // 15€/heure
                60    // 60 minutes
        );
        annonceService.setNiveauExpertise("Expert");
        annonceService.ajouterJourDisponible(DayOfWeek.MONDAY);
        annonceService.ajouterJourDisponible(DayOfWeek.WEDNESDAY);
        annonceService.ajouterJourDisponible(DayOfWeek.FRIDAY);
        annonceService.setHoraireDisponible("14h-19h");
        annonceService.ajouterCompetence("Algèbre linéaire");
        annonceService.ajouterCompetence("Analyse réelle");
        
        System.out.println("\n→ Création d'une annonce de DON (livres):");
        DonAnnonce annonceDon = this.annonceService.publierDon(
                "Livres d'informatique L1/L2",
                "Je donne mes anciens livres: Introduction à Java, " +
                "Algorithmique, Bases de données.",
                charlie,
                categories.get(3), // Études
                "Très bon état",
                "Fin d'études"
        );
        annonceDon.setQuantiteDisponible(3);
        annonceDon.setConditionsRecuperation("À récupérer à la BU, contactez-moi pour RDV");
        
        System.out.println("\n📊 Résultat de la Factory:");
        System.out.println("   " + this.annonceService.getNombreAnnonces() + " annonces créées");
        System.out.println("   Types: BIEN, SERVICE, DON - tous créés via AnnonceFactory\n");
    }
    
    /**
     * Démonstration du Pattern OBSERVER.
     */
    private void demonstrationObserver() {
        System.out.println("\n" +
                "┌─────────────────────────────────────────────────────────────────────┐\n" +
                "│  👁️ PATTERN OBSERVER - Système de notifications                     │\n" +
                "└─────────────────────────────────────────────────────────────────────┘\n");
        
        System.out.println("Le pattern Observer permet de notifier automatiquement");
        System.out.println("les utilisateurs intéressés lors de changements.\n");
        
        Utilisateur diana = utilisateurs.get(3);
        Annonce annonce = annonceService.getAnnonces().get(0); // Le vélo
        
        // Création d'observateurs
        System.out.println("→ Diana s'abonne aux notifications pour le vélo:\n");
        
        EmailObserver emailObs = new EmailObserver(diana);
        PushObserver pushObs = new PushObserver(diana);
        SMSObserver smsObs = new SMSObserver(diana, "06 12 34 56 78");
        
        // Attachement des observateurs
        annonce.attach(emailObs);
        annonce.attach(pushObs);
        annonce.attach(smsObs);
        
        System.out.println("→ Le propriétaire modifie le prix de l'annonce:\n");
        annonce.setPrixBase(4.0); // Modification qui déclenche les notifications
        
        System.out.println("→ L'annonce passe en statut 'Réservée':\n");
        annonce.changerStatut(StatutAnnonce.RESERVEE);
        
        // Détachement
        System.out.println("→ Diana se désabonne des notifications SMS:\n");
        annonce.detach(smsObs);
        
        System.out.println("→ Nouvelle notification (sans SMS):\n");
        annonce.changerStatut(StatutAnnonce.ACTIVE);
        
        System.out.println("📊 Le pattern Observer permet:");
        System.out.println("   - Découplage entre l'annonce et les modes de notification");
        System.out.println("   - Ajout facile de nouveaux canaux (Email, Push, SMS...)");
        System.out.println("   - Abonnement/désabonnement dynamique\n");
    }
    
    /**
     * Démonstration du Pattern STRATEGY.
     */
    private void demonstrationStrategy() {
        System.out.println("\n" +
                "┌─────────────────────────────────────────────────────────────────────┐\n" +
                "│  🎯 PATTERN STRATEGY - Calcul flexible des prix                     │\n" +
                "└─────────────────────────────────────────────────────────────────────┘\n");
        
        System.out.println("Le pattern Strategy permet de changer l'algorithme de");
        System.out.println("tarification sans modifier le code de la réservation.\n");
        
        Utilisateur alice = utilisateurs.get(0);
        Utilisateur bob = utilisateurs.get(1);
        Annonce velo = annonceService.getAnnonces().get(0);
        velo.changerStatut(StatutAnnonce.ACTIVE); // S'assurer que c'est disponible
        
        LocalDateTime debut = LocalDateTime.now().plusDays(1);
        LocalDateTime finCourt = debut.plusHours(3);    // 3 heures
        LocalDateTime finLong = debut.plusDays(10);     // 10 jours
        
        System.out.println("📌 Annonce: " + velo.getTitre());
        System.out.println("   Prix de base: " + velo.getPrixBase() + "€");
        System.out.println();
        
        System.out.println("→ Comparaison des stratégies pour une location de 3 heures:\n");
        
        // Stratégie horaire
        StrategyTarification stratHoraire = new TarifHoraire();
        System.out.println("   " + stratHoraire.getNom() + ":");
        System.out.println("   " + stratHoraire.getDescription());
        System.out.println("   Prix: " + String.format("%.2f€", 
                stratHoraire.calculerPrix(velo.getPrixBase(), 
                        java.time.Duration.between(debut, finCourt))));
        
        // Stratégie journalière
        StrategyTarification stratJour = new TarifJournalier();
        System.out.println("\n   " + stratJour.getNom() + ":");
        System.out.println("   " + stratJour.getDescription());
        System.out.println("   Prix: " + String.format("%.2f€", 
                stratJour.calculerPrix(velo.getPrixBase(), 
                        java.time.Duration.between(debut, finCourt))));
        
        // Stratégie forfaitaire
        StrategyTarification stratForfait = new TarifForfaitaire();
        System.out.println("\n   " + stratForfait.getNom() + ":");
        System.out.println("   " + stratForfait.getDescription());
        System.out.println("   Prix: " + String.format("%.2f€", 
                stratForfait.calculerPrix(velo.getPrixBase(), 
                        java.time.Duration.between(debut, finCourt))));
        
        // Stratégie gratuite
        StrategyTarification stratGratuit = new TarifGratuit();
        System.out.println("\n   " + stratGratuit.getNom() + ":");
        System.out.println("   " + stratGratuit.getDescription());
        System.out.println("   Prix: " + String.format("%.2f€", 
                stratGratuit.calculerPrix(velo.getPrixBase(), 
                        java.time.Duration.between(debut, finCourt))));
        
        System.out.println("\n→ Comparaison pour une location longue durée (10 jours):\n");
        
        System.out.println("   Tarif Journalier (avec réduction -20% >= 7 jours):");
        System.out.println("   Prix: " + String.format("%.2f€", 
                stratJour.calculerPrix(velo.getPrixBase(), 
                        java.time.Duration.between(debut, finLong))));
        System.out.println("   (au lieu de " + String.format("%.2f€", velo.getPrixBase() * 10) + " sans réduction)");
        
        System.out.println("\n📊 Le pattern Strategy permet:");
        System.out.println("   - Changer la tarification à la volée");
        System.out.println("   - Ajouter de nouvelles stratégies facilement");
        System.out.println("   - Code propre et maintenable\n");
    }
    
    /**
     * Démonstration du Pattern ADAPTER.
     */
    private void demonstrationAdapter() {
        System.out.println("\n" +
                "┌─────────────────────────────────────────────────────────────────────┐\n" +
                "│  🔌 PATTERN ADAPTER - Intégration calendrier externe                │\n" +
                "└─────────────────────────────────────────────────────────────────────┘\n");
        
        System.out.println("Le pattern Adapter permet d'intégrer des systèmes externes");
        System.out.println("(comme Google Calendar) sans modifier notre code.\n");
        
        Utilisateur bob = utilisateurs.get(1);
        Utilisateur diana = utilisateurs.get(3);
        Annonce courseMaths = annonceService.getAnnonces().get(1);
        
        LocalDateTime debut = LocalDateTime.now().plusDays(2).withHour(14);
        LocalDateTime fin = debut.plusHours(2);
        
        System.out.println("→ Création d'une réservation avec tarif horaire:\n");
        
        Reservation reservation = reservationService.reserverAvecTarifHoraire(
                courseMaths, diana, debut, fin);
        
        System.out.println("\n→ Confirmation de la réservation (sync avec Google Calendar):\n");
        reservationService.confirmerReservation(reservation);
        
        System.out.println("\n📊 Le pattern Adapter permet:");
        System.out.println("   - Notre code utilise l'interface 'Calendrier'");
        System.out.println("   - L'adaptateur traduit vers l'API Google");
        System.out.println("   - Facile de changer pour Outlook, Apple Calendar...\n");
    }
    
    /**
     * Démonstration d'un scénario complet.
     */
    private void demonstrationScenarioComplet() {
        System.out.println("\n" +
                "┌─────────────────────────────────────────────────────────────────────┐\n" +
                "│  🎬 SCÉNARIO COMPLET - Tous les patterns en action                  │\n" +
                "└─────────────────────────────────────────────────────────────────────┘\n");
        
        Utilisateur alice = utilisateurs.get(0);
        Utilisateur emma = utilisateurs.get(4);
        
        // Emma s'inscrit aux notifications
        System.out.println("1️⃣  Emma s'abonne aux notifications de la catégorie 'Transport'\n");
        EmailObserver emmaEmail = new EmailObserver(emma);
        annonceService.ajouterObservateurGlobal(emmaEmail);
        
        // Alice publie une nouvelle annonce
        System.out.println("2️⃣  Alice publie une nouvelle annonce de trottinette:\n");
        BienAnnonce trottinette = annonceService.publierBien(
                "Trottinette électrique Xiaomi",
                "Trottinette électrique, autonomie 25km, vitesse max 25km/h. " +
                "Parfaite pour aller en cours !",
                alice,
                categories.get(0), // Transport
                "Très bon état",
                8.0 // 8€/jour
        );
        trottinette.setLocalisation("Parking Résidence A");
        
        // Emma fait une réservation
        System.out.println("\n3️⃣  Emma réserve la trottinette pour 3 jours:\n");
        LocalDateTime debutRes = LocalDateTime.now().plusDays(1);
        LocalDateTime finRes = debutRes.plusDays(3);
        
        Reservation resaTrottinette = reservationService.reserverAvecTarifJournalier(
                trottinette, emma, debutRes, finRes);
        resaTrottinette.setMessageAccompagnement("Bonjour, j'ai besoin de la trottinette " +
                "pour aller au stage la semaine prochaine. Merci !");
        
        // Alice confirme
        System.out.println("\n4️⃣  Alice confirme la réservation:\n");
        reservationService.confirmerReservation(resaTrottinette);
        
        // La réservation se termine
        System.out.println("\n5️⃣  La réservation se termine:\n");
        reservationService.demarrerReservation(resaTrottinette);
        reservationService.terminerReservation(resaTrottinette);
        
        // Évaluations mutuelles
        System.out.println("\n6️⃣  Évaluations mutuelles:\n");
        evaluationService.evaluerProprietaire(resaTrottinette, 5, 
                "Alice est très arrangeante, la trottinette était impeccable !");
        evaluationService.evaluerDemandeur(resaTrottinette, 5, 
                "Emma a rendu la trottinette en parfait état, je recommande !");
        
        System.out.println("\n✅ Scénario terminé avec succès !");
        System.out.println("   Tous les patterns ont été utilisés:\n");
        System.out.println("   • Factory → création de l'annonce trottinette");
        System.out.println("   • Observer → notification à Emma");
        System.out.println("   • Strategy → calcul du prix (tarif journalier)");
        System.out.println("   • Adapter → synchronisation calendrier");
    }
    
    /**
     * Affiche le résumé final.
     */
    private void afficherResume() {
        System.out.println("\n" +
                "╔═══════════════════════════════════════════════════════════════════════╗\n" +
                "║                         📊 RÉSUMÉ FINAL                               ║\n" +
                "╚═══════════════════════════════════════════════════════════════════════╝\n");
        
        System.out.println("📌 Statistiques de l'application:\n");
        System.out.println("   Utilisateurs: " + utilisateurs.size());
        System.out.println("   Catégories: " + categories.size());
        System.out.println("   Annonces publiées: " + annonceService.getNombreAnnonces());
        System.out.println("   Annonces actives: " + annonceService.getNombreAnnoncesActives());
        System.out.println("   Réservations: " + reservationService.getNombreReservations());
        System.out.println("   Évaluations: " + evaluationService.getNombreEvaluations());
        
        System.out.println("\n📌 Design Patterns implémentés:\n");
        System.out.println("   ✓ Simple Factory (AnnonceFactory)");
        System.out.println("   ✓ Observer (système de notifications)");
        System.out.println("   ✓ Strategy (tarification flexible)");
        System.out.println("   ✓ Adapter (intégration calendrier)");
        
        System.out.println("\n📌 Fonctionnalités principales:\n");
        System.out.println("   ✓ Publication d'annonces (Biens, Services, Dons)");
        System.out.println("   ✓ Recherche et filtrage");
        System.out.println("   ✓ Système de réservation");
        System.out.println("   ✓ Calcul de prix flexible");
        System.out.println("   ✓ Notifications multi-canaux");
        System.out.println("   ✓ Évaluations et réputation");
        System.out.println("   ✓ Intégration calendrier externe");
        
        System.out.println("\n" +
                "═══════════════════════════════════════════════════════════════════════════\n" +
                "                    FIN DE LA DÉMONSTRATION                                \n" +
                "═══════════════════════════════════════════════════════════════════════════\n");
    }
    
    // ==================== Initialisation des données ====================
    
    /**
     * Initialise les catégories prédéfinies.
     */
    private void initialiserCategories() {
        categories = Arrays.asList(
                new Categorie("CAT001", "Transport", "Vélos, trottinettes, covoiturage", "🚲"),
                new Categorie("CAT002", "High-Tech", "Ordinateurs, téléphones, accessoires", "💻"),
                new Categorie("CAT003", "Logement", "Meubles, électroménager, déco", "🏠"),
                new Categorie("CAT004", "Études", "Livres, cours, tutorat", "📚"),
                new Categorie("CAT005", "Loisirs", "Sport, musique, jeux", "🎮"),
                new Categorie("CAT006", "Entraide", "Aide diverse, services", "🤝")
        );
    }
    
    /**
     * Initialise les utilisateurs de démonstration.
     */
    private void initialiserUtilisateurs() {
        utilisateurs = Arrays.asList(
            // id, nom, prenom, email, pass, numEtu, campus
            new Etudiant("U001", "Martin", "Alice", "alice.martin@etu.univ.fr", "pass123", "2025001", "Campus Nord"),
            new Etudiant("U002", "Dupont", "Bob", "bob.dupont@etu.univ.fr", "pass123", "2025002", "Campus Nord"),
            new Etudiant("U003", "Durand", "Charlie", "charlie.durand@etu.univ.fr", "pass123", "2025003", "Campus Sud"),
            new Etudiant("U004", "Leroy", "Diana", "diana.leroy@etu.univ.fr", "pass123", "2025004", "Campus Sud"),
            new Etudiant("U005", "Bernard", "Emma", "emma.bernard@etu.univ.fr", "pass123", "2025005", "Campus Est")
        );
    }
    
    // ==================== Getters pour les tests ====================
    
    public AnnonceService getAnnonceService() {
        return annonceService;
    }
    
    public ReservationService getReservationService() {
        return reservationService;
    }
    
    public EvaluationService getEvaluationService() {
        return evaluationService;
    }
    
    public List<Categorie> getCategories() {
        return categories;
    }
    
    public List<Utilisateur> getUtilisateurs() {
        return utilisateurs;
    }
}
