package ch.my.familytrust.dtos;

import ch.my.familytrust.enums.CashflowType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record AccountCashFlowRequest(LocalDateTime cashFlowDate, UUID accountId, UUID userId, BigDecimal cashFlowAmount, CashflowType cashFlowType, String comment) {
}

