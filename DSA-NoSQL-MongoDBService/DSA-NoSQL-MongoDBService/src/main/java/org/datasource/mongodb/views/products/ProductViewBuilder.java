package org.datasource.mongodb.views.products;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.datasource.mongodb.MongoDataSourceConnector;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductViewBuilder {

    // Data cache
    private List<ProductView> productViewList = new ArrayList<>();

    public List<ProductView> getProductViewList() {
        return productViewList;
    }

    // Builder workflow — acelasi pattern ca DepartamentViewBuilder.java
    public ProductViewBuilder build() throws Exception {
        return this.select();
    }

    public ProductViewBuilder select() throws Exception {
        MongoDatabase db = dataSourceConnector.getMongoDatabase();

        MongoCollection<Document> productsCollection =
                db.getCollection("products");

        this.productViewList = new ArrayList<>();

        for (Document doc : productsCollection.find().limit(500)) {
            Integer productId = doc.getInteger("product_id");
            String productName = doc.getString("product_name");
            String category = doc.getString("category");

            // FIX: price poate fi Integer sau Double in MongoDB
            // Folosim Number in loc de getDouble direct
            Double price = 0.0;
            Object priceObj = doc.get("price");
            if (priceObj instanceof Double) {
                price = (Double) priceObj;
            } else if (priceObj instanceof Integer) {
                price = ((Integer) priceObj).doubleValue();
            } else if (priceObj instanceof Number) {
                price = ((Number) priceObj).doubleValue();
            }

            Integer stockQuantity = doc.getInteger("stock_quantity");
            String brand = doc.getString("brand");

            this.productViewList.add(new ProductView(
                productId, productName, category,
                price, stockQuantity, brand
            ));
        }

        return this;
    }

    // Dependency injection — acelasi pattern ca DepartamentViewBuilder.java linia 32
    private MongoDataSourceConnector dataSourceConnector;

    public ProductViewBuilder(MongoDataSourceConnector dataSourceConnector) {
        this.dataSourceConnector = dataSourceConnector;
    }
}
