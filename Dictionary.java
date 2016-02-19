/**
 ******************************************************************************
 ******************************************************************************
 *                    Amortized Dictionary
 ******************************************************************************
 *
 * Implementation of an Amortized Array-Based Dictionary data structure.
 *
 * This data structure supports duplicates and does *NOT* support storage of
 * null references.
 *
 *****************************************************************************/

import static java.util.Arrays.binarySearch;
import java.util.Arrays;

public class Dictionary<E extends Comparable<E>>  implements DictionaryInterface<E>
{
	private int size;//Keeps track of the number of elements in the dictionary.
	private Node head;//The head reference to the linked list of Nodes.

	/* Creates an empty dictionary*/
	public Dictionary()
	{
		size = 0;
		head = null;
	}

	/*Adds item to the dictionary*/
	public void add(E item)
	{
		if(item == null)
		{
			throw new NullPointerException("Error passing null object to add");
		}


		/* item exists and has to be added to the linkedlist as a new node*/ 

		Comparable [] a= new Comparable [1];//converting item into array of comparable since the node constructor expects an array
		a[0]=item;

		head = new Node (a, head);//made head the new node for the incoming item //Adding new node to start of list
		size++;//incrementing Number of items in the Dictionary

		//check if merge is needed by checking size of each array and call mergeDown() if sizes are the same
		Node tmp1 = head;
		while(tmp1.next!=null)
		{	
			Node tmp2=tmp1.next;
			int size1=tmp1.returnSize();
			int size2=tmp2.returnSize();
			if(size1==size2)
			{
				/*Merge the arrays of Nodes tmp1 and tmp2 and delete tmp2*/
				mergeDown(size1,tmp1,tmp2);
				tmp1=head;
			}
			else
			{
				tmp1=tmp1.next;
			}
		}
	}

	/**
	 * Starting with the smallest array, mergeDown() merges arrays of the same size together
	 * This is very useful for implementing add(item)!
	 */
	private void mergeDown(int a, Node tmp1,Node tmp2)
	{
		Node tmp3=tmp2.next;
		int i=0,j=0,k=0;
		Comparable[] b= new Comparable[2*a];

		//Merge Both the arrays
		while((i<a)&&(j<a))
		{
			if(tmp1.array[i].compareTo(tmp2.array[j])>0)
			{
				b[k]=tmp2.array[j];
				j++;
			}
			else
			{
				b[k]=tmp1.array[i];
				i++;
			}
			k++;
		}

		while((i==a)&&(j<a)){
			b[k]=tmp2.array[j];
			j++;
			k++;
		}

		while((j==a)&&(i<a)){
			b[k]=tmp1.array[i];
			i++;
			k++;
		}

		tmp1.array=b;
		tmp1.next=tmp3;
	}


	/**
	 * Returns true if the dictionary contains an element equal to item, otherwise- false.
	 * Use the method contains() in the Node class.
	 */
	public boolean contains(E item)
	{
		if(item == null)
		{
			System.out.println("Exception shiiiiittttt");
			throw new NullPointerException("Error passing null object to contain");
		}
		Node tmp1 = head;
		while(tmp1!=null)
		{

			if(tmp1.contains(item))
			{
				return true;
			}
			tmp1=tmp1.next;
		}
		return false;
	}


	/**
	 * Returns the size of the dictionary.
	 */
	public int size()
	{
		return size;
	}

	/**
	 * Returns a helpful string representation of the dictionary.
	 */
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
		Node tmp = head;
		while(tmp != null)
		{
			sb.append( tmp.array.length + ": ");
			sb.append(tmp.toString());
			sb.append("\n");
			tmp = tmp.next;
		}
		return sb.toString();
	}



	/**
	 * Implementation of the underlying array-based data structure.
	 */
	@SuppressWarnings("unchecked")
		private static class Node
		{
			private Comparable[] array;
			private Node next;

			/**
			 * Creates an Node with the specified parameters.
			 */
			public Node(Comparable[] array, Node next)
			{
				this.array = array;
				this.next = next;
			}

			public int returnSize()
			{
				return array.length;
			}

			/**
			 * Returns	true, if there is an element in the array equal to item
			 * 			false, otherwise
			 */
			public boolean contains(Comparable item)
			{
				if(Arrays.binarySearch(array,item)>=0)
					return true;
				else
					return false;
			}

			/**
			 * Returns a useful representation of this Node.  (Note how this is used by Dictionary's toString()).
			 */
			public String toString()
			{
				return java.util.Arrays.toString(array);
			}
		}

}
