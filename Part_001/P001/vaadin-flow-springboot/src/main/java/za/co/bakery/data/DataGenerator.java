package za.co.bakery.data;

import com.vaadin.flow.spring.annotation.SpringComponent;
import jakarta.annotation.PostConstruct;
import org.springframework.security.crypto.password.PasswordEncoder;
import za.co.bakery.app.HasLogger;
import za.co.bakery.backend.data.Role;
import za.co.bakery.backend.data.entity.Order;
import za.co.bakery.backend.data.entity.PickUpLocation;
import za.co.bakery.backend.data.entity.Product;
import za.co.bakery.backend.data.entity.User;
import za.co.bakery.backend.repository.OrderRepository;
import za.co.bakery.backend.repository.PickUpLocationRepository;
import za.co.bakery.backend.repository.ProductRepository;
import za.co.bakery.backend.repository.UserRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

@SpringComponent
public class DataGenerator implements HasLogger {
    private static final String[] FILLING = new String[]{"Pudding", "Strawberry", "BlueBerry", "Raspberry"};
    private static final String[] TYPE = new String[]{"Cake", "Muffin", "Biscuit", "Bread", "Pastry" ,"Tart", "Cheese Cake", "Cookie", "Craker", "Bun", "Brownie"};
    private static final String[] FIRST_NAMES = new String[]{"Bonga", "Thando", "Daemon", "Olwethu","Chris","June", "April","Noluthando","Mandla","Adam"};
    private static final String[] LAST_NAMES = new String[]{"Gougota","Chalmat","Gongotha","Moses","Bityeri","Khumalo","Moyo", "Diko", "Mdluli","Nkosi"};
    private static final Random random = new Random(1L);

    private OrderRepository orderRepository;
    private UserRepository userRepository;
    private ProductRepository productRepository;
    private PickUpLocationRepository pickUpLocation;
    private PasswordEncoder passwordEncoder;

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
        getLogger().info("Run this method to insert demo data.");

        getLogger().info("Admin user created.");
        User adminUser = createAdminUser(userRepository,passwordEncoder);
        User user = createUser("bongaxaba98@outlook.com", "Bonga","Gougota",
                "12345678", "user",false);

        getLogger().info("Add products......");
        Supplier<Product> productSupplier = createProducts(productRepository,10);

        getLogger().info("Create pick up locations.....");
        Supplier<PickUpLocation> pickUpLocationSupplier = createPickUpLocations(pickUpLocation);

        getLogger().info("Generate orders....");
        createOrders(orderRepository, productSupplier, pickUpLocationSupplier, adminUser, user);
    }

    private void createOrders(OrderRepository orderRepository,
                                         Supplier<Product> productSupplier,
                                         Supplier<PickUpLocation> pickUpLocationSupplier,
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

    private Order createOrder(Supplier<Product> productSupplier, Supplier<PickUpLocation> pickUpLocationSupplier, User adminUser, User user, LocalDate now) {
    }

    private Supplier<PickUpLocation> createPickUpLocations(PickUpLocationRepository pickUpLocation) {
        List<PickUpLocation> pickUpLocationList = Arrays.asList(
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
            product.setPrice((int)doublePrice);
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

    private Supplier<PickUpLocation> pickUpLocations(PickUpLocationRepository pickUpLocationRepository){
        List<PickUpLocation> pickUpLocations = Arrays.asList(
                pickUpLocationRepository.save(createPickUpLocation("Store")),
                pickUpLocationRepository.save(createPickUpLocation("Pick-Up"))
        );
        return () -> pickUpLocations.get(random.nextInt(pickUpLocations.size()-1));
    }

    private PickUpLocation createPickUpLocation(String name){
        PickUpLocation pickUpLocation = new PickUpLocation();
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
        name = " " +getRandom(TYPE);
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
