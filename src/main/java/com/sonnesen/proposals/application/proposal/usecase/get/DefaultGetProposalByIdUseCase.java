package com.sonnesen.proposals.application.proposal.usecase.get;

import java.util.Objects;

import com.sonnesen.proposals.application.proposal.exception.NotFoundException;
import com.sonnesen.proposals.application.proposal.gateway.ProposalGateway;

/**
 * Default implementation of the use case for retrieving a proposal by its ID.
 */
public class DefaultGetProposalByIdUseCase extends GetProposalByIdUseCase {
    private final ProposalGateway proposalGateway;

    public DefaultGetProposalByIdUseCase(final ProposalGateway proposalGateway) {
        this.proposalGateway = Objects.requireNonNull(proposalGateway, "proposalGateway must not be null");
    }

    /**
     * Executes the use case to retrieve a proposal by its ID.
     *
     * @param proposalId The ID of the proposal to retrieve.
     * @return The output data containing the proposal details.
     * @throws NotFoundException if the proposal with the given ID is not found.
     */
    @Override
    public GetProposalByIdUseCaseOutput execute(final Long proposalId) {
        return proposalGateway.getById(proposalId).map(GetProposalByIdUseCaseOutput::fromDomain)
                .orElseThrow(() -> new NotFoundException(
                        "Proposal with ID " + proposalId + " not found"));
    }

}
