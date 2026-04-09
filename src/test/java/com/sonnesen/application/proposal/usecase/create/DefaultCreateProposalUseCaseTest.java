package com.sonnesen.application.proposal.usecase.create;

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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.sonnesen.proposals.application.proposal.gateway.ProposalGateway;
import com.sonnesen.proposals.application.proposal.usecase.create.CreateProposalUseCaseInput;
import com.sonnesen.proposals.application.proposal.usecase.create.CreateProposalUseCaseOutput;
import com.sonnesen.proposals.application.proposal.usecase.create.DefaultCreateProposalUseCase;
import com.sonnesen.proposals.domain.proposal.Proposal;
import com.sonnesen.proposals.domain.proposal.ProposalStatus;

@ExtendWith(MockitoExtension.class)
@DisplayName("CreateProposalUseCase Tests")
class DefaultCreateProposalUseCaseTest {

    @Mock
    private ProposalGateway proposalGateway;

    private DefaultCreateProposalUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new DefaultCreateProposalUseCase(proposalGateway);
    }

    @Test
    @DisplayName("Should create proposal successfully")
    void shouldCreateProposalSuccessfully() {
        // Given
        String customerName = "John Doe";
        BigDecimal amount = new BigDecimal("10000.00");
        Integer termInMonths = 12;

        CreateProposalUseCaseInput input = CreateProposalUseCaseInput.with(
                customerName, amount, termInMonths);

        Instant now = Instant.now();
        Proposal createdProposal = Proposal.with(
                1L, customerName, amount, termInMonths,
                ProposalStatus.IN_PROGRESS, now, now);

        when(proposalGateway.create(any(Proposal.class))).thenReturn(createdProposal);

        // When
        CreateProposalUseCaseOutput output = useCase.execute(input);

        // Then
        assertAll(
                () -> assertNotNull(output),
                () -> assertEquals(1L, output.proposalId()),
                () -> assertEquals(customerName, output.customerName()),
                () -> assertEquals(amount, output.amount()),
                () -> assertEquals(termInMonths, output.termInMonths()),
                () -> assertEquals("IN_PROGRESS", output.status()),
                () -> assertNotNull(output.createdAt()),
                () -> assertNotNull(output.updatedAt()));

        verify(proposalGateway, times(1)).create(any(Proposal.class));
    }

    @Test
    @DisplayName("Should throw exception when gateway is null")
    void shouldThrowExceptionWhenGatewayIsNull() {
        // When & Then
        assertThrows(NullPointerException.class, () -> {
            new DefaultCreateProposalUseCase(null);
        });
    }

    @Test
    @DisplayName("Should throw exception when input is invalid")
    void shouldThrowExceptionWhenInputIsInvalid() {
        // Given
        CreateProposalUseCaseInput input = CreateProposalUseCaseInput.with(
                "", new BigDecimal("10000.00"), 12);

        // When & Then
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> useCase.execute(input));
        assertEquals("Customer name must not be null or blank.", exception.getMessage());

        verify(proposalGateway, never()).create(any(Proposal.class));
    }
}