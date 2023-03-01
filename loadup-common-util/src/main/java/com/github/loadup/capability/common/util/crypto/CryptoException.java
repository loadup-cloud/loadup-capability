package com.github.loadup.capability.common.util.crypto;

import java.security.GeneralSecurityException;

public class CryptoException extends GeneralSecurityException {
    public CryptoException() {
        super();
    }

    public CryptoException(String message) {
        super(message);
    }

    public CryptoException(String message, Throwable cause) {
        super(message, cause);
    }

    public CryptoException(Throwable cause) {
        super(cause);
    }
}
