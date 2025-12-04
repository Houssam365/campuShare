package com.campusshare.paiement;

import com.campusshare.model.Etudiant;
import com.campusshare.model.Transaction;

/**
 * Stratégie de paiement par points campus.
 * Cette stratégie débite les points de l'émetteur et crédite le receveur.
 * Le paiement échoue si l'émetteur n'a pas assez de points.
 *
 * Pattern: Strategy Pattern #1 (Finance & Paiements)
 *
 * Règles:
 * - Solde minimum requis: le montant exact
 * - Échec si l'étudiant a 0 points
 * - Succès si l'étudiant a 100 points ou plus (selon le montant)
 *
 * @author Equipe CampusShare - Member 3
 * @version 1.0
 */
public class PaiementPoints implements IPaiementStrategy {

    /**
     * Effectue un paiement par points entre deux étudiants.
     * Débite l'émetteur et crédite le receveur si le solde est suffisant.
     *
     * @param montant Le montant en points à payer
     * @param emetteur L'étudiant qui paie (sera débité)
     * @param receveur L'étudiant qui reçoit (sera crédité)
     * @return true si le paiement a réussi, false sinon
     */
    @Override
    public boolean payer(double montant, Etudiant emetteur, Etudiant receveur) {
        System.out.println("[PAIEMENT POINTS] Tentative de paiement");
        System.out.println("→ Émetteur: " + emetteur.getNomComplet() + " (Solde: " + emetteur.getSoldePoints() + " pts)");
        System.out.println("→ Receveur: " + receveur.getNomComplet() + " (Solde: " + receveur.getSoldePoints() + " pts)");
        System.out.println("→ Montant: " + (int)montant + " points");

        // Vérification du solde
        if (!verifierSolde(emetteur, (int)montant)) {
            System.out.println("✗ ECHEC: Solde insuffisant");
            return false;
        }

        // Exécution du transfert
        boolean debitReussi = emetteur.debiterPoints((int)montant);

        if (debitReussi) {
            receveur.crediterPoints((int)montant);
            System.out.println("✓ Transfert de " + (int)montant + " points réussi");
            return true;
        }

        System.out.println("✗ ECHEC: Impossible de débiter les points");
        return false;
    }

    /**
     * Vérifie si l'étudiant a suffisamment de points pour effectuer le paiement.
     *
     * @param etudiant L'étudiant dont on vérifie le solde
     * @param montant Le montant requis
     * @return true si le solde est suffisant, false sinon
     */
    private boolean verifierSolde(Etudiant etudiant, int montant) {
        int solde = etudiant.getSoldePoints();

        // Cas spécifiques mentionnés dans les critères d'acceptation
        if (solde == 0) {
            System.out.println("→ Vérification: ECHEC (0 points disponibles)");
            return false;
        }

        if (solde >= montant) {
            System.out.println("→ Vérification: OK (solde: " + solde + " >= requis: " + montant + ")");
            return true;
        }

        System.out.println("→ Vérification: ECHEC (solde: " + solde + " < requis: " + montant + ")");
        return false;
    }

    /**
     * Valide un paiement par points pour une transaction donnée.
     *
     * @param transaction La transaction à valider
     * @return true si la validation a réussi, false sinon
     */
    @Override
    public boolean validerPaiement(Transaction transaction) {
        Etudiant emetteur = transaction.getEmetteur();
        int montant = (int)transaction.getMontant();

        boolean valide = verifierSolde(emetteur, montant);

        if (valide) {
            System.out.println("[VALIDATION] Paiement par points validé pour transaction: " + transaction.getReference());
        } else {
            System.out.println("[VALIDATION] Paiement par points refusé: solde insuffisant");
        }

        return valide;
    }
}
