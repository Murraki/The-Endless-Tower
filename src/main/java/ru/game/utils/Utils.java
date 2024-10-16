package ru.game.utils;

import lombok.Data;
import lombok.experimental.UtilityClass;
import ru.game.enums.messenger.MessengerTextColor;
import ru.game.service.messenger.MessengerService;

import java.util.Random;
import java.util.Scanner;

@Data
@UtilityClass
public class Utils {

    public Scanner newScanner() {
        return new Scanner(System.in);
    }

    public Random newRandom() {
        return new Random();
    }

    public int getIntInput(int min, int max) {
        Scanner scanner = newScanner();
        int choice;
        while (true) {
            try {
                MessengerService
                        .messenger(MessengerTextColor.YELLOW)
                        .text("Введите число от " + min + " до " + max + ": ")
                        .print();

                choice = Integer.parseInt(scanner.nextLine());
                if (choice >= min && choice <= max) {
                    return choice;
                } else {
                    MessengerService
                            .messenger(MessengerTextColor.RED)
                            .text("Пожалуйста, введите число в диапазоне от " + min + " до " + max + ".")
                            .newLine()
                            .print();
                }
            } catch (NumberFormatException e) {
                MessengerService
                        .messenger(MessengerTextColor.RED)
                        .text("Пожалуйста, введите корректное целое число.")
                        .newLine()
                        .print();
            }
        }
    }

}
