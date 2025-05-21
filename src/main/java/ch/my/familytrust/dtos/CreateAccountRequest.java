package ch.my.familytrust.dtos;

import java.util.UUID;

public record CreateAccountRequest(String accountName, String currencyCode, UUID ownerUserId) {
}