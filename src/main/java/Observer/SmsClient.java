package Observer;

public class SmsClient extends Observer {
    @Override
    public void update() {
        System.out.println("Sms message");
    }
}
