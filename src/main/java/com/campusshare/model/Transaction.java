package com.campusshare.model;

import com.campusshare.paiement.IPaiementStrategy;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

/**
 * Classe représentant une transaction financière entre deux étudiants.
 * Une transaction utilise une stratégie de paiement (Strategy Pattern).
 *
 * @author Equipe CampusShare - Member 3
 * @version 1.0
 */
public class Transaction {

    private String id;
    private LocalDateTime dateTransaction;
    private double montant;
    private StatutTransaction statut;
    private String reference;
    private Etudiant emetteur;
    private Etudiant receveur;
    private IPaiementStrategy strategie;

    /**
     * Constructeur de la transaction.
     *
     * @param montant Le montant de la transaction
     * @param emetteur L'étudiant qui émet le paiement
     * @param receveur L'étudiant qui reçoit le paiement
     * @param strategie La stratégie de paiement à utiliser
     */
    public Transaction(double montant, Etudiant emetteur, Etudiant receveur, IPaiementStrategy strategie) {
        this.id = UUID.randomUUID().toString();
        this.dateTransaction = LocalDateTime.now();
        this.montant = montant;
        this.emetteur = emetteur;
        this.receveur = receveur;
        this.strategie = strategie;
        this.statut = StatutTransaction.EN_ATTENTE;
        this.reference = genererReference();
    }

    /**
     * Génère une référence unique pour la transaction.
     *
     * @return La référence générée
     */
    private String genererReference() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        return "TXN-" + dateTransaction.format(formatter) + "-" + id.substring(0, 8).toUpperCase();
    }

    /**
     * Exécute la transaction en utilisant la stratégie de paiement.
     *
     * @return true si la transaction a réussi, false sinon
     */
    public boolean executerTransac() {
        System.out.println("\n═══════════════════════════════════════════════");
        System.out.println("   EXECUTION DE LA TRANSACTION " + reference);
        System.out.println("═══════════════════════════════════════════════");
        System.out.println("Emetteur: " + emetteur.getNomComplet());
        System.out.println("Receveur: " + receveur.getNomComplet());
        System.out.println("Montant: " + montant);
        System.out.println("Stratégie: " + strategie.getClass().getSimpleName());
        System.out.println("───────────────────────────────────────────────");

        boolean succes = strategie.payer(montant, emetteur, receveur);

        if (succes) {
            this.statut = StatutTransaction.VALIDE;
            System.out.println("✓ Transaction VALIDEE");
        } else {
            this.statut = StatutTransaction.REFUSE;
            System.out.println("✗ Transaction REFUSEE");
        }

        System.out.println("═══════════════════════════════════════════════\n");
        return succes;
    }

    /**
     * Annule la transaction si elle est en attente.
     *
     * @return true si l'annulation a réussi, false sinon
     */
    public boolean annuler() {
        if (statut == StatutTransaction.EN_ATTENTE) {
            statut = StatutTransaction.ANNULE;
            System.out.println("[ANNULATION] Transaction " + reference + " annulée");
            return true;
        }
        System.out.println("[ERREUR] Impossible d'annuler une transaction avec statut: " + statut);
        return false;
    }

    /**
     * Valide manuellement la transaction.
     *
     * @return true si la validation a réussi, false sinon
     */
    public boolean valider() {
        if (statut == StatutTransaction.EN_ATTENTE && strategie.validerPaiement(this)) {
            statut = StatutTransaction.VALIDE;
            System.out.println("[VALIDATION] Transaction " + reference + " validée");
            return true;
        }
        return false;
    }

    // Getters et Setters

    public String getId() {
        return id;
    }

    public LocalDateTime getDateTransaction() {
        return dateTransaction;
    }

    public double getMontant() {
        return montant;
    }

    public StatutTransaction getStatut() {
        return statut;
    }

    public void setStatut(StatutTransaction statut) {
        this.statut = statut;
    }

    public String getReference() {
        return reference;
    }

    public Etudiant getEmetteur() {
        return emetteur;
    }

    public Etudiant getReceveur() {
        return receveur;
    }

    public IPaiementStrategy getStrategie() {
        return strategie;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return String.format("Transaction[%s] %s - %.2f - %s -> %s - Statut: %s",
                reference,
                dateTransaction.format(formatter),
                montant,
                emetteur.getNomComplet(),
                receveur.getNomComplet(),
                statut.getLibelle());
    }
}
