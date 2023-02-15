package hashmap;

import java.util.Objects;

public class Item implements Comparable<Item> {
    private String name;
    private int goldPieces;
    private double weight;

    public String getName() { return this.name; }

    public int getGoldPieces() { return this.goldPieces; }

    public double getWeight() { return this.weight; }

    public Item(String name, int goldPieces, double weight) {
        this.name = name;
        this.goldPieces = goldPieces;
        this.weight = weight;
    }

    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() != this.getClass() ) { return  false; }

        Item item = (Item) obj;

        return (Objects.equals(this.name, item.getName()) && this.goldPieces == item.getGoldPieces() && this.weight == item.getWeight());
    }

    public int compareTo(Item other) { return this.name.compareTo(other.getName()); }

    public String toString() {
        return String.format("%s is worth %sgp and weighs %skg", this.name, this.goldPieces, (int) this.weight);
    }
}
