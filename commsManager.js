// commsManager.js

export async function fetchGameData() {
    try {
      const response = await fetch('http://localhost:3100/gameStats');
      const data = await response.json();
      

      
    
      if (data.success) {
        return data.data;
      } else {
        console.error('Error fetching game stats:', data.message);
        return null;
      }
    } catch (error) {
      console.error('Error fetching game stats:', error);
      return null;
    }
  }
  
  export async function updateGameData(gameStats) {
    try {
      const response = await fetch('http://localhost:3100/updateGameStats', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify({
          gameStats
        })
      });
      const data = await response.json();
      if (data.success) {
        console.log('Stats saved successfully');
      } else {
        console.error('Error saving stats:', data.message);
      }
    } catch (error) {
      console.error('Error saving stats:', error);
    }
  }
  export async function getImage() {
    try {
        const response = await fetch('http://localhost:3100/getImage');
        const imageData = await response.text(); // Assuming the image is stored as text data
        console.log("GET IMAGE" + imageData)
        return imageData;
    } catch (error) {
        console.error('Error fetching image:', error);
        return null;
    }
}

export async function saveImage(imageData) {
    try {
        
        const response = await fetch('http://localhost:3100/uploadImage', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json' // Assuming the image data is sent as plain text
            },
            body: JSON.stringify({"image":imageData})
        });
        const data = await response.json();
        
        if (data.success) {
            console.log('Image saved successfully');
        } else {
            console.error('Error saving image:', data.message);
        }
    } catch (error) {
        console.error('Error saving image:', error);
    }
}
  