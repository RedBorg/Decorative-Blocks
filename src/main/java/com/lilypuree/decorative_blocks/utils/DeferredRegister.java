package com.lilypuree.decorative_blocks.utils;

import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.ArrayList;
import java.util.function.Supplier;

/**
 * A class to emulate some parts of Forge's DeferredRegister, to ease transition to fabric
 */
public class DeferredRegister<T> {

    /**
     * Allows to associate an id to an entry.
     * Also, allows to have an id lazily-defined.
     *
     * @param <T> The type of the entry
     */
    public static class EntryAndId<T> {
        public Identifier id;
        protected T entry;
        protected Supplier<T> supplier;

        public EntryAndId(Identifier id, T entry) {
            this.id = id;
            this.entry = entry;
            this.supplier = null;
        }

        public EntryAndId(Identifier id, Supplier<T> supplier) {
            this.id = id;
            this.entry = null;
            this.supplier = supplier;
        }

        public T getEntry() {
            if (entry == null) {
                entry = supplier.get();
            }

            return entry;
        }
    }

    public final String MODID;
    public final Registry<T> REGISTRY;

    protected ArrayList<EntryAndId<T>> entries = new ArrayList<>();

    public DeferredRegister(Registry<T> registry, String modid) {
        this.MODID = modid;
        this.REGISTRY = registry;
    }

    /**
     * Adds an entry to the list and return it.
     *
     * @param id_name id of the entry, with the mod id omitted
     * @return the passed entry
     */
    public <E extends T> E add(String id_name, E entry) {
        entries.add(new EntryAndId<>(new Identifier(MODID, id_name), entry));
        return entry;
    }

    /**
     * Adds an lazily-computed entry, and returns the lazy wrapper.
     *
     * @param id_name id of the entry, with the mod id omitted.
     * @return The lazy wrapper around the entry supplier.
     */
    public <E extends T> EntryAndId<E> add(String id_name, Supplier<E> supplier) {
        EntryAndId lazy_entry = new EntryAndId(new Identifier(MODID, id_name), supplier);
        entries.add(lazy_entry);
        return lazy_entry;
    }

    public void register() {
        for (EntryAndId<T> x : entries) {
            Registry.register(REGISTRY, x.id, x.entry);
        }
    }

}
