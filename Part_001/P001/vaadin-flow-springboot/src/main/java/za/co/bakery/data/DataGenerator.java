package za.co.bakery.data;

import com.vaadin.flow.spring.annotation.SpringComponent;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import za.co.bakery.app.HasLogger;
import za.co.bakery.backend.data.OrderState;
import za.co.bakery.backend.data.Role;
import za.co.bakery.backend.data.entity.Customer;
import za.co.bakery.backend.data.entity.HistoryItem;
import za.co.bakery.backend.data.entity.Order;
import za.co.bakery.backend.data.entity.OrderItem;
import za.co.bakery.backend.data.entity.PickupLocation;
import za.co.bakery.backend.data.entity.Product;
import za.co.bakery.backend.data.entity.User;
import za.co.bakery.backend.repository.OrderRepository;
import za.co.bakery.backend.repository.PickUpLocationRepository;
import za.co.bakery.backend.repository.ProductRepository;
import za.co.bakery.backend.repository.UserRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

@SpringComponent
public class DataGenerator implements HasLogger {
    private static final String[] FILLING = new String[]{"Pudding", "Strawberry", "BlueBerry", "Raspberry"};
    private static final String[] TYPE = new String[]{"Cake", "Muffin", "Biscuit", "Bread", "Pastry" ,"Tart",
            "Cheese Cake", "Cookie", "Craker", "Bun", "Brownie","Albany","Pastecioo","Noodles","Milk","Cheeso-o"};
    private static final String[] FIRST_NAMES = new String[]{"Bonga", "Thando", "Daemon", "Olwethu","Chris","June", "April","Noluthando","Mandla","Adam"};
    private static final String[] LAST_NAMES = new String[]{"Gougota","Chalmat","Gongotha","Moses","Bityeri","Khumalo","Moyo", "Diko", "Mdluli","Nkosi"};
    private static final Random random = new Random(1L);

    private OrderRepository orderRepository;
    private UserRepository userRepository;
    private ProductRepository productRepository;
    private PickUpLocationRepository pickUpLocation;
    private PasswordEncoder passwordEncoder;
    @Autowired
    public DataGenerator(OrderRepository orderRepository, UserRepository userRepository,
                         ProductRepository productRepository, PickUpLocationRepository pickUpLocationRepository,
                         PasswordEncoder passwordEncoder){
        this.orderRepository = orderRepository;
        this.pickUpLocation = pickUpLocationRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void loadData(){
        if(userRepository.count() != 0L){
            getLogger().info("User the existing databse");
            return;
        }

        getLogger().info("Run this method to insert demo data.");

        getLogger().info("Admin user created.");
        User admin = createAdmin(userRepository,passwordEncoder);
        User waiter = createWaiter(userRepository,passwordEncoder);
        User baker = createBaker(userRepository,passwordEncoder);
        createAdminUser(userRepository,passwordEncoder);
        createDeletableUsers(userRepository,passwordEncoder);

        getLogger().info("Add products......");
        Supplier<Product> productSupplier = createProducts(productRepository,8);
        //A set of products without relationships
        createProducts(productRepository, 4);

        getLogger().info("Create pick up locations.....");
        Supplier<PickupLocation> pickUpLocationSupplier = createPickUpLocations(pickUpLocation);

        getLogger().info("Generate orders....");
        createOrders(orderRepository, productSupplier, pickUpLocationSupplier, waiter, baker);
    }

    private User createWaiter(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return userRepository.save(createUser("waiter@mail.com","Manno","Coom",
                passwordEncoder.encode("123456778"),Role.WAITER,true));
    }

    private void createDeletableUsers(UserRepository userRepository, PasswordEncoder passwordEncoder){
        userRepository.save(createUser("timmy@mail.com","tim","Myy",
                passwordEncoder.encode("123456789"),Role.BAKER,false ));
        userRepository.save(createUser("Amanda@mail.com","AManda","Ndukula",
                passwordEncoder.encode("123456789"),Role.BAKER, false));
    }

    private User createBaker(UserRepository userRepository, PasswordEncoder passwordEncoder){
        return userRepository.save(createUser("baker@mail.com","Sipho","Matheba",
                passwordEncoder.encode("123456789"),Role.BAKER,false));
    }

    private User createAdmin(UserRepository repository, PasswordEncoder passwordEncoder){
        return repository.save(createUser("admin@mail.com","Bonga","Gougota",
                passwordEncoder.encode("@ZXCp0001"),Role.ADMIN,false));
    }

    private void createOrders(OrderRepository orderRepository,
                                         Supplier<Product> productSupplier,
                                         Supplier<PickupLocation> pickUpLocationSupplier,
                                         User adminUser, User user) {

        int yearsToInclude = 2;
        LocalDate now = LocalDate.now();
        LocalDate oldestDate = LocalDate.of(now.getYear() - yearsToInclude, 1, 1);
        LocalDate newestDate = now.plusMonths(1L);

        Order order = createOrder(productSupplier, pickUpLocationSupplier, adminUser, user, now);
        order.setDueTime(LocalTime.of(8, 0));
        order.setHistoryItems(order.getHistoryItems().subList(0, 1));
        order.setItemList(order.getOrderItems().subList(0,1));
        orderRepository.save(order);

        for (LocalDate dueDate = oldestDate; dueDate.isBefore(newestDate); dueDate = dueDate.plusDays(1)) {
            int relativeYear = dueDate.getYear() - now.getYear() + yearsToInclude;
            int relativeMonth = relativeYear * 12 + dueDate.getMonthValue();
            double multiplier = 1.0 + 0.03 * relativeMonth;
            int ordersThisDay = (int) (random.nextInt(10) + 1 * multiplier);
            for (int i = 0; i < ordersThisDay; i++) {
                orderRepository.save(createOrder(productSupplier, pickUpLocationSupplier,
                        adminUser, user, dueDate));
            }
        }
    }

    private Order createOrder(Supplier<Product> productSupplier,
                              Supplier<PickupLocation> pickUpLocationSupplier,
                              User adminUser, User user, LocalDate now) {
        Order order = new Order(adminUser);

        fillCustomer(order.getCustomer());
        order.setPickUpLocation(pickUpLocationSupplier.get());
        order.setDueDate(now);
        order.setDueTime(getRandomDueTime());
        order.changeState(adminUser, getRandomState(order.getDueDate()));

        int itemCount = random.nextInt(3);
        List<OrderItem> items = new ArrayList<>();

        for(int i = 0; i <= itemCount; i++){
            OrderItem item = new OrderItem();
            Product product;
            do{
                product = productSupplier.get();
            } while (containsProduct(items, product));
            item.setProduct(product);
            item.setQuantity(random.nextInt()+1);
            if(random.nextInt(5) == 0){
                if(random.nextBoolean()){
                    item.setComment("Lactose free");
                }
                else {
                    item.setComment("Gluten Free");
                }
            }
            items.add(item);
        }
        order.setItemList(items);
        order.setHistoryItems(createOrderHistory(order, adminUser, user));
        return order;
    }

    private List<HistoryItem> createOrderHistory(Order order, User user, User baker){
        List<HistoryItem> orderHistoryItems = new ArrayList<>();
        HistoryItem item = new HistoryItem(user, "order placed");
        item.setOrderState(OrderState.NEW);
        LocalDateTime orderPlaced = order.getDueDate().minusDays(random.nextInt(5)+2L)
                .atTime(random.nextInt(10)+7, 00);
        item.setTimeStamp(orderPlaced);
        orderHistoryItems.add(item);
        if(order.getOrderState() == OrderState.CANCELLED){
            item = new HistoryItem(user, "Order cancelled");
            item.setNewState(OrderState.CANCELLED);
            item.setTimeStamp(orderPlaced.plusDays(random.nextInt(
                    (int)orderPlaced.until(order.getDueDate().atTime(order.getDueTime()), ChronoUnit.DAYS))));
        } else if (order.getOrderState() == OrderState.CONFIRMED || order.getState() == OrderState.DELIVERED
                || order.getState() == OrderState.PROBLEM || order.getState() == OrderState.READY){
            item = new HistoryItem(user, "Order Confirmed");
            item.setNewState(OrderState.CONFIRMED);
            item.setTimeStamp(orderPlaced.plusDays(random.nextInt(2)).plusHours(random.nextInt(5)));
            orderHistoryItems.add(item);

            if(order.getState() == OrderState.PROBLEM){
                item = new HistoryItem(user,"Cant fulfill this order at them moment");
                item.setNewState(OrderState.PROBLEM);
                item.setTimeStamp(order.getDueDate().atTime(random.nextInt(4)+4,0));
                orderHistoryItems.add(item);
            } else if (order.getState() == OrderState.READY || order.getState() == OrderState.DELIVERED) {
                item = new HistoryItem(user, "Order Ready for pickup");
                item.setNewState(OrderState.READY);
                item.setTimeStamp(order.getDueDate().atTime(random.nextInt(2)+8, random.nextBoolean() ? 0 :30));
                orderHistoryItems.add(item);
                if(order.getOrderState() == OrderState.DELIVERED){
                    item = new HistoryItem(user,"Order deliverred");
                    item.setNewState(OrderState.DELIVERED);
                    item.setTimeStamp(order.getDueDate().atTime(order.getDueTime().minusMinutes(random.nextInt(120))));
                    orderHistoryItems.add(item);
                }
            }
        }

        return orderHistoryItems;
    }

    private boolean containsProduct(List<OrderItem> items, Product product){
        for(OrderItem item: items){
            if(item.getProduct() == product){
                return true;
            }
        }
        return false;
    }

    public OrderState getRandomState(LocalDate dueDate){
        LocalDate today = LocalDate.now();
        LocalDate tomorrow = today.plusDays(2);
        LocalDate twoDays = today.plusDays(2);

        if(dueDate.isBefore(today)){
            if(random.nextDouble() < 0.9){
                return OrderState.DELIVERED;
            }else {
                return OrderState.CANCELLED;
            }
        } else {
            if(dueDate.isAfter(twoDays)){
                return OrderState.NEW;
            } else if (dueDate.isAfter(tomorrow)) {
                double resolution = random.nextDouble();
                if(resolution < 0.8){
                    return  OrderState.NEW;
                } else if (resolution < 0.9) {
                    return OrderState.PROBLEM;
                } else {
                    return OrderState.CANCELLED;
                }
            } else {
                double resolution = random.nextDouble();
                if(resolution < 0.6){
                    return OrderState.READY;
                } else if (resolution < 0.8) {
                    return OrderState.DELIVERED;
                } else if (resolution < 0.9) {
                    return  OrderState.PROBLEM;
                } else {
                    return OrderState.CANCELLED;
                }
            }
        }
    }


    public void fillCustomer(Customer customer){
        String firstName = getRandom(FIRST_NAMES);
        String lastName = getRandom(LAST_NAMES);
        customer.setFullName(firstName + " "+lastName);
        customer.setPhoneNumber(getRandomPhone());
        if(random.nextInt(10) == 0){
            customer.setDetails("New customer");
        }
    }

    public LocalTime getRandomDueTime(){
        int time = 8+4*random.nextInt(3);
        return LocalTime.of(time,0);
    }

    public String getRandomPhone(){
        return "+2776-385-"+String.format("%04d", random.nextInt(10000));
    }
    private Supplier<PickupLocation> createPickUpLocations(PickUpLocationRepository pickUpLocation) {
        List<PickupLocation> pickUpLocationList = Arrays.asList(
                pickUpLocation.save(createPickUpLocation("Boxer")),
                pickUpLocation.save(createPickUpLocation("Makro")));
                return () -> pickUpLocationList.get(random.nextInt(pickUpLocationList.size()));
    }

    private Supplier<Product> createProducts(ProductRepository productRepository, int numberOfItems){
        List<Product> products = new ArrayList<>();
        for(int i = 0; i < numberOfItems; i++){
            Product product = new Product();
            product.setName(getRandomProductName());
            double doublePrice = 2.0 * random.nextDouble()*100;
            product.setPrice((int)(doublePrice*100));
            products.add(productRepository.save(product));
        }
        return () -> {
            double cutOff = 2.5;
            double g = random.nextGaussian();
            g = Math.min(cutOff, g);
            g = Math.max(-cutOff, g);
            g += cutOff;
            g /= (cutOff *2);
            return products.get((int)(g*products.size()-1));
        };
    }

    private Supplier<PickupLocation> pickUpLocations(PickUpLocationRepository pickUpLocationRepository){
        List<PickupLocation> pickUpLocations = Arrays.asList(
                pickUpLocationRepository.save(createPickUpLocation("Store")),
                pickUpLocationRepository.save(createPickUpLocation("Pick-Up"))
        );
        return () -> pickUpLocations.get(random.nextInt(pickUpLocations.size()-1));
    }

    private PickupLocation createPickUpLocation(String name){
        PickupLocation pickUpLocation = new PickupLocation();
        pickUpLocation.setName(name);
        return pickUpLocation;
    }

    private <T> T getRandom(T[] arrayStrings){
        return arrayStrings[random.nextInt(arrayStrings.length)];
    }

    private String getRandomProductName(){
        String firstFilling = getRandom(FILLING);
        String name;
        if(random.nextBoolean()){
            String secondFilling;
            do{
                secondFilling = getRandom(FILLING);
            }
            while (secondFilling.equals(firstFilling));
                name = firstFilling + " " + secondFilling;
        }
        else{
            name = firstFilling;
        }
        name += " " +getRandom(TYPE);
        return name;
    }

    private User createUser(String email, String firstName, String lastName,
                            String password, String userRole, boolean locked){
        User user = new User();
        user.setEmail(email);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setHashedPassword(password);
        user.setRole(userRole);
        user.setLocked(locked);
        return user;
    }

    private User createAdminUser(UserRepository userRepository, PasswordEncoder passwordEncoder){
        return userRepository.save(createUser("bonga@admin.com","Bonga", "Gougota",
                passwordEncoder.encode("@ZXCp0001"), "admin",true));
    }

}
