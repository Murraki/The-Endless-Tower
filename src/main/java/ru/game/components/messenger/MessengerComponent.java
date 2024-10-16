package ru.game.components.messenger;

import lombok.Data;

@Data
public class MessengerComponent {

    private int choice;
    private StringBuilder text;

    public MessengerComponent() {
        this.text = new StringBuilder();
    }

}
