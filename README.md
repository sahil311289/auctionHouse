# auctionHouse
Command line based auction house for bidding

This application is a JAVA-console based solution for Auction House. It makes use of JAVA-MySQL JDBC connection.

How to run
1. Run the MySQL file - AuctionHouseSchema.sql.
2. Import the Eclipse project from zip file “AuctionHouse.zip”
3. If needed, add mysql-connector-java-5.1.40-bin.jar to the build path.
4. Hit run

Additional Bonus Requirements
1.	Different console based UI for Admin, Seller and Bidder
2.	Additional flexible features have added
a.	Seller can view all the running auctions
b.	Seller can view the approval status for the submitted item
c.	Seller can withdraw items from auction
d.	Seller can view withdrawn items
e.	Admin can opt to only approve the auction and not start timer
f.	Admin can start timer thereby approving the auction request
g.	Admin can view all the withdrawn auctions
3.	Data is persisted in MYSQL DB using JDBC connection
4.	There is no initial UI for Admin to log in, so type “admin” on the first screen to see login menu for the admin instead of 1 or 2. (Username: 786, Password: admin)

Procedure

Seller has to register and then login with the generated username and entered password. 
Options:
1.	Submit items for auction
a.	User enters product details, base price and auction duration
b.	An approval request is generated for the admin to approve and start the timer for auction
c.	Request ID is returned to the user
2.	Set/change base price for your submitted item
a.	Seller can change the base price for his own submitted items for which auction has not yet started
3.	Set time duration for auction
a.	Seller can change the duration of the auction at any point until the item has been sold
4.	View all the running auctions
a.	Seller can see all the auctions going on in the auction house after they have been approved by the admin
5.	View (approval) status of your items
a.	Seller can see the approval status for the submitted item
6.	View all the bids for your item
a.	Seller can see all the bids in real-time for the submitted item
7.	View earnings (after auction)
a.	Seller can view earnings - 95% of the auction value of the product after the auction is over
8.	Withdraw item from auction
a.	Seller can withdraw an item from auction at any time after the auction has been started and it has been approved by the admin
b.	After the withdrawal, the item is no longer available for bidding
9.	View withdrawn items
a.	An item can be withdrawn in 2 cases:
i.	The user withdraws the item from auction
ii.	No bids are placed in the duration specified by the seller, which automatically puts the item into the withdrawn items section
10.	Exit 
a.	Seller can exit the application

Bidder has to register and then login with the generated username and entered password. 
Options:
1.	View available and running auctions
a.	Bidder can view all the available items for auction that have been approved by the admin
2.	View my bids
a.	Bidder can view all the items for which he/she has submitted bids
3.	Bid for an auction
a.	Bidder can bid for an item if
i.	The item has been approved and timer has been started by the admin for auction
ii.	The auction time is not over
iii.	The entered bid is higher than the previous bid
4.	View bought items
a.	An item is considered bought by the bidder if the item has not been withdrawn and
i.	Bidder placed a bid and no other bids have been placed and the auction time elapses
ii.	The bidder’s bid is the highest bid until the auction time elapses
5.	Exit
a.	Bidder can exit the application

Admin need not register. On the first screen, type “admin” to see the welcome message for Rita
Username: 786
Password: admin

Options:
1.	Accept seller auction request
a.	Admin can
i.	Opt to only approve the auction request and not start the timer
ii.	Opt to approve and start timer for the auction, which makes the auction active
2.	Start timer for an auction
a.	Admin can opt to approve the request and start the timer at once with this option which makes the auction active
3.	View all running auctions and bids
a.	Admin can view all the products and then the bids placed for that product in real time
4.	View all sold products and earnings
a.	Admin can view all the products that have been sold and the earnings (5%) from those auctions
5.	View all withdrawn items from auction
a.	Admin can view all the items that have been withdrawn from auctions either 
i.	By the seller themselves
ii.	By the system due to absence of bids in the mentioned duration
6.	Exit
Admin can exit the application
