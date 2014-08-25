package EransFirstJavaRest.app.Model;

import EransFirstJavaRest.app.Database.DbEntity;
import EransFirstJavaRest.app.Utils.InstanceGetter;

/**
 * Created by erans on 21-Aug-14.
 */
public class Message extends DbEntity implements InstanceGetter<Message>  {
    public int userID;
    public String autherName;
    public String email;
    public String TheMessage;

    @Override
    public Message[] GetArrayInstance(int length) {
        Message[] returnArray = new Message[1];
        returnArray[0] = this;

        return returnArray;
    }
}
