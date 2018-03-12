/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.backede.jeconomix.utils;

import java.util.LinkedList;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author Joakim Backede ( joakim.backede@outlook.com )
 */
@Slf4j
public class GenericIterator<T> {

    private Integer currentIndex = 0;
    private Integer lastIndex = 1;
    private LinkedList<T> list;

    protected GenericIterator() {
    }

    public GenericIterator(LinkedList<T> list) {
        this.list = list;
        lastIndex = list.size() - 1;
    }

    public boolean hasNext() {
        return currentIndex + 1 <= lastIndex ? true : false;
    }

    public boolean hasPrevious() {
        return currentIndex - 1 >= 0 ? true : false;
    }

    public T first() {
        return list.getFirst();
    }

    public T next() {
        if (currentIndex + 1 <= lastIndex) {
            currentIndex++;
            return list.get(currentIndex);
        } else {
            return list.get(currentIndex);
        }

    }

    public T previous() {
        if (currentIndex - 1 >= 0) {
            currentIndex--;
            return list.get(currentIndex);
        } else {
            return list.get(currentIndex);
        }
    }

    public T getAtCurrentIndex() {
        return list.get(currentIndex);
    }

    public void replaceAtCurrentIndex(T data) {
        list.set(currentIndex, data);
    }

    public LinkedList<T> getAll() {
        return list;
    }

}
