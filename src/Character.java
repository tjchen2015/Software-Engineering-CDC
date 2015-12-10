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

    public Character(int clientNumber) {
        this.clientNumber = clientNumber;
        this.x = 0;
        this.y = 0;
        this.dir = 0;
        this.velocity = 0;
        this.updated = true;
    }

    public String[] toString(String command){
        String[] characterString = {Integer.toString(clientNumber), Integer.toString(x), Integer.toString(y),
                Integer.toString(dir), Integer.toString(velocity), command};

        return characterString;
    }
}
