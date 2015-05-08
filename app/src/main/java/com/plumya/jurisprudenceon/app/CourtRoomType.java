package com.plumya.jurisprudenceon.app;

/**
 * Created by toml on 08.05.15.
 */
public class CourtRoomType {
    private enum TYPES {
        IC(1), IK(2), IPUSiP(3), IW(4), PS(5);
        private int value;

        private TYPES (int value) {
            this.value = value;
        }
    };

    private int position;

    public CourtRoomType(int position) {
        this.position = position;
    }

    @Override
    public String toString() {
        switch (position) {
            case (TYPES.IC.ordinal()):
                break;
        }
    }
}
