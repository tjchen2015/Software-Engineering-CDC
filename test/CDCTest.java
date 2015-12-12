import org.junit.Before;

import java.util.Vector;

import static org.junit.Assert.*;

public class CDCTest {
    CDC cdc;
    TestThreadController testThreadController;

    @org.junit.Before
    public void CDC() throws Exception{
        testThreadController = new TestThreadController();
        testThreadController.flag = true;
        cdc = new CDC(testThreadController);
    }

    @org.junit.Test //(expected = ExceedMaxException.class)
    public void testAddVirtualCharacter() throws ExceedMaxException {
        cdc.addVirtualCharacter(1);
        cdc.addVirtualCharacter(2);
        cdc.addVirtualCharacter(3);
        //cdc.addVirtualCharacter(4);
        //cdc.addVirtualCharacter(5);

        Vector<Object> updateInfo = cdc.getUpdateInfo();
        assertEquals(updateInfo.size(), 3);

        Character character = (Character) updateInfo.get(0);
        assertEquals(character.clientNumber, 1);
        assertEquals(character.x, 20);
        assertEquals(character.y, 20);
        assertEquals(character.dir, 0);
        assertEquals(character.velocity, 0);
        character = (Character) updateInfo.get(1);
        assertEquals(character.clientNumber, 2);
        assertEquals(character.x, 20);
        assertEquals(character.y, 20);
        assertEquals(character.dir, 0);
        assertEquals(character.velocity, 0);
        character = (Character) updateInfo.get(2);
        assertEquals(character.clientNumber, 3);
        assertEquals(character.x, 20);
        assertEquals(character.y, 20);
        assertEquals(character.dir, 0);
        assertEquals(character.velocity, 0);
        /*character = (Character) updateInfo.get(3);
        assertEquals(character.clientNumber, 4);
        assertEquals(character.x, 20);
        assertEquals(character.y, 20);
        assertEquals(character.dir, 0);
        assertEquals(character.velocity, 0);*/
    }

    @org.junit.Test
    public void testAddItem() throws Exception {
        cdc.addItem("item1", -1, true, 1, 1);
        cdc.addItem(null, 2, false, 2, 3);
        //cdc.addItem("item1", 2, false, 2, 2);

        Vector<Object> updateInfo = cdc.getUpdateInfo();
        assertEquals(updateInfo.size(), 2);
        Item item = (Item) updateInfo.get(0);
        assertEquals(item.name, "item1");
        assertEquals(item.index, -1);
        assertEquals(item.shared, true);
        assertEquals(item.x, 1);
        assertEquals(item.y, 1);
        item = (Item) updateInfo.get(1);
        assertEquals(item.name, null);
        assertEquals(item.index, 2);
        assertEquals(item.shared, false);
        assertEquals(item.x, 2);
        assertEquals(item.y, 3);
    }

    @org.junit.Test
    public void testUpdateDirection() throws Exception {
        cdc.addVirtualCharacter(11);
        cdc.addVirtualCharacter(121);
        cdc.updateDirection(11, 2);
        cdc.updateDirection(121, 3);

        Vector<Object> updateInfo = cdc.getUpdateInfo();
        assertEquals(updateInfo.size(), 2);
        Character character = (Character) updateInfo.get(0);
        assertEquals(character.dir, 2);
        assertEquals(character.velocity, 2);
        character = (Character) updateInfo.get(1);
        assertEquals(character.dir, 3);
        assertEquals(character.velocity, 2);
    }

    @org.junit.Test
    public void testGetItem() throws Exception {
        cdc.addVirtualCharacter(2);
        cdc.addItem("item1", 2, true, 20, 20);

        cdc.getItem(2);

        Vector<Object> updateInfo = cdc.getUpdateInfo();
        Character character = (Character) updateInfo.get(0);
        assertEquals(character.ownedItem.get(0).name, "item1");
    }

    @org.junit.Test
    public void testGetUpdateInfo() throws Exception {
        Vector<Object> updateInfo = cdc.getUpdateInfo();
        assertTrue(updateInfo.isEmpty());
    }

    @org.junit.Test
    public void testStartUpdatingThread() throws Exception {
        cdc.addVirtualCharacter(1);
        cdc.addVirtualCharacter(2);
        cdc.addVirtualCharacter(3);
        cdc.addVirtualCharacter(4);
        cdc.updateDirection(1, 0);
        cdc.updateDirection(2, 1);
        cdc.updateDirection(3, 2);
        cdc.updateDirection(4, 3);

        cdc.startUpdatingThread();
        testThreadController.flag = false;
        testThreadController.flag = true;
        Thread.sleep(5000);

        Vector<Object> updateInfo = cdc.getUpdateInfo();
        Character character = (Character) updateInfo.get(0);
        assertEquals(character.x, 20);
        assertEquals(character.y, 18);
        character = (Character) updateInfo.get(1);
        assertEquals(character.x, 22);
        assertEquals(character.y, 20);
    }
}