package com.tricol.Tricol.service;

import com.tricol.Tricol.dto.command.CommandeRequestDto;
import com.tricol.Tricol.mapper.CommandeMapper;
import com.tricol.Tricol.model.Commande;
import com.tricol.Tricol.model.Fournisseur;
import com.tricol.Tricol.model.Produit;
import com.tricol.Tricol.repository.CommandRepository;
import com.tricol.Tricol.repository.FournisseurRepository;
import com.tricol.Tricol.repository.ProduitRepository;
import com.tricol.Tricol.service.serviceInterface.CommandeService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

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
    public Commande createCommande(CommandeRequestDto dto){
        // Fetch related entities
        Fournisseur fournisseur = fournisseurRepository.findById(dto.getFournisseurId())
                .orElseThrow(() -> new RuntimeException("Fournisseur with ID " + dto.getFournisseurId() + " not found"));


        // Map DTO to Entity
        Commande commande = new Commande();

        commande.setFournisseur(fournisseur);
        if(dto.getProduits() != null) {
        commande.setProduits(dto.getProduits());
        }
        commande.setMontant_total(dto.getMontant_total());
        commande.setStatut(dto.getStatut());

        // Save via repository
        Commande saved = commandRepository.save(commande);
        System.out.println("Generated ID: " + saved.getId());
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
