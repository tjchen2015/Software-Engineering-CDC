import org.junit.Before;

import java.util.Vector;

import static org.junit.Assert.*;

public class CDCTest {
    CDC cdc;

    @org.junit.Before
    public void CDC() throws Exception{
        cdc = new CDC();
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
        assertEquals(character.x, 0);
        assertEquals(character.y, 0);
        assertEquals(character.dir, 0);
        assertEquals(character.velocity, 0);
        character = (Character) updateInfo.get(1);
        assertEquals(character.clientNumber, 2);
        assertEquals(character.x, 0);
        assertEquals(character.y, 0);
        assertEquals(character.dir, 0);
        assertEquals(character.velocity, 0);
        character = (Character) updateInfo.get(2);
        assertEquals(character.clientNumber, 3);
        assertEquals(character.x, 0);
        assertEquals(character.y, 0);
        assertEquals(character.dir, 0);
        assertEquals(character.velocity, 0);
        /*character = (Character) updateInfo.get(3);
        assertEquals(character.clientNumber, 4);
        assertEquals(character.x, 0);
        assertEquals(character.y, 0);
        assertEquals(character.dir, 0);
        assertEquals(character.velocity, 0);*/
    }

    @org.junit.Test
    public void testAddItem() throws Exception {
        cdc.addItem("item1", Integer.parseInt(null), true, 1, 1);//null?????????
        cdc.addItem(null, 2, false, 2, 2);

        Vector<Object> updateInfo = cdc.getUpdateInfo();
        Item item = (Item) updateInfo.get(0);
        assertEquals(updateInfo.size(), 2);
        assertEquals(item.name, "item1");
        assertEquals(item.index, null);//????????
        assertEquals(item.shared, true);
        assertEquals(item.x, 1);
        assertEquals(item.y, 1);
    }

    @org.junit.Test
    public void testUpdateDirection() throws Exception {
        cdc.addVirtualCharacter(1);
        //cdc.updateDirection(1, 2);

        Vector<Object> updateInfo = cdc.getUpdateInfo();
        assertEquals(updateInfo.size(), 0);
        Character character = (Character) updateInfo.get(2);
        assertEquals(character.dir, 0);
    }

    @org.junit.Test
    public void testGetItem() throws Exception {
        cdc.addVirtualCharacter(2);
        cdc.addItem("item1", Integer.parseInt(null), true, 0, 0);

        cdc.getItem(2);
    }

    @org.junit.Test
    public void testGetUpdateInfo() throws Exception {
        Vector<Object> updateInfo = cdc.getUpdateInfo();
        assertTrue(updateInfo.isEmpty());
    }

    @org.junit.Test
    public void testStartUpdatingThread() throws Exception {

    }
}