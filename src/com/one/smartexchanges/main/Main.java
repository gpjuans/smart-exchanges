package com.one.smartexchanges.main;

import com.one.smartexchanges.models.*;
import com.one.smartexchanges.operators.*;

import java.util.Locale;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        UsersOperator usersOperator = new UsersOperator();
        final String START_MENU =
                """
                        ******************************\s
                        \tSMART-EXCHANGES\s
                        ******************************\s

                        Please, select an option\s

                        1 -> Create user
                        2 -> Sign in
                        9 -> Exit
                """;
        int userSelectionStartMenu =0;
        final String USER_MENU =
                """
                        ******************************\s
                        \tSMART-EXCHANGES\s
                        ******************************\s

                        Please, select an option\s

                        1 -> Create consult
                        2 -> Consults history
                        3 -> Log out
                        9 -> Exit
                """;
        int userSelectionUserMenu;
        Scanner userInputs = new Scanner(System.in);
        String username;
        String password;
        double valueBase;

        final String CURRENCY_CODES =
                """
                        ARS-Argentine Peso      AUD-Australian Dollar       BOB-Bolivian Boliviano      BRL-Brazilian Real
                        CAD-Canadian Dollar     CLP-Chilean Peso            CNY-Chinese Renminbi        COP-Colombian Peso
                        CRC-Costa Rican Colon   CUP-Cuban Peso              DOP-Dominican Peso          EUR-Euro
                        GBP-Pound Sterling      GTQ-Guatemalan Quetzal      GYD-Guyanese Dollar         HKD-Hong Kong Dollar
                        HNL-Honduran Lempira    MXN-Mexican Peso            NIO-Nicaraguan Córdoba      PAB-Panamanian Balboa
                        PEN-Peruvian Sol        USD-United States Dollar    UYU-Uruguayan Peso          VES-Venezuelan Bolívar Soberano
                """;

        while (userSelectionStartMenu != 9) {
            userSelectionUserMenu = 0;
            System.out.println(START_MENU);
            try {
                userSelectionStartMenu = Integer.parseInt(userInputs.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input " + e.getMessage());
            }

            switch (userSelectionStartMenu) {
                case 1:
                    System.out.println("Enter an username");
                    username = userInputs.nextLine().trim();
                    System.out.println("Enter a password");
                    password = userInputs.nextLine().trim();
                    boolean userCreated = usersOperator.createUser(username,password);
                    if (userCreated){
                        System.out.println("User created");
                    }else{
                        System.out.println("Username not available");
                    }
                    break;

                case 2:
                    if (!usersOperator.getUsers().isEmpty()){
                        System.out.println("Enter your username");
                        username = userInputs.nextLine().trim();
                        System.out.println("Enter your password");
                        password = userInputs.nextLine().trim();
                        User userActive = usersOperator.singIn(username, password);
                        if (userActive == null) {
                            System.out.println("Invalid credentials\n" +
                                    "Your session has not been started");
                        }else {
                            System.out.println("Session started");
                            while (userSelectionUserMenu != 9) {
                                System.out.println(USER_MENU);
                                try {
                                    userSelectionUserMenu = Integer.parseInt(userInputs.nextLine());
                                } catch (NumberFormatException e) {
                                    System.out.println("Invalid input " + e.getMessage());
                                }

                                switch (userSelectionUserMenu) {
                                    case 1:
                                        try {
                                            System.out.println("Write value to exchange");
                                            valueBase = Double.parseDouble(userInputs.nextLine().trim());
                                            System.out.println("CURRENCYS CODES\n" +
                                                    CURRENCY_CODES);
                                            System.out.println("Write base currency code");
                                            String baseCurrency = userInputs.nextLine().trim().toUpperCase(Locale.ROOT);
                                            System.out.println("Write target currency code");
                                            String targetCurrency = userInputs.nextLine().trim().toUpperCase(Locale.ROOT);

                                            ExchangeConsultor exchangeConsultor = new ExchangeConsultor();
                                            Exchange exchange = exchangeConsultor.exchangeCurrency(valueBase, baseCurrency, targetCurrency);
                                            Consult consult = new Consult(exchange);
                                            userActive.addConsult(consult);
                                            System.out.println(consult);

                                        } catch (NumberFormatException e) {
                                            System.out.println("Invalid input " + e.getMessage());
                                            break;
                                        } catch (RuntimeException e) {
                                            System.out.println("Currency not found " + e.getMessage());
                                            break;
                                        }
                                        break;

                                    case 2:
                                        System.out.println(
                                                """
                                                ------------------------------
                                                \tConsults history
                                                ------------------------------
                                                """);
                                        System.out.println("Consults made: " + userActive.getConsultHistory().size() + "\n");
                                        System.out.println(userActive.getConsultHistory());
                                        break;

                                    case 3:
                                        usersOperator.logOut(userActive);
                                        System.out.println("Closed session");
                                        userSelectionUserMenu = 9;
                                        break;

                                    case 9:
                                        usersOperator.logOut(userActive);
                                        userSelectionStartMenu = 9;
                                        System.out.println("Closed session");
                                        System.out.println("Exiting the system");
                                        break;

                                    default:
                                        System.out.println("Enter a valid option");
                                        break;
                                }
                            }
                        }
                    }else {
                        System.out.println("Without users registered");
                    }
                    break;

                case 9:
                    System.out.println("Exiting the system");
                    break;

                default:
                    System.out.println("Enter a valid option");
                    break;
            }
        }
    }
}