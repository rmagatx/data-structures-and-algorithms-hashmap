package test;

import hashmap.Entry;
import hashmap.HashMap;
import hashmap.Item;
import hashmap.StringKey;
import org.junit.jupiter.api.Test;
import java.util.Iterator;
import static org.junit.jupiter.api.Assertions.*;

class HashMapTest
{
    //regionConstructor Tests
    /// <summary>
    /// Test the HashMap NoArg constructor to make sure it sets the properties properly.
    /// </summary>
    @Test
    public void HashMapNoArgConstructorTest()
    {
        HashMap<StringKey, Item> newHashMap = new HashMap<StringKey, Item>();

        assertNotEquals(null, newHashMap);
        assertEquals(newHashMap.getTable().length, 11);
        assertEquals(newHashMap.size(), 0);

    }

    /// <summary>
    /// Test the HashMap OneArg constructor to make sure it creates an object.
    /// </summary>
    @Test
    public void HashMapOneArgConstructorTest()
    {
        HashMap<StringKey, Item> newHashMap = new HashMap<StringKey, Item>(5);

        assertNotEquals(null, newHashMap);
        assertEquals(newHashMap.getTable().length, 5);
        assertEquals(newHashMap.size(), 0);
    }

    /// <summary>
    /// Test the HashMap TwoArg Constructor to make sure it creates an object.
    /// </summary>
    @Test
    public void HashMapTwoArgConstructorTest()
    {
        HashMap<StringKey, Item> newHashMap = new HashMap<StringKey, Item>(7, 0.3);

        assertNotEquals(null, newHashMap);
        assertEquals(newHashMap.getTable().length, 7);
        assertEquals(newHashMap.size(), 0);
    }

    /// <summary>
    /// Test that the one arg constructor throws an exception if you pass in a zero size for initial capacity.
    /// </summary>
    @Test
    public void HashMapOneArgConstructorZeroTest()
    {
        Throwable exception = assertThrows(IllegalArgumentException.class,
                () -> new HashMap<StringKey, Item>(0) );
    }

    /// <summary>
    /// Test that the one arg constructor throws an exception if you pass in a negative size for initial capacity.
    /// </summary>
    @Test
    public void HashMapOneArgConstructorNegativeTest()
    {
        Throwable exception = assertThrows(IllegalArgumentException.class,
                () -> new HashMap<StringKey, Item>(-1) );
    }

    /// <summary>
    /// Test that the two arg constructor throws an exception if you pass in zero size for initial capacity.
    /// </summary>
    @Test
    public void HashMapTwoArgConstructorZeroSizeTest()
    {
        Throwable exception = assertThrows(IllegalArgumentException.class,
                () ->new HashMap<StringKey, Item>(0, 0.75));
    }

    /// <summary>
    /// Test that the two arg constructor throws an exception if you pass in a negative size for initial capacity.
    /// </summary>
    @Test
    public void HashMapTwoArgConstructorNegativeSizeTest()
    {
        Throwable exception = assertThrows(IllegalArgumentException.class,
                () ->new HashMap<StringKey, Item>(-1, 0.75));
    }

    /// <summary>
    /// Test that the two arg constructor throws an exception if you pass in a negative value for threshold.
    /// </summary>
    @Test
    public void HashMapTwoArgConstructorNegativeThresholdTest()
    {
        Throwable exception = assertThrows(IllegalArgumentException.class,
                () ->new HashMap<StringKey, Item>(1, -1));
    }

    /// <summary>
    /// Test that the two arg constructor throws an exception if you pass in value greater than 1 for initial capacity.
    /// </summary>
    @Test
    public void HashMapTwoArgConstructorTooLargeThresholdTest()
    {
        Throwable exception = assertThrows(IllegalArgumentException.class,
                () ->new HashMap<StringKey, Item>(0, 2));
    }
        //endregion

        //regionPut Test
    /// <summary>
    /// Test that put throws an exception when called with a null key value.
    /// </summary>
    @Test
    public void Put_Throws_Exception_With_Null_Key_Test()
    {
        HashMap<StringKey, Item> hashMap = new HashMap<StringKey, Item>();
        StringKey key = new StringKey("item");
        Item item = new Item("item", 1, 1.0);

        Throwable exception = assertThrows(NullPointerException.class,
                () -> hashMap.put(null, item));
    }

    /// <summary>
    /// Test that put throws an exception when called with a null value.
    /// </summary>
    @Test
    public void Put_Throws_Exception_With_Null_Value_Test()
    {
        HashMap<StringKey, Item> hashMap = new HashMap<StringKey, Item>();
        StringKey key = new StringKey("item");
        Item item = new Item("item", 1, 1.0);

        Throwable exception = assertThrows(NullPointerException.class,
                () -> hashMap.put(key, null));
    }

    /// <summary>
    /// Test that Put returns null when there is no collsion (not an update).
    /// </summary>
    @Test
    public void Put_Returns_Null_when_a_new_insert_Test()
    {
        HashMap<StringKey, Item> hashMap = new HashMap<StringKey, Item>();
        StringKey key = new StringKey("item");
        Item item = new Item("item", 1, 1.0);

        assertEquals(null, hashMap.put(key, item));
    }

    /// <summary>
    /// Test that Put increases the size of the hash.
    /// </summary>
    @Test
    public void Put_increases_size_Test()
    {
        HashMap<StringKey, Item> hashMap = new HashMap<StringKey, Item>();
        String Name = "item";
        StringKey key = new StringKey(Name);
        Item item = new Item("item", 1, 1.0);
        hashMap.put(key, item);

        assertEquals(1, hashMap.size());
    }

    /// <summary>
    /// Test that put adds the passed in Value to the hash.
    /// </summary>
    @Test
    public void Put_Adds_Value_To_Hash_Test()
    {
        HashMap<StringKey, Item> hashMap = new HashMap<StringKey, Item>();
        StringKey key = new StringKey("item");
        Item item = new Item("item", 1, 1.0);
        hashMap.put(key, item);

        Entry<StringKey, Item>[] table = hashMap.getTable();

        int bucket = key.hashCode() % table.length;

        assertEquals(item, table[bucket].getValue());
    }


    /// <summary>
    /// Test Put returns the replaced value when there is an Update.
    /// </summary>
    @Test
    public void Put_Returns_Replaced_Value_On_Update_Test()
    {
        HashMap<StringKey, Item> hashMap = new HashMap<StringKey, Item>();
        StringKey key = new StringKey("item");
        Item item01 = new Item("item", 1, 1.0);
        Item item02 = new Item("item", 2, 2.0);
        hashMap.put(key, item01);

        assertEquals(item01, hashMap.put(key, item02));
    }

    /// <summary>
    /// Test Put overwrites the value at the key when there is an Update.
    /// </summary>
    @Test
    public void Put_Overwrites_Value_At_Key_On_Update_Test()
    {
        HashMap<StringKey, Item> hashMap = new HashMap<StringKey, Item>();
        StringKey key = new StringKey("item");
        Item originalItem = new Item("item", 1, 1.0);
        Item newItem = new Item("item", 2, 2.0);
        hashMap.put(key, originalItem);
        Item oldValue = hashMap.put(key, newItem);

        Entry<StringKey, Item>[] table = hashMap.getTable();

        int bucket = key.hashCode() % table.length;

        assertEquals(newItem, table[bucket].getValue());

        // doing a thourough check that the OLD value is no longer in the map anywhere
        boolean isFound = false;
        for (int i = 0; i < table.length; i++)
        {
            if (table[i] != null && table[i].getValue().equals(oldValue) && table[i].getKey().equals(key))
            {
                isFound = true;
            }
        }

        assertFalse(isFound);
    }

    /// <summary>
    /// Test Put dosn't increase size when there is an Update (not an insert)
    /// </summary>
    @Test
    public void Put_Does_Not_Increase_Size_On_Update_Test()
    {
        HashMap<StringKey, Item> hashMap = new HashMap<StringKey, Item>();
        StringKey key = new StringKey("item");
        Item item01 = new Item("item", 1, 1.0);
        Item item02 = new Item("item", 2, 2.0);

        hashMap.put(key, item01);

        assertEquals(1, hashMap.size());

        hashMap.put(key, item02);

        assertEquals(1, hashMap.size());
    }

    /// <summary>
    /// Test Put uses linear probing on collisions Test
    /// </summary>
    @Test
    public void Put_uses_linear_probing_on_collisions_Test()
    {
        HashMap<StringKey, Item> hashMap = new HashMap<StringKey, Item>();
        Entry<StringKey, Item>[] table = hashMap.getTable();

        // try these if quiver/diamond doesn't collide!
        Item item1Value = new Item("Purse", 10, 1);
        StringKey item1Key = new StringKey(item1Value.getName());

        Item item2Value = new Item("Helmet", 3, 2.5);
        StringKey item2Key = new StringKey(item2Value.getName());

//        Item item1Value = new Item("Quiver", 10, 3);
//        StringKey item1Key = new StringKey(item1Value.getName());
//
//        Item item2Value = new Item("Diamond", 500, 0.5);
//        StringKey item2Key = new StringKey(item2Value.getName());

        hashMap.put(item1Key, item1Value);

        int bucket1 = item1Key.hashCode() % table.length;
        int bucket2 = item2Key.hashCode() % table.length;

        // check to see if there is a collision
        assertEquals(bucket1, bucket2);

        // causes a collision, test for linear probing here!
        hashMap.put(item2Key, item2Value);

        // The second item is in the next available index
        assertEquals(item2Value, table[((bucket1+1) % table.length)].getValue());
    }

    /// <summary>
    /// Test constructors for an increase in the table resize at 75% of 11
    /// </summary>
    @Test
    public void Put_increases_table_size_on_default_constructor_at_75_percent_of_11_Test()
    {
        double loadFactor = 0.75; // default
        int capacity = 11; // default;

        HashMap<StringKey, Item> hashMap = new HashMap<StringKey, Item>();
        int threshold = (int)(capacity * loadFactor);
        int i = 0;
        for (i = 0; i < threshold-1; i++)
        {
            hashMap.put(new StringKey("item"+i), new Item("item"+i, i, 0.0+ i));
        }

        // just before the threshold, the table should still be the same
        assertEquals(capacity, hashMap.getTable().length);
        assertEquals(threshold - 1, hashMap.size());

        hashMap.put(new StringKey("item" + i), new Item("item" + i, i, 0.0 + i));

        // the next prime after 11 is 11*2 = 22... and 22+1 is 23!
        assertEquals(23, hashMap.getTable().length);
        assertEquals(threshold, hashMap.size());
    }

    /// <summary>
    /// Test constructors for an increase in the table resize at 75% of 5
    /// </summary>
    @Test
    public void Put_increases_table_size_on_custom_size_constructor_at_75_percent_Test()
    {
        double loadFactor = 0.75; // default
        int capacity = 5; // default;

        HashMap<StringKey, Item> hashMap = new HashMap<StringKey, Item>(capacity);
        int threshold = (int)(capacity * loadFactor);
        int i = 0;
        for (i = 0; i < threshold - 1; i++)
        {
            hashMap.put(new StringKey("item" + i), new Item("item" + i, i, 0.0 + i));
        }

        // just before the threshold, the table should still be the same
        assertEquals(capacity, hashMap.getTable().length);
        assertEquals(threshold - 1, hashMap.size());

        hashMap.put(new StringKey("item" + i), new Item("item" + i, i, 0.0 + i));

        // the next prime after 5 is 5*2 = 10... and 10+1 is 1!
        assertEquals(11, hashMap.getTable().length);
        assertEquals(threshold, hashMap.size());
    }

    /// <summary>
    /// Test constructors for an increase in the table resize at 50% of 7
    /// </summary>
    @Test
    public void Put_increases_table_size_on_custom_capacity_with_a_custom_loadfactor_Test()
    {
        double loadFactor = 0.5; // default
        int capacity = 7; // default;

        HashMap<StringKey, Item> hashMap = new HashMap<StringKey, Item>(capacity, loadFactor);
        int threshold = (int)(capacity * loadFactor);
        int i = 0;
        for (i = 0; i < threshold - 1; i++)
        {
            hashMap.put(new StringKey("item" + i), new Item("item" + i, i, 0.0 + i));
        }

        // just before the threshold, the table should still be the same
        assertEquals(capacity, hashMap.getTable().length);
        assertEquals(threshold - 1, hashMap.size());

        hashMap.put(new StringKey("item" + i), new Item("item" + i, i, 0.0 + i));

        // the next prime after 7 is 7*2 = 14... and 14+1 is 15.. that's not prime, so 15+2 is 17!
        assertEquals(17, hashMap.getTable().length);
        assertEquals(threshold, hashMap.size());
    }
        //endregion

        //regionIsEmpty Test
    /// <summary>
    /// Test that IsEmpty returns true on an empty hash map.
    /// </summary>
    @Test
    public void HashMapIsEmptyReturnsTrueTest()
    {
        HashMap<StringKey, Item> hashMap = new HashMap<StringKey, Item>();

        assertTrue(hashMap.isEmpty());
    }

    /// <summary>
    /// Test that IsEmpty returns false on a hash map with contents.
    /// </summary>
    @Test
    public void HashMapIsEmptyReturnsFalseTest()
    {
        HashMap<StringKey, Item> hashMap = new HashMap<StringKey, Item>();
        hashMap.put(new StringKey("item"), new Item("item", 1, 1.0));

        assertFalse(hashMap.isEmpty());
    }
        //endregion

        //regionClear Test
    /// <summary>
    /// Test that Clear removes items from the HashMap.
    /// </summary>
    @Test
    public void ClearEmptiesTableTest()
    {
        HashMap<StringKey, Item> hashMap = new HashMap<StringKey, Item>();
        StringKey key = new StringKey("item");
        hashMap.put(key, new Item("item", 1, 1.0));

        // make sure to actually clear the hashmap before testing... :|
        hashMap.clear();

        assertEquals(null, hashMap.get(key));
        assertTrue(hashMap.isEmpty());
        assertEquals(0, hashMap.size());
    }
        //endregion

        //regionGet Tests
    /// <summary>
    /// Test Get returns the value that matches the key passed in.
    /// </summary>
    @Test
    public void Get_Retuns_Value_of_key_Test()
    {
        HashMap<StringKey, Item> hashMap = new HashMap<StringKey, Item>();
        StringKey key = new StringKey("item");
        Item item = new Item("item", 1, 1.0);
        hashMap.put(key, item);

        assertEquals(item, hashMap.get(key));
        assertEquals(1, hashMap.size()); // size should remain 1
    }

    /// <summary>
    /// Test Get returns null if the key doesn't exist in the hash.
    /// </summary>
    @Test
    public void Get_Retuns_Null_on_key_missing_from_hash_Test()
    {
        HashMap<StringKey, Item> hashMap = new HashMap<StringKey, Item>();
        StringKey key = new StringKey("item");
        Item item = new Item("item", 1, 1.0);

        assertEquals(null, hashMap.get(key));
    }
        //endregion
        //regionRemove Tests
    /// <summary>
    /// Test Remove decreases the size of the HashMap.
    /// </summary>
    @Test
    public void Remove_Decreases_Size_Test()
    {
        HashMap<StringKey, Item> hashMap = new HashMap<StringKey, Item>();
        StringKey key = new StringKey("item");
        Item item = new Item("item", 1, 1.0);
        hashMap.put(key, item);
        assertEquals(1, hashMap.size());
        hashMap.remove(key);
        assertEquals(0, hashMap.size());
    }

    /// <summary>
    /// Test Remove removes the key from the HashMap.
    /// </summary>
    @Test
    public void Remove_Removes_Key_From_Hash_Test()
    {
        HashMap<StringKey, Item> hashMap = new HashMap<StringKey, Item>();
        StringKey key = new StringKey("item");
        Item item = new Item("item", 1, 1.0);
        hashMap.put(key, item);
        hashMap.remove(key);

        assertEquals(null, hashMap.get(key));
    }

    /// <summary>
    /// Test Remove returns the removed value from the HashMap.
    /// </summary>
    @Test
    public void Remove_Returns_Removed_Value_Test()
    {
        HashMap<StringKey, Item> hashMap = new HashMap<StringKey, Item>();
        StringKey key = new StringKey("item");
        Item item = new Item("item", 1, 1.0);
        hashMap.put(key, item);
        Item returnedItem = hashMap.remove(key);

        assertEquals(item, returnedItem);
    }

    /// <summary>
    /// Test Remove returns null when removing a value that doesn't exist in the HashMap.
    /// </summary>
    @Test
    public void Remove_Returns_null_or_default_Value_when_no_match_exists_Test()
    {
        HashMap<StringKey, Item> hashMap = new HashMap<StringKey, Item>();
        StringKey key = new StringKey("item");

        // try to remove a value that doesn't exist:
        Item returnedItem = hashMap.remove(key);

        assertTrue(returnedItem == null);
    }

    /// <summary>
    /// Test that remove throws an exception when called with a null key value.
    /// </summary>
    @Test
    public void Remove_Throws_Exception_With_Null_Key_Test()
    {
        HashMap<StringKey, Item> hashMap = new HashMap<StringKey, Item>();

        Throwable exception = assertThrows(NullPointerException.class,
                () -> hashMap.remove(null));
    }

    /// <summary>
    /// Test that remove keeps a placeholder in the table
    /// </summary>
    @Test
    public void Remove_keeps_a_key_placeholder_Test()
    {
        HashMap<StringKey, Item> hashMap = new HashMap<StringKey, Item>();
        StringKey key = new StringKey("item");
        Item item = new Item("item", 1, 1.0);
        hashMap.put(key, item);
        Item returnedItem = hashMap.remove(key);

        Entry<StringKey, Item>[] table = hashMap.getTable();
        int bucket = key.hashCode() % table.length;

        // a placeholder will have an Entry with a null for the value, but the key is stil the same as the removed
        assertEquals(table[bucket].getKey(), key);
        assertEquals(table[bucket].getValue(), null);
    }

    /// <summary>
    /// Removes cause placeholders to remain in the table, test to ensure that
    /// a resize still occurs even when items are removed
    /// </summary>
    @Test
    public void Remove_placeholders_count_towards_resize_Test()
    {
        double loadFactor = 0.5; // default
        int capacity = 7; // default;

        HashMap<StringKey, Item> hashMap = new HashMap<StringKey, Item>(capacity, loadFactor);
        int threshold = (int)(capacity * loadFactor);
        int i = 0;
        for (i = 0; i < threshold - 1; i++)
        {
            hashMap.put(new StringKey("item" + i), new Item("item" + i, i, 0.0 + i));
        }

        // just before the threshold, the table should still be the same
        assertEquals(capacity, hashMap.getTable().length);
        assertEquals(threshold - 1, hashMap.size());

        hashMap.remove(new StringKey("item1"));
        assertEquals(threshold - 2, hashMap.size());

        hashMap.put(new StringKey("item" + i), new Item("item" + i, i, 0.0 + i));

        // the next prime after 7 is 7*2 = 14... and 14+1 is 15.. that's not prime, so 15+2 is 17; which is prime!
        assertEquals(17, hashMap.getTable().length);
        assertEquals(threshold - 1, hashMap.size());
    }
        //endregion

        //regionKeys()
    /// <summary>
    /// Keys returns an Iterator of all the keys in the hashmap.
    /// </summary>
    @Test
    public void Keys_returns_list_of_keys_Test()
    {
        HashMap<StringKey, Item> hashMap = new HashMap<StringKey, Item>();

        for (int i = 0; i < 15; i++)
        {
            hashMap.put(new StringKey("item" + i), new Item("item" + i, i, 0.0 + i));
        }

        // get the keys for the map
        Iterator keys = hashMap.keys();
        int count = 0;
        while (keys.hasNext())
        {
            StringKey currentKey = (StringKey)keys.next();
            // look up each item in the hashmap, using the keys, they should all be there!
            Item currItem = hashMap.get(currentKey);
            assertNotNull(currItem);
            count++;
        }

        assertEquals(hashMap.size(), count);
    }
        //endregion


        //regionValues()
    /// <summary>
    /// Values returns an Iterator of all the Values in the hashmap.
    /// </summary>
    @Test
    public void Values_returns_list_of_Values_Test()
    {
        HashMap<StringKey, Item> hashMap = new HashMap<StringKey, Item>();

        for (int i = 0; i < 15; i++)
        {
            hashMap.put(new StringKey("item" + i), new Item("item" + i, i, 0.0 + i));
        }

        Entry<StringKey, Item>[] table = hashMap.getTable();

        // get the keys for the map
        Iterator values = hashMap.values();
        int count = 0;

        // loop through the values of the hashmap values
        while (values.hasNext())
        {
            Item currValue = (Item)values.next();

            StringKey currKey = new StringKey(currValue.getName());

            Item matchValue = hashMap.get(currKey);

            // The returned value should be the value we looked up with get, based on the same key name
            assertEquals(matchValue, currValue);

            count++;
        }

        // The count of the values should be the same as the puts into the table earlier in the test.
        assertEquals(hashMap.size(), count);
    }
        //endregion
}