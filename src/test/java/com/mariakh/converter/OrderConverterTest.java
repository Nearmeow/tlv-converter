package com.mariakh.converter;

import com.mariakh.exception.ConverterException;
import com.mariakh.model.Order;
import org.junit.Test;

import static org.junit.Assert.*;

public class OrderConverterTest {

    OrderConverter orderConverter = new OrderConverter(BaseTest.getOrderDataForConverter());
    OrderConverter orderConverterWrongData = new OrderConverter(BaseTest.getOrderWrongDataForConverter());

    @Test
    public void getOrderTest() throws ConverterException {
        Order order = orderConverter.getOrder();
        assertEquals("ООО Ромашка", order.getCustomerName());
        assertEquals("2016-01-10T10:30:00", order.getDateTime());
        assertEquals(160004, order.getNumber());
    }

    @Test(expected = ConverterException.class)
    public void getOrderTestWrongData() throws ConverterException {
        orderConverterWrongData.getOrder();
    }
}