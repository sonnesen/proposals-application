package com.sonnesen.proposals.application.proposal.usecase.list;

import java.math.BigDecimal;
import java.time.Instant;

import com.sonnesen.proposals.domain.proposal.Proposal;

/**
 * Output data representing a proposal in the list of proposals.
 */
public record ListProposalsUseCaseOutput(Long proposalId, String customerName, BigDecimal amount,
        Integer termInMonths, String status, Instant createdAt, Instant updatedAt) {

    /**
     * Creates a ListProposalsUseCaseOutput from a Proposal domain object.
     *
     * @param proposal
     * @return the corresponding ListProposalsUseCaseOutput
     */
    public static ListProposalsUseCaseOutput fromDomain(Proposal proposal) {
        return new ListProposalsUseCaseOutput(proposal.getProposalId(), proposal.getCustomerName(),
                proposal.getAmount(), proposal.getTermInMonths(), proposal.getStatus().name(),
                proposal.getCreatedAt(), proposal.getUpdatedAt());
    }
}
