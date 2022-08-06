import React, {useParams, useState} from 'react';
import "./ClubProfile.css";
import 'antd/dist/antd.css';
import Login from './login.js'; 
import {
        InstagramOutlined,
        MailOutlined,
        IdcardOutlined,
        LinkOutlined
        } from "@ant-design/icons";

const ClubProfileDescription = (props) => {
    let socials = props.clubInfo["clubSocialDOS"];
    let clubwebsite = null; 
    let instagram = null;
    let email = null;
    let classroom = null;
    let other = null;
    for(const social of socials) {
        if(social["socialName"] = "Club Website") clubwebsite = social["socialLink"];
        if(social["socialName"] = "Instagram") instagram = social["socialLink"];
        if(social["socialName"] = "Email") email = "mailto:" + social["socialLink"];
        if(social["socialName"] = "Classroom") classroom = social["socialLink"];
        if(social["socialName"] = "Other") other = social["socialLink"];
    }
  


return (
    <>
<div className="twoColumnContainer">
        <div className="twoColumnElement verticalCenter">
                <img className="clubProfilePicture" src = {(props.clubInfo["profilePictureUrl"] === null) ? "./img/ccalogo.png" : props.clubInfo["profilePictureUrl"]}/>
            <div className="linkShelf">
                <a className= {(clubwebsite === null) ? "socialnull" : "social"}><IdcardOutlined style= {{color: '#FFFFFF'}} href = {clubwebsite}/></a>
                <a className={(instagram === null) ? "socialnull" : "social"}><InstagramOutlined style= {{color: '#FFFFFF'}} href = {instagram}/></a> 
                <a className={(email === null) ? "socialnull" : "social"}><MailOutlined style= {{color: '#FFFFFF'}} href = {email}/></a>
                <a className = {(classroom == null) ? "socialnull" : "social"} href = {classroom}><img className = "social"/></a>
                <a className = {(other == null) ? "socialnull" : "social"}><LinkOutlined style= {{color: '#FFFFFF'}} href = {other}/></a>
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