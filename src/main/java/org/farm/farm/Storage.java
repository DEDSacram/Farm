package org.farm.farm;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Storage {
    private Map<Product, Integer> products;

    public Storage() {
        this.products = new HashMap<>();
    }

    public void addProduct(Product productName, int quantity) {
        products.put(productName, products.getOrDefault(productName, 0) + quantity);
    }

    public void removeProduct(Product productName, int quantity) {
        if (products.containsKey(productName)) {
            int remaining = products.get(productName) - quantity;
            if (remaining <= 0) products.remove(productName);
            else products.put(productName, remaining);
        }
    }

    public Map<Product, Integer> getProducts() {
        return products;
    }

    public Product getbyIndex(int index){

        List<Map.Entry<Product, Integer>> productList = new ArrayList<>(products.entrySet());
        Map.Entry<Product, Integer> entryAtIndex = productList.get(index);
        Product product = entryAtIndex.getKey();
        Integer quantity = entryAtIndex.getValue();
        return product;
    }

    // New method to print the contents of the storage
    public void printStorage() {
        if (products.isEmpty()) {
            System.out.println("Storage is empty.");
        } else {
            System.out.println("Storage contents:");
            for (Map.Entry<Product, Integer> entry : products.entrySet()) {
                Product product = entry.getKey();
                int quantity = entry.getValue();
                System.out.println(product.getName() + " - Quantity: " + quantity);
            }
        }
    }
}
