CREATE TABLE IF NOT EXISTS mouvement_stock (
    id varchar(255) primary key,
    produit_id varchar(255) not null,
    commande_id varchar(255) not null,
    date_mouvement DATETIME DEFAULT CURRENT_TIMESTAMP,
    quantite decimal(10,2) default 0.0,
    type ENUM('ENTREE','SORTIE','AJUSTEMENT'),
    foreign key (produit_id) references produit(id),
    foreign key (commande_id) references commande(id)
);