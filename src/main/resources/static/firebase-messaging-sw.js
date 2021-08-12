//Firebase에 호스팅 햇을 때
/*importScripts('/__/firebase/8.9.0/firebase-app.js');
importScripts('/__/firebase/8.9.0/firebase-messaging.js');
importScripts('/__/firebase/init.js');*/

//호스팅 안했을 때
importScripts('https://www.gstatic.com/firebasejs/8.9.0/firebase-app.js');
importScripts('https://www.gstatic.com/firebasejs/8.9.0/firebase-messaging.js');
// 전달하여 서비스 워커에서 Firebase 앱을 초기화합니다.
// 앱의 Firebase 구성 객체.
// https://firebase.google.com/docs/web/setup#config-object

var firebaseConfig = {
    apiKey: "AIzaSyCQX_Ckogcx9f3fuPUnptxv2uuTQCHOACM",
    authDomain: "ichatu-d9085.firebaseapp.com",
    projectId: "ichatu-d9085",
    storageBucket: "ichatu-d9085.appspot.com",
    messagingSenderId: "390106992570",
    appId: "1:390106992570:web:11de721021b09c43961783",
    measurementId: "G-RNMNSRP76P"
};

firebase.initializeApp(firebaseConfig);

var messaging = firebase.messaging();