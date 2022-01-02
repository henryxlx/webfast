package com.jetwinner.webfast.kernel.model;

/**
 * @author xulixin
 */
public class AppModelRole {

    private Integer id;
    private String label;
    private String roleName;
    private String permissionData;
    private Integer createdUserId;
    private Long createdTime;
    private Long updatedTime;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getLabel() {
    return label;
  }

  public void setLabel(String label) {
    this.label = label;
  }

  public String getRoleName() {
    return roleName;
  }

  public void setRoleName(String roleName) {
    this.roleName = roleName;
  }

  public String getPermissionData() {
    return permissionData;
  }

  public void setPermissionData(String permissionData) {
    this.permissionData = permissionData;
  }

  public Integer getCreatedUserId() {
    return createdUserId;
  }

  public void setCreatedUserId(Integer createdUserId) {
    this.createdUserId = createdUserId;
  }

  public Long getCreatedTime() {
    return createdTime;
  }

  public void setCreatedTime(Long createdTime) {
    this.createdTime = createdTime;
  }

  public Long getUpdatedTime() {
    return updatedTime;
  }

  public void setUpdatedTime(Long updatedTime) {
    this.updatedTime = updatedTime;
  }
}
