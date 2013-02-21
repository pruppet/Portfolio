package Assn2;
import java.io.*;

/*
 * Author: Maggie Laidlaw
 * Date: Fab 19, 2012
 * 
 * An implementation of a Binary search tree and AVL tree made for an Assignment. 
 */

public class BinarySearchTree 
{
	public static BinaryTreeVertex root;
	public static int count = 0;
	
	public BinarySearchTree()
	{
		root = null; 
	}
	
	//Inserts a value into a binary tree
	public void insert(String value)
	{
		Boolean placed = false;
		
		if(root == null)
		{
			root = new BinaryTreeVertex(value,null,null);
			placed = true;
			return;
		}
		BinaryTreeVertex current = root;
		
		
		while (placed == false)
		{
			//if the value being places is smaller than the current vertex
			//the value needs to be placed to the left
			if(value.compareTo(current.value) < 0)
			{
				//If no value in left child, place there
				if(current.left_child == null)
				{
					current.left_child = new BinaryTreeVertex(value,null,null);
					placed = true;
					//updateHeight(root);
				}
				else 
					current = current.left_child;
			}
			
			//if the value being places is larger than the current vertex
			//the value needs to be placed to the right
			else
			{
				//If no value in right child, place there
				if(current.right_child == null)
				{
					current.right_child = new BinaryTreeVertex(value,null,null);
					placed = true;
					//updateHeight(root);
				}
				else 
					current = current.right_child;
			}
			
		}
	}
	
    //Inserts a value in to a binary tree and balances the tree according to AVL tree logic.     
	public void insertAVL (String value)
	{
		Boolean placed = false;
		
		//if there is no root, make value the root.
		if(root == null)
		{
			root = new BinaryTreeVertex(value,null,null);
			count++;
			placed = true;
			root.height = 1;
			return;
		}
		BinaryTreeVertex current = root;
		BinaryTreeVertex past = null;
		BinaryTreeVertex gpast = null;
		String trace = "";
		
		int balance;
		//Inserts value and balances tree, and traces the path to placing the value
		while (placed == false)
		{
			//if the value being places is smaller than the current vertex
			//the value needs to be placed to the left
			if(value.compareTo(current.value) < 0)
			{
				trace += 'l';
				//If there is no value to the left, place it there and balance
				if(current.left_child == null)
				{
					count++;
					current.left_child = new BinaryTreeVertex(value,null,null);
					updateH();
					if(past != null)
						balance(root, trace,0);
					placed = true;
					updateH();
				}
				//Otherwise continue to the left
				else 
				{
					gpast = past;
					past = current;
					
					current = current.left_child;
				}
			}
			
			//if the value being places is larger than the current vertex
			//the value needs to be placed to the right
			else if (value.compareTo(current.value) > 0)
			{
				trace += 'r';
				//If there is no value to the right, place it there and balance
				if(current.right_child == null)
				{
					count++;
					current.right_child = new BinaryTreeVertex(value,null,null);
					updateH();
					if(past != null)
						balance(root,trace,0);
					placed = true;
					updateH();
					
				}
				//Otherwise continue to the right
				else 
				{
					gpast = past;
					past = current;
					
					current = current.right_child;
				}
			}
			//No case for where there are duplicates, do not place.
			else if(value.compareTo(current.value) == 0)
				placed = true;
			
		}
		
	}
	
	//Update height from the root.
	public static void updateH()
	{
		updateHeight(root);
	}
	
	public static BinaryTreeVertex lowBal = null;
	public static int lowIndex = -1;
	
	//Balances tree based on AVL tree logic.
	public static void balance (BinaryTreeVertex gpar, String trace, int index)
	{
		//Sets the variables
		if(gpar == root)
		{
			lowBal = null;
			lowIndex = -1;
		}
		int balance = checkBal(gpar);
		int tl = trace.length();
		BinaryTreeVertex ggpar = null;
		BinaryTreeVertex vert = null;
		
		//if balance is off at this vertex, set this as the lowest place 
		//where an off balance is found.
		if(balance > 1 || balance < -1)
		{
			lowBal = gpar;
			lowIndex = index;
		}
		
		
		//recursively go through the tree following the path until 
		//there is no chance of inbalance
		if(tl-index > 2)
		{
			
			if(trace.charAt(index) == 'r')
			{
				balance(gpar.right_child, trace, index+1);
			}
			else
			{
				balance(gpar.left_child, trace, index+1);
			}
		}
		
		//Balance at the lowest vertex where an inbalance is found.
		else
		{
			if(lowBal != null)
			{			
				if(trace.charAt(lowIndex+1) == 'r')
				{
					if(trace.charAt(lowIndex) == 'r')
						ggpar = right_right(lowBal);//Rotate right-right
					else
						ggpar = left_right(lowBal);//rotate left-right
				}
				else if (trace.charAt(lowIndex+1) == 'l')
				{
					if(trace.charAt(lowIndex) == 'l')
						ggpar = left_left(lowBal);//rotate left-left
					else
						ggpar = right_left(lowBal);//rotate right-left
				}
			
				//Re-attach the balanced sub tree on to the tree.
				vert = findParent(lowBal,trace);
				if(vert == null)
				{
					root = ggpar;
				}
				
				else
				{
					if(trace.charAt(index-1) == 'l')
						vert.left_child = ggpar;
					else
						vert.right_child = ggpar;	
				}
			}
		}	
	}
	
	//Finds parent of the given vertex and the trace.
	public static BinaryTreeVertex findParent (BinaryTreeVertex child,String trace)
	{
		BinaryTreeVertex v = root;
		if(v == child)
			return null;
		
		for(int i = 0; i < trace.length(); i++)
		{
			if(trace.charAt(i) == 'r')
			{
				if(v.right_child == child)
					return v;
				else 
					v = v.right_child;
			}
			
			else
			{
				if(v.left_child == child)
					return v;
				else
					v = v.left_child;
			}
		}
		return null;
		
	}
	
	//Check tha balance at the vertex v.
	public static int checkBal(BinaryTreeVertex v)
	{
		if(v.right_child == null)
			return (0 - v.left_child.height);
		else if(v.left_child == null)
			return v.right_child.height;
		else
			return (v.right_child.height - v.left_child.height);
	}
	
	
	//right right rotation
	public static BinaryTreeVertex right_right (BinaryTreeVertex vert)
	{
		BinaryTreeVertex temp;
		temp = vert.right_child;
		vert.right_child = temp.left_child;
		temp.left_child = vert;
		//vertPar.
		
		//updateHeight(temp);
		return temp;
	}
	
	//left left rotation
	public static BinaryTreeVertex left_left (BinaryTreeVertex vert)
	{
		BinaryTreeVertex temp;
		
		temp = vert.left_child;
		vert.left_child = temp.right_child;
		temp.right_child = vert;
		
		//updateHeight(temp);
		return temp;
	}
	
	//left right rotation
	public static BinaryTreeVertex left_right(BinaryTreeVertex vert)
	{
		vert.left_child = right_right(vert.left_child);
		System.out.println("XXXXXXXXX");
		return left_left(vert);
	}
	//right left rotation
	public static BinaryTreeVertex right_left (BinaryTreeVertex vert)
	{
		vert.right_child = left_left(vert.right_child);
		return right_right(vert);
	}
	
	//Updates the height from the vertex vert down.
	public static void updateHeight(BinaryTreeVertex vert)
	{
		if(vert.left_child == null && vert.right_child == null)
			vert.height = 1;
		else if(vert.left_child == null)
		{
			updateHeight(vert.right_child);
			vert.height = vert.right_child.height +1;
		}
		else if(vert.right_child == null)
		{
			updateHeight(vert.left_child);
			vert.height = vert.left_child.height +1;
		}
		else
		{
			updateHeight(vert.right_child);
			updateHeight(vert.left_child);
			vert.height = 1 + Math.max(vert.right_child.height, vert.left_child.height);
		}
	}
	
	//display the tree				
	public void display_tree() throws IOException
	{
		System.out.println();
		if (this.root == null)
			System.out.println("Tree is empty");
		else			
			this.rec_display_tree(this.root,"");
		System.out.println();
	}
	
	public void rec_display_tree(BinaryTreeVertex current,String trace) throws IOException
	{
		if (current != null)
		{
			//print the right subtree, indented
			this.rec_display_tree(current.right_child,trace+"r");
			
			try
			{
				FileWriter fw = new FileWriter("tree.txt", true);
				BufferedWriter bw = new BufferedWriter(fw);
			//print the current vertex's value
				if (current == this.root)
				{
				//if we are at the root, no indent is needed
					if (current.right_child != null)
					{
						System.out.println("*");
						bw.write("*");
						bw.newLine();
					}//if there is a right child, print a connecting symbol
				//print the value in the root	
					System.out.println(current.value);
					bw.write(current.value);
					bw.newLine();
				}
				
				else
				{
				//not at the root, so build a string representing all the
				//tree edges that we need to show when printing this
				//vertex
					String indent = "";
					for (int t = 0; t < trace.length()-1; t++)
					{
						if (trace.charAt(t) != trace.charAt(t+1))
							indent += "*\t";
						else
							indent += "\t";
					}
					if (trace.charAt(trace.length()-1) == 'l')
						indent += "*";
				
					String connect_indent;
				
					if (current.right_child != null)
						connect_indent = indent + "\t*"; //if there is a right child, add a connector 
					else
						connect_indent = indent;
				
					//print a line with all the connecting symbols
					//this is just for vertical spacing - it keeps the tree from being too cramped
					System.out.println(connect_indent);
					bw.write(connect_indent);
					bw.newLine();
				// print a line with all the connecting symbols, and the current value
				
					String spacer;
				
					if (trace.charAt(trace.length()-1) == 'l')
						spacer = "***";
					else
						spacer = "****";
				
					System.out.println(indent + spacer + current.value);
					bw.write(indent + spacer + current.value);
					bw.newLine();
					bw.close();
				}
			}catch(IOException ex)
			{
				System.out.println();
			}
			//print the left subtree, indented
			this.rec_display_tree(current.left_child,trace+"l");

		}//end if
	}//end rec_display
	
}

