import java.util.ArrayList;

/**
 * Created by Sandy on 2015/12/4.
 */
public class Character {
    public int clientNumber;
    public int x;
    public int y;
    public int dir;
    public int velocity;//block / second
    public boolean updated;
    public ArrayList<Item> ownedItem;

    public Character(int clientNumber) {
        this.clientNumber = clientNumber;
        this.x = 20;
        this.y = 20;
        this.dir = 0;
        this.velocity = 0;
        this.updated = true;
        this.ownedItem = new ArrayList<Item>();
    }

    @Override
    public String toString(){
        return Integer.toString(clientNumber) + " " + Integer.toString(x) + " " + Integer.toString(y) + " "
                + Integer.toString(dir) + " " + Integer.toString(velocity) + " " + ownedItem.toString();
    }
}
