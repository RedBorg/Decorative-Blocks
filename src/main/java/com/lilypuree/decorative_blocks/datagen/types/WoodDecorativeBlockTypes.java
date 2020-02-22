package com.lilypuree.decorative_blocks.datagen.types;

public enum WoodDecorativeBlockTypes {
    BEAM("beam"), PALISADE("palisade"), SEAT("seat"), SUPPORT("support");

    private final String name;

    WoodDecorativeBlockTypes(String name) {
        this.name = name;
    }

    public String toString() {
        return this.getName();
    }

    public String getName() {
        return this.name;
    }
}
