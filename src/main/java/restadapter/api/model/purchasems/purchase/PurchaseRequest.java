package restadapter.api.model.purchasems.purchase;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.sql.Timestamp;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import restadapter.api.model.purchasems.orderline.PurchaseRequestItem;
import restadapter.api.model.purchasems.supplier.SupplierRequest;

/** OrderLineRequest Model. */
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class PurchaseRequest {

  @NotBlank
  @Pattern(regexp = "^PUR[0-9]{7}$")
  private String id;

  @NotBlank
  @Pattern(regexp = "^ORD[0-9]{7}$")
  @JsonProperty("order_id")
  private String orderId;

  @Valid @NotNull SupplierRequest supplier;

  @Pattern(regexp = "(processing|ordered)")
  private String status;

  @JsonFormat(pattern = "MM-dd-yyyy'T'HH:mm:ss.SSS'Z'")
  private Timestamp datetime;

  @NotEmpty private List<PurchaseRequestItem> items;

  /**
   * Custom Constructor.
   *
   * @param id Id.
   * @param orderId Order id.
   * @param supplier OrderLineRequest.
   * @param status Purchase status name.
   * @param datetime Timestamp.
   * @param items List of OrderLines.
   */
  public PurchaseRequest(
      String id,
      String orderId,
      SupplierRequest supplier,
      String status,
      Timestamp datetime,
      List<PurchaseRequestItem> items) {
    this.id = id;
    this.orderId = orderId;
    this.supplier = supplier;
    this.status = status;
    this.datetime = datetime == null ? null : new Timestamp(datetime.getTime());
    this.items = items;
  }

  public Timestamp getDatetime() {
    return this.datetime == null ? null : new Timestamp(this.datetime.getTime());
  }

  public void setDatetime(Timestamp datetime) {
    this.datetime = datetime == null ? null : new Timestamp(datetime.getTime());
  }
}
