package mobileshopeesystem;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.ResultSet;
import java.sql.*;
import java.util.*;

class NiveBayes
{
    public static void main(String args[])
    {
		try
		{
			/*
			c1 and c2 denote class 1 & class 2. 
			class1 :- Customer buys computer
			class2 :- Customer doesn't buy computer
			*/
                     Cluster cluster = Cluster.builder().addContactPoint("127.0.0.1").build();
    Session session=cluster.connect();
    Session s=cluster.connect();
			/*Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			Connection con = DriverManager.getConnection("jdbc:odbc:ecompany");
			Statement s = con.createStatement();*/
			String query = null;
			ResultSet rs = null;
			int c1=0 ,c2=0 ,n=0;

			query ="SELECT     COUNT(*) FROM   classify WHERE (class = 'yes') ";
			//s.execute(query);
                        ResultSet rs1=s.execute(query);
			String q=rs.all().toString().replace("Row[","").replace("[[", "").replace("]]", "").replaceAll(" ", "").replace("[", "");
			c1=Integer.parseInt(q);

			
			String query2 ="SELECT COUNT(*) FROM classify WHERE (class = 'no') ";
			//s.execute(query);
                        ResultSet rs2=s.execute(query2);
			String q2=rs2.all().toString().replace("Row[","").replace("[[", "").replace("]]", "").replaceAll(" ", "").replace("[", "");
			c2=Integer.parseInt(q2);
                        
			String query3 = "SELECT COUNT(*) FROM classify ";
			ResultSet rs3=s.execute(query3);
			String q3=rs3.all().toString().replace("Row[","").replace("[[", "").replace("]]", "").replaceAll(" ", "").replace("[", "");
			n=Integer.parseInt(q3);

			float pc1 = (float)c1/n; //General probability for class c1
			float pc2 = (float)c2/n; //General probability for class c2

			System.out.println("c1= " +c1 +"\nc2="+c2+"\ntotal="+n);
			System.out.println("p(c1)="+pc1);
			System.out.println("p(c2)="+pc2);

			Scanner sc = new Scanner(System.in);

			String age=null,income=null,gender=null,class1;
			
			// Accept the parameter values for which class is to be predicted
			
			/*System.out.println("Enter age: (youth/middle/senior)");
			age = sc.next();

			System.out.println("Enter income:(low/medium/high)");
			income = sc.next();

			System.out.println("Enter gender:(yes/no)");
			student = sc.next();

			System.out.println("Enter credit_rating:(fair/excellent)");
			credit_rating = sc.next();*/


			float pinc1=0,pinc2=0;
			//pinc1 = probability of prediction to be class1 (will buy)
			//pinc2 = probability of prediction to be class2  (will not buy)
				
			pinc1 = pfind(age,income,gender,"yes");
			pinc2 = pfind(age,income,gender,"no");

			pinc1 = pinc1 * pc1;
			pinc2 = pinc2 * pc2;

			// compare pinc1 & pinc2 and predict the class that user will or won't buy
			if(pinc1 > pinc2)
				System.out.println("He will buy computer");
			else
				System.out.println("He will not buy computer");
		}
		catch(Exception e)
		{
			System.out.println("Exception:"+ e);
		}
	}

	public static float pfind(String age,String income,String gender,String class1)
	{
		float ans = 0;
		try{
                    Cluster cluster = Cluster.builder().addContactPoint("127.0.0.1").build();
    Session session=cluster.connect();
    Session s=cluster.connect();
			
			String query = null;
			ResultSet rs = null;
			int a=0 , b=0 , c=0 , d=0 , total=0;
			
			/* 	Queries below are constructed using parameter values of age , income , student , credit_rating , class
				passed to function. Function finds probability for every individual parameter of provided class value 
				and using naive baye's theorem it calculates total probability */
			

			query ="SELECT COUNT(*) FROM classify WHERE (age = '"+ age + "' ) AND (class = '" +class1 +"') ";
			ResultSet rs2=s.execute(query);
			String q2=rs2.all().toString().replace("Row[","").replace("[[", "").replace("]]", "").replaceAll(" ", "").replace("[", "");
			a=Integer.parseInt(q2);
			// a = count of values in training set having age , class same as passed in argument

			 String query3 ="SELECT COUNT(*) FROM classify WHERE ( income = '"+ income + "' ) AND (class = '" +class1 +"') ";
			ResultSet rs3=s.execute(query3);
			String q3=rs3.all().toString().replace("Row[","").replace("[[", "").replace("]]", "").replaceAll(" ", "").replace("[", "");
			b=Integer.parseInt(q3);
			// b = count of values in training set having income , class same as passed in argument


			String query4 ="SELECT COUNT(*) FROM classify WHERE ( gender = '"+ gender + "' ) AND (class = '" +class1 +"') ";
			ResultSet rs4=s.execute(query4);
			String q4=rs4.all().toString().replace("Row[","").replace("[[", "").replace("]]", "").replaceAll(" ", "").replace("[", "");
			c=Integer.parseInt(q4);
			// c = count of values in training set having student , class same as passed in argument


			/*String query5 ="SELECT    COUNT(*) AS Expr1     FROM   customer WHERE   ( credit_rating = '"+ credit_rating + "' ) AND (class = '" +class1 +"')";
			ResultSet rs5=s.execute(query5);
			String q5=rs5.all().toString().replace("Row[","").replace("[[", "").replace("]]", "").replaceAll(" ", "").replace("[", "");
			d=Integer.parseInt(q5);*/
			// d = count of values in training set having credit_rating , class same as passed in argument
			String query6 ="SELECT COUNT(*) FROM classify WHERE (class = '" +class1 +"') ";
			ResultSet rs6=s.execute(query6);
			String q6=rs6.all().toString().replace("Row[","").replace("[[", "").replace("]]", "").replaceAll(" ", "").replace("[", "");
			total=Integer.parseInt(q6);
                        //total no resuults

			ans = (float)a / (float)total  * (float)b /(float)total * (float)c /(float)total /* (float)d /(float)total*/ ;
			//calculating total probability by naive bayes
		
			
		}
		catch(Exception e)
		{
			System.out.println("Exception:"+ e);
		}
		return ans;
	}
}