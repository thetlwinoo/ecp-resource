package com.resource.server.service.dto;
import java.time.LocalDate;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the {@link com.resource.server.domain.Reviews} entity.
 */
public class ReviewsDTO implements Serializable {

    private Long id;

    private String reviewerName;

    private String emailAddress;

    private LocalDate reviewDate;

    private Integer overAllSellerRating;

    @Lob
    private String overAllSellerReview;

    private Integer overAllDeliveryRating;

    @Lob
    private String overAllDeliveryReview;

    private Boolean reviewAsAnonymous;

    private Boolean completedReview;

    private String lastEditedBy;

    private LocalDate lastEditedWhen;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReviewerName() {
        return reviewerName;
    }

    public void setReviewerName(String reviewerName) {
        this.reviewerName = reviewerName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public LocalDate getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(LocalDate reviewDate) {
        this.reviewDate = reviewDate;
    }

    public Integer getOverAllSellerRating() {
        return overAllSellerRating;
    }

    public void setOverAllSellerRating(Integer overAllSellerRating) {
        this.overAllSellerRating = overAllSellerRating;
    }

    public String getOverAllSellerReview() {
        return overAllSellerReview;
    }

    public void setOverAllSellerReview(String overAllSellerReview) {
        this.overAllSellerReview = overAllSellerReview;
    }

    public Integer getOverAllDeliveryRating() {
        return overAllDeliveryRating;
    }

    public void setOverAllDeliveryRating(Integer overAllDeliveryRating) {
        this.overAllDeliveryRating = overAllDeliveryRating;
    }

    public String getOverAllDeliveryReview() {
        return overAllDeliveryReview;
    }

    public void setOverAllDeliveryReview(String overAllDeliveryReview) {
        this.overAllDeliveryReview = overAllDeliveryReview;
    }

    public Boolean isReviewAsAnonymous() {
        return reviewAsAnonymous;
    }

    public void setReviewAsAnonymous(Boolean reviewAsAnonymous) {
        this.reviewAsAnonymous = reviewAsAnonymous;
    }

    public Boolean isCompletedReview() {
        return completedReview;
    }

    public void setCompletedReview(Boolean completedReview) {
        this.completedReview = completedReview;
    }

    public String getLastEditedBy() {
        return lastEditedBy;
    }

    public void setLastEditedBy(String lastEditedBy) {
        this.lastEditedBy = lastEditedBy;
    }

    public LocalDate getLastEditedWhen() {
        return lastEditedWhen;
    }

    public void setLastEditedWhen(LocalDate lastEditedWhen) {
        this.lastEditedWhen = lastEditedWhen;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ReviewsDTO reviewsDTO = (ReviewsDTO) o;
        if (reviewsDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), reviewsDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ReviewsDTO{" +
            "id=" + getId() +
            ", reviewerName='" + getReviewerName() + "'" +
            ", emailAddress='" + getEmailAddress() + "'" +
            ", reviewDate='" + getReviewDate() + "'" +
            ", overAllSellerRating=" + getOverAllSellerRating() +
            ", overAllSellerReview='" + getOverAllSellerReview() + "'" +
            ", overAllDeliveryRating=" + getOverAllDeliveryRating() +
            ", overAllDeliveryReview='" + getOverAllDeliveryReview() + "'" +
            ", reviewAsAnonymous='" + isReviewAsAnonymous() + "'" +
            ", completedReview='" + isCompletedReview() + "'" +
            ", lastEditedBy='" + getLastEditedBy() + "'" +
            ", lastEditedWhen='" + getLastEditedWhen() + "'" +
            "}";
    }
}
