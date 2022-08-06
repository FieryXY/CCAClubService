import React, {useState, useEffect} from 'react';
import "./ClubEditor.css";
import 'antd/dist/antd.css';
import Modal from 'react-modal';
import ClubService from './ClubService';
import SocialModal from './SocialModal';
import {
        InstagramOutlined,
        MailOutlined,
        IdcardOutlined,
        LinkOutlined
        } from "@ant-design/icons";
const ClubEditorSocials = (props) => {
        let socials = props.clubSocials;
        const [clubwebsite, setClubWebsite] = useState("");
        const [instagram, setInstagram] = useState("");
        const [email, setEmail] = useState("");
        const [classroom, setClassroom] = useState("");
        const [other, setOther] = useState("");
        const [currentSocial, setCurrentSocial] = useState("");
        useEffect(() => {
                for(const social of socials) {
                        if(social["socialName"] === "Club Website") setClubWebsite(social["socialLink"]);
                        if(social["socialName"] === "Instagram") setInstagram(social["socialLink"]);
                        if(social["socialName"] === "Email") setEmail("mailto:" + social["socialLink"]);
                        if(social["socialName"] === "Classroom") setClassroom(social["socialLink"]);
                        if(social["socialName"] === "Other") setOther(social["socialLink"]);
                }}, [])
        const [socialModalIsOpen, setSocialModalIsOpen] = useState(false);
        const onSocialAdd = (social) => {
                setCurrentSocial(social);
                setSocialModalIsOpen(true);
        }
        
        return(
        <>      
                <Modal style = {{content: {"background" : "#3A4750", "overflow" : "scroll", "borderRadius" : "25px"}}} isOpen = {socialModalIsOpen} onRequestClose = {() => {setSocialModalIsOpen(false)}}><SocialModal socialMap = {{"Club Website" : clubwebsite, "Instagram" : instagram, "Email" : email, "Google Classroom": classroom, "Other Website's" : other}} setRefresh = {props.setRefresh} currentSocial = {currentSocial} setClubSocials = {props.setClubSocials}/></Modal>
                <div className="linkShelf">
                        <a className= {(clubwebsite === "") ? "socialnull" : "social"} onClick = {() => {onSocialAdd("Club Website")}}><IdcardOutlined style= {{color: '#FFFFFF'}} href = {clubwebsite}/></a>
                        <a className={(instagram === "") ? "socialnull" : "social"} onClick = {() => {onSocialAdd("Instagram")}}><InstagramOutlined style= {{color: '#FFFFFF'}} href = {instagram}/></a> 
                        <a className={(email === "") ? "socialnull" : "social"} onClick = {() => {onSocialAdd("Email")}}><MailOutlined style= {{color: '#FFFFFF'}} href = {email}/></a>
                        <a className = {(classroom === "") ? "socialnull" : "social"} onClick = {() => {onSocialAdd("Google Classroom")}}><img className = "social"/></a>
                        <a className = {(other === "") ? "socialnull" : "social"} onClick = {() => {onSocialAdd("Other Website's")}}><LinkOutlined style= {{color: '#FFFFFF'}} href = {other}/></a>
                </div>        
        </>
        );
}
export default ClubEditorSocials;