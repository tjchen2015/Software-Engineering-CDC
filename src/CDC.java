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
    void addVirtualCharacter(int clientno){
        Character character = new Character(clientno);
        characterList.add(character);
    }

    //initialize
    void addItem(String name, int index, boolean shared, int x, int y){
        Item item = new Item(name, index, shared, x, y);
        itemList.add(item);
    }

    void updateDirection(int clientno, int moveCode){
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

    void getItem(int clientno){
        Iterator<Character> charIterator = characterList.iterator();
        while (charIterator.hasNext()){
            Character character = charIterator.next();
            if (character.clientNumber == clientno){
                checkItemStatus(character.x, character.y);
            }
        }
    }

    Vector getUpdateInfo(){
        Vector updatedInfo = new Vector();

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

    void startUpdatingThread(){
        UpdateThread updateThread = new UpdateThread(map, characterList);
        Thread thread = new Thread(updateThread);
        thread.start();
    }

    private void checkItemStatus(int checkX, int checkY){
        Iterator<Item> itemIterator = itemList.iterator();
        while (itemIterator.hasNext()){
            Item item = itemIterator.next();
            if (item.x==checkX && item.y==checkY){
                if ((item.shared && !item.owned) || !item.shared){
                    item.owned = true;
                    item.updated = true;
                }
                return;
            }
        }
    }
}
