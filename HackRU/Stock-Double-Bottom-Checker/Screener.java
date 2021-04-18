//package screener;

import java.io.*;  
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/*
 *
 * Our Program Tries To Scan CSV Market Data And Identify Double Bottoms At PRICE
 * Test Program For HackRU
 *
 */

public class Screener {

	public static double[] arr;
    public static String[] arr2;
	public static int csvLength = 0;
	public static File file = null;
	public static Scanner sc = null;
	public static String csvData = "";
	public static String fName = "";
    public static int temp = 0;
    public static double firstTemp = 0;
    public static double secondTemp = 0;
    


	public static void main(String[] args) throws Exception {  
		
		fName = "AMRN.csv";
		csvLengthChecker();
		System.out.println("Double Bottom Checker Free");
		TimeUnit.SECONDS.sleep(1);
		System.out.println("Made by Vansh Sharma and Anthony Zatika");
		System.out.println("...");
		TimeUnit.SECONDS.sleep(1);
		System.out.println("Number of Days utilized: " + csvLength);
		

		
		//System.out.println(csvLength);

		arr = new double[csvLength];
        arr2 = new String[csvLength];
		sc = new Scanner(file);
		csvData = sc.nextLine();

		for (int i = 0; i < csvLength-1; i++)
		{
			csvData = sc.nextLine();
			String[] values = csvData.split(",");

			//System.out.println("We're getting the " + values[3]);

			// we do whatever we do....
			double temp = Double.parseDouble(values[3]);

			arr[i] = temp;

            String str = values[0];
            arr2[i] = str;
			//System.out.println(str);
			//System.out.println("lol" + csvLength);

			// Perform out logic
			
		}
        int var = arr2.length - 2;
		System.out.println("Time Frame: " + arr2[0] + " - " + arr2[var]);
		TimeUnit.SECONDS.sleep(3);
		final double startTime = System.nanoTime();
        int temp2 = 0;
        for (int j = 0; j < arr.length; j++)
			{
				double higher = arr[j] + 1;
				double lower = arr[j] - 1;
				for (int k = 0; k < arr.length; k++)
				{
					if (arr[k] >= lower && arr[k] <= higher) //looks for other numbers that fall between higher and lower.
					{
						checker(j, k); //checker algorithm to confirm that they're not right next to each other
					}
                    temp2++;
                    //System.out.println("Loop Counter: " + temp2);
				} 
			}
	
			sc.close();
			final double endTime = System.nanoTime();
			final double runtime = (endTime - startTime)/1000000000;

			System.out.println("Results brought to you in: " + runtime + " seconds");
	}

	public static double percent(double val, double percentage) { //calculates percentage
		double per = (val * percentage / 100);
		return per;
	}

	public static void csvLengthChecker()
	{
		try 
		{
			file = new File(fName);
			sc = new Scanner(new FileInputStream(file));
			while (sc.hasNextLine()) {
				csvLength++;
				sc.nextLine();
			}

		}
		catch (FileNotFoundException e){
			System.out.println("File not found");
			e.printStackTrace();
		}
		sc.close();
		sc = null;
	}

	public static boolean checker(int j, int k)
	{
		boolean first = false;
		boolean second = false;
		boolean third = false;
		boolean fourth = false;

		//System.out.println("I'm here at checker...");

		int buffer;
		if (j < 10)
		{
			buffer = j;
		}
		else
		{
			buffer = 10;
		}
		int counter = 0;
		for (int x = j - buffer; x < j; x++) //checking for before the first value
		{

			if (arr[x] > arr[j])
			{
				counter++;
			}
			if (counter >= buffer + 1)
			{
				first = true;
			}
            else
            {
                first = false;
            }   
		}

		//System.out.println("Checker test one complete");
		int counter2 = 0;
		for (int y = j+1; y < k; y++)
		{
			
			if (arr[y] > arr[j] && arr[y] > arr[k]) //Checking between the first and the second value
			{
				counter2++;
			}
			if(counter2 == (k-j))
			{
				second = true;
			}
            else
            {
                second = false;
            }
		}

		//System.out.println("Checker test two complete");

		int buffer2;
		if (k > csvLength - 11)
	
		{
			buffer2 = csvLength-k;
		}
		else
		{
			buffer2 = 11;
		}

		int counter3 = 0;
		for (int z = k+1; z < (k+buffer2); z++) //Checking after the second value
		{
			if (arr[z] > arr[k])
			{
				counter3++;
			}

			if (counter3 == buffer2 - 1)
			{
				third = true;
			}
            else
            {
                third = false;
            }
		}

		//System.out.println("Checker test three complete");

		if (k-j >= 15)
		{
			fourth = true;
		}

		//System.out.println("Checker test four complete");

        temp++;
        //System.out.println("Count: " + temp);

        
		if (first == true && second == true && third == true && fourth == true) 
		{
            //if (firstTemp == arr[j] && secondTemp == arr[k])
            //{
                //return false;
            //}
            if ((firstTemp >= (arr[j]-0.5) && firstTemp <= (arr[j]+0.5)) || (secondTemp >= (arr[k]-0.5) && secondTemp <= (arr[k]+0.5)))
            {
                return false; // conf
            }
            else
            {
                firstTemp = arr[j];
                secondTemp = arr[k];
                System.out.println("Double bottom found -- Value 1: " +  arr[j] + " | Value 2: " + arr[k] + " | Gap: " + (k-j) + " days | Date1: " + arr2[j] + " | Date 2: " + arr2[k]);
			    return true;
            }
			
		}
		else
		{
			return false;
		}
	}
}
