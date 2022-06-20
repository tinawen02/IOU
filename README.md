Original App Design Project - README Template
===

# IOU

## Table of Contents
1. [Overview](#Overview)
1. [Product Spec](#Product-Spec)
1. [Wireframes](#Wireframes)
2. [Schema](#Schema)

## Overview
### Description
IOU is an app that helps a group of people split a bill after a night out at a restaurant. Instead of having to calculate by hand the amount each person owes after tips, discounts, etc., IOU allows a user to quickly determine who is responsible for what.

### App Evaluation
[Evaluation of your app across the following attributes]
- **Category:** Social Media
- **Mobile:** The app is primarily suited for mobile users because it gives users easy access at restaurants.
- **Story:** Each user can begin with an account, with the notifying other users that they owe a certain amount of money to another.
- **Market:** Any individual over the age 12 can choose to use this app.
- **Habit:** This app could be used every time the user needs to split a bill at a restaurant.
- **Scope:** The application would have the core feature of being able to split a bill for a group of people - and eventually being able to render a map with pins locating the places each user has eaten.
## Product Spec

### 1. User Stories (Required and Optional)

**Required Must-have Stories**

* User can create a new account
* User can login
* User can split a bill evenly
* User can split a bill based on items each individual purchased
* Home pages for each user
* Rendering a map with pins of places a user has eaten

**Optional Nice-to-have Stories**

* Push notifications notifying user they still owe money to another user
* Foreign currency conversion
* Add a back button for when a user makes a mistake 
* A user can add friends and see their transactions
* A user can add a profile picture

### 2. Screen Archetypes

* Login
* Register - User signs up 
   * Upon reopening of the application, the user is brought to the home page 
* Main Frame (adding a bill)
   * Allows a user to choose between splitting a bill evenly or by individual items
       * Even Split - user lists the restaurant, people who went, the item total, tax rate, tip rate and discount rate
       * Item Split - user lists the restaurant, people who went, individual item prices, tax rate, tip rate and discount rate
* Home
   * Shows the users previous transactions 
   * When a previous transaction is clicked, user can see details about the transaction
* Porfile
   * Shows the users previous transactions 
   * When a previous transaction is clicked, user can see details about the transaction

### 3. Navigation

**Tab Navigation** (Tab to Screen)

* Add a Bill
* Home 
* Map

**Flow Navigation** (Screen to Screen)

* Forced Login -> Account creation if no login is available
* Add a Bill Page -> Choice between splitting a bill evenly or by item
    * Even Bill -> Forces user to input information, then displays settlement
    * Split Bill -> Forces user to input information, divide items, then displays settlement
* Home Page -> Displays previous transactions, which can be clicked on to show a more detailed view
* Map -> Renders a map of restaurants user has been to

## Wireframes
[Add picture of your hand sketched wireframes in this section]
<img src="YOUR_WIREFRAME_IMAGE_URL" width=600>

### [BONUS] Digital Wireframes & Mockups

### [BONUS] Interactive Prototype

## Schema 

### Models
#### Even Bill
| Property | Type | Description |
| -------- | -------- | -------- |
| restaurantName | String | name of the resturant user went to |
| people | Array<String> | names of people who dined at the restaurant |
| itemTotal | Number | the total cost of the items ordered |
| taxRate | Number | tax rate of the location the restaurant is at |
| tipRate | Number | tip rate of the user's choosing |
| discountRate | Number | the discount (if any) the user has |
| currencyCode | String | the preferred currency for display |
    
#### Split Bill
| Property | Type | Description |
| -------- | -------- | -------- |
| restaurantName | String | name of the resturant user went to |
| people | Array<Person> | names of people who dined at the restaurant |
| items | Array<Item>| the costs and names of items ordered |
| taxRate | Number | tax rate of the location the restaurant is at |
| tipRate | Number | tip rate of the user's choosing |
| discountRate | Number | the discount (if any) the user has |
| currencyCode | String | the preferred currency for display |
    
#### Item
| Property | Type | Description |
| -------- | -------- | -------- |
| name | String | the name of the item ordered |
| cost | Number | the cost of the item ordered |

#### User
| Property | Type | Description |
| -------- | -------- | -------- |
| evenBills | Array<Even Bill> | the user's past transactions with even bills|
| splitBills | Array<Split Bill> | the user's past transactions with split bills|
| name     | String     | user's name |
| phoneNumber | Number | user's phone number |


### Networking
#### List of network requests by screen
- Add a Bill Screen
    - (Create/POST) Create a new bill
- Map Screen
    - (Create/POST) Create a new map
- Home Screen
    - (Read/GET) Query logged in user object
    - (Update/PUT) Update user profile image

- [Create basic snippets for each Parse network request]
- [OPTIONAL: List endpoints if using existing API such as Yelp]
