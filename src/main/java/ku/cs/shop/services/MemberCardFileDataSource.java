package ku.cs.shop.services;

import ku.cs.shop.models.MemberCard;
import ku.cs.shop.models.MemberCardList;

import java.io.*;

public class MemberCardFileDataSource implements DataSource<MemberCardList> {
    private String fileDirectoryName;
    private String fileName;
    private MemberCardList memberCardList;

    public MemberCardFileDataSource(String fileDirectoryName, String fileName) {
        this.fileDirectoryName = fileDirectoryName;
        this.fileName = fileName;
        checkFileIsExisted();
    }

    private void checkFileIsExisted() {
        File file = new File(fileDirectoryName);
        if (!file.exists()) {
            file.mkdirs();
        }
        String filePath = fileDirectoryName + File.separator + fileName;
        file = new File(filePath);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.err.println("Cannot create " + filePath);
            }
        }
    }

    @Override
    public MemberCardList readData() {
        MemberCardList memberCardList = new MemberCardList();
        String filePath = fileDirectoryName + File.separator + fileName;
        File file = new File(filePath);
        FileReader reader = null;
        BufferedReader buffer = null;


        try {
            reader = new FileReader(file);
            buffer = new BufferedReader(reader);
            String line = "";
            while ((line = buffer.readLine()) != null) {
                String[] data = line.split(",");
                MemberCard memberCard = new MemberCard(data[0].trim(), data[1].trim(),Integer.parseInt(data[2].trim()));
                memberCard.setCumulativePurchase(Double.parseDouble(data[3].trim()));
                memberCard.setImagePath(data[4].trim());
                memberCardList.addCard(memberCard);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                buffer.close();
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }


        return memberCardList;
    }

    @Override
    public void writeData(MemberCardList memberCardList) {
        String filePath = fileDirectoryName + File.separator + fileName;
        File file = new File(filePath);
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(file);
            BufferedWriter writer = new BufferedWriter(fileWriter);

            for (MemberCard memberCard: memberCardList.getAllCards()){
                String line = memberCard.getName() + ","
                        + memberCard.getPhone() + ","
                        + memberCard.getStamp() + ","
                        + memberCard.getCumulativePurchase() + ","
                        + memberCard.getImagePath();
                writer.append(line);
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            System.err.println("Cannot write " + filePath);
        }
    }
}
