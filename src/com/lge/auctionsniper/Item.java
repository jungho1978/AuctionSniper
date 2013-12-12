package com.lge.auctionsniper;

public class Item {
    public String item;
    public int price;
    public int bid;
    public String status;

    public Item(String item, int price, int bid, String status) {
        this.item = item;
        this.price = price;
        this.bid = bid;
        this.status = status;
    }
}
