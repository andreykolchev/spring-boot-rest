package com.example.model.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class ClaimRequestRs {

    private BigDecimal id;
    private BigDecimal claimId;
    private Date requestDate;
    private String explanation;
    private BigDecimal amount;
    private String currency;
    private BigDecimal claimState;
    private String solveWay;
    private String requestType;
    private Date dueDate;
    private BigDecimal claimType;
    private BigDecimal claimantId;
    private BigDecimal claimStateAux;
    private String accumulatedLR;
    private BigDecimal providerId;
    private BigDecimal paymentType;
    private String subrogation;
    private String recovery;
    private BigDecimal reopenRequestId;
    private String reopenReason;
    private Date convertDate;
    private BigDecimal providerNetworkId;
    private BigDecimal providerContractId;
    private String invoiceId;
    private Date invoiceDate;
    private BigDecimal invoiceAmount;
    private BigDecimal primaryDiagnosisId;
    private BigDecimal primaryDiagnosisGroupId;
    private String servicePlace;
    private Date admissionDate;
    private Date admissionTime;
    private Date dischargeDate;
    private Date dischargeTime;
    private Date expectedAdmissionDate;
    private BigDecimal parClaimId;
    private BigDecimal parRequestId;
    private BigDecimal batchId;
    private String invoiceCurrency;
    private BigDecimal claimReqSeq;
    private Date operationDate;

}
