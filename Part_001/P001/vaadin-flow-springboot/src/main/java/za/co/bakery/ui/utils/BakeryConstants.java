package za.co.bakery.ui.utils;

public  class BakeryConstants {
    public static final String PAGE_STORE_FRONT = "storefront";
    public static final String ORDER_ID = "orderID";
    public static final String EDIT_SEGMENT = "edit";
    public static final String PAGE_ROOT = "";
    public static final String PAGE_STOREFRONT_ORDER_TEMPLATE = PAGE_STORE_FRONT + "/:" + ORDER_ID + "?" + "storefront/:orderID?";
    public static final String PAGE_STOREFRONT_ORDER_EDIT_TEMPLATE = PAGE_STORE_FRONT + "/:" + ORDER_ID + "/" + EDIT_SEGMENT;
}
