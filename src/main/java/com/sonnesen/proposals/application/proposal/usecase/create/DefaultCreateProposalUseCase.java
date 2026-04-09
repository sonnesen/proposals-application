package com.sonnesen.proposals.application.proposal.usecase.create;

import java.util.Objects;

import com.sonnesen.proposals.application.proposal.gateway.ProposalGateway;
import com.sonnesen.proposals.domain.proposal.Proposal;

/**
 * Default implementation of the CreateProposalUseCase.
 */
public class DefaultCreateProposalUseCase extends CreateProposalUseCase {
    private final ProposalGateway proposalGateway;

    public DefaultCreateProposalUseCase(ProposalGateway proposalGateway) {
        Objects.requireNonNull(proposalGateway);
        this.proposalGateway = proposalGateway;
    }

    /**
     * Executes the use case to create a new proposal.
     *
     * @param input The input data required to create a new proposal.
     * @return The output data containing the ID of the created proposal.
     */
    @Override
    public CreateProposalUseCaseOutput execute(CreateProposalUseCaseInput input) {
        final var newProposal = Proposal.newProposal(input.customerName(), input.amount(), input.termInMonths());
        final var createdProposal = proposalGateway.create(newProposal);

        return new CreateProposalUseCaseOutput(createdProposal.getProposalId(),
                createdProposal.getCustomerName(), createdProposal.getAmount(),
                createdProposal.getTermInMonths(), createdProposal.getStatus().name(),
                createdProposal.getCreatedAt(), createdProposal.getUpdatedAt());
    }

}
