package com.sonnesen.proposals.application.proposal.usecase.update;

import java.math.BigDecimal;

/**
 * Input data required to update a proposal.
 */
public record UpdateProposalUseCaseInput(Long proposalId, String customerName, BigDecimal amount,
        Integer termInMonths) {

    public static UpdateProposalUseCaseInput with(Long proposalId, String customerName,
            BigDecimal amount, Integer termInMonths) {
        return new UpdateProposalUseCaseInput(proposalId, customerName, amount, termInMonths);
    }

}
