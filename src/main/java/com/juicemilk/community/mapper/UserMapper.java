package com.juicemilk.community.mapper;

import com.juicemilk.community.model.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface UserMapper {
    @Insert("insert into public.user(name,account_id,token,gmt_create,gmt_modified,login,avatar_url) values(#{name},#{accountId},#{token},#{gmtCreate},#{gmtModified},#{login},#{avatarUrl})")
    void insert(User user);

    @Select("select * from public.user where token=#{token}")
    User findByToken(@Param("token") String token);

//    @Delete("delete token from public.user where token=#{token}")
//    void delete(@Param("token") String token);

    @Update("update public.user set token=#{newToken} where token=#{token}")
    void updateToken(@Param("token") String token,@Param("newToken") String newToken);

    @Update("update public.user set gmt_modified=#{newGMTModified} where account_id=#{accountId}")
    void updateModified(@Param("newGMTModified") Long newGMTModified,@Param("accountId") String accountId);

    @Select("select * from public.user where account_id=#{accountId}")
    User findByAccountId(@Param("accountId") String accountId);
}
