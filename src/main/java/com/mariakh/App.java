package com.mariakh;

import com.mariakh.converter.ItemConverter;
import com.mariakh.converter.OrderConverter;
import com.mariakh.converter.StreamDataConverter;
import com.mariakh.exception.ConverterException;
import com.mariakh.model.Item;
import com.mariakh.model.Order;

import java.io.*;
import java.util.*;

public class App {

    public static void main(String[] args) {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        FilePathReader filePathReader = new FilePathReader(reader);
        String sourceFileName = filePathReader.getSourceFilePath();

        try (DataInputStream inputStream = new DataInputStream(new BufferedInputStream(new FileInputStream(sourceFileName)))) {
            StreamDataConverter streamDataConverter = new StreamDataConverter(inputStream);
            Order order = new OrderConverter(streamDataConverter.getOrderData()).getOrder();
            List<Item> items = new ItemConverter(streamDataConverter.getItemsData()).getItems();
            order.setItems(items);
            JsonSaver.save(filePathReader.getOutputFile(), order);
            reader.close();
        } catch (IOException | ConverterException e) {
            System.out.println(e.getMessage());
        }
    }
}