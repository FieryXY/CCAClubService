import React, {useState, useEffect, useRef} from 'react';
import "./ClubEditor.css";  
import ClubService from './ClubService.js';
import ClubEditorTags from './ClubEditorTags';
import ClubEditorSocials from './ClubEditorSocials';
import ClubEditorPfp from './ClubEditorPfp';
const ClubEditor = () => {
    
    const [refresh, setRefresh] = useState(true);
    const [clubInfo, setClubInfo] = useState(null);
    const [clubName, setClubName] = useState("");
    const [clubDescription, setClubDescription] = useState("");
    const [clubPfp, setClubPfp] = useState("");
    const [clubSocials, setClubSocials] = useState([]);
    const [clubTags, setClubTags] = useState([]);

    let textAreaRef = useRef(null);

    const onDescriptionChange = (e) => {
		textAreaRef.current.style.height = "0px";
		textAreaRef.current.style.height = `${e.target.scrollHeight}px`;
        setClubDescription(e.target.value);
	};

    const onDescriptionSubmit = () => {
        ClubService.doClubDescriptionChange(clubDescription).then(response => {setRefresh(true)});
    }

    if(refresh === true) {
        ClubService.doClubInfo(sessionStorage.getItem("clubId")).then(response => response.json()).then(json => {
            setClubInfo(json);
            setClubName(json["clubName"]);
            setClubDescription(json["description"]);
            setClubPfp(json["profilePictureUrl"]);
            setClubSocials(json["clubSocialDOS"]);
            setClubTags(json["clubCategories"]);
        }).catch(err => {console.log("backend is not responding")})
        setRefresh(false);
    }


    if(clubInfo === null) {
        return (
        <h1 className="clubProfileTitle"> Can Not Load Club Information </h1>
      );
    }
    if(clubPfp === null) setClubPfp("./img/ccalogo.png");
    
    return(
    <>
    <div className="twoColumnContainer">
        <div className="twoColumnElement verticalCenter">
                <ClubEditorPfp setRefresh = {setRefresh} clubPfp = {clubPfp} setClubPfp = {setClubPfp}/>
                {/*MAKE CSS FOR MODAL LOL */}
                <ClubEditorSocials clubSocials = {clubSocials} setClubSocials = {setClubSocials} setRefresh = {setRefresh}/>
        </div>
        <div className="twoColumnElement" style={{flexGrow:"2"}}>
            <h1 className="clubProfileTitle">{clubName}</h1>
            <textarea value = {clubDescription} ref={textAreaRef} onChange={onDescriptionChange} className="clubDescriptionEditor"/>
            {/*MAKE BUTTON CSS */} 
            <button className = "descChangeButton" onClick = {onDescriptionSubmit}>Submit</button> 
            {/* MAKE CLUB EDITOR AND MODAL CSS */}
            <ClubEditorTags clubTags = {clubTags} setClubTags = {setClubTags} setRefresh = {setRefresh}/>      
        </div>
    </div>
    </>
    );
}

export default ClubEditor;