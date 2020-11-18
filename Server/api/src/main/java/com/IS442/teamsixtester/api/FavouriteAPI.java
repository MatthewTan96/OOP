package com.IS442.teamsixtester.api;

import org.springframework.http.ResponseEntity;

public interface FavouriteAPI {
    ResponseEntity favouritePost(String vesselShortName, String incoming, String email);

    ResponseEntity favouriteDelete(String vesselShortName, String incoming, String email);

    String FAVOURITE_PATH_POST = "/postFavourite";

    String FAVOURITE_PATH_DELETE = "/deleteFavourite";
}
