package com.example.OnlineStore.exceptions;

import java.time.LocalDateTime;
public record RuntimeErrorResponse (String path,
                                        String msg,
                                        int statusCode,
                                        LocalDateTime localDateTime) {
}


