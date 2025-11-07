package com.tricol.Tricol.service.serviceInterface;

import com.tricol.Tricol.dto.command.CommandeRequestDto;
import com.tricol.Tricol.model.Commande;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface CommandeService {
    Commande createCommande(CommandeRequestDto dto);
    Optional<CommandeRequestDto> getCommandeById(int id);
    CommandeRequestDto updateStatus(int id ,String dto);
    Page<CommandeRequestDto> getAllCommandes(int page , int size);
    CommandeRequestDto findCommandeByID(int id);
}
