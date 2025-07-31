# 📱 AndroidMVCExample

[//]: # (Banner)

A simple Android project showcasing **MVC (Model-View-Controller)** architecture combined with the **Repository Pattern**, built using Kotlin and Retrofit. This app fetches data from the [DummyJSON API](https://dummyjson.com) and includes basic features like login and viewing posts.


## ✨ Main Features
- 🔐 Login as a user and get access + refresh tokens
- 📄 Fetch all posts
- 📝 View details of a single post
- 💬 View comments on a post
- 🔄 Refresh access token using a refresh toke

## 🧱 Project Structure
```
androidmvcexample/
├── controller/ -> Handles UI logic (Controller)
├── model/ -> Data models, repository, and API responses
├── network/ -> Retrofit config and interceptors
├── util/ -> Utilities (dialogs, shared preferences, etc.)
├── view/ -> UI (Activity, contracts, base view & controller)
```

## 🛠️ Technologies Used
- **Kotlin** – Main language used to build the app
- **Retrofit** – Library for handling REST API communication
- **OkHttp** – Library for HTTP requests and logging

## 🌐 API Endpoints
- `POST /user/login` → Log in user
- `GET /posts` → Get all posts
- `GET /posts/{id}` → Get single post by ID
- `GET /posts/{id}/comments` → Get comments for a post
- `POST /auth/refresh` → Refresh session with refresh token

## 📷 Screenshots

[//]: # (App UI Preview Images)