package com.cclogic.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Nishant on 9/19/2017.
 */

@NoArgsConstructor
@Data
@AllArgsConstructor
public class ExceptionResponse {

    private int errorCode;
    private String errorMessage;
}
