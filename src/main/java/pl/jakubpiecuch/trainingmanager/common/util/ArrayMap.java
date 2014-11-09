package pl.jakubpiecuch.trainingmanager.common.util;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.util.Assert;

import java.util.*;

public abstract class ArrayMap<V> implements Map<Integer, V> {

    private V[] array;

    public ArrayMap(V[] array) {
        Assert.notNull(array);
        this.array = array;
    }

    public abstract void update(V[] array);

    @Override
    public int size() {
        return array.length;
    }

    @Override
    public boolean isEmpty() {
        return array.length == 0;
    }

    @Override
    public boolean containsKey(Object key) {
        return array.length - 1 >= (Integer) key;
    }

    @Override
    public boolean containsValue(Object value) {
        return ArrayUtils.contains(array, value);
    }

    @Override
    public V get(Object key) {
        Assert.isTrue(containsKey(key));
        return array[(Integer) key];
    }

    @Override
    public V put(Integer key, V value) {
        Assert.isTrue(array.length >= key);
        V oldVal = array.length > key ? array[key] : null;
        array = (V[]) ArrayUtils.add(array, key, value);
        update(array);
        return oldVal;
    }

    @Override
    public V remove(Object key) {
        V val = get(key);
        array = (V[]) ArrayUtils.remove(array, (Integer) key);
        update(array);
        return val;
    }

    @Override
    public void putAll(Map<? extends Integer, ? extends V> m) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {}

    @Override
    public Set<Integer> keySet() {
        return new HashSet<Integer>() {
            {
                for(int i = 0; i < array.length; i++) {
                    add(i);
                }
            }
        };
    }

    @Override
    public Collection<V> values() {
        return new ArrayList<V>() {
            {
                for(int i = 0; i < array.length; i++) {
                    add(array[i]);
                }
            }
        };
    }

    @Override
    public Set<Entry<Integer, V>> entrySet() {
        return new HashSet<Entry<Integer, V>>() {
            {
                for(int i = 0; i < array.length; i++) {
                    final int key = i;
                    final V val = array[i];
                    add(new Entry<Integer, V>() {
                        @Override
                        public Integer getKey() {
                            return key;
                        }

                        @Override
                        public V getValue() {
                            return val;
                        }

                        @Override
                        public V setValue(V value) {
                            return null;
                        }
                    });
                }
            }
        };
    }
}
