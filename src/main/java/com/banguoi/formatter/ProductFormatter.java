package com.banguoi.formatter;

import com.banguoi.model.Product;
import com.banguoi.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Locale;

@Component
public class ProductFormatter implements Formatter<Product> {

    private ProductService productService;

    @Autowired
    public ProductFormatter(ProductService productService) {
        this.productService = productService;
    }
    @Override
    public Product parse(String text, Locale locale) throws ParseException {
        Long id = Long.parseLong(text);
        Product product = productService.findById(id);
        return product;
    }

    @Override
    public String print(Product object, Locale locale) {
        return object.toString();
    }
}
