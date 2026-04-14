package com.sonnesen.proposals.application.proposal.usecase.list;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.sonnesen.proposals.application.proposal.gateway.ProposalGateway;
import com.sonnesen.proposals.application.proposal.shared.pagination.Page;
import com.sonnesen.proposals.application.proposal.shared.pagination.Pagination;
import com.sonnesen.proposals.domain.proposal.Proposal;
import com.sonnesen.proposals.domain.proposal.ProposalStatus;

@ExtendWith(MockitoExtension.class)
class DefaultListProposalsUseCaseTest {

    @InjectMocks
    DefaultListProposalsUseCase useCase;

    @Mock
    ProposalGateway proposalGateway;

    @Test
    void shouldListProposalsSuccessfully() {
        // Given
        final var page = new Page(0, 10);
        final var proposalId = 1L;
        final var customerName = "John Doe";
        final var amount = new BigDecimal("10000.00");
        final var termInMonths = 12;
        final var status = ProposalStatus.IN_PROGRESS;
        final var createdAt = Instant.now();
        final var updatedAt = Instant.now();
        final var proposal = Proposal.with(proposalId, customerName, amount, termInMonths, status, createdAt,
                updatedAt);
        final var paginatedProposals = new Pagination<>(page.currentPage(), page.perPage(), 1, 1, List.of(proposal));

        when(proposalGateway.list(page)).thenReturn(paginatedProposals);

        // When
        final var result = useCase.execute(page);

        // Then
        assertNotNull(result);
        assertEquals(page.currentPage(), result.currentPage());
        assertEquals(page.perPage(), result.perPage());
        assertEquals(1, result.totalItems());
        assertEquals(1, result.totalPages());
        assertEquals(1, result.items().size());

        verify(proposalGateway, times(1)).list(page);
    }

    @Test
    void shouldReturnEmptyListWhenNoProposals() {
        // Given
        final var page = new Page(0, 10);
        final var paginatedProposals = new Pagination<>(page.currentPage(), page.perPage(), 0, 0, List.<Proposal>of());

        when(proposalGateway.list(page)).thenReturn(paginatedProposals);

        // When
        final var result = useCase.execute(page);

        // Then
        assertNotNull(result);
        assertEquals(page.currentPage(), result.currentPage());
        assertEquals(page.perPage(), result.perPage());
        assertEquals(0, result.totalItems());
        assertEquals(0, result.totalPages());
        assertEquals(0, result.items().size());

        verify(proposalGateway, times(1)).list(page);
    }

}
