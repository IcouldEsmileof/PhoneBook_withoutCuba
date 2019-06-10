package containers;

import java.util.*;

/**
 * Created by Aykut Ismailov on 9.6.2019 г.
 */
public class Owner {
    private static Set<Owner> owners = new HashSet<>();
    private static int maxID=0;
    private int id;
    private String name;
    private List<PhoneNumber> phoneNumbers;

    private Owner(String name) {
        this.id = maxID;
        maxID++;
        this.name = name;
        owners.add(this);
        phoneNumbers = new ArrayList<>();
    }

    public static Set<Owner> getAllOwners() {
        return Collections.unmodifiableSet(owners);
    }

    public static Owner get(int id) {
        Iterator<Owner> iterator = owners.iterator();
        for (; iterator.hasNext(); ) {
            Owner o = iterator.next();
            if (o.id == id) {
                return o;
            }
        }
        throw new NullPointerException();
    }

    public static boolean containsId(int id) {
        if (id < 0) {
            return false;
        }
        for (Owner o : owners) {
            if (o.id == id) {
                return true;
            }
        }
        return false;
    }

    public static boolean contains(Owner o) {
        return owners.contains(o);
    }

    public static Owner add(String name) {
        return new Owner(name);
    }

    public static void remove(int id) {
        Iterator<Owner> iterator = owners.iterator();
        for (; iterator.hasNext(); ) {
            Owner o = iterator.next();
            if (o.id == id) {
                iterator.remove();
                return;
            }
        }
    }

    public static void remove(Owner o) {
        owners.remove(o);
    }

    public static void clear() {
        owners.clear();
    }

    public List<PhoneNumber> getPhoneNumbers() {
        return phoneNumbers;
    }

    public void addPhone(PhoneNumber newNumber) {
        phoneNumbers.add(newNumber);
    }

    public void removePhone(PhoneNumber phone) {
        phoneNumbers.remove(phone);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
        if(maxID<=id){
            maxID=id+1;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Owner owner = (Owner) o;
        return id == owner.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (PhoneNumber p : phoneNumbers) {
            builder.append(id).append(",").append(name).append(",").append(p.toString()).append(System.lineSeparator());
        }
        return builder.toString();
    }
}
