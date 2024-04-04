package fr.cermak.gamesuite.utils;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import fr.cermak.gamesuite.controller.GameSuiteController;
import fr.cermak.gamesuite.socket.GameResponse;
import fr.cermak.gamesuite.utils.MessageMapping;
import lombok.Getter;

public class MessageHandler {
    private static MessageHandler single_instance = null;
    public static Map<String, Method> methodMap = new HashMap<>();

    public static synchronized MessageHandler getInstance()
    {
        if (single_instance == null)
            single_instance = new MessageHandler();

        return single_instance;
    }
    private MessageHandler(){
        for (Method method: GameSuiteController.class.getMethods()){
            if (method.isAnnotationPresent(MessageMapping.class)){
                MessageMapping annotation = method.getAnnotation(MessageMapping.class);
                methodMap.put(annotation.path(), method);
            }
        }
    }

    public void handleMessage(byte path, GameResponse message) throws Exception {
        Method method = methodMap.get(path);
        if (method != null) {
            method.invoke(null, message); // Assuming static methods, change null to handler instance for non-static methods
        } else {
            throw new IllegalArgumentException("No handler found for path: " + path);
        }
    }
}
