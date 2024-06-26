package fr.cermak.gamesuite.util;

import java.lang.reflect.Method;

import fr.cermak.gamesuite.controller.GameSuiteController;
import fr.cermak.gamesuite.socket.GameResponse;

public class MessageHandler {
    private static MessageHandler single_instance = null;
    public Method[] methodMap = new Method[256];
    private GameSuiteController controller;

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
                methodMap[ByteUtil.toUnsignedInt(annotation.path())] = method;
            }
        }

        controller = new GameSuiteController();
    }

    public GameResponse handleMessage(int path, byte[] data) throws Exception {
        Method method = methodMap[path];
        if (method != null) {
            return (GameResponse) method.invoke(controller, data);
        } else {
            throw new IllegalArgumentException("No handler found for path: " + path);
        }
    }
}
