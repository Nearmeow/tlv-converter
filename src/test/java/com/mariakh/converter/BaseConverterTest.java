package com.mariakh.converter;

import com.mariakh.exception.ConverterException;
import org.junit.Test;

import static org.junit.Assert.*;

public class BaseConverterTest {

    @Test
    public void getTagOrLengthTest() {
        byte[] array = new byte[]{2, 0, 4, 0};
        short tag = BaseConverter.getTagOrLength(array, 0);
        assertEquals(2, tag);
    }

    @Test
    public void getLongAndReverseArrayTest() throws ConverterException {
        byte[] array = new byte[]{4, 113, 2};
        long value = BaseConverter.getLongAndReverseArray(array);
        assertEquals(160004, value);
    }

    @Test(expected = ConverterException.class)
    public void getLongAndReverseArrayTestWithTooLongArray() throws ConverterException {
        byte[] array = new byte[]{4, 113, 2, 5, 5, 5, 5, 5, 5};
        BaseConverter.getLongAndReverseArray(array);
    }

    @Test(expected = ConverterException.class)
    public void getLongAndReverseArrayTestWithEmptyArray() throws ConverterException {
        byte[] array = new byte[]{};
        BaseConverter.getLongAndReverseArray(array);
    }
}