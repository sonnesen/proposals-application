package com.sonnesen.proposals.application.proposal.usecase.cancel;

import java.util.Objects;

import com.sonnesen.proposals.application.proposal.exception.NotFoundException;
import com.sonnesen.proposals.application.proposal.gateway.ProposalGateway;

/**
 * Default implementation of the CancelProposalUseCase.
 */
public class DefaultCancelProposalUseCase extends CancelProposalUseCase {
    private final ProposalGateway proposalGateway;

    public DefaultCancelProposalUseCase(final ProposalGateway proposalGateway) {
        this.proposalGateway = Objects.requireNonNull(proposalGateway, "proposalGateway must not be null");
    }

    /**
     * Executes the use case to cancel an existing proposal.
     *
     * @param proposalId The ID of the proposal to cancel.
     * @throws NotFoundException if the proposal with the given ID is not found.
     */
    @Override
    public void execute(final Long proposalId) {
        final var proposal = proposalGateway.getById(proposalId).orElseThrow(
                () -> new NotFoundException("Proposal with ID " + proposalId + " not found"));

        proposal.cancel();
        proposalGateway.update(proposal);
    }

}
