package com.Parfetch.ParFetch.model;

import jakarta.persistence.*;

@Entity
public class Parcel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tracking_number")
    private Integer trackingNumber;

    @ManyToOne
    @JoinColumn(name = "receiver_id") // Adjust this if needed
    private Receiver receiver;

    private String deliveryStatus;
    private String receiveDate;
    private String paymentStatus;
    private String receiverName;
    private String receiverPhone;
    private String senderPhone;
    private Integer weight;

    // --- Getters and Setters ---

    public Integer getTrackingNumber() {
        return trackingNumber;
    }

    public void setTrackingNumber(Integer trackingNumber) {
        this.trackingNumber = trackingNumber;
    }

    public Receiver getReceiver() {
        return receiver;
    }

    public void setReceiver(Receiver receiver) {
        this.receiver = receiver;
    }

    public String getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(String deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    public String getReceiveDate() {
        return receiveDate;
    }

    public void setReceiveDate(String receiveDate) {
        this.receiveDate = receiveDate;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getReceiverPhone() {
        return receiverPhone;
    }

    public void setReceiverPhone(String receiverPhone) {
        this.receiverPhone = receiverPhone;
    }

    public String getSenderPhone() {
        return senderPhone;
    }

    public void setSenderPhone(String senderPhone) {
        this.senderPhone = senderPhone;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }
}