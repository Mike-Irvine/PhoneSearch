import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TextUserInterface {
    
    private final Scanner reader;
    private final PhoneBook pb;

    public TextUserInterface(Scanner reader) {
        this.reader = reader;
        this.pb = new PhoneBook();
    }
    
    public void start() {
        System.out.println("phone search\n" +
                           "available operations:\n" +
                           " 1 add a number\n" +
                           " 2 search for a number\n" +
                           " 3 search for a person by phone number\n" +
                           " 4 add an address\n" +
                           " 5 search for personal information\n" +
                           " 6 delete personal information\n" +
                           " 7 filtered listing\n" +
                           " x quit");
        
        // user command loop
        while (true) {
            System.out.print("\ncommand: ");
            String command = reader.nextLine().trim();
            if (command.equals("x")){
                break;
            }
            // pass user command to handler
            handleCommand(command);
        }
    }
    
    // 
    public void handleCommand(String command) {
        try {
            switch (Integer.parseInt(command)) {
                case 1:  addNumber();
                         break;
                case 2:  findNumbersFromName();
                         break;
                case 3:  findNameFromNumber();
                         break;
                case 4:  addAddress();
                         break;
                case 5:  findAddressAndNumbersFromName();
                         break;
                case 6:  delete();
                         break;
                case 7:  filteredSearch();
                         break;
                default:
            }
        } catch (NumberFormatException e) { // non-numeric and out of range commands ignored, return to user command loop
        }
    }
    
    // Command 1 - request user input (name, number) and pass to phonebook
    private void addNumber() {
        System.out.print("whose number: ");
        String name = reader.nextLine();
        System.out.print("number: ");
        String number = reader.nextLine();
        this.pb.addNumber(name, number);
    }

    // Command 2 - request user input (name) and print list of numbers of matching person
    private void findNumbersFromName() {
        List<String> numbers;
        
        System.out.print("whose number: ");
        String name = reader.nextLine();
        numbers = this.pb.getNumbers(name);
        
        if (numbers.isEmpty()) {
            System.out.println("  not found"); // print simple message if no matching person found
        } else {
            for (String number : numbers) {
                System.out.println(" " + number);
            }
        }
    }

    // Command 3 - request user input (number) and print name of matching person
    private void findNameFromNumber() {
        System.out.print("number: ");
        String number = reader.nextLine();
        
        String name = this.pb.getNameFromNumber(number);
        
        if (name.isEmpty()) {
            System.out.println(" not found"); // print simple message if no matching person found
        } else {
            System.out.println(" " + name);
        }
    }

    // Command 4 - request user input (name, street, city) and pass to phonebook
    private void addAddress() {
        List<String> address = new ArrayList<String>();
        
        System.out.print("whose address: ");
        String name = reader.nextLine();
        System.out.print("street: ");
        address.add(reader.nextLine());
        System.out.print("city: ");
        address.add(reader.nextLine());
        
        this.pb.addAddress(name, address);
    }

    // Command 5 - request user input (name) and print 
    private void findAddressAndNumbersFromName() {
        List<String> address;
        List<String> numbers;
        
        System.out.print("whose information: ");
        String name = reader.nextLine();
        
        if (this.pb.findPersonByName(name) < 0) {
            System.out.println("  not found"); // print simple message if no matching person found
            return;
        }
        
        address = this.pb.getAddress(name);
        
        if (address.isEmpty()) {
            System.out.println("  address unknown"); // print simple message if matching person has no stored address
        } else {
            String addressCombined = "";
            for (String addressPart : address) { // combine street and city into single string (address)
                addressCombined += addressPart + " ";
            }
//            addressCombined = addressCombined.substring(0, (addressCombined.length() - 1));
            System.out.println("  address: " + addressCombined.trim());
        }
        
        numbers = this.pb.getNumbers(name);
        
        if (numbers.isEmpty()) {
            System.out.println("  phone number not found"); // print simple message if matching person has no stored phone number
        } else {
            System.out.println("  phone numbers:");
            for (String number : numbers) {
                System.out.println("   " + number); // print each matching phone number
            }
        }
    }

    // Command 6 - request user input (name) and pass to phonebook
    private void delete() {
        System.out.print("whose information: ");
        String name = reader.nextLine();
        
        this.pb.delete(name);
    }

    // Command 7 - request user input (keyword) and pass to phonebook
    private void filteredSearch() {
        List<ArrayList<String>> resultsList;
        
        System.out.print("keyword (if empty, all listed): ");
        String keyword = reader.nextLine();
        
        resultsList = this.pb.filteredSearch(keyword);
        
        for (ArrayList<String> list : resultsList) {    // loop through list
            for (String string : list) {                // loop through sublists
                System.out.println(string);
            }
        }
    }
}
