CREATE TABLE IF NOT EXISTS produit  (
    id varchar(255) PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    description TEXT,
    prix_unitaire DECIMAL(10,2) NOT NULL,
    categorie VARCHAR(50),
    stock_actuel INT DEFAULT 0
);