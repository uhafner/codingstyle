package edu.hm.hafner.util;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.function.UnaryOperator;

import edu.umd.cs.findbugs.annotations.NonNull;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

/**
 * An observable list that decorates a wrapped list with the observer pattern. This list will delegate all methods to
 * the wrapped list. Each method that changes the state of this list will notify its observers about the changes.
 *
 * @param <E>
 *         type of the list elements
 */
// TODO: implement observer pattern
public class ObservableList<E> implements List<E> {
    private final List<E> wrapped;

    /**
     * Creates a new observable list that decorates the specified list.
     *
     * @param decorated
     *         the list that should be decorated
     */
    @SuppressFBWarnings(value = "EI_EXPOSE_REP2", justification = "Decorator pattern requires that the mutable decorated instance is stored")
    public ObservableList(final List<E> decorated) {
        wrapped = decorated;
    }

    @Override
    public int size() {
        return wrapped.size();
    }

    @Override
    public boolean isEmpty() {
        return wrapped.isEmpty();
    }

    @Override
    public boolean contains(final Object o) {
        return wrapped.contains(o);
    }

    @Override
    public Iterator<E> iterator() {
        return wrapped.iterator();
    }

    @Override
    public Object[] toArray() {
        return wrapped.toArray();
    }

    @Override
    public <T> T[] toArray(@NonNull final T[] a) {
        return wrapped.toArray(a);
    }

    @Override
    public boolean add(final E e) {
        return wrapped.add(e);
    }

    @Override
    public boolean remove(final Object o) {
        return wrapped.remove(o);
    }

    @Override @SuppressWarnings("SlowListContainsAll")
    public boolean containsAll(@NonNull final Collection<?> c) {
        return wrapped.containsAll(c);
    }

    @Override
    public boolean addAll(@NonNull final Collection<? extends E> c) {
        return wrapped.addAll(c);
    }

    @Override
    public boolean addAll(final int index, @NonNull final Collection<? extends E> c) {
        return wrapped.addAll(index, c);
    }

    @Override
    public boolean removeAll(@NonNull final Collection<?> c) {
        return wrapped.removeAll(c);
    }

    @Override
    public boolean retainAll(@NonNull final Collection<?> c) {
        return wrapped.retainAll(c);
    }

    @Override
    public void replaceAll(final UnaryOperator<E> operator) {
        wrapped.replaceAll(operator);
    }

    @Override
    public void sort(final Comparator<? super E> c) {
        wrapped.sort(c);
    }

    @Override
    public void clear() {
        wrapped.clear();
    }

    @Override
    @SuppressWarnings("com.haulmont.jpb.EqualsDoesntCheckParameterClass")
    public boolean equals(final Object o) {
        return wrapped.equals(o);
    }

    @Override
    public int hashCode() {
        return wrapped.hashCode();
    }

    @Override
    public E get(final int index) {
        return wrapped.get(index);
    }

    @Override
    public E set(final int index, final E element) {
        return wrapped.set(index, element);
    }

    @Override
    public void add(final int index, final E element) {
        wrapped.add(index, element);
    }

    @Override
    public E remove(final int index) {
        return wrapped.remove(index);
    }

    @Override
    public int indexOf(final Object o) {
        return wrapped.indexOf(o);
    }

    @Override
    public int lastIndexOf(final Object o) {
        return wrapped.lastIndexOf(o);
    }

    @Override
    public ListIterator<E> listIterator() {
        return wrapped.listIterator();
    }

    @Override
    public ListIterator<E> listIterator(final int index) {
        return wrapped.listIterator(index);
    }

    @Override
    public List<E> subList(final int fromIndex, final int toIndex) {
        return wrapped.subList(fromIndex, toIndex);
    }
}
