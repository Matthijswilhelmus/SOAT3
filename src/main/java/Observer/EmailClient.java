package Observer;

public class EmailClient extends Observer {
    @Override
    public void update() {
        System.out.println("Email message");
    }

}
