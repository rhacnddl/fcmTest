const firebaseModule = (function () {
    async function init() {
        // Your web app's Firebase configuration
        if ('serviceWorker' in navigator) {
            window.addEventListener('load', function() {
                navigator.serviceWorker.register('/firebase-messaging-sw.js')
                    .then(registration => {
                        var firebaseConfig = {
                            apiKey: "AIzaSyDOCAbC123dEf456GhI789jKl012-MnO",
                            authDomain: "ichatu-d9085.firebaseapp.com",
                            databaseURL: "https://ichatu-d9085.firebaseio.com",
                            projectId: "ichatu-d9085",
                            storageBucket: "ichatu-d9085.appspot.com",
                            messagingSenderId: "390106992570",
                            appId: "1:390106992570:web:11de721021b09c43961783"
                            //measurementId: "G-MEASUREMENT_ID"
                        };
                        // Initialize Firebase
                        firebase.initializeApp(firebaseConfig);


                        // Show Notificaiton Dialog
                        const messaging = firebase.messaging();

                        messaging.requestPermission()
                            .then(function() {
                                return messaging.getToken();
                            })
                            .then(async function(token) {
                                await fetch('/register', { method: 'post', body: token })
                                messaging.onMessage(payload => {
                                    const title = payload.notification.title
                                    const options = {
                                        body : payload.notification.body
                                    }
                                    navigator.serviceWorker.ready.then(registration => {
                                        registration.showNotification(title, options);
                                    })
                                })
                            })
                            .catch(function(err) {
                                console.log("Error Occured");
                            })
                    })
            })
        }
    }

    return {
        init: function () {
            init()
        }
    }
})()

firebaseModule.init()