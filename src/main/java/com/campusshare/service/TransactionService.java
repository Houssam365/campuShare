package com.campusshare.service;

import com.campusshare.model.Etudiant;
import com.campusshare.model.Transaction;
import com.campusshare.model.annonce.Annonce;
import com.campusshare.paiement.IPaiementStrategy;
import java.util.ArrayList;
import java.util.List;

/**
 * Service de gestion des transactions financières.
 * Ce service orchestre la création et l'exécution des transactions
 * en utilisant différentes stratégies de paiement.
 *
 * @author Equipe CampusShare - Member 3
 * @version 1.0
 */
public class TransactionService {

    private List<Transaction> historiqueTransactions;

    /**
     * Constructeur du service de transactions.
     */
    public TransactionService() {
        this.historiqueTransactions = new ArrayList<>();
    }

    /**
     * Crée et exécute une transaction pour une annonce donnée.
     * Cette méthode prend en charge le flux complet de paiement :
     * 1. Création de la transaction
     * 2. Exécution du paiement via la stratégie
     * 3. Enregistrement dans l'historique
     *
     * @param annonce L'annonce concernée par la transaction
     * @param acheteur L'étudiant qui achète/demande
     * @param strategie La stratégie de paiement à utiliser
     * @return La transaction créée, ou null si l'exécution a échoué
     */
    public Transaction effectuerTransaction(Annonce annonce, Etudiant acheteur, IPaiementStrategy strategie) {
        System.out.println("\n╔═══════════════════════════════════════════════╗");
        System.out.println("║   NOUVEAU PROCESSUS DE TRANSACTION            ║");
        System.out.println("╚═══════════════════════════════════════════════╝");
        System.out.println("Annonce: " + annonce.getTitre());
        System.out.println("Vendeur: " + annonce.getAuteur().getNomComplet());
        System.out.println("Acheteur: " + acheteur.getNomComplet());
        System.out.println();

        // Récupération du vendeur depuis l'annonce
        Etudiant vendeur = (Etudiant) annonce.getAuteur();

        // Vérification que l'acheteur n'est pas le vendeur
        if (acheteur.getId().equals(vendeur.getId())) {
            System.out.println("✗ ERREUR: Impossible d'acheter sa propre annonce");
            return null;
        }

        // Calcul du montant basé sur le type d'annonce
        double montant = annonce.getPrixEstime();

        // Création de la transaction
        Transaction transaction = new Transaction(montant, acheteur, vendeur, strategie);

        // Exécution de la transaction
        boolean succes = transaction.executerTransac();

        if (succes) {
            // Ajout à l'historique
            historiqueTransactions.add(transaction);

            // Mise à jour de la disponibilité de l'annonce
            annonce.setEstDisponible(false);

            System.out.println("✓ Transaction enregistrée dans l'historique");
            System.out.println("✓ Annonce marquée comme non disponible");

            return transaction;
        } else {
            System.out.println("✗ La transaction a échoué et n'a pas été enregistrée");
            return null;
        }
    }

    /**
     * Récupère l'historique complet des transactions.
     *
     * @return La liste de toutes les transactions
     */
    public List<Transaction> getHistoriqueTransactions() {
        return new ArrayList<>(historiqueTransactions);
    }

    /**
     * Récupère les transactions d'un étudiant spécifique (émetteur ou receveur).
     *
     * @param etudiant L'étudiant concerné
     * @return La liste des transactions liées à cet étudiant
     */
    public List<Transaction> getTransactionsEtudiant(Etudiant etudiant) {
        List<Transaction> transactions = new ArrayList<>();

        for (Transaction t : historiqueTransactions) {
            if (t.getEmetteur().getId().equals(etudiant.getId()) ||
                t.getReceveur().getId().equals(etudiant.getId())) {
                transactions.add(t);
            }
        }

        return transactions;
    }

    /**
     * Affiche l'historique complet des transactions.
     */
    public void afficherHistorique() {
        System.out.println("\n╔═══════════════════════════════════════════════╗");
        System.out.println("║   HISTORIQUE DES TRANSACTIONS                 ║");
        System.out.println("╚═══════════════════════════════════════════════╝");

        if (historiqueTransactions.isEmpty()) {
            System.out.println("Aucune transaction enregistrée.");
        } else {
            System.out.println("Nombre total de transactions: " + historiqueTransactions.size());
            System.out.println();

            for (int i = 0; i < historiqueTransactions.size(); i++) {
                Transaction t = historiqueTransactions.get(i);
                System.out.println((i + 1) + ". " + t.toString());
            }
        }

        System.out.println();
    }

    /**
     * Calcule le montant total des transactions réussies.
     *
     * @return Le montant total
     */
    public double calculerMontantTotal() {
        return historiqueTransactions.stream()
                .filter(t -> t.getStatut().toString().equals("Validée"))
                .mapToDouble(Transaction::getMontant)
                .sum();
    }

    /**
     * Compte le nombre de transactions réussies.
     *
     * @return Le nombre de transactions validées
     */
    public int compterTransactionsReussies() {
        return (int) historiqueTransactions.stream()
                .filter(t -> t.getStatut().toString().equals("Validée"))
                .count();
    }
}
