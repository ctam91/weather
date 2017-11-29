// Global variabes

function initMap() {
        var map = new google.maps.Map(document.getElementById('map'), {
          mapTypeControl: false,
          center: {lat: 47.6062, lng: -122.3321},
          zoom: 7
        });

        /** var image = "/img/umbrella.png";
        var marker = new google.maps.Marker({
            position: {lat: 47.6062, lng: -122.3321},
            map: map,
            icon: image
        });
        */
        new AutocompleteDirectionsHandler(map);

          // Global variables
          var geoJSON;
          var request;
          var gettingData = false;
          var openWeatherMapKey = "56ec6b66183fb28d7a0354e944c3a4fc";
          var infowindow = new google.maps.InfoWindow();

        // Define function intialize.
          function initialize() {
            // Add interaction listeners to make weather requests
            google.maps.event.addListener(map, 'idle', checkIfDataRequested);

            // Sets up and populates info windows with details.
            // The info window displays a weather icon, city name, temperature in celsisu, and a short weather description above map at given location. Each info window is associate with a marker.
            map.data.addListener('click', function(event) {
              infowindow.setContent(
               "<img src=" + event.feature.getProperty("icon") + " >"
               + "<br /><strong>" + event.feature.getProperty("city") + "</strong>"
               + "<br />" + convertTemp(event.feature.getProperty("temperature")) + "&deg;F"
               + "<br />" + event.feature.getProperty("weather")
               );
              infowindow.setOptions({
                  position:{
                    lat: event.latLng.lat(),
                    lng: event.latLng.lng()
                  },
                  pixelOffset: {
                    width: 0,
                    height: -15
                  }
                });
              infowindow.open(map);
            });
          }

          var checkIfDataRequested = function() {
            // Stop extra requests being sent
            while (gettingData === true) {
              request.abort();
              gettingData = false;
            }
            getCoords();
          };

          // Get the coordinates from the Map bounds
          var getCoords = function() {
            var bounds = map.getBounds();
            var NE = bounds.getNorthEast();
            var SW = bounds.getSouthWest();
            getWeather(NE.lat(), NE.lng(), SW.lat(), SW.lng());
          };

          // Make the weather request
          var getWeather = function(northLat, eastLng, southLat, westLng) {
            gettingData = true;
            var requestString = "http://api.openweathermap.org/data/2.5/box/city?bbox="
                                + westLng + "," + northLat + "," //left top
                                + eastLng + "," + southLat + "," //right bottom
                                + map.getZoom()
                                + "&cluster=yes&format=json"
                                + "&APPID=" + openWeatherMapKey;
            request = new XMLHttpRequest();
            request.onload = processResults;
            request.open("get", requestString, true);
            request.send();
          };

          // Take the JSON results and process them.
          // Create a var called results to store the results.
          // If the result's length is greater than 0 (if we have a result), clear the map and
          // for each element in the result, turn the json into GeoJson and add it to our geoJSON features.
          // Add icons (based on geoJSON) to the map.
          var processResults = function() {
            console.log(this);
            var results = JSON.parse(this.responseText);
            if (results.list.length > 0) {
                resetData();
                for (var i = 0; i < results.list.length; i++) {
                  geoJSON.features.push(jsonToGeoJson(results.list[i]));
                }
                drawIcons(geoJSON);
            }
          };

          // For each result that comes back, convert the data to geoJSON.
          var jsonToGeoJson = function (weatherItem) {
            var feature = {
              type: "Feature",
              properties: {
                city: weatherItem.name,
                weather: weatherItem.weather[0].main,
                temperature: weatherItem.main.temp,
                min: weatherItem.main.temp_min,
                max: weatherItem.main.temp_max,
                humidity: weatherItem.main.humidity,
                pressure: weatherItem.main.pressure,
                windSpeed: weatherItem.wind.speed,
                windDegrees: weatherItem.wind.deg,
                windGust: weatherItem.wind.gust,
                icon: "http://openweathermap.org/img/w/"
                      + weatherItem.weather[0].icon  + ".png",
                coordinates: [weatherItem.coord.Lon, weatherItem.coord.Lat]
              },
              geometry: {
                type: "Point",
                coordinates: [weatherItem.coord.Lon, weatherItem.coord.Lat]
              }
            };
            // Set the custom marker icon
            map.data.setStyle(function(feature) {
              return {
                icon: {
                  url: feature.getProperty('icon'),
                  anchor: new google.maps.Point(25, 25)
                }
              };
            });
            // returns object
            return feature;
          };

          // Add the markers to the map
          var drawIcons = function (weather) {
             map.data.addGeoJson(geoJSON);
             // Set the flag to finished
             gettingData = false;
          };

          // Clear data layer and geoJSON
          var resetData = function () {
            geoJSON = {
              type: "FeatureCollection",
              features: []
            };
            map.data.forEach(function(feature) {
              map.data.remove(feature);
            });
          };

          var convertTemp = function(tempC){
            var tempF = tempC * (9/5) + 32;
            return Math.trunc(tempF)
          }

          // Adds a listener to the window object, which as soon as the load event is triggered (i.e. "the page has finished loading") executes the function initialize.
          google.maps.event.addDomListener(window, 'load', initialize);
      }

   /**
    * @constructor
   */
function AutocompleteDirectionsHandler(map) {
    this.map = map;
    this.originPlaceId = null;
    this.destinationPlaceId = null;
    this.travelMode = 'WALKING';
    var originInput = document.getElementById('origin-input');
    var destinationInput = document.getElementById('destination-input');
    var modeSelector = document.getElementById('mode-selector');
    this.directionsService = new google.maps.DirectionsService;
    this.directionsDisplay = new google.maps.DirectionsRenderer;
    this.directionsDisplay.setMap(map);

    var originAutocomplete = new google.maps.places.Autocomplete(originInput, {placeIdOnly: true});
    var destinationAutocomplete = new google.maps.places.Autocomplete(destinationInput, {placeIdOnly: true});

    this.setupClickListener('changemode-walking', 'WALKING');
    this.setupClickListener('changemode-transit', 'TRANSIT');
    this.setupClickListener('changemode-driving', 'DRIVING');

    this.setupPlaceChangedListener(originAutocomplete, 'ORIG');
    this.setupPlaceChangedListener(destinationAutocomplete, 'DEST');

    this.map.controls[google.maps.ControlPosition.TOP_LEFT].push(originInput);
    this.map.controls[google.maps.ControlPosition.TOP_LEFT].push(destinationInput);
    this.map.controls[google.maps.ControlPosition.TOP_LEFT].push(modeSelector);
  }

  // Sets a listener on a radio button to change the filter type on Places
  // Autocomplete.
  AutocompleteDirectionsHandler.prototype.setupClickListener = function(id, mode) {
    var radioButton = document.getElementById(id);
    var me = this;
    radioButton.addEventListener('click', function() {
      me.travelMode = mode;
      me.route();
    });
  };

  AutocompleteDirectionsHandler.prototype.setupPlaceChangedListener = function(autocomplete, mode) {
    var me = this;
    autocomplete.bindTo('bounds', this.map);
    autocomplete.addListener('place_changed', function() {
      var place = autocomplete.getPlace();
      if (!place.place_id) {
        window.alert("Please select an option from the dropdown list.");
        return;
      }
      if (mode === 'ORIG') {
        me.originPlaceId = place.place_id;
      } else {
        me.destinationPlaceId = place.place_id;
      }
      me.route();
    });

  };

  AutocompleteDirectionsHandler.prototype.route = function() {
    if (!this.originPlaceId || !this.destinationPlaceId) {
      return;
    }
    var me = this;

    this.directionsService.route({
      origin: {'placeId': this.originPlaceId},
      destination: {'placeId': this.destinationPlaceId},
      travelMode: this.travelMode
    }, function(response, status) {
      if (status === 'OK') {
        me.directionsDisplay.setDirections(response);
      } else {
        window.alert('Directions request failed due to ' + status);
      }
    });
  };
