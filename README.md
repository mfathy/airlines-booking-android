![](https://github.com/mfathy/AirlinesBook/blob/master/art/airlines.gif)

##  **[AirlinesBook](https://github.com/mfathy/AirlinesBook)** 

### **22<sup>th</sup> December 2018**

## REQUIREMENTS

Intro: Create an Android app that get list of airline schedules and display their origin and destination airports on a map.

Task: Implement an Android app with the following features:

 - Give option to the user to select the origin and destination airport.
 - Fetch list of airline schedules based on the selections above.
 - Display them on a list
 - Show the origin and destination of the flight on a map upon selection of a schedule and connect them with a polyline

## GETTING STARTED	

These instructions will get you a copy of the project up and running on your local machine:

1.  Clone the [project repository](https://github.com/mfathy/AirlinesBook) from Github.
    ```
    git clone https://github.com/mfathy/AirlinesBook.git
    ```
2.  Open **Android studio**, Select File | Open... and point to the the project, wait until the project syncs and builds successfully.
3.  Get access to lufthansa api from this guide >> https://developer.lufthansa.com/docs/read/api_basics/Getting_Started
3.  Run the project using Android studio.

## DISCUSSION 

###  Data Sources
There are two levels of data persistence: 
*   Network - Very slow.
*   Disk( Room Database ) - Slow.

The data layer consists of:
*   A repository pattern to provide data outside the layer itself.
*   A Remote data store layer to access remote server data.
*   A Cached data store layer to access the local data from database.

The chosen fetch of data is simple:
*   In get airports operation:
    *   Return local/cached copy if exists and not expired.
    *   Return remote copy.
*   In get access token operation: 
    *   Return remote copy.
    *   Return local/cached copy if exists and not expired.
*   In get flight schedules:
    *   Return remote copy.

#### **Remote data source""**
_The remote data source uses Okhttp or Retrofit API to call the Backend API._
#### **Local data source""**
_The local data source uses both room database and shared preference to cache/add/update/delete data locally._

### Dependency Injection
I've used **dagger** for dependency injection, also I've added different component and modules for test layer.

### Testing
I have included the required Instrumentation, Unit and UI tests with the project:
*   Unit tests for most of the app classes.
*   Integration tests for testing integration between layers components and the layer itself.
*   Ui tests using Espresso.

### Architecture
I have used a custom version of **clean architecture** with **MVVM, **which has some of clean architecture principles except layer independence, as I've used data layer models across the domain and ui layer.

#### **MVVM**
The [MVVM](https://en.wikipedia.org/wiki/Model%E2%80%93view%E2%80%93viewmodel) architecture.
*   **Model**: refers either to a domain model, or to the data access layer.
*   **View**: refers to the UI.
*   **View model**: is an abstraction of the view exposing public properties and commands. It has a binder, which automates communication between the view and its bound properties in the view model. 

#### **Why MVVM?**
*   **A good event-driven architecture**: ViewModel exposes streams of events to which the Views can bind to.
*   A **one-to-many relation** between View and ViewModel, it uses data binding to ensure that the View and ViewModel remain in sync bi-directionally.
*   **Testability**: since presenters are hard bound to Views, writing unit test becomes slightly difficult as there is a dependency of a View. ViewModels are even more Unit Test friendly.

#### **Libraries**
*   [Common Android support libraries](https://developer.android.com/topic/libraries/support-library/index.html) - Packages in the com.android.support.* namespace provide backwards compatibility and other features.
*   [AndroidX Library](https://developer.android.com/jetpack/androidx/) - AndroidX is a major improvement to the original Android [Support Library](https://developer.android.com/topic/libraries/support-library/index). Like the Support Library, AndroidX ships separately from the Android OS and provides backwards-compatibility across Android releases. AndroidX fully replaces the Support Library by providing feature parity and new libraries.
*   [Mockito](http://site.mockito.org/) - A mocking framework used to implement unit tests.
*   [Play-services](https://developers.google.com/maps/documentation/android-sdk/intro) - for google maps support.
*   [Dagger](https://github.com/google/dagger) - for dependency Injection
*   [Gson](https://github.com/google/gson) - a json serialize and deserialize library.
*   [RxJava](https://github.com/ReactiveX/RxJava) - Reactive Extensions for the JVM – a library for composing asynchronous and event-based programs using observable sequences for the Java VM. 
*   [Okhttp](http://square.github.io/okhttp/) - An HTTP+HTTP/2 client for Android and Java applications.
*   [Hamcrest](http://hamcrest.org/JavaHamcrest/) -  Junit Matchers
*   [MockWebServer](https://github.com/square/okhttp/tree/master/mockwebserver) - A scriptable web server for testing HTTP clients.
*   [Retrofit](https://square.github.io/retrofit/) - A type-safe HTTP client for Android and Java.
*   [Android Architecture Components](https://developer.android.com/topic/libraries/architecture/) - LiveData & ViewModel.
