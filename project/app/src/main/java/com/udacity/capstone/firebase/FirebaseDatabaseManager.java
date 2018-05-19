package com.udacity.capstone.firebase;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.udacity.capstone.data.model.Item;
import com.udacity.capstone.data.model.ItemList;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;


import static com.google.firebase.database.DatabaseError.*;



@SuppressWarnings("CanBeFinal")
public class FirebaseDatabaseManager implements DatabaseInterface {


    public interface EventCallback {

        void added(Item item);

        void removed(Item item);

        void searchResult(ItemList list);
    }

    private EventCallback callback;

    public void setCallback(EventCallback eventCallback){

        callback = eventCallback;
    }


    private final int ADD_ACTION = 0;

    private final int  REMOVE_ACTION = 1;


    private FirebaseUser user;

    private FirebaseDatabase database;

    private FirebaseAnalyticsManager analyticsManager;

    private DatabaseReference allUsersRef;

    private DatabaseReference userNode;

    private int action = ADD_ACTION;

    public List<Integer> favoriteIds;


    @Inject
    public FirebaseDatabaseManager(FirebaseAuth auth, FirebaseDatabase database, FirebaseAnalyticsManager analyticsManager){
        this.user = auth.getCurrentUser();
        this.database = database;
        this.analyticsManager = analyticsManager;
        favoriteIds = new ArrayList<>();

        allUsersRef = database.getReference(USERS_REFERENCE).child(user.getUid());

    }

    public void setUser() {

        // Get users node and child with current user id
        userNode = allUsersRef.child(user.getUid());

        allUsersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(!dataSnapshot.exists()) {
                    //create new user
                    userNode.child(EMAIL_REFERENCE).setValue(user.getEmail());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void getFavorites(){
        DatabaseReference fav = database.getReference(USERS_REFERENCE+"/"+ user.getUid()).child(FAVORITES_REFERENCE);
        Query query = fav.orderByKey();
        query.addChildEventListener(childEventListener);
    }

    public void addFavorite(Item item){
        if (favoriteIds.contains(item.getId())) return;

        action = ADD_ACTION;
        DatabaseReference fav = database.getReference(USERS_REFERENCE +"/"+ user.getUid()).child(FAVORITES_REFERENCE);
        String key = fav.push().getKey();
        fav.child(key).setValue(item);

    }

    public void removeFavorite(Item item) {
        if (!favoriteIds.contains(item.getId())) return;

        action = REMOVE_ACTION;
        Query query;
        DatabaseReference fav = database.getReference(USERS_REFERENCE +"/"+ user.getUid()).child(FAVORITES_REFERENCE);

        query = fav.orderByChild(ID_REFERENCE).equalTo(item.getId());

        query.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    //create new user
                    Item item = null;
                    for (DataSnapshot singleSnapshot: dataSnapshot.getChildren()) {
                        item = singleSnapshot.getValue(Item.class);
                        singleSnapshot.getRef().removeValue();
                    }

                    if (callback != null) callback.removed(item);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void searchFavoritesByName(String text) {

        Query query;
        DatabaseReference fav = database.getReference(USERS_REFERENCE+ "/"+ user.getUid()).child(FAVORITES_REFERENCE);

        query = fav.orderByChild(NAME_REFERENCE).startAt(text);

        query.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                ItemList itemList = new ItemList(null, ItemList.Type.isCharacter);
                List<Item> list = new ArrayList<>();

                for (DataSnapshot singleSnapshot: dataSnapshot.getChildren()) {
                    Item item = singleSnapshot.getValue(Item.class);
                    list.add(item);
                }
                itemList.setList(list);

                if (callback != null) callback.searchResult(itemList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


    private final ChildEventListener childEventListener = new ChildEventListener() {

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {}

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {}

        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {

            Item item = dataSnapshot.getValue(Item.class);
            assert item != null;
            favoriteIds.add(item.getId());
            if (callback != null) callback.added(item);

            analyticsManager.logAddFavoriteSuccessEvent(item.getId().toString(), item.getName());
        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {

            Item item = dataSnapshot.getValue(Item.class);
            assert item != null;
            favoriteIds.remove(item.getId());
            if (callback != null) callback.removed(item);

            analyticsManager.logRemoveFavoriteSuccessEvent(item.getId().toString(), item.getName());
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

            String msg = "";

            switch (databaseError.getCode()) {
                case DISCONNECTED:
                    msg = DISCONNECTED_MSG;
                    break;
                case EXPIRED_TOKEN:
                    msg = EXPIRED_TOKEN_MSG;
                    break;
                case INVALID_TOKEN:
                    msg = INVALID_TOKEN_MSG;
                    break;
                case MAX_RETRIES:
                    msg = MAX_RETRIES_MSG;
                    break;
                case NETWORK_ERROR:
                    msg = NETWORK_ERROR_MSG;
                    break;
                case OPERATION_FAILED:
                    msg = OPERATION_FAILED_MSG;
                    break;
                case OVERRIDDEN_BY_SET:
                    msg = OVERRIDDEN_BY_SET_MSG;
                    break;
                case PERMISSION_DENIED:
                    msg = PERMISSION_DENIED_MSG;
                    break;
                case UNAVAILABLE:
                    msg = UNAVAILABLE_MSG;
                    break;
                case UNKNOWN_ERROR:
                    msg = UNKNOWN_ERROR_MSG;
                    break;
                case USER_CODE_EXCEPTION:
                    msg = USER_CODE_EXCEPTION_MSG;
                    break;
                case WRITE_CANCELED:
                    msg = WRITE_CANCELED_MSG;
                    break;
            }

            switch(action){
                case ADD_ACTION:
                    analyticsManager.logAddFavoriteFailureEvent(msg);
                    break;
                case REMOVE_ACTION:
                    analyticsManager.logRemoveFavoriteFailureEvent(msg);
                    break;
            }
        }
    };

}
