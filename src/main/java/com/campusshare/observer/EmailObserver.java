package com.campusshare.observer;

import com.campusshare.model.Utilisateur;
import com.campusshare.model.annonce.Annonce;

/**
 * Observateur concret qui envoie des notifications par email.
 * Implémentation du pattern Observer.
 * 
 * @author Equipe CampusShare
 * @version 1.0
 */
public class EmailObserver implements Observer {
    
    private Utilisateur utilisateur;
    
    /**
     * Constructeur de l'observateur email.
     * 
     * @param utilisateur L'utilisateur qui recevra les emails
     */
    public EmailObserver(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }
    
    /**
     * Envoie une notification par email à l'utilisateur.
     * (Simulation - en production, utiliserait une API email)
     * 
     * @param annonce L'annonce concernée
     * @param message Le message de notification
     */
    @Override
    public void update(Annonce annonce, String message) {
        // Simulation d'envoi d'email
        System.out.println("📧 EMAIL envoyé à " + utilisateur.getEmail());
        System.out.println("   Sujet: [CampusShare] Notification - " + annonce.getTitre());
        System.out.println("   Message: " + message);
        System.out.println();
    }
    
    public Utilisateur getUtilisateur() {
        return utilisateur;
    }
    
    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }
}
