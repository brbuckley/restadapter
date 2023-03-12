package restadapter;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import restadapter.api.model.purchasems.orderline.PurchaseRequestItem;
import restadapter.api.model.purchasems.product.ProductRequest;
import restadapter.api.model.purchasems.purchase.PurchaseRequest;
import restadapter.api.model.purchasems.purchase.PurchaseResponse;
import restadapter.api.model.purchasems.supplier.SupplierRequest;

public class AdapterResponseUtil {

  public static PurchaseRequest defaultPurchaseRequest() {
    List<PurchaseRequestItem> items = new ArrayList<>();
    items.add(defaultOrderLineRequest());
    items.add(anotherOrderLineRequest());
    return new PurchaseRequest(
        "PUR0000001",
        "ORD0000001",
        defaultSupplierRequest(),
        "pending",
        Timestamp.valueOf("2022-07-29 12:16:00"),
        items);
  }

  public static PurchaseRequestItem defaultOrderLineRequest() {
    return new PurchaseRequestItem(1, defaultProductRequest());
  }

  public static PurchaseRequestItem anotherOrderLineRequest() {
    return new PurchaseRequestItem(2, anotherProductRequest());
  }

  public static ProductRequest defaultProductRequest() {
    return new ProductRequest("PRD0000001", "Heineken", new BigDecimal("10"), "beer");
  }

  public static ProductRequest anotherProductRequest() {
    return new ProductRequest("PRD0000002", "Amstel", new BigDecimal("4.99"), "beer");
  }

  public static SupplierRequest defaultSupplierRequest() {
    return new SupplierRequest("SUP0000001", "Supplier A");
  }

  public static PurchaseResponse defaultPurchaseResponse() {
    return new PurchaseResponse("PUR0000001");
  }
}
