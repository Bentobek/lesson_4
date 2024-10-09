import java.util.Random;

public class main2 {
    public static int bossHealth = 700;
    public static int bossDamage = 50;
    public static String bossDefence;
    public static int[] heroesHealth = {280, 270, 240, 400, 260};
    public static int[] heroesDamage = {20, 15, 10, 0, 15};
    public static String[] heroesAttackType = {"Physical", "Magical", "Kinetic", "Medic", "Lucky"};
    public static int medicHealAmount = 35;
    public static int luckyDodge = 40;
    public static int roundNumber;

    public static void main(String[] args) {
        printStatistics();
        while (!isGameOver()) {
            playRound();
        }
    }

    public static void playRound() {
        roundNumber++;
        chooseBossDefence();
        bossAttacks();
        heroesAttack();
        medicHeals();
        printStatistics();
    }

    public static boolean isGameOver() {
        if (bossHealth <= 0) {
            System.out.println("Heroes won!!!");
            return true;
        }
        boolean allHeroesDead = true;
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0) {
                allHeroesDead = false;
                break;
            }
        }
        if (allHeroesDead) {
            System.out.println("Boss won!!!");
            return true;
        }
        return false;
    }

    public static void chooseBossDefence() {
        Random random = new Random();
        int randomInd = random.nextInt(heroesAttackType.length - 1);
        bossDefence = heroesAttackType[randomInd];
    }

    public static void heroesAttack() {
        for (int i = 0; i < heroesDamage.length - 1; i++) {
            if (heroesHealth[i] > 0 && bossHealth > 0) {
                int damage = heroesDamage[i];
                if (heroesAttackType[i].equals(bossDefence)) {
                    Random random = new Random();
                    int coefficient = random.nextInt(9) + 2;  // 2,3,4,5,6,7,8,9,10
                    damage *= coefficient;
                    System.out.println("Critical damage: " + damage);
                }
                if (bossHealth - damage < 0) {
                    bossHealth = 0;
                } else {
                    bossHealth = bossHealth - damage;
                }
            }
        }
    }

    public static void bossAttacks() {
        Random random = new Random();
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0) {
                if (heroesAttackType[i].equals("Lucky")) {
                    int dodgeChance = random.nextInt(100);
                    if (dodgeChance < luckyDodge) {
                        System.out.println("Lucky dodged the attack!");
                        continue;
                    }
                }
                 if (heroesHealth[i] - bossDamage < 0) {
                    heroesHealth[i] = 0;
                } else {
                    heroesHealth[i] = heroesHealth[i] - bossDamage;
                }
            }
        }
    }

    public static void medicHeals() {
        if (heroesHealth[3] > 0) {
            for (int i = 0; i < heroesHealth.length - 1; i++) {
                if (heroesHealth[i] > 0 && heroesHealth[i] < 100) {
                    heroesHealth[i] += medicHealAmount;
                    if (heroesHealth[i] > 100) {
                        heroesHealth[i] = 100;
                    }
                    System.out.println("Medic healed " + heroesAttackType[i] + " for " + medicHealAmount + " health points.");
                    break;
                }
            }
        }
    }

    public static void printStatistics() {
        System.out.println("ROUND " + roundNumber + " ------------");
        System.out.println("BOSS health: " + bossHealth + " damage: " + bossDamage +
                " defence: " + (bossDefence == null ? "No Defence" : bossDefence));
        for (int i = 0; i < heroesHealth.length; i++) {
            System.out.println(heroesAttackType[i] + " health: " + heroesHealth[i]
                    + " damage: " + heroesDamage[i]);
        }
    }
}


