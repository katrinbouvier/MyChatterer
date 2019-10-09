package com.company;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String statement;
        Chatterer bot = new Chatterer();

        //TODO: ИСПОЛЬЗОВАТЬ ИМЯ СОБЕСЕДНИКА В ДИАЛОГЕ
        System.out.println("Как мне следут к вам обращаться?");
        String name = sc.nextLine();

        do {
            statement = sc.nextLine();
            System.out.println(bot.answer(statement, name)+"\n");
        } while(!statement.equals("stop"));
    }
}
