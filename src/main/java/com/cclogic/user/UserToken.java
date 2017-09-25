package com.cclogic.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Nishant on 9/26/2017.
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserToken {

    private long id;
    private String refreshToken;

}
