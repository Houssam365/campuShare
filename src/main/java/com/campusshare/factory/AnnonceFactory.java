package com.campusshare.factory;

import com.campusshare.model.Categorie;
import com.campusshare.model.Utilisateur;
import com.campusshare.model.annonce.*;

import java.util.UUID;

/**
 * Factory pour la création d'annonces.
 * 
 * Pattern Simple Factory: centralise la logique de création des différents
 * types d'annonces (Bien, Service, Don) dans une seule classe.
 * 
 * Avantages:
 * - Encapsule la logique de création complexe
 * - Le code client n'a pas besoin de connaître les classes concrètes
 * - Facilite l'ajout de nouveaux types d'annonces
 * - Point unique pour la génération des IDs et l'initialisation
 * 
 * @author Equipe CampusShare
 * @version 1.0
 */
public class AnnonceFactory {
    
    /**
     * Crée une annonce du type spécifié.
     * 
     * @param type Le type d'annonce à créer
     * @param titre Titre de l'annonce
     * @param description Description détaillée
     * @param proprietaire Utilisateur propriétaire
     * @param categorie Catégorie de l'annonce
     * @return L'annonce créée
     * @throws IllegalArgumentException si le type est inconnu
     */
    public static Annonce creerAnnonce(TypeAnnonce type, String titre, String description,
                                       Utilisateur proprietaire, Categorie categorie) {
        
        String id = genererIdUnique(type);
        
        switch (type) {
            case BIEN:
                BienAnnonce bienAnnonce = new BienAnnonce(id, titre, description, 
                        proprietaire, categorie);
                System.out.println("📦 Création d'une annonce de BIEN: " + titre);
                return bienAnnonce;
                
            case SERVICE:
                ServiceAnnonce serviceAnnonce = new ServiceAnnonce(id, titre, description, 
                        proprietaire, categorie);
                System.out.println("🛠️ Création d'une annonce de SERVICE: " + titre);
                return serviceAnnonce;
                
            case DON:
                DonAnnonce donAnnonce = new DonAnnonce(id, titre, description, 
                        proprietaire, categorie);
                System.out.println("🎁 Création d'une annonce de DON: " + titre);
                return donAnnonce;
                
            default:
                throw new IllegalArgumentException("Type d'annonce inconnu: " + type);
        }
    }
    
    /**
     * Crée une annonce de type Bien avec des paramètres supplémentaires.
     * 
     * @param titre Titre de l'annonce
     * @param description Description détaillée
     * @param proprietaire Utilisateur propriétaire
     * @param categorie Catégorie de l'annonce
     * @param etat État du bien
     * @param prixBase Prix de base (par jour généralement)
     * @return L'annonce de bien créée
     */
    public static BienAnnonce creerAnnonceBien(String titre, String description,
                                                Utilisateur proprietaire, Categorie categorie,
                                                String etat, double prixBase) {
        BienAnnonce annonce = (BienAnnonce) creerAnnonce(TypeAnnonce.BIEN, titre, 
                description, proprietaire, categorie);
        annonce.setEtat(etat);
        annonce.setPrixBase(prixBase);
        return annonce;
    }
    
    /**
     * Crée une annonce de type Service avec des paramètres supplémentaires.
     * 
     * @param titre Titre de l'annonce
     * @param description Description détaillée
     * @param proprietaire Utilisateur propriétaire
     * @param categorie Catégorie de l'annonce
     * @param typeService Type de service proposé
     * @param prixBase Prix de base (par heure généralement)
     * @param dureeMinutes Durée estimée en minutes
     * @return L'annonce de service créée
     */
    public static ServiceAnnonce creerAnnonceService(String titre, String description,
                                                      Utilisateur proprietaire, Categorie categorie,
                                                      String typeService, double prixBase, 
                                                      int dureeMinutes) {
        ServiceAnnonce annonce = (ServiceAnnonce) creerAnnonce(TypeAnnonce.SERVICE, titre, 
                description, proprietaire, categorie);
        annonce.setTypeService(typeService);
        annonce.setPrixBase(prixBase);
        annonce.setDureeMinutesEstimee(dureeMinutes);
        return annonce;
    }
    
    /**
     * Crée une annonce de type Don avec des paramètres supplémentaires.
     * 
     * @param titre Titre de l'annonce
     * @param description Description détaillée
     * @param proprietaire Utilisateur propriétaire
     * @param categorie Catégorie de l'annonce
     * @param etatObjet État de l'objet donné
     * @param raisonDon Raison du don
     * @return L'annonce de don créée
     */
    public static DonAnnonce creerAnnonceDon(String titre, String description,
                                              Utilisateur proprietaire, Categorie categorie,
                                              String etatObjet, String raisonDon) {
        DonAnnonce annonce = (DonAnnonce) creerAnnonce(TypeAnnonce.DON, titre, 
                description, proprietaire, categorie);
        annonce.setEtatObjet(etatObjet);
        annonce.setRaisonDon(raisonDon);
        return annonce;
    }
    
    /**
     * Génère un identifiant unique pour une annonce.
     * Format: TYPE-UUID (ex: BIEN-a1b2c3d4)
     * 
     * @param type Type d'annonce
     * @return Identifiant unique
     */
    private static String genererIdUnique(TypeAnnonce type) {
        String uuid = UUID.randomUUID().toString().substring(0, 8);
        return type.name() + "-" + uuid;
    }
}
