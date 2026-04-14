package com.sonnesen.proposals.application.proposal.usecase.reject;

import java.util.Objects;

import com.sonnesen.proposals.application.proposal.exception.NotFoundException;
import com.sonnesen.proposals.application.proposal.gateway.ProposalGateway;

/**
 * Default implementation of the RejectProposalUseCase.
 */
public class DefaultRejectProposalUseCase extends RejectProposalUseCase {
    private final ProposalGateway proposalGateway;

    public DefaultRejectProposalUseCase(final ProposalGateway proposalGateway) {
        this.proposalGateway = Objects.requireNonNull(proposalGateway, "proposalGateway must not be null");
    }

    /**
     * Executes the use case to reject an existing proposal.
     *
     * @param proposalId The ID of the proposal to reject.
     * @throws NotFoundException if the proposal with the given ID is not found.
     */
    @Override
    public void execute(final Long proposalId) {
        final var proposal = proposalGateway.getById(proposalId).orElseThrow(
                () -> new NotFoundException("Proposal with ID " + proposalId + " not found"));

        proposal.reject();
        proposalGateway.update(proposal);
    }

}
