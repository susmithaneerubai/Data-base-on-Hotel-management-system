//File: Menu.java
//
// This program illustrates the use of a menu, which would be the basis
// for constructing a larger program by adding more options, where each
// option is handled by a separate function.
//

import java.sql.*;
import java.util.Scanner;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.time.LocalDate;
public class Menu
{
public static void main(String[] args)
{
int choice;
Connection conn = null;
try
{
// Step 1: connect to the database server using a connection string.
String host = "cslab-db.cs.wichita.edu";
int port = 3306;
String database = "dbuser20_database";
String user = "dbuser20";
String password = "tOnGHerkIJOv";
String url = String.format("jdbc:mariadb://%s:%s/%s?user=%s&password=%s",
host, port, database, user, password);
conn = DriverManager.getConnection(url);
// Step 2: Display the menu and get the user response.
choice = PrintMenuAndGetResponse( );
// Step 3: Respond to the menu choice.
switch (choice)
{
case 1: // A choice of 1 is to print all student names, IDs, and
// their majors.
ListAllHotels(conn);
Break;
case 2: // A choice of 1 is to print all student names, IDs, and
// their majors.
GetCustomerReservation(conn);
Break;
case 3: // A choice of 1 is to print all student names, IDs, and
// their majors.
UpdateCheckOutDate(conn);
Break;
case 4: // To quit the program.
GettingAverageOfGuest(conn);
Break;
case 5: // To quit the program.
HighestServiceCost(conn);
Break;
case 6: // To quit the program.
PayBill(conn);
Break;
case 7: // To delete a customer.
PrintAllCustomer(conn);
DeleteCustomer(conn);
PrintAllCustomer(conn);
Break;
case 8: //add Service
PrintAllHotel(conn);
InsertNewService(conn);
PrintAllService(conn);
Break;
case 9: //delete Service
PrintAllHotel(conn);
DeleteService(conn);
PrintAllService(conn);
Break;
case 10: // To quit the program.
System.out.println("Exiting Program");
break;
default: // Illegal choice for integers other than 1, 2 and 3.
System.out.println("Illegal choice");
Break;
}
}
catch(SQLException{
e.printStackTrace();
}
finally
{
e)
// Step 4: Disconnect from the database server.
try
{
if (conn != null)
conn.close();
}
catch(SQLException e)
{
e.printStackTrace();
}
}
}
// This function controls the user interaction with the menu.
public static int PrintMenuAndGetResponse( )
{
Scanner keyboard = new Scanner(System.in);
int response;
System.out.println("Choose from one of the following options:");
System.out.println(" 1. List all hotel cities and number of hotels in each city: ");//good
System.out.println(" 2.Get number of Reservation for a customer: ");//good
System.out.println(" 3. Update no of Guests: ");
System.out.println(" 4. Average Number of Guests: ");
System.out.println(" 5. Highest costing service: ");
System.out.println(" 6. Pay bill: ");
System.out.println(" 7. Delete a customer: ");
System.out.println(" 8. Add a service in a certain hotel: ");
System.out.println(" 9. Remove a service in a certain hotel: ");
System.out.println(" 10. Quit the program%n");
System.out.print("Your choice ==> ");
response = keyboard.nextInt();
// Leave a blank line before printing the output response.
System.out.println( );
return response;
}
// print all customer
public static void PrintAllCustomer(Connection conn) throws SQLException
{
Statement stmt = conn.createStatement();
String qry = "select Customer_ID, First_Name, Last_Name "
+
" from Customer";
ResultSet rs = stmt.executeQuery(qry);
// Loop through the result set and print the output.
// First -- print the output column headings.
System.out.format("%n");
System.out.format("%-4s %12s %-12s%n", "ID", "First Name", "Last Name");
// Then -- print the body of the output table.
while (rs.next())
{
int sId = rs.getInt("Customer_ID");
String dFirst = rs.getString("First_Name");
String kLast = rs.getString("Last_Name");
System.out.format("%-4s %12s %-12s%n", sId, dFirst, kLast);
}
System.out.println();
rs.close();
}
//delete one customer
public static void DeleteCustomer(Connection conn)throws SQLException
{
System.out.println("Choose a customerID you want to delete: ");
Scanner keyboard = new Scanner(System.in);
int choosenID = keyboard.nextInt();
Statement stmt = conn.createStatement();
String qry = "select * from Customer where Customer_ID=" +
Integer.toString(choosenID);
ResultSet rs = stmt.executeQuery(qry);
if (rs.next() == false)
{
System.out.println("ResultSet in empty for the provided Customer_ID");
}
else
{
String qry1 = "DELETE FROM Customer"
+
" Where Customer_ID =" + Integer.toString(choosenID);
ResultSet rs1 = stmt.executeQuery(qry1);
System.out.println("Successful DELETE a customer");
rs1.close();
}
rs.close();
}
//print hotels
public static void PrintAllHotel(Connection conn) throws SQLException
{
Statement stmt = conn.createStatement();
String qry = "select Hotel_ID, Name"
+
" from Hotel";
ResultSet rs = stmt.executeQuery(qry);
// Loop through the result set and print the output.
// First -- print the output column headings.
System.out.format("%n");
System.out.format("%-4s %12s%n", "Hotel_ID", "Name");
// Then -- print the body of the output table.
while (rs.next())
{
int sId = rs.getInt("Hotel_ID");
String sName = rs.getString("Name");
System.out.format("%-4s %12s%n", sId, sName);
}
System.out.println();
rs.close();
}
//print service
public static void PrintAllService(Connection conn) throws SQLException
{
Statement stmt = conn.createStatement();
String qry = "select *"
+
" from Service";
ResultSet rs = stmt.executeQuery(qry);
// Loop through the result set and print the output.
// First -- print the output column headings.
System.out.format("%n");
System.out.format("%-12s %12s %-12s %12s%n", "Service_ID", "Service_Type",
"Service_Desc","Service_Charges");
// Then -- print the body of the output table.
while (rs.next())
{
int sId = rs.getInt("Service_ID");
String sType = rs.getString("Service_Type");
String sDesc = rs.getString("Service_Desc");
Float sCharge = rs.getFloat("Service_Charges");
System.out.format("%-12s %12s %-12s %12s%n", sId, sType, sDesc, sCharge);
}
System.out.println();
rs.close();
}
//adding service
public static void InsertNewService(Connection conn)throws SQLException
{
Statement stmt = conn.createStatement();
Scanner keyboard = new Scanner(System.in);
String ServiceType,ServiceDesc;
System.out.println("Which hotel will this new service apply? (enter one number)");
String hotelIDNumber = keyboard.nextLine();
System.out.println("Enter the service type: ");
ServiceType = keyboard.nextLine();
System.out.print("Enter the service description: ");
ServiceDesc = keyboard.nextLine();
;
System.out.println("Enter the service charge: ");
Float ServiceCharges = keyboard.nextFloat();
//Service insert
String qry = "INSERT INTO Service(Service_Type, Service_Desc, Service_Charges)"
+
" VALUE('"+ ServiceType +"','"+ ServiceDesc +"',"+ ServiceCharges +")";
ResultSet rs = stmt.executeQuery(qry);
System.out.println( );
rs.close();
//HotelService insert
//String qryHotelService = "INSERT INTO HotelService(HotelID, ServiceID)"
// +
// " VALUE("+ hotelIDNumber +","+ +")";
// ResultSet rsHotelService = stmt.executeQuery(qryHotelService);
//rsHotelService.close();
}
//delete one service
public static void DeleteService(Connection conn)throws SQLException
{
System.out.println("Choose a ServiceID you want to delete: ");
Statement stmt = conn.createStatement();
Scanner keyboard = new Scanner(System.in);
int choosenServiceID = keyboard.nextInt();
System.out.println("Which hotelID do you want this service to remove from? ");
int choosenHotelID = keyboard.nextInt();
//HotelService delete
String qryHotelService = "DELETE FROM HotelService"
+
" Where ServiceID =" + Integer.toString(choosenServiceID)
+
" AND HotelID =" + Integer.toString(choosenHotelID);
ResultSet rsHotelService = stmt.executeQuery(qryHotelService);
rsHotelService.close();
//Service delete
String qry = "DELETE FROM Service"
+
" Where Service_ID =" + Integer.toString(choosenServiceID);
ResultSet rs = stmt.executeQuery(qry);
System.out.println("Successful DELETE a customer");
rs.close();

}
public static void ListAllHotels(Connection conn) throws SQLException
{
Statement stmt = conn.createStatement();
String qry = "select City , count( Hotel_ID) as NumofHotels"
+
" from Hotel "
+
"group by City ";
ResultSet rs = stmt.executeQuery(qry);
// Loop through the result set and print the output.
// First -- print the output column headings.
System.out.format("%n");
System.out.format("%-12s %-20s%n", "City", "NumofHotels");
// Then -- print the body of the output table.
while (rs.next())
{
String Cityname = rs.getString("City");
int NumberofHotels = rs.getInt("NumofHotels");
System.out.format("%-12s %-20s%n", Cityname, NumberofHotels);
}
System.out.println();
rs.close();
}
public static void GetCustomerReservation(Connection conn)throws SQLException
{
Statement stmt = conn.createStatement();
//Scanner keyboard = new Scanner(System.in);
//String firstName;
//String lastName;
//System.out.println("Enter First Name: ");
//firstName= keyboard.nextLine();
//System.out.println("Enter Last Name: ");
//lastName= keyboard.nextLine();
String qry = "Select Customer_ID, First_Name, Last_Name, count(Book_ID) as BookIDS "
+
"from Hotel,Customer, Booking, Room"
+
" where Customer_ID=CustomerID"
+
" AND Room_ID=RoomID"
+
" AND Room.Hotel_ID=Hotel.Hotel_ID"
+
" Group by Customer_ID ;";
ResultSet rs = stmt.executeQuery(qry);
// Loop through the result set and print the output.
// First -- print the output column headings.
System.out.format("%n");
System.out.format("%-12s %4s %4s %-20s%n", "CustomerID",
"First_Name","Last_Name", "NoOfBookings");
// Then -- print the body of the output table.
while (rs.next())
{
int id = rs.getInt("Customer_ID");
String First_Name = rs.getString("First_Name");
String Last_Name = rs.getString("Last_Name");
int no = rs.getInt("BookIDS");
System.out.format("%-12s %12s %8s %-20s%n",id, First_Name, Last_Name, no);
}
System.out.println( );
rs.close();
}
public static void UpdateCheckOutDate(Connection conn) throws SQLException
{
Statement stmt = conn.createStatement();
Scanner keyboard = new Scanner(System.in);
int BookID;

System.out.println("Enter the bookingID: ");
BookID= keyboard.nextInt();
String qry = "select No_of_Guest"
+
" from Booking"
+
" where Book_ID="
+ BookID ;
// Loop through the result set and print the output.
// First -- print the output column headings.
System.out.println("original NoofGuests:");
ResultSet rs = stmt.executeQuery(qry);
if (rs.next())
{
int oldNoofGuests = rs.getInt("No_of_Guest");
System.out.format("%3d%n", oldNoofGuests);
}
int newNoofGuests;
System.out.println("Enter the NumberofGuests: ");
newNoofGuests= keyboard.nextInt();
String cmd = "update Booking set No_of_Guest=" + newNoofGuests + " where Book_ID="
+BookID ;
stmt.executeUpdate(cmd);
// Step 4: Show the changed MajorId of student number 1.
System.out.println("Here is the changed no of guests :");
rs = stmt.executeQuery(qry);
if (rs.next())
{
int noofguests = rs.getInt("No_of_Guest");
System.out.format("%3d%n", noofguests);
}
rs.close();
}
public static void GettingAverageOfGuest(Connection conn)throws SQLException
{
Statement stmt = conn.createStatement();

System.out.println("The average number of guest per hotel are: ");
String qry = "select Name, Avg(No_of_Guest) as Avg_No_of_Guest "
+
" from Booking, Hotel"
+
" Where HotelID = Hotel_ID"
+
" Group By HotelID";
ResultSet rs = stmt.executeQuery(qry);
// Loop through the result set and print the output.
// First -- print the output column headings.
System.out.format("%n");
System.out.format("%-12s %-12s %n", "Hotel Name", "Average of Guest");
// Then -- print the body of the output table.
while (rs.next())
{
String sName = rs.getString("Name");
int dAverage = rs.getInt("Avg_No_of_Guest");
System.out.format("%-12s %-12s%n", sName, dAverage);
}
System.out.println( );
rs.close();
}
public static void HighestServiceCost(Connection conn)throws SQLException
{
Statement stmt = conn.createStatement();
System.out.println("The most expensive service is: ");

String qry = "select Name, Max(Service_Charges) as Service_Charges"
+
" from Service, Hotel, HotelService"
+
" where Service_ID = ServiceID "
+
" and Hotel_ID = HotelID"
+" Group by Name";
ResultSet rs = stmt.executeQuery(qry);
// Loop through the result set and print the output.
// First -- print the output column headings.
System.out.format("%n");
System.out.format("%-12s %-6s%n", "HotelName", "Service_Charges");
// Then -- print the body of the output table.
while (rs.next())
{
String sName = rs.getString("Name");
String fCharge = rs.getString("Service_Charges");
System.out.format("%-12s %-6s%n", sName, fCharge);
}
System.out.println( );
rs.close();
}
public static void PayBill(Connection conn) throws SQLException
{
Statement stmt = conn.createStatement();
Scanner keyboard = new Scanner(System.in);
int BillID;
System.out.println("Enter the BillId: ");
String BillingId= keyboard.nextLine();
BillID= Integer.parseInt(BillingId);
String qry = "select * from Billing" ;
ResultSet rs = stmt.executeQuery(qry);
if (rs.next() == false)
{
System.out.println("ResultSet in empty for the provided billing ID");
}
else
{
System.out.print("Please Enter Payment type 1. CreditCard 2. DebitCard 3. Cash");
String optionline= keyboard.nextLine();
int option= Integer.parseInt(optionline);
if( option==3)
{
//String qry2= "select max(Payment_ID) as maxID from Payment" ;
//ResultSet rs1 = stmt.executeQuery(qry2);
//int id = rs1.getInt("maxID");
String cmd = "Insert into Payment (Exp_Date, CreditCardNo, CVC, Payment_Date,
NameOnCard, BillingID, Payment_MethodID) Values (NULL,NULL,
NULL,'2019-05-17',NULL,"+ BillID + ", 1)";
stmt.executeUpdate(cmd);
System.out.println("Thank you for your payment ");
String qry1 = "select Payment_ID, Exp_Date,CreditCardNo,CVC, Payment_Date,
NameOnCard from Payment";
ResultSet rs2 = stmt.executeQuery(qry1);
// Loop through the result set and print the output.
// First -- print the output column headings.
System.out.format("%n");
System.out.format("%-12s %4s %4s %4s %4s %-4s%n",
"Payment_ID","Exp_Date","CreditCard","CVC","Payment_Date", "NameOnCard");
// Then -- print the body of the output table.
while (rs2.next())

{
int ID = rs2.getInt("Payment_ID");
String ExpDate = rs2.getString("Exp_Date");
int CreditCard= rs2.getInt("CreditCardNo");
int Cvc= rs2.getInt("CVC");
Date Payment_Date= rs2.getDate("Payment_Date");
String NameonCard= rs2.getString("NameOnCard");
System.out.format("%-12s %4s %4s %4s %4s %-4s%n", ID, ExpDate, CreditCard,
Cvc, Payment_Date, NameonCard);
}
System.out.println();
rs2.close();
}
else if (option==2 ||option==1)
{
System.out.println("Enter Name on CardName");
String NameOnCardUser;
NameOnCardUser= keyboard.nextLine();
System.out.println("Enter CardNo");
String Card;
Card= keyboard.nextLine();
int CardNo= Integer.parseInt(Card);
System.out.println("Enter ExpDate");
String ExpDatefromUser;
ExpDatefromUser= keyboard.nextLine();
System.out.println("Enter CVC");
int CvcfromUser;
CvcfromUser= keyboard.nextInt();
String cmd = "Insert into Payment (Exp_Date, CreditCardNo, CVC, Payment_Date,
NameOnCard, BillingID, Payment_MethodID) Values (" +ExpDatefromUser+ "," + CardNo +
"," + CvcfromUser+ ", '2019-05-17',"+NameOnCardUser+ "," + BillID + ", 2)";
stmt.executeUpdate(cmd);
System.out.println("Thank you for your payment ");
String qry1 = "select Payment_ID, Exp_Date,CreditCardNo,CVC, Payment_Date,
NameOnCard from Payment";
36
ResultSet rs2 = stmt.executeQuery(qry1);
//Loop through the result set and print the output.
// First -- print the output column headings.
System.out.format("%n");
System.out.format("%-12s %4s %4s %4s %4s %-4s%n",
"Payment_ID","Exp_Date","CreditCard","CVC","Payment_Date", "NameOnCard");
// Then -- print the body of the output table.
while (rs2.next())
{
int ID = rs2.getInt("Payment_ID");
Date ExpDate = rs2.getDate("Exp_Date");
int CreditCard= rs2.getInt("CreditCardNo");
int Cvc= rs2.getInt("CVC");
Date Payment_Date= rs2.getDate("Payment_Date");
String NameonCard= rs2.getString("NameOnCard");
System.out.format("%-12s %4s %4s %4s %4s %-4s%n", ID, ExpDate, CreditCard,
Cvc, Payment_Date, NameonCard);
}
System.out.println();
rs2.close();
}
else
{
System.out.println("Wrong Payment Option");
}
}
rs.close();
}
}
