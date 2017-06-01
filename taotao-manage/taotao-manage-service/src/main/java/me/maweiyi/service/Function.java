package me.maweiyi.service;

/**
 * Created by maweiyi on 6/1/17.
 */
interface Function<T, E> {
    public T callback(E e);
}

