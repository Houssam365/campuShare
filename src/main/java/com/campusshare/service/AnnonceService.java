package com.campusshare.service;

import com.campusshare.factory.AnnonceFactory;
import com.campusshare.model.Categorie;
import com.campusshare.model.StatutAnnonce;
import com.campusshare.model.Utilisateur;
import com.campusshare.model.annonce.*;
import com.campusshare.observer.Observer;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service de gestion des annonces.
 * Centralise toutes les opérations sur les annonces.
 * 
 * Utilise:
 * - Pattern Simple Factory pour la création d'annonces
 * - Pattern Observer pour les notifications
 * 
 * @author Equipe CampusShare
 * @version 1.0
 */
public class AnnonceService {
    
    private List<Annonce> annonces;
    private List<Observer> observersGlobaux; // Observateurs pour toutes les nouvelles annonces
    
    /**
     * Constructeur du service.
     */
    public AnnonceService() {
        this.annonces = new ArrayList<>();
        this.observersGlobaux = new ArrayList<>();
    }
    
    // ==================== Création d'annonces (utilise Factory) ====================
    
    /**
     * Crée et publie une nouvelle annonce en utilisant la Factory.
     * 
     * @param type Type d'annonce
     * @param titre Titre
     * @param description Description
     * @param proprietaire Propriétaire
     * @param categorie Catégorie
     * @return L'annonce créée
     */
    public Annonce publierAnnonce(TypeAnnonce type, String titre, String description,
                                   Utilisateur proprietaire, Categorie categorie) {
        // Utilisation du Pattern Simple Factory
        Annonce annonce = AnnonceFactory.creerAnnonce(type, titre, description, 
                proprietaire, categorie);
        
        // Ajout des observateurs globaux
        for (Observer observer : observersGlobaux) {
            annonce.attach(observer);
        }
        
        annonces.add(annonce);
        proprietaire.getAnnoncesPubliees().add(annonce);
        
        // Notification des observateurs globaux
        annonce.notifyObservers("Nouvelle annonce publiée: " + titre);
        
        return annonce;
    }
    
    /**
     * Crée une annonce de bien avec paramètres détaillés.
     */
    public BienAnnonce publierBien(String titre, String description,
                                    Utilisateur proprietaire, Categorie categorie,
                                    String etat, double prixBase) {
        BienAnnonce annonce = AnnonceFactory.creerAnnonceBien(titre, description, 
                proprietaire, categorie, etat, prixBase);
        
        for (Observer observer : observersGlobaux) {
            annonce.attach(observer);
        }
        
        annonces.add(annonce);
        proprietaire.getAnnoncesPubliees().add(annonce);
        annonce.notifyObservers("Nouveau bien à louer: " + titre);
        
        return annonce;
    }
    
    /**
     * Crée une annonce de service avec paramètres détaillés.
     */
    public ServiceAnnonce publierService(String titre, String description,
                                          Utilisateur proprietaire, Categorie categorie,
                                          String typeService, double prixBase, int dureeMinutes) {
        ServiceAnnonce annonce = AnnonceFactory.creerAnnonceService(titre, description, 
                proprietaire, categorie, typeService, prixBase, dureeMinutes);
        
        for (Observer observer : observersGlobaux) {
            annonce.attach(observer);
        }
        
        annonces.add(annonce);
        proprietaire.getAnnoncesPubliees().add(annonce);
        annonce.notifyObservers("Nouveau service disponible: " + titre);
        
        return annonce;
    }
    
    /**
     * Crée une annonce de don avec paramètres détaillés.
     */
    public DonAnnonce publierDon(String titre, String description,
                                  Utilisateur proprietaire, Categorie categorie,
                                  String etatObjet, String raisonDon) {
        DonAnnonce annonce = AnnonceFactory.creerAnnonceDon(titre, description, 
                proprietaire, categorie, etatObjet, raisonDon);
        
        for (Observer observer : observersGlobaux) {
            annonce.attach(observer);
        }
        
        annonces.add(annonce);
        proprietaire.getAnnoncesPubliees().add(annonce);
        annonce.notifyObservers("Nouveau don disponible: " + titre);
        
        return annonce;
    }
    
    // ==================== Recherche et filtrage ====================
    
    /**
     * Recherche des annonces par mot-clé dans le titre ou la description.
     */
    public List<Annonce> rechercherParMotCle(String motCle) {
        String motCleLower = motCle.toLowerCase();
        return annonces.stream()
                .filter(a -> a.getStatut() == StatutAnnonce.ACTIVE)
                .filter(a -> a.getTitre().toLowerCase().contains(motCleLower) ||
                            a.getDescription().toLowerCase().contains(motCleLower))
                .collect(Collectors.toList());
    }
    
    /**
     * Filtre les annonces par catégorie.
     */
    public List<Annonce> filtrerParCategorie(Categorie categorie) {
        return annonces.stream()
                .filter(a -> a.getStatut() == StatutAnnonce.ACTIVE)
                .filter(a -> a.getCategorie().equals(categorie))
                .collect(Collectors.toList());
    }
    
    /**
     * Filtre les annonces par type.
     */
    public List<Annonce> filtrerParType(TypeAnnonce type) {
        return annonces.stream()
                .filter(a -> a.getStatut() == StatutAnnonce.ACTIVE)
                .filter(a -> a.getType() == type)
                .collect(Collectors.toList());
    }
    
    /**
     * Filtre les annonces par prix maximum.
     */
    public List<Annonce> filtrerParPrixMax(double prixMax) {
        return annonces.stream()
                .filter(a -> a.getStatut() == StatutAnnonce.ACTIVE)
                .filter(a -> a.getPrixBase() <= prixMax)
                .collect(Collectors.toList());
    }
    
    /**
     * Retourne les annonces d'un utilisateur.
     */
    public List<Annonce> getAnnoncesUtilisateur(Utilisateur utilisateur) {
        return annonces.stream()
                .filter(a -> a.getProprietaire().equals(utilisateur))
                .collect(Collectors.toList());
    }
    
    /**
     * Retourne toutes les annonces actives.
     */
    public List<Annonce> getAnnoncesActives() {
        return annonces.stream()
                .filter(a -> a.getStatut() == StatutAnnonce.ACTIVE)
                .collect(Collectors.toList());
    }
    
    // ==================== Gestion des annonces ====================
    
    /**
     * Supprime une annonce (change son statut).
     */
    public void supprimerAnnonce(Annonce annonce) {
        annonce.changerStatut(StatutAnnonce.SUPPRIMEE);
    }
    
    /**
     * Marque une annonce comme réservée.
     */
    public void marquerReservee(Annonce annonce) {
        annonce.changerStatut(StatutAnnonce.RESERVEE);
    }
    
    /**
     * Rend une annonce à nouveau disponible.
     */
    public void rendreDisponible(Annonce annonce) {
        annonce.changerStatut(StatutAnnonce.ACTIVE);
    }
    
    /**
     * Trouve une annonce par son ID.
     */
    public Annonce trouverParId(String id) {
        return annonces.stream()
                .filter(a -> a.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
    
    // ==================== Gestion des observateurs globaux ====================
    
    /**
     * Ajoute un observateur global (notifié pour toutes les annonces).
     */
    public void ajouterObservateurGlobal(Observer observer) {
        observersGlobaux.add(observer);
        // Attache aussi aux annonces existantes
        for (Annonce annonce : annonces) {
            annonce.attach(observer);
        }
    }
    
    /**
     * Retire un observateur global.
     */
    public void retirerObservateurGlobal(Observer observer) {
        observersGlobaux.remove(observer);
        for (Annonce annonce : annonces) {
            annonce.detach(observer);
        }
    }
    
    // ==================== Getters ====================
    
    public List<Annonce> getAnnonces() {
        return annonces;
    }
    
    public int getNombreAnnonces() {
        return annonces.size();
    }
    
    public int getNombreAnnoncesActives() {
        return (int) annonces.stream()
                .filter(a -> a.getStatut() == StatutAnnonce.ACTIVE)
                .count();
    }
}
