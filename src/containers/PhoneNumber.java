package containers;

import java.util.*;

/**
 * Created by Aykut Ismailov on 9.6.2019 г.
 */
public class PhoneNumber {
    private static Set<PhoneNumber> phoneNumbers = new HashSet<>();

    private String number;
    private Owner owner;
    private Integer callCount;

    private PhoneNumber(String number, Owner owner) throws Exception {
        number = normalizeNumber(number);
        if (checkNumber(number)) {
            this.number = number;
            this.owner = owner;
            this.callCount = 0;
            owner.addPhone(this);
            phoneNumbers.add(this);
        }
    }

    public static Set<PhoneNumber> getAllPhoneNumbers() {
        return Collections.unmodifiableSet(phoneNumbers);
    }

    public static PhoneNumber getPhoneNumber(String number) {
        Iterator<PhoneNumber> iterator = phoneNumbers.iterator();
        for (; iterator.hasNext(); ) {
            PhoneNumber p = iterator.next();
            if (p.number.equals(number)) {
                return p;
            }
        }
        throw new NullPointerException();
    }

    public static PhoneNumber add(String number, Owner owner) throws Exception {
        return new PhoneNumber(number, owner);
    }

    public static PhoneNumber add(String number, String ownerName) throws Exception {
        return new PhoneNumber(number, Owner.add(ownerName));
    }

    public static PhoneNumber add(String number, int ownerId) throws Exception {
        return new PhoneNumber(number, Owner.get(ownerId));
    }

    public static void remove(String number) {
        Iterator<PhoneNumber> iterator = phoneNumbers.iterator();
        for (; iterator.hasNext(); ) {
            PhoneNumber p = iterator.next();
            if (p.number.equals(number)) {
                p.owner.removePhone(p);
                iterator.remove();
                return;
            }
        }
    }

    public static void clear() {
        phoneNumbers.clear();
    }

    private boolean checkNumber(String number) {
        Iterator<PhoneNumber> iterator = phoneNumbers.iterator();
        for (; iterator.hasNext(); ) {
            PhoneNumber p = iterator.next();
            if (p.number.equals(number)) {
                return false;
            }
        }
        return true;
    }

    private String normalizeNumber(String number) throws Exception {
        if (number.length() == 13) {
            if (number.matches("\\+359(87|88|89|98)[2-9][0-9]{6}")) {
                return number;
            } else {
                throw new Exception("Invalid number.");
            }
        } else if (number.length() == 10) {
            if (number.matches("0(87|88|89|98)[2-9][0-9]{6}")) {
                return "+359" + number.substring(1);
            } else {
                throw new Exception("Invalid number.");
            }
        } else if (number.length() == 14) {
            if (number.matches("00359(87|88|89|98)[2-9][0-9]{6}")) {
                return "+" + number.substring(2);
            } else {
                throw new Exception("Invalid number.");
            }
        } else {
            throw new Exception("Invalid number.");
        }
    }

    public void call() {
        callCount++;
    }

    public String getNumber() {
        return number;
    }

    public Owner getOwner() {
        return owner;
    }

    public Integer getCallCount() {
        return callCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PhoneNumber that = (PhoneNumber) o;
        return Objects.equals(number, that.number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number);
    }

    @Override
    public String toString() {
        return number;
    }
}
