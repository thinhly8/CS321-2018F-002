
import java.io.DataOutputStream;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Kevin
 */
public class Player {
    private LinkedList<Item> currentInventory;
    private String name;
    private String lastWhisperName;
    private int currentRoom;
    private Direction currentDirection;
    private PrintWriter replyWriter = null;
    private DataOutputStream outputWriter = null;
    private Money money;

    /* START 405_ignore variables*/
    private ArrayList<String> ignoreList;
    private ArrayList<String> ignoredByList;
    /* END 405_ignore variables*/

    public Player(String name) {
        this.currentRoom = 1;
        this.currentDirection = Direction.NORTH;
        this.name = name;
        this.currentInventory = new LinkedList<>();
        this.money = new Money(20);
  /* START 405_ignore*/
        this.ignoreList = new ArrayList<String>();
        this.ignoredByList = new ArrayList<String>();
        /* END 405_ignore  */
    }

    public void turnLeft() {
        switch(this.currentDirection.toString()) {
            case "North":
                this.currentDirection = Direction.WEST;
                break;
            case "South":
                this.currentDirection = Direction.EAST;
                break;
            case "East":
                this.currentDirection = Direction.NORTH;
                break;
            case "West":
                this.currentDirection = Direction.SOUTH;
                break;
        }
    }

    public void turnRight() {
        switch(this.currentDirection.toString()) {
            case "North":
                this.currentDirection = Direction.EAST;
                break;
            case "South":
                this.currentDirection = Direction.WEST;
                break;
            case "East":
                this.currentDirection = Direction.SOUTH;
                break;
            case "West":
                this.currentDirection = Direction.NORTH;
                break;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLastWhisperName(String name) {
        this.lastWhisperName = name;
    }

    public String getLastWhisperName() {
        return this.lastWhisperName;
    }

    public LinkedList<Item> getCurrentInventory() {
        return currentInventory;
    }

    public void setCurrentInventory(LinkedList<Item> currentInventory) {
        this.currentInventory = currentInventory;
    }

    public void addObjectToInventory(Item object) {
        this.currentInventory.add(object);
    }

    public Item removeObjectFomInventory(String object) {
        for(Item obj : this.currentInventory) {
            if(obj.getItemName().equalsIgnoreCase(object)) {
                this.currentInventory.remove(obj);
                return obj;
              }
            }
        return null;
    }

    /**
     * Allows an an object to be taken away from player's inventory.
     * @return Message showing success.
     */
    public String removeRandomItem()  {
        if (this.currentInventory.isEmpty()){
            return "You have no items in your inventory.";
        }
        Random randInt = new Random();
        int randItem = randInt.nextInt(this.currentInventory.size());
        String targetItem = this.currentInventory.remove(randItem).getItemName();
        setCurrentInventory(this.currentInventory);
        return targetItem + " was removed from your inventory.";
    }

    public void setReplyWriter(PrintWriter writer) {
        this.replyWriter = writer;
    }

    public PrintWriter getReplyWriter() {
        return this.replyWriter;
    }

    public void setOutputWriter(DataOutputStream writer) {
        this.outputWriter = writer;
    }

    public DataOutputStream getOutputWriter() {
        return this.outputWriter;
    }

    public int getCurrentRoom() {
        return this.currentRoom;
    }

    public void setCurrentRoom(int room) {
        this.currentRoom = room;
    }

    public String getCurrentDirection() {
        return this.currentDirection.name();
    }

    public Direction getDirection() {
        return this.currentDirection;
    }
    public Money getMoney() {
      return this.money;
    }

    public void addMoney(double value) {
        Money added = new Money();
        int fives = (int)(value / 5);
        int ones = (int)(value % 5);
        double coins = value - ((int)value);
        coins *= 100;
        coins = (int) coins;
        int quarters = (int)(coins / 25);
        int dimes = (int)((coins - (quarters * 25)) / 10);
        int pennies = (int)(coins - (25 * quarters) - (10 * dimes));
        
        added.numFives = fives;
        added.numOnes = ones;
        added.numQuarters = quarters;
        added.numDimes = dimes;
        added.numOnes = ones;
        
        addMoney(added);
    }
    
    public void addMoney(Money moneyToAdd){
        this.money.numFives += moneyToAdd.numFives;
        this.money.numOnes += moneyToAdd.numOnes;
        this.money.numQuarters += moneyToAdd.numQuarters;
        this.money.numDimes += moneyToAdd.numDimes;
        this.money.numPennies += moneyToAdd.numPennies;
    }
    
    public void removeMoney(Money moneyRemove){
      this.money.numFives -= moneyRemove.numFives;
      this.money.numOnes -= moneyRemove.numOnes;
      this.money.numQuarters -= moneyRemove.numQuarters;
      this.money.numDimes -= moneyRemove.numDimes;
      this.money.numPennies -= moneyRemove.numPennies;
    }
   
    public String viewMoney() {
        return this.money.toString();
    }
 
    public void setDirection(Direction direction){
        this.currentDirection = direction;
    }

    public Money giveMoney(Player giver,Player receiver,double value){
        Money moneyToGive = new Money();
        replyWriter.println("You are giving away "+value);

        if(this.money.sum() < value){
            replyWriter.println("Not enough money!");
            return moneyToGive;
        }
        int i = 0;
        while(i < value){
            receiver.money.dollars.add(this.money.dollars.remove(0));
            i++;
        }
        receiver.getReplyWriter().println("You received " +value + " dollars!");
        return moneyToGive;
    }

    public String viewInventory() {
        String result = "";
        if(this.currentInventory.isEmpty() == true) {
            return " nothing.";
        }
        else {
            for(Item obj : this.currentInventory) {
                result += " " + obj;
            }
            result += ".";
        }
        result += ".";
        return result;
    }

    @Override
    public String toString() {
        return "Player " + this.name + ": " + currentDirection.toString();
    }

 /* START 405_ignore */
    public void ignorePlayer(String name) {
  ignoreList.add(name);
    }

    public void addIgnoredBy( String name) {
  ignoredByList.add(name);
    }

    public boolean searchIgnoredBy(String name) {
     int listSize = ignoredByList.size();
  for( int x = 0; x < listSize; x++){
   if( name.equalsIgnoreCase(ignoredByList.get(x)))
    return true;
  }
  return false;
    }

    public boolean searchIgnoreList(String name) {
     int listSize = ignoreList.size();
  for( int x = 0; x < listSize; x++){
   if( name.equalsIgnoreCase(ignoreList.get(x)))
    return true;
  }
  return false;
    }
    /* END 405_ignore */
    //407
    public String showIgnoreList()
    {
        String res = "";
        for(int i = 0; i < ignoreList.size(); i++)
            res += ignoreList.get(i) + " ";
        return res;
    }

   public void unIgnorePlayer(String name) {
  ignoreList.remove(name);
    }

 public void removeIgnoredBy( String name) {
  ignoredByList.remove(name);
    }
}
