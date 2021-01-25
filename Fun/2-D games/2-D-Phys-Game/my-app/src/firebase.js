import firebase from 'firebase';

const firebaseConfig = {
    apiKey: "AIzaSyBEeBhDLCN04T4BttkD5PRT3Wd5015ZCnQ",
    authDomain: "comp426-7149a.firebaseapp.com",
    databaseURL: "https://comp426-7149a.firebaseio.com",
    projectId: "comp426-7149a",
    storageBucket: "comp426-7149a.appspot.com",
    messagingSenderId: "243486709644",
    appId: "1:243486709644:web:5deb2c504ae5169de8e8a9",
    measurementId: "G-L9XJNJ231H"
  };

  firebase.initializeApp(firebaseConfig);
  export const auth = firebase.auth();
  export default firebase;