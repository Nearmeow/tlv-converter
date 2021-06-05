package com.mariakh.converter;

import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.*;

public class StreamDataConverterTest {

    @Test
    public void getOrderDataTest() {
        Map<Short, byte[]> map = BaseTest.streamDataConverter.getOrderData();
        assertEquals(3, map.size());
        assertTrue(map.containsKey((short) 1));
        assertTrue(map.containsKey((short) 2));
        assertTrue(map.containsKey((short) 3));
    }

    @Test
    public void getItemsData() {
    }
}