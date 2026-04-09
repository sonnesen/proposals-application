package com.sonnesen.proposals.application.proposal.usecase.list;

import java.util.Objects;

import com.sonnesen.proposals.application.proposal.gateway.ProposalGateway;
import com.sonnesen.proposals.domain.pagination.Page;
import com.sonnesen.proposals.domain.pagination.Pagination;

/**
 * Default implementation of the ListProposalsUseCase.
 */
public class DefaultListProposalsUseCase extends ListProposalsUseCase {
    private final ProposalGateway proposalGateway;

    public DefaultListProposalsUseCase(ProposalGateway proposalGateway) {
        Objects.requireNonNull(proposalGateway);
        this.proposalGateway = proposalGateway;
    }

    /**
     * Executes the use case to list proposals with pagination.
     *
     * @param page The pagination information.
     * @return The paginated list of proposals.
     */
    @Override
    public Pagination<ListProposalsUseCaseOutput> execute(Page page) {
        return proposalGateway.list(page).mapItems(ListProposalsUseCaseOutput::fromDomain);
    }

}
