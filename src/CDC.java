import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;

public class CDC {
    private static ArrayList<Character> characterList;
    private static ArrayList<Item> itemList;
    private static int[][] map;
    private static ThreadController threadController;

    public CDC(ThreadController threadController) {
        characterList = new ArrayList<Character>(4);
        itemList = new ArrayList<Item>();

        this.threadController = threadController;
    }

    //initialize
    public void addVirtualCharacter (int clientno) throws ExceedMaxException {
        assert !clientnoExist(clientno) : "clientno already exists";

        if (characterList.size() == 4){//at most 4 characters
            throw new ExceedMaxException();
        }

        Character character = new Character(clientno);
        characterList.add(character);
    }

    //initialize
    public void addItem(String name, int index, boolean shared, int x, int y){
        //assert !itemNameExist(name, index) : "item name / index already exists";
        assert !itemPositionTaken(x, y) : "position taken by another item";

        Item item = new Item(name, index, shared, x, y);
        itemList.add(item);
    }

    public void updateDirection(int clientno, int moveCode){
        assert clientnoExist(clientno) : "clientno does not exist";
        assert (0<=moveCode && moveCode<4) : "invalid moveCode";

        Character character = getCertainCharacter(clientno);
        character.dir = moveCode;
        character.velocity = 2;//????????????
        character.updated = true;
    }

    public void getItem(int clientno){
        assert clientnoExist(clientno) : "clientno does not exist";

        Character character = getCertainCharacter(clientno);
        Iterator<Item> itemIterator = itemList.iterator();
        while (itemIterator.hasNext()) {
            Item item = itemIterator.next();
            if (item.x == character.x && item.y == character.y) {
                if (item.shared && !item.owned){//shared item and not owned
                    item.owned = true;
                    item.updated = true;
                }
                else if (!item.shared) {//not shared item
                    item.updated = true;//????????????
                }
                character.ownedItem.add(item);
                break;
            }
        }
    }

    public Vector<Object> getUpdateInfo(){
        Vector<Object> updatedInfo = new Vector<Object>();

        Iterator<Character> characterIterator = characterList.iterator();
        while (characterIterator.hasNext()){
            Character character = characterIterator.next();
            if (character.updated){
                updatedInfo.add(character);
                character.updated = false;
            }
        }

        Iterator<Item> itemIterator = itemList.iterator();
        while (itemIterator.hasNext()){
            Item item = itemIterator.next();
            if (item.updated){
                updatedInfo.add(item);
                item.updated = false;
            }
        }

        return updatedInfo;
    }

    public void startUpdatingThread(){
        UpdateThread updateThread = new UpdateThread(map, characterList);
        updateThread.setThreadController(threadController);

        Thread thread = new Thread(updateThread);
        thread.start();
    }

    private Character getCertainCharacter(int clientno){
        Iterator<Character> charIterator = characterList.iterator();
        while (charIterator.hasNext()) {
            Character character = charIterator.next();
            if (character.clientNumber == clientno) {
                return character;
            }
        }
        throw new AssertionError("Character with clientno cannot be found");
    }

    private boolean clientnoExist(int clientno){
        Iterator<Character> charIterator = characterList.iterator();
        while (charIterator.hasNext()) {
            Character character = charIterator.next();
            if (character.clientNumber == clientno) {
                return true;
            }
        }
        return false;
    }

    private boolean itemNameExist(String name, int index){
        Iterator<Item> itemIterator = itemList.iterator();
        while (itemIterator.hasNext()){
            Item item = itemIterator.next();
            if (item.name.equals(name) || item.index==index){
                return true;
            }
        }
        return false;
    }

    private boolean itemPositionTaken(int checkX, int checkY){
        Iterator<Item> itemIterator = itemList.iterator();
        while (itemIterator.hasNext()){
            Item item = itemIterator.next();
            if (item.x==checkX && item.y==checkY){
                return true;
            }
        }
        return false;
    }
}
