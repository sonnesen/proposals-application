package com.sonnesen.proposals.application.proposal.usecase.list;

import java.util.Objects;

import com.sonnesen.proposals.application.proposal.gateway.ProposalGateway;
import com.sonnesen.proposals.application.proposal.shared.pagination.Page;
import com.sonnesen.proposals.application.proposal.shared.pagination.Pagination;

/**
 * Default implementation of the ListProposalsUseCase.
 */
public class DefaultListProposalsUseCase extends ListProposalsUseCase {
    private final ProposalGateway proposalGateway;

    public DefaultListProposalsUseCase(final ProposalGateway proposalGateway) {
        this.proposalGateway = Objects.requireNonNull(proposalGateway, "proposalGateway must not be null");
    }

    /**
     * Executes the use case to list proposals with pagination.
     *
     * @param page The pagination information.
     * @return The paginated list of proposals.
     */
    @Override
    public Pagination<ListProposalsUseCaseOutput> execute(final Page page) {
        return proposalGateway.list(page).mapItems(ListProposalsUseCaseOutput::fromDomain);
    }

}
