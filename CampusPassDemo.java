abstract class CampusPass {
    private final String id;
    private final String holder;
    private final int months;

    CampusPass(String id, String holder, int months) {
        if (id == null || id.trim().isEmpty()) throw new IllegalArgumentException("ID cannot be blank");
        if (holder == null || holder.trim().isEmpty()) throw new IllegalArgumentException("Holder cannot be blank");
        if (months <= 0) throw new IllegalArgumentException("Months must be positive");
        this.id = id;
        this.holder = holder;
        this.months = months;
    }

    public String getId() { return id; }
    public String getHolder() { return holder; }
    public int getMonths() { return months; }

    public abstract double monthlyFee();

    public double totalFee() {
        return monthlyFee() * months;
    }
}

class HostelStudent extends CampusPass {
    HostelStudent(String id, String holder, int months) {
        super(id, holder, months);
    }
    @Override public double monthlyFee() { return 500.0; }
}

class DayScholar extends CampusPass {
    DayScholar(String id, String holder, int months) {
        super(id, holder, months);
    }
    @Override public double monthlyFee() { return 300.0; }
}

class Staff extends CampusPass {
    Staff(String id, String holder, int months) {
        super(id, holder, months);
    }
    @Override public double monthlyFee() { return 100.0; }
}

class CampusRegistry {

    public static boolean hasDuplicates(CampusPass[] passes) {
        if (passes == null) throw new IllegalArgumentException("Passes cannot be null");
        for (int i = 0; i < passes.length; i++) {
            if (passes[i] == null) throw new IllegalArgumentException("Pass cannot be null");
            for (int j = i + 1; j < passes.length; j++) {
                if (passes[j] == null) throw new IllegalArgumentException("Pass cannot be null");
                if (passes[i].getId().equals(passes[j].getId())) {
                    return true;
                }
            }
        }
        return false;
    }

    public static double totalCollection(CampusPass[] passes) {
        if (passes == null) throw new IllegalArgumentException("Passes cannot be null");
        double sum = 0;
        for (CampusPass p : passes) {
            if (p == null) throw new IllegalArgumentException("Pass cannot be null");
            sum += p.totalFee();
        }
        return sum;
    }

    public static String report(CampusPass[] passes) {
        if (passes == null) throw new IllegalArgumentException("Passes cannot be null");
        if (hasDuplicates(passes)) {
            throw new IllegalArgumentException("Duplicate pass IDs detected");
        }

        CampusPass[] sorted = new CampusPass[passes.length];
        for (int i = 0; i < passes.length; i++) {
            if (passes[i] == null) throw new IllegalArgumentException("Pass cannot be null");
            sorted[i] = passes[i];
        }
        for (int i = 1; i < sorted.length; i++) {
            CampusPass key = sorted[i];
            int j = i - 1;
            while (j >= 0 && sorted[j].getId().compareTo(key.getId()) > 0) {
                sorted[j + 1] = sorted[j];
                j--;
            }
            sorted[j + 1] = key;
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < sorted.length; i++) {
            CampusPass p = sorted[i];
            sb.append(p.getId())
              .append(" ")
              .append(p.getHolder())
              .append(" ")
                            .append(p.getMonths())
              .append("mo fee=")
              .append(String.format("%.2f", p.totalFee()));
            if (i < sorted.length - 1) sb.append("\n");
        }
        return sb.toString();
    }
}

public class CampusPassDemo {
    public static void main(String[] args) {
        CampusPass[] passes = {
            new HostelStudent("H1", "Alice", 1),
            new Staff("S1", "Bob", 3)
        };

        System.out.printf("Hostel fee: %.2f%n", passes[0].totalFee());
        System.out.printf("Staff fee: %.2f%n", passes[1].totalFee());
        System.out.printf("Collection=%.2f%n", CampusRegistry.totalCollection(passes));
        System.out.println();
        System.out.println(CampusRegistry.report(passes));
    }
}