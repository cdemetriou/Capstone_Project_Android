package com.udacity.capstone;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.udacity.capstone.data.model.Item;

import org.parceler.Parcels;

import icepick.Bundler;
import icepick.Icepick;

/**
 * Created by christosdemetriou on 27/04/2018.
 */


public class BaseFragment extends Fragment {

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Icepick.restoreInstanceState(this, savedInstanceState);
    }

    @Override public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Icepick.saveInstanceState(this, outState);
    }


    /*public class ExampleBundler implements Bundler<Item> {
        @Override
        public void put(String s, Item example, Bundle bundle) {
            bundle.putParcelable(s, Parcels.wrap(example));
        }

        @Override
        public Item get(String s, Bundle bundle) {
            return Parcels.unwrap(bundle.getParcelable(s));
        }
    }*/
}
