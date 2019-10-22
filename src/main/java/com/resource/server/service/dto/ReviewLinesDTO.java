package com.resource.server.service.dto;
import java.time.LocalDate;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the {@link com.resource.server.domain.ReviewLines} entity.
 */
public class ReviewLinesDTO implements Serializable {

    private Long id;

    private Integer productRating;

    @Lob
    private String productReview;

    private Integer sellerRating;

    @Lob
    private String sellerReview;

    private Integer deliveryRating;

    @Lob
    private String deliveryReview;

    @Lob
    private byte[] photo;

    private String photoContentType;
    private String lastEditedBy;

    private LocalDate lastEditedWhen;


    private Long reviewId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getProductRating() {
        return productRating;
    }

    public void setProductRating(Integer productRating) {
        this.productRating = productRating;
    }

    public String getProductReview() {
        return productReview;
    }

    public void setProductReview(String productReview) {
        this.productReview = productReview;
    }

    public Integer getSellerRating() {
        return sellerRating;
    }

    public void setSellerRating(Integer sellerRating) {
        this.sellerRating = sellerRating;
    }

    public String getSellerReview() {
        return sellerReview;
    }

    public void setSellerReview(String sellerReview) {
        this.sellerReview = sellerReview;
    }

    public Integer getDeliveryRating() {
        return deliveryRating;
    }

    public void setDeliveryRating(Integer deliveryRating) {
        this.deliveryRating = deliveryRating;
    }

    public String getDeliveryReview() {
        return deliveryReview;
    }

    public void setDeliveryReview(String deliveryReview) {
        this.deliveryReview = deliveryReview;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public String getPhotoContentType() {
        return photoContentType;
    }

    public void setPhotoContentType(String photoContentType) {
        this.photoContentType = photoContentType;
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

    public Long getReviewId() {
        return reviewId;
    }

    public void setReviewId(Long reviewsId) {
        this.reviewId = reviewsId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ReviewLinesDTO reviewLinesDTO = (ReviewLinesDTO) o;
        if (reviewLinesDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), reviewLinesDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ReviewLinesDTO{" +
            "id=" + getId() +
            ", productRating=" + getProductRating() +
            ", productReview='" + getProductReview() + "'" +
            ", sellerRating=" + getSellerRating() +
            ", sellerReview='" + getSellerReview() + "'" +
            ", deliveryRating=" + getDeliveryRating() +
            ", deliveryReview='" + getDeliveryReview() + "'" +
            ", photo='" + getPhoto() + "'" +
            ", lastEditedBy='" + getLastEditedBy() + "'" +
            ", lastEditedWhen='" + getLastEditedWhen() + "'" +
            ", review=" + getReviewId() +
            "}";
    }
}
