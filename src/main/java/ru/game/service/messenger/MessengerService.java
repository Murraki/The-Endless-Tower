package ru.game.service.messenger;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.game.components.messenger.MessengerComponent;
import ru.game.enums.messenger.MessengerTextColor;

@Data
@AllArgsConstructor
public class MessengerService {

    private MessengerComponent component;

    private static final int TEXT_SPEED = 42;

    public static MessengerService messenger(MessengerTextColor color) {
        MessengerService messenger = new MessengerService(new MessengerComponent());
        if (color != null) {
            messenger.component.getText().append(color.getStartCode());
        }
        return messenger;
    }

    public static MessengerService messenger() {
        return messenger(null);
    }

    public MessengerService text(String text, Object... arguments) {
        for (Object next : arguments) {
            text = text.replaceFirst("\\{}", next.toString());
        }
        this.component.getText().append(text);
        return this;
    }

    public void print() {
        String txt = component.getText().toString();
        for (int i = 0; i < txt.length(); i++) {
            char ch = txt.toCharArray()[i];
            System.out.print(ch);
            try {
                Thread.sleep(TEXT_SPEED);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException(e);
            }
        }
    }

    public MessengerService newLine() {
        this.component.getText().append("\n");
        return this;
    }

    public MessengerService end() {
        this.component.getText().append(MessengerTextColor.ANSI_RESET);
        return this;
    }

}
