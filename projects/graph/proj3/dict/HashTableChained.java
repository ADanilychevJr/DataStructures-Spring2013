/* HashTableChained.java */
package dict;
import list.*;
/**
 * HashTableChained implements a Dictionary as a hash table with chaining.
 * All objects used as keys must have a valid hashCode() method, which is
 * used to determine which bucket of the hash table an entry is stored in.
 * Each object's hashCode() is presumed to return an int between
 * Integer.MIN_VALUE and Integer.MAX_VALUE. The HashTableChained class
 * implements only the compression function, which maps the hash code to
 * a bucket in the table's range.
 *
 * DO NOT CHANGE ANY PROTOTYPES IN THIS FILE.
 **/
public class HashTableChained implements Dictionary {
	/**
	 * Place any data fields here.
	 **/
	protected int numEntries;
	protected DList[] hashArray;
	/** 
	 * Construct a new empty hash table intended to hold roughly sizeEstimate
	 * entries. (The precise number of buckets is up to you, but we recommend
	 * you use a prime number, and shoot for a load factor between 0.5 and 1.)
	 **/
	public HashTableChained(int sizeEstimate) {
		numEntries = 0; 
		int counter = 0;
		int size = numberOfBuckets(sizeEstimate);
		hashArray = new DList[size];
		while (counter < size){
			hashArray[counter] = new DList();
			counter ++;
		}
	}

	/**
	 * arrayLength returns the current length of hashArray
	 * @return an integer, which is the current length of hashArray
	 */
	public int arrayLength(){
		return hashArray.length;
	}
	/** 
	 * Construct a new empty hash table with a default size. Say, a prime in
	 * the neighborhood of 100.
	 **/
	public HashTableChained() {
		this(101);
	}
	/**
	 * Converts a hash code in the range Integer.MIN_VALUE...Integer.MAX_VALUE
	 * to a value in the range 0...(size of hash table) - 1.
	 *
	 * This function should have package protection (so we can test it), and
	 * should be used by insert, find, and remove.
	 **/
	int compFunction(int code) {
		int prime = generatePrime(hashArray.length*100);
		int temp = ((500*code+97)%prime)%(hashArray.length);
		if (temp < 0){
			return temp + hashArray.length;
		} else {
			return temp;
		}
	}
	/** 
	 * Returns the number of entries stored in the dictionary. Entries with
	 * the same key (or even the same key and value) each still count as
	 * a separate entry.
	 * @return number of entries in the dictionary.
	 **/
	public int size() {
		return numEntries;
	}
	/** 
	 * Tests if the dictionary is empty.
	 *
	 * @return true if the dictionary has no entries; false otherwise.
	 **/
	public boolean isEmpty() {
		return numEntries == 0;
	}
	/**
	 * Create a new Entry object referencing the input key and associated value,
	 * and insert the entry into the dictionary. Return a reference to the new
	 * entry. Multiple entries with the same key (or even the same key and
	 * value) can coexist in the dictionary.
	 *
	 * This method should run in O(1) time if the number of collisions is small.
	 *
	 * @param key the key by which the entry can be retrieved.
	 * @param value an arbitrary object.
	 * @return an entry containing the key and value.
	 **/
	public Entry insert(Object key, Object value) {
		if( ((double) size())/hashArray.length > .75){
			resize();
		}
		Entry e = new Entry();
		int bucket = Math.abs(compFunction(key.hashCode()));
		e.key = key;
		e.value = value;
		if(hashArray[bucket]==null) {
			hashArray[bucket] = new DList();
		}
		hashArray[bucket].insertBack(e);
		numEntries++;
		return e;
	}
	/** 
	 * Search for an entry with the specified key. If such an entry is found,
	 * return it; otherwise return null. If several entries have the specified
	 * key, choose one arbitrarily and return it.
	 *
	 * This method should run in O(1) time if the number of collisions is small.
	 *
	 * @param key the search key.
	 * @return an entry containing the key and an associated value, or null if
	 * no entry contains the specified key.
	 **/
	public Entry find(Object key) {
		int correctBucket = compFunction(key.hashCode());
		DList currentList = (DList) hashArray[correctBucket];
		if (currentList.length() == 0){ /** CHANGE ALEX MADE **/ //because each bucket has a dlist to begin with
			return null;
		}
		DListNode current = (DListNode) currentList.front();
		while(current != null){
			if(((Entry)current.item).key().equals(key)){
				return ((Entry) current.item);
			}else{
				current = currentList.next(current);
			}
		}
		return null;
	}
	/** 
	 * Remove an entry with the specified key. If such an entry is found,
	 * remove it from the table and return it; otherwise return null.
	 * If several entries have the specified key, choose one arbitrarily, then
	 * remove and return it.
	 *
	 * This method should run in O(1) time if the number of collisions is small.
	 *
	 * @param key the search key.
	 * @return an entry containing the key and an associated value, or null if
	 * no entry contains the specified key.
	 */
	public Entry remove(Object key) {
		int correctBucket = compFunction(key.hashCode());
		DList currentList = (DList) hashArray[correctBucket];
		DListNode current = (DListNode) currentList.front();
		while(current != null){
			if(((Entry)current.item).key().equals(key)){
				currentList.remove(current);
				numEntries--;
				return ((Entry) current.item);
			}else{
				current = currentList.next(current);
			}
		}
		return null;
	}
	/**
	 * resize() generates a new hashArray that is roughly twice as large
	 * but has a prime length. It then rehashes all of the old elements to 
	 * their new buckets
	 */
	public void resize(){
		System.out.println("Size before resizing: " + hashArray.length);
		int length = hashArray.length;
		int newSize = generatePrime(length * 2);//find the new size, should be a prime roughly twice as large 
		HashTableChained newTable = new HashTableChained(newSize);//make a new table
		int counter = 0;
		while(counter < length){
			if (hashArray[counter].length() > 0){
				Entry e = (Entry) hashArray[counter].front().item;
				Object key = e.key;
				int bucket = newTable.compFunction(key.hashCode());
				newTable.hashArray[bucket] = hashArray[counter];
			}
			counter++;
		}
		this.hashArray = newTable.hashArray;
		System.out.println("Size after resizing: " + hashArray.length);
	}
	/**
	 * Remove all entries from the dictionary.
	 */
	public void makeEmpty() {
		numEntries = 0;
		hashArray = new DList[hashArray.length];
	}

	/**
	 * generatePrime returns a prime number that is the closest prime number to
	 * numberNear
	 * @param numberNear is an integer that is roughly as large as the prime
	 * we want 
	 * @return an integer that is prime and is close to numberNear
	 */
	public int generatePrime(int numberNear){
		int tryPrime = numberNear;
		while(true){
			boolean isPrime = true;
			for(int divisors = 2; divisors*divisors<=tryPrime; divisors++){
				if(tryPrime%divisors==0){
					isPrime = false;
				}
			}
			if(isPrime){
				return tryPrime;
			}else{
				tryPrime++;
			}
		}
	}

	/**
	 * numberOfBuckets returns a prime number that is roughly sizeEstimate/.7
	 * @param sizeEstimate is an integer
	 * @return a prime number that is roughly sizeEstimate/.7
	 */
	public int numberOfBuckets(int sizeEstimate){
		double factor = .7;
		return generatePrime((int) (sizeEstimate/factor));
	}

}