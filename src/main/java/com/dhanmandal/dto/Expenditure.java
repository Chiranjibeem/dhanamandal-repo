package com.dhanmandal.dto;

public class Expenditure {

    private String paymentId;
    private String descriptions;
    private Double eamount;
    private String etransactionType;
    private String paymentBy;

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(String descriptions) {
        this.descriptions = descriptions;
    }

    public Double getEamount() {
        return eamount;
    }

    public void setEamount(Double eamount) {
        this.eamount = eamount;
    }

    public String getEtransactionType() {
        return etransactionType;
    }

    public void setEtransactionType(String etransactionType) {
        this.etransactionType = etransactionType;
    }

    public String getPaymentBy() {
        return paymentBy;
    }

    public void setPaymentBy(String paymentBy) {
        this.paymentBy = paymentBy;
    }
}
