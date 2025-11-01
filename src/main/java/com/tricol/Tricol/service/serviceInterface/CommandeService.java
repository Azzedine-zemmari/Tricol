package com.tricol.Tricol.service.serviceInterface;

import com.tricol.Tricol.dto.command.CommandeRequestDto;
import com.tricol.Tricol.model.Commande;

public interface CommandeService {
    Commande createCommande(CommandeRequestDto dto);
}
