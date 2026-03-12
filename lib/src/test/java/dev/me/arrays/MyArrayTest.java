package dev.me.arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MyArrayTest {

    private MyArray array;

    @BeforeEach
    void setUp(){
        array = new MyArray(5);
    }

    @Test
    void shouldReturnItemAtIndex()
    {
        array.push("hello");
        Object result = array.get(0);
        assertEquals("hello", result);
    }

    @Test
    void shouldReturnDifferentItems()
    {
        array.push("hello");
        array.push("world");

        Object result = array.get(0);
        Object result2 = array.get(1);

        assertEquals("hello", result);
        assertEquals("world", result2);
    }

    @Test
    void shouldPushUntilReachesCapacity()
    {
        assertDoesNotThrow(() -> {
            array.push("Item 1");
            array.push("Item 2");
            array.push("Item 3");
            array.push("Item 4");
            array.push("Item 5");
        });
    }

    @Test
    void shouldThrowExceptionWhenPushingMoreThanCapacity()
    {
        assertThrows(IndexOutOfBoundsException.class, () -> {
            array.push("Item 1");
            array.push("Item 2");
            array.push("Item 3");
            array.push("Item 4");
            array.push("Item 5");
            array.push("Item 6");
        });
    }

    @Test
    void shouldPopItem()
    {
        array.push("Item 1");
        array.push("Item 2");

        Object result = array.pop();

        assertEquals("Item 1" , result);
    }


}