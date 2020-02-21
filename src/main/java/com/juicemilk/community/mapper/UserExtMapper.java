package com.juicemilk.community.mapper;

import com.juicemilk.community.model.User;

public interface UserExtMapper {
    int incFollow(User record);
    int decFollow(User record);

    int incFan(User record);
    int decFan(User record);
}
