package com.mariakh.converter;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Map;

public class BaseTest {

    private static final byte[] testArray = new byte[]{
            1, 0, 4, 0, -88, 50, -110, 86, 2, 0, 3, 0, 4, 113, 2, 3,
            0, 11, 0, -114, -114, -114, 32, -112, -82, -84, -96, -24,
            -86, -96, 4, 0, 29, 0, 11, 0, 7, 0, -124, -21, -32, -82,
            -86, -82, -85, 12, 0, 2, 0, 32, 78, 13, 0, 2, 0, 0, 2,
            14, 0, 2, 0, 64, -100, 4, 0, 29, 0, 11, 0, 7, 0, -124,
            -21, -32, -82, -86, -82, -85, 12, 0, 2, 0, 32, 78, 13,
            0, 2, 0, 0, 2, 14, 0, 2, 0, 64, -100
    };
    private static final byte[] testArrayWrongData = new byte[]{
            7, 0, 4, 0, -88, 50, -110, 86, 2, 0, 3, 0, 4, 113, 2, 3,
            0, 11, 0, -114, -114, -114, 32, -112, -82, -84, -96, -24,
            -86, -96, 4, 0, 38, 0, 11, 0, 9, 0, -124, -21, -32, -82,
            -86, -82, -85, 12, 12, 12, 0, 9, 0, 11, 11, 11, 11, 11, 11, 11, 32, 78, 13, 0, 2, 0, 0, 2,
            14, 0, 2, 0, 64, -100, 4, 0, 29, 0, 11, 0, 7, 0, -124,
            -21, -32, -82, -86, -82, -85, 12, 0, 2, 0, 32, 78, 13,
            0, 2, 0, 0, 2, 14, 0, 2, 0, 64, -100
    };
    public static final StreamDataConverter streamDataConverter = new StreamDataConverter(new ByteArrayInputStream(testArray));
    public static final StreamDataConverter streamWrongDataConverter = new StreamDataConverter(new ByteArrayInputStream(testArrayWrongData));

    public static List<byte[]> getItemsDataForConverter() {
        return streamDataConverter.getItemsData();
    }

    public static Map<Short, byte[]> getOrderDataForConverter() {
        return streamDataConverter.getOrderData();
    }

    public static List<byte[]> getItemsWrongDataForConverter() {
        return streamWrongDataConverter.getItemsData();
    }

    public static Map<Short, byte[]> getOrderWrongDataForConverter() {
        return streamWrongDataConverter.getOrderData();
    }
}

