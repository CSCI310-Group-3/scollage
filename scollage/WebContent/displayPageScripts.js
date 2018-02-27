//User stopped writing on input box, disables button if there is no test 
function editingStopped(){
    if(document.getElementById('inputBox').value.trim().length > 0) { 
        document.getElementById('submitButton').disabled = false; 
    } else { 
        document.getElementById('submitButton').disabled = true;
    }
}

//Links the image being displayed to the download button
function prepareDownload(){
	var img = document.getElementById('mainCollage');

	var canvas = document.createElement('canvas');
	canvas.id = "myCanvas";
	canvas.height = img.height;
	canvas.width = img.width;
	console.log(canvas.width);
	console.log(canvas.height);
	context = canvas.getContext('2d');
	context.drawImage(img,0,0,canvas.width,canvas.height);
	var newImg = canvas.toDataURL('image.png');
	var exportDiv = document.getElementById('exportButton');
	exportDiv.href = newImg;
	exportDiv.download = img.alt + ".png";

	// var exportDiv = document.getElementById('exportButton');
	// exportDiv.href = img.src;
	// exportDiv.download = img.alt + ".png";
}

//Hides header and main collage and displays error msg
function displayErrorMsg(topic){
	var mainCollage = document.getElementById('mainCollage');
	mainCollage.style.display = 'none';

	var exportButton = document.getElementById('exportButton');
	exportButton.style.display = 'none';

	var collageTitle = document.getElementById('title');
	collageTitle.innerHTML = ('Collage for topic ' + topic);

	var insufficientImagesMsg = document.getElementById('insufficientImages');
	insufficientImagesMsg.style.display = 'block';
	
	//Displaying all in gallery
	var galleryMainDiv = document.getElementById('galleryInner');
	var galleryImages = galleryMainDiv.children;

	//
	for(i=0; i<galleryImages.length;i++){
		galleryImages[i].style.display = 'inline-block';
	}
}

//Displays header and main collage and hides error msg
function displayMainCollageAndHeader(){
	var mainCollage = document.getElementById('mainCollage');
	mainCollage.style.display = 'block';

	var exportButton = document.getElementById('exportButton');
	exportButton.style.display = 'block';

	var insufficientImagesMsg = document.getElementById('insufficientImages');
	insufficientImagesMsg.style.display = 'none';
}

//Logic for when gallery item is pressed.
function clickedGallery(divNum){
	//Display main space and header in case error was prev displayed
	displayMainCollageAndHeader();

	//Get clicked collageID
	var clickedImgId = ("galleryCollage-" + divNum);
	var galleryMainDiv = document.getElementById('galleryInner');
	var galleryImages = galleryMainDiv.children;

	//Going through all the collages in the gallery, displaying all but the one clicked
	for(i=0; i<galleryImages.length;i++){
		if(galleryImages[i].id == clickedImgId){
			galleryImages[i].style.display = 'none';
		} else{
			galleryImages[i].style.display = 'inline-block';
		}
	}

	//Sending clicked collage to the main space
	var clickedImgContainer = document.getElementById(clickedImgId);
	var clickedImg = clickedImgContainer.children[0];
	var mainCollageImg = document.getElementById('mainCollage');
	mainCollageImg.src = clickedImg.src;
	mainCollage.alt = clickedImg.alt;

	//Setting title according to collage
	var collageTitle = document.getElementById('title');
	var imgTopic = clickedImg.alt;
	collageTitle.innerHTML = ('Collage for topic ' + imgTopic);
}

//Functionality for when a new collage is added
function addedNewCollage(imgObject){
	var galleryMainDiv = document.getElementById('galleryInner');
	var newCollageNum = galleryMainDiv.children.length;

	newCollageImg = document.createElement('img');
	newCollageImg.src = 'data:image/png;base64,' + imgObject.imageBase64;
	newCollageImg.className = 'galleryImage';
	newCollageImg.alt = imgObject.title;

	newCollageLink = document.createElement('a');
	newCollageLink.id = ("galleryCollage-" + newCollageNum);
	newCollageLink.className = "galleryLink";
	newCollageLink.setAttribute("onclick","clickedGallery('" + newCollageNum + "')");
	
	newCollageLink.appendChild(newCollageImg);
	galleryMainDiv.appendChild(newCollageLink);

	//After collage is added to the gallery, 
	//'clicking it' to display on main collage and hide form gallery
	clickedGallery(newCollageNum);
	return false;
}

function loadFirstContent(title,imgData,isError){
    console.log(title);
    console.log(imgData);
    console.log(isError);
    if(isError == 'true'){
    	newImg = {
       			title: title,
       			imageBase64: imgData
       	};
       	addedNewCollage(newImg);   		
    } else {
    	displayErrorMsg(title);
    }
}

function makeCollageRequest(){
	event.preventDefault();
	var querry = document.getElementById("inputBox").value;
	var url = "BuildAnotherImageServer?topic=" + querry;
	
	var req = new XMLHttpRequest();
	req.open("GET", url, true);
	req.send();
	req.onreadystatechange = function () {
		if(req.readyState == 4 && req.status == 200) {
			console.log(req.responseText);
			var imgDataJson = JSON.parse(req.responseText);
			var imgTitle = imgDataJson.collageTitle;
			var imgData = imgDataJson.collageImage;
			var imgValid = imgDataJson.collageValid;
			
			if(imgValid == 'true'){
				var imgObject = {
						title: imgTitle,
						imageBase64: imgData
				};
				addedNewCollage(imgObject); 
			} else {
				displayErrorMsg(imgTitle);
			}			
		}
	}
	
	
	return false;
}