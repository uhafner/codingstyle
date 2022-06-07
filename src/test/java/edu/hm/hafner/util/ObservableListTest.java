package edu.hm.hafner.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.function.UnaryOperator;

import org.junit.jupiter.api.Test;

import edu.hm.hafner.util.ChangeEvent.ChangeType;
import edu.umd.cs.findbugs.annotations.NonNull;

import static org.assertj.core.api.Assertions.*;

/**
 * Tests the class {@link ObservableList} with strings.
 *
 * @author Ullrich Hafner
 */
class ObservableListTest {
    private ObservableList<String> createObservableList() {
        List<String> wrapped = new ArrayList<>();

        return new ObservableList<>(wrapped);
    }

    private Changes<String> registerObserver(final ObservableList<String> observable) {
        Changes<String> changes = new Changes<>();
        // TODO: register the changes as an observer in the observable list
        return changes;
    }

    @Test
    void shouldNotifyObserverOnAdd() {
        // Given
        ObservableList<String> observable = createObservableList();

        // When
        Changes<String> changes = registerObserver(observable);

        // Then
        assertThat(observable).isEmpty();
        assertThat(changes).isEmpty();

        // When
        String hello = "Hello World";
        observable.add(hello);

        // Then
        assertThat(observable).containsExactly(hello);
        assertThat(changes).containsExactly(new ChangeEvent<>(hello, ChangeType.ADDITION));
    }

    @Test
    void shouldNotifyMultipleObserversOnRemove() {
        // Given
        ObservableList<String> observable = createObservableList();
        String initial = "Initial";
        observable.add(initial);

        // When
        Changes<String> firstChanges = registerObserver(observable);
        Changes<String> secondChanges = registerObserver(observable);

        // Then
        assertThat(observable).containsExactly(initial);
        assertThat(firstChanges).isEmpty();
        assertThat(secondChanges).isEmpty();

        // When
        observable.remove(initial);

        // Then
        assertThat(observable).isEmpty();
        assertThat(firstChanges).containsExactly(new ChangeEvent<>(initial, ChangeType.REMOVAL));
        assertThat(firstChanges).containsExactly(new ChangeEvent<>(initial, ChangeType.REMOVAL));
    }

    @Test
    void shouldNotifyObserverOnAddAll() {
        // Given
        ObservableList<String> observable = createObservableList();

        // When
        Changes<String> changesHandler = registerObserver(observable);

        // Then
        assertThat(observable).isEmpty();
        assertThat(changesHandler).isEmpty();

        // When
        String[] values = {"Hello", " ", "World"};
        observable.addAll(Arrays.asList(values));

        // Then
        assertThat(observable).containsExactly(values);
        assertThat(changesHandler).extracting(ChangeEvent::getElement).containsExactly(values);
    }

    /**
     * Mock that listens for change events in the observed collection. The current changes of the observed collection
     * will be stored in the internal list instance so that tests can use this instance in assertions.
     *
     * @param <E>
     *         type of the elements
     */
    private static class Changes<E> implements CollectionChangeListener<E>, List<ChangeEvent<E>> {
        private final List<ChangeEvent<E>> currentChanges = new ArrayList<>();

        @Override
        public int size() {
            return currentChanges.size();
        }

        @Override
        public boolean isEmpty() {
            return currentChanges.isEmpty();
        }

        @Override
        public boolean contains(final Object o) {
            return currentChanges.contains(o);
        }

        @Override
        @NonNull
        public Iterator<ChangeEvent<E>> iterator() {
            return currentChanges.iterator();
        }

        @Override
        @NonNull
        public Object[] toArray() {
            return currentChanges.toArray();
        }

        @Override
        @NonNull
        public <T> T[] toArray(@NonNull final T[] a) {
            return currentChanges.toArray(a);
        }

        @Override
        public boolean add(final ChangeEvent<E> eChangeEvent) {
            return currentChanges.add(eChangeEvent);
        }

        @Override
        public boolean remove(final Object o) {
            return currentChanges.remove(o);
        }

        @Override
        public boolean containsAll(@NonNull final Collection<?> c) {
            return currentChanges.containsAll(c);
        }

        @Override
        public boolean addAll(@NonNull final Collection<? extends ChangeEvent<E>> c) {
            return currentChanges.addAll(c);
        }

        @Override
        public boolean addAll(final int index, @NonNull final Collection<? extends ChangeEvent<E>> c) {
            return currentChanges.addAll(index, c);
        }

        @Override
        public boolean removeAll(@NonNull final Collection<?> c) {
            return currentChanges.removeAll(c);
        }

        @Override
        public boolean retainAll(@NonNull final Collection<?> c) {
            return currentChanges.retainAll(c);
        }

        @Override
        public void replaceAll(final UnaryOperator<ChangeEvent<E>> operator) {
            currentChanges.replaceAll(operator);
        }

        @Override
        public void sort(final Comparator<? super ChangeEvent<E>> c) {
            currentChanges.sort(c);
        }

        @Override
        public void clear() {
            currentChanges.clear();
        }

        @Override
        @SuppressWarnings("com.haulmont.jpb.EqualsDoesntCheckParameterClass")
        public boolean equals(final Object o) {
            return currentChanges.equals(o);
        }

        @Override
        public int hashCode() {
            return currentChanges.hashCode();
        }

        @Override
        public ChangeEvent<E> get(final int index) {
            return currentChanges.get(index);
        }

        @Override
        public ChangeEvent<E> set(final int index, final ChangeEvent<E> element) {
            return currentChanges.set(index, element);
        }

        @Override
        public void add(final int index, final ChangeEvent<E> element) {
            currentChanges.add(index, element);
        }

        @Override
        public ChangeEvent<E> remove(final int index) {
            return currentChanges.remove(index);
        }

        @Override
        public int indexOf(final Object o) {
            return currentChanges.indexOf(o);
        }

        @Override
        public int lastIndexOf(final Object o) {
            return currentChanges.lastIndexOf(o);
        }

        @Override
        @NonNull
        public ListIterator<ChangeEvent<E>> listIterator() {
            return currentChanges.listIterator();
        }

        @Override
        @NonNull
        public ListIterator<ChangeEvent<E>> listIterator(final int index) {
            return currentChanges.listIterator(index);
        }

        @Override
        @NonNull
        public List<ChangeEvent<E>> subList(final int fromIndex, final int toIndex) {
            return currentChanges.subList(fromIndex, toIndex);
        }

        /**
         * Listens for change events and replaces the current changes of this instance with the changes of the event.
         *
         * @param changes
         *         the changes in the observed collection
         */
        @Override
        public void handleChange(final List<ChangeEvent<E>> changes) {
            currentChanges.clear();
            currentChanges.addAll(changes);
        }
    }
}
