function showSelectTag() {
    var tagId=$("#select_tag");
    tagId.show();
}

/**
 * 选择标签
 */

function selectTag(e) {
    var previous=$("#tag").val();
    var tag=e.getAttribute("data-tagName");
    if(previous.indexOf(tag)==-1){
        if(previous){
            $("#tag").val(previous+','+tag);
        }else{
            $("#tag").val(tag);
        }
    }
}

/**
 * 提交回复
 */

function post() {
   var questionId=$("#question_id").val();
   var content=$("#comment_content").val();
   comment2target(questionId,1,content);
}
function comment(e) {
    var commentId=e.getAttribute("data-commentId");
    var content=$("#input-"+commentId).val();
    console.log(commentId);
    console.log(content);
    comment2target(commentId,2,content);
}


function comment2target(targetId,type,content){
    if(!content){
        alert("不能回复空内容");
        return;
    }
    $.ajax({
        type:"POST",
        contentType:"application/json",
        url:"/comment",
        data: JSON.stringify({
            "parentId":targetId,
            "content":content,
            "type":type
        }),
        success:function(response){
            if(response.code==200){
                // $("#comment_section").hide();
                window.location.reload();
            }else{
                if(response.code==2004){
                    var isAccepted=confirm(response.message);
                    if(isAccepted){
                        window.open("https://github.com/login/oauth/authorize?client_id=134e5fe7c1822a42be40&redirect_uri=http://localhost:8887/callback&scope=user&state=1");
                        window.localStorage.setItem("closeable",true);
                    }
                }else{
                    alert(response.message);
                }

            }
        },
        dataType:"json"
    });
    console.log(targetId);
    console.log(content);
}

/**
 * 展开二级回复
 */
function collapseComment(e){
    var commentId=e.getAttribute("data-commentId");
    var comments=$("#comment-"+commentId);
    if(comments.hasClass("in")){
        comments.toggleClass("in");
        e.classList.remove("active");

    }else{
        var subCommentContainer=$("#comment-"+commentId);
        if(subCommentContainer.children().length!==1){
            comments.addClass("in");

            e.classList.add("active");
        }else{
            $.getJSON( "/comment/"+commentId, function( data) {

                $.each( data.data.reverse(), function( index,comment  ) {

                    var mediaBodyElement=$("<div/>",{
                        "class":"media-body",
                        "style":"padding-left: 3px; font-size: 14px",
                    }).append($("<h7/>",{
                        "class":"media-heading",
                        "style":"color: #155faa",
                        "html":comment.user.login
                    })).append($("<div/>",{
                        "style":"margin-top: 6px",
                        "html":comment.content
                    })).append($("<div/>",{
                        "class":"comment_menu",

                    })).append($("<span/>",{
                        "class":"pull-right",
                        "html":moment(comment.gmtCreate).format('YYYY-MM-DD HH:mm')

                    }));

                    var mediaLeftElement=$("<div/>",{
                        "class":"media-left",
                    }).append($("<img/>",{
                        "class":"media-object img-rounded media-image",
                        "src":comment.user.avatarUrl
                    }));

                    var mediaElement=$("<div/>",{
                        "class":"media ",
                    }).append(mediaLeftElement).append(mediaBodyElement);

                    var commentElement=$("<div/>",{
                        "class":"col-lg-12 col-md-12 col-sm-12 col-xs-12 comments",
                    }).append(mediaElement);
                    subCommentContainer.prepend(commentElement);
                });

            });
            comments.addClass("in");
            e.classList.remove("active");
        }

    }

}

