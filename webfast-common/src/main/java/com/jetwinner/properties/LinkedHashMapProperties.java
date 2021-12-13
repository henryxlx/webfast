package com.jetwinner.properties;

import java.util.*;

/**
 * Java中Properties加载属性文件后是无法保证输出的顺序与文件中一致的，因为Properties是继承自Hashtable的，
 * key/value 都是直接存在 Hashtable 中的，而 Hashtable 是不保证进出顺序的。
 * 总有时候会有关心顺序一致的需求，恰如有 org.apache.commons.collections.OrderedMap一样（其实用 LinkedHashMap 就是保证顺序），
 * 我们也想要有个 OrderedProperties。网上查了下还真有：
 * http://livedocs.adobe.com/jrun/4/javadocs/jrunx/util/OrderedProperties.html
 * http://www.openrdf.org/doc/alibaba/2.0-rc4/apidocs/org/openrdf/repository/object/composition/helpers/OrderedProperties.html
 * 其实自己写一个 OrderedProperties也不难，并不需要重头写起，只要继承自 Properties，
 * 覆盖原来的 put/keys,keySet,stringPropertyNames 即可，其中用一个 LinkedHashSet 来保存它的所有 key。
 * <p>
 * 注意：有两处父类是synchronized,子类没加synchronized,子类还是加上比较好
 *
 * @author xulixin
 */
public class LinkedHashMapProperties extends Properties {

    private static final long serialVersionUID = -4627607243846121965L;

    private final LinkedHashSet<Object> keys = new LinkedHashSet<>();

    @Override
    public synchronized Enumeration<Object> keys() {
        return Collections.enumeration(keys);
    }

    @Override
    public synchronized Object put(Object key, Object value) {
        keys.add(key);
        return super.put(key, value);
    }

    @Override
    public Set<Object> keySet() {
        return keys;
    }

    @Override
    public Set<String> stringPropertyNames() {
        Set<String> set = new LinkedHashSet<>();
        for (Object key : this.keys) {
            set.add((String) key);
        }
        return set;
    }
}