package com.mariakh.converter;

import com.mariakh.exception.ConverterException;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.util.Map;

import static org.junit.Assert.*;

public class BaseConverterTest {

    private final byte[] testArray = new byte[]{
            1, 0, 4, 0, -88, 50, -110, 86, 2, 0, 3, 0, 4, 113, 2, 3,
            0, 11, 0, -114, -114, -114, 32, -112, -82, -84, -96, -24,
            -86, -96, 4, 0, 29, 0, 11, 0, 7, 0, -124, -21, -32, -82,
            -86, -82, -85, 12, 0, 2, 0, 32, 78, 13, 0, 2, 0, 0, 2,
            14, 0, 2, 0, 64, -100, 4, 0, 29, 0, 11, 0, 7, 0, -124,
            -21, -32, -82, -86, -82, -85, 12, 0, 2, 0, 32, 78, 13,
            0, 2, 0, 0, 2, 14, 0, 2, 0, 64, -100
    };
    private final byte[] testArrayWrongData = new byte[]{
            1, 0, 4, 0, -88, 50, -110, 86, 2, 0, 3, 0, 4, 113, 2, 3,
            0, 11, 0, -114, -114, -114, 32, -112, -82, -84, -96, -24,
            -86, -96
    };
    private final BaseConverter baseConverter = new BaseConverter(new ByteArrayInputStream(testArray));
    private final BaseConverter baseConverterWrongData = new BaseConverter(new ByteArrayInputStream(testArrayWrongData));

    @Test
    public void getTagsWithDataTest() {
        Map<Short, byte[]> map = baseConverter.getTagsWithData(3);
        assertTrue(map.containsKey((short) 1));
        assertTrue(map.containsKey((short) 2));
        assertTrue(map.containsKey((short) 3));
    }

    @Test
    public void getLongAndReverseArrayTest() throws ConverterException {
        byte[] array = new byte[]{4, 113, 2};
        long value = baseConverter.getLongAndReverseArray(array);
        assertEquals(160004, value);
    }

    @Test(expected = ConverterException.class)
    public void getLongAndReverseArrayTestWithTooLongValue() throws ConverterException {
        byte[] array = new byte[]{4, 113, 2, 32, 78, 13, 4, 4, 4};
        baseConverter.getLongAndReverseArray(array);
    }

    @Test
    public void getTagOrLengthTest() {
        short value = baseConverter.getTagOrLength();
        assertEquals(1, value);
    }

    @Test
    public void getTagOrLengthTestWithWrongData() {
        short value = baseConverter.getTagOrLength();
        assertEquals(1, value);
    }

}