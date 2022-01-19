package com.jetwinner.webfast.session;

import com.jetwinner.util.ListUtil;

import java.util.*;

/**
 * @author xulixin
 */
public class FlashBag {

    public static String DEFAULT_SESSION_KEY = "_sf2_flashes";

    private String name = "flashes";

    /**
     * Flash messages.
     */
    private Map<String, List<String>> flashes = new HashMap<>(0);

    /**
     * The storage key for flashes in the session
     */
    private final String storageKey;

    /**
     * Constructor.
     *
     * @param storageKey The key used to store flashes in the session.
     */
    public FlashBag(String storageKey) {
        this.storageKey = storageKey;
    }

    public FlashBag() {
        this.storageKey = "_sf2_flashes";
    }

    public Map<String, List<String>> toMap() {
        return this.flashes;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void initialize(Map<String, List<String>> flashes) {
        this.flashes = flashes;
    }

    public void add(String type, String message) {
        List<String> list = this.flashes.computeIfAbsent(type, k -> new ArrayList<>());
        list.add(message);
    }

    public List<String> peek(String type, List<String> defaultValue) {
        if (defaultValue == null) {
            defaultValue = new ArrayList<>();
        }
        return this.has(type) ? this.flashes.get(type) : defaultValue;
    }

    public Map<String, List<String>> peekAll() {
        return this.flashes;
    }

    public void set(String type, String messages) {
        this.flashes.put(type, ListUtil.newArrayList(messages));
    }

    public void setAll(Map<String, List<String>> messages) {
        this.flashes = messages;
    }

    public boolean has(String type) {
        return this.flashes.containsKey(type);
    }

    public Set<String> keys() {
        return this.flashes.keySet();
    }

    public String getStorageKey() {
        return this.storageKey;
    }

}
