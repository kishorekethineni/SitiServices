package com.example.android.sitiservices;
public class chatmessage {
    private boolean isimage,ismine;
    private String content;

    public chatmessage(boolean isimage, boolean ismine, String content) {
        this.isimage = isimage;
        this.ismine = ismine;
        this.content = content;
    }

    public boolean isIsimage() {
        return isimage;
    }

    public void setIsimage(boolean isimage) {
        this.isimage = isimage;
    }

    public boolean isIsmine() {
        return ismine;
    }

    public void setIsmine(boolean ismine) {
        this.ismine = ismine;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
