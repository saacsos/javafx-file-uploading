package ku.cs.shop.services;

import java.util.HashMap;
import java.util.Map;

public class StringConfiguration {
    private Map<String, String> pairs;

    public StringConfiguration() {
        pairs = new HashMap<>();
    }

    public StringConfiguration(String ...args) {
        this();
        set(args);
    }

    public void set(String ...args) {
        for (String arg: args) {
            String k = arg.substring(0, arg.indexOf(":")).trim();
            String v = arg.substring(arg.indexOf(":") + 1).trim();
            pairs.put(k, v);
        }
    }

    public String get(String key) {
        return pairs.get(key);
    }

    public int size() {
        return pairs.size();
    }
}
