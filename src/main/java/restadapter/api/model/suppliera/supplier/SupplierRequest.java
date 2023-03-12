package restadapter.api.model.suppliera.supplier;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import restadapter.api.model.suppliera.orderline.SupplierRequestItem;

/** Purchase Request Model for Supplier A. */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class SupplierRequest {

  private String id;
  private List<SupplierRequestItem> items;
}
