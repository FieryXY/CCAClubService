import React, {useParams, useState} from 'react';
import "./ClubProfile.css";;
import 'antd/dist/antd.css';
import Login from './login.js'; 
import {
        InstagramOutlined,
        MailOutlined,
        IdcardOutlined,
        LinkOutlined
        } from "@ant-design/icons";

const ClubProfileDescription = (props) => {

    const formatURL = (url) => {

        if (url === null) {
            return null;
        }

        if(!url.startsWith("http://") && !url.startsWith("https://")) {
            url = "http://" + url;
        }
        return url;
    }


    if(props.clubInfo == null) {
        return (
            <h1 className="clubProfileTitle" style={{textAlign: "center"}}>Unable to Load Club Profile for this Club</h1>
        );
    }
  


        let socials = props.clubInfo["clubSocialDOS"];
        let clubwebsite = null; 
        let instagram = null;
        let email = null;
        let classroom = null;
        let other = null;
        for(const social of socials) {
            if(social["socialName"] == "Club Website") clubwebsite = social["socialLink"];
            if(social["socialName"] == "Instagram") instagram = social["socialLink"];
            if(social["socialName"] == "Email") email = "mailto:" + social["socialLink"];
            if(social["socialName"] == "Classroom") classroom = social["socialLink"];
            if(social["socialName"] == "Other") other = social["socialLink"];
        }
    


return (
    <>
<div className="twoColumnContainer">
        <div className="twoColumnElement verticalCenter">
            <div style={{position: "relative", width:"50%"}}>
                <img className="clubProfilePicture" src = {(props.clubInfo["profilePictureUrl"] === null) ? require("./img/ccalogo.png").default : props.clubInfo["profilePictureUrl"]}/>
            </div>
            <div className="linkShelf">
                <a className= {(clubwebsite === null) ? "socialnull" : "social"} href = {formatURL(clubwebsite)} target="_blank"><IdcardOutlined style= {{color: '#FFFFFF'}}/></a>
                                <a className={(instagram === null) ? "socialnull" : "social"} href = {formatURL(instagram)} target="_blank"><InstagramOutlined style= {{color: '#FFFFFF'}}/></a>
                                <a className={(email === null) ? "socialnull" : "social"} href = {email} target="_blank"><MailOutlined style= {{color: '#FFFFFF'}}/></a>
                                <a className = {(classroom == null) ? "socialnull" : "social"} href = {formatURL(classroom)} target="_blank"><img style={{height: "85%"}} src = {googleclassroom}/></a>

                                <a className = {(other == null) ? "socialnull" : "social"} href = {formatURL(other)} target="_blank"><LinkOutlined style= {{color: '#FFFFFF'}}/></a>
            </div>
        </div>
        <div className="twoColumnElement" style={{flexGrow:"2"}}>
            <h1 className="clubProfileTitle">{props.clubInfo["clubName"]}</h1>
            <p className="clubProfileDescription">{props.clubInfo["description"]}</p>
            <div className="clubProfileTagDiv">
                {props.clubInfo["clubCategories"].map(tag => 
                    <a className="displayTagRed">{tag}</a>
                )}
            </div>
        </div>
    </div>
    </>

        );
    }   
    export default ClubProfileDescription;