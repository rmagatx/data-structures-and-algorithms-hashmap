package hashmap;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Adventure {
    private HashMap<StringKey, Item> map;

    public Adventure(String fileName) {
        if (fileName == null) {
            illegalArgumentException();
        }

        try {
            FileReader fr = new FileReader(fileName);
            BufferedReader br = new BufferedReader(fr);
            String line;

            this.map = new HashMap<>();

            while ((line = br.readLine()) != null) {
                String[] value = line.split(", ");

                Item item = new Item(value[0], Integer.parseInt(value[1].trim()), Double.parseDouble(value[2]));
                StringKey key = new StringKey(item.getName());

                this.map.put(key, item);
            }

            br.close();
        } catch (IOException e) {
            illegalArgumentException();
        }
    }

    private void illegalArgumentException() {
        throw new IllegalArgumentException("Invalid fileName.");
    }

    public HashMap<StringKey, Item> getMap() {
        return this.map;
    }

    private String getValueWeight(int index) {
        String string;

        if (this.map.table[index].getValue().getWeight() == (int) this.map.table[index].getValue().getWeight()) {
            string = Integer.toString((int) this.map.table[index].getValue().getWeight());
        } else {
            string = Double.toString(this.map.table[index].getValue().getWeight());
        }

        return string;
    }

    public String printLootMap() {
        StringBuilder sb = new StringBuilder();
        List<String> items = new ArrayList<>();

        for (int i = 0; i < this.map.table.length; i++) {
            if (this.map.table[i] != null && this.map.table[i].getValue().getGoldPieces() != 0) {
                items.add(this.map.table[i].getValue().getName() + " is worth " +
                          this.map.table[i].getValue().getGoldPieces() + "gp" + " and weighs " +
                          getValueWeight(i) + "kg");
            }
        }

        Collections.sort(items);

        for (String item : items) {
            sb.append(item).append("\n");
        }

        return sb.toString();
    }
}
