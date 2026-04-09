package com.sonnesen.application.proposal.usecase.approve;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
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

import com.sonnesen.proposals.application.proposal.gateway.ProposalGateway;
import com.sonnesen.proposals.application.proposal.usecase.approve.DefaultApproveProposalUseCase;
import com.sonnesen.proposals.domain.exception.IllegalProposalStateException;
import com.sonnesen.proposals.domain.exception.NotFoundException;
import com.sonnesen.proposals.domain.proposal.Proposal;
import com.sonnesen.proposals.domain.proposal.ProposalStatus;

@ExtendWith(MockitoExtension.class)
@DisplayName("ApproveProposalUseCase Tests")
class DefaultApproveProposalUseCaseTest {

    @Mock
    private ProposalGateway proposalGateway;

    private DefaultApproveProposalUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new DefaultApproveProposalUseCase(proposalGateway);
    }

    @Test
    @DisplayName("Should approve proposal successfully")
    void shouldApproveProposalSuccessfully() {
        // Given
        Long proposalId = 1L;
        Instant now = Instant.now();
        Proposal existingProposal = Proposal.with(
                proposalId, "John Doe", new BigDecimal("10000.00"), 12,
                ProposalStatus.IN_PROGRESS, now.minusSeconds(3600), now.minusSeconds(3600));

        when(proposalGateway.getById(proposalId)).thenReturn(Optional.of(existingProposal));
        when(proposalGateway.update(any(Proposal.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // When
        assertDoesNotThrow(() -> useCase.execute(proposalId));

        // Then
        verify(proposalGateway, times(1)).getById(proposalId);
        verify(proposalGateway, times(1)).update(any(Proposal.class));
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
        verify(proposalGateway, never()).update(any(Proposal.class));
    }

    @Test
    @DisplayName("Should throw IllegalProposalStateException when proposal is not IN_PROGRESS")
    void shouldThrowIllegalProposalStateExceptionWhenProposalIsNotInProgress() {
        // Given
        Long proposalId = 1L;
        Instant now = Instant.now();
        Proposal approvedProposal = Proposal.with(
                proposalId, "John Doe", new BigDecimal("10000.00"), 12,
                ProposalStatus.APPROVED, now.minusSeconds(3600), now.minusSeconds(3600));

        when(proposalGateway.getById(proposalId)).thenReturn(Optional.of(approvedProposal));

        // When & Then
        IllegalProposalStateException exception = assertThrows(
                IllegalProposalStateException.class,
                () -> useCase.execute(proposalId));
        assertEquals("Only proposals in progress can be approved.", exception.getMessage());

        verify(proposalGateway, times(1)).getById(proposalId);
        verify(proposalGateway, never()).update(any(Proposal.class));
    }

    @Test
    @DisplayName("Should throw exception when gateway is null")
    void shouldThrowExceptionWhenGatewayIsNull() {
        // When & Then
        assertThrows(NullPointerException.class, () -> {
            new DefaultApproveProposalUseCase(null);
        });
    }
}