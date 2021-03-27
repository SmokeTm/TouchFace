package com.codex.touchface;

public class UserPost {
    public String id;
    public String nickname;
    public String email;
    public String password;
    public String role;
    public String ban_reason;
    public String status;
    public String ava_url;
    public int lvl_role;
    public int money;
    public int friends;
    public int subscribers;
    public int ban;
    public int network_lvl;
    public int background;
    public int lvl;
    public int lr;

    public UserPost(String id, String nick, String email, String password, String ban_reason, String status, String ava_url, int lvl_role, int money, int friends, int subscribers, int ban, int network_lvl, int background, int lvl, int lr) {
    }


    public UserPost(String id, String nickname, String email, String password, String role, String ban_reason, String status, String ava_url, int lvl_role, int money, int friends, int subscribers, int ban, int network_lvl, int background, int lvl, int lr) {
        this.id = id;
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.role = role;
        this.ban_reason = ban_reason;
        this.status = status;
        this.ava_url = ava_url;
        this.lvl_role = lvl_role;
        this.money = money;
        this.friends = friends;
        this.subscribers = subscribers;
        this.ban = ban;
        this.network_lvl = network_lvl;
        this.background = background;
        this.lvl = lvl;
        this.lr = lr;
    }
}
