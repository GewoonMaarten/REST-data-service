package web;

import dataAccess.DataDAO;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;

@Path("/")
public class dataService {
    private DataDAO dataDAO = DataDAO.getInstance();

    @GET
    @Path("/token/new")
    public Response getNewToken(){
        String token = tokenGenerator();

        ArrayList<String> allTokens = dataDAO.getAllTokens();
        while(allTokens.contains(token)){
            token = tokenGenerator();
        }

        return Response.status(200).entity(token).build();
    }



    private String tokenGenerator(){
        SecureRandom random = new SecureRandom();
        return new BigInteger(130, random).toString(32);
    }
}
