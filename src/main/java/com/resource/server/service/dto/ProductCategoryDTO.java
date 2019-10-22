package com.resource.server.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the {@link com.resource.server.domain.ProductCategory} entity.
 */
public class ProductCategoryDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    private String label;

    @Lob
    private byte[] photo;

    private String photoContentType;

    private Long parentId;

    private String parentName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
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

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long productCategoryId) {
        this.parentId = productCategoryId;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String productCategoryName) {
        this.parentName = productCategoryName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ProductCategoryDTO productCategoryDTO = (ProductCategoryDTO) o;
        if (productCategoryDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), productCategoryDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProductCategoryDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", label='" + getLabel() + "'" +
            ", photo='" + getPhoto() + "'" +
            ", parent=" + getParentId() +
            ", parent='" + getParentName() + "'" +
            "}";
    }
}
