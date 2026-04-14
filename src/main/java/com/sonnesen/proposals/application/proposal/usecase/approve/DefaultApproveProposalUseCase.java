package com.sonnesen.proposals.application.proposal.usecase.approve;

import java.util.Objects;

import com.sonnesen.proposals.application.proposal.exception.NotFoundException;
import com.sonnesen.proposals.application.proposal.gateway.ProposalGateway;

/**
 * Default implementation of the ApproveProposalUseCase.
 */
public class DefaultApproveProposalUseCase extends ApproveProposalUseCase {
    private final ProposalGateway proposalGateway;

    public DefaultApproveProposalUseCase(final ProposalGateway proposalGateway) {
        this.proposalGateway = Objects.requireNonNull(proposalGateway, "proposalGateway must not be null");
    }

    /**
     * Executes the use case to approve an existing proposal.
     *
     * @param proposalId The ID of the proposal to approve.
     * @throws NotFoundException if the proposal with the given ID is not found.
     */
    @Override
    public void execute(final Long proposalId) {
        final var proposal = proposalGateway.getById(proposalId).orElseThrow(
                () -> new NotFoundException("Proposal with ID " + proposalId + " not found"));

        proposal.approve();
        proposalGateway.update(proposal);
    }

}
