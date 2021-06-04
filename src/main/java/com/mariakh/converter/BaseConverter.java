package com.mariakh.converter;

import com.mariakh.exception.ConverterException;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.*;

public class BaseConverter {

    public Map<Short, byte[]> dataMap;
    public List<byte[]> itemsByteArrayList = new ArrayList<>();
    private final byte[] byteArray;
    private static ByteBuffer twoBytesBuffer = ByteBuffer.allocate(2);
    private static ByteBuffer eightBytesBuffer = ByteBuffer.allocate(8);

    public BaseConverter(InputStream inputStream) throws IOException {
        dataMap = new HashMap<>();
        byteArray = inputStream.readAllBytes();
        getTagsWithData();
    }

    public void getTagsWithData() {
        for (int i = 0; i < byteArray.length;) {
            short tag = getTagOrLength(byteArray, i);
            short length = getTagOrLength(byteArray, i + 2);
            if (tag != 4) {
                byte[] dataBuffer = Arrays.copyOfRange(byteArray, i + 4, i += 4 + length);
                dataMap.put(tag, dataBuffer);
            } else {
                itemsByteArrayList.add(Arrays.copyOfRange(byteArray, i + 4, i += 4 + length));
            }
        }
    }

    protected short getTagOrLength(byte[] array, int position) {
        twoBytesBuffer.clear();
        byte[] buffer = Arrays.copyOfRange(array, position, position + 2);
        twoBytesBuffer = ByteBuffer.wrap(buffer);
        return twoBytesBuffer.order(ByteOrder.LITTLE_ENDIAN).getShort();
    }

    protected long getLongAndReverseArray(byte[] value) throws ConverterException {
        if (value.length == 0 || value.length > 8) {
            throw new ConverterException("Wrong TLV structure.");
        }
        reverseByteArray(value);
        return getLongFromVlnValue(value);
    }

    protected long getLongFromVlnValue(byte[] value) {
        eightBytesBuffer.clear();
        for (int i = value.length; i < Long.BYTES; i++) {
            eightBytesBuffer.put((byte) 0);
        }
        eightBytesBuffer.put(value);
        return eightBytesBuffer.getLong(0);
    }

    protected void stringValidation(String string, int maxLength) throws ConverterException {
        if (string.length() > maxLength) {
            throw new ConverterException("Wrong TLV structure. One of the values is too long.");
        }
    }

    private void reverseByteArray(byte[] value) {
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
