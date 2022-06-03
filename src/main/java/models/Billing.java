package models;

import models.base.SqlColumn;
import models.base.SqlType;

import java.math.BigDecimal;
import java.sql.Date;

public class Billing {

    @SqlColumn(columnName = "id", type = SqlType.LONG)
    private Long id;

    @SqlColumn(columnName = "request_id", type = SqlType.LONG)
    private Long requestId;

    @SqlColumn(columnName = "price", type = SqlType.DECIMAL)
    private BigDecimal price;

    @SqlColumn(columnName = "pay_end_date", type = SqlType.DATE)
    private java.sql.Date payEndDate;

    @SqlColumn(columnName = "paid", type = SqlType.BOOLEAN)
    private Boolean paid = false;

    @SqlColumn(columnName = "room_registry_id", type = SqlType.LONG)
    private Long roomRegistryId;

    public Billing(Long requestId, BigDecimal price, Long roomRegistryId) {
        this.requestId = requestId;
        this.price = price;
        this.roomRegistryId = roomRegistryId;
    }

    public Billing() {
    }

    public Long getId() {return id;}

    public void setId(Long id) {this.id = id;}

    public Long getRequestId() {return requestId;}

    public void setRequestId(Long requestId) {this.requestId = requestId;}

    public BigDecimal getPrice() {return price;}

    public void setPrice(BigDecimal price) {this.price = price;}

    public Date getPayEndDate() {return payEndDate;}

    public void setPayEndDate(Date payEndDate) {this.payEndDate = payEndDate;}

    public Boolean getPaid() {return paid;}

    public void setPaid(Boolean paid) {this.paid = paid;}

    public Long getRoomRegistryId() {
        return roomRegistryId;
    }

    public void setRoomRegistryId(Long roomRegistryId) {
        this.roomRegistryId = roomRegistryId;
    }
}
