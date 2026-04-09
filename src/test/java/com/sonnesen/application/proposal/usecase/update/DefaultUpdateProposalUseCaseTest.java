package com.sonnesen.application.proposal.usecase.update;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
import com.sonnesen.proposals.application.proposal.usecase.update.DefaultUpdateProposalUseCase;
import com.sonnesen.proposals.application.proposal.usecase.update.UpdateProposalUseCaseInput;
import com.sonnesen.proposals.application.proposal.usecase.update.UpdateProposalUseCaseOutput;
import com.sonnesen.proposals.domain.exception.IllegalProposalStateException;
import com.sonnesen.proposals.domain.exception.NotFoundException;
import com.sonnesen.proposals.domain.proposal.Proposal;
import com.sonnesen.proposals.domain.proposal.ProposalStatus;

@ExtendWith(MockitoExtension.class)
@DisplayName("UpdateProposalUseCase Tests")
class DefaultUpdateProposalUseCaseTest {

        @Mock
        private ProposalGateway proposalGateway;

        private DefaultUpdateProposalUseCase useCase;

        @BeforeEach
        void setUp() {
                useCase = new DefaultUpdateProposalUseCase(proposalGateway);
        }

        @Test
        @DisplayName("Should update proposal successfully")
        void shouldUpdateProposalSuccessfully() {
                // Given
                Long proposalId = 1L;
                String newCustomerName = "Jane Smith";
                BigDecimal newAmount = new BigDecimal("15000.00");
                Integer newTermInMonths = 24;

                UpdateProposalUseCaseInput input = UpdateProposalUseCaseInput.with(
                                proposalId, newCustomerName, newAmount, newTermInMonths);

                Instant now = Instant.now();
                Proposal existingProposal = Proposal.with(
                                proposalId, "John Doe", new BigDecimal("10000.00"), 12,
                                ProposalStatus.IN_PROGRESS, now.minusSeconds(3600), now.minusSeconds(3600));

                Proposal updatedProposal = Proposal.with(
                                proposalId, newCustomerName, newAmount, newTermInMonths,
                                ProposalStatus.IN_PROGRESS, now.minusSeconds(3600), now);

                when(proposalGateway.getById(proposalId)).thenReturn(Optional.of(existingProposal));
                when(proposalGateway.update(any(Proposal.class))).thenReturn(updatedProposal);

                // When
                UpdateProposalUseCaseOutput output = useCase.execute(input);

                // Then
                assertAll(
                                () -> assertNotNull(output),
                                () -> assertEquals(proposalId, output.proposalId()),
                                () -> assertEquals(newCustomerName, output.customerName()),
                                () -> assertEquals(newAmount, output.amount()),
                                () -> assertEquals(newTermInMonths, output.termInMonths()),
                                () -> assertEquals("IN_PROGRESS", output.status()),
                                () -> assertNotNull(output.createdAt()),
                                () -> assertNotNull(output.updatedAt()));

                verify(proposalGateway, times(1)).getById(proposalId);
                verify(proposalGateway, times(1)).update(any(Proposal.class));
        }

        @Test
        @DisplayName("Should throw NotFoundException when proposal does not exist")
        void shouldThrowNotFoundExceptionWhenProposalDoesNotExist() {
                // Given
                Long proposalId = 999L;
                UpdateProposalUseCaseInput input = UpdateProposalUseCaseInput.with(
                                proposalId, "Jane Smith", new BigDecimal("15000.00"), 24);

                when(proposalGateway.getById(proposalId)).thenReturn(Optional.empty());

                // When & Then
                NotFoundException exception = assertThrows(
                                NotFoundException.class,
                                () -> useCase.execute(input));
                assertEquals("Proposal with ID 999 not found", exception.getMessage());

                verify(proposalGateway, times(1)).getById(proposalId);
                verify(proposalGateway, never()).update(any(Proposal.class));
        }

        @Test
        @DisplayName("Should throw IllegalProposalStateException when proposal is not IN_PROGRESS")
        void shouldThrowIllegalProposalStateExceptionWhenProposalIsNotInProgress() {
                // Given
                Long proposalId = 1L;
                UpdateProposalUseCaseInput input = UpdateProposalUseCaseInput.with(
                                proposalId, "Jane Smith", new BigDecimal("15000.00"), 24);

                Instant now = Instant.now();
                Proposal approvedProposal = Proposal.with(
                                proposalId, "John Doe", new BigDecimal("10000.00"), 12,
                                ProposalStatus.APPROVED, now.minusSeconds(3600), now.minusSeconds(3600));

                when(proposalGateway.getById(proposalId)).thenReturn(Optional.of(approvedProposal));

                // When & Then
                IllegalProposalStateException exception = assertThrows(
                                IllegalProposalStateException.class,
                                () -> useCase.execute(input));
                assertEquals("Only proposals in progress can be updated.", exception.getMessage());

                verify(proposalGateway, times(1)).getById(proposalId);
                verify(proposalGateway, never()).update(any(Proposal.class));
        }

        @Test
        @DisplayName("Should throw exception when gateway is null")
        void shouldThrowExceptionWhenGatewayIsNull() {
                // When & Then
                assertThrows(NullPointerException.class, () -> {
                        new DefaultUpdateProposalUseCase(null);
                });
        }

        @Test
        @DisplayName("Should validate input data")
        void shouldValidateInputData() {
                // Given
                Long proposalId = 1L;
                UpdateProposalUseCaseInput input = UpdateProposalUseCaseInput.with(
                                proposalId, "", new BigDecimal("15000.00"), 24);

                Instant now = Instant.now();
                Proposal existingProposal = Proposal.with(
                                proposalId, "John Doe", new BigDecimal("10000.00"), 12,
                                ProposalStatus.IN_PROGRESS, now.minusSeconds(3600), now.minusSeconds(3600));

                when(proposalGateway.getById(proposalId)).thenReturn(Optional.of(existingProposal));

                // When & Then
                IllegalArgumentException exception = assertThrows(
                                IllegalArgumentException.class,
                                () -> useCase.execute(input));
                assertEquals("Customer name must not be null or blank.", exception.getMessage());

                verify(proposalGateway, times(1)).getById(proposalId);
                verify(proposalGateway, never()).update(any(Proposal.class));
        }
}