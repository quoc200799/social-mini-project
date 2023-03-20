package entity;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.Queue;
import java.util.Stack;
import java.util.UUID;

public class Conversation implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private String id;
    private List<String> contacts;
    private Stack<Messenger> messengers;

    public Conversation(List<String> contacts, Stack<Messenger> messengers) {
        this.id = UUID.randomUUID().toString();
        this.contacts = contacts;
        this.messengers = messengers;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getContacts() {
        return contacts;
    }

    public void setContacts(List<String> contacts) {
        this.contacts = contacts;
    }

    public Stack<Messenger> getMessengers() {
        return messengers;
    }

    public void setMessengers(Stack<Messenger> messengers) {
        this.messengers = messengers;
    }
}
