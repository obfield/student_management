package com.koko.entity;

public class Permission {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column permission.permission_id
     *
     * @mbg.generated Fri May 29 10:17:26 CST 2020
     */
    private Integer permissionId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column permission.permission_code
     *
     * @mbg.generated Fri May 29 10:17:26 CST 2020
     */
    private String permissionCode;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column permission.permission_name
     *
     * @mbg.generated Fri May 29 10:17:26 CST 2020
     */
    private String permissionName;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column permission.permission_id
     *
     * @return the value of permission.permission_id
     *
     * @mbg.generated Fri May 29 10:17:26 CST 2020
     */
    public Integer getPermissionId() {
        return permissionId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column permission.permission_id
     *
     * @param permissionId the value for permission.permission_id
     *
     * @mbg.generated Fri May 29 10:17:26 CST 2020
     */
    public void setPermissionId(Integer permissionId) {
        this.permissionId = permissionId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column permission.permission_code
     *
     * @return the value of permission.permission_code
     *
     * @mbg.generated Fri May 29 10:17:26 CST 2020
     */
    public String getPermissionCode() {
        return permissionCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column permission.permission_code
     *
     * @param permissionCode the value for permission.permission_code
     *
     * @mbg.generated Fri May 29 10:17:26 CST 2020
     */
    public void setPermissionCode(String permissionCode) {
        this.permissionCode = permissionCode == null ? null : permissionCode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column permission.permission_name
     *
     * @return the value of permission.permission_name
     *
     * @mbg.generated Fri May 29 10:17:26 CST 2020
     */
    public String getPermissionName() {
        return permissionName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column permission.permission_name
     *
     * @param permissionName the value for permission.permission_name
     *
     * @mbg.generated Fri May 29 10:17:26 CST 2020
     */
    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName == null ? null : permissionName.trim();
    }
}