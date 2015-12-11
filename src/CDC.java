import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;

public class CDC {
    private static ArrayList<Character> characterList;
    private static ArrayList<Item> itemList;
    private static int[][] map;

    public CDC() {
        characterList = new ArrayList<Character>(4);
        itemList = new ArrayList<Item>();
    }

    //initialize
    public void addVirtualCharacter (int clientno) throws ExceedMaxException {
        assert clientnoExist(clientno);//check if clientno already exists

        if (characterList.size() == 4){//at most 4 characters
            throw new ExceedMaxException();
        }

        Character character = new Character(clientno);
        characterList.add(character);
    }

    //initialize
    public void addItem(String name, int index, boolean shared, int x, int y){
        assert !itemNameExist(name, index);//check if item name, index already exists

        Item item = new Item(name, index, shared, x, y);
        itemList.add(item);
    }

    public void updateDirection(int clientno, int moveCode){
        assert clientnoExist(clientno);//check if clientno exists
        assert 0<=moveCode && moveCode<4;//check if moveCode is valid (0~3)

        Iterator<Character> it = characterList.iterator();
        while (it.hasNext()){
            Character character = it.next();
            if (character.clientNumber == clientno){
                character.dir = moveCode;
                character.velocity = 2;//????????????
                character.updated = true;
                break;
            }
        }
    }

    public void getItem(int clientno){
        assert clientnoExist(clientno);//check if clientno exists

        Iterator<Character> characterIterator = characterList.iterator();
        while (characterIterator.hasNext()) {
            Character character = characterIterator.next();
            if (character.clientNumber == clientno) {

                Iterator<Item> itemIterator = itemList.iterator();
                while (itemIterator.hasNext()) {
                    Item item = itemIterator.next();
                    if (item.x == character.x && item.y == character.y) {
                        item.owned = true;
                        item.updated = true;
                    }
                }

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
        Thread thread = new Thread(updateThread);
        thread.start();
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

    private boolean itemExist(int checkX, int checkY){
        Iterator<Item> itemIterator = itemList.iterator();
        while (itemIterator.hasNext()){
            Item item = itemIterator.next();
            if (item.x==checkX && item.y==checkY){
                if ((item.shared && !item.owned) || !item.shared){
                    return true;
                }
                return false;
            }
        }
        return false;
    }
}
