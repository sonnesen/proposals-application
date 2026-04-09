package com.sonnesen.proposals.application.proposal.usecase.approve;

import java.util.Objects;

import com.sonnesen.proposals.application.proposal.gateway.ProposalGateway;
import com.sonnesen.proposals.domain.exception.NotFoundException;

/**
 * Default implementation of the ApproveProposalUseCase.
 */
public class DefaultApproveProposalUseCase extends ApproveProposalUseCase {
    private final ProposalGateway proposalGateway;

    public DefaultApproveProposalUseCase(ProposalGateway proposalGateway) {
        Objects.requireNonNull(proposalGateway);
        this.proposalGateway = proposalGateway;
    }

    /**
     * Executes the use case to approve an existing proposal.
     *
     * @param proposalId The ID of the proposal to approve.
     * @throws NotFoundException if the proposal with the given ID is not found.
     */
    @Override
    public void execute(Long proposalId) {
        final var proposal = proposalGateway.getById(proposalId).orElseThrow(
                () -> new NotFoundException("Proposal with ID " + proposalId + " not found"));

        proposal.approve();
        proposalGateway.update(proposal);
    }

}
