package com.mariakh.converter;

import com.mariakh.exception.ConverterException;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.*;

public class BaseConverter {

    protected static short getTagOrLength(byte[] array, int position) {
        byte[] buffer = Arrays.copyOfRange(array, position, position + 2);
        ByteBuffer byteBuffer = ByteBuffer.wrap(buffer);
        return byteBuffer.order(ByteOrder.LITTLE_ENDIAN).getShort();
    }

    protected static long getLongAndReverseArray(byte[] value) throws ConverterException {
        if (value.length == 0 || value.length > 8) {
            throw new ConverterException("Wrong TLV structure. One of the numeric value is incorrect.");
        }
        reverseByteArray(value);
        return getLongFromVlnValue(value);
    }

    protected static long getLongFromVlnValue(byte[] value) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(Long.BYTES);
        for (int i = value.length; i < Long.BYTES; i++) {
            byteBuffer.put((byte) 0);
        }
        byteBuffer.put(value);
        return byteBuffer.getLong(0);
    }

    protected static void stringValidation(String string, int maxLength) throws ConverterException {
        if (string.length() > maxLength) {
            throw new ConverterException("Wrong TLV structure. One of the string values is too long.");
        }
    }

    private static void reverseByteArray(byte[] value) {
        int i = 0;
        int j = value.length - 1;
        byte tmp;
        while (j > i) {
            tmp = value[j];
            value[j] = value[i];
            value[i] = tmp;
            j--;
            i++;
        }
    }
}
