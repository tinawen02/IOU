IOU - README Template
===

# IOU

## Table of Contents
1. [Overview](#Overview)
1. [Product Spec](#Product-Spec)
1. [Wireframes](#Wireframes)
2. [Schema](#Schema)

## Overview
### Description
IOU is a tool that helps users keep track of their restaurant bills to make sure that each person only pays for the items they ordered. Instead of having to calculate by hand the amount each person owes after tips, discounts and taxes, IOU allows a user to quickly determine who is responsible for what.

### App Evaluation
- **Category:** Finance
- **Mobile:** The app is primarily suited for mobile users because it gives users easy access at restaurants.
- **Story:** A user can use IOU after registering for an account.
- **Market:** Any individual over the age 12 can choose to use this app.
- **Habit:** This app can be used every time the user needs to split a bill at a restaurant, or when a user wants to explore nearby restaurants.
- **Scope:** The application has core features of being able to split a bill for a group of people. IOU is also able to show a list of nearby restaurants a user can explore.

## Product Spec

### 1. User Stories (Required and Optional)

**Required Must-have Stories**

* User can create a new account
* User can login
* User can split a bill evenly
* User can split a bill based on items each individual purchased
* Home feed showing transaction history
* User can explore nearby restaurants through a map

**Optional Nice-to-have Stories**

* Push notifications when a user is inactive for a week
* Add a back button for when a user makes a mistake 
* User can search for restauarnts to eat it
* Walk-through tutorial to demonstrate to the user how to use IOU
* Allow a user to check-off people who have paid them back

### 2. Screen Archetypes

* Login
* Register - User signs up 
   * Upon reopening of the application, the user is brought to the home page 
* Bill Fragment
   * Allows a user to choose between splitting a bill evenly or by individual items
       * Even Split - user lists the restaurant, people who went, and the final bill after taxes/tips/discounts
       * Item Split - user lists the restaurant, people who went, individual item prices, and the final bill after taxes/tips/discounts
           * A user can click on checkboxes to indicate which person ordered which item
* Home Fragment
   * Shows the users previous transactions 
   * When a previous transaction is clicked, user can check-off the names of people who have paid the user back
* Map Fragment
   * Allows a user to explore nearby restaurants
   * Allows a user to specifically search for a specific type of food or restaurant near them
### 3. Navigation

**Tab Navigation** (Tab to Screen)

* Bill Fragment
* Home Fragment
* Map Fragment
* Settings Page

**Flow Navigation** (Screen to Screen)

* Forced Login -> Account creation if no login is available
* Bill Fragment -> Choice between splitting a bill evenly or by item
    * Even Bill -> Forces user to input information, then displays settlement
    * Split Bill -> Forces user to input information, divides items, then displays settlement
* Home Fragment -> Displays previous transactions, which can be clicked on to show a more detailed view
* Map Fragment -> Renders a map containing restaurants a user can explore
* Settings Page -> Contains tutorial for users with questions on using IOU, as well as the log out button

## Wireframes
<img src="https://raw.githubusercontent.com/tinawen02/IOU/main/IOU%20Wireframe%20-2.jpg" width=600>

### [BONUS] Digital Wireframes & Mockups

### [BONUS] Interactive Prototype

## Schema 

### Models
#### BillItem
| Property | Type | Description |
| -------- | -------- | -------- |
| price | double | the price of a specific item |
| people | Array<String> | names of people who dined at the restaurant |
| checkedList | Array<Boolean> | true if a person ate the item, false if the person did not |
    
#### SplitBill
| Property | Type | Description |
| -------- | -------- | -------- |
| restaurantName | String | name of the resturant user went to |
| people | Array<Person> | names of people who dined at the restaurant |
| items | Array<Double>| the costs of items ordered |
| billTotal | Double | the final bill amount after taxes/tips/dicounts|
    
#### BillParse (this was stored in the database Parse)
| Property | Type | Description |
| -------- | -------- | -------- |
| user | User | the logged-in user who recorded the transaction |
| location | String | name of the restaurant the user dined |
| finalBill | Number | the final bill amount after taxes/tips/dicounts|
| amountsOwed | String | a string listing the amounts each person owes |
| selectedIndices | Array<Boolean> | true, if a person has paid the user back; if not, false |

### Networking
#### List of network requests by screen
- Bill Fragment
    - (Create/POST) Create a new bill
- Map Fragment
    - (Read/GET) Get restaurants nearby the user's current location
- Home Fragment
    - (Read/GET) Query logged in user object
    - (Update/PUT) Update the home feed with a new transaction

- List endpoints if using an API
    - Google Maps API
        - Dynamic Maps
        - Markers
        - Info Windows
        - Controls and Gestures
