package com.campusshare.paiement;

import com.campusshare.model.Etudiant;
import com.campusshare.model.Transaction;

/**
 * Stratégie de paiement gratuit pour les dons et échanges sans contrepartie financière.
 * Cette stratégie accepte toujours le paiement et ne débite aucun compte.
 *
 * Pattern: Strategy Pattern #1 (Finance & Paiements)
 *
 * @author Equipe CampusShare - Member 3
 * @version 1.0
 */
public class PaiementGratuit implements IPaiementStrategy {

    /**
     * Effectue un "paiement" gratuit (toujours réussi).
     * Utilisé pour les dons et les échanges gratuits.
     *
     * @param montant Le montant (ignoré, devrait être 0)
     * @param emetteur L'étudiant qui "paie" (donneur)
     * @param receveur L'étudiant qui reçoit
     * @return true (toujours)
     */
    @Override
    public boolean payer(double montant, Etudiant emetteur, Etudiant receveur) {
        System.out.println("[PAIEMENT GRATUIT] Transaction gratuite acceptée");
        System.out.println("→ De: " + emetteur.getNomComplet());
        System.out.println("→ Vers: " + receveur.getNomComplet());
        System.out.println("→ Type: Don/Échange gratuit");
        return true;
    }

    /**
     * Valide un paiement gratuit (toujours valide).
     *
     * @param transaction La transaction à valider
     * @return true (toujours)
     */
    @Override
    public boolean validerPaiement(Transaction transaction) {
        System.out.println("[VALIDATION] Paiement gratuit validé pour transaction: " + transaction.getReference());
        return true;
    }
}
