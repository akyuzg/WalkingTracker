# WalkingTracker
 An Android app written in Kotlin that demonstrates a clean architecture with MVVM.

### MVVM pattern with Clean architecture developed with Kotlin.
- Tracks your walking with images for every 100 meter.
- Image service provided from Flickr based on location.
- User can see photos in a list 
- Even if the user send the application to the background, the photos contunie to be saved.
- Room database was used to store photo data.



##### Android Jetpack Components used:
- Fragment
- ViewModel 
- View Binding
- LiveData 
- Flow
- Room

##### Libraries:
- [Hilt Dagger](https://developer.android.com/training/dependency-injection/hilt-android) 
- [Kotlin Coroutines](https://developer.android.com/kotlin/coroutines)
- [Retrofit](https://square.github.io/retrofit/)
- [OkHttp](https://square.github.io/okhttp/)
- [Glide](https://github.com/bumptech/glide)

##### Flickr API:
- [flickr.photos.search](https://www.flickr.com/services/api/flickr.photos.search.html)

