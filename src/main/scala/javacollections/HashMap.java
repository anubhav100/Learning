package javacollections;

public class HashMap {

    /** How Hashmap works??
     *  Hash Map is implemented as an array of nodes where each node has its hashcode,key,
     *  value,and pointer to next node in case hash code is found to be equal
     */

    /** How put method works
     * first hashcode of key is calculated and then index is calculated using formula index = hashcode & n-1
     * where n is the default size of hash map which is 16
     * if two hashcode is found to be same then they will mentain as the link list of nodes
     */

    /** How get method works
     * based on the key hash code is calcualted and then index using same formula as above then value so hashcode
     * is matched with the hashcode of node at given index if there is match then they respective key value is mathced
     * using the equals method
     */

    /** Note
     *  in case you are writing the equals method to compare the two udt objectsyou must write the hashcode implemtation as well
     *  because if we put these objects in hash map and then you try to get a value then there is a chance that hashcode which is still
     *  system generated do not match the hashcode in bucket
     */
}
