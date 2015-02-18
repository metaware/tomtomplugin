var TomTomPlugin = {
    openNavAppClient: function(latitude, longitude, successCallback, errorCallback) {
        cordova.exec(
            successCallback, 
            errorCallback, 
            'TomTomPlugin',
            'openNavAppClient',
            [{
                "longitude": longitude,
                "latitude": latitude
            }]
        ); 
     }
}
module.exports = TomTomPlugin;