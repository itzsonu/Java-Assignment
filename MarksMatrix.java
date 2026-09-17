public class MarksMatrix {

    public static void main(String[] args) {
        int[][] marks = {
            {80, 70, 90},
            {60, 55, 65},
            {90, 30, 95}
        };

        int[] totals = totals(marks);
        double[] averages = subjectAverages(marks);
        int topper = topperIndex(marks);
        String[] codes = resultCodes(marks);

        System.out.println(java.util.Arrays.toString(totals));
        System.out.println(java.util.Arrays.toString(averages));
        System.out.println(topper);
        System.out.println(java.util.Arrays.toString(codes));
    }

    public static int[] totals(int[][] marks) {
        if (marks == null || marks.length == 0) {
            return new int[0];
        }

        int subjects = marks[0].length;
        int[] totals = new int[marks.length];

        for (int i = 0; i < marks.length; i++) {
            if (marks[i] == null || marks[i].length != subjects) {
                throw new IllegalArgumentException("Malformed matrix: row " + i);
            }
            int sum = 0;
            for (int j = 0; j < subjects; j++) {
                int mark = marks[i][j];
                if (mark < 0 || mark > 100) {
                    throw new IllegalArgumentException("Mark out of range: " + mark);
                }
                sum += mark;
            }
            totals[i] = sum;
        }

        return totals;
    }

    public static double[] subjectAverages(int[][] marks) {
        if (marks == null || marks.length == 0) {
            return new double[0];
        }

        int subjects = marks[0].length;
        double[] sums = new double[subjects];

        for (int i = 0; i < marks.length; i++) {
            if (marks[i] == null || marks[i].length != subjects) {
                throw new IllegalArgumentException("Malformed matrix: row " + i);
            }
            for (int j = 0; j < subjects; j++) {
                int mark = marks[i][j];
                if (mark < 0 || mark > 100) {
                    throw new IllegalArgumentException("Mark out of range: " + mark);
                }
                sums[j] += mark;
            }
        }

        double[] averages = new double[subjects];
        for (int j = 0; j < subjects; j++) {
            averages[j] = round2(sums[j] / marks.length);
        }
        return averages;
    }

    public static int topperIndex(int[][] marks) {
        if (marks == null || marks.length == 0) {
            return -1;
        }

        int[] totals = totals(marks);
        int best = 0;
        for (int i = 1; i < totals.length; i++) {
            if (totals[i] > totals[best]) {
                best = i;
            }
        }
        return best;
    }

    public static String[] resultCodes(int[][] marks) {
        if (marks == null || marks.length == 0) {
            return new String[0];
        }

        int subjects = marks[0].length;
        String[] codes = new String[marks.length];

        for (int i = 0; i < marks.length; i++) {
            if (marks[i] == null || marks[i].length != subjects) {
                throw new IllegalArgumentException("Malformed matrix: row " + i);
            }

            boolean failed = false;
            int sum = 0;
            for (int j = 0; j < subjects; j++) {
                int mark = marks[i][j];
                if (mark < 0 || mark > 100) {
                    throw new IllegalArgumentException("Mark out of range: " + mark);
                }
                if (mark < 40) {
                    failed = true;
                }
                sum += mark;
            }

            if (failed) {
                codes[i] = "F";
            } else {
                double average = (double) sum / subjects;
                if (average >= 75) {
                    codes[i] = "D";
                } else if (average >= 60) {
                    codes[i] = "M";
                } else {
                    codes[i] = "P";
                }
            }
        }

        return codes;
    }

    private static double round2(double value) {
        return Math.round(value * 100.0) / 100.0;
    }
}