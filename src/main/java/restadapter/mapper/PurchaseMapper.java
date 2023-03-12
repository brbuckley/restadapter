package restadapter.mapper;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;
import restadapter.api.model.purchasems.orderline.PurchaseRequestItem;
import restadapter.api.model.purchasems.purchase.PurchaseRequest;
import restadapter.api.model.suppliera.orderline.SupplierRequestItem;
import restadapter.api.model.suppliera.supplier.SupplierRequest;

/** Parser for Purchase objects. */
@Component
public class PurchaseMapper {

  /**
   * Parses a Purchase Request into a Supplier Request.
   *
   * @param purchase Purchase Request.
   * @return Supplier Request.
   */
  public SupplierRequest toSupplierRequest(PurchaseRequest purchase) {
    List<SupplierRequestItem> items = new ArrayList<>();
    for (PurchaseRequestItem orderLine : purchase.getItems()) {
      items.add(new SupplierRequestItem(orderLine.getQuantity(), orderLine.getProduct().getId()));
    }
    return new SupplierRequest(purchase.getId(), items);
  }
}
