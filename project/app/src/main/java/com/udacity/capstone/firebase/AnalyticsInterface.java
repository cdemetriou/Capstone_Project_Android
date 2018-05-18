package com.udacity.capstone.firebase;



interface AnalyticsInterface {

        String SCREEN_PROPERTY = "Launch_";
        String USER_PROPERTY = "email";
        String FAV_ID_PROPERTY = "fav_id";
        String FAV_NAME_PROPERTY = "fav_name";
        String EXCEPTION_PROPERTY = "exception";
        String REQUEST_PROPERTY = "request";

        String ADD_FAV_SUCCESS_EVENT = "AddFavoriteSuccessEvent";
        String REMOVE_FAV_SUCCESS_EVENT = "RemoveFavoriteSuccessEvent";
        String ADD_FAV_FAILURE_EVENT = "AddFavoriteFailureEvent";
        String REMOVE_FAV_FAILURE_EVENT = "RemoveFavoriteFailureEvent";
        String REQUEST_SUCCESS_EVENT = "DataRequestSuccessEvent";
        String REQUEST_FAILURE_EVENT = "DataRequestFailureEvent";


        void logScreenLaunch(String screenName);
        void logSetUser();
        void logAddFavoriteSuccessEvent(String id, String name);
        void logRemoveFavoriteSuccessEvent(String id, String name);
        void logAddFavoriteFailureEvent(String exception);
        void logRemoveFavoriteFailureEvent(String exception);
        void logDataRequestSuccessEvent(String request);
        void logDataRequestFailureEvent(String request, String exception);

}
