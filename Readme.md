# XChat

XChat is a real-time chat application built with Jetpack Compose for the frontend and Nest.js for the backend. It utilizes Socket.io for real-time communication and Mongo-Db for storing chat data.

## Features

- **Real-time Communication**: XChat is a real-time chat application powered by Socket.io.
- **Secure Authentication**: Users can securely login/signup using JWT authentication.
- **Personal and Group Chats**: Engage in personal and group chats seamlessly.
- **Profile Management**: Edit about, add profile picture, and logout.
- **User Search**: Search for other users and send friend requests.
- **In App Notification**: Receive notifications for incoming friend requests.
- **Notification**: Receive notifications when other user send message while we are on a chat .
- **Group Chat Management**: Admins can manage users, change group details, and leave groups.
- **File Storage**: Utilizes AWS S3 for storing files including profile pictures and media.
- **Message Retention**: Messages are automatically deleted after 24 hours using Node.js cron jobs.
- **Message Actions**: Store, replay a message, or unsent messages.

## Technologies Used

### Frontend
- Jetpack Compose
- Socket.io
- Hilt Dagger
- Data Store
- Coil
- CameraX
- Retrofit

### Backend
- Nest.js
- Socket.io
- MongoDB
- Multer
- AWS S3
- Node.js Cron Jobs

### App UI
![Home Screen](https://github.com/kishore-bot/xchatapps/blob/main/files/Home.png)

### Code
- Ui on app folder
- Backend on backend folder
- image on files folder

## Getting Started

To get started with XChat:

1. Clone this repository.
2. Set up the frontend and backend environments.
3. Install dependencies using `npm install` or `yarn install`.
4. Run the application locally.
5. Explore the features!


For any inquiries or support, please contact [kishoreabhi333@gmail.com].

