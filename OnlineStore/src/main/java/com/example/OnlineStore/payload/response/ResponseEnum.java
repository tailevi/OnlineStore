package com.example.OnlineStore.payload.response;

import lombok.*;


@RequiredArgsConstructor
public enum ResponseEnum {
    DELETED("Item was deleted"),
    ADDED("The Item was successfully added."),
    UPDATED("The Item was successfully updated.");

    @Getter
    private final String resString;
}
