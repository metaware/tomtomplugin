# Apache Cordova TomTom Plugin

Its a cordova plugin that'll integrate your phonegap/cordova app with the TomTom Bridge NavApp Client. Currently, the plugin just support one API call which will open up the NavApp Client, from your phonegap application. In the cordova app you can simply pass the destination coordinates and the API will plan the trip to the destination from the user's current location. 

# Installation 

On CLI type the following:

`cordova plugin add https://github.com/metaware/tomtomplugin.git`

```
  var lat = "49.0";
  var lang = "-79.12";
  alert("routing to Lat:"+ lat + ", " + "lang: " + lang);
  TomTomPlugin.openNavAppClient(lat, lang, function(message){
    alert("success!"); // Callback success block
  }, function(msg){
    alert(msg); // Callback error block
  })
```

You can contact us at hello@metawarelabs.com for more information or if you have any questions.

