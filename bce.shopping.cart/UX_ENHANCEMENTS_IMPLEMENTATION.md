# User Experience Enhancements Implementation

This document describes the implementation of User Experience Enhancements for the BCE Shopping Cart application.

## Overview

The following features have been implemented:
1. **Wishlist** - Save books for later purchase
2. **Reviews & Ratings** - 5-star rating system with reviews
3. **Notifications** - In-app notification system
4. **Social Features** - Social sharing buttons (framework ready)

## Database Schema

### 1. Wishlist Table
```sql
CREATE TABLE `wishlist` (
    `wishlist_id` INT NOT NULL AUTO_INCREMENT,
    `userid` VARCHAR(50) NOT NULL,
    `bookid` INT NOT NULL,
    `added_date` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`wishlist_id`),
    UNIQUE KEY `unique_user_book` (`userid`, `bookid`),
    FOREIGN KEY (`bookid`) REFERENCES `book_details`(`bookid`) ON DELETE CASCADE
) ENGINE = InnoDB;
```

### 2. Book Reviews Table
```sql
CREATE TABLE `book_reviews` (
    `review_id` INT NOT NULL AUTO_INCREMENT,
    `bookid` INT NOT NULL,
    `userid` VARCHAR(50) NOT NULL,
    `rating` INT NOT NULL CHECK (`rating` >= 1 AND `rating` <= 5),
    `review_text` TEXT,
    `review_date` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `is_approved` TINYINT(1) NOT NULL DEFAULT 0,
    `helpful_count` INT NOT NULL DEFAULT 0,
    PRIMARY KEY (`review_id`),
    FOREIGN KEY (`bookid`) REFERENCES `book_details`(`bookid`) ON DELETE CASCADE
) ENGINE = InnoDB;
```

### 3. Review Votes Table
```sql
CREATE TABLE `review_votes` (
    `vote_id` INT NOT NULL AUTO_INCREMENT,
    `review_id` INT NOT NULL,
    `userid` VARCHAR(50) NOT NULL,
    `is_helpful` TINYINT(1) NOT NULL,
    `vote_date` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`vote_id`),
    UNIQUE KEY `unique_user_review` (`review_id`, `userid`),
    FOREIGN KEY (`review_id`) REFERENCES `book_reviews`(`review_id`) ON DELETE CASCADE
) ENGINE = InnoDB;
```

### 4. Notifications Table
```sql
CREATE TABLE `notifications` (
    `notification_id` INT NOT NULL AUTO_INCREMENT,
    `userid` VARCHAR(50) NOT NULL,
    `title` VARCHAR(200) NOT NULL,
    `message` TEXT NOT NULL,
    `type` VARCHAR(50) NOT NULL DEFAULT 'info',
    `is_read` TINYINT(1) NOT NULL DEFAULT 0,
    `created_date` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `link_url` VARCHAR(500),
    PRIMARY KEY (`notification_id`)
) ENGINE = InnoDB;
```

## Implementation Details

### 1. Wishlist Functionality

#### Components:
- **WishlistDTO**: Data transfer object for wishlist items
- **WishlistDAO**: Data access layer for wishlist operations
- **WishlistBC**: Business logic layer
- **WishlistController**: REST endpoints for wishlist operations
- **Wishlist.html**: Thymeleaf template for wishlist view

#### Features:
- Add books to wishlist from search results
- View wishlist with book details
- Remove items from wishlist
- Move items from wishlist to cart
- Check if book is already in wishlist

#### Endpoints:
- `GET /pages/html/postLogin/Wishlist` - View wishlist
- `POST /pages/html/postLogin/Wishlist/add` - Add to wishlist
- `POST /pages/html/postLogin/Wishlist/remove` - Remove from wishlist
- `POST /pages/html/postLogin/Wishlist/removeById` - Remove by wishlist ID
- `POST /pages/html/postLogin/Wishlist/moveToCart` - Move to cart

### 2. Reviews & Ratings

#### Components:
- **BookReviewDTO**: Data transfer object for reviews
- **BookReviewDAO**: Data access layer for review operations
- **BookReviewBC**: Business logic layer
- **ReviewController**: REST endpoints for review operations

#### Features:
- Submit book reviews with 1-5 star ratings
- View approved reviews for books
- Calculate average rating and review count
- Vote on helpful reviews
- Admin approval workflow for reviews

#### Endpoints:
- `POST /pages/html/postLogin/Review/add` - Submit review
- `POST /pages/html/postLogin/Review/voteHelpful` - Vote on review
- `GET /admin/reviews/pending` - View pending reviews (admin)
- `POST /admin/reviews/approve` - Approve review (admin)
- `POST /admin/reviews/delete` - Delete review (admin)

### 3. Notifications System

#### Components:
- **NotificationDTO**: Data transfer object for notifications
- **NotificationDAO**: Data access layer for notification operations
- **NotificationBC**: Business logic layer with helper methods
- **NotificationController**: REST endpoints for notifications
- **Notifications.html**: Thymeleaf template for notifications view

#### Features:
- Create notifications for users
- View all notifications (read and unread)
- Mark notifications as read
- Mark all notifications as read
- Delete notifications
- Unread count badge in header
- Auto-refresh notification count

#### Endpoints:
- `GET /pages/html/postLogin/Notifications` - View notifications
- `GET /api/notifications/unread-count` - Get unread count (AJAX)
- `POST /pages/html/postLogin/Notifications/markRead` - Mark as read
- `POST /pages/html/postLogin/Notifications/markAllRead` - Mark all as read
- `POST /pages/html/postLogin/Notifications/delete` - Delete notification

#### Helper Methods:
- `notifyOrderPlaced()` - Create notification when order is placed
- `notifyOrderStatusChanged()` - Create notification when order status changes

### 4. Social Features (Framework)

Social sharing buttons can be added to book listings. The framework is ready for integration with social media APIs.

## UI Enhancements

### Header Updates:
- Added Wishlist link with icon
- Added Notifications link with unread count badge
- Auto-refreshing notification count (every 30 seconds)

### Search Results:
- Added "Add to Wishlist" button for each book
- Wishlist button appears only for logged-in users

### Wishlist Page:
- Table view of wishlist items
- Book details (title, author, publisher, price, stock)
- Add to Cart button
- Remove from Wishlist button
- Date added display

### Notifications Page:
- List of all notifications
- Unread indicator
- Notification types (info, success, error, warning)
- Mark as read functionality
- Delete functionality
- Link to related pages

## Integration Points

### Order Placement:
When an order is placed, a notification is automatically created:
```java
notificationBC.notifyOrderPlaced(userName, orderId);
```

### Order Status Updates:
When order status changes, a notification is created:
```java
notificationBC.notifyOrderStatusChanged(userName, orderId, newStatus);
```

## Setup Instructions

1. **Run Database Migrations**:
   Execute the SQL files in `src/main/resources/db/migrations/`:
   - `wishlist_table.sql`
   - `reviews_table.sql`
   - `notifications_table.sql`

2. **Rebuild Application**:
   ```bash
   mvn clean compile
   ```

3. **Restart Application**:
   The new features will be available after restart.

## Future Enhancements

1. **Wishlist Sharing**: Share wishlist via link or email
2. **Price Drop Alerts**: Notify users when wishlist items go on sale
3. **Review Images**: Allow users to upload images with reviews
4. **Email Notifications**: Send email notifications for important events
5. **Social Login**: Integrate Google, Facebook login
6. **Social Sharing**: Add share buttons for books on social media

## Notes

- All new features require user authentication
- Reviews require admin approval before being visible
- Notifications are stored in the database and persist across sessions
- Wishlist items are unique per user (one entry per book)
- The notification count badge updates automatically every 30 seconds

