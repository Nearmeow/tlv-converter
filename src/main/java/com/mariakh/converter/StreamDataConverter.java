package com.mariakh.converter;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class StreamDataConverter extends BaseConverter {

    private Map<Short, byte[]> orderData;
    private List<byte[]> itemsData;
    private byte[] byteArray;

    public StreamDataConverter(InputStream inputStream) {
        try {
            byteArray = inputStream.readAllBytes();
            setOrderAndItemsData();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public Map<Short, byte[]> getOrderData() {
        return orderData;
    }

    public List<byte[]> getItemsData() {
        return itemsData;
    }

    private void setOrderAndItemsData() {
        orderData = new HashMap<>();
        itemsData = new ArrayList<>();
        for (int i = 0; i < byteArray.length;) {
            short tag = getTagOrLength(byteArray, i);
            short length = getTagOrLength(byteArray, i + 2);
            if (tag != 4) {
                byte[] dataBuffer = Arrays.copyOfRange(byteArray, i + 4, i += 4 + length);
                orderData.put(tag, dataBuffer);
            } else {
                itemsData.add(Arrays.copyOfRange(byteArray, i + 4, i += 4 + length));
            }
        }
    }
}
