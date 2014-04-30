package com.gmail.gazlloyd.rafflegrabber;

/**
 * Created by Gareth Lloyd on 30/04/14.
 */
public enum ResourceType {
    TIMBER ("Timber"),
    STONE ("Stone"),
    CHARCOAL ("Charcoal"),
    ORE ("Ore"),
    BARS ("Bars"),
    PRECIOUS_ORE ("Precious Ore"),
    PRECIOUS_BARS ("Precious Bars"),
    CLOTH ("Cloth"),
    RATIONS ("Rations"),
    MINIONS ("Minions");

    private final String name;

    ResourceType(String name) {
        this.name = name;
    }

    public String toString() { return name; }
}
