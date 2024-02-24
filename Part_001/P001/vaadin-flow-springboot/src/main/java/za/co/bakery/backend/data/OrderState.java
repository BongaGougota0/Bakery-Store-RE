package za.co.bakery.backend.data;

import com.vaadin.flow.shared.util.SharedUtil;

import java.util.Locale;

public enum OrderState {
    NEW, CONFIRMED, DELIVERED, PROBLEM, CANCELLED;
    public String getDisplayName(){return SharedUtil.capitalize(name().toLowerCase(Locale.ENGLISH));
    };
}
