package com.sonnesen.proposals.application.proposal.usecase.list;

import com.sonnesen.proposals.application.proposal.usecase.UseCase;
import com.sonnesen.proposals.domain.pagination.Page;
import com.sonnesen.proposals.domain.pagination.Pagination;

/**
 * Use case for listing proposals with pagination.
 */
public abstract class ListProposalsUseCase
        extends UseCase<Page, Pagination<ListProposalsUseCaseOutput>> {

}
