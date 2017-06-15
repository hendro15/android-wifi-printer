package com.example.sonic.hitiprinter.printerprotocol.printercommand;

import java.net.Socket;

public class Job {
    public String AlbumName;
    public String IP;
    public String Password;
    public int PhotoNumber;
    public String SSID;
    public String ThumbnailName;
    private int mAlbumId;
    public int mID;
    private Socket mSocket;
    private int mStorageId;
    private int mThumbnailId;
    private String mThumbnailPath;
    private String originalPath;
    public int port;

    public Job(Socket socket) {
        this.mID = 0;
        this.mSocket = null;
        this.mAlbumId = 0;
        this.mStorageId = 0;
        this.mThumbnailId = 0;
        this.AlbumName = null;
        this.PhotoNumber = 0;
        this.mThumbnailPath = null;
        this.ThumbnailName = null;
        this.originalPath = null;
        this.IP = null;
        this.port = 0;
        this.SSID = null;
        this.Password = null;
        this.mSocket = socket;
    }

    public Socket getSocket() {
        return this.mSocket;
    }

    public void setAlbumId(int albumId) {
        this.mAlbumId = albumId;
    }

    public int getAlbumId() {
        return this.mAlbumId;
    }

    public void setStorageId(int storageId) {
        this.mStorageId = storageId;
    }

    public int getStorageId() {
        return this.mStorageId;
    }

    public void setThumbnailId(int thumbnailId) {
        this.mThumbnailId = thumbnailId;
    }

    public int getThumbnailId() {
        return this.mThumbnailId;
    }

    public void setThumbnailPath(String thumbnailPath) {
        this.mThumbnailPath = thumbnailPath;
    }

    public String getThumbnailPath() {
        return this.mThumbnailPath;
    }

    public void setPhotoPath(String originalPath) {
        this.originalPath = originalPath;
    }

    public String getPhotoPath() {
        return this.originalPath;
    }
}
