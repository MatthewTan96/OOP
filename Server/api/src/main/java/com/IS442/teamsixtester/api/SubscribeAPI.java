package com.IS442.teamsixtester.api;

import org.springframework.http.ResponseEntity;

public interface SubscribeAPI {
    ResponseEntity favouritePost(String vesselShortName, String incoming, String email);

    ResponseEntity favouriteDelete(String vesselShortName, String incoming, String email);

    String SUBSCRIBE_PATH_POST = "/postSubscribe";

    String SUBSCRIBE_PATH_DELETE = "/deleteSubscribe";
}
