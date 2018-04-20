import java.util.ArrayList;
import java.util.List;

public class Person {
    
    private final String name;
    private final List<String> numbers = new ArrayList<String>();
    private List<String> address;

    // this constructor should never be called externally
    public Person(String name, String number, List<String> address) {
        this.name = name;
        if (!number.isEmpty()) { // only store real phone numbers
            this.numbers.add(number);
        }
        this.address = address;
    }
    
    // constructor called if new person created with phone number
    public Person(String name, String number) {
        this(name, number, new ArrayList<String>());
    }
    
    // constructor called if new person created with address (address input by user and stored as a pair of strings - street and city)
    public Person(String name, List<String> address) {
        this(name, "", address); // pass blank phone number so it won't be stored
    }

    public void addNumber(String number) {
        this.numbers.add(number);
    }
    
    // Person can only have 1 address, okay to overwrite existing
    public void addAddress(List<String> address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public List<String> getNumbers() {
        return numbers;
    }

    public List<String> getAddress() {
        return address;
    }
}
