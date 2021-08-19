# Cheers 🍻
Using "punkapi.com" API to retrieve beer information. Using Jetpack Compose for UI.

The following app use the **MVVM pattern** to handle data flows.

## Architecture

- The app has only one Activity: [MainActivity.kt](https://github.com/federicoviceconti/Cheers/blob/master/app/src/main/java/open/vice/cheers/MainActivity.kt), 
which contains the definition of the theme.
- The home page is under the folder ["functionalities/home/HomeComponentView.kt"](https://github.com/federicoviceconti/Cheers/blob/master/app/src/main/java/open/vice/cheers/functionalities/home/HomeComponentView.kt)
- HomePageComponentView has a ViewModel associated: "HomeComponentViewModel", where we handle the data change using the MutableLiveData

## Network

To interact with the [punkapi.com](PunkApi), I used the Retrofit plugin

## Dependency management

To reduce the boilerplate and avoid to handle the dependency manually, I used Hilt. 
This library is easy to use and it's build on the Dagger library.
