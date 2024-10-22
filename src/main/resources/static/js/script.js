// Open and close the popup for image upload (you can remove these if you no longer need the popup functionality)
function openPopup() {
    document.getElementById("uploadPopup").style.display = "block";
}

function closePopup() {
    document.getElementById("uploadPopup").style.display = "none";
}

// Function to upload the selected image and additional data to the backend
function uploadImage(event) {
    event.preventDefault(); // Prevent the default form submission

    const imageInput = document.getElementById('imageInput');
    const nameInput = document.getElementById('nameInput');
    const descriptionInput = document.getElementById('descriptionInput');
    const locationInput = document.getElementById('locationInput');
    const uploadStatus = document.getElementById('uploadStatus');

    if (imageInput.files.length === 0) {
        uploadStatus.textContent = 'Please select an image!';
        return;
    }

    const file = imageInput.files[0];
    const formData = new FormData();
    formData.append('image', file);
    formData.append('name', nameInput.value);
    formData.append('description', descriptionInput.value);
    formData.append('location', locationInput.value);

    fetch('/uploadToGoogleDrive', {
        method: 'POST',
        body: formData,
    })
        .then(response => response.json())
        .then(data => {
            if (data.status === 200) {
                uploadStatus.textContent = 'Image successfully uploaded!';
                clearInputs(); // Optionally clear the inputs after successful upload
            } else {
                uploadStatus.textContent = 'Error uploading image: ' + data.message;
            }
        })
        .catch(error => {
            uploadStatus.textContent = 'Error uploading image: ' + error.message;
        });
}

// Function to display current time
function displayTime() {
    const timeDisplay = document.getElementById('time');
    const now = new Date();
    timeDisplay.textContent = now.toLocaleTimeString();
}

setInterval(displayTime, 1000);

// Dummy search function (you can integrate this with your backend if needed)
function searchLocation() {
    const searchBar = document.getElementById('searchBar');
    const query = searchBar.value;
    alert('Search functionality for "' + query + '" is not yet implemented.');
}

// Function to clear input fields
function clearInputs() {
    document.getElementById('imageInput').value = '';
    document.getElementById('nameInput').value = '';
    document.getElementById('descriptionInput').value = '';
    document.getElementById('locationInput').value = '';
}
