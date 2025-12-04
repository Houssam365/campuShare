package com.campusshare;

import com.campusshare.adapter.GoogleCalendarAdapter;
import com.campusshare.factory.AnnonceFactory;
import com.campusshare.model.*;
import com.campusshare.model.annonce.*;
import com.campusshare.paiement.*;
import com.campusshare.service.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Interface Graphique (GUI) pour CampusShare.
 * Connecte les services existants à une interface Swing moderne.
 */
public class CampusShareGUI extends JFrame {

    // Services du Backend
    private AnnonceService annonceService;
    private TransactionService transactionService;
    private ReservationService reservationService;
    private List<Utilisateur> utilisateurs;
    private List<Categorie> categories;

    // État actuel
    private Utilisateur currentUser;

    // Composants UI
    private JComboBox<String> userSelector;
    private JTable adsTable;
    private DefaultTableModel tableModel;
    private JTextArea logArea;

    public static void main(String[] args) {
        // Look & Feel système pour un aspect plus moderne
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> new CampusShareGUI().setVisible(true));
    }

    public CampusShareGUI() {
        // 1. Initialisation du Backend
        initBackend();

        // 2. Configuration de la fenêtre
        setTitle("📱 CampusShare - Plateforme Étudiante");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // 3. Barre supérieure (Header & Login)
        JPanel headerPanel = createHeaderPanel();
        add(headerPanel, BorderLayout.NORTH);

        // 4. Contenu Principal (Onglets)
        JTabbedPane tabbedPane = new JTabbedPane();
        
        // Onglet 1: Marché (Annonces)
        JPanel marketPanel = createMarketPanel();
        tabbedPane.addTab("🛒 Marché & Annonces", marketPanel);

        // Onglet 2: Mes Activités (Console/Logs)
        JPanel activityPanel = createActivityPanel();
        tabbedPane.addTab("📜 Journal d'activité", activityPanel);

        add(tabbedPane, BorderLayout.CENTER);

        // 5. Chargement initial
        refreshAdsTable();
        log("Bienvenue sur CampusShare v1.0 GUI");
    }

    // ================== INIT BACKEND ==================

    private void initBackend() {
        // On réutilise la logique de CampusShareApp pour initialiser les données
        CampusShareApp app = new CampusShareApp();
        this.annonceService = app.getAnnonceService();
        this.reservationService = app.getReservationService();
        this.utilisateurs = app.getUtilisateurs();
        this.categories = app.getCategories();
        this.transactionService = new TransactionService(); // Nouveau service pour la GUI
        
        // Simuler quelques annonces par défaut via la Factory
        CampusShareApp demo = new CampusShareApp();
        demo.executerDemonstration(); // Peuple les services avec des données
        
        // Services récupérés après population
        this.annonceService = demo.getAnnonceService();
        this.reservationService = demo.getReservationService();
        
        // Utilisateur par défaut
        this.currentUser = utilisateurs.get(1); // Bob
    }

    // ================== UI COMPONENTS ==================

    private JPanel createHeaderPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(63, 81, 181)); // Bleu Material Design
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JLabel titleLabel = new JLabel("CampusShare");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);

        // Sélecteur d'utilisateur (Mock Login)
        JPanel userPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        userPanel.setOpaque(false);
        
        JLabel userLabel = new JLabel("Connecté en tant que: ");
        userLabel.setForeground(Color.WHITE);
        
        userSelector = new JComboBox<>();
        for (Utilisateur u : utilisateurs) {
            userSelector.addItem(u.getNomComplet() + " (" + ((Etudiant)u).getSoldePoints() + " pts)");
        }
        userSelector.setSelectedIndex(1); // Default Bob
        userSelector.addActionListener(e -> {
            int idx = userSelector.getSelectedIndex();
            currentUser = utilisateurs.get(idx);
            log("👤 Changement d'utilisateur: " + currentUser.getNomComplet());
        });

        userPanel.add(userLabel);
        userPanel.add(userSelector);

        panel.add(titleLabel, BorderLayout.WEST);
        panel.add(userPanel, BorderLayout.EAST);

        return panel;
    }

    private JPanel createMarketPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // --- Toolbar ---
        JPanel toolbar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton refreshBtn = new JButton("🔄 Actualiser");
        JButton createBtn = new JButton("➕ Publier une annonce");
        JButton viewBtn = new JButton("👁️ Voir Détails");
        JButton buyBtn = new JButton("💳 Acheter / Réserver");

        styleButton(createBtn, new Color(76, 175, 80)); // Vert
        styleButton(buyBtn, new Color(255, 152, 0));    // Orange

        toolbar.add(refreshBtn);
        toolbar.add(createBtn);
        toolbar.add(viewBtn);
        toolbar.add(buyBtn);

        // --- Table ---
        String[] columns = {"ID", "Type", "Titre", "Prix", "Catégorie", "Auteur", "Statut"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        adsTable = new JTable(tableModel);
        adsTable.setRowHeight(25);
        adsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(adsTable);

        // --- Actions ---
        refreshBtn.addActionListener(e -> refreshAdsTable());
        createBtn.addActionListener(e -> openCreateAdDialog());
        viewBtn.addActionListener(e -> showAdDetails());
        buyBtn.addActionListener(e -> openTransactionDialog());

        panel.add(toolbar, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createActivityPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        logArea = new JTextArea();
        logArea.setEditable(false);
        logArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        logArea.setBackground(new Color(245, 245, 245));
        panel.add(new JScrollPane(logArea), BorderLayout.CENTER);
        return panel;
    }

    // ================== LOGIC & ACTIONS ==================

    private void refreshAdsTable() {
        tableModel.setRowCount(0); // Clear
        List<Annonce> annonces = annonceService.getAnnonces();
        
        for (Annonce a : annonces) {
            tableModel.addRow(new Object[]{
                a.getId(),
                a.getType(),
                a.getTitre(),
                a.getPrixBase() + " €/pts",
                a.getCategorie().getNom(),
                a.getAuteur().getNomComplet(),
                a.getStatut()
            });
        }
        updateUserBalanceDisplay();
    }

    private void openCreateAdDialog() {
        JDialog dialog = new JDialog(this, "Publier une Annonce (Factory Pattern)", true);
        dialog.setLayout(new GridLayout(6, 2, 10, 10));
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(this);

        JTextField titleField = new JTextField();
        JTextField descField = new JTextField();
        JTextField priceField = new JTextField("0");
        JComboBox<TypeAnnonce> typeCombo = new JComboBox<>(TypeAnnonce.values());
        JComboBox<String> catCombo = new JComboBox<>();
        categories.forEach(c -> catCombo.addItem(c.getNom()));

        dialog.add(new JLabel("  Type (Factory):")); dialog.add(typeCombo);
        dialog.add(new JLabel("  Titre:")); dialog.add(titleField);
        dialog.add(new JLabel("  Description:")); dialog.add(descField);
        dialog.add(new JLabel("  Prix:")); dialog.add(priceField);
        dialog.add(new JLabel("  Catégorie:")); dialog.add(catCombo);

        JButton submitBtn = new JButton("Publier");
        submitBtn.addActionListener(e -> {
            try {
                // Utilisation de la Factory
                TypeAnnonce type = (TypeAnnonce) typeCombo.getSelectedItem();
                double prix = Double.parseDouble(priceField.getText());
                Categorie cat = categories.get(catCombo.getSelectedIndex());
                
                // Appel au service (qui appelle la Factory)
                if (type == TypeAnnonce.BIEN) {
                    annonceService.publierBien(titleField.getText(), descField.getText(), currentUser, cat, "Bon état", prix);
                } else if (type == TypeAnnonce.SERVICE) {
                    annonceService.publierService(titleField.getText(), descField.getText(), currentUser, cat, "Service", prix, 60);
                } else {
                    annonceService.publierDon(titleField.getText(), descField.getText(), currentUser, cat, "Bon état", "Don");
                }
                
                log("✅ Annonce publiée via Factory: " + titleField.getText());
                refreshAdsTable();
                dialog.dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, "Erreur: " + ex.getMessage());
            }
        });

        dialog.add(new JLabel("")); 
        dialog.add(submitBtn);
        dialog.setVisible(true);
    }

    private void openTransactionDialog() {
        int row = adsTable.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner une annonce.");
            return;
        }

        String id = (String) tableModel.getValueAt(row, 0);
        Annonce annonce = annonceService.trouverParId(id);

        if (annonce.getAuteur().getId().equals(currentUser.getId())) {
            JOptionPane.showMessageDialog(this, "Vous ne pouvez pas acheter votre propre annonce.");
            return;
        }
        
        if (!annonce.estDisponible()) {
            JOptionPane.showMessageDialog(this, "Cette annonce n'est plus disponible.");
            return;
        }

        // Dialog pour choisir la stratégie
        String[] options = {"Points Campus", "Carte Bancaire (Simulé)", "Gratuit (Don)"};
        int choice = JOptionPane.showOptionDialog(this, 
            "Choisissez votre méthode de paiement (Strategy Pattern):", 
            "Transaction", 
            JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

        if (choice != -1) {
            IPaiementStrategy strategy;
            if (choice == 0) strategy = new PaiementPoints();
            else if (choice == 1) strategy = new PaiementCarteSimule();
            else strategy = new PaiementGratuit();

            // Exécution via TransactionService
            Transaction tx = transactionService.effectuerTransaction(annonce, (Etudiant)currentUser, strategy);

            if (tx != null && tx.getStatut() == StatutTransaction.VALIDE) {
                log("💰 Transaction réussie via " + strategy.getClass().getSimpleName());
                log("   Montant: " + tx.getMontant());
                JOptionPane.showMessageDialog(this, "Achat réussi !");
                refreshAdsTable();
            } else {
                log("❌ Echec de la transaction.");
                JOptionPane.showMessageDialog(this, "Echec de la transaction (Solde insuffisant ?).");
            }
        }
    }
    
    private void showAdDetails() {
        int row = adsTable.getSelectedRow();
        if (row == -1) return;
        
        String id = (String) tableModel.getValueAt(row, 0);
        Annonce a = annonceService.trouverParId(id);
        
        String details = "ID: " + a.getId() + "\n" +
                         "Titre: " + a.getTitre() + "\n" +
                         "Description: " + a.getDescription() + "\n" +
                         "Prix: " + a.getPrixBase() + "\n" +
                         "Vendeur: " + a.getAuteur().getNomComplet() + "\n\n" +
                         "Détails Spécifiques:\n" + a.getDetailsSpecifiques();
                         
        JOptionPane.showMessageDialog(this, details, "Détails Annonce", JOptionPane.INFORMATION_MESSAGE);
    }

    // ================== UTILS ==================

    private void log(String msg) {
        logArea.append(LocalDateTime.now().toLocalTime() + " - " + msg + "\n");
        logArea.setCaretPosition(logArea.getDocument().getLength());
    }
    
    private void updateUserBalanceDisplay() {
        // Mettre à jour le texte du combo box pour refléter le nouveau solde
        // C'est un peu un hack UI, mais ça marche pour une démo
        if (userSelector != null && currentUser instanceof Etudiant) {
           // On ne rafraichit pas tout le combo pour ne pas perdre la sélection, 
           // juste logguer le nouveau solde
           int solde = ((Etudiant)currentUser).getSoldePoints();
           setTitle("📱 CampusShare - Utilisateur: " + currentUser.getNom() + " | Solde: " + solde + " pts");
        }
    }

    private void styleButton(JButton btn, Color bg) {
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Arial", Font.BOLD, 12));
    }
}