package ru.geekbrains.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.geekbrains.repr.ProductRepr;
import ru.geekbrains.service.model.LineItem;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CartServiceTest {

    private CartService cartService;

    @BeforeEach
    public void init() {
        cartService = new CartServiceImpl();
    }

    @Test
    public void testAddProductAndQty() {
        ProductRepr expectedProduct = new ProductRepr();
        expectedProduct.setId(1L);
        expectedProduct.setName("SomeProduct");
        expectedProduct.setPrice(new BigDecimal(22348));
        expectedProduct.setCategoryName("SomeCategory");

        cartService.addProductQty(expectedProduct, "someColor", "someMaterial", 1);

        List<LineItem> lineItemList = cartService.getLineItems();
        assertNotNull(lineItemList);

        LineItem lineItem = lineItemList.get(0);
        assertNotNull(lineItem);
        assertEquals("someColor", lineItem.getColor());
        assertEquals("someMaterial", lineItem.getMaterial());
        assertEquals(1, lineItem.getQty());

        assertNotNull(lineItem.getProductRepr());
        assertEquals(expectedProduct.getId(), lineItem.getProductId());
        assertEquals(expectedProduct.getName(), lineItem.getProductRepr().getName());
        assertEquals(expectedProduct.getPrice(), lineItem.getProductRepr().getPrice());
    }

    @Test
    public void testAddQty() {

        ProductRepr expectedProduct = new ProductRepr();
        expectedProduct.setId(1L);

        cartService.addProductQty(expectedProduct, "someColor", "someMaterial", 5);
        cartService.addProductQty(expectedProduct, "someColor", "someMaterial", 5);

        List<LineItem> lineItemList = cartService.getLineItems();

        LineItem lineItem = lineItemList.get(lineItemList.size() - 1);
        assertEquals(10, lineItem.getQty());
    }

    @Test
    public void testRemoveProduct() {

        ProductRepr expectedProduct = new ProductRepr();

        cartService.addProductQty(expectedProduct, "someColor", "someMaterial", 5);

        List<LineItem> lineItemList = cartService.getLineItems();

        LineItem lineItem = lineItemList.get(lineItemList.size() - 1);

        cartService.removeProduct(lineItem);

        assertFalse(cartService.getLineItems().contains(lineItem));
    }

    @Test
    public void testRemoveProductQty() {

        ProductRepr expectedProduct = new ProductRepr();
        expectedProduct.setId(1L);

        cartService.addProductQty(expectedProduct, "someColor", "someMaterial", 5);

        List<LineItem> lineItemList = cartService.getLineItems();

        LineItem lineItem = lineItemList.get(lineItemList.size() - 1);

        cartService.removeProductQty(expectedProduct, lineItem.getColor(), lineItem.getMaterial(), 4);

        assertEquals(1, cartService.getLineItems().get(0).getQty());

        cartService.removeProductQty(expectedProduct, lineItem.getColor(), lineItem.getMaterial(), 4);

        assertFalse(cartService.getLineItems().contains(lineItem));
    }

    @Test
    public void testGetSubTotal() {

        ProductRepr testProduct1 = new ProductRepr();
        testProduct1.setPrice(new BigDecimal(1440));
        ProductRepr testProduct2 = new ProductRepr();
        testProduct2.setPrice(new BigDecimal(434));

        cartService.addProductQty(testProduct1,"red", "wood", 2); // 2 * 1440 = 2880
        cartService.addProductQty(testProduct2, "blue", "steel", 3); // 3 * 434 = 1302

        List<LineItem> lineItemList = cartService.getLineItems();
        assertEquals(new BigDecimal(4182), cartService.getSubTotal());

    }
}
