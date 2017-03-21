package web;

import dataAccess.DataDAO;
import org.bson.Document;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;

@Path("/data")
public class DataService {
    private DataDAO dataDAO = DataDAO.getInstance();

    @GET
    @Path("/new_token")
    @Produces("application/json")
    public Response getNewToken(){
        try{
            String token = tokenGenerator();

            ArrayList<String> allTokens = dataDAO.getAllTokens();
            while(allTokens.contains(token)){
                token = tokenGenerator();
            }
            dataDAO.insertToken(token);
            return Response.status(201).entity("{\"token\" : \""+token+"\"}").build();
        } catch (Exception e){
            return Response.status(500).entity("Couldn't get new token!").build();
        }

    }

    @GET
    @Path("/{token}")
    @Produces("application/json")
    public Response getDataByToken(@PathParam("token") String token){
        try {
            Document doc = dataDAO.getDataByToken(token);
            return Response.status(200).entity(doc.toJson()).build();
        } catch (NullPointerException e){
            return Response.status(400).entity("No data found with that token!").build();
        }

    }

    @POST
    @Path("/{token}")
    @Consumes("application/json")
    @Produces("plain/text")
    public Response postDataByToken(@PathParam("token") String token, String json){
        try{
            //So unsafe, very bad
            @SuppressWarnings("unchecked")
            ArrayList<String> data = (ArrayList<String>) Document.parse(json).get("data");
            String message = dataDAO.insertData(token, data);
            return Response.status(200).entity(message).build();
        }catch (Exception e){
            return Response.status(500).entity("Could not insert Data.").build();
        }
    }

    private String tokenGenerator(){
        SecureRandom random = new SecureRandom();
        return new BigInteger(130, random).toString(32);
    }
}
