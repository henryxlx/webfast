package com.jetwinner.util;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public final class ListUtil {

    private ListUtil() {
        // reserved.
    }

    public static <E> ArrayList<E> newArrayList() {
        return new ArrayList<E>();
    }

    @SafeVarargs
    public static <E> ArrayList<E> newArrayList(E... elements) {
        ArrayList<E> list = new ArrayList<E>(elements.length);
        Collections.addAll(list, elements);
        return list;
    }

    public static <E> ArrayList<E> newArrayList(Iterable<? extends E> elements) {
        return (elements instanceof Collection) ? new ArrayList<E>(cast(elements))
                : newArrayList(elements.iterator());
    }

    public static <E> ArrayList<E> newArrayList(Iterator<? extends E> elements) {
        ArrayList<E> list = newArrayList();
        addAll(list, elements);
        return list;
    }

    public static <E> LinkedList<E> newLinkedList() {
        return new LinkedList<E>();
    }

    public static <E> LinkedList<E> newLinkedList(Iterable<? extends E> elements) {
        LinkedList<E> list = newLinkedList();
        addAll(list, elements);
        return list;
    }

    public static <E> CopyOnWriteArrayList<E> newCopyOnWriteArrayList() {
        return new CopyOnWriteArrayList<E>();
    }

    public static <E> CopyOnWriteArrayList<E> newCopyOnWriteArrayList(Iterable<? extends E> elements) {
        Collection<? extends E> elementsCollection = (elements instanceof Collection)
                ? cast(elements) : newArrayList(elements);
        return new CopyOnWriteArrayList<E>(elementsCollection);
    }

    private static <T> Collection<T> cast(Iterable<T> iterable) {
        return (Collection<T>) iterable;
    }

    private static <T> boolean addAll(Collection<T> addTo, Iterator<? extends T> iterator) {
        boolean wasModified = false;
        while (iterator.hasNext()) {
            wasModified |= addTo.add(iterator.next());
        }
        return wasModified;
    }

    public static <T> boolean addAll(Collection<T> addTo, Iterable<? extends T> elementsToAdd) {
        if (elementsToAdd instanceof Collection) {
            Collection<? extends T> c = cast(elementsToAdd);
            return addTo.addAll(c);
        }
        return addAll(addTo, elementsToAdd.iterator());
    }

    /**
     * ??????????????????.
     */
    public static boolean isEmpty(Collection collection) {
        return (collection == null || collection.isEmpty());
    }

    /**
     * ????????????????????????.
     */
    public static boolean isNotEmpty(Collection collection) {
        return !(isEmpty(collection));
    }

    /**
     * ??????Collection???????????????????????????collection????????????null.
     */
    public static <T> T getFirst(Collection<T> collection) {
        if (isEmpty(collection)) {
            return null;
        }
        return collection.iterator().next();
    }

    /**
     * ??????Collection????????????????????? ?????????collection????????????null.
     */
    public static <T> T getLast(Collection<T> collection) {
        if (isEmpty(collection)) {
            return null;
        }
        //????????????List???????????????????????????????????? ???
        if (collection instanceof List) {
            List<T> list = (List<T>) collection;
            return list.get(list.size() - 1);
        }
        //??????????????????iterator???????????????????????????.
        Iterator<T> iterator = collection.iterator();
        while (true) {
            T current = iterator.next();
            if (!iterator.hasNext()) {
                return current;
            }
        }
    }

    /**
     * ??????a+b??????List.
     */
    public static <T> List<T> union(final Collection<T> a, final Collection<T> b) {
        List<T> result = new ArrayList<T>(a);
        result.addAll(b);
        return result;
    }

    /**
     * ??????a-b??????List.
     */
    public static <T> List<T> subtract(final Collection<T> a, final Collection<T> b) {
        List<T> list = new ArrayList<T>(a);
        for (T element : b) {
            list.remove(element);
        }
        return list;
    }

    /**
     * ??????a???b???????????????List.
     */
    public static <T> List<T> intersection(Collection<T> a, Collection<T> b) {
        List<T> list = new ArrayList<T>();
        for (T element : a) {
            if (b.contains(element)) {
                list.add(element);
            }
        }
        return list;
    }

}
