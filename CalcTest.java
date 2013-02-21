package calcTest;

//Author: Maggie Laidlaw
//Back end of a very simple Calculator App
public class CalcTest {

	public static String operation;
	public static double op1;
	public static double op2;
	
	public static void main(String[] args) 
	{
		operation = "1+2*3n";
		equals();
		System.out.println(operation);
	}

	public static void equals()
	{
		int c1;
		int c2;
		double result = -1;
		for (int i = 0; i < operation.length(); i++)
		{
			switch(operation.charAt(i))
			{
			case 'n':
			case 'r':
			{
				c1 = getNum(-1,i);//returns double version of number before operator
				switch(operation.charAt(i))
				{
				case 'n':
					result = CalculatorTest.negate(op1);
					break;
				case 'r':
					result = CalculatorTest.root(op1);
					break;
				}
				changeOperation (i, c1, 0, result);
			}
			break;
			}
		}
		//By PEMDAS, Multiply/Divide first:
		for (int i = 0; i < operation.length(); i++)
		{
			switch (operation.charAt(i))
			{
			case '*':
			case '/':
			case 'm':
			{
				c1 = getNum(-1,i);//returns double version of number before operator
				c2 = getNum(1,i);//returns double version of number after operator
				switch (operation.charAt(i))
				{
					case '*':
						result = CalculatorTest.multiply(op1,op2);
						break;
					case '/':
						result = CalculatorTest.divide(op1,op2);
						break;
					case 'm':
						result = CalculatorTest.modulo(op1,op2);
						break;
				}
				changeOperation (i, c1, c2, result); 
			}
			break;
			case 'd':
			{
				c1 = getNum(-1,i);//returns double version of number before operator
				result = CalculatorTest.oneOverX(op1);
				changeOperation (i, c1, 0, result);
			}
			break;
			}
		}
		
		for (int i = 0; i < operation.length(); i++)
		{
			switch (operation.charAt(i))
			{
			case '+':
			case '-':
			{
				c1 = getNum(-1,i);//returns double version of number before operator
				c2 = getNum(1,i);//returns double version of number after operator
				switch (operation.charAt(i))
				{
					case '+':
						result = CalculatorTest.add(op1,op2);
						break;
					case '-':
						result = CalculatorTest.subtract(op1,op2);
						break;
				}
				changeOperation (i, c1, c2, result); 
			}
			break;
			}
		}
	}
		
	public static int getNum (int d, int pos)
	{
		String strOp = "";
		pos += d;
		while (pos >= 0 && pos < operation.length() && !isOperator(pos))
		{
			if(d == -1)
				strOp = operation.substring(pos,pos+1) + strOp;
			else
				strOp += operation.substring(pos,pos+1);
			pos += d;
		}
		
		if (strOp.charAt(0) == '!')
			strOp = "-"+ strOp.substring(1);
		
		if(d == -1)
			op1 = Double.parseDouble (strOp);
		else
			op2 = Double.parseDouble (strOp);
		
		return strOp.length();
	}
	
	public static Boolean isOperator (int pos)
	{
		switch (operation.charAt(pos))
		{
			case '*':
			case '/':
			case '+':
			case '-':
			case 'r':
			case 'd':
			case 'm':
			case 'n':
				return true;
			default: 
				return false;
		}
	}
	
	public static int changeOperation (int posOp, int s1, int s2, double result)
	{
		String strResult = "" + result;
		if (strResult.charAt(0) == '-')
			strResult = "!"+ strResult.substring(1);
		operation = operation.substring(0,posOp-s1) + strResult + operation.substring(posOp+s2+1);
		return posOp - s1 + strResult.length(); 
	}
}


