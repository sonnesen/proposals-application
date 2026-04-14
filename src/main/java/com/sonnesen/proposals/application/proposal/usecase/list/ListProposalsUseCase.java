package com.sonnesen.proposals.application.proposal.usecase.list;

import com.sonnesen.proposals.application.proposal.shared.pagination.Page;
import com.sonnesen.proposals.application.proposal.shared.pagination.Pagination;
import com.sonnesen.proposals.application.proposal.usecase.UseCase;

/**
 * Use case for listing proposals with pagination.
 */
public abstract class ListProposalsUseCase
                extends UseCase<Page, Pagination<ListProposalsUseCaseOutput>> {

}
