package com.accbdd.reclamation_util.item;

import com.blakebr0.cucumber.item.BaseWateringCanItem;

public class WateringCanItem extends BaseWateringCanItem {
    public WateringCanItem(int range, double chance) {
        super(range, chance);
    }

    @Override
    protected boolean allowFakePlayerWatering() {
        return false;
    }
}