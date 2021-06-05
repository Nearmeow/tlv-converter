package com.mariakh.converter;

import com.mariakh.exception.ConverterException;
import com.mariakh.model.Order;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.TimeZone;

public class OrderConverter extends BaseConverter {

    private final Map<Short, byte[]> orderData;

    public OrderConverter(Map<Short, byte[]> orderData) {
        this.orderData = orderData;
    }

    public Order getOrder() throws ConverterException {
        Order order = new Order();
        for (Map.Entry<Short, byte[]> entry : orderData.entrySet()) {
            byte[] tempBytes = entry.getValue();
            switch (entry.getKey()) {
                case 1:
                    String dateTime = getDateFromBytes(tempBytes);
                    order.setDateTime(dateTime);
                    break;
                case 2:
                    long orderNumber = getLongAndReverseArray(tempBytes);
                    order.setNumber(orderNumber);
                    break;
                case 3:
                    String customerName = new String(tempBytes, Charset.forName("cp866"));
                    stringValidation(customerName, 1000);
                    order.setCustomerName(customerName);
                    break;
                default:
                    throw new ConverterException("Wrong TLV structure. Unknown tag in order data.");
            }
        }
        return order;
    }

    private String getDateFromBytes(byte[] array) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(array);
        int data = byteBuffer.order(ByteOrder.LITTLE_ENDIAN).getInt();
        Date date = new Date((long) data * 1000);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        return dateFormat.format(date);
    }
}
