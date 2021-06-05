package com.mariakh.converter;

import com.mariakh.exception.ConverterException;
import com.mariakh.model.Item;

import java.nio.charset.Charset;
import java.util.*;

public class ItemConverter extends BaseConverter {

    private final List<byte[]> itemsData;

    public ItemConverter(List<byte[]> itemsByteArrayList) {
        this.itemsData = itemsByteArrayList;
    }

    public List<Item> getItems() throws ConverterException {
        List<Item> itemList = new ArrayList<>();
        for (byte[] itemBytes : itemsData) {
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

    private Item processOneItem(Map<Short, byte[]> itemMap) throws ConverterException {
        Item item = new Item();
        for (Map.Entry<Short, byte[]> entry : itemMap.entrySet()) {
            byte[] value = entry.getValue();
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
                default:
                    throw new ConverterException("Wrong TLV structure. Unknown tag in 'items' field.");
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
            throw new ConverterException("Wrong TLV structure. One of the numeric value in 'items' field is incorrect.");
        }
    }
}
