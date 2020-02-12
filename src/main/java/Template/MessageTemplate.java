package Template;

public abstract class MessageTemplate {

    final void sendMessage() {
        determineAddressInformation();
        setAddressInformation();
        setMessageBody();
        send();
    }

    abstract void determineAddressInformation();

    abstract void setAddressInformation();

    void setMessageBody() {
        // implementation here
    }

    void send() {
    }
}
