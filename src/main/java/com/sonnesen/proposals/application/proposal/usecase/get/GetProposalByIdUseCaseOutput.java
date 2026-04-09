package com.sonnesen.proposals.application.proposal.usecase.get;

import java.math.BigDecimal;
import java.time.Instant;

import com.sonnesen.proposals.domain.proposal.Proposal;

/**
 * Output data returned when retrieving a proposal by its ID.
 *
 * @param proposalId   The unique identifier of the proposal.
 * @param customerName The name of the customer associated with the proposal.
 * @param status       The current status of the proposal.
 * @param amount       The amount requested in the proposal.
 * @param termInMonths The term of the proposal in months.
 * @param createdAt    The timestamp when the proposal was created.
 * @param updatedAt    The timestamp when the proposal was last updated.
 */
public record GetProposalByIdUseCaseOutput(Long proposalId, String customerName, String status,
        BigDecimal amount, Integer termInMonths, Instant createdAt, Instant updatedAt) {

    /**
     * Creates a GetProposalByIdOutput from a Proposal domain object.
     *
     * @param proposal The Proposal domain object.
     * @return the corresponding GetProposalByIdOutput
     */
    public static GetProposalByIdUseCaseOutput fromDomain(Proposal proposal) {
        return new GetProposalByIdUseCaseOutput(proposal.getProposalId(),
                proposal.getCustomerName(), proposal.getStatus().name(), proposal.getAmount(),
                proposal.getTermInMonths(), proposal.getCreatedAt(), proposal.getUpdatedAt());
    }
}
