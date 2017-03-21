import com.mongodb.util.JSON;
import org.junit.Assert;
import org.junit.Test;

import web.DataService;

public class MainTest {
    DataService dataService = new DataService();

    @Test
    public void newTokenTest(){
        String string = null;

        Object object = dataService.getNewToken().getEntity();
        if(object instanceof String){
            string = (String) object;
        }
        Assert.assertTrue(string.contains("token"));
    }
    @Test
    public void postDataByExistingTokenTest(){
        String string = null;
        Object object;

        object = dataService.getNewToken().getEntity();
        if(object instanceof String){
            string = (String) object;
        }

        String token = (String) string.subSequence(12, 38);

        object  = dataService.postDataByToken(token, "{\"data\": [\"data\"]}").getEntity();
        if(object instanceof String){
            string = (String) object;
        }

        Assert.assertEquals("Succesfully inserted data.", string);
    }

    @Test
    public void postDataByNotExistingTokenTest(){
        String string = null;
        Object object;

        object  = dataService.postDataByToken("AAAAAAAAAAAAAAAA", "{\"data\": [\"data\"]}").getEntity();
        if(object instanceof String){
            string = (String) object;
        }

        Assert.assertEquals("Could not insert Data.", string);
    }

    @Test
    public void getDataByTokenTest(){
        //Moet bestaan in de database met data
        String token = "90eqkboadetbjd83eak1n8vphb";

        String string = null;

        Object object = dataService.getDataByToken(token).getEntity();
        if(object instanceof String){
            string = (String) object;
        }
        Assert.assertTrue(string.contains("_id"));
    }
}
