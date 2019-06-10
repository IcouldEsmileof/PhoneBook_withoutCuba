import containers.Owner;
import containers.PhoneNumber;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Aykut Ismailov on 10.6.2019 г.
 */
class MainTest {

    @BeforeAll
    @Test
    public static void readFromFile() {
        Main.readFromFile("Record.txt");
        assertEquals(2, Owner.getAllOwners().size());
    }


    @Test
    public void addNewAccount() {
        Main.addNewAccount("dssa","0897354321");
        assertEquals(2, Owner.getAllOwners().size());
    }

    @Test
    public void deleteAccount() {
        Main.deleteAccount("asd");
        assertEquals(1, Owner.getAllOwners().size());
    }

    @Test
    public void accessPhone() {
        assertEquals(0, Main.accessPhone("asd").size());
    }

    @AfterEach
    @Test
    public void printPhone() {
        assertTrue(isSortedOwners(Main.printPhone()));
    }

    @Test
    public void mostCalled() {
        randomCaller();
        assertTrue(isSortedNumbers(Main.mostCalled()));
    }

    private void randomCaller() {
        Iterator<PhoneNumber> iterator = PhoneNumber.getAllPhoneNumbers().iterator();
        for (; iterator.hasNext(); ) {
            PhoneNumber p = iterator.next();
            for (int i = 0, j = ((int) (Math.random()*10)); i < j; i++) {
                p.call();
            }
        }
    }

    private boolean isSortedNumbers(List<PhoneNumber> list) {
        for (int i = 1; i < list.size(); i++) {
            if (list.get(i).getCallCount().compareTo(list.get(i - 1).getCallCount()) > 0) {
                return false;
            }
        }
        return true;
    }

    private boolean isSortedOwners(List<Owner> list) {
        for (int i = 1; i < list.size(); i++) {
            if (list.get(i).getName().compareTo(list.get(i - 1).getName()) < 0) {
                return false;
            }
        }
        return true;
    }
}