package com.sonnesen.proposals.application.proposal.gateway;

import java.util.Optional;

import com.sonnesen.proposals.domain.pagination.Page;
import com.sonnesen.proposals.domain.pagination.Pagination;
import com.sonnesen.proposals.domain.proposal.Proposal;

public interface ProposalGateway {
    Proposal create(Proposal newProposal);

    Proposal update(Proposal proposal);

    Optional<Proposal> getById(Long id);

    Pagination<Proposal> list(Page page);
}
