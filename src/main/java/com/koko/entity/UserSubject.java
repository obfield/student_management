package com.koko.entity;

public class UserSubject {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_subject.username
     *
     * @mbg.generated Fri May 29 10:17:26 CST 2020
     */
    private Integer username;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_subject.subject_id
     *
     * @mbg.generated Fri May 29 10:17:26 CST 2020
     */
    private Integer subjectId;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_subject.username
     *
     * @return the value of user_subject.username
     *
     * @mbg.generated Fri May 29 10:17:26 CST 2020
     */
    public Integer getUsername() {
        return username;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_subject.username
     *
     * @param username the value for user_subject.username
     *
     * @mbg.generated Fri May 29 10:17:26 CST 2020
     */
    public void setUsername(Integer username) {
        this.username = username;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_subject.subject_id
     *
     * @return the value of user_subject.subject_id
     *
     * @mbg.generated Fri May 29 10:17:26 CST 2020
     */
    public Integer getSubjectId() {
        return subjectId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_subject.subject_id
     *
     * @param subjectId the value for user_subject.subject_id
     *
     * @mbg.generated Fri May 29 10:17:26 CST 2020
     */
    public void setSubjectId(Integer subjectId) {
        this.subjectId = subjectId;
    }
}