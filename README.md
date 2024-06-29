# 💅 SEA SALON

Introducing SEA SALON, a rising star in the salon industry known for its outstanding services and excellent reviews.
With a rapidly growing clientele and a stellar reputation, SEA SALON is your premier destination for all your beauty needs.
This app lets users easily browse available services, select their preferred stylist, and book appointments quickly.
With a user-friendly interface and seamless booking integration, making a reservation with a top stylist has never been easier!

## 📦 Deployment
Click the link below to download the `.apk` file: </br>
[Download SEA SALON](https://drive.google.com/drive/folders/1WrW06zoaSF2dsebosySsfyTZ0Frn15sE?usp=sharing)

## 📹 Demo Video
<a href="https://www.youtube.com/watch?v=pvng_vFeulo">
  <img src="https://github.com/elginbrian/COMPFEST16-SEA-SALON/assets/132267129/f0ef7563-32f1-4d84-98ef-1f39cbe584a1" alt="YouTube Demo" style="width:70%">
</a>

## 🛠️ Tech Stack

- **Kotlin**
- **Jetpack Compose**
- **Firebase**
- **Google Maps SDK**

This project uses Model-View-ViewModel (MVVM) Clean Architecture. 
All user interaction occurs within the View, which is in charge of detecting 
the user’s input (user's clicks, keyboard input) and forwarding it to the ViewModel.

The ViewModel is the link between the View and the Model. 
It implements and exposes public properties and commands that 
the View uses by way of data binding. If any state changes occur, 
the ViewModel notifies the View through notification events.

The Models are classes that model the application’s domain. 
Models encapsulate the application’s data and business logic. 
They can be considered business objects that have absolutely no 
relation to the visual aspect of the application.

<img src="https://github.com/elginbrian/COMPFEST16-SEA-SALON/assets/132267129/1a377adb-99d5-4fce-9c89-2825c6980527" alt="MVVM Clean Architecture" style="width:50%;"/>

## 🚀 How to run the code?
if you get this project from my GitHub page:

    1. Clone this GitHub repository in Android Studio.
    2. Insert the google-services.json file inside the "app" folder.
    3. Wait for the gradle sync to complete.
    4. Run SEA SALON via an emulator or real device.

    *The google-services.json file can be downloaded from the link below.

[Download google-services.json](https://drive.google.com/drive/folders/1WrW06zoaSF2dsebosySsfyTZ0Frn15sE?usp=sharing)
