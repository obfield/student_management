package com.koko.entity;

public class Subject {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column subject.subject_id
     *
     * @mbg.generated Fri May 29 10:17:26 CST 2020
     */
    private Integer subjectId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column subject.subject_name
     *
     * @mbg.generated Fri May 29 10:17:26 CST 2020
     */
    private String subjectName;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column subject.subject_id
     *
     * @return the value of subject.subject_id
     *
     * @mbg.generated Fri May 29 10:17:26 CST 2020
     */
    public Integer getSubjectId() {
        return subjectId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column subject.subject_id
     *
     * @param subjectId the value for subject.subject_id
     *
     * @mbg.generated Fri May 29 10:17:26 CST 2020
     */
    public void setSubjectId(Integer subjectId) {
        this.subjectId = subjectId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column subject.subject_name
     *
     * @return the value of subject.subject_name
     *
     * @mbg.generated Fri May 29 10:17:26 CST 2020
     */
    public String getSubjectName() {
        return subjectName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column subject.subject_name
     *
     * @param subjectName the value for subject.subject_name
     *
     * @mbg.generated Fri May 29 10:17:26 CST 2020
     */
    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName == null ? null : subjectName.trim();
    }
}