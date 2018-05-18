package com.udacity.capstone.firebase;

import com.udacity.capstone.data.model.Item;


@SuppressWarnings("unused")
interface DatabaseInterface {

    String USERS_REFERENCE = "users";
    String FAVORITES_REFERENCE = "favorites";
    String NAME_REFERENCE = "name";
    String EMAIL_REFERENCE = "email";
    String ID_REFERENCE = "id";

    String DISCONNECTED_MSG = "The operation had to be aborted due to a network disconnect";
    String EXPIRED_TOKEN_MSG = "The supplied auth token has expired";
    String INVALID_TOKEN_MSG = "The specified authentication token is invalid.";
    String MAX_RETRIES_MSG = "The transaction had too many retries";
    String NETWORK_ERROR_MSG = "The operation could not be performed due to a network error.";
    String OPERATION_FAILED_MSG  = "The server indicated that this operation failed";
    String OVERRIDDEN_BY_SET_MSG= "The transaction was overridden by a subsequent set";
    String PERMISSION_DENIED_MSG = "This client does not have permission to perform this operation";
    String UNAVAILABLE_MSG = "The service is unavailable";
    String UNKNOWN_ERROR_MSG = "An unknown error occurred.";
    String USER_CODE_EXCEPTION_MSG = "An exception occurred in user code";
    String WRITE_CANCELED_MSG = "The write was canceled locally";

    void setUser();
    void getFavorites();
    void removeFavorite(Item item);
    void searchFavoritesByName(String text);
}
