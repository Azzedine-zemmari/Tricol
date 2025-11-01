package com.tricol.Tricol.service;

import com.tricol.Tricol.dto.command.CommandeRequestDto;
import com.tricol.Tricol.model.Commande;
import com.tricol.Tricol.model.Fournisseur;
import com.tricol.Tricol.model.Produit;
import com.tricol.Tricol.repository.CommandRepository;
import com.tricol.Tricol.repository.FournisseurRepository;
import com.tricol.Tricol.repository.ProduitRepository;
import com.tricol.Tricol.service.serviceInterface.CommandeService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CommandeServiceImpl implements CommandeService {
    private final CommandRepository commandRepository;
    private final FournisseurRepository fournisseurRepository;
    private final ProduitRepository produitRepository;


    public CommandeServiceImpl(CommandRepository commandRepository,  FournisseurRepository fournisseurRepository, ProduitRepository produitRepository) {
        this.commandRepository = commandRepository;
        this.fournisseurRepository = fournisseurRepository;
        this.produitRepository = produitRepository;
    }

    @Override
    public Commande createCommande(CommandeRequestDto dto){
        // Fetch related entities
        Fournisseur fournisseur = fournisseurRepository.findById(dto.getFournisseurId())
                .orElseThrow(() -> new RuntimeException("Fournisseur with ID " + dto.getFournisseurId() + " not found"));

        List<Produit> produits = produitRepository.findAllById(dto.getProduits());
        if (produits.size() != dto.getProduits().size()) {
            throw new RuntimeException("One or more products not found");
        }

        // Map DTO to Entity
        Commande commande = new Commande();
        commande.setId(UUID.randomUUID().toString());  // Or use generator annotation on entity
        commande.setFournisseur(fournisseur);
        commande.setProduits(produits);
        commande.setMontant_total(dto.getMontant_total());
        commande.setStatut(dto.getStatut());

        // Save via repository
        Commande saved = commandRepository.save(commande);
        System.out.println("Generated ID: " + saved.getId());
        return saved;
    }
}
