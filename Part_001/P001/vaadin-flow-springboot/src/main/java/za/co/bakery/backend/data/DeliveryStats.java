package za.co.bakery.backend.data;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeliveryStats {
    private int deliveredToday;
    private int dueToday;
    private int newOrders;
    private int dueTomorrow;
    private int notAvailableToday;
}
