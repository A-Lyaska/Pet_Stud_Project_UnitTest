package com.learning.processing.DB;

public class Account {
    private final int userId;
    private int money;
    private int accountId;

    public Account(User user, int money, int accountId){
        this.userId = user.getUserId();
        this.money = money;
        this.accountId = accountId;
        user.getAccounts().put(accountId, this);
    }

    public int getUserId() {
        return userId;
    }

    public int getMoney() {
        return money;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setMoney(int money) {
        this.money = money;
    }
}
