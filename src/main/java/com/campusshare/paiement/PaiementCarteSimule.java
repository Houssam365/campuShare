package com.campusshare.paiement;

import com.campusshare.model.Etudiant;
import com.campusshare.model.Transaction;
import java.util.Random;

/**
 * Stratégie de paiement par carte bancaire simulée.
 * Cette stratégie simule un paiement par carte bancaire sans véritable intégration bancaire.
 * Utilisée pour les achats nécessitant une transaction monétaire réelle.
 *
 * Pattern: Strategy Pattern #1 (Finance & Paiements)
 *
 * Note: Il s'agit d'une simulation à des fins éducatives. Dans un système réel,
 * cette classe intégrerait une API de paiement (Stripe, PayPal, etc.).
 *
 * @author Equipe CampusShare - Member 3
 * @version 1.0
 */
public class PaiementCarteSimule implements IPaiementStrategy {

    private Random random;
    private static final double TAUX_REUSSITE = 0.95; // 95% de réussite simulée

    /**
     * Constructeur de la stratégie de paiement par carte.
     */
    public PaiementCarteSimule() {
        this.random = new Random();
    }

    /**
     * Effectue un paiement par carte bancaire simulé.
     * Simule une connexion à une plateforme de paiement externe.
     *
     * @param montant Le montant en euros à payer
     * @param emetteur L'étudiant qui paie (acheteur)
     * @param receveur L'étudiant qui reçoit (vendeur)
     * @return true si le paiement a réussi, false sinon
     */
    @Override
    public boolean payer(double montant, Etudiant emetteur, Etudiant receveur) {
        System.out.println("[PAIEMENT CARTE BANCAIRE] Simulation de paiement");
        System.out.println("→ Acheteur: " + emetteur.getNomComplet());
        System.out.println("→ Vendeur: " + receveur.getNomComplet());
        System.out.println("→ Montant: " + String.format("%.2f", montant) + " EUR");

        // Simulation de la connexion à une API de paiement
        System.out.println("→ Connexion à la plateforme de paiement...");

        boolean paiementReussi = simulerPaiementCB();

        if (paiementReussi) {
            String numeroAutorisation = genererNumeroAutorisation();
            System.out.println("✓ Paiement autorisé - Code: " + numeroAutorisation);
            System.out.println("✓ Fonds transférés avec succès");

            // Dans un système réel, on créditerait le compte bancaire du vendeur
            // Ici, on peut optionnellement créditer des points bonus
            int pointsBonus = calculerPointsBonus(montant);
            if (pointsBonus > 0) {
                receveur.crediterPoints(pointsBonus);
                System.out.println("→ Bonus: " + pointsBonus + " points crédités au vendeur");
            }

            return true;
        } else {
            System.out.println("✗ ECHEC: Paiement refusé par la banque");
            System.out.println("→ Raison: Simulation d'échec aléatoire (fonds insuffisants, carte expirée, etc.)");
            return false;
        }
    }

    /**
     * Simule l'appel à une API de paiement par carte bancaire.
     * Dans un système réel, cette méthode appellerait l'API de Stripe, PayPal, etc.
     *
     * @return true si le paiement est accepté, false sinon
     */
    private boolean simulerPaiementCB() {
        // Simulation d'un délai de traitement
        try {
            Thread.sleep(100); // 100ms pour simuler la latence réseau
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Simulation d'un taux de réussite de 95%
        return random.nextDouble() < TAUX_REUSSITE;
    }

    /**
     * Génère un numéro d'autorisation de paiement fictif.
     *
     * @return Le numéro d'autorisation
     */
    private String genererNumeroAutorisation() {
        int numero = 100000 + random.nextInt(900000);
        return "AUTH-" + numero;
    }

    /**
     * Calcule les points bonus à attribuer au vendeur en fonction du montant.
     * 1 point pour chaque tranche de 10 euros.
     *
     * @param montant Le montant de la transaction
     * @return Le nombre de points bonus
     */
    private int calculerPointsBonus(double montant) {
        return (int)(montant / 10);
    }

    /**
     * Valide un paiement par carte pour une transaction donnée.
     * Dans un système réel, cette méthode vérifierait le statut auprès de la banque.
     *
     * @param transaction La transaction à valider
     * @return true si la validation a réussi, false sinon
     */
    @Override
    public boolean validerPaiement(Transaction transaction) {
        System.out.println("[VALIDATION] Vérification du paiement par carte...");
        System.out.println("→ Transaction: " + transaction.getReference());
        System.out.println("→ Montant: " + String.format("%.2f", transaction.getMontant()) + " EUR");

        // Simulation de validation (toujours vraie une fois le paiement effectué)
        boolean valide = true;

        if (valide) {
            System.out.println("✓ Paiement validé par la banque");
        } else {
            System.out.println("✗ Paiement non validé");
        }

        return valide;
    }
}
