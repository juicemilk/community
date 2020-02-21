package com.juicemilk.community.mapper;

import com.juicemilk.community.model.QuestionCollect;
import com.juicemilk.community.model.QuestionCollectExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface QuestionCollectMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table QUESTION_COLLECT
     *
     * @mbg.generated Thu Feb 20 22:22:45 CST 2020
     */
    long countByExample(QuestionCollectExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table QUESTION_COLLECT
     *
     * @mbg.generated Thu Feb 20 22:22:45 CST 2020
     */
    int deleteByExample(QuestionCollectExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table QUESTION_COLLECT
     *
     * @mbg.generated Thu Feb 20 22:22:45 CST 2020
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table QUESTION_COLLECT
     *
     * @mbg.generated Thu Feb 20 22:22:45 CST 2020
     */
    int insert(QuestionCollect record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table QUESTION_COLLECT
     *
     * @mbg.generated Thu Feb 20 22:22:45 CST 2020
     */
    int insertSelective(QuestionCollect record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table QUESTION_COLLECT
     *
     * @mbg.generated Thu Feb 20 22:22:45 CST 2020
     */
    List<QuestionCollect> selectByExampleWithRowbounds(QuestionCollectExample example, RowBounds rowBounds);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table QUESTION_COLLECT
     *
     * @mbg.generated Thu Feb 20 22:22:45 CST 2020
     */
    List<QuestionCollect> selectByExample(QuestionCollectExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table QUESTION_COLLECT
     *
     * @mbg.generated Thu Feb 20 22:22:45 CST 2020
     */
    QuestionCollect selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table QUESTION_COLLECT
     *
     * @mbg.generated Thu Feb 20 22:22:45 CST 2020
     */
    int updateByExampleSelective(@Param("record") QuestionCollect record, @Param("example") QuestionCollectExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table QUESTION_COLLECT
     *
     * @mbg.generated Thu Feb 20 22:22:45 CST 2020
     */
    int updateByExample(@Param("record") QuestionCollect record, @Param("example") QuestionCollectExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table QUESTION_COLLECT
     *
     * @mbg.generated Thu Feb 20 22:22:45 CST 2020
     */
    int updateByPrimaryKeySelective(QuestionCollect record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table QUESTION_COLLECT
     *
     * @mbg.generated Thu Feb 20 22:22:45 CST 2020
     */
    int updateByPrimaryKey(QuestionCollect record);
}