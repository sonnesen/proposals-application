package com.sonnesen.proposals.application.proposal.usecase.cancel;

import java.util.Objects;

import com.sonnesen.proposals.application.proposal.gateway.ProposalGateway;
import com.sonnesen.proposals.domain.exception.NotFoundException;

/**
 * Default implementation of the CancelProposalUseCase.
 */
public class DefaultCancelProposalUseCase extends CancelProposalUseCase {
    private final ProposalGateway proposalGateway;

    public DefaultCancelProposalUseCase(ProposalGateway proposalGateway) {
        Objects.requireNonNull(proposalGateway);
        this.proposalGateway = proposalGateway;
    }

    /**
     * Executes the use case to cancel an existing proposal.
     *
     * @param proposalId The ID of the proposal to cancel.
     * @throws NotFoundException if the proposal with the given ID is not found.
     */
    @Override
    public void execute(Long proposalId) {
        final var proposal = proposalGateway.getById(proposalId).orElseThrow(
                () -> new NotFoundException("Proposal with ID " + proposalId + " not found"));

        proposal.cancel();
        proposalGateway.update(proposal);
    }

}
