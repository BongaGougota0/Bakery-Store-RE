package za.co.bakery.ui.views.storefront;

import com.vaadin.flow.data.renderer.LitRenderer;
import za.co.bakery.backend.data.entity.Order;
import za.co.bakery.backend.data.entity.OrderSummary;

import java.time.LocalDate;

public class OrderCard {
    private final OrderSummary order;
    private boolean recent, inWeek;
    public OrderCard(OrderSummary order) {
        this.order = order;
        LocalDate now = LocalDate.now();
        LocalDate date = order.getDueDate();
        recent = date.equals(now) || date.equals(now.minusDays(1));
    }

    public static LitRenderer<Order> getTemplate(){
        return LitRenderer.of("<order-card"
                + "  .header='${item.header}'"
                + "  .orderCard='${item.orderCard}'"
                + "  @card-click='${cardClick}'>"
                + "</order-card>");
    }

    public static OrderCard create(OrderSummary orderSummary){return  new OrderCard(orderSummary);}
}
