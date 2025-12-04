package com.campusshare.paiement;

import com.campusshare.model.Etudiant;
import com.campusshare.model.Transaction;

/**
 * Interface définissant le contrat pour les stratégies de paiement.
 * Implémente le pattern Strategy pour permettre différents modes de paiement.
 *
 * Pattern: Strategy Pattern #1 (Finance & Paiements)
 *
 * @author Equipe CampusShare - Member 3
 * @version 1.0
 */
public interface IPaiementStrategy {

    /**
     * Effectue un paiement entre deux étudiants.
     *
     * @param montant Le montant à payer
     * @param emetteur L'étudiant qui paie
     * @param receveur L'étudiant qui reçoit le paiement
     * @return true si le paiement a réussi, false sinon
     */
    boolean payer(double montant, Etudiant emetteur, Etudiant receveur);

    /**
     * Valide un paiement pour une transaction donnée.
     *
     * @param transaction La transaction à valider
     * @return true si la validation a réussi, false sinon
     */
    boolean validerPaiement(Transaction transaction);
}
