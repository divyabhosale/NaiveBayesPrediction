/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mobileshopeesystem;
import com.datastax.driver.core.*;
import java.util.*;
/**
 *
 * @author divya_000
 */
public class MobileShopeeManagementSystem_1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
    
  

    int ch,ch1;
    String k=null;
    Scanner s=new Scanner(System.in);
    Cluster cluster = Cluster.builder().addContactPoint("127.0.0.1").build();
    Session session=cluster.connect();
    while(true)
    {
    
        
    System.out.println("Mobile Shopee Management System");
        System.out.println("1. Login");
        System.out.println("2. Register\n3. Exit");
        System.out.println("Enter your choice");
    ch1=s.nextInt();
    
    switch(ch1)
    {
        case 1: System.out.println("Enter Username: "); 
            String uname=s.next();
            System.out.println("Enter Password: "); 
            String pass=s.next();
            String q4="select uname, pass from registration where uname='"+uname+"' "+"and pass='"+pass+"'";
              Session s3=cluster.connect("mobiledata");
              ResultSet rs= s3.execute(q4);
            if(rs.isExhausted())
            {
                System.out.println("Error in login");
                break;
            }
            else
            {
                System.out.println("Welcome");
                System.out.println("Enter the mobile brand that u want to search for (IN NUMBERS):");
                System.out.println("1. Samsung\n2. Motorola\n3. Lenovo\n3. Intex\n4. Oppo\n5. MI\n6. Nokia\n7. HTC");
                int brand=s.nextInt();
                
                switch(brand)
                {
                    case 1: String sam="select * from mobiledetails where company=Samsung";
                            Session samsung=cluster.connect("mobiledata");
                        ResultSet rs1=samsung.execute(sam);
                        if(rs1.isExhausted())
                        {
                            System.out.println("Samsung phones not available");
                        }
                    else
                        {
                            System.out.println("\n"+rs1.all());    
                        }
                    
                    case 2: String moto="select * from mobiledetails where company=Motorola";
                            Session motorola=cluster.connect("mobiledata");
                        ResultSet rs2=motorola.execute(moto);
                        if(rs2.isExhausted())
                        {
                            System.out.println("Motorola phones not available");
                        }
                    else
                        {
                            System.out.println("\n"+rs2.all());    
                        }   
                }
                continue;
            }
        
        case 2: System.out.println("Registration Form");
            System.out.println("Enter name: ");
            String name=s.next();
            System.out.println("Enter address: ");
            String adds=s.next();
            System.out.println("Enter email: ");
            String email=s.next();
            System.out.println("Enter mobile: ");
            String mobile=s.next();
            System.out.println("Enter username: ");
            String username=s.next();
            System.out.println("Enter password: ");
            String password=s.next();
             Session s2=cluster.connect("mobiledata");//connect to keyspace
            String q3="insert into registration(name,mobile,email,address,uname,pass) values('"
                    +name+"','"+mobile+"','"+email+"','"+adds+"','"+username+"','"+password+"');";
                    
            s2.execute(q3);
            System.out.println("Data Inserted Sucessfully");
            continue;
        case 3: return;
    }
    }
    }
}

                        
                        
                        
                        
                        
                        
                
            
    
    
    
    
   



