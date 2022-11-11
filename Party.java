import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.io.File;
import java.util.Scanner;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author esaddler3
 * @version 1.0
 *          Class of methods that allow reading and writing to database.
 */
public class Party {
    /**
     * Method that reads file contents and returns an array list of party members.
     * @param file String representing file name.
     * @return an array list of party members.
     * @throws FileNotFoundException an exception when a file is unable to be found.
     */
    public static ArrayList<PartyMember> recruitParty(String file) throws FileNotFoundException {
        ArrayList members = new ArrayList<PartyMember>();
        try {
            File contents = new File(file);
            Scanner scanner = new Scanner(contents);
            while (scanner.hasNext()) {
                members.add(processInfo(scanner.nextLine()));
            }
        } catch (FileNotFoundException e) {
            System.out.println("Inputed file not found.");
        }
        return members;
    }

    /**
     * processes information of a string with information on a party member.
     * @param line String of member information to be processed.
     * @return a Party Member created with the string information.
     */
    private static PartyMember processInfo(String line) {
        Scanner scanner = new Scanner(line);
        String[] lineArr = scanner.nextLine().split(", ");
        // for (String token : lineArr) System.out.println(token);
        String type = lineArr[0].substring(7, lineArr[0].length());
        if (type.equals("Warrior")) {
            String name = lineArr[1].substring(6, lineArr[1].length());
            String strHealth = lineArr[2].substring(8, lineArr[2].length());
            int health = Integer.parseInt(strHealth);
            String strAttack = lineArr[3].substring(13, lineArr[3].length());
            int attack = Integer.parseInt(strAttack);
            String weapon = lineArr[4].substring(8, lineArr[4].length());
            String strArmor = lineArr[5].substring(13, lineArr[5].length());
            int armor = Integer.parseInt(strArmor);
            Warrior member = new Warrior(name, health, attack, weapon, armor);
            System.out.println(name + health + attack + weapon + armor);
            return member;
        } else if (type.equals("Mage")) {
            String name = lineArr[1].substring(6, lineArr[1].length());
            String strHealth = lineArr[2].substring(8, lineArr[2].length());
            int health = Integer.parseInt(strHealth);
            String strAttack = lineArr[3].substring(13, lineArr[3].length());
            int attack = Integer.parseInt(strAttack);
            String strSpell = lineArr[4].substring(14, lineArr[4].length());
            int spell = Integer.parseInt(strSpell);
            String strSlots = lineArr[5].substring(13, lineArr[5].length());
            int slots = Integer.parseInt(strSlots);
            Mage member = new Mage(name, health, attack, spell, slots);
            return member;
        } else {
            throw new InvalidPartyMemberException();
        }
    }

    /**
     * Writes the contents of an array list of party members to a file.
     * @param fileName name of file to be written to.
     * @param members  array list of party members.
     * @return a boolean representing if anything was written to the file.
     */
    public static boolean partyRoster(String fileName, ArrayList<PartyMember> members) {
        boolean writeCheck = false;
        try {
            File file = new File(fileName);
            PrintWriter writer = new PrintWriter(file);
            for (PartyMember member : members) {
                if (member == null) {
                    continue;
                } else if (writeCheck) {
                    writer.print("\n");
                }
                if (member.getClass().getName().equals("Warrior")) {
                    Warrior warrior = (Warrior) member;
                    writer.print(warrior.toString());
                    writeCheck = true;
                } else if (member.getClass().getName().equals("Mage")) {
                    Mage mage = (Mage) member;
                    writer.print(mage.toString());
                    writeCheck = true;
                }
            }
            writer.close();
            return writeCheck;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return writeCheck;
        }
    }

    /**
     * A method that checks if a quest is within the quest file, and removes it if
     * it is successful.
     * @param name    String of the quest name to be completed.
     * @param members Array list of party members.
     * @return a boolean if the quest was completed.
     * @throws QuestNotFoundException exception thrown if the quest was not found in
     *                                the file.
     * @throws FileNotFoundException  exception thrown if the file is not found.
     */
    public static boolean getQuest(String name, ArrayList<PartyMember> members)
            throws QuestNotFoundException, FileNotFoundException {
        try {
            File file = new File("quest.csv");
            Scanner scanner = new Scanner(file);
            ArrayList<String> lines = new ArrayList<String>();
            while (scanner.hasNextLine()) {
                lines.add(scanner.nextLine());
            }
            String questName;
            String questLevel;
            String reward;
            String[] first = new String[2]; // Okay: 10, 15
            String[] second = new String[2];
            for (String line : lines) {
                String[] csvSplit = line.split(": "); // first is name, second is csv of questLevel, reward
                String[] questLevelReward = csvSplit[1].split(", ");
                questLevel = questLevelReward[0];
                reward = questLevelReward[1];
                questName = csvSplit[0];
                if (questName.equals(name)) {
                    if (Integer.parseInt(questLevel) < partyQuestLevel(members)) {
                        System.out.printf("Success! Your party gained %s coins. This calls for a trip to the Tavern!",
                                reward);
                        PrintWriter writer = new PrintWriter(file);
                        for (String x : lines) {
                            if (!x.contains(questName)) {
                                writer.println(x);
                            }
                        }
                        writer.close();
                        return true;
                    } else {
                        System.out.println("Failure... Your party was defeated. Better Luck Next Time!");
                        return false;
                    }
                }
            }
            throw new QuestNotFoundException("Quest was not found!");
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("Quest file not found.");
        }
    }

    /**
     * Method that calculates the total quest level of a party.
     * @param members Array list of members whose level wants to be calculated.
     * @return an int representing the party level.
     */
    private static int partyQuestLevel(ArrayList<PartyMember> members) {
        if (members.isEmpty()) {
            return -1;
        }
        int total = 0;
        for (PartyMember member : members) {
            total += member.questLevel();
        }
        return total;
    }

    /**
     * Main method to test construction of objects and methods.
     * @param args not used.
     */
    public static void main(String[] args) {
        ArrayList<PartyMember> members = new ArrayList<PartyMember>();
        Mage mage = new Mage("Wizard", 100, 2, 3, 4);
        Mage mage1 = new Mage("MagicMan", 100, 2, 3, 4);
        Mage mage2 = new Mage("MagicWoman", 100, 2, 3, 2);
        Warrior warrior = new Warrior("Barb", 100, 1, "Sword", 2);
        Warrior warrior1 = new Warrior("Knight", 100, 4, "Knife", 1);
        Warrior warrior2 = new Warrior("Hulk", 100, 5, "Fist", 3);
        try {
            PrintWriter writer = new PrintWriter("TestParty.csv");
            writer.println(mage.toString() + "\n");
            writer.println(mage1.toString() + "\n");
            writer.println(mage2.toString() + "\n");
            writer.println(warrior.toString() + "\n");
            writer.println(warrior1.toString() + "\n");
            writer.println(warrior2.toString() + "\n");
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
        try {
            ArrayList<PartyMember> party = recruitParty("TestParty.csv");
            for (PartyMember member : party) {
                System.out.println(member.toString());
            }
            Mage mage3 = null;
            members.add(mage);
            members.add(mage1);
            members.add(mage2);
            members.add(mage3);
            members.add(warrior);
            members.add(warrior1);
            members.add(warrior2);
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
        try {
            getQuest("ooga", members);
        } catch (QuestNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
        partyRoster("roster.txt", members);
    }
}