import containers.Owner;
import containers.PhoneNumber;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Aykut Ismailov on 9.6.2019 г.
 */
public class Main {

    private static void read(String filename) {
        try {
            Scanner s = new Scanner(new FileInputStream(new File(filename)));
            Map<Integer,Integer> m=new HashMap<>();
            for (; s.hasNextLine(); ) {
                String[] line = s.nextLine().split(",");
                try {
                    if (line.length == 3) {
                        if (!m.containsKey(Integer.valueOf(line[0]))) {
                            Owner o = Owner.add(line[1]);
                            m.put(Integer.valueOf(line[0]),o.getId());
                            PhoneNumber.add(line[2], o);
                        } else {
                            PhoneNumber.add(line[2], m.get(Integer.valueOf(line[0])));
                        }
                    }
                    if(line.length==2){
                        Owner o = Owner.add(line[0]);
                        m.put(o.getId(),o.getId());
                        PhoneNumber.add(line[1], o);
                    }
                } catch (Exception e) {
                    System.out.println(String.join(",", line) + " is not valid account.");
                }
            }
        } catch (FileNotFoundException ignore) {
            System.out.println("Records not found");
        }

    }

    public static void readFromFile(String... params) {
        Scanner s = new Scanner(System.in);
        Owner.clear();
        PhoneNumber.clear();
        if (params.length == 0) {
            System.out.println("Enter full path to the file:");
            String path = s.nextLine();
            read(path);
        } else {
            read(params[0]);
        }
    }

    public static void addNewAccount(String...params) {
        Scanner s = new Scanner(System.in);
        String name,number;
        if(params.length!=2) {

            System.out.println("Enter owners name:");
            name = s.nextLine();
            System.out.println("Enter number:");
            number = s.nextLine();
        }else{
            name=params[0];
            number=params[1];
        }
        try {
            PhoneNumber.add(number, name);
        } catch (Exception e) {
            System.out.println("Invalid input");
        }
    }

    public static void deleteAccount(String...params) {
        Scanner s = new Scanner(System.in);
        String name;
        if(params.length==1){
            name=params[0];
        }else {
            System.out.println("Enter owner's name:");
            name = s.nextLine();
        }
        Iterator<PhoneNumber> iterator = PhoneNumber.getAllPhoneNumbers().iterator();
        List<Owner> owners = new ArrayList<>();
        List<PhoneNumber> list=new ArrayList<>();
        for (; iterator.hasNext(); ) {
            PhoneNumber p = iterator.next();
            if (p.getOwner().getName().equals(name)) {
                list.add(p);

            }
        }
        for(PhoneNumber p:list){
            PhoneNumber.remove(p.getNumber());
            p.getOwner().removePhone(p);
            if (!owners.contains(p.getOwner())) {
                owners.add(p.getOwner());
            }
        }
        for (Owner o : owners) {
            Owner.remove(o);
        }
    }

    public static Set<Owner> accessPhone(String...params) {
        Scanner s = new Scanner(System.in);
        String name;
        if(params.length==1){
            name=params[0];
        }else {
            System.out.println("Enter owner's name:");
            name = s.nextLine();
        }
        Iterator<PhoneNumber> iterator = PhoneNumber.getAllPhoneNumbers().iterator();
        Set<Owner> set = new HashSet<>();
        for (; iterator.hasNext(); ) {
            PhoneNumber p = iterator.next();
            if (p.getOwner().getName().equals(name)) {
                set.add(p.getOwner());
                System.out.println(p.getOwner().getName() + "," + p.getNumber());
            }
        }
        return set;
    }

    public static List<Owner> printPhone() {
        List<Owner> owners = Owner.getAllOwners().stream().sorted((Comparator.comparing(Owner::getName))).peek(System.out::println).collect(Collectors.toList());
        return owners;
    }

    public static List<PhoneNumber> mostCalled() {
        List<PhoneNumber> l = PhoneNumber.getAllPhoneNumbers()
                .stream()
                .sorted(((o1, o2) -> o2.getCallCount().compareTo(o1.getCallCount())))
                .collect(Collectors.toList());
        for (int i = 0; i < 5 && i < l.size(); i++) {
            System.out.println(l.get(i).getOwner().getName() + "," + l.get(i).getNumber() + "," + l.get(i).getCallCount());
        }
        return l;
    }

    private static void write() {

        try {
            FileOutputStream finalOut = new FileOutputStream(new File("Record.txt"));
            Owner.getAllOwners().forEach(owner -> {
                try {
                    finalOut.write(owner.toString().getBytes());
                } catch (IOException ignore) {
                    System.err.println(owner.toString() + " couldn't be recorded.");
                }
            });
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }


    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        read("Record.txt");
        o:
        for (; ; ) {
            System.out.println("Press 1 to read from file.");
            System.out.println("Press 2 to add a new account.");
            System.out.println("Press 3 to delete an account.");
            System.out.println("Press 4 to access a phone number.");
            System.out.println("Press 5 to print all phone numbers.");
            System.out.println("Press 6 to print top 5 most called phone numbers.");
            System.out.println("Press 7 to exit.");
            int i = s.nextInt();
            switch (i) {
                case 1:
                    readFromFile();
                    break;
                case 2:
                    addNewAccount();
                    break;
                case 3:
                    deleteAccount();
                    break;
                case 4:
                    accessPhone();
                    break;
                case 5:
                    printPhone();
                    break;
                case 6:
                    mostCalled();
                    break;
                case 7:
                    break o;
                default:
                    System.out.println("Invalid input. ");
            }
        }
        write();
    }
}
