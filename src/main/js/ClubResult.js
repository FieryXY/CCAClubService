import React, {useState} from 'react';
import { useNavigate } from 'react-router'; 
import { render } from 'react-dom';
import "./ClubSearch.css";



const maxDescriptionChars = 100;

//bro idek if I got the json names right
const ClubResult = (props) => {
    let navigate = useNavigate();
    function nav() {
     navigate('/club-profile/' + props.clubJSON["clubId"]);
    }

    //If the description is too long, cut it off and add an ellipsis
    let description = props.clubJSON["description"];
    if(description.length > maxDescriptionChars) {
        description = description.substring(0, maxDescriptionChars) + "...";
    }


    return (
        <>
        <div className="clubResult">
        <div className = "logo">
            <img className="clubSearchImage" src = {(props.clubJSON["profilePictureUrl"] == null) ? require("./img/ccalogo.png").default : props.clubJSON["profilePictureUrl"]} alt= {"Club picture of " + props.clubJSON["clubName"]}/>
         </div>
            <div className = "clubListText">
                <a onClick = {nav}  className="writeupTitle clubSearchPageA">{props.clubJSON["clubName"]}</a>
                <p style={{color:"white"}}>{description}</p>
                <div className="clubtag clubSearchPageAContainer">
                {props.clubJSON["clubCategories"].map(tag => 
                    <a className="displayTag clubSearchPageA">{tag}</a>
                )}
            </div>
        </div>
    </div>
    <hr></hr>
    </>
    
)}
export default ClubResult;