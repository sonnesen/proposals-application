package com.sonnesen.proposals.application.proposal.usecase.update;

import java.math.BigDecimal;
import java.time.Instant;

/**
 * Output data returned after creating a new proposal.
 */
public record UpdateProposalUseCaseOutput(Long proposalId, String customerName,
        BigDecimal amount, Integer termInMonths, String status, Instant createdAt, Instant updatedAt) {

}
