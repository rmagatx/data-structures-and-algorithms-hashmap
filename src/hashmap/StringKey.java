package hashmap;

import java.util.Objects;

public class StringKey implements Comparable<StringKey> {
    private String keyName;

    public String getKeyName() { return this.keyName; }

    public StringKey(String keyName) { this.keyName = keyName; }

    public boolean equals(Object obj) {
        if (obj == null) { return  false; }
        if (obj.getClass() != this.getClass()) { return false; }

        StringKey sk = (StringKey) obj;

        return Objects.equals(this.keyName, sk.getKeyName());
    }

    public int compareTo(StringKey other) { return this.keyName.compareTo(other.getKeyName()); }

    public String toString() {
        return String.format("KeyName: %s HashCode: %s", this.keyName, hashCode());
    }

    private int powerValue(int base, int exponent) {
        int result = 1;

        for (int i = 0; i < exponent; i++) {
            result = base * result;
        }

        return result;
    }

    public int hashCode() {
        int hashCode = 0;
        char[] keyName = this.keyName.toCharArray();

        for (int i = 0; i < keyName.length; i++) {
            hashCode += keyName[i] * powerValue(31, i);
        }

        return Math.abs(hashCode);
    }
}
