package com.sonnesen.proposals.application.proposal.usecase.create;

import java.math.BigDecimal;

/**
 * Input data required to create a new proposal.
 */
public record CreateProposalUseCaseInput(String customerName, BigDecimal amount,
        Integer termInMonths) {

    public static CreateProposalUseCaseInput with(String customerName, BigDecimal amount,
            Integer termInMonths) {
        return new CreateProposalUseCaseInput(customerName, amount, termInMonths);
    }

}
