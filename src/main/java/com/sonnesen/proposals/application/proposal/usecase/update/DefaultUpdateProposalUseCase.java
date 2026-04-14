package com.sonnesen.proposals.application.proposal.usecase.update;

import java.util.Objects;

import com.sonnesen.proposals.application.proposal.exception.NotFoundException;
import com.sonnesen.proposals.application.proposal.gateway.ProposalGateway;

/**
 * Default implementation of the UpdateProposalUseCase.
 */
public class DefaultUpdateProposalUseCase extends UpdateProposalUseCase {
    private final ProposalGateway proposalGateway;

    public DefaultUpdateProposalUseCase(final ProposalGateway proposalGateway) {
        this.proposalGateway = Objects.requireNonNull(proposalGateway, "proposalGateway must not be null");
    }

    /**
     * Executes the use case to update an existing proposal.
     *
     * @param input The input data required to update a proposal.
     * @return The output data containing the ID of the updated proposal.
     */
    @Override
    public UpdateProposalUseCaseOutput execute(final UpdateProposalUseCaseInput input) {
        final var proposal = proposalGateway.getById(input.proposalId()).orElseThrow(() -> new NotFoundException(
                "Proposal with ID " + input.proposalId() + " not found"));

        proposal.update(input.customerName(), input.amount(), input.termInMonths());
        final var updatedProposal = proposalGateway.update(proposal);

        return new UpdateProposalUseCaseOutput(updatedProposal.getProposalId(),
                updatedProposal.getCustomerName(), updatedProposal.getAmount(),
                updatedProposal.getTermInMonths(), updatedProposal.getStatus().name(),
                updatedProposal.getCreatedAt(), updatedProposal.getUpdatedAt());
    }

}
