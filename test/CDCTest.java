import org.junit.Before;

import java.util.Vector;

import static org.junit.Assert.*;

public class CDCTest {
    CDC cdc;

    @org.junit.Before
    public void CDC() throws Exception{
        cdc = new CDC();
    }

    @org.junit.Test
    public void testAddVirtualCharacter() throws Exception {
        cdc.addVirtualCharacter(1);
        cdc.addVirtualCharacter(2);

        Vector<Object> updateInfo = cdc.getUpdateInfo();
        System.out.println(updateInfo.size());
        assertTrue(updateInfo.isEmpty());
    }

    @org.junit.Test
    public void testAddItem() throws Exception {

    }

    @org.junit.Test
    public void testUpdateDirection() throws Exception {

    }

    @org.junit.Test
    public void testGetItem() throws Exception {

    }

    @org.junit.Test
    public void testGetUpdateInfo() throws Exception {
        Vector<Object> updateInfo = cdc.getUpdateInfo();
        System.out.println(updateInfo.size());
        assertTrue(updateInfo.isEmpty());
    }

    @org.junit.Test
    public void testStartUpdatingThread() throws Exception {

    }
}