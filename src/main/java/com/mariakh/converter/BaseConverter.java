package com.mariakh.converter;

import com.mariakh.exception.ConverterException;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.HashMap;
import java.util.Map;

public class BaseConverter {

    private InputStream inputStream;
    private static ByteBuffer twoBytesBuffer = ByteBuffer.allocate(2);
    private static ByteBuffer eightBytesBuffer = ByteBuffer.allocate(8);

    public BaseConverter(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    protected Map<Short, byte[]> getTagsWithData(int iterationQuantity) {
        Map<Short, byte[]> dataMap = new HashMap<>();
        try {
            for (int i = 0; i < iterationQuantity; i++) {
                short tag = getTagOrLength();
                short length = getTagOrLength();
                byte[] dataBuffer = new byte[length];
                inputStream.read(dataBuffer);
                dataMap.put(tag, dataBuffer);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return dataMap;
    }

    protected long getLongAndReverseArray(byte[] value) throws ConverterException {
        if (value.length == 0 || value.length > 8) {
            throw new ConverterException("Wrong TLV structure.");
        }
        reverseByteArray(value);
        return getLongFromVlnValue(value);
    }

    protected short getTagOrLength() {
        byte[] buffer = new byte[2];
        twoBytesBuffer.clear();
        try {
            inputStream.read(buffer);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        twoBytesBuffer = ByteBuffer.wrap(buffer);
        return twoBytesBuffer.order(ByteOrder.LITTLE_ENDIAN).getShort();
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
