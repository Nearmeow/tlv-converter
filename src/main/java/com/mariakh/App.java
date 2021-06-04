package com.mariakh;

import com.mariakh.converter.ItemConverter;
import com.mariakh.converter.OrderConverter;
import com.mariakh.exception.ConverterException;
import com.mariakh.model.Item;
import com.mariakh.model.Order;

import java.io.*;
import java.nio.file.Path;
import java.util.*;

public class App {

    public static void main(String[] args) {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        FilePathChecker filePathChecker = new FilePathChecker(reader);
        String sourceFileName = filePathChecker.getSourceFilePath();
        Path outputFile = filePathChecker.getOutputFile();

        try (DataInputStream inputStream = new DataInputStream(new BufferedInputStream(new FileInputStream(sourceFileName)))) {
            Order order = new OrderConverter(inputStream).getOrder();
            List<Item> items = new ItemConverter(inputStream).getItems();
            order.setItems(items);
            JsonSaver.save(outputFile, order);
            reader.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
//D:/test/data-1.bin
//D:/test/data-output.json