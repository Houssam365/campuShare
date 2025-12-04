package com.campusshare.observer;

import com.campusshare.model.Utilisateur;
import com.campusshare.model.annonce.Annonce;

/**
 * Observateur concret qui envoie des notifications push.
 * Implémentation du pattern Observer.
 * 
 * @author Equipe CampusShare
 * @version 1.0
 */
public class PushObserver implements Observer {
    
    private Utilisateur utilisateur;
    private String deviceToken; // Token du device mobile
    
    /**
     * Constructeur de l'observateur push.
     * 
     * @param utilisateur L'utilisateur qui recevra les notifications push
     */
    public PushObserver(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
        this.deviceToken = "DEVICE_" + utilisateur.getId();
    }
    
    /**
     * Envoie une notification push à l'utilisateur.
     * (Simulation - en production, utiliserait Firebase/APNs)
     * 
     * @param annonce L'annonce concernée
     * @param message Le message de notification
     */
    @Override
    public void update(Annonce annonce, String message) {
        // Simulation d'envoi de notification push
        System.out.println("📱 PUSH envoyé à " + utilisateur.getNomComplet());
        System.out.println("   Device: " + deviceToken);
        System.out.println("   Titre: " + annonce.getTitre());
        System.out.println("   Corps: " + message);
        System.out.println();
    }
    
    public Utilisateur getUtilisateur() {
        return utilisateur;
    }
    
    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }
    
    public String getDeviceToken() {
        return deviceToken;
    }
    
    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }
}
