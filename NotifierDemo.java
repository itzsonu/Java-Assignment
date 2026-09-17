interface Notifier {
    String send(String recipient, String message);
}

class EmailNotifier implements Notifier {
    @Override
    public String send(String recipient, String message) {
        return "EMAIL to " + recipient + ": " + message;
    }
}

class SmsNotifier implements Notifier {
    @Override
    public String send(String recipient, String message) {
        String truncated = message.length() > 20
            ? message.substring(0, 20)
            : message;
        return "SMS to " + recipient + ": " + truncated;
    }
}

class BulkSender {
    public static String[] bulkSend(Notifier notifier, String[] recipients, String message) {
        if (notifier == null) throw new IllegalArgumentException("Notifier cannot be null");
        if (recipients == null) throw new IllegalArgumentException("Recipients cannot be null");
        if (message == null) throw new IllegalArgumentException("Message cannot be null");

        String[] results = new String[recipients.length];
        for (int i = 0; i < recipients.length; i++) {
            String r = recipients[i];
            if (r == null || r.trim().isEmpty()) {
                throw new IllegalArgumentException("Recipient cannot be null or blank");
            }
            results[i] = notifier.send(r, message);
        }
        return results;
    }
}

public class NotifierDemo {
    public static void main(String[] args) {
        String[] recipients = { "a@x.com", "b@x.com" };
        String message = "Lab";

        Notifier email = new EmailNotifier();
        Notifier sms = new SmsNotifier();

        for (String line : BulkSender.bulkSend(email, recipients, message)) {
            System.out.println(line);
        }

        System.out.println("---");

        for (String line : BulkSender.bulkSend(sms, recipients, message)) {
            System.out.println(line);
        }

        System.out.println("---");

        String longMessage = "This message is definitely longer than twenty characters";
        for (String line : BulkSender.bulkSend(sms, recipients, longMessage)) {
            System.out.println(line);
        }
    }
}