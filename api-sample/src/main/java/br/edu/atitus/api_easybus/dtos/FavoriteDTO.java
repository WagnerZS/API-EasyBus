package br.edu.atitus.api_easybus.dtos;

import java.util.UUID;

public record FavoriteDTO(UUID pointId, String description, Double latitude, Double longitude) {

}

