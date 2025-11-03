package com.tricol.Tricol.service.serviceInterface;

import com.tricol.Tricol.dto.command.CommandeRequestDto;
import com.tricol.Tricol.model.Commande;

import java.util.Optional;

public interface CommandeService {
    Commande createCommande(CommandeRequestDto dto);
    Optional<CommandeRequestDto> getCommandeById(int id);
    CommandeRequestDto updateStatus(int id ,String dto);
}
