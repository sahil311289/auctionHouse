import java.io.Console;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.*;
import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.*;

public class AuctionHouse {
	static String url = "jdbc:mysql://localhost:3306/auction_house?autoReconnect=true&useSSL=false";
	static String dbuname = "root";
	static String dbpass = "admin";
	static String classForName = "com.mysql.jdbc.Driver";

	public static void main(String[] args) throws Exception {

		while (true) {
			System.out.println("\b");
			System.out.println("*********  Welcome to Rita's Auction House  ***********");
			System.out.println("Please select your role to proceed:");
			System.out.println("1. Seller");
			System.out.println("2. Bidder");
			System.out.print("Please enter your choice: ");

			Scanner scan = new Scanner(System.in);
			String fOption = scan.next();
			char[] a = fOption.toCharArray();
			int asci = (int) a[0];

			switch (asci) {
			case 49:
				System.out.println("\n1. Sign-in");
				System.out.println("2. Register");
				System.out.print("Please enter your choice: ");
				int sOption = scan.nextInt();

				switch (sOption) {
				case 1:
					int auth = authenticate(49);
					switch (auth) {
					case 0:
						System.out.println("Username/Password did not match. Please try again.");
						break;
					default:
						int sellerOption = 0;
						while (sellerOption != 10) {
							System.out.println("\n Welcome back seller !");
							System.out.println("1. Submit item(s) for auction");
							System.out.println("2. Set/change base price for your submitted item");
							System.out.println("3. Set time duration for auction");
							System.out.println("4. View all the running auctions");
							System.out.println("5. View (approval) status of your items");
							System.out.println("6. View all the bids for your item");
							System.out.println("7. View earnings (after auction)");
							System.out.println("8. Withdraw item from auction");
							System.out.println("9. View withdrawn items");
							System.out.println("10. Exit");
							System.out.println("\b");
							System.out.print("Please enter your choice: ");
							sellerOption = scan.nextInt();
							switch (sellerOption) {
							case 1:
								sellerSubmitItem(auth);
								break;
							case 2:
								sellerSetBasePrice(auth);
								break;
							case 3:
								sellerSetTime(auth);
								break;
							case 4:
								sellerViewRunningAuctions(auth);
								break;
							case 5:
								sellerViewItemStatus(auth);
								break;
							case 6:
								sellerViewBids(auth);
								break;
							case 7:
								sellerViewSellerEarnings(auth);
								break;
							case 8:
								sellerWithdrawAuction(auth);
								break;
							case 9:
								sellerViewWithdrawnItems(auth);
								break;
							}
						}
					}
					break;
				case 2:
					int reg = register(49);
					if (!(reg > 0))
						System.out.println("Oops ! Something went wrong. Please try again after some time.");
					break;
				}
				break;
			case 50:
				System.out.println("\n1. Sign-in");
				System.out.println("2. Register");
				System.out.print("Please enter your choice: ");
				int bOption = scan.nextInt();

				switch (bOption) {
				case 1:
					int auth = authenticate(50);
					switch (auth) {
					case 0:
						System.out.println("Username/Password did not match. Please try again.");
						break;
					default:
						int bidderOption = 0;
						while (bidderOption != 5) {
							System.out.println("\n Welcome back bidder !");
							System.out.println("1. View available and running auctions");
							System.out.println("2. View my bids");
							System.out.println("3. Bid for an auction");
							System.out.println("4. View bought items");
							System.out.println("5. Exit");
							System.out.println("\b");
							System.out.print("Please enter your choice: ");
							bidderOption = scan.nextInt();
							switch (bidderOption) {
							case 1:
								bidderAvailAucs(auth);
								break;
							case 2:
								bidderMyBids(auth);
								break;
							case 3:
								bidderBid(auth);
								break;
							case 4:
								bidderBought(auth);
								break;
							}
						}
					}
					break;
				case 2:
					int reg = register(50);
					if (!(reg > 0))
						System.out.println("Oops ! Something went wrong. Please try again after some time.");
					break;
				}
				break;
			case 97:
				System.out.println("\b");
				System.out.println("Hello Rita !");
				int auth = authenticate(97);
				switch (auth) {
				case 0:
					System.out.println("Username/Password did not match. Please try again.");
					break;
				default:
					int adminOption = 0;
					while (adminOption != 6) {
						System.out.println("\n Welcome back admin !");
						System.out.println("1. Accept seller auction request");
						System.out.println("2. Start timer for an auction");
						System.out.println("3. View all running auctions and bids");
						System.out.println("4. View all sold products and earnings");
						System.out.println("5. View all withdrawn items from auction");
						System.out.println("6. Exit");
						System.out.println("\b");
						System.out.print("Please enter your choice: ");
						adminOption = scan.nextInt();
						switch (adminOption) {
						case 1:
							adminAcceptSeller();
							break;
						case 2:
							adminStartTimer();
							break;
						case 3:
							adminViewAucBid();
							break;
						case 4:
							adminViewSoldEarn();
							break;
						case 5:
							adminViewWithdrawn();
							break;
						}
					}
				}
				break;
			}
		}
	}

	private static void bidderBought(int auth) throws Exception {
		Class.forName(classForName);
		Connection con = DriverManager.getConnection(url, dbuname, dbpass);
		Statement st = con.createStatement();

		String query1 = "select id, pname, des, baseprice, BidValue from product where endTime < (select NOW()) and approved = 1 and lastBidder = "
				+ auth;
		ResultSet rs = st.executeQuery(query1);
		System.out.println("\b");
		int i = 0;
		while (rs.next()) {
			i++;
			if (i == 1) {
				System.out.printf("%3s%13s%20s%28s%15s%14s\n","S.No","Product ID","Product Name","Product Description","Base Price","Your Bid");
			}			
			System.out.printf("%2d%11d%23s%30s%13s%14s\n",i,rs.getInt(1),rs.getString(2),rs.getString(3),"$ "+rs.getString(4),"$ "+rs.getString(5));
		}
		if (i == 0) {
			System.out.println(
					"You haven't been sold any items yet. Reasons:\n1. You have not placed any bid.\n2. The auction is not over yet.\nPlease check back in some time.");
		}
		st.close();
		con.close();
	}

	private static void bidderBid(int auth) throws Exception {
		Scanner scan = new Scanner(System.in);
		Class.forName(classForName);
		Connection con = DriverManager.getConnection(url, dbuname, dbpass);
		Statement st = con.createStatement();

		String query1 = "select id, pname, des, baseprice, BidValue, endTime from product where endTime > (select NOW()) and approved = 1 ";
		ResultSet rs = st.executeQuery(query1);
		System.out.println("\b");
		int i = 0;
		LinkedHashMap lhm = new LinkedHashMap();
		while (rs.next()) {
			i++;
			if (i == 1) {
				System.out.printf("%5s%15s%30s%45s%23s%18s%25s\n","S.No","Product ID","Product Name","Product Description","Base Price","Last Bid","Valid For");
			}
			if (rs.getDouble(5) > rs.getDouble(4))
				lhm.put((int) rs.getInt(1), rs.getDouble(5));
			else
				lhm.put((int) rs.getInt(1), rs.getDouble(4));

			Date end = rs.getTimestamp(6);
			Date today = new Date();

			Calendar calendar = Calendar.getInstance();
			calendar.setTime(end);
			long endMili = calendar.getTimeInMillis();

			calendar.setTime(today);
			long todayMili = calendar.getTimeInMillis();
			long millis = endMili - todayMili;

			long days = TimeUnit.MILLISECONDS.toDays(millis);
			millis -= TimeUnit.DAYS.toMillis(days);
			long hours = TimeUnit.MILLISECONDS.toHours(millis);
			millis -= TimeUnit.HOURS.toMillis(hours);
			long minutes = TimeUnit.MILLISECONDS.toMinutes(millis);
			millis -= TimeUnit.MINUTES.toMillis(minutes);
			long seconds = TimeUnit.MILLISECONDS.toSeconds(millis);

			StringBuilder sb = new StringBuilder(64);
			if (days != 0) {
				sb.append(days);
				sb.append(" days ");
			}
			if (hours != 0) {
				sb.append(hours);
				sb.append(" hrs ");
			}
			if (minutes != 0) {
				sb.append(minutes);
				sb.append(" mins ");
			}
			if (seconds != 0) {
				sb.append(seconds);
				sb.append(" secs");
			}

			String duration = sb.toString();

			System.out.printf("%4s%12s%35s%50s%17s%17s%33s\n",i,rs.getInt(1),rs.getString(2),rs.getString(3),"$ "+rs.getString(4),"$ "+rs.getString(5),duration);
		}

		if (i > 0) {
			System.out.print("Product ID: ");
			int prodId = scan.nextInt();
			int myBid = 0;
			Set set = lhm.entrySet();
			Iterator itr = set.iterator();
			double val = 0;
			while (itr.hasNext()) {
				Map.Entry me = (Map.Entry) itr.next();
				if (prodId == (int) me.getKey()) {
					val = (double) me.getValue();
					break;
				}
			}

			System.out.print("Your Bid (with 2 decimal places): $ ");
			double newBid = scan.nextDouble();
			while (newBid < val) {
				System.out.println(
						"New bid cannot be lesser than the base price/original bid.\nPlease enter a bigger bid: $ ");
				newBid = scan.nextDouble();
			}

			String query2 = "update product set BidValue = " + newBid + ", lastBidder = '" + auth + "' where id = "
					+ prodId + " and endTime > (select NOW()) and approved = 1";
			int ret = st.executeUpdate(query2);

			switch (ret) {
			case 0:
				System.out.println("Cannot bid on this product now. \nPlease select another product.");
				break;
			case 1:
				String query3 = "insert into bidder_bids(uname, productId, bidValue, placed, wen) values (" + auth + ","
						+ prodId + "," + newBid + ", 1, (select NOW()))";
				int result = st.executeUpdate(query3);
				if (result == 1) {
					System.out.println("\b");
					System.out.println("Your bid has been successfully placed !");
				}
				break;
			}
		} else {
			System.out.println("\b");
			System.out.println("Sorry, no active auctions found!");
		}
		st.close();
		con.close();
	}

	private static void bidderMyBids(int auth) throws Exception {
		Class.forName(classForName);
		Connection con = DriverManager.getConnection(url, dbuname, dbpass);
		Statement st = con.createStatement();

		String query1 = "select B.id, B.productId, P.pname, B.bidValue , B.wen from bidder_bids B inner join product P on B.productId = P.id where P.approved = 1 and uname = "
				+ auth + " ORDER BY B.id DESC";
		ResultSet rs = st.executeQuery(query1);
		int i = 0;
		System.out.println("\b");
		while (rs.next()) {
			i++;
			if (i == 1) {
				System.out.printf("%3s%17s%14s%17s\n","S.No","Product Name","Bid Value","Time");
			}
			System.out.printf("%2d%19s%13s%27s\n",i,rs.getString(3),"$ "+rs.getString(4),rs.getString(5));
		}
		if (i == 0) {
			System.out.println("You don't have active bids at this moment!");
		}
		st.close();
		con.close();
	}

	private static void bidderAvailAucs(int auth) throws Exception {
		Scanner scan = new Scanner(System.in);
		Class.forName(classForName);
		Connection con = DriverManager.getConnection(url, dbuname, dbpass);
		Statement st = con.createStatement();

		String query1 = "select id, pname, des, baseprice, BidValue, endTime from product where endTime > (select NOW()) and approved = 1";
		ResultSet rs = st.executeQuery(query1);
		System.out.println("\b");
		int i = 0;
		while (rs.next()) {
			i++;
			if (i == 1) {
				System.out.printf("%5s%15s%30s%45s%23s%18s%25s\n","S.No","Product ID","Product Name","Product Description","Base Price","Last Bid","Valid For");
			}
			Date end = rs.getTimestamp(6);
			Date today = new Date();

			Calendar calendar = Calendar.getInstance();
			calendar.setTime(end);
			long endMili = calendar.getTimeInMillis();

			calendar.setTime(today);
			long todayMili = calendar.getTimeInMillis();
			long millis = endMili - todayMili;

			long days = TimeUnit.MILLISECONDS.toDays(millis);
			millis -= TimeUnit.DAYS.toMillis(days);
			long hours = TimeUnit.MILLISECONDS.toHours(millis);
			millis -= TimeUnit.HOURS.toMillis(hours);
			long minutes = TimeUnit.MILLISECONDS.toMinutes(millis);
			millis -= TimeUnit.MINUTES.toMillis(minutes);
			long seconds = TimeUnit.MILLISECONDS.toSeconds(millis);

			StringBuilder sb = new StringBuilder(64);
			if (days != 0) {
				sb.append(days);
				sb.append(" days ");
			}
			if (hours != 0) {
				sb.append(hours);
				sb.append(" hrs ");
			}
			if (minutes != 0) {
				sb.append(minutes);
				sb.append(" mins ");
			}
			if (seconds != 0) {
				sb.append(seconds);
				sb.append(" secs ");
			}

			String duration = sb.toString();

			System.out.printf("%4s%12s%35s%50s%17s%17s%33s\n",i,rs.getInt(1),rs.getString(2),rs.getString(3),"$ "+rs.getString(4),"$ "+rs.getString(5),duration);
		}
		if (i == 0) {
			System.out.println("Sorry, no active auctions at this moment!");
		}
		st.close();
		con.close();
	}
	
	private static void sellerViewWithdrawnItems(int auth) throws Exception{
		Class.forName(classForName);
		Connection con = DriverManager.getConnection(url, dbuname, dbpass);
		Statement st = con.createStatement();

		System.out.println("\b");
		String query2 = "select W.productId, W.pname, W.uname, W.wen, W.baseprice, W.lastbid from withdraws W inner join seller_sells S on W.productId = S.productId where S.uname = "+auth;
		ResultSet rs2 = st.executeQuery(query2);
		int i = 0;
		while(rs2.next()){
			i++;
			if (i == 1) {
				System.out.printf("%10s%22s%20s%20s%25s%15s\n","Product ID","Product Name","Reason","When","Base Price","Last Bid");
			}
			String reason = "";
			if(rs2.getInt(3) == 0)
				reason = "No bids/Timeout";
			else
				reason = "Self";
			String val = "";
			if(rs2.getString(6) == null){
				val = "0.00";
			}
			else{
				val = rs2.getString(6);
			}
			System.out.printf("%6s%27s%23s%25s%14s%17s\n",rs2.getInt(1),rs2.getString(2),reason,rs2.getString(4),"$ "+rs2.getString(5),"$ "+val);
		}
		if(i==0){
			System.out.println("No item has been withdrawn!");
		}
	}

	private static void sellerWithdrawAuction(int auth) throws Exception {
		Scanner scan = new Scanner(System.in);
		Class.forName(classForName);
		Connection con = DriverManager.getConnection(url, dbuname, dbpass);
		Statement st = con.createStatement();

		System.out.println("\b");
		String query2 = "select S.productId, P.pname, P.baseprice, P.BidValue from seller_sells S inner join product P on S.productId = P.id where P.endTime > (SELECT NOW()) and P.approved = 1 and S.uname = "
				+ auth;
		ResultSet rs2 = st.executeQuery(query2);
		LinkedHashMap lhm = new LinkedHashMap();
		int l = 0;
		while (rs2.next()) {
			l++;
			if (l == 1) {
				System.out.printf("%8s%25s%18s%20s\n","Product ID","Product Name","Base Price","Current Bid Value");
			}
			System.out.printf("%7s%31s%14s%18s\n",rs2.getInt(1),rs2.getString(2),"$ "+rs2.getString(3),"$ "+rs2.getString(4));
			lhm.put(rs2.getInt(1), rs2.getString(2) + " " + rs2.getString(3) + " " + rs2.getString(4));
		}
		if (l > 0) {
			System.out.println("\b");
			System.out.print("Enter Product ID to withdraw: ");
			int n = scan.nextInt();
			Set set = lhm.entrySet();
			Iterator itr = set.iterator();
			String name = "";
			double base = 0.00;
			double bidV = 0.00;
			while (itr.hasNext()) {
				Map.Entry me = (Map.Entry) itr.next();
				int key = (int) me.getKey();
				if (n == key) {
					String value = me.getValue().toString();
					String[] ar = value.split(" ");
					name = ar[0];
					base = Double.parseDouble(ar[ar.length - 2]);
					bidV = Double.parseDouble(ar[ar.length - 1]);
				}
			}

			String query3 = "update product set approved = 0 where id = " + n;
			int ret = st.executeUpdate(query3);
			if (ret == 0) {
				System.out.println("\b");
				System.out.println("Could not delete auction request.\nPlease try again later.");
			} else {
				System.out.println("\b");
				System.out.println("Product from auction : ");

				String query4 = "insert into withdraws(productId, pname, uname, wen, baseprice, lastbid) values (" + n
						+ ",'" + name + "'," + auth + ",(select NOW())," + base + "," + bidV + ")";
				int result = st.executeUpdate(query4);
				switch (result) {
				case 0:
					System.out.println("Could not insert. try again later");
					break;
				case 1:
					System.out.print("Success !");
					break;
				}
			}
		} else {
			System.out.println("\b");
			System.out.println("Sorry, no item can be withdrawn from auction at this time!");
		}

		st.close();
		con.close();
	}

	private static void sellerViewSellerEarnings(int auth) throws Exception {
		Scanner scan = new Scanner(System.in);
		Class.forName(classForName);
		Connection con = DriverManager.getConnection(url, dbuname, dbpass);
		Statement st = con.createStatement();

		System.out.println("\b");
		String query2 = "select P.pname, (0.95*P.BidValue) as earning from product P inner join seller_sells S on S.productId = P.id where P.endTime < (SELECT NOW()) and P.approved = 1 and S.uname = "
				+ auth;
		ResultSet rs2 = st.executeQuery(query2);
		double earning = 0.00;
		int i = 0;
		while (rs2.next()) {
			i++;
			if (i == 1) {
				System.out.printf("%20s%20s\n","Product Name","Earnings");
			}
			BigDecimal bd = new BigDecimal(String.valueOf(rs2.getDouble(2))).setScale(2, BigDecimal.ROUND_FLOOR);
			System.out.printf("%20s%19s\n",rs2.getString(1),"$ "+bd);
			earning += rs2.getDouble(2);
		}
		System.out.println("\b");
		BigDecimal bd = new BigDecimal(String.valueOf(earning)).setScale(2, BigDecimal.ROUND_FLOOR);
		System.out.println("Total earning: $ " + bd);
		st.close();
		con.close();
	}

	private static void sellerViewRunningAuctions(int auth) throws Exception {
		Scanner scan = new Scanner(System.in);
		Class.forName(classForName);
		Connection con = DriverManager.getConnection(url, dbuname, dbpass);
		Statement st = con.createStatement();

		System.out.println("\b");
		String query2 = "select id, pname, baseprice, BidValue, startTime, endTime from product P where endTime > (SELECT NOW()) and approved = 1";
		ResultSet rs2 = st.executeQuery(query2);
		int i = 0;
		while (rs2.next()) {
			i++;
			if (i == 1) {
				System.out.printf("%8s%25s%18s%16s%24s%24s\n","Product ID","Product Name","Base Price","Last Bid","Start Time","End Time");
			}
			System.out.printf("%7s%31s%14s%16s%28s%28s\n",rs2.getInt(1),rs2.getString(2),"$ "+rs2.getString(3),rs2.getString(4),rs2.getString(5),rs2.getString(6));
		}
		if (i == 0) {
			System.out.println("No active auctions found!");
		}
		st.close();
		con.close();
	}

	private static void sellerViewItemStatus(int auth) throws Exception {
		Class.forName(classForName);
		Connection con = DriverManager.getConnection(url, dbuname, dbpass);
		Statement st = con.createStatement();

		String query1 = "select S.productId, P.pname, P.approved from seller_sells S inner join product P on S.productId = P.id where uname = "
				+ auth;
		ResultSet rs = st.executeQuery(query1);
		int i = 0;
		System.out.println("\b");
		while (rs.next()) {
			i++;
			if (i == 1) {
				System.out.printf("%8s%22s%30s\n","Product ID","Product Name","Approval Status");
			}
			int status = rs.getInt(3);
			String stat = "";
			if (status == 0)
				stat = "Pending approval/Item withdrawn";
			else
				stat = "Approved";
			System.out.printf("%4d%28s%36s\n",rs.getInt(1),rs.getString(2),stat);
		}
		if (i == 0) {
			System.out.println("Please submit items for auction.");
		}
		st.close();
		con.close();
	}

	private static void sellerViewBids(int auth) throws Exception {
		Scanner scan = new Scanner(System.in);
		Class.forName(classForName);
		Connection con = DriverManager.getConnection(url, dbuname, dbpass);
		Statement st = con.createStatement();

		System.out.println("\b");
		String query2 = "select S.productId, P.pname, B.bidValue, B.wen from seller_sells S inner join bidder_bids B on S.productId = B.productId inner join product P on S.productId = P.id where S.uname = "
				+ auth + " ORDER BY productId DESC, wen DESC";
		ResultSet rs2 = st.executeQuery(query2);
		int k = 0;
		while (rs2.next()) {
			k++;
			if (k == 1) {
				System.out.printf("%10s%20s%20s%20s\n","Product ID","Product Name","Bid Value","Time");
			}
			System.out.printf("%4d%26s%19s%28s\n",rs2.getInt(1),rs2.getString(2),"$ "+rs2.getString(3),rs2.getString(4));
		}
		if (k == 0)
			System.out.println("No bids received for any of your items.");
		st.close();
		con.close();
	}

	private static void sellerSetTime(int auth) throws Exception {
		Scanner scan = new Scanner(System.in);
		Class.forName(classForName);
		Connection con = DriverManager.getConnection(url, dbuname, dbpass);
		Statement st = con.createStatement();

		String query2 = "select S.productId, P.pname, P.duration from seller_sells S inner join product P on S.productId = P.id where P.sold = 0 and P.endTime > (SELECT NOW()) and S.uname = "
				+ auth;
		ResultSet rs2 = st.executeQuery(query2);
		System.out.println("\b");
		int j = 0;
		String duration = "";
		while (rs2.next()) {
			j++;
			if (j == 1) {
				System.out.printf("%10s%25s%25s\n","Product ID","Product Name","Duration");
			}
			long millis = rs2.getLong(3);
			long days = TimeUnit.MILLISECONDS.toDays(millis);
			millis -= TimeUnit.DAYS.toMillis(days);
			long hours = TimeUnit.MILLISECONDS.toHours(millis);
			millis -= TimeUnit.HOURS.toMillis(hours);
			long minutes = TimeUnit.MILLISECONDS.toMinutes(millis);
			millis -= TimeUnit.MINUTES.toMillis(minutes);
			long seconds = TimeUnit.MILLISECONDS.toSeconds(millis);

			StringBuilder sb = new StringBuilder(64);
			if (days != 0) {
				sb.append(days);
				sb.append(" Days ");
			}
			if (hours != 0) {
				sb.append(hours);
				sb.append(" Hrs ");
			}
			if (minutes != 0) {
				sb.append(minutes);
				sb.append(" Mins ");
			}
			if (seconds != 0) {
				sb.append(seconds);
				sb.append(" Sec");
			}

			duration = sb.toString();
			System.out.printf("%6s%32s%30s\n",rs2.getInt(1),rs2.getString(2),duration);
		}
		if (j > 0) {
			String quit = "Y";
			long newMilli = 0;
			while (!quit.equals("N")) {
				System.out.println("\b");
				System.out.print("Product number to change: ");
				int opt = scan.nextInt();
				System.out.print("Enter new duration in days hrs mins secs. \n");
				System.out.print("Days: ");
				int days = scan.nextInt();
				System.out.print("Hours: ");
				int hours = scan.nextInt();
				System.out.print("Minutes: ");
				int minutes = scan.nextInt();
				System.out.print("Seconds: ");
				int seconds = scan.nextInt();

				newMilli = TimeUnit.SECONDS.toMillis(seconds) + TimeUnit.MINUTES.toMillis(minutes)
						+ TimeUnit.HOURS.toMillis(hours) + TimeUnit.DAYS.toMillis(days);

				String query3 = "update product set duration = " + newMilli + " , endTime = DATE_ADD(NOW(), INTERVAL "
						+ (newMilli / 1000) + " second) where id = " + opt;
				int ret = st.executeUpdate(query3);
				if (ret == 0) {
					System.out.println("No row updated !");
				} else {
					System.out.println("\b");
					System.out
							.print("Duration updated successfully ! \nDo you wish to make any more updates ? (Y/N): ");
					quit = scan.next().toUpperCase();
				}
			}
		} else {
			System.out.println("\b");
			System.out.println("Sorry, you haven't submitted any new items for auction !");
		}
		st.close();
		con.close();
	}

	private static void sellerSetBasePrice(int auth) throws Exception {
		Scanner scan = new Scanner(System.in);
		Class.forName(classForName);
		Connection con2 = DriverManager.getConnection(url, dbuname, dbpass);
		Statement st2 = con2.createStatement();
		Connection con3 = DriverManager.getConnection(url, dbuname, dbpass);
		Statement st3 = con3.createStatement();

		String query2 = "select S.productId, P.pname, P.baseprice from seller_sells S inner join product P on S.productId = P.id where P.startTime IS NULL and S.uname = "
				+ auth;
		ResultSet rs2 = st2.executeQuery(query2);
		System.out.println("\b");
		int j = 0;
		while (rs2.next()) {
			j++;
			if (j == 1) {
				System.out.printf("%10s%20s%15s\n","Product ID","Product Name","Base Price");
			}
			System.out.printf("%5s%20s%18s\n",rs2.getInt(1),rs2.getString(2),"$ "+rs2.getString(3));
		}
		if (j > 0) {
			String quit = "Y";
			while (!quit.equals("N")) {
				System.out.println("\b");
				System.out.print("Product number to change: ");
				int opt = scan.nextInt();
				System.out.print("New base price (with 2 decimal digits): $ ");
				double newBase = scan.nextDouble();

				String query3 = "update product set baseprice = " + newBase + " where id = " + opt;
				int ret = st3.executeUpdate(query3);
				if (ret == 0) {
					System.out.println("No row updated !");
				} else {
					System.out.println("\b");
					System.out.print(
							"Base price updated successfully ! \nDo you wish to make any more updates ? (Y/N): ");
					quit = scan.next().toUpperCase();
				}
			}
		} else {
			System.out.println("\b");
			System.out.println("Sorry, you haven't submitted any new items for auction !");
		}
		st3.close();
		con3.close();
		st2.close();
		con2.close();
	}

	private static void sellerSubmitItem(int auth) throws Exception {
		Scanner scan = new Scanner(System.in);
		System.out.println("\b");
		System.out.println("Welcome Seller ID (" + auth + "). Please give the product details:");
		System.out.println("\b");
		System.out.print("Product Name: ");
		String prodName = scan.nextLine();
		System.out.print("Product Desciption (12-15 words): ");
		String prodDesc = scan.nextLine();
		prodDesc = prodDesc.substring(0, Math.min(prodDesc.length(), 98));
		System.out.print("Base Price (with 2 decimal digits): $ ");
		double prodBase = scan.nextDouble();
		System.out.print("Auction Duration:\n");
		System.out.print("days: ");
		int dd = scan.nextInt();
		System.out.print("hours: ");
		int hh = scan.nextInt();
		System.out.print("mins: ");
		int mm = scan.nextInt();
		System.out.print("seconds: ");
		int ss = scan.nextInt();

		int milliSec = ss * 1000 + (mm * 60 * 1000) + (hh * 60 * 60 * 1000) + (dd * 24 * 60 * 60 * 1000);

		String query = "insert into product(pname, des, baseprice, duration) values ('" + prodName + "','" + prodDesc
				+ "','" + prodBase + "','" + milliSec + "')";
		Class.forName(classForName);
		Connection con = DriverManager.getConnection(url, dbuname, dbpass);
		Statement st = con.createStatement();
		int count = st.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
		ResultSet rs = st.getGeneratedKeys();
		int productId = 0;
		if (rs.next()) {
			productId = rs.getInt(1);
		}
		if (count == 0) {
			System.out.println("Could not save data. Please try again later.");
		} else {
			System.out.println("\b");
			System.out.println(
					"Item saved successfully ! The admin will soon approve your auction request and start the timer.");
			String query2 = "insert into seller_sells(uname, productId) values (" + auth + "," + productId + ")";
			int count2 = st.executeUpdate(query2, Statement.RETURN_GENERATED_KEYS);
			ResultSet rs2 = st.getGeneratedKeys();
			int requestId = 0;
			if (rs2.next()) {
				requestId = rs2.getInt(1);
				System.out.println("\b");
				System.out.println("For reference, your request ID is " + requestId);
			}
		}
		st.close();
		con.close();
	}

	private static void adminViewWithdrawn() throws Exception {
		Class.forName(classForName);
		Connection con = DriverManager.getConnection(url, dbuname, dbpass);
		Statement st = con.createStatement();

		String query1 = "select id, productId, pname, uname, wen, baseprice, lastbid from withdraws";
		ResultSet rs = st.executeQuery(query1);
		System.out.println("\b");
		int i = 0;
		while (rs.next()) {
			i++;
			if (i == 1) {
				System.out.printf("%5s%15s%23s%18s%20s%23s%18s\n","S.No.","Product ID","Product Name","Seller ID","Time","Base Price","Last Bid");
			}
			String val = "";
			if(rs.getString(7) == null)
				val = "0.00";
			else
				val = rs.getString(7);
			System.out.printf("%3s%14s%26s%14s%31s%15s%18s\n",rs.getInt(1),rs.getInt(2),rs.getString(3),rs.getString(4),rs.getString(5),"$ "+rs.getString(6),"$ "+val);
		}
		if (i == 0) {
			System.out.println("No items have been withdrawn from auction!");
		}
	}

	private static void adminViewSoldEarn() throws Exception {
		Class.forName(classForName);
		Connection con = DriverManager.getConnection(url, dbuname, dbpass);
		Statement st = con.createStatement();

		String query1 = "select id, pname, baseprice, BidValue, lastBidder from product where endTime < (select NOW()) and approved = 1";
		ResultSet rs = st.executeQuery(query1);
		ResultSetMetaData rsmd = rs.getMetaData();
		int cols = rsmd.getColumnCount();
		System.out.println("\b");
		double earning = 0.00;
		int i = 0;
		while (rs.next()) {
			i++;
			if (i == 1) {
				System.out.printf("%10s%20s%17s%15s%20s%15s\n","Product ID","Product Name","Base Price","Last Bid","Last Bidder ID","Earning");
			}
			double val = (0.05 * rs.getDouble(4));
			BigDecimal bd = new BigDecimal(String.valueOf(val)).setScale(2, BigDecimal.ROUND_FLOOR);
			
			System.out.printf("%6d%24s%15s%16s%15s%21s\n",rs.getInt(1),rs.getString(2),"$ "+rs.getString(3),"$ "+rs.getString(4),rs.getInt(5),"$ "+bd);
			earning += (0.05 * rs.getDouble(4));;
		}
		System.out.println("\b");
		BigDecimal bd = new BigDecimal(String.valueOf(earning)).setScale(2, BigDecimal.ROUND_FLOOR);
		System.out.println("Total earning so far: $ " + bd);
		st.close();
		con.close();
	}

	private static void adminViewAucBid() throws Exception {
		Scanner scan = new Scanner(System.in);
		Class.forName(classForName);
		Connection con = DriverManager.getConnection(url, dbuname, dbpass);
		Statement st = con.createStatement();

		String query1 = "select id, pname, baseprice, BidValue, startTime, endTime from product where endTime > (select NOW()) and approved = 1";
		ResultSet rs = st.executeQuery(query1);
		System.out.println("\b");
		int i = 0;
		while (rs.next()) {
			i++;
			if (i == 1) {
				System.out.printf("%8s%25s%18s%18s%24s%25s\n","Product ID","Product Name","Base Price","Last Bid","Start Time","End Time");
			}
			System.out.printf("%7s%30s%16s%18s%27s%27s\n",rs.getInt(1),rs.getString(2),"$ "+rs.getString(3),"$ "+rs.getString(4),rs.getString(5),rs.getString(6));
		}
		if (i > 0) {
			System.out.println("\b");
			System.out.print("Enter product id to view bids: ");
			int prodId = scan.nextInt();

			String query2 = "select id, uname, bidValue, wen from bidder_bids where productId = " + prodId
					+ " order by id desc";
			ResultSet rs2 = st.executeQuery(query2);
			System.out.println("\b");
			int j = 0;
			while (rs2.next()) {
				j++;
				if (j == 1) {
					System.out.printf("%10s%15s%18s%18s\n","Bid ID","Bidder ID","Bid Value","Time");
				}
				System.out.printf("%7s%13s%21s%28s\n",rs2.getInt(1),rs2.getInt(2),"$ "+rs2.getString(3),rs2.getString(4));
			}
			if (j == 0) {
				System.out.println("\b");
				System.out.println("No bids have been placed so far for this product id!");
			}
		} else {
			System.out.println("\b");
			System.out.println("No active auctions!");
		}
		st.close();
		con.close();
	}

	private static void adminStartTimer() throws Exception {
		Scanner scan = new Scanner(System.in);
		Class.forName(classForName);
		Connection con = DriverManager.getConnection(url, dbuname, dbpass);
		Statement st = con.createStatement();
		Connection con2 = DriverManager.getConnection(url, dbuname, dbpass);
		Statement st2 = con2.createStatement();

		String query1 = "select * from product where startTime IS NULL";
		ResultSet rs = st.executeQuery(query1);
		System.out.println("\b");
		int i = 0;
		while (rs.next()) {
			i++;
			if (i == 1) {
				System.out.printf("%10s%27s%43s%30s%30s\n","Product ID","Product Name","Product Description","Base Price","Duration");
			}
			long millis = rs.getLong(5);
			long days = TimeUnit.MILLISECONDS.toDays(millis);
			millis -= TimeUnit.DAYS.toMillis(days);
			long hours = TimeUnit.MILLISECONDS.toHours(millis);
			millis -= TimeUnit.HOURS.toMillis(hours);
			long minutes = TimeUnit.MILLISECONDS.toMinutes(millis);
			millis -= TimeUnit.MINUTES.toMillis(minutes);
			long seconds = TimeUnit.MILLISECONDS.toSeconds(millis);

			StringBuilder sb = new StringBuilder(64);
			if (days != 0) {
				sb.append(days);
				sb.append(" Days ");
			}
			if (hours != 0) {
				sb.append(hours);
				sb.append(" Hrs ");
			}
			if (minutes != 0) {
				sb.append(minutes);
				sb.append(" Mins ");
			}
			if (seconds != 0) {
				sb.append(seconds);
				sb.append(" Sec");
			}

			String duration = sb.toString();
			System.out.printf("%5s%38s%50s%17s%37s\n",rs.getInt(1),rs.getString(2),rs.getString(3),"$ "+rs.getString(4),duration);
			System.out.println("\b");
			System.out.print("Start timer for this product? (Y/N): ");
			String ans2 = scan.nextLine().toUpperCase();
			String query2 = "";
			switch (ans2) {
			case "Y":
				long milli = rs.getLong(5);
				long sec = milli / 1000;
				query2 = "update product set approved = 1 , startTime = (select NOW()) , endTime = ((select NOW()) + INTERVAL "
						+ sec + " SECOND) where id = " + (int) rs.getInt(1);
				break;
			}
			int ret = st2.executeUpdate(query2);
			if (ret > 0) {
				System.out.println("\b");
				System.out.println("Record updated succesfully !");
			}
		}
		if (i == 0) {
			System.out.println("No request to start timer!");
		}
		st.close();
		con.close();
	}

	private static void adminAcceptSeller() throws Exception {
		Scanner scan = new Scanner(System.in);
		Class.forName(classForName);
		Connection con = DriverManager.getConnection(url, dbuname, dbpass);
		Statement st = con.createStatement();
		Connection con2 = DriverManager.getConnection(url, dbuname, dbpass);
		Statement st2 = con2.createStatement();

		String query1 = "select * from product where approved = 0 and startTime IS NULL";
		ResultSet rs = st.executeQuery(query1);
		System.out.println("\b");
		int i = 0;
		while (rs.next()) {
			i++;
			if (i == 1) {
				System.out.printf("%10s%27s%43s%30s%30s\n","Product ID","Product Name","Product Description","Base Price","Duration");
			}
			long millis = rs.getLong(5);
			long days = TimeUnit.MILLISECONDS.toDays(millis);
			millis -= TimeUnit.DAYS.toMillis(days);
			long hours = TimeUnit.MILLISECONDS.toHours(millis);
			millis -= TimeUnit.HOURS.toMillis(hours);
			long minutes = TimeUnit.MILLISECONDS.toMinutes(millis);
			millis -= TimeUnit.MINUTES.toMillis(minutes);
			long seconds = TimeUnit.MILLISECONDS.toSeconds(millis);

			StringBuilder sb = new StringBuilder(64);
			if (days != 0) {
				sb.append(days);
				sb.append(" Days ");
			}
			if (hours != 0) {
				sb.append(hours);
				sb.append(" Hrs ");
			}
			if (minutes != 0) {
				sb.append(minutes);
				sb.append(" Mins ");
			}
			if (seconds != 0) {
				sb.append(seconds);
				sb.append(" Sec");
			}

			String duration = sb.toString();
			System.out.printf("%5s%38s%50s%17s%37s\n",rs.getInt(1),rs.getString(2),rs.getString(3),"$ "+rs.getString(4),duration);
			System.out.println("\b");
			System.out.print("Approve? (Y/N): ");
			String ans1 = scan.nextLine().toUpperCase();
			int ans11;
			if (ans1.equals("Y"))
				ans11 = 1;
			else
				ans11 = 0;
			System.out.println("\b");
			System.out.print("Start auction of this product? (Y/N): ");
			String ans2 = scan.nextLine().toUpperCase();
			String query2 = "";
			switch (ans2) {
			case "N":
				query2 = "update product set approved = " + ans11 + " where id = " + (int) rs.getInt(1);
				break;
			case "Y":
				long milli = rs.getLong(5);
				long sec = milli / 1000;
				query2 = "update product set approved = " + ans11
						+ " , startTime = (select NOW()) , endTime = ((select NOW()) + INTERVAL " + sec
						+ " SECOND) where id = " + (int) rs.getInt(1);
				break;
			}
			int ret = st2.executeUpdate(query2);
			if (ret > 0) {
				System.out.println("\b");
				System.out.println("Record updated succesfully !");
			}
		}
		if (i == 0) {
			System.out.println("No auction request!");
		}
		st.close();
		con.close();
		st2.close();
		con2.close();
	}

	private static int register(int i) throws Exception {
		Scanner scan = new Scanner(System.in);
		System.out.println("\b");
		System.out.println("Registration: ");
		System.out.println("\b");
		System.out.print("Password: ");
		String passWord = scan.nextLine();
		System.out.print("First name: ");
		String firstName = scan.nextLine();
		System.out.print("Last Name: ");
		String lastName = scan.nextLine();
		System.out.print("Phone: ");
		String phone = scan.nextLine();
		System.out.print("Email ID: ");
		String emailID = scan.nextLine();
		String userType = "";

		String query = "";
		switch (i) {
		case 49:
			userType = "(seller)";
			query = "insert into seller(pass, fname, lname, ph, email) values ('" + passWord + "','" + firstName + "','"
					+ lastName + "','" + phone + "','" + emailID + "')";
			break;
		case 50:
			userType = "(bidder)";
			query = "insert into bidder(pass, fname, lname, ph, email) values ('" + passWord + "','" + firstName + "','"
					+ lastName + "','" + phone + "','" + emailID + "')";
			break;
		}
		Class.forName(classForName);
		Connection con = DriverManager.getConnection(url, dbuname, dbpass);
		Statement st = con.createStatement();
		st.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
		ResultSet rs = st.getGeneratedKeys();
		int key = 0;
		if (rs.next()) {
			key = rs.getInt(1);
		}
		System.out.println("\b");
		System.out.println(
				"Your " + userType + " username is: " + key + "\nPlease use your username and password to sign in.");
		st.close();
		con.close();
		return key;
	}

	private static int authenticate(int i) throws Exception {
		System.out.println("\b");
		System.out.print("Username: ");
		Scanner scan = new Scanner(System.in);
		int uname = scan.nextInt();
		System.out.print("Password: ");
		String pass = scan.next();

		String query = "";
		switch (i) {
		case 49:
			query = "select pass from seller where uname = " + uname;
			break;
		case 50:
			query = "select pass from bidder where uname = " + uname;
			break;
		case 97:
			query = "select pass from admin where uname = " + uname;
			break;
		}

		Class.forName(classForName);
		Connection con = DriverManager.getConnection(url, dbuname, dbpass);
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(query);
		int returnVal = 0;
		if (rs.next()) {
			String passFetch = rs.getString("pass");
			if (pass.equals(passFetch)) {
				System.out.println("\b");
				System.out.println("Successfully verified !");
				returnVal = uname;
			} 
		}
		st.close();
		con.close();
		return returnVal;
	}
}
