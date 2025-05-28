package ch.my.familytrust.dtos;

import java.util.UUID;

public record DeleteAccountRequest(UUID userId, UUID accountId) {
}
