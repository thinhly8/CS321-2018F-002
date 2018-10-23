
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.ArrayList;

/**
 *
 * @author Kevin
 */
public class Player {
    private LinkedList<String> currentInventory;
    private String name;
    private int currentRoom;
    private Direction currentDirection;
    private PrintWriter replyWriter = null;
    private DataOutputStream outputWriter = null;
    private BufferedReader reader = null; 
    // add a money field to track player money
    private Money money;

    public Player(String name) {
        this.currentRoom = 1;
        this.currentDirection = Direction.NORTH;
        this.name = name;
        this.currentInventory = new LinkedList<>();
        // set a default amount of money for each player
        this.money = new Money(20);
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

    public LinkedList<String> getCurrentInventory() {
        return currentInventory;
    }

    public void setCurrentInventory(LinkedList<String> currentInventory) {
        this.currentInventory = currentInventory;
    }
    
    public void addObjectToInventory(String object) {
        this.currentInventory.add(object);
    }
    
    public void setReplyWriter(PrintWriter writer) {
        this.replyWriter = writer;
    }
    
    public PrintWriter getReplyWriter() {
        return this.replyWriter;
    }
/*
    public String output() {
     
     InputStreamReader keyboardReader = new InputStreamReader(System.in);
        BufferedReader keyboardInput = new BufferedReader(keyboardReader);
        String keyboardStatement;
        
     replyWriter.println("Enter an input (y/n): ");
     
    //System.out.println("Enter an input: ");
    String input = null;
    
 try {
  input = keyboardInput.readLine();
 } catch (IOException e) {
  // TODO Auto-generated catch block
  e.printStackTrace();
 }
     return input; 
    }*/


    
    public void setOutputWriter(DataOutputStream writer) {
        this.outputWriter = writer;
    }
    
    public DataOutputStream getOutputWriter() {
        return this.outputWriter;
    }
    
   
    public BufferedReader getReader() {
     return this.reader; 
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
    // get money 
    public Money getMoney() {
      return this.money;
    }
    // return a string to print to the screen when player wants to view money
    public String viewMoney() {
      return this.money.toString();
    }
    // allows a player to accept money from another player
    public void acceptMoney(Money moneyToAdd){
      this.money.dollars.addAll(moneyToAdd.getDollars());
      this.money.coins.addAll(moneyToAdd.getCoins());
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
            return "nothing.";
        }
        else {
            for(String obj : this.currentInventory) {
                result += " " + obj;
            }
            result += ".";
        }
        return result;
    }

    @Override
    public String toString() {
        return "Player " + this.name + ": " + currentDirection.toString();
    }
}
