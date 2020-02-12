package Adapter;


public class MessageAdapter implements MailNotifier {
    MessageNotifier messageNotifier;

    public MessageAdapter(MessageNotifier messageNotifier) {
        this.messageNotifier = messageNotifier;
    }

    @Override
    public void sendMail() {
        messageNotifier.sendMessage();
    }
}
