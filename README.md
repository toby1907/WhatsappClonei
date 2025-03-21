## WhatsappClonei
This project is a simplified clone of the popular WhatsApp messaging application, focusing on core features such as user authentication, status updates (text, image, and video), and real-time data synchronization. The app is built using modern Android development practices and leverages the power of Jetpack libraries and Firebase services.
## Features
- User Authentication: Secure user sign-in and registration using phone number verification.
- Messaging: Implement real-time text messaging between users.
- Status Updates: Users can create and view status updates, including text, images, and videos.
- Real-time Data: Status updates are synchronized in real-time across all users.
- Status Expiration: Statuses automatically expire after 24 hours.


## Technology Stack
- **[Kotlin](https://kotlinlang.org/)**: The official programming language for developing Android applications.
- **[Jetpack Compose](https://developer.android.com/develop/ui/compose)**: A modern UI toolkit for building Android applications in Kotlin.
- **[Dagger Hilt](https://dagger.dev/hilt/)**: Dependency Injection Framework.
- **[ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel)**: A lifecycle-aware Android Architecture Component for holding state.
- **[Room Persistence Library](https://developer.android.com/training/data-storage/room)**: Android Jetpack Library for local data caching.
- **[Firebase Authentication](https://firebase.google.com/docs/auth/)**:  Manages user authentication using phone number verification.
- **[Firebase Storage](https://firebase.google.com/docs/storage/)**: Used to store media files (images and videos) for status updates
- **[ExoPlayer](https://developer.android.com/media/media3/exoplayer)**: A library for playing audio and video content. Used to display video statuses.

## Libraries
- **[Coil](https://coil-kt.github.io/coil/)**: A library for playing audio and video content. Used to display video statuses.

## Screenshots
|       ::::::::::::::::::::::::::::::::::::::::       |       ::::::::::::::::::::::::::::::::::::::::       |   ::::::::::::::::::::::::::::::::::::::::   |    ::::::::::::::::::::::::::::::::::::::::     |    ::::::::::::::::::::::::::::::::::::::::     |      ::::::::::::::::::::::::::::::::::::::::      |     ::::::::::::::::::::::::::::::::::::::::      |    ::::::::::::::::::::::::::::::::::::::::    |
|:----------------------------------------------------:|:----------------------------------------------------:|:--------------------------------------------:|:-----------------------------------------------:|:-----------------------------------------------:|:--------------------------------------------------:|:-------------------------------------------------:|:----------------------------------------------:|
| ![Verification Screen](./screenshots/screen%201.jpg) | ![Confirm_Code_Screen](./screenshots/screen%202.jpg) | ![Home Screen](./screenshots/screen%203.jpg) | ![Chat Screen 01](./screenshots/screen%204.jpg) | ![Chat Screen 02](./screenshots/screen%205.jpg) | ![StatusList Screen](./screenshots/screen%206.jpg) | ![StatusAdd Screen](./screenshots/screen%208.jpg) | ![Status Screen](./screenshots/screen%207.jpg) |

## Setup Instructions

1. **Clone the repository to your local machine.**
```bash
git clone  https://github.com/toby1907/WhatsappClonei.git

cd WhatsappClonei/
```
2. **Open in Android Studio**
- Open Android Studio
- Select `File` > `Open...`
```
3. **Build and run the app**
- Ensure your Android device or emulator is set up.
- Click on the `Run` button or use `Shift + F10`.


