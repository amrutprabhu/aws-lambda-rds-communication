package com.amrut.prabhu;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.Map;

public class LambdaRDSCommunication implements RequestHandler<Map<String, String>, String> {
    Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public String handleRequest(Map<String, String> inputValues, Context context) {
        LambdaLogger logger = context.getLogger();
        // log execution details
        logger.log("ENVIRONMENT VARIABLES: " + gson.toJson(System.getenv()));
        logger.log("CONTEXT: " + gson.toJson(context));

        SessionFactory sessionFactory = DatabaseUtil.createSessionFactory();
        try (Session session = sessionFactory.openSession()) {
            logger.log("Session factory opened");
            User user = new User(1, "Amrut");
            session.beginTransaction();
            logger.log("Transaction created");
            session.save(user);
            session.getTransaction().commit();
            logger.log("Transaction committed");
        }
        return "200 OK";
    }

}
