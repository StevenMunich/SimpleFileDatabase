import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;
public class Main {
    public record account(int accountNumber, String firstName, String lastName, double balance){};
    public static void main(String[] args) throws IOException {
       displayMenu();
    } //end main()

    public static void displayMenu() throws IOException {
        List<account> accounts =readAll();
        Scanner input = new Scanner(System.in);
        System.out.println("Main Menu: 1. Display Database 2. Add a new entry. 3. Save Database type 999 to exit");

        while(!input.hasNext("999")){
            System.out.println("Main Menu: 1. Display Database 2. Add a new entry. 3. Save Database type 4. Delete an Account 999 to exit");
            try{
                int i = input.nextInt();
                switch(i){
                    case 1 :
                        displayList(accounts);
                        input.reset();
                        break;
                    case 2:
                        writeRecord(accounts);
                        input.reset();
                        break;
                    case 3:
                        writeList(accounts);
                        input.reset();
                    break;
                    case 4:
                        deleteRecord(accounts);
                        input.reset();
                        break;
                    default:
                } //end switch
            }catch(Exception e){
                System.err.println("Invalid Option. Please try again or type 999 to quit.");
                input.nextLine();
            }
        }//end while
    } //end displayMenu()
    public static void deleteRecord(List<account> accounts) throws IOException {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter the account number you wish to delete: ");
        int i = input.nextInt();
        for (account a : accounts) //search
            if (a.accountNumber == i) { //found
                accounts.remove(a); //delete
                System.out.println(a + " was removed from the set. Don't forget to save!");
            } //end if
        //end loop
        input.reset();
        input.close();
    }//end deleteRecord()

    public static List<account> readAll() throws IOException {
        List<account> accounts = new ArrayList<>();
        try(Scanner input = new Scanner(Paths.get("output.txt"))){
            while(input.hasNext()) {
                account A = new account(input.nextInt(), input.next(), input.next(), input.nextDouble());
                accounts.add(A);
            }//end while
        }
        return accounts;
    }

    public static void writeList(List<account> accounts) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt"))) {

           for(account a: accounts){
               writer.write(a.accountNumber + " ");
               writer.write(a.firstName+ " ");
               writer.write(a.lastName + " ");
               writer.write(String.valueOf(a.balance)); //balance needs to be casted into a String
               writer.newLine(); // Adds a newline
           }//end loop
        } catch (IOException e) {
            e.printStackTrace();
        }
    } //end writeList()
    public static void displayList(List<account> accounts) {
        for(account a: accounts)
            System.out.println(a);
    }
    public static void writeRecord(List<account> accounts){

            Scanner input = new Scanner(System.in);
            System.out.printf("%s%n%s%n?",
                    "Enter account number, first name, last name, and balance.",
                    "Enter end-of-file indicator to end input. 999 to Quit");
            while(!input.hasNext("999")) { //.hasNext() reads without removing
                try {
                    int account_number = input.nextInt();
                    String first_name = input.next();
                    String last_name = input.next();
                    double account_balance = input.nextDouble();
                    account a = new account(account_number, first_name, last_name, account_balance);
                    accounts.add(a);
                   // System.out.println(s);
                } catch (NoSuchElementException elementException) {
                    System.err.println("Invalid Input. Please try again.");
                    input.reset(); //discards input so user can try again
                }
                System.out.print("Add Record(type 999 to return):");
            }//end while
            //Neccasary because of hasNext() when returned to displayMenu
            System.out.println("Main Menu: 1. Display Database 2. Add a new entry. 3. Save Database type 999 to exit");

    } //end Write Record
}

/*
    public static account readFile(){
        try(Scanner input = new Scanner(Paths.get("clients.txt"))){
            //System.out.printf("%-10s%-12s%-12s%10s%n" , "Account",
             //       "First Name", "Last Name", "Balance");
            while(input.hasNext()){
                account A = new account(input.nextInt(), input.next(), input.next(), input.nextDouble()); // System.out.printf("%-10s%-12s%-12s%10s%n" , input.nextInt(), input.next(), input.next(), input.nextDouble());
                input.close();
                return A;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    } //end readFile()
* */