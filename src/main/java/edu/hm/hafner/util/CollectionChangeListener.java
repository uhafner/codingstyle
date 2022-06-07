package edu.hm.hafner.util;

import java.util.List;

/**
 * A listener for changes in an observable collection.
 *
 * @param <E> type of the collection elements
 * @author Ullrich Hafner
 */
public interface CollectionChangeListener<E> {
    /**
     * Handles the changes in the observed collection.
     *
     * @param changes the changes in the collection
     */
    void handleChange(List<ChangeEvent<E>> changes);
}
