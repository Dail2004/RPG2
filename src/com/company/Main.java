package com.company;

import java.util.Random;

public class Main {

    public static int bossHealth = 900;
    public static int bossDamage = 50;
    public static String bossDefenceType = "";
    public static int[] heroesHealth = {260, 250, 270, 210, 370, 230, 250, 260};
    public static int[] heroesDamage = {20, 25, 15, 0, 10, 20, 25, 30};
    public static String[] heroesAttackType = {"Physical", "Magical", "Kinetic", "Medical", "Golem", "Lucky", "Berserk", "Thor"};
    public static void main(String[] args) {
        printStatistics();
        while (!isGameFinished()) { // !false = true / !true = false
            round();
        }
    }

    public static void round() {
        if (bossHealth > 0) {
            chooseBossDefence();
            bossHits();
        }
        heroesHit();
        heal();
        printStatistics();
        golem();
        lucky();
        berserk();
        thor();
    }

    public static void heal() {
        int index = 0;
        for (String a:heroesAttackType) {
            if (a == "Medical"){
                if (heroesHealth[index] > 0){
                    for (int i = 0; i < heroesHealth.length; i++) {
                        if (heroesHealth[i] <100 && heroesHealth[i] > 0){
                            Random random = new Random();
                            int healRandom = random.nextInt(50)+1;
                            heroesHealth[i]=heroesHealth[i]+healRandom;
                            System.out.println(heroesAttackType[index] + " вылечил: " + heroesAttackType[i]);
                            break;
                        }
                    }
                }
            }
            index++;
        }
    }

    //Добавить n-го игрока, Golem, который имеет увеличенную жизнь но слабый удар.
    //Может принимать на себя 1/5 часть урона исходящего от босса по другим игрокам
    public static void golem() {
        int index = 0;
        for (String name:heroesAttackType) {
            if (name == "Golem"){
                if (heroesHealth[index] > 0){
                    heroesHealth[index] = heroesHealth[index] - bossDamage;
            }
            }
        }index++;
    }

    public static void lucky(){
        int index = 0;
        for (String b:heroesAttackType) {
            if (b == "Lucky"){
                if (heroesHealth[index] > 0) {
                    Random random = new Random();
                    int lucky = random.nextInt(3);
                    if (lucky == 2){
                        heroesHealth[index]=heroesHealth[index]+50;
                        System.out.println(heroesAttackType[index] + " увернулся от босса ");
                        break;
                    }

                }
            }
        index++;
        }
    }
//Добавить n-го игрока, Berserk, блокирует часть удара босса по себе и прибавляет
//заблокированный урон к своему урону и возвращает его боссу
    public static void berserk(){
        int index = 0;
        for (String ber:heroesAttackType) {
            if (ber == "Berserk"){
                if (heroesHealth[index] > 0){
                    Random random = new Random();
                    int сounterattack = random.nextInt(51);
                    heroesHealth[index]=heroesHealth[index] + сounterattack;
                    heroesDamage[index]=heroesDamage[index] + сounterattack;
                    System.out.println(heroesAttackType[index]+" контратаковал босса: " + сounterattack);
                    break;
                }
            }
        }
        index++;
    }
//Добавить n-го игрока, Thor, удар по боссу имеет шанс оглушить босса на 1 раунд,
//вследствие чего босс пропускает 1 раунд и не наносит урон героям. //
//random.nextBoolean(); - true, false
    public static void thor(){
        int index = 0;
        for (String thor:heroesAttackType) {
            if (thor=="Thor"){
                if (heroesHealth[index]>0){
                    Random random = new Random();
                    boolean shock = random.nextBoolean();
                    if (shock == true){
                        
                        System.out.println(heroesAttackType[index] + " Тор заглушил босса");
                        break;
                    }
                }
            }
        }
        index++;
    }

    public static void chooseBossDefence() {
        Random random = new Random();
        int randomIndex = random.nextInt(heroesAttackType.length); // 0, 1, 2
        bossDefenceType = heroesAttackType[randomIndex];
        System.out.println("Boss chose defence: " + bossDefenceType);
    }

    public static boolean isGameFinished() {
        if (bossHealth <= 0) {
            System.out.println("Heroes won!!!");
            return true;
        }
        boolean allHeroesDead = true;
        for (int j : heroesHealth) {
            if (j > 0) {
                allHeroesDead = false;
                break;
            }
        }
        if (allHeroesDead) {
            System.out.println("Boss won!!!");
        }
        return allHeroesDead;
    }

    public static void heroesHit() {
        for (int i = 0; i < heroesDamage.length; i++) {
            if (heroesHealth[i] > 0 && bossHealth > 0) {
                if (bossDefenceType == heroesAttackType[i]) {
                    Random random = new Random();
                    int coeff = random.nextInt(8) + 2; // 2, 3, 4, 5, 6, 7, 8, 9
                    bossHealth = checkHealth(bossHealth - heroesDamage[i] * coeff);
                    System.out.println(heroesAttackType[i] + " hits boss critically " + heroesDamage[i] * coeff);
                } else {
                    bossHealth = checkHealth(bossHealth - heroesDamage[i]);
                }
            }
        }
    }

    public static void bossHits() {
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0) {
                heroesHealth[i] = checkHealth(heroesHealth[i] - bossDamage);
            }
        }
    }

    public static int checkHealth(int health) {
        if (health < 0) {
            return 0;
        } else {
            return health;
        }
    }

    public static void printStatistics() {
        System.out.println("________________");
        System.out.println("Boss health: " + bossHealth + " [" + bossDamage + "]");
        for (int i = 0; i < heroesHealth.length; i++) {
            System.out.println(heroesAttackType[i] + " health: " + heroesHealth[i] + " [" + heroesDamage[i] + "]");
        }
        System.out.println("________________");
    }
}
