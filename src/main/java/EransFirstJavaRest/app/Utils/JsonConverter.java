package EransFirstJavaRest.app.Utils;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;

/**
 * Created by erans on 21-Aug-14.
 */
public class JsonConverter {
    public static String toJson(Object obj){
        String ret = new String();
        ObjectMapper mapper = new ObjectMapper();

        try{
            ret = mapper.writeValueAsString(obj);
        }catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ret;
    }

    public static Object fromJson(String JSon)
    {
        ObjectMapper mapper = new ObjectMapper();

        Object RetObject = null;

        try {

            // read from file, convert it to user class
            RetObject = mapper.readValue(JSon, Object.class);

        } catch (JsonGenerationException e) {

            e.printStackTrace();

        } catch (JsonMappingException e) {

            e.printStackTrace();

        } catch (IOException e) {

            e.printStackTrace();

        }

        return RetObject;
    }
}
