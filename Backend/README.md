# XChat Backend

XChat Backend is the server-side component of the XChat application, responsible for handling user authentication, managing chats, and interacting with databases and external services.

## Technologies Used

- **Nest.js**: A progressive Node.js framework for building efficient, reliable, and scalable server-side applications.
- **Socket.io**: Enables real-time, bidirectional, and event-based communication between clients and servers.
- **MongoDB**: A NoSQL database used for storing chat data.
- **AWS S3**: Cloud storage service used for storing user files and media.
- **Node.js Cron Jobs**: Used for scheduling tasks, such as deleting messages after a certain time period.

## Features

- **Real-time Communication**: Utilizes Socket.io for real-time communication between clients.
- **JWT Authentication**: Provides secure login/signup using JWT tokens.
- **Chat Management**: Manages personal and group chats, including creating, updating, and deleting messages.
- **User Management**: Handles user authentication, profile management, and friend requests.
- **File Storage**: Integrates with AWS S3 for storing user files and media.
- **Message Retention**: Utilizes Node.js cron jobs to automatically delete messages after a specified time period.

## Installation

1. Clone this repository:


2. Install dependencies:



3. Set up environment variables:

   - Create a `.env` file based on the provided `.env.example` file.
   - Configure environment variables such as MongoDB connection URI, JWT secret, AWS S3 credentials, etc.

4. Run the application:




5. The server should now be running locally. You can access it at `http://localhost:3000`.

6. The API endpoints and their usage are documented in detail. You can find the API documentation [here](https://github.com/kishore-bot/xchatapps/blob/main/files/Chat%20App%20Https.postman_collection.json).



## Contact

For any inquiries or support, please contact [kishoreabhi333@gmail.com].
