package ku.cs.shop.models;

import java.util.ArrayList;

public class MemberCardList {
    private ArrayList<MemberCard> cards;

    public MemberCardList() {
        // ใช้ new เพื่อสร้าง instance ของ ArrayList
        cards = new ArrayList<>();
    }
    public void addCard(MemberCard card) {
        // เรียก method add จาก ArrayList เพื่อเพิ่มข้อมูล
        cards.add(card);
    }
    public ArrayList<MemberCard> getAllCards() {
        return cards;
    }

}
