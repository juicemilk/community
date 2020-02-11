package com.juicemilk.community.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

@Data
public class PageDTO {
    private List<QuestionDTO> questionDTOList;
    private boolean showPreviousPage;
    private boolean showFirstPage;
    private boolean showNextPage;
    private boolean showEndPage;
    private Integer page;
    private Integer totalPage;
    private List<Integer> pages =new ArrayList<>();

    public void setPagination(Integer totalCount, Integer page, Integer size) {
        if(totalCount%size==0){
            totalPage=totalCount/size;
        }else{
            totalPage=totalCount/size+1;
        }
        if(page>totalPage){
            page=totalPage;
        }
        if(page<1){
            page=1;
        }
        this.page=page;
        int iniPage=4;
        int middlePage=7;
        int spacePage=middlePage-iniPage;
        if(page<=middlePage-spacePage){
            for(int i=1;i<=page+spacePage;i++){
                if(i>=1&&i<=totalPage){
                    pages.add(i);
                }
            }
        }
        if(pages.size()==0){
            if(page>middlePage-spacePage&&page<totalPage-spacePage){
                for(int i=page-3;i<=page+3;i++){
                    if(i>=1&&i<=totalPage){
                        pages.add(i);
                    }
                }
            }
        }

        if(pages.size()==0){
            if(page>=totalPage-spacePage){
                for(int i=page-spacePage;i<=totalPage;i++){
                    if(i>=1&&i<=totalPage){
                        pages.add(i);
                    }

                }
            }
        }
        if(page==1){
            showPreviousPage=false;
        }else{
            showPreviousPage=true;
        }
        if(page==totalPage){
            showNextPage=false;
        }else{
            showNextPage=true;
        }

        if(pages.contains(1)){
            showFirstPage=false;
        }else{
            showFirstPage=true;
        }

        if(pages.contains(totalPage)){
            showEndPage=false;
        }else{
            showEndPage=true;
        }
    }
}
