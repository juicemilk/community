package com.juicemilk.community.service;

import com.juicemilk.community.dto.PageDTO;
import com.juicemilk.community.dto.QuestionDTO;
import com.juicemilk.community.dto.QuestionQueryDTO;
import com.juicemilk.community.exception.CustomizeErrorCode;
import com.juicemilk.community.exception.CustomizeException;
import com.juicemilk.community.mapper.QuestionExtMapper;
import com.juicemilk.community.mapper.QuestionMapper;
import com.juicemilk.community.mapper.UserMapper;
import com.juicemilk.community.model.Question;
import com.juicemilk.community.model.QuestionExample;
import com.juicemilk.community.model.User;
import com.juicemilk.community.model.UserExample;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionService {
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionExtMapper questionExtMapper;

    public void incView(Long id) {
        Question question=new Question();
        question.setId(id);
        question.setViewCount(1);
        questionExtMapper.incView(question);
    }

    public PageDTO list(String search, Integer page, Integer size) {
        if(StringUtils.isNotBlank(search)){
            String firstSearch=StringUtils.replace(search,",","|");
            String secondSearch=StringUtils.replace(firstSearch,",","|");
            search=StringUtils.replace(secondSearch," ","|");


        }
        PageDTO<QuestionDTO> pageDTO=new PageDTO();

        QuestionQueryDTO questionQueryDTO = new QuestionQueryDTO();
        questionQueryDTO.setSearch(search);
        Integer totalCount=(int)questionExtMapper.countBySearch(questionQueryDTO);
        pageDTO.setPagination(totalCount,page,size);
        page=pageDTO.getPage();
        Integer offset=size*(page-1);
        QuestionExample questionExample=new QuestionExample();
        questionExample.setOrderByClause("gmt_modified desc");
        questionQueryDTO.setPage(offset);
        questionQueryDTO.setSize(size);
        List<Question> questionList = questionExtMapper.selectBySearch(questionQueryDTO);
        List<QuestionDTO> questionDTOList=new ArrayList<>();
        for (Question question : questionList) {
            UserExample userExample=new UserExample();
            userExample.createCriteria().andIdEqualTo(question.getCreator());
            List<User> users = userMapper.selectByExample(userExample);
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(users.get(0));
            questionDTOList.add(questionDTO);
        }
        pageDTO.setDataList(questionDTOList);

        return pageDTO;
    }

    public PageDTO listByUser(Long id,Integer page,Integer size){
        PageDTO<QuestionDTO> pageDTO=new PageDTO();
        QuestionExample questionExample=new QuestionExample();
        questionExample.createCriteria().andCreatorEqualTo(id);
        questionExample.setOrderByClause("gmt_modified desc");
        Integer totalCount=(int)questionMapper.countByExample(questionExample);
        pageDTO.setPagination(totalCount,page,size);
        page=pageDTO.getPage();
        Integer offset=size*(page-1);
        List<Question> questionList = questionMapper.selectByExampleWithRowbounds(questionExample,new RowBounds(offset,size));
        List<QuestionDTO> questionDTOList=new ArrayList<>();
        for (Question question : questionList) {
            UserExample userExample=new UserExample();
            userExample.createCriteria().andIdEqualTo(question.getCreator());
            List<User> users = userMapper.selectByExample(userExample);
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(users.get(0));
            questionDTOList.add(questionDTO);
        }
        pageDTO.setDataList(questionDTOList);

        return pageDTO;
    }

    public QuestionDTO getById(Long id) {
        Question question=questionMapper.selectByPrimaryKey(id);
        if(question==null){
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }
        QuestionDTO questionDTO=new QuestionDTO();
        BeanUtils.copyProperties(question,questionDTO);
        UserExample userExample=new UserExample();
        userExample.createCriteria().andIdEqualTo(question.getCreator());
        List<User> users = userMapper.selectByExample(userExample);
        questionDTO.setUser(users.get(0));
        return questionDTO;

    }

    public void createOrUpdate(Question question) {
        if(question.getId()==null){
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            question.setLikeCount(0);
            question.setViewCount(0);
            question.setCommentCount(0);
            questionMapper.insert(question);
        }else{
            question.setGmtModified(System.currentTimeMillis());
            QuestionExample questionExample=new QuestionExample();
            questionExample.createCriteria().andIdEqualTo(question.getId());
            int updatedCode=questionMapper.updateByExampleSelective(question,questionExample);
            if(updatedCode!=1){
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
        }
    }

    public List<QuestionDTO> selectRelated(QuestionDTO questionDTO) {
        if(StringUtils.isBlank(questionDTO.getTag())){
            return new ArrayList<>();
        }
//        StringUtils.split(questionDTO.getTag(),",|，");
        String tempTag=StringUtils.replace(questionDTO.getTag(),",","|");
        String newTag=StringUtils.replace(tempTag,"，","|");
        Question question=new Question();
        question.setId(questionDTO.getId());
        question.setTag(newTag);
        List<Question> questionList=questionExtMapper.selectRelated(question);
        List<QuestionDTO> questionDTOList=questionList.stream().map(question1 -> {QuestionDTO questionDTO1=new QuestionDTO();
        BeanUtils.copyProperties(question1,questionDTO1);
        return questionDTO1;
        }).collect(Collectors.toList());
        return questionDTOList;
    }

    public List<QuestionDTO> hotQuestionList(int page, int size) {
        QuestionExample questionExample=new QuestionExample();
        questionExample.setOrderByClause("view_count desc");
        List<Question> questionList=questionMapper.selectByExampleWithRowbounds(questionExample,new RowBounds(page,size));
        List<QuestionDTO> questionDTOList=questionList.stream().map(question1 -> {QuestionDTO questionDTO1=new QuestionDTO();
            BeanUtils.copyProperties(question1,questionDTO1);
            return questionDTO1;
        }).collect(Collectors.toList());
        return questionDTOList;
    }
//    删除问题
    public void delectQuestion(Long id){
        Question question=questionMapper.selectByPrimaryKey(id);
        if(question==null){
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }
        questionMapper.deleteByPrimaryKey(id);
    }

}
