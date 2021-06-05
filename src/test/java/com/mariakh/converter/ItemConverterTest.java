package com.mariakh.converter;

import com.mariakh.exception.ConverterException;
import com.mariakh.model.Item;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ItemConverterTest {

    ItemConverter itemConverter = new ItemConverter(BaseTest.getItemsDataForConverter());
    ItemConverter itemConverterWrongData = new ItemConverter(BaseTest.getItemsWrongDataForConverter());

    @Test
    public void getItemsTest() throws ConverterException {
        List<Item> itemList = itemConverter.getItems();
        assertEquals(2, itemList.size());
        assertEquals("Дырокол", itemList.get(1).getName());
        assertEquals(20000, itemList.get(1).getPrice());
        assertEquals(40000, itemList.get(1).getSum());

    }

    @Test(expected = ConverterException.class)
    public void getItemsTestWrongData() throws ConverterException {
        itemConverterWrongData.getItems();
    }
}