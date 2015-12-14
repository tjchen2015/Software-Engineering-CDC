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

    @org.junit.Test
    public void testAddVirtualCharacter() throws ExceedMaxException{
        cdc.addVirtualCharacter(1);
        cdc.addVirtualCharacter(2);
        cdc.addVirtualCharacter(3);
        cdc.addVirtualCharacter(4);

        Vector<Object> updateInfo = cdc.getUpdateInfo();
        assertEquals(4, updateInfo.size());

        Character character = (Character) updateInfo.get(0);
        assertEquals(1, character.clientNumber);
        assertEquals(20, character.x);
        assertEquals(20, character.y);
        assertEquals(0, character.dir);
        assertEquals(0, character.velocity);
        character = (Character) updateInfo.get(1);
        assertEquals(2, character.clientNumber);
        assertEquals(20, character.x);
        assertEquals(20, character.y);
        assertEquals(0, character.dir);
        assertEquals(0, character.velocity);
        character = (Character) updateInfo.get(2);
        assertEquals(3, character.clientNumber);
        assertEquals(20, character.x);
        assertEquals(20, character.y);
        assertEquals(0, character.dir);
        assertEquals(0, character.velocity);
        character = (Character) updateInfo.get(3);
        assertEquals(4, character.clientNumber);
        assertEquals(20, character.x);
        assertEquals(20, character.y);
        assertEquals(0, character.dir);
        assertEquals(0, character.velocity);
    }

    @org.junit.Test (expected = AssertionError.class)
    public void testAddSameVirtualCharacter() throws AssertionError, ExceedMaxException {
        cdc.addVirtualCharacter(1);
        cdc.addVirtualCharacter(2);
        cdc.addVirtualCharacter(2);
    }

    @org.junit.Test (expected = ExceedMaxException.class)
    public void testAddExceededVirtualCharacter() throws ExceedMaxException{
        cdc.addVirtualCharacter(1);
        cdc.addVirtualCharacter(2);
        cdc.addVirtualCharacter(3);
        cdc.addVirtualCharacter(4);
        cdc.addVirtualCharacter(5);
    }

    @org.junit.Test
    public void testAddItem() throws Exception {
        cdc.addItem("item1", -1, true, 1, 1);
        cdc.addItem(null, 2, false, 2, 3);
        cdc.addItem("item1", 6, true, 5, 2);

        Vector<Object> updateInfo = cdc.getUpdateInfo();
        assertEquals(updateInfo.size(), 3);
        Item item = (Item) updateInfo.get(0);
        assertEquals("item1", item.name);
        assertEquals(-1, item.index);
        assertEquals(true, item.shared);
        assertEquals(1, item.x);
        assertEquals(1, item.y);
        item = (Item) updateInfo.get(1);
        assertEquals(null, item.name);
        assertEquals(2, item.index);
        assertEquals(false, item.shared);
        assertEquals(2, item.x);
        assertEquals(3, item.y);
        item = (Item) updateInfo.get(2);
        assertEquals("item1", item.name);
        assertEquals(6, item.index);
        assertEquals(true, item.shared);
        assertEquals(5, item.x);
        assertEquals(2, item.y);
    }

    @org.junit.Test (expected = AssertionError.class)
    public void testAddSamePositionItem() throws Exception{
        cdc.addItem("item1", -1, true, 1, 1);
        cdc.addItem(null, 3, false, 2, 3);
        cdc.addItem("item3", 4, false, 2, 3);
    }

    @org.junit.Test
    public void testUpdateDirection() throws Exception {
        cdc.addVirtualCharacter(11);
        cdc.addVirtualCharacter(121);
        cdc.updateDirection(11, 2);
        cdc.updateDirection(121, 3);

        Vector<Object> updateInfo = cdc.getUpdateInfo();
        assertEquals(2, updateInfo.size());
        Character character = (Character) updateInfo.get(0);
        assertEquals(2, character.dir);
        assertEquals(2, character.velocity);
        character = (Character) updateInfo.get(1);
        assertEquals(3, character.dir);
        assertEquals(2, character.velocity);
    }

    @org.junit.Test (expected = AssertionError.class)
    public void testNoClientnoUpdateDirection() throws Exception {
        cdc.addVirtualCharacter(11);
        cdc.addVirtualCharacter(121);

        cdc.updateDirection(111, 2);
    }

    @org.junit.Test (expected = AssertionError.class)
    public void testUpdateInvalidDirection() throws Exception {
        cdc.addVirtualCharacter(11);
        cdc.addVirtualCharacter(121);

        cdc.updateDirection(11, 5);
    }

    @org.junit.Test
    public void testGetSharedItem() throws Exception {
        cdc.addVirtualCharacter(2);
        cdc.addItem("item1", 2, true, 20, 20);

        cdc.getItem(2);

        Vector<Object> updateInfo = cdc.getUpdateInfo();
        Character character = (Character) updateInfo.get(0);
        assertEquals("item1", character.ownedItem.get(0).name);
    }

    @org.junit.Test
    public void testGetNonSharedItem() throws Exception{
        cdc.addVirtualCharacter(1);
        cdc.addItem("item", -1, false, 20, 20);

        cdc.getItem(1);

        Vector<Object> updateInfo = cdc.getUpdateInfo();
        Character character = (Character) updateInfo.get(0);
        assertEquals("item", character.ownedItem.get(0).name);
    }

    @org.junit.Test (expected = AssertionError.class)
    public void testClientnoGetItem() throws Exception {
        cdc.addVirtualCharacter(1);
        cdc.addItem("item", 2, true, 20, 20);

        cdc.getItem(3);
    }

    @org.junit.Test
    public void testGetUpdateInfo() throws Exception {
        cdc.addVirtualCharacter(1);
        cdc.addVirtualCharacter(2);
        cdc.addItem("item 1", -1, true, 3, 4);
        cdc.addItem(null, 2, false, 5, 1);

        Vector<Object> updateInfo = cdc.getUpdateInfo();
        Character character = (Character) updateInfo.get(0);
        assertEquals(1, character.clientNumber);
        assertEquals(20, character.x);
        assertEquals(20, character.y);
        assertEquals(0, character.dir);
        assertEquals(0, character.velocity);
        character = (Character) updateInfo.get(1);
        assertEquals(2, character.clientNumber);
        assertEquals(20, character.x);
        assertEquals(20, character.y);
        assertEquals(0, character.dir);
        assertEquals(0, character.velocity);
        Item item = (Item) updateInfo.get(2);
        assertEquals("item 1", item.name);
        assertEquals(-1, item.index);
        assertEquals(true, item.shared);
        assertEquals(3, item.x);
        assertEquals(4, item.y);
        item = (Item) updateInfo.get(3);
        assertEquals(null, item.name);
        assertEquals(2, item.index);
        assertEquals(false, item.shared);
        assertEquals(5, item.x);
        assertEquals(1, item.y);
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
        Thread.sleep(500);
        testThreadController.flag = true;
        Thread.sleep(2000);

        Vector<Object> updateInfo = cdc.getUpdateInfo();
        Character character = (Character) updateInfo.get(0);
        assertEquals(20, character.x);
        assertEquals(19, character.y);
        character = (Character) updateInfo.get(1);
        assertEquals(21, character.x);
        assertEquals(20, character.y);
        character = (Character) updateInfo.get(2);
        assertEquals(20, character.x);
        assertEquals(21, character.y);
        character = (Character) updateInfo.get(3);
        assertEquals(19, character.x);
        assertEquals(20, character.y);
    }
}