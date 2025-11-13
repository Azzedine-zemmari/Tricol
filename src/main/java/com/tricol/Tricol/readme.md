# 🧵 Tricol - Module de Gestion des Commandes Fournisseurs

## 🏢 Contexte du projet
L’entreprise **Tricol**, spécialisée dans la **conception et fabrication de vêtements professionnels**, poursuit la **digitalisation de ses processus internes**.  
Après la mise en place du module de gestion des fournisseurs, la direction souhaite développer un **module complémentaire** dédié à la **gestion des commandes fournisseurs**.  
Ce module permet un **suivi rigoureux des approvisionnements** en matières premières et équipements, et constitue une **étape clé** vers un système complet de gestion des approvisionnements et de la production.

---

## 🎯 Objectif du projet
Développer une **API REST complète avec Spring Boot** permettant de gérer **tout le cycle de vie des commandes fournisseurs**, de leur création à leur suivi, en respectant les bonnes pratiques et les concepts modernes du framework.

---

## ⚙️ Stack Technique

| Technologie | Description |
|--------------|-------------|
| **Spring Boot** | Framework principal pour le développement de l’API REST |
| **Spring Data JPA** | Gestion de la persistance et des requêtes vers la base de données |
| **MapStruct** | Mapping automatique entre les entités et les DTO |
| **Liquibase** | Gestion des migrations et de la structure de la base de données |
| **Swagger / OpenAPI** | Documentation automatique et interactive de l’API |
| **Jakarta Validation** | Validation des données en entrée |
| **H2 Database** | Base de données en mémoire utilisée pour le développement et les tests |

---

## 🧩 Architecture du projet

L’application suit une architecture **en couches** claire :

src/
├── 📂 controller/      → Exposition des endpoints REST  
├── 📂 service/         → Logique métier  
├── 📂 repository/      → Accès aux données (Spring Data JPA)  
├── 📂 dto/             → Objets de transfert (DTO)  
├── 📂 mapper/          → Mappers MapStruct pour la conversion Entity ↔ DTO  
├── 📂 model/           → Entités JPA  
├── 📂 config/          → Configuration (Swagger, Liquibase, etc.)  
└── 📜 Application.java → Point d’entrée principal de l’application
 ## add featurs 