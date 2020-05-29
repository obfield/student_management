package com.koko.dao;

import com.koko.entity.Subject;
import com.koko.entity.SubjectExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface SubjectMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table subject
     *
     * @mbg.generated Fri May 29 10:17:26 CST 2020
     */
    long countByExample(SubjectExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table subject
     *
     * @mbg.generated Fri May 29 10:17:26 CST 2020
     */
    int deleteByExample(SubjectExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table subject
     *
     * @mbg.generated Fri May 29 10:17:26 CST 2020
     */
    int deleteByPrimaryKey(Integer subjectId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table subject
     *
     * @mbg.generated Fri May 29 10:17:26 CST 2020
     */
    int insert(Subject record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table subject
     *
     * @mbg.generated Fri May 29 10:17:26 CST 2020
     */
    int insertSelective(Subject record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table subject
     *
     * @mbg.generated Fri May 29 10:17:26 CST 2020
     */
    List<Subject> selectByExampleWithRowbounds(SubjectExample example, RowBounds rowBounds);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table subject
     *
     * @mbg.generated Fri May 29 10:17:26 CST 2020
     */
    List<Subject> selectByExample(SubjectExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table subject
     *
     * @mbg.generated Fri May 29 10:17:26 CST 2020
     */
    Subject selectByPrimaryKey(Integer subjectId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table subject
     *
     * @mbg.generated Fri May 29 10:17:26 CST 2020
     */
    int updateByExampleSelective(@Param("record") Subject record, @Param("example") SubjectExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table subject
     *
     * @mbg.generated Fri May 29 10:17:26 CST 2020
     */
    int updateByExample(@Param("record") Subject record, @Param("example") SubjectExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table subject
     *
     * @mbg.generated Fri May 29 10:17:26 CST 2020
     */
    int updateByPrimaryKeySelective(Subject record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table subject
     *
     * @mbg.generated Fri May 29 10:17:26 CST 2020
     */
    int updateByPrimaryKey(Subject record);
}