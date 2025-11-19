# 🧵 Tricol – Module de Gestion des Commandes Fournisseurs
API REST développée avec **Spring Boot**

---

## 📌 Contexte du projet
L’entreprise **Tricol**, spécialisée dans la conception et fabrication de vêtements professionnels, poursuit la digitalisation de ses processus internes.

Après la mise en place du module de gestion des fournisseurs, un nouveau module dédié à la **gestion des commandes fournisseurs** a été développé pour suivre les approvisionnements en matières premières et équipements.

Ce module constitue une étape essentielle vers la création d’un système complet de gestion de la production.

---

# 🎯 Objectif du projet
Développer une **API REST complète** utilisant :

- Spring Boot
- Spring Data JPA
- MapStruct
- Liquibase
- Swagger / OpenAPI
- Jakarta Validation

L’objectif est de gérer **tout le cycle de vie** d’une commande fournisseur, incluant la création, la modification, la livraison, la valorisation du stock et la consultation des mouvements.

---

# 🧩 Fonctionnalités principales

## 🔹 Gestion des Fournisseurs
- Ajouter un fournisseur
- Modifier un fournisseur
- Supprimer un fournisseur
- Consulter un fournisseur

**Champs :** société, adresse, contact, email, téléphone, ville, ICE

---

## 🔹 Gestion des Produits
- Ajouter / modifier / supprimer / consulter un produit  
  **Champs :** nom, description, prix unitaire, catégorie, stockActuel

Chaque commande contient un ou plusieurs produits.

---

## 🔹 Gestion des Commandes Fournisseurs
- Créer une commande
- Annuler ou modifier une commande
- Consulter toutes les commandes
- Voir les détails d’une commande
- Associer un fournisseur
- Associer des produits à la commande
- Calcul automatique du montant total

**Statuts :** `EN_ATTENTE`, `VALIDÉE`, `LIVRÉE`, `ANNULÉE`

---

## 🔹 Gestion du Stock & Valorisation

### 🎯 Objectifs
- Suivre **entrées / sorties / ajustements**
- Mettre à jour automatiquement le **stock disponible**
- Valoriser les coûts selon :
    - **FIFO**
    - **CUMP** (par défaut)

### 📦 Fonctionnalités
#### 1️⃣ Mouvements automatiques
Lorsqu'une commande est livrée → création de mouvements **ENTREE**.

#### 2️⃣ Mise à jour du stock
- ENTREE → augmente stockActuel
- SORTIE / AJUSTEMENT → diminue stockActuel

#### 3️⃣ Calcul du coût total
\[
montantTotal = \sum (prixUnitaireProduit \times quantitéCommandée)
\]

#### 4️⃣ Consultation de l’historique des mouvements
Filtrage par :
- produit
- type de mouvement
- commande associée

---

# 📚 Pagination & Filtrage
Les endpoints GET supportent :

| Paramètre | Description |
|----------|-------------|
| `page` | numéro de page (défaut : 0) |
| `size` | taille de page (défaut : 10) |
| `sort` | champ + ordre (`field,asc`) |

Exemple de réponse :
```json
{
  "content": [...],
  "totalElements": 120,
  "totalPages": 12,
  "pageNumber": 0
}

```
# Gestion BD :

- 🟦 Liquibase utilisé pour les migrations.

# 📦 Modèle de données (simplifié)
*🧍 Fournisseur*

 - id

- societe

- adresse

- contact

- email

- téléphone

- ville

- ICE

*📦 Produit*

- id

- nom

- description

- prixUnitaire

- catégorie

- stockActuel

*📄 CommandeFournisseur*

- id

- dateCommande

- statut

- montantTotal

- fournisseur (ManyToOne)

- produits (ManyToMany)

*🔄 MouvementStock*

- id

- dateMouvement

- type (ENTREE / SORTIE / AJUSTEMENT)

- quantité

- produit

- commandeFournisseur


# 🧪 Phase de Testing

## 🎯 Contexte
Le test logiciel permet de garantir que le module fonctionne comme prévu et que les interactions entre les composants restent fiables.

L’objectif est de valider le cycle complet :

- Gestion des fournisseurs
- Gestion des produits
- Commandes fournisseurs
- Mouvements de stock
- Valorisation FIFO / CUMP

Les tests assurent un code **robuste, fiable et maintenable**.

---

## 🧪 Types de Tests

### 🔹 1. Tests Unitaires
Objectif : vérifier la logique métier des classes principales.

Fonctionnalités couvertes :

- Gestion des fournisseurs
- Création / modification des produits
- Calcul du montant total d’une commande
- Génération automatique des mouvements de stock
- Calcul FIFO / CUMP

Outils utilisés :

- **JUnit 5**
- **Mockito** pour le mocking
- Tests centrés sur les **services** (❌ pas de tests sur les repositories seuls)

---

### 🔹 2. Tests d’Intégration
Objectif : valider le fonctionnement global du module.

Vérifications effectuées :

- Fonctionnement des endpoints REST
- Interaction Service + Repository + Base de données
- Génération automatique des mouvements de stock
- Mise à jour du stock après chaque opération

Outils utilisés :

- **Spring Boot Test**
- **MockMvc** ou **TestRestTemplate**
- Base de données de test : **H2** ou **Testcontainers**

---

### 🔹 3. Contrôle de Qualité

- Analyse de la couverture de code avec **JaCoCo**
- Génération des rapports via :

```bash
mvn test
mvn jacoco:report
```

## 🔧 Technologies utilisées

| Technologie         | Rôle                              |
|---------------------|------------------------------------|
| **Spring Boot**     | API REST                           |
| **Spring Data JPA** | Persistance                        |
| **MapStruct**       | Mapping DTO                        |
| **Liquibase**       | Migration Base de Données          |
| **JUnit 5**         | Tests unitaires                    |
| **Mockito**         | Mocking                            |
| **H2 / Testcontainers** | Tests d'intégration           |
| **JaCoCo**          | Couverture de code                |
| **Swagger**         | Documentation API                  |
