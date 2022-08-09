import React, {useState, useEffect, useRef} from 'react';
import "./ClubEditor.css";  
import ClubService from './ClubService.js';
import ClubEditorTags from './ClubEditorTags';
import ClubEditorSocials from './ClubEditorSocials';
import ClubEditorPfp from './ClubEditorPfp';
import {notification} from 'antd';

const ClubEditor = () => {
    
    const [refresh, setRefresh] = useState(true);
    const [clubInfo, setClubInfo] = useState(null);
    const [clubName, setClubName] = useState("");
    const [clubDescription, setClubDescription] = useState("");
    const [clubPfp, setClubPfp] = useState(null);
    const [clubSocials, setClubSocials] = useState([]);
    const [clubTags, setClubTags] = useState([]);

    let textAreaRef = useRef(null);

    const descriptionChangeNotification = () => {
        notification.open({
            message: 'Description Changed Successfully'
        });
    };

    const onDescriptionChange = (e) => {
		textAreaRef.current.style.height = "0px";
		textAreaRef.current.style.height = `${e.target.scrollHeight}px`;
        setClubDescription(e.target.value);
	};

    const onDescriptionSubmit = () => {
        ClubService.doClubDescriptionChange(clubDescription).then(response => {
            descriptionChangeNotification();
            setRefresh(true);
        });
    }

    useEffect(() => {
        if(refresh) {
            ClubService.doClubInfo(sessionStorage.getItem("clubId")).then(response => {
                if(response["status"] != 200) {
                    throw "Backend is not responding"
                }
                return response.json();
            }).then(json => {
                setClubInfo(json);
                setClubName(json["clubName"]);
                setClubDescription(json["description"]);
                setClubPfp(json["profilePictureUrl"]);
                setClubSocials(json["clubSocialDOS"]);
                setClubTags(json["clubCategories"]);
            }).catch(err => {alert("Something went Wrong Loading the Club Information")})
            setRefresh(false);
        }
    }, [refresh]);

    //Change description height when clubDescription state is edited directly (when loading club JSON information from server)
    useEffect(() => {
        if(textAreaRef.current == null) {
            return;
        }
        textAreaRef.current.style.height = "0px";
		textAreaRef.current.style.height = `${textAreaRef.current.scrollHeight}px`;
    }, [clubDescription])


    if(clubInfo === null) {
        return (
        <h1 className="clubProfileTitle"> Can Not Load Club Information </h1>
      );
    }
    
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
                <button className = "descChangeButton" onClick = {onDescriptionSubmit}>Submit Change</button> 
                {/* MAKE CLUB EDITOR AND MODAL CSS */}
                <ClubEditorTags clubTags = {clubTags} setClubTags = {setClubTags} setRefresh = {setRefresh}/>      
            </div>
        </div>
        </>
    );
}

export default ClubEditor;