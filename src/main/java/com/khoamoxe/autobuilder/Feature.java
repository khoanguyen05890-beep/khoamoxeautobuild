package com.khoamoxe.autobuilder;

/** Danh sach cac chuc nang trong menu chinh. */
public enum Feature {
    AUTO_BUILD("AUTO BUILD"),
    AUTO_ROTATION("AUTO ROTATION"),
    AUTO_INVENTORY("AUTO INVENTORY"),
    AUTO_SHOP("AUTO SHOP"),
    AUTO_AUCTION_HOUSE("AUTO AUCTION HOUSE"),
    AUTO_CHEST("AUTO CHEST"),
    AUTO_WATER("AUTO WATER"),
    AUTO_LAVA("AUTO LAVA"),
    AUTO_FIX_BLOCK("AUTO FIX BLOCK"),
    SETTINGS("SETTINGS");

    public final String label;

    Feature(String label) {
        this.label = label;
    }
}
