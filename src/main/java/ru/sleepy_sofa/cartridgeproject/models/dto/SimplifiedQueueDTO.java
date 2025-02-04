package ru.sleepy_sofa.cartridgeproject.models.dto;

public class SimplifiedQueueDTO {
    private Long id;
    private String officeName;
    private Long officeId;
    private String cartridgeName;
    private Long cartridgeId;

    public SimplifiedQueueDTO(Long id, String officeName, Long officeId, String cartridgeName, Long cartridgeId) {
        this.id = id;
        this.officeName = officeName;
        this.officeId = officeId;
        this.cartridgeName = cartridgeName;
        this.cartridgeId = cartridgeId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOfficeName() {
        return officeName;
    }

    public void setOfficeName(String officeName) {
        this.officeName = officeName;
    }

    public Long getOfficeId() {
        return officeId;
    }

    public void setOfficeId(Long officeId) {
        this.officeId = officeId;
    }

    public String getCartridgeName() {
        return cartridgeName;
    }

    public void setCartridgeName(String cartridgeName) {
        this.cartridgeName = cartridgeName;
    }

    public Long getCartridgeId() {
        return cartridgeId;
    }

    public void setCartridgeId(Long cartridgeId) {
        this.cartridgeId = cartridgeId;
    }
}