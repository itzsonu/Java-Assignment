class Product {
    private final String name;
    private final double price;

    Product(String name, double price) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be blank");
        }
        if (price < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public double price() {
        return price;
    }

    public double price(double coupon) {
        if (coupon < 0) {
            throw new IllegalArgumentException("Coupon cannot be negative");
        }
        double discounted = price() - coupon;
        return discounted < 0 ? 0.0 : discounted;
    }
}

class DiscountedProduct extends Product {
    private final double discountPercent;

    DiscountedProduct(String name, double price, double discountPercent) {
        super(name, price);
        if (discountPercent < 0 || discountPercent > 100) {
            throw new IllegalArgumentException("Discount must be between 0 and 100");
        }
        this.discountPercent = discountPercent;
    }

    @Override
    public double price() {
        return getPrice() * (1 - discountPercent / 100.0);
    }
}

class Cart {
    private final Product[] products;
    private final int[] quantities;

    Cart(Product[] products, int[] quantities) {
        if (products == null || quantities == null) {
            throw new IllegalArgumentException("Products and quantities cannot be null");
        }
        if (products.length != quantities.length) {
            throw new IllegalArgumentException("Length mismatch");
        }
        this.products = products;
        this.quantities = quantities;
    }

    public double subtotal() {
        double sum = 0;
        for (int i = 0; i < products.length; i++) {
            if (products[i] == null) throw new IllegalArgumentException("Product cannot be null");
            if (quantities[i] <= 0) throw new IllegalArgumentException("Quantity must be positive");
            sum += products[i].price() * quantities[i];
        }
        return sum;
    }

    public double total(double coupon) {
        if (coupon < 0) throw new IllegalArgumentException("Coupon cannot be negative");
        double sub = subtotal();
        double after = sub - coupon;
        return after < 0 ? 0.0 : after;
    }

    public String receipt(double coupon) {
        if (coupon < 0) throw new IllegalArgumentException("Coupon cannot be negative");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < products.length; i++) {
            if (products[i] == null) throw new IllegalArgumentException("Product cannot be null");
            if (quantities[i] <= 0) throw new IllegalArgumentException("Quantity must be positive");
            double line = products[i].price() * quantities[i];
            sb.append(products[i].getName())
              .append(" x ")
              .append(quantities[i])
              .append(" = ")
              .append(String.format("%.2f", line))
              .append("\n");
        }
        sb.append("SUBTOTAL=").append(String.format("%.2f", subtotal())).append("\n");
        sb.append("COUPON=").append(String.format("%.2f", coupon)).append("\n");
        sb.append("TOTAL=").append(String.format("%.2f", total(coupon)));
        return sb.toString();
    }
}

public class ProductDemo {
    public static void main(String[] args) {
        Product regular = new Product("Regular", 100);
        Product discounted = new DiscountedProduct("Discounted", 200, 10);

        System.out.printf("regular with coupon 20: %.2f%n", regular.price(20));
        System.out.printf("discounted: %.2f%n", discounted.price());

        Product[] items = { regular, discounted };
        int[] qty = { 1, 1 };
        Cart cart = new Cart(items, qty);

        System.out.println();
        System.out.println(cart.receipt(20));
    }
}