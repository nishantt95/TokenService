package com.cclogic.user;

/**
 * Created by Nishant on 9/26/2017.
 */
public interface UserTokenRepository {

    void saveUserToken(UserToken userToken);
    UserToken getUserTokenFromId(long id);
}
