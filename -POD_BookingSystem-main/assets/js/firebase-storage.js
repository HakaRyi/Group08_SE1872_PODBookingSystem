import { getStorage, ref, getDownloadURL } from "https://www.gstatic.com/firebasejs/10.14.1/firebase-storage.js";
import { initializeApp } from "https://www.gstatic.com/firebasejs/10.14.1/firebase-app.js";

const firebaseConfig = {
    apiKey: "AIzaSyBb4O_rxVQu5590kRl46VQhx9EzIcY0_WA",
    authDomain: "pod-booking-system-437803.firebaseapp.com",
    projectId: "pod-booking-system-437803",
    storageBucket: "pod-booking-system-437803.appspot.com",
    messagingSenderId: "627345452977",
    appId: "1:627345452977:web:6a6ff675728bcdd5161790",
    measurementId: "G-8Y8FVDJY6K"
};

// Initialize Firebase
const app = initializeApp(firebaseConfig);
const storage = getStorage(app);

// Retrieve buildings from localStorage
let storedBuildings = JSON.parse(localStorage.getItem('buildings'));

if (!storedBuildings) {
    // Initialize buildings data if none exists
    const buildings = [
        {
            building_id: "BD-01",
            name: "Rex Hotel",
            address: "141 Nguyen Hue Blvd, District 1",
            location: "Ho Chi Minh City",
            description: "A historic luxury hotel located in the heart of Ho Chi Minh City.",
            imageName: "default-image.jpg"
        },
        {
            building_id: "BD-02",
            name: "Landmark 81",
            address: "720A Dien Bien Phu, Binh Thanh District",
            location: "Ho Chi Minh City",
            description: "One of the tallest buildings in Vietnam.",
            imageName: "Landmark.jpg"
        },
        {
            building_id: "BD-04",
            name: "Bitexco Financial Tower",
            address: "2 Hai Trieu, Ben Nghe, District 1",
            location: "Ho Chi Minh City",
            description: "An iconic building in Ho Chi Minh City.",
            imageName: "Bitexco.jpg"
        },
    ];

    // Store the new buildings in localStorage
    localStorage.setItem('buildings', JSON.stringify(buildings));
} else {
    console.log('Buildings already exist in localStorage:', storedBuildings);
}
