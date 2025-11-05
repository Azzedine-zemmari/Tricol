package com.tricol.Tricol.service;

import com.tricol.Tricol.dto.LineCommandeDto;
import com.tricol.Tricol.dto.command.CommandeRequestDto;
import com.tricol.Tricol.mapper.CommandeMapper;
import com.tricol.Tricol.model.Commande;
import com.tricol.Tricol.model.Fournisseur;
import com.tricol.Tricol.model.LineCommande;
import com.tricol.Tricol.model.Produit;
import com.tricol.Tricol.repository.CommandRepository;
import com.tricol.Tricol.repository.FournisseurRepository;
import com.tricol.Tricol.repository.ProduitRepository;
import com.tricol.Tricol.service.serviceInterface.CommandeService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CommandeServiceImpl implements CommandeService {
    private final CommandRepository commandRepository;
    private final FournisseurRepository fournisseurRepository;
    private final ProduitRepository produitRepository;
    private final CommandeMapper commandeMapper;


    public CommandeServiceImpl(CommandRepository commandRepository, FournisseurRepository fournisseurRepository, ProduitRepository produitRepository, CommandeMapper commandeMapper) {
        this.commandRepository = commandRepository;
        this.fournisseurRepository = fournisseurRepository;
        this.produitRepository = produitRepository;
        this.commandeMapper = commandeMapper;
    }

    @Override
    @Transactional
    public Commande createCommande(CommandeRequestDto dto) {
        // Fetch the fournisseur
        Fournisseur fournisseur = fournisseurRepository.findById(dto.getFournisseurId())
                .orElseThrow(() -> new RuntimeException(
                        "Fournisseur with ID " + dto.getFournisseurId() + " not found")
                );

        // Create a new Commande
        Commande commande = new Commande();
        commande.setFournisseur(fournisseur);
        commande.setStatut(dto.getStatut());

        BigDecimal montantTotal = BigDecimal.ZERO;

        // Loop through each product (line) in the request
        for (LineCommandeDto lineDto : dto.getProduits()) {
            Produit produit = produitRepository.findById(lineDto.getProduitId())
                    .orElseThrow(() -> new RuntimeException(
                            "Produit with ID " + lineDto.getProduitId() + " not found")
                    );

            BigDecimal prixUnitaire = BigDecimal.valueOf(produit.getPrix_unitaire());
            BigDecimal quantite = BigDecimal.valueOf(lineDto.getQuantite());
            BigDecimal sousTotal = prixUnitaire.multiply(quantite);

            // Accumulate total
            montantTotal = montantTotal.add(sousTotal);

            // Create and link LineCommande
            LineCommande line = new LineCommande();
            line.setProduit(produit);
            line.setQuantite(lineDto.getQuantite());
            line.setPrix_unitaire(produit.getPrix_unitaire());
            line.setCommande(commande);

            // Add this line to the commande (so it gets saved via cascade)
            commande.getLignesCommande().add(line);
        }

        // Set total and save
        commande.setMontant_total(montantTotal);

        Commande saved = commandRepository.save(commande);

        System.out.println("Commande created with ID: " + saved.getId() +
                " | Montant total: " + saved.getMontant_total());

        return saved;
    }

    @Override
    public Optional<CommandeRequestDto> getCommandeById(int id){
        return commandRepository.findById(id).map(commande -> commandeMapper.toDto(commande));
    }
    @Transactional
    @Override
    public CommandeRequestDto updateStatus(int id, String nouveauStatut) {
        Optional<Commande> commandeOpt = commandRepository.findById(id);

        if (commandeOpt.isEmpty()) {
            return null; // or throw exception
        }

        Commande commande = commandeOpt.get();
        commande.setStatut(nouveauStatut); // update only status
        Commande updated = commandRepository.save(commande);

        // MapStruct can still be used if you want to return a DTO
        return commandeMapper.toDto(updated);
    }

    @Override
    public List<CommandeRequestDto> getAllCommandes() {
        return commandRepository.findAll().stream().map(commandeMapper::toDto).toList();
    }

    @Override
    public CommandeRequestDto findCommandeByID(int id){
        return commandeMapper.toDto(commandRepository.findById(id).get());
    }
}
