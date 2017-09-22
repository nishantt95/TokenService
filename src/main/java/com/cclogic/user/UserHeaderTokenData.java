package com.cclogic.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Nishant on 9/20/2017.
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserHeaderTokenData {
    private int Id;
    private String role;
}
