CREATE TABLE IF NOT EXISTS commande (
    id int AUTO_INCREMENT primary key ,
    fournisseur_id varchar(255) not null ,
    date_command DATETIME DEFAULT CURRENT_TIMESTAMP,
    montant_total DECIMAL(10,2) DEFAULT 0.0,
    statut ENUM('EN_ATTENTE','VALIDÉE','LIVRÉE','ANNULÉE') DEFAULT 'EN_ATTENTE',
    FOREIGN KEY (fournisseur_id) references fournisseur(id)
)