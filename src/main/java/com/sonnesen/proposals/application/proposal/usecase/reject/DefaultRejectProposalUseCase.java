package com.sonnesen.proposals.application.proposal.usecase.reject;

import java.util.Objects;

import com.sonnesen.proposals.application.proposal.gateway.ProposalGateway;
import com.sonnesen.proposals.domain.exception.NotFoundException;

/**
 * Default implementation of the RejectProposalUseCase.
 */
public class DefaultRejectProposalUseCase extends RejectProposalUseCase {
    private final ProposalGateway proposalGateway;

    public DefaultRejectProposalUseCase(ProposalGateway proposalGateway) {
        Objects.requireNonNull(proposalGateway);
        this.proposalGateway = proposalGateway;
    }

    /**
     * Executes the use case to reject an existing proposal.
     *
     * @param proposalId The ID of the proposal to reject.
     * @throws NotFoundException if the proposal with the given ID is not found.
     */
    @Override
    public void execute(Long proposalId) {
        final var proposal = proposalGateway.getById(proposalId).orElseThrow(
                () -> new NotFoundException("Proposal with ID " + proposalId + " not found"));

        proposal.reject();
        proposalGateway.update(proposal);
    }

}
