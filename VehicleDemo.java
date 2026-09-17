abstract class Vehicle {
    private final String registration;

    Vehicle(String registration) {
        if (registration == null || registration.trim().isEmpty()) {
            throw new IllegalArgumentException("Registration cannot be blank");
        }
        this.registration = registration;
    }

    public String getRegistration() {
        return registration;
    }

    public abstract double rent(int days);
}

class Bike extends Vehicle {
    Bike(String registration) {
        super(registration);
    }

    @Override
    public double rent(int days) {
        if (days < 0) throw new IllegalArgumentException("Days cannot be negative");
        return days * 300.0;
    }
}

class Car extends Vehicle {
    Car(String registration) {
        super(registration);
    }

    @Override
    public double rent(int days) {
        if (days < 0) throw new IllegalArgumentException("Days cannot be negative");
        double total = days * 1000.0;
        if (days > 5) {
            total += 500.0;
        }
        return total;
    }
}

class Fleet {
    public static double total(Vehicle[] vehicles, int days) {
        if (vehicles == null) throw new IllegalArgumentException("Vehicles cannot be null");
        if (days < 0) throw new IllegalArgumentException("Days cannot be negative");
        double sum = 0;
        for (Vehicle v : vehicles) {
            if (v == null) throw new IllegalArgumentException("Vehicle cannot be null");
            sum += v.rent(days);
        }
        return sum;
    }

    public static String costliest(Vehicle[] vehicles, int days) {
        if (vehicles == null || vehicles.length == 0) return "";
        if (days < 0) throw new IllegalArgumentException("Days cannot be negative");
        String best = null;
        double bestRent = -1;
        for (Vehicle v : vehicles) {
            if (v == null) throw new IllegalArgumentException("Vehicle cannot be null");
            double r = v.rent(days);
            if (r > bestRent) {
                bestRent = r;
                best = v.getRegistration();
            }
        }
        return best;
    }

    public static String summary(Vehicle[] vehicles, int days) {
        if (vehicles == null) throw new IllegalArgumentException("Vehicles cannot be null");
        if (days < 0) throw new IllegalArgumentException("Days cannot be negative");
        StringBuilder sb = new StringBuilder();
        for (Vehicle v : vehicles) {
            if (v == null) throw new IllegalArgumentException("Vehicle cannot be null");
            sb.append(v.getRegistration())
              .append(" ")
              .append(String.format("%.2f", v.rent(days)))
              .append("\n");
        }
        sb.append("TOTAL ")
          .append(String.format("%.2f", total(vehicles, days)));
        return sb.toString();
    }
}

public class VehicleDemo {
    public static void main(String[] args) {
        Vehicle[] fleet = {
            new Bike("BIKE-01"),
            new Car("CAR-01")
        };

        System.out.printf("Bike 2 days: %.2f%n", fleet[0].rent(2));
        System.out.printf("Car 6 days: %.2f%n", fleet[1].rent(6));
        System.out.println("Costliest: " + Fleet.costliest(fleet, 6));
        System.out.println(Fleet.summary(fleet, 6));
    }
}