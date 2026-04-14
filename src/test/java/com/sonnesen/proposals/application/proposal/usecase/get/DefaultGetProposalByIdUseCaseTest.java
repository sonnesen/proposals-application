package com.sonnesen.proposals.application.proposal.usecase.get;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.sonnesen.proposals.application.proposal.exception.NotFoundException;
import com.sonnesen.proposals.application.proposal.gateway.ProposalGateway;
import com.sonnesen.proposals.domain.proposal.Proposal;
import com.sonnesen.proposals.domain.proposal.ProposalStatus;

@ExtendWith(MockitoExtension.class)
@DisplayName("GetProposalByIdUseCase Tests")
class DefaultGetProposalByIdUseCaseTest {

    @Mock
    private ProposalGateway proposalGateway;

    private DefaultGetProposalByIdUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new DefaultGetProposalByIdUseCase(proposalGateway);
    }

    @Test
    @DisplayName("Should get proposal by ID successfully")
    void shouldGetProposalByIdSuccessfully() {
        // Given
        Long proposalId = 1L;
        Instant now = Instant.now();
        Proposal existingProposal = Proposal.with(
                proposalId, "John Doe", new BigDecimal("10000.00"), 12,
                ProposalStatus.IN_PROGRESS, now, now);

        when(proposalGateway.getById(proposalId)).thenReturn(Optional.of(existingProposal));

        // When
        GetProposalByIdUseCaseOutput output = useCase.execute(proposalId);

        // Then
        assertAll(
                () -> assertNotNull(output),
                () -> assertEquals(proposalId, output.proposalId()),
                () -> assertEquals("John Doe", output.customerName()),
                () -> assertEquals(new BigDecimal("10000.00"), output.amount()),
                () -> assertEquals(12, output.termInMonths()),
                () -> assertEquals("IN_PROGRESS", output.status()),
                () -> assertEquals(now, output.createdAt()),
                () -> assertEquals(now, output.updatedAt()));

        verify(proposalGateway, times(1)).getById(proposalId);
    }

    @Test
    @DisplayName("Should throw NotFoundException when proposal does not exist")
    void shouldThrowNotFoundExceptionWhenProposalDoesNotExist() {
        // Given
        Long proposalId = 999L;
        when(proposalGateway.getById(proposalId)).thenReturn(Optional.empty());

        // When & Then
        NotFoundException exception = assertThrows(
                NotFoundException.class,
                () -> useCase.execute(proposalId));
        assertEquals("Proposal with ID 999 not found", exception.getMessage());

        verify(proposalGateway, times(1)).getById(proposalId);
    }

    @Test
    @DisplayName("Should throw exception when gateway is null")
    void shouldThrowExceptionWhenGatewayIsNull() {
        // When & Then
        assertThrows(NullPointerException.class, () -> {
            new DefaultGetProposalByIdUseCase(null);
        });
    }
}