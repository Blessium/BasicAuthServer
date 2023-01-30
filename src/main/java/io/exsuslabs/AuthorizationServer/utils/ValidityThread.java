package io.exsuslabs.AuthorizationServer.utils;

import java.util.List;
import java.util.concurrent.Semaphore;

public class ValidityThread extends Thread {

    private static List<AccessToken> accessTokens;

    public ValidityThread(List<AccessToken> accessTokens) {
        ValidityThread.accessTokens = accessTokens;
    }

    @Override
    public void run() {
        while (true) {
            accessTokens.removeIf(AccessToken::isExpired);
        }
    }
}
