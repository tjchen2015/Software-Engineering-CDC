/**
 * Created by Sandy on 2015/12/5.
 */
public class Item {
    public String name;
    public int index;
    public boolean shared;
    public int x;
    public int y;
    public boolean owned;
    public boolean updated;

    public Item(String name, int index, boolean shared, int x, int y) {
        this.name = name;
        this.index = index;
        this.shared = shared;//it can be owned by one or more character
        this.x = x;
        this.y = y;
        this.owned = false;
        this.updated = true;
    }

    public String[] toString(String command){
        String[] itemString = {name, Integer.toString(index), Boolean.toString(shared),
                Integer.toString(x), Integer.toString(y), Boolean.toString(owned), command};

         return itemString;
    }
}
