import java.util.ArrayList;
import java.util.Scanner;
import java.lang.Math;

class Main {
    private static String tournamentName = "";
    private static String player1 = "";
    private static String player2 = "";
    private static float maxSets = 0;
    private static Integer probWinPlayer1 = null;
    private static Integer probWinPlayer2 = null;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingresa los nombres de los jugadores.\n" +
                "Primer jugador: ");
        player1 = scanner.nextLine();
        System.out.println("Segundo jugador: ");
        player2 = scanner.nextLine();
        System.out.println("Nombre del torneo: ");
        tournamentName = scanner.nextLine();
        do {
            System.out.println("Cantidad de sets, al mejor de (3 / 5):");
            maxSets = Float.parseFloat(scanner.nextLine());
            if (maxSets != 3 && maxSets != 5) {
                System.out.println("Error. Escribiste " + maxSets + ". Debe escribir '3' o '5'");
            }
        } while (maxSets != 3 && maxSets != 5);
        do {
            System.out.println("Probabilidad de que cada jugador gane el partido (0 - 100): \n" +
                    "jugador " + player1 + ":");
            probWinPlayer1 = Integer.parseInt(scanner.nextLine());
            System.out.println("jugador " + player2 + ":");
            probWinPlayer2 = Integer.parseInt(scanner.nextLine());
            if (probWinPlayer1 + probWinPlayer2 != 100 || probWinPlayer1 < 0 || probWinPlayer2 < 0) {
                System.out.println("Error. Escribiste " + probWinPlayer1 + " y " + probWinPlayer2 + ".\n" +
                        " La suma debe ser 100, y probabilidades entre 0 y 100");
            }
        } while (probWinPlayer1 + probWinPlayer2 != 100 || probWinPlayer1 < 0 || probWinPlayer2 < 0);

        Boolean rematch = false;
        do {
            tournamentSimulation();
            String doRematch;
            do {
                System.out.println("Deseas jugar una revancha? (si / no): ");
                doRematch = scanner.nextLine();
                if (doRematch.equals("si")) {
                    rematch = true;
                }
                else if (doRematch.equals("no")) {
                    rematch = false;
                } else {
                    System.out.println("Error. Escribiste " + doRematch +
                            " Responde con 'si' o 'no'.");
                }
            } while (rematch == null);
        } while (rematch);

    }
    static boolean gameSimulation() {
        int scorePlayer1 = 0;
        int scorePlaye2 = 0;
        String pointsPlayer1 = "0";
        String pointsPlayer2 = "0";
        boolean winner = true;
        boolean endGame = false;

        while (endGame != true) {
            Double randomPoint = Math.random();
            String scoreboard = "";

            if (randomPoint * 100 < probWinPlayer1) {
                scorePlayer1++;

                if ((scorePlayer1 > 3) && (Math.abs(scorePlayer1 - scorePlaye2) > 1)) {
                    System.out.println(player1 + " gana el juego");
                    scorePlayer1 = 0;
                    scorePlaye2 = 0;
                    winner = true;
                    endGame = true;
                }
            } else {
                scorePlaye2++;

                if ((scorePlaye2 > 3) && (Math.abs(scorePlaye2 - scorePlayer1) > 1)) {
                    System.out.println(player2 + " gana el juego");
                    scorePlayer1 = 0;
                    scorePlaye2 = 0;
                    winner = false;
                    endGame = true;
                }
            }

            if (scorePlayer1 == 1) {
                pointsPlayer1 = "15";
                scoreboard = pointsPlayer1 + " - " + pointsPlayer2;
            }
            if (scorePlayer1 == 2) {
                pointsPlayer1 = "30";
                scoreboard = pointsPlayer1 + " - " + pointsPlayer2;
            }
            if (scorePlayer1 == 3) {
                pointsPlayer1 = "40";
                scoreboard = pointsPlayer1 + " - " + pointsPlayer2;
            }
            if (scorePlaye2 == 1) {
                pointsPlayer2 = "15";
                scoreboard = pointsPlayer1 + " - " + pointsPlayer2;
            }
            if (scorePlaye2 == 2) {
                pointsPlayer2 = "30";
                scoreboard = pointsPlayer1 + " - " + pointsPlayer2;
            }
            if (scorePlaye2 == 3) {
                pointsPlayer2 = "40";
                scoreboard = pointsPlayer1 + " - " + pointsPlayer2;
            }
            if (scorePlayer1 > 3 && scorePlaye2 > 3 && scorePlayer1 == scorePlaye2) {
                scoreboard = "deuce";
            }
            if (scorePlayer1 > 3 && scorePlayer1 > scorePlaye2) {
                pointsPlayer1 = "";
                pointsPlayer2 = "";
                scoreboard = "ventaja " + player1;
            }
            if (scorePlaye2 > 3 && scorePlaye2 > scorePlayer1) {
                pointsPlayer1 = "";
                pointsPlayer2 = "";
                scoreboard = "ventaja " + player2;
            }

            System.out.println(scoreboard);
        }
        return winner;
    }


    static void tournamentSimulation() {
        ArrayList<Integer> gamesScorePlayer1 = new ArrayList<>();
        ArrayList<Integer> gamesScorePlayer2 = new ArrayList<>();
        int gamesPlayer1 = 0;
        int gamesPlayer2 = 0;
        int setsPlayer1 = 0;
        int setsPlayer2 = 0;
        float set1 = (maxSets / 2);
        double set = set1 + 0.5;
        boolean endMatch = false;
        boolean startPlayer1 = Math.random() < 0.5;
        while (!endMatch) {
            if (startPlayer1) {
                System.out.println("Saca el jugador " + player1);
            } else {
                System.out.println("Saca el jugador " + player2);
            }
            startPlayer1 = ! startPlayer1;
            boolean gameResult = gameSimulation();
            if (gameResult) {
                gamesPlayer1++;
            } else {
                gamesPlayer2++;
            }

            if (gamesPlayer1 == 6 && gamesPlayer2 == 6) {
                System.out.println("Tie Break");
            }

            if (gamesPlayer1 >= 6 && (Math.abs(gamesPlayer1 - gamesPlayer2) > 1) || gamesPlayer1 == 7 && gamesPlayer2 == 6) {
                setsPlayer1 = setsPlayer1 + 1;
                gamesScorePlayer1.add(gamesPlayer1);
                gamesScorePlayer2.add(gamesPlayer2);
                System.out.println(player1 + " gana el set por " + gamesPlayer1 + " a " + gamesPlayer2 + "\n");
                gamesPlayer1 = 0;
                gamesPlayer2 = 0;
                if (setsPlayer1 == set && setsPlayer1 > setsPlayer2) {
                    String out = "";
                    for (Integer i : gamesScorePlayer1) {
                        out = out.concat(" " + i.toString());
                    }
                    System.out.println(player1 + out);
                    out = "";
                    for (Integer i : gamesScorePlayer2) {
                        out = out.concat(" " + i.toString());
                    }
                    System.out.println(player2 + out);
                    System.out.println(player1 + " gana el torneo " + tournamentName);
                    endMatch = true;
                }

            }
            if (gamesPlayer2 >= 6 && (Math.abs(gamesPlayer2 - gamesPlayer1) > 1) || gamesPlayer2 == 7 && gamesPlayer1 == 6) {
                setsPlayer2 = setsPlayer2 + 1;
                gamesScorePlayer1.add(gamesPlayer1);
                gamesScorePlayer2.add(gamesPlayer2);
                System.out.println(player2 + " gana el set por " + gamesPlayer2 + " a " + gamesPlayer1 + "\n");
                gamesPlayer1 = 0;
                gamesPlayer2 = 0;
                if (setsPlayer2 == set && setsPlayer2 > setsPlayer1) {
                    String out = "";
                    for (Integer i : gamesScorePlayer1) {
                        out = out.concat(" " + i.toString());
                    }
                    System.out.println(player1 + out);
                    out = "";
                    for (Integer i : gamesScorePlayer2) {
                        out = out.concat(" " + i.toString());
                    }
                    System.out.println(player2 + out);
                    System.out.println(player2 + " gana el torneo " + tournamentName);
                    endMatch = true;
                }
            }
        }
    }
}