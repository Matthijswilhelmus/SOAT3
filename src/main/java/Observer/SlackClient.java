package Observer;

public class SlackClient extends Observer {
    @Override
    public void update() {
        System.out.println("Slack message");
    }
}
