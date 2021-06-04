package com.mariakh.converter;

import com.mariakh.exception.ConverterException;
import com.mariakh.model.Item;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.*;

public class ItemConverter extends BaseConverter {

    public ItemConverter(InputStream inputStream) throws IOException {
        super(inputStream);
    }

    public List<Item> getItems() throws IOException {
        List<Item> itemList = new ArrayList<>();
        for (byte[] itemBytes : itemsByteArrayList) {
            Map<Short, byte[]> itemMap = new HashMap<>();
            for (int i = 0; i < itemBytes.length;) {
                short tag = getTagOrLength(itemBytes, i);
                short length = getTagOrLength(itemBytes, i + 2);
                byte[] dataBuffer = Arrays.copyOfRange(itemBytes, i + 4, i += 4 + length);
                itemMap.put(tag, dataBuffer);
            }
            Item newItem = processOneItem(itemMap);
            itemList.add(newItem);
        }
        return itemList;
    }

    /*public List<Item> getItems() throws IOException {
        List<Item> items = new ArrayList<>();
        while(inputStream.available() > 0) {
            short tag = getTagOrLength();
            short length = getTagOrLength();
            if (tag == 4) {
                Map<Short, byte[]> itemMap = getTagsWithData(4);
                items.add(processOneItem(itemMap));
            }
        }
        return items;
    }*/

    private Item processOneItem(Map<Short, byte[]> itemMap) {
        Item item = new Item();
        for (Map.Entry<Short, byte[]> entry : itemMap.entrySet()) {
            byte[] value = entry.getValue();
            try {
                switch (entry.getKey()) {
                    case 11:
                        String name = new String(value, Charset.forName("cp866"));
                        stringValidation(name, 200);
                        item.setName(name);
                        break;
                    case 12:
                        validateLongValueForItem(value);
                        long price = getLongAndReverseArray(value);
                        item.setPrice(price);
                        break;
                    case 13:
                        double quantity = getDoubleFromFvlnValue(value);
                        item.setQuantity(quantity);
                        break;
                    case 14:
                        validateLongValueForItem(value);
                        long sum = getLongAndReverseArray(value);
                        item.setSum(sum);
                        break;
                }
            } catch (ConverterException e) {
                System.out.println(e.getMessage());
            }
        }
        return item;
    }

    private double getDoubleFromFvlnValue(byte[] value) throws ConverterException {
        if (value.length == 0 || value.length > 8) {
            throw new ConverterException("Wrong TLV structure");
        }
        int charsNumberAfterDot = value[0] & 0xFF;
        byte[] arrWithoutFirstByte = new byte[value.length - 1];
        System.arraycopy(value, 1, arrWithoutFirstByte, 0, value.length - 1);
        long longValue = getLongFromVlnValue(arrWithoutFirstByte);
        double res;
        if (charsNumberAfterDot > 0) {
            int divider = (int) Math.pow(10, charsNumberAfterDot);
            res = (double) longValue / divider;
            return res;
        } else {
            return longValue;
        }
    }

    private void validateLongValueForItem(byte[] value) throws ConverterException {
        if (value.length > 6) {
            throw new ConverterException("Wrong TLV structure");
        }
    }
}
