
package com.trafficticketbuddy.lawyer.model.allbid;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Response {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("client_id")
    @Expose
    private String clientId;
    @SerializedName("lawyer_id")
    @Expose
    private String lawyerId;
    @SerializedName("bid_amount")
    @Expose
    private String bidAmount;
    @SerializedName("bid_text")
    @Expose
    private String bidText;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("is_accepted")
    @Expose
    private String isAccepted;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("lawyer_first_name")
    @Expose
    private String lawyerFirstName;
    @SerializedName("lawyer_last_name")
    @Expose
    private String lawyerLastName;
    @SerializedName("lawyer_email")
    @Expose
    private String lawyerEmail;
    @SerializedName("lawyer_phone")
    @Expose
    private String lawyerPhone;
    @SerializedName("lawyer_profile_image")
    @Expose
    private String lawyerProfileImage;
    @SerializedName("case_number")
    @Expose
    private String caseNumber;
    @SerializedName("case_details")
    @Expose
    private String caseDetails;
    @SerializedName("case_front_img")
    @Expose
    private String caseFrontImg;
    @SerializedName("case_rear_img")
    @Expose
    private String caseRearImg;
    @SerializedName("driving_license")
    @Expose
    private String drivingLicense;
    @SerializedName("case_status")
    @Expose
    private String caseStatus;
    @SerializedName("case_created_at")
    @Expose
    private String caseCreatedAt;
    @SerializedName("accepted_at")
    @Expose
    private Object acceptedAt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getLawyerId() {
        return lawyerId;
    }

    public void setLawyerId(String lawyerId) {
        this.lawyerId = lawyerId;
    }

    public String getBidAmount() {
        return bidAmount;
    }

    public void setBidAmount(String bidAmount) {
        this.bidAmount = bidAmount;
    }

    public String getBidText() {
        return bidText;
    }

    public void setBidText(String bidText) {
        this.bidText = bidText;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getIsAccepted() {
        return isAccepted;
    }

    public void setIsAccepted(String isAccepted) {
        this.isAccepted = isAccepted;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLawyerFirstName() {
        return lawyerFirstName;
    }

    public void setLawyerFirstName(String lawyerFirstName) {
        this.lawyerFirstName = lawyerFirstName;
    }

    public String getLawyerLastName() {
        return lawyerLastName;
    }

    public void setLawyerLastName(String lawyerLastName) {
        this.lawyerLastName = lawyerLastName;
    }

    public String getLawyerEmail() {
        return lawyerEmail;
    }

    public void setLawyerEmail(String lawyerEmail) {
        this.lawyerEmail = lawyerEmail;
    }

    public String getLawyerPhone() {
        return lawyerPhone;
    }

    public void setLawyerPhone(String lawyerPhone) {
        this.lawyerPhone = lawyerPhone;
    }

    public String getLawyerProfileImage() {
        return lawyerProfileImage;
    }

    public void setLawyerProfileImage(String lawyerProfileImage) {
        this.lawyerProfileImage = lawyerProfileImage;
    }

    public String getCaseNumber() {
        return caseNumber;
    }

    public void setCaseNumber(String caseNumber) {
        this.caseNumber = caseNumber;
    }

    public String getCaseDetails() {
        return caseDetails;
    }

    public void setCaseDetails(String caseDetails) {
        this.caseDetails = caseDetails;
    }

    public String getCaseFrontImg() {
        return caseFrontImg;
    }

    public void setCaseFrontImg(String caseFrontImg) {
        this.caseFrontImg = caseFrontImg;
    }

    public String getCaseRearImg() {
        return caseRearImg;
    }

    public void setCaseRearImg(String caseRearImg) {
        this.caseRearImg = caseRearImg;
    }

    public String getDrivingLicense() {
        return drivingLicense;
    }

    public void setDrivingLicense(String drivingLicense) {
        this.drivingLicense = drivingLicense;
    }

    public String getCaseStatus() {
        return caseStatus;
    }

    public void setCaseStatus(String caseStatus) {
        this.caseStatus = caseStatus;
    }

    public String getCaseCreatedAt() {
        return caseCreatedAt;
    }

    public void setCaseCreatedAt(String caseCreatedAt) {
        this.caseCreatedAt = caseCreatedAt;
    }

    public Object getAcceptedAt() {
        return acceptedAt;
    }

    public void setAcceptedAt(Object acceptedAt) {
        this.acceptedAt = acceptedAt;
    }

}