package com.pathbook.pathbook_api.exception;

public class StorageFileNotFoundException extends StorageException {
    public StorageFileNotFoundException(String filename) {
        this(filename, null);
    }

    public StorageFileNotFoundException(String filename, Throwable cause) {
        super("Could not read file: " + filename.toString(), cause);
    }
}
