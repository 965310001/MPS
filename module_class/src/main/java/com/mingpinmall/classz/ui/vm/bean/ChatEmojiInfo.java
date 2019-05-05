package com.mingpinmall.classz.ui.vm.bean;

public class ChatEmojiInfo {

    /**
     * 表情资源图片对应的ID
     */
    private int id;

    /**
     * 表情资源对应的文字描述
     */
    private String character;

    /**
     * 表情资源的文件名
     */
    private String faceName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ChatEmojiInfo() {
    }

    public ChatEmojiInfo(int id, String character) {
        this.id = id;
        this.character = character;
    }

    public String getCharacter() {
        return character;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public String getFaceName() {
        return faceName;
    }

    public void setFaceName(String faceName) {
        this.faceName = faceName;
    }
}
