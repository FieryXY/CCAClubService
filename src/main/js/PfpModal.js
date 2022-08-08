import React, {useRef, useState, useEffect} from 'react'
import 'react-image-crop/dist/ReactCrop.css'
import ReactCrop, {centerCrop, makeAspectCrop} from 'react-image-crop';
import "./ClubEditor.css";  


const storageSideLength = 200;

const PfpModal = (props) => {
    const fileInput = useRef(null);
    const [image, setImage] = useState("");
    const [aspect, setAspect] = useState(1);
    const [crop, setCrop] = useState({
        unit: '%',
        width: 50,
        height: 50,
        x: 25,
        y: 25
    });
    const [showImageLoading, setShowImageLoading] = useState(false);


    const createImageFromCrop = () => {

      if(image == null || image == "") {
        return;
      }

      let canvas = document.getElementById("invisible-canvas")
      let ctx = canvas.getContext("2d")
      canvas.width = storageSideLength;
      canvas.height = storageSideLength;
      let imageObj = new Image();
      imageObj.src = image;

      let cropX = crop.x * imageObj.width / 100;
            let cropY = crop.y * imageObj.height / 100;
            let cropWidth = crop.width * imageObj.width / 100;
            let cropHeight = crop.height * imageObj.height / 100;

      ctx.drawImage(imageObj, cropX, cropY, cropWidth, cropHeight, 0, 0, storageSideLength, storageSideLength);

      canvas.toBlob((blob) => {
        props.handleFile(blob);
      });
    }

    function onImageLoad(e) {
        const { naturalWidth: width, naturalHeight: height } = e.currentTarget;
      
        const crop = centerCrop(
          makeAspectCrop(
            (width < height) ? {
              unit: '%',
              width: 100    
            } : {
              unit: '%',
              height: 100
            },
            1,
            width,
            height
          ),
          width,
          height
        )
      
        setCrop(crop);
      }

    const handleFileInput = (e) => {
        setShowImageLoading(false);
        const file = e.target.files[0];
        if(file == null) return;
        //change this back later
        if (file.size < 2048) {
          props.onFileSelectError({ error: "File size cannot exceed more than 2MB" });
          return;
        }
        if (e.target.files && e.target.files[0]) {
          setImage(URL.createObjectURL(e.target.files[0]));
        }

    }
    const submitForm = () => {
        props.setPfpSelectorOpen(false);
        createImageFromCrop();
    };

    return (
        <div>
            <form encType= "multipart/form-data" className="pfpForm">
            <h1 className="clubSearchPageH1">Select a Club Profile Picture</h1>
            {showImageLoading && <p>Image Loading...</p>}

            
            
            <ReactCrop crop={crop} onChange={(_, percentCrop) => setCrop(percentCrop)} circularCrop = {true} keepSelection = {true} aspect = {1} style={{maxWidth: "40%"}}>
                <img id="target" src={image} onLoad = {onImageLoad} style={{maxWidth: "100%", height: "auto"}}/>
            </ReactCrop>

            <label>
              <input type = "file" name="myImage" accept="image/x-png,image/jpeg" onChange={handleFileInput} ref={fileInput} onClick={() => {setShowImageLoading(true)}}
               style={{display:"none"}}   />
              <span className="pfpFileInput">Choose File</span>
            </label>
            
            <input type ="button" value="Submit" onClick = {submitForm}/>

            </form>

            <canvas style={{visiblity: "none"}} id="invisible-canvas"></canvas>
        </div>
    ); 
}

export default PfpModal;