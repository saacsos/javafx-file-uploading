package ku.cs.shop.services;

import ku.cs.shop.models.MemberCard;
import ku.cs.shop.models.MemberCardList;

public
class MemberCardListHardCodeDataSource implements DataSource<MemberCardList> {
    private MemberCardList cardList;

    public MemberCardListHardCodeDataSource() {
        cardList = new MemberCardList();
    }

    @Override
    public MemberCardList readData() {
        cardList = new MemberCardList();
        MemberCard john = new MemberCard("John Smith", "081-222-8888");
        MemberCard anna = new MemberCard("Anna Frost", "082-333-9999", 135);
        MemberCard harry = new MemberCard("Harry Potter", "083-444-0000", 40000);
        cardList.addCard(john);
        cardList.addCard(anna);
        cardList.addCard(harry);
        cardList.addCard(new MemberCard("Charles Babbage", "091-777-6543", 800));
        return cardList;
    }

    @Override
    public void writeData(MemberCardList memberCardList) {
        this.cardList = memberCardList;
    }

}
