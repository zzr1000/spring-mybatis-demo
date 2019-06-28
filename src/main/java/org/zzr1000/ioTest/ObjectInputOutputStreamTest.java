package org.zzr1000.ioTest;

import org.junit.Test;

import java.io.*;

/*

 */
public class ObjectInputOutputStreamTest {

    @Test
    public void test() throws IOException, ClassNotFoundException {

        User user = new User();
        user.setName("AAA");

        String file = "f:\\b";

        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
        oos.writeObject(user);

        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));

        User user1 = (User)ois.readObject();

        System.out.println(user1.getName());

    }

}

class User implements Serializable{
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;
}
