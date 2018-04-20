import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class PhoneBook {
    
    private final List<Person> people = new ArrayList<Person>();

    // Search through list of people by name, return index if found, otherwise return -1
    public int findPersonByName(String name) {
        int index = -1;
        
        for (int i = 0; i < people.size(); i++) {
            if (this.people.get(i).getName().equals(name)) {
                index = i;
            }
        }
        return index;
    }
    
    // Search through list of people by phone number, return index if found, otherwise return -1
    private int findPersonByNumber(String number) {
        int index = -1;
        List<String> numbers;
        
        for (int i = 0; i < people.size(); i++) {
            numbers = this.people.get(i).getNumbers();
            // check each phone number associated to this person
            for (String phone : numbers) {
                if (phone.equals(number)) {
                    index = i;
                }
            }
        }
        return index;
    }
    
    // Search through list of people by keyword (partial matches against name and address)
    private List<Integer> findPersonsByKeyword(String keyword) {
        List<Integer> matchingPersons = new ArrayList<Integer>();
        String nameAndAddress;
        
        // empty keyword -> return list of all persons
        if (keyword.isEmpty()) {
            for (int i = 0; i < this.people.size(); i++) {
                matchingPersons.add(i);
            }
            return matchingPersons;
        }
        
        // check if keyword matches contents of name or address of each person
        for (int i = 0; i < this.people.size(); i++) {
            nameAndAddress = "";
            nameAndAddress += this.people.get(i).getName() + " ";
            nameAndAddress += String.join(" ", this.people.get(i).getAddress());
            
            if (nameAndAddress.contains(keyword)) {
                matchingPersons.add(i);
            }
        }
        
        // return list of matches (empty list of no matches found)
        return matchingPersons;
    }
    
    // Command 1 - add number to person with matching name, or create new person
    public void addNumber(String name, String number) {
        
        // returns -1 if not found
        int index = findPersonByName(name);
        
        if (index < 0) {
            this.people.add(new Person(name, number));
            return;
        }
        this.people.get(index).addNumber(number);
    }
    
    // Command 2/5 - return list of phone numbers for person with matching name, or return empty list
    public List<String> getNumbers(String name) {

        // returns -1 if not found
        int index = findPersonByName(name);
        
        if (index < 0) {
            return new ArrayList<String>();
        }
        return this.people.get(index).getNumbers();
    }
    
    // Command 3 - return name of person with matching phone number, or return empty string
    public String getNameFromNumber(String number) {

        // returns -1 if not found
        int index = findPersonByNumber(number);
        
        if (index < 0) {
            return "";
        }
        return this.people.get(index).getName();
    }

    // Command 4 - add address to person with matching name, or create new person
    public void addAddress(String name, List<String> address) {

        // returns -1 if not found
        int index = findPersonByName(name);
        
        if (index < 0) {
            this.people.add(new Person(name, address));
            return;
        }
        this.people.get(index).addAddress(address);
    }
    
    // Command 5 - return address list of person with matching name, or return empty list
    public List<String> getAddress(String name) {
        
        int index = findPersonByName(name);
        
        if (index < 0) {
            return new ArrayList<String>();
        }
        return this.people.get(index).getAddress();
    }

    // Command 6 - delete person with matching name from list of people (not possible for 2 persons with same name to exist)
    public void delete(String name) {
        
        // returns -1 if not found
        int index = findPersonByName(name);
        
        if (index < 0) {
            return;
        }
        this.people.remove(index);
    }
    
    // Command 7 - return name, address and phone numbers of persons whose name or address matches the user's keyword
    // return name, address and phone numbers for all persons if keyword is empty
    public List<ArrayList<String>> filteredSearch(String keyword) {
        
        List<ArrayList<String>> resultsList = new ArrayList<ArrayList<String>>(); // results will be returned as a list of sublists
        TreeMap<String, Integer> nameAndIndex = new TreeMap<String, Integer>(); // use TreeMap to alphabetise list of persons based on name
        
        // find indices of matching persons
        List<Integer> matchingPeople = this.findPersonsByKeyword(keyword);
        
        // if no person matches users keyword - return List containing one list containing only a simple message
        if (matchingPeople.isEmpty()) {
            resultsList.add(new ArrayList<String>());
            resultsList.get(0).add(" keyword not found");
            return resultsList;
        }
        
        // create TreeMap of names and matching indices (to alphabetise results by name)
        for (int index : matchingPeople) {
            nameAndIndex.put(this.people.get(index).getName(), index);
        }
        
        // loop through matching persons and add each person's details to a new sublist within the main list
        int i = 0;
        for (String name : nameAndIndex.keySet()) {
            
            // create new sublist and add name
            resultsList.add(new ArrayList<String>());
            resultsList.get(i).add("");
            resultsList.get(i).add(" " + name);
            
            // add address to sublist //
            List<String> addressLines = this.people.get(nameAndIndex.get(name)).getAddress();
            
            if (addressLines.isEmpty()) {
                resultsList.get(i).add("  address unknown");
            } else {

                String address = "";
                
                // combine street and city strings
                for (String line : addressLines) {
                    address += line + " ";
                }
                resultsList.get(i).add("  address: " + address.trim());
            }
            // end of address addition //
            
            // add phone numbers to sublist //
            List<String> phoneNumbers = this.people.get(nameAndIndex.get(name)).getNumbers();

            if (phoneNumbers.isEmpty()) {
                resultsList.get(i).add("  phone number not found");
            } else {
                resultsList.get(i).add("  phone numbers:");
                for (String phoneNumber : phoneNumbers) {
                    resultsList.get(i).add("   " + phoneNumber);
                }
            }
            // end of phone numbers addition//
            
            // switch to next person/sublist
            i++;
        }
        return resultsList;
    }
}
