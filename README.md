# Beer-App
Find the beer of your life with this small app

# Arquitecture

This app harness the power of the latest MAD (Modern Android Development) skills to show you a tinder-app like for beers. A match made in heaven!

## Layers

![arch](https://developer.android.com/topic/libraries/architecture/images/final-architecture.png)

### Model
Classes that transfer data from one layer onto another. This classes are divided in two types:

- Local: Local models are the classes that transfer UI-driver data to upper layers, such as the View layer.
- Remote: Remote models are models that parse the data from the external sources such an API. These models get trasnformed into Local models in the repository layer.

### Datasource
This layer contains the classes responsible for retrieving the beer dat. This classes are also split in two:

- Local: Local datasources such as SQL databases or SharedPreferences file. This classes function as cache of important data when the app is offline.
- Remote: Remote datasources such as Retrofit API interfaces.

### Repository
This layer is responsible for being the source of truth of the app. This means using one or various datasources available to fetch Beer data and then report the most 
useful data based on certain conditions (such as internet connection availability or cache validity) and report said data to the upper layers in a Local model format.

### ViewmOdel
This layer processes the UI interaction intercepted by UI controllers (Activity/Fragments) and translate them into actions a repository can understand such as fetch a 
list of favorite beers. This layer communicates to the upper view layer via the observer pattern using LiveData.

### View
This layer contains the UI controlles (Activity/Fragments) responsible of the app UI/UX, such as intercept user interactions and draw data in a user-friendly way.

## Running the app

There're two ways to get the app up and running:

1- Clone this repository, connect your devices and hit run!

2- Download the APK generated in the releases section
