CREATE TABLE IF NOT EXISTS line_commande (
    id int auto_increment primary key ,
    commande_id int not null,
    produit_id varchar(255) not null,
    prix_unitaire decimal(10,2) ,
    qualite int,
    FOREIGN KEY (produit_id) REFERENCES produit(id),
    FOREIGN KEY (commande_id) REFERENCES commande(id)
    );