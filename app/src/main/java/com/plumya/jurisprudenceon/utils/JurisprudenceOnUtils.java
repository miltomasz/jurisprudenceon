package com.plumya.jurisprudenceon.utils;

import android.content.Context;
import android.graphics.Color;

import com.parse.ParseObject;
import com.plumya.jurisprudenceon.R;

/**
 * Created by toml on 15.05.15.
 */
public class JurisprudenceOnUtils {

    public static int color(String courtRoom) {
        switch(courtRoom) {
            case "IC" : return R.color.colorAccent;
            case "IK" : return R.color.lightRed;
            case "IPUSiSP" : return R.color.yellow;
            case "IW" : return R.color.golden;
            case "PS" : return R.color.accent_material_light;
            default:return Color.WHITE;
        }
    }

    public static String label(Context context, String courtRoom) {
        String[] courtRooms = context.getResources()
                .getStringArray(R.array.court_rooms_array);
        switch(courtRoom) {
            case "IC" : return courtRooms[1];
            case "IK" : return courtRooms[2];
            case "IPUSiSP" : return courtRooms[3];
            case "IW" : return courtRooms[4];
            case "PS" : return courtRooms[5];
            default:return courtRooms[0];
        }
    }
}
