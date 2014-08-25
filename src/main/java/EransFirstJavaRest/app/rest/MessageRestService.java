package EransFirstJavaRest.app.rest;

import EransFirstJavaRest.app.Database.Database;
import EransFirstJavaRest.app.Model.Message;
import EransFirstJavaRest.app.Utils.ArrayWrapper;
import EransFirstJavaRest.app.Utils.ICallback;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

@Path("/message")
public class MessageRestService {

    public MessageRestService()
    {
        Database.RegisterOnDBInitialization(new InstantiateData());
    }

    private class InstantiateData implements ICallback
    {
        @Override
        public void OnCallback() {
            Message message1 = new Message();
            message1.autherName = "Eran Sagi";
            message1.email = "eran.sagi@hp.com";
            message1.TheMessage = "Hi there";
            message1.userID = 1234;
            Database.<Message>getInstance().create(message1);

            Message message2 = new Message();
            message2.autherName = "Sagi Eran";
            message2.email = "eransagi1@gmail.com";
            message2.TheMessage = "What's up";
            message2.userID = 4321;
            Database.<Message>getInstance().create(message2);
        }
    }


    @POST
    @Path("/")
    @Consumes("application/json")
    public Response AddMessage(Message message) {
        Database.<Message>getInstance().create(message);
        return Response.status(HttpURLConnection.HTTP_CREATED).entity(message).build();
    }

    @PUT
    @Path("/")
    @Consumes("application/json")
    public Response UpdateMessage(Message message) {

        if(message.userID == 0 || message.DbIdentifier == null){
            throw new WebApplicationException(HttpURLConnection.HTTP_BAD_REQUEST);
        }else
        {
            List<Message> theMessages = Database.<Message>getInstance().getAll();
            Message LocatedMessage = null;
            for (Message msg : theMessages)
            {
                if((msg.userID == message.userID) &&(msg.DbIdentifier.compareTo(message.DbIdentifier) == 0))
                {
                    LocatedMessage = msg;
                    break;
                }
            }
            if(LocatedMessage!=null){
                if(Database.<Message>getInstance().update(message) != null)
                {
                    return Response.status(HttpURLConnection.HTTP_OK).entity(message).build();
                }else{
                    throw new WebApplicationException(HttpURLConnection.HTTP_INTERNAL_ERROR);
                }
            }else{
                throw new WebApplicationException(HttpURLConnection.HTTP_NOT_FOUND);
            }
        }
    }


    @GET
    @Path("/")
    @Produces("application/json")
    public Response GetMessage(@QueryParam("userID") Integer userID,@QueryParam("DbIdentifier") Integer DbIdentifier) {


        if((userID != null) && (DbIdentifier != null)){
            throw new WebApplicationException(HttpURLConnection.HTTP_BAD_REQUEST);
        }else if((userID == null) && (DbIdentifier == null)){ // return all
            List<Message> theMessages = Database.<Message>getInstance().getAll();
            Message[] msgarr = new Message[theMessages.size()];
            msgarr = theMessages.toArray(msgarr);
            ArrayWrapper<Message> messages = new  ArrayWrapper<Message>(msgarr);
            return Response.status(HttpURLConnection.HTTP_OK).entity(messages).build();
        }else // query get by ID
        {
            List<Message> theMessages = Database.<Message>getInstance().getAll();

            List<Message> FoundMessages = new ArrayList<Message>();
            for (Message message : theMessages)
            {
                if(((userID!=null)&&(userID.compareTo(message.userID) == 0)) || // get by user ID
                        ((DbIdentifier!=null)&&(DbIdentifier.compareTo(message.DbIdentifier) == 0))) // get by DB ID
                {
                    FoundMessages.add(message);
                }
            }

            if(FoundMessages.size() !=0)
            {
                return Response.status(HttpURLConnection.HTTP_OK).entity(FoundMessages).build();
            }else
            {
                throw new WebApplicationException(HttpURLConnection.HTTP_NOT_FOUND);
            }
        }

    }

    @DELETE
    @Path("/")
    @Produces("application/json")
    public Response DeleteMessage(@QueryParam("DbIdentifier") Integer DbIdentifier) {
        if(DbIdentifier == null)
        {
            throw new WebApplicationException(HttpURLConnection.HTTP_BAD_REQUEST);
        }

        if(Database.<Message>getInstance().delete(DbIdentifier) == null)
        {
            throw new WebApplicationException(HttpURLConnection.HTTP_NOT_FOUND);
        }else
        {
            return Response.status(HttpURLConnection.HTTP_OK).build();
        }
    }
}

